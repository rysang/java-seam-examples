#include "gotpl/gotpl.h"
#include "gotpl/gotpl_parsers.h"
#include "gotpl/gotpl_stack.h"
#include "gotpl/gotpl_tag_map.h"
#include "gotpl/gotpl_tag_builtin.h"
#include "gotpl/gotpl_tag_list.h"

struct gotpl_parser {
	gotpl_pool* pool;
	gotpl_stack* parser_tag_stack;
	gotpl_tag_map* available_tags_map;
};

typedef struct {
	gotpl_bool in_start_tag_begin;
	gotpl_bool in_end_tag_begin;
	gotpl_bool in_tag;
	gotpl_bool in_start_tag_end;
	gotpl_bool in_end_tag_end;
	gotpl_bool in_tag_arg_phase;
	gotpl_bool in_expr_begin;
	gotpl_bool in_expr;
	gotpl_bool in_expr_end;

	//current value
	gotpl_ui current_value_size;
	gotpl_ci current_value;
	gotpl_i8 text_buffer[gotpl_default_parser_buffer_size];
	gotpl_ui index;

	//custom tags
	gotpl_tag_map* tags;

	//Default tags
	gotpl_tag_map* available_tags;

} gotpl_state;

gotpl_bool gotpl_parser_handle_lt(gotpl_state* state);
gotpl_bool gotpl_parser_handle_gt(gotpl_state* state);
gotpl_bool gotpl_parser_handle_diez(gotpl_state* state);
gotpl_bool gotpl_parser_handle_dollar(gotpl_state* state);
gotpl_bool gotpl_parser_handle_slash(gotpl_state* state);
gotpl_bool gotpl_parser_handle_open_accolade(gotpl_state* state);
gotpl_bool gotpl_parser_handle_close_accolade(gotpl_state* state);
gotpl_bool gotpl_parser_handle_plain_text(gotpl_state* state);

gotpl_bool gotpl_parser_handle_next_char(gotpl_state* state);

gotpl_bool gotpl_parser_is_cwhitespace(gotpl_ci utf8Char) {

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

		parser->available_tags_map = gotpl_tag_map_create(
				gotpl_default_map_size, pool);
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

	gotpl_tag_list* ret_list = (gotpl_tag_list*) gotpl_tag_list_create(
			parser->pool);

	//State machine
	gotpl_state* state = (gotpl_state*) gotpl_pool_alloc(parser->pool,
			sizeof(gotpl_state));
	memset(state, '\0', sizeof(gotpl_state));
	state->tags = tags;
	state->available_tags = parser->available_tags_map;

	while (in->has_more(in)) {
		state->current_value_size = in->read(in);
		state->current_value = in->current_char;

		gotpl_parser_handle_next_char(state);
	}

	return ret_list;
}

gotpl_bool gotpl_parser_handle_next_char(gotpl_state* state) {

	switch (state->current_value.m8[0]) {
	case '<':
		break;
	case '#':
		break;
	case '/':
		break;
	case '$':
		break;
	case '{':
		break;
	case '}':
		break;
	case '>':
		break;
	default:
		if (gotpl_parser_is_cwhitespace(in->current_char)) {

		}
		break;
	}

	return gotpl_true;
}
