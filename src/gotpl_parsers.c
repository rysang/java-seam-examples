#include "gotpl/gotpl.h"
#include "gotpl/gotpl_parsers.h"
#include "gotpl/gotpl_stack.h"
#include "gotpl/gotpl_tag_map.h"
#include "gotpl/gotpl_tag_builtin.h"
#include "gotpl/gotpl_tag_list.h"

struct gotpl_parser {
	gotpl_pool* pool;
	gotpl_stack* parser_tag_stack;
	gotpl_stack* char_index_stack;
	gotpl_tag_map* available_tags_map;
};

typedef enum {

	gotpl_state_in_start_tag,
	gotpl_state_in_end_tag,
	gotpl_state_in_tag,
	gotpl_state_in_tag_params,
	gotpl_state_in_expr_begin,
	gotpl_state_in_expr,
	gotpl_state_in_expr_end,

	gotpl_state_plain_text

} gotpl_parser_state;

typedef struct {
	gotpl_parser_state parser_state;

	//current value
	gotpl_ui current_value_size;
	gotpl_ci current_value;
	gotpl_i8 text_buffer[gotpl_default_parser_buffer_size];
	gotpl_ui index;

	//A small stack for previous elements.
	gotpl_stack* char_index_stack;

	//reusable tag stack
	gotpl_stack* parser_tag_stack;

	//custom tags
	gotpl_tag_map* tags;

	//Default tags
	gotpl_tag_map* available_tags;

	//return list
	gotpl_tag_list* ret_list;

} gotpl_state;

static gotpl_bool gotpl_parser_is_cwhitespace(gotpl_ci utf8Char);

static gotpl_bool gotpl_parser_handle_lt(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_gt(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_diez(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_dollar(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_slash(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_open_accolade(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_close_accolade(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_plain_text(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_tag_name_text(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_tag_params_text(gotpl_state* state);
static gotpl_bool gotpl_parser_handle_expr_text(gotpl_state* state);

static gotpl_bool gotpl_parser_handle_next_char(gotpl_state* state);
static gotpl_void gotpl_parser_reset_state(gotpl_state* state);

static gotpl_bool gotpl_parser_is_cwhitespace(gotpl_ci utf8Char) {

	switch (utf8Char.m8[0]) {

	case 0x09:
	case 0x0A:
	case 0x0B:
	case 0x0C:
	case 0x0D:
	case 0x20:
		return gotpl_true;

	}

	return gotpl_false;
}

gotpl_parser* gotpl_utf8parser_create(gotpl_pool* pool) {
	gotpl_parser* parser = (gotpl_parser*) gotpl_pool_alloc(pool,
			sizeof(gotpl_parser));
	if (parser) {
		parser->parser_tag_stack = gotpl_stack_create(pool);
		if (!parser->parser_tag_stack) {
			GOTPL_ERROR("Failed to create a tag stack.");
			return 0;
		}

		parser->char_index_stack = gotpl_stack_create(pool);
		if (!parser->char_index_stack) {
			GOTPL_ERROR("Failed to create a char stack.");
			return 0;
		}

		parser->available_tags_map = gotpl_tag_map_create(
				gotpl_default_map_size, pool);

		//Add available tags here.
		if (!parser->available_tags_map) {
			GOTPL_ERROR("Failed to create a tag map.");
			return 0;
		}

		GOTPL_DEBUG("Allocated parser.");
		parser->pool = pool;
		return parser;
	}

	GOTPL_ERROR("Failed to allocate parser.");
	return 0;
}

gotpl_tag_list* gotpl_utf8parser_parse(gotpl_parser* parser,
		gotpl_input_stream* in, gotpl_tag_map* tags) {
	//State machine
	gotpl_state* state = (gotpl_state*) gotpl_pool_alloc(parser->pool,
			sizeof(gotpl_state));

	state->ret_list = (gotpl_tag_list*) gotpl_tag_list_create(parser->pool);

	memset(state, '\0', sizeof(gotpl_state));
	state->tags = tags;
	state->available_tags = parser->available_tags_map;
	state->char_index_stack = parser->char_index_stack;
	state->parser_tag_stack = parser->parser_tag_stack;
	state->tags = tags;

	while (in->has_more(in)) {
		state->current_value_size = in->read(in);
		state->current_value = in->current_char;

		if (!gotpl_parser_handle_next_char(state)) {
			GOTPL_ERROR("Error occured.");
			break;
		}
	}

	return state->ret_list;
}

static gotpl_void gotpl_parser_reset_state(gotpl_state* state) {
	state->parser_state = gotpl_state_plain_text;
	memset(state->text_buffer, '\0', gotpl_default_parser_buffer_size);
}

static gotpl_bool gotpl_parser_handle_next_char(gotpl_state* state) {
	gotpl_stack_push(state->char_index_stack,
			(gotpl_i8*) state->current_value_size);

	switch (state->current_value.m8[0]) {
	case '<':
		return gotpl_parser_handle_lt(state);
		break;
	case '#':
		return gotpl_parser_handle_diez(state);
		break;
	case '/':
		return gotpl_parser_handle_slash(state);
		break;
	case '$':
		return gotpl_parser_handle_dollar(state);
		break;
	case '{':
		return gotpl_parser_handle_open_accolade(state);
		break;
	case '}':
		return gotpl_parser_handle_close_accolade(state);
		break;
	case '>':
		return gotpl_parser_handle_gt(state);
		break;
	default:
		if (gotpl_parser_is_cwhitespace(state->current_value)) {

		} else {
			return gotpl_parser_handle_plain_text(state);
		}
		break;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_lt(gotpl_state* state) {

	switch (state->parser_state) {
	case gotpl_state_in_start_tag:
		return gotpl_parser_handle_plain_text(state);
		break;
	case gotpl_state_in_end_tag:
		return gotpl_parser_handle_tag_name_text(state);
		break;
	case gotpl_state_in_tag:
		return gotpl_parser_handle_tag_name_text(state);
		break;
	case gotpl_state_in_tag_params:
		return gotpl_parser_handle_tag_params_text(state);
		break;
	case gotpl_state_in_expr_begin:
		return gotpl_parser_handle_plain_text(state);
		break;
	case gotpl_state_in_expr:
		return gotpl_parser_handle_expr_text(state);
		break;
	case gotpl_state_in_expr_end:
		return gotpl_parser_handle_plain_text(state);
		break;

	default:
		gotpl_parser_reset_state(state);
		state->parser_state = gotpl_state_in_start_tag;
		gotpl_stack_push(state->char_index_stack, state->index);
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_gt(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_diez(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_dollar(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_slash(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_open_accolade(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_close_accolade(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_plain_text(gotpl_state* state) {
	gotpl_ui i;

	if ((state->index + state->current_value_size)
			< gotpl_default_parser_buffer_size) {

		for (i = 0; i < state->current_value_size; i++) {
			state->text_buffer[state->index + i] = state->current_value.m8[i];
		}

		state->index += state->current_value_size;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_tag_name_text(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_tag_params_text(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_expr_text(gotpl_state* state) {

	return gotpl_true;
}
