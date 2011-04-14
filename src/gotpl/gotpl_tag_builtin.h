#include "gotpl.h"
#include "gotpl_tag.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_TAG_BUILTIN__H
#define __GOTPL_TAG_BUILTIN__H


//Plain text
#define PLAIN_TEXT_NAME "$$text$$"
gotpl_tag* gotpl_tag_create_plaintext(gotpl_i8* str, gotpl_ui length,
		gotpl_pool* pool);

//Expression tag
#define EXPRESSION_NAME "$$expression$$"
gotpl_tag* gotpl_tag_create_expression(gotpl_i8* expr, gotpl_ui length,
		gotpl_pool* pool);

#endif
