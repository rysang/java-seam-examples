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
	gotpl_bool in_tag = gotpl_false;
	gotpl_bool in_expr = gotpl_false;
	gotpl_bool in_expr_deep = gotpl_false;

	while (in->has_more(in)) {
		ch_byte_count = in->read(in);

		if (gotpl_parser_tag_begins(in->current_char)) {
			if (in_tag) {
				GOTPL_ERROR("Error < and > are not allowed in the definition of a tag.");
				return 0;
			}

		} else if (gotpl_parser_expression_begins(in->current_char)) {

			if (in_expr) {
				GOTPL_ERROR("Error $ is not allowed in the definition of an expression.");
				return 0;
			}

		} else {

			//check if utf8 sequence + current bytes count greater than max buffer
			if ((count + ch_byte_count) >= gotpl_default_parser_buffer_size) {

				//if so we must create a plain text tag and put it in the array.

				//check first if we have a parent available.
				gotpl_tag* parent = (gotpl_tag*) gotpl_stack_peek(
						parser->parser_tag_stack);
				gotpl_tag* plain_text = gotpl_tag_create_plaintext(tmp_buffer,
						count, parser->pool);

				if (parent) {
					gotpl_tag_list_add(parent->children, plain_text);
				} else {
					gotpl_tag_list_add(ret_list, plain_text);
				}

				count = 0;

			} else {
				memcpy(tmp_buffer + count, &in->current_char, ch_byte_count);
				count += ch_byte_count;
			}
		}
	}

	return ret_list;
}
