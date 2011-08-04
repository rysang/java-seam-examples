#include "gotpl/gotpl_io.h"
#include "gotpl/gotpl_pool.h"
#include "gotpl/gotpl_object_array.h"
#include "gotpl/gotpl_object_list.h"
#include "3rd_party/super_fast_hash.h"
#include "gotpl/gotpl_object_map.h"
#include "gotpl/gotpl_util.h"
#include "gotpl/gotpl_parsers.h"
#include "gotpl/gotpl_tag.h"
#include "gotpl/gotpl_tag_list.h"
#include "gotpl/gotpl_stack.h"
#include <stdio.h>
#include <string.h>

gotpl_pool* pool = 0;

static gotpl_bool testParser() {

	gotpl_input_stream is;
	if (!gotpl_create_std_input_stream(&is, "test.txt", gotpl_enc_utf8)) {
		GOTPL_ERROR("Failed to create stream.");
		return gotpl_false;
	}

	gotpl_tag_map* dummy_map = gotpl_tag_map_create(2, pool);
	gotpl_parser* parser = gotpl_utf8parser_create(pool);

	if (parser && dummy_map) {
		GOTPL_DEBUG("Created parser and empty map.");

		gotpl_tag_list* list = gotpl_utf8parser_parse(parser, &is, dummy_map);
		GOTPL_DEBUG_I4("List length: ",gotpl_tag_list_length(list));

		gotpl_output_stream os;
		if (!gotpl_create_std_output_stream(&os, "output.txt")) {
			GOTPL_ERROR("Failed to create stream.");
			return gotpl_false;
		}

		gotpl_tag_list_iterator* iterator =
				gotpl_tag_list_iterator_create(list);
		while (gotpl_tag_list_iterator_has_more(iterator)) {
			gotpl_tag* tag = gotpl_tag_list_iterator_next(iterator);
			tag->execute(tag, 0, 0, &os);
		}

		os.close(&os);
	}

	return gotpl_false;
}

static gotpl_void gotpl_test_expressions() {
	gotpl_i8* str = "mine.price.sasas. sasa";
	gotpl_tag* expr_tag = gotpl_tag_create_expression(str, strlen(str), pool);

	GOTPL_DEBUG(expr_tag->name)
	;
}

int main() {

	if (!gotpl_pool_create(&pool, 1024 * 1024 * 5)) {
		GOTPL_ERROR("Failed to alloc pool.");
		return 0;
	}

	gotpl_input_stream is;
	gotpl_create_std_input_stream(&is, "template.txt", gotpl_enc_utf8);

	gotpl_parser* parser = gotpl_utf8parser_create(pool);
	gotpl_utf8parser_parse(parser, &is, gotpl_tag_map_create(10, pool));

	is.close(&is);
	gotpl_pool_destroy(&pool);

	return 0;
}
