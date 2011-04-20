#include "../gotpl/gotpl.h"
#include "../gotpl/gotpl_tag_builtin.h"
#include "../gotpl/gotpl_object_list.h"
#include "../gotpl/gotpl_util.h"
#include "../gotpl/gotpl_parsers.h"

typedef struct {
	gotpl_object_array* indirection_array;
} expr_priv;

#define gotpl_found_success 1
#define gotpl_found_fail 0
#define gotpl_found_error -1

static gotpl_bool expr_is_cwhitespace(gotpl_i8 ch) {

	switch (ch) {

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

static gotpl_bool expr_is_char_allowed(gotpl_i8 ch) {
	if (ch >= 'A' && ch <= 'Z') {
		return gotpl_true;

	} else if (ch >= 'a' && ch <= 'z') {
		return gotpl_true;

	} else if (ch == '_' || ch == '.' || expr_is_cwhitespace(ch)) {
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
		gotpl_ui length, gotpl_ui* index, gotpl_pool* pool) {

	gotpl_ui i, l_index = 0;
	gotpl_i8 buff[gotpl_default_indirection_size];

	for (i = *index; i < length; i++) {
		if (l_index > gotpl_default_indirection_size) {

			GOTPL_ERROR_I4("Maximum indentifier length is in bytes : ",
					gotpl_default_indirection_size);
			return gotpl_found_error;

		} else if (expr[i] == '.') {
			*index = i + 1;
			*str = *(gotpl_create_string_withlen(buff, l_index, pool));
			return gotpl_found_success;

		} else if (expr_is_cwhitespace(expr[i])) {

			//ignore and decrease
			l_index--;

		} else {

			buff[l_index] = expr[i];
		}

		l_index++;
	}

	*index = i + 1;
	*str = *(gotpl_create_string_withlen(buff, l_index, pool));

	return gotpl_found_success;
}

gotpl_tag* gotpl_tag_create_expression(gotpl_i8* expr, gotpl_ui length,
		gotpl_pool* pool) {

	gotpl_ui i;
	gotpl_ui indirection_level = 1;
	gotpl_ui indirection_index = 0;

	for (i = 0; i < length; i++) {
		if (!expr_is_char_allowed(expr[i])) {
			GOTPL_ERROR("Expression contains not allowed characters. the allowed ones are [0-9][A-Z][a-z]_.[WHITE_SPACE]");
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

	gotpl_object str;
	for (i = 0; i < indirection_level; i++) {
		if (expr_find_next_indirection(&str, expr, length, &indirection_index,
				pool) == gotpl_found_error) {
			GOTPL_ERROR("An error occured in expression.");
			return 0;
		} else {

			gotpl_object_array_add(priv->indirection_array, &str);
		}
	}

	return expr_tag;
}
