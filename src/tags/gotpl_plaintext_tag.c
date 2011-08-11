#include "../gotpl/gotpl_tag_builtin.h"
#include "../gotpl/gotpl_io.h"
#include "../gotpl/gotpl_tag_list.h"
#include "../gotpl/gotpl_object_map.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
	gotpl_i8* str;
	gotpl_ui length;
} tag_text_private;

static gotpl_bool plaintext_init(gotpl_tag* owner, gotpl_i8* cmd_line,
		gotpl_ui cmd_line_len) {
	return gotpl_true;
}

static gotpl_bool plaintext_execute(gotpl_tag* owner, gotpl_tag* parent,
		gotpl_object_map* context, gotpl_output_stream* out) {

	if (strcmp(owner->name, PLAIN_TEXT_NAME)) {
		GOTPL_ERROR("This is not a plain text tag.");
		return gotpl_false;
	}

	tag_text_private* priv = (tag_text_private*) owner->_private;
	out->write(out, priv->str, priv->length);

	return gotpl_true;
}

static gotpl_bool plaintext_end_execute(gotpl_tag* owner, gotpl_tag* parent,
		gotpl_object_map* context, gotpl_output_stream* out) {

	//Flush could be added here.
	return gotpl_true;
}

static gotpl_bool plaintext_destroy(gotpl_tag* owner) {
	return gotpl_true;
}

gotpl_tag* gotpl_tag_create_plaintext(gotpl_i8* str, gotpl_ui length,
		gotpl_pool* pool) {

	gotpl_tag* text_tag =
			(gotpl_tag*) gotpl_pool_alloc(pool, sizeof(gotpl_tag));
	tag_text_private* text_tag_priv = (tag_text_private*) gotpl_pool_alloc(
			pool, sizeof(tag_text_private));
	text_tag_priv->str = gotpl_pool_alloc(pool, length);

	text_tag_priv->length = length;
	memcpy(text_tag_priv->str, str, length);

	//assign tag
	text_tag->name = PLAIN_TEXT_NAME;
	text_tag->_private = text_tag_priv;
	text_tag->init = plaintext_init;
	text_tag->children = gotpl_tag_list_create(pool);
	text_tag->execute = plaintext_execute;
	text_tag->end_execute = plaintext_end_execute;
	text_tag->destroy = plaintext_destroy;

	return text_tag;
}

