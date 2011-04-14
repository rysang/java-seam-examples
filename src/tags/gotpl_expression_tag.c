#include "../gotpl/gotpl.h"
#include "../gotpl/gotpl_tag_builtin.h"
#include "../gotpl/gotpl_object_list.h"

typedef struct {
	gotpl_object_array* indirection_array;
} expr_priv;

#define gotpl_found_success 1
#define gotpl_found_fail 0
#define gotpl_found_error -1

static gotpl_bool expr_is_char_allowed(gotpl_i8 ch) {
	if (ch >= 'A' && ch <= 'Z') {
		return gotpl_true;

	} else if (ch >= 'a' && ch <= 'z') {
		return gotpl_true;

	} else if (ch == '_' || ch == '.' || ch == ' ') {
		return gotpl_true;

	} else if (ch >= '0' && ch <= '9') {
		return gotpl_true;

	}

	return gotpl_false;
}

static gotpl_bool expr_init(gotpl_tag* owner, gotpl_i8* cmd_line) {
	return gotpl_true;
}

static gotpl_bool expr_execute(gotpl_tag* owner, gotpl_tag* parent,
		gotpl_object_map* context, gotpl_output_stream* out) {

	if (strcmp(owner->name, EXPRESSION_NAME)) {
		GOTPL_ERROR("This is not a plain text tag.");
		return gotpl_false;
	}

	return gotpl_true;
}

static gotpl_bool expr_end_execute(gotpl_tag* owner, gotpl_tag* parent,
		gotpl_object_map* context, gotpl_output_stream* out) {

	//Flush could be added here.
	return gotpl_true;
}

static gotpl_bool expr_destroy(gotpl_tag* owner) {
	return gotpl_true;
}

static gotpl_i expr_find_next_indirection(gotpl_object* str, gotpl_i8* expr,
		gotpl_ui length, gotpl_ui* index) {
	gotpl_ui l_index = *index;
	gotpl_ui i;
	gotpl_i8 buff[gotpl_default_indirection_size];
	str->o_type = gotpl_type_string;

	while (l_index < length) {

	}

	return gotpl_false;
}

gotpl_tag* gotpl_tag_create_expression(gotpl_i8* expr, gotpl_ui length,
		gotpl_pool* pool) {

	gotpl_ui i;
	gotpl_ui indirection_level = 0;
	gotpl_ui indirection_index = 0;

	for (i = 0; i < length; i++) {
		if (!expr_is_char_allowed(expr[i])) {
			GOTPL_ERROR("Expression contains not allowed characters. the allowed ones are [0-9][A-Z][a-z]_.");
			return 0;
		}

		if (expr[i] == '.') {
			indirection_level++;
		}
	}

	gotpl_tag* expr_tag =
			(gotpl_tag*) gotpl_pool_alloc(pool, sizeof(gotpl_tag));
	expr_priv* priv = (expr_priv*) gotpl_pool_alloc(pool, sizeof(expr_priv));
	expr_tag->_private = priv;

	priv->indirection_array = gotpl_object_array_create(indirection_level + 1,
			pool);
}
