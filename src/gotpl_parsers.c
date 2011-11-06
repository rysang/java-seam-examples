#include "gotpl/gotpl.h"
#include "gotpl/gotpl_parsers.h"
#include "gotpl/gotpl_stack.h"
#include "gotpl/gotpl_tag_map.h"
#include "gotpl/gotpl_tag_builtin.h"
#include "gotpl/gotpl_tag_list.h"
#include "gotpl/gotpl_util.h"

#define gotpl_parser_args_buffer (2 * gotpl_default_parser_buffer_size)

struct gotpl_parser {
	gotpl_pool* pool;
	gotpl_stack* parser_tag_stack;
	gotpl_stack* char_index_stack;
	gotpl_tag_map* available_tags_map;
};

typedef enum {

	gotpl_state_open_tag_start,
	gotpl_state_close_tag_start,
	gotpl_state_in_end_tag,
	gotpl_state_in_tag,
	gotpl_state_in_tag_params,
	gotpl_state_in_expr_begin,
	gotpl_state_in_expr,
	gotpl_state_in_expr_end,
	gotpl_state_plain_text,

	gotpl_state_size
} gotpl_parser_state;

typedef enum {

	gotpl_current_char_gt, // <
	gotpl_current_char_lt, // >
	gotpl_current_char_diez, // #
	gotpl_current_char_slash, // /
	gotpl_current_char_dollar, // $
	gotpl_current_char_open_bracket, // {
	gotpl_current_char_close_bracket, // }
	gotpl_current_char_escape,
	gotpl_current_char_any,

	gotpl_current_char_size
} gotpl_current_char;

typedef struct {
	gotpl_parser_state parser_state;

	//current value
	gotpl_ui current_value_size;
	gotpl_ci current_value;

	gotpl_i8 text_buffer[gotpl_default_parser_buffer_size];
	gotpl_ui text_index;

	gotpl_i8 args_buffer[gotpl_parser_args_buffer];
	gotpl_ui args_index;

	//A small stack for previous elements.
	gotpl_stack* char_index_stack;

	//reusable tag stack
	gotpl_stack* parser_tag_stack;

	//custom tags
	gotpl_tag_map* custom_tags;

	//Default tags
	gotpl_tag_map* available_tags;

	//return list
	gotpl_tag_list* ret_list;

	//pool for misc allocations.
	gotpl_pool* pool;

} gotpl_state;

static gotpl_bool gotpl_parser_is_cwhitespace(gotpl_ci utf8Char);

// New stuff
static gotpl_bool gotpl_parser_dummy_handler(gotpl_state* state);
static gotpl_bool gotpl_parser_any_char(gotpl_state* state);

static gotpl_bool gotpl_parser_open_tag_start_gt(gotpl_state* state);
static gotpl_bool gotpl_parser_plain_text_lt(gotpl_state* state);

//End new STuff

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
static gotpl_bool gotpl_parser_handle_white_space(gotpl_state* state);

static gotpl_bool gotpl_parser_handle_next_char(gotpl_state* state);
static gotpl_bool gotpl_parser_pack_text_buffer(gotpl_state* state);

static gotpl_void gotpl_parser_reset_text(gotpl_state* state);
static gotpl_void gotpl_parser_reset_args(gotpl_state* state);

static gotpl_tag* gotpl_parser_get_tag(gotpl_state* state);

typedef gotpl_bool (* gotpl_char_handler_t)(gotpl_state* is);

//Define handlers
static const gotpl_char_handler_t
		handlers[gotpl_state_size][gotpl_current_char_size] = {

		// gotpl_state_open_tag_start
				gotpl_parser_open_tag_start_gt, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_close_tag_start
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_in_end_tag
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_in_tag
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_in_tag_params
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_in_expr_begin
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_in_expr
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_in_expr_end
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_dummy_handler, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler, //gotpl_current_char_any

				// gotpl_state_plain_text
				gotpl_parser_dummy_handler, //gotpl_current_char_gt
				gotpl_parser_plain_text_lt, //gotpl_current_char_lt
				gotpl_parser_dummy_handler, //gotpl_current_char_diez
				gotpl_parser_dummy_handler, //gotpl_current_char_slash
				gotpl_parser_dummy_handler, //gotpl_current_char_dollar
				gotpl_parser_dummy_handler, //gotpl_current_char_open_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_close_bracket
				gotpl_parser_dummy_handler, //gotpl_current_char_escape
				gotpl_parser_dummy_handler //gotpl_current_char_any
				};

static gotpl_bool gotpl_parser_dummy_handler(gotpl_state* state) {
	GOTPL_DEBUG("Dummy processing.");
	return gotpl_true;
}

static gotpl_bool gotpl_parser_any_char(gotpl_state* state) {
	GOTPL_DEBUG("Handling any character. Adding to buffer.");
	gotpl_ui i;

	if ((state->text_index + state->current_value_size)
			< gotpl_default_parser_buffer_size) {

		for (i = 0; i < state->current_value_size; i++) {
			state->text_buffer[state->text_index + i]
					= state->current_value.m8[i];
		}

		state->text_index += state->current_value_size;
	}

	else {
		return gotpl_parser_pack_text_buffer(state);
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_open_tag_start_gt(gotpl_state* state) {
	GOTPL_DEBUG("Handling state open_tag_start and char <");
	return gotpl_true;
}

static gotpl_bool gotpl_parser_plain_text_lt(gotpl_state* state) {
	GOTPL_DEBUG("Treating tag start.");
	state->parser_state = gotpl_state_open_tag_start;
	return gotpl_true;
}

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

	memset(state, '\0', sizeof(gotpl_state));

	state->ret_list = (gotpl_tag_list*) gotpl_tag_list_create(parser->pool);
	state->custom_tags = tags;
	state->available_tags = parser->available_tags_map;
	state->char_index_stack = parser->char_index_stack;
	state->parser_tag_stack = parser->parser_tag_stack;
	state->custom_tags = tags;
	state->parser_state = gotpl_state_plain_text;
	state->pool = parser->pool;

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

static gotpl_void gotpl_parser_reset_text(gotpl_state* state) {
	state->text_index = 0;
	memset(state->text_buffer, '\0', gotpl_default_parser_buffer_size);
}

static gotpl_void gotpl_parser_reset_args(gotpl_state* state) {
	state->args_index = 0;
	memset(state->args_buffer, '\0', gotpl_parser_args_buffer);
}

static gotpl_bool gotpl_parser_handle_next_char(gotpl_state* state) {
	//	gotpl_stack_push(state->char_index_stack,
	//		(gotpl_i8*) state->current_value_size);

	switch (state->current_value.m8[0]) {
	case '<':
		return handlers[state->parser_state][gotpl_current_char_gt](state);
		break;
	case '#':
		return handlers[state->parser_state][gotpl_current_char_diez](state);
		break;
	case '/':
		return handlers[state->parser_state][gotpl_current_char_slash](state);
		break;
	case '$':
		return handlers[state->parser_state][gotpl_current_char_dollar](state);
		break;
	case '{':
		return handlers[state->parser_state][gotpl_current_char_open_bracket](
				state);
		break;
	case '}':
		return handlers[state->parser_state][gotpl_current_char_close_bracket](
				state);
		break;
	case '>':
		return handlers[state->parser_state][gotpl_current_char_lt](state);
		break;
	case '\\':
		return handlers[state->parser_state][gotpl_current_char_escape](state);
		break;
	default:
		if (gotpl_parser_is_cwhitespace(state->current_value)) {
			return gotpl_parser_handle_white_space(state);

		} else {
			return gotpl_parser_handle_plain_text(state);
		}
		break;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_lt(gotpl_state* state) {

	switch (state->parser_state) {
	case gotpl_state_open_tag_start:
		return gotpl_true;
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
		state->parser_state = gotpl_state_open_tag_start;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_gt(gotpl_state* state) {
	gotpl_ui tmp_val_size;
	gotpl_ci tmp_val;

	switch (state->parser_state) {
	case gotpl_state_open_tag_start:
		state->parser_state = gotpl_state_plain_text;
		//Save current values.
		tmp_val_size = state->current_value_size;
		tmp_val = state->current_value;

		//Must add also the lt missed.
		state->current_value.m8[0] = '<';
		state->current_value_size = 1;

		if (!gotpl_parser_handle_plain_text(state)) {
			GOTPL_ERROR("Failed to add char to the buffer.");
			return gotpl_false;
		}

		state->current_value = tmp_val;
		state->current_value_size = tmp_val_size;

		return gotpl_parser_handle_plain_text(state);

		break;
	case gotpl_state_in_end_tag:
		return gotpl_parser_handle_tag_name_text(state);
		break;
	case gotpl_state_in_tag:
		state->parser_state = gotpl_state_plain_text;
		gotpl_tag* current_tag = gotpl_parser_get_tag(state);

		if (!current_tag) {
			GOTPL_ERROR("Tag not found. If this was not meant to be a tag escape one of < or #.");
			return gotpl_false;
		}

		gotpl_tag* parent_tag = (gotpl_tag*) gotpl_stack_peek(
				state->parser_tag_stack);
		if (parent_tag != 0) {
			gotpl_tag_list_add(parent_tag->children, current_tag);

		} else {
			gotpl_tag_list_add(state->ret_list, current_tag);
		}

		gotpl_parser_pack_text_buffer(state);
		gotpl_stack_push(state->parser_tag_stack, (gotpl_i8*) current_tag);
		gotpl_parser_reset_args(state);

		return gotpl_true;

		break;
	case gotpl_state_in_tag_params:
		current_tag = (gotpl_tag*) gotpl_stack_peek(state->parser_tag_stack);
		current_tag->init(current_tag, state->args_buffer, state->args_index);
		state->parser_state = gotpl_state_plain_text;

		return gotpl_true;
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
		return gotpl_parser_handle_plain_text(state);
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_diez(gotpl_state* state) {

	switch (state->parser_state) {
	case gotpl_state_open_tag_start:
		state->parser_state = gotpl_state_in_tag;
		return gotpl_true;
		break;
	case gotpl_state_close_tag_start:
		state->parser_state = gotpl_state_in_end_tag;
		return gotpl_true;
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
		state->parser_state = gotpl_state_open_tag_start;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_dollar(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_slash(gotpl_state* state) {

	gotpl_ui tmp_val_size;
	gotpl_ci tmp_val;

	switch (state->parser_state) {
	case gotpl_state_open_tag_start:
		state->parser_state = gotpl_state_close_tag_start;
		return gotpl_true;
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
		state->parser_state = gotpl_state_open_tag_start;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_open_accolade(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_close_accolade(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_plain_text(gotpl_state* state) {
	gotpl_ui i, tmp_val_size;
	gotpl_ci tmp_val;

	switch (state->parser_state) {
	case gotpl_state_open_tag_start:
		state->parser_state = gotpl_state_plain_text;
		//Save current values.
		tmp_val_size = state->current_value_size;
		tmp_val = state->current_value;

		//Must add also the lt missed.
		state->current_value.m8[0] = '<';
		state->current_value_size = 1;

		if (!gotpl_parser_handle_plain_text(state)) {
			GOTPL_ERROR("Failed to add char to the buffer.");
			return gotpl_false;
		}

		state->current_value = tmp_val;
		state->current_value_size = tmp_val_size;

		return gotpl_parser_handle_plain_text(state);
		break;
	case gotpl_state_close_tag_start:

		state->parser_state = gotpl_state_plain_text;
		//Save current values.
		tmp_val_size = state->current_value_size;
		tmp_val = state->current_value;

		//Must add also the lt missed.
		state->current_value.m8[0] = '<';
		state->current_value_size = 1;

		if (!gotpl_parser_handle_plain_text(state)) {
			GOTPL_ERROR("Failed to add char to the buffer.");
			return gotpl_false;
		}

		//Must add also the lt missed.
		state->current_value.m8[0] = '/';
		state->current_value_size = 1;

		if (!gotpl_parser_handle_plain_text(state)) {
			GOTPL_ERROR("Failed to add char to the buffer.");
			return gotpl_false;
		}

		state->current_value = tmp_val;
		state->current_value_size = tmp_val_size;

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

		if ((state->text_index + state->current_value_size)
				< gotpl_default_parser_buffer_size) {

			for (i = 0; i < state->current_value_size; i++) {
				state->text_buffer[state->text_index + i]
						= state->current_value.m8[i];
			}

			state->text_index += state->current_value_size;
		}

		else {
			gotpl_parser_pack_text_buffer(state);
		}
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_tag_name_text(gotpl_state* state) {
	gotpl_ui i;

	if ((state->args_index + state->current_value_size)
			< (gotpl_parser_args_buffer - 1)) {

		for (i = 0; i < state->current_value_size; i++) {
			state->args_buffer[state->args_index + i]
					= state->current_value.m8[i];
		}

		state->args_index += state->current_value_size;
	}

	else {
		GOTPL_ERROR("Tag arguments buffer exceeded. When you compile the library please supply a greater value.");
		return gotpl_false;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_tag_params_text(gotpl_state* state) {

	gotpl_ui i;

	if ((state->args_index + state->current_value_size)
			< (gotpl_parser_args_buffer - 1)) {

		for (i = 0; i < state->current_value_size; i++) {
			state->args_buffer[state->args_index + i]
					= state->current_value.m8[i];
		}

		state->args_index += state->current_value_size;
	}

	else {
		GOTPL_ERROR("Tag arguments buffer exceeded. When you compile the library please supply a greater value.");
		return gotpl_false;
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_expr_text(gotpl_state* state) {

	return gotpl_true;
}

static gotpl_bool gotpl_parser_handle_white_space(gotpl_state* state) {
	switch (state->parser_state) {
	case gotpl_state_open_tag_start:
		return gotpl_parser_handle_plain_text(state);
		break;
	case gotpl_state_in_end_tag:
		GOTPL_ERROR("Unexpected white space in end tag.")
		;
		return gotpl_false;
		break;
	case gotpl_state_in_tag:
		state->parser_state = gotpl_state_in_tag_params;
		gotpl_tag* current_tag = gotpl_parser_get_tag(state);

		if (!current_tag) {
			GOTPL_ERROR("Tag not found. If this was not meant to be a tag escape one of < or #.");
			return gotpl_false;
		}

		gotpl_tag* parent_tag = (gotpl_tag*) gotpl_stack_peek(
				state->parser_tag_stack);
		if (parent_tag != 0) {
			gotpl_tag_list_add(parent_tag->children, current_tag);

		} else {
			gotpl_tag_list_add(state->ret_list, current_tag);
		}

		gotpl_parser_pack_text_buffer(state);
		gotpl_stack_push(state->parser_tag_stack, (gotpl_i8*) current_tag);
		gotpl_parser_reset_args(state);

		return gotpl_true;

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
		return gotpl_parser_handle_plain_text(state);
	}

	return gotpl_true;
}

static gotpl_bool gotpl_parser_pack_text_buffer(gotpl_state* state) {

	gotpl_tag* current_tag = gotpl_tag_create_plaintext(state->text_buffer,
			state->text_index, state->pool);

	gotpl_tag* parent_tag = (gotpl_tag*) gotpl_stack_peek(
			state->parser_tag_stack);
	if (parent_tag != 0) {
		gotpl_tag_list_add(parent_tag->children, current_tag);

	} else {
		gotpl_tag_list_add(state->ret_list, current_tag);
	}

	gotpl_parser_reset_text(state);
	return gotpl_true;
}

static gotpl_tag* gotpl_parser_get_tag(gotpl_state* state) {
	state->args_buffer[state->args_index + 1] = '\0';
	gotpl_tag* ret = gotpl_tag_map_get(state->custom_tags, state->args_buffer);

	if (!ret) {
		ret = gotpl_tag_map_get(state->available_tags, state->args_buffer);
	}

	if (ret) {
		ret->children = gotpl_tag_list_create(state->pool);
	}

	return ret;
}
