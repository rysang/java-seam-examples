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

gotpl_bool gotpl_parser_tag_begins(gotpl_ci utf8Char) {
	if (utf8Char.m8[0] == '<') {
		return gotpl_true;
	}

	return gotpl_false;
}
gotpl_bool gotpl_parser_expression_begins(gotpl_ci utf8Char) {
	if (utf8Char.m8[0] == '$') {
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

	gotpl_i8* tmp_buffer = (gotpl_i8*) gotpl_pool_alloc(parser->pool,
			gotpl_default_parser_buffer_size);

	gotpl_ui count = 0;
	gotpl_i ch_byte_count = 0;

	//State machine
	gotpl_bool in_tag_begin = gotpl_false;
	gotpl_bool in_tag = gotpl_false;
	gotpl_bool in_tag_arg_phase = gotpl_false;
	gotpl_bool in_expr_begin = gotpl_false;
	gotpl_bool in_expr = gotpl_false;

	while (in->has_more(in)) {
		ch_byte_count = in->read(in);

		if (in_tag_begin) {

		} else if (in_tag) {

		} else if (in_tag_arg_phase) {

		} else if (in_expr_begin) {

		} else if (in_expr) {

		} else {

		}
	}

	return ret_list;
}
