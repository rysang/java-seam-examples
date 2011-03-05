#include "gotpl.h"
#include "gotpl_pool.h"
#include "gotpl_io.h"

#ifndef __GOTPL_PARSERS__H
#define __GOTPL_PARSERS__H

gotpl_bool gotpl_parser_is_cwhitespace(gotpl_ci utf8Char);
gotpl_bool gotpl_parser_tag_begins(gotpl_ci utf8Char);
gotpl_bool gotpl_parser_expression_begins(gotpl_ci utf8Char);

gotpl_parser* gotpl_utf8parser_create(gotpl_pool* pool);
gotpl_tag_list* gotpl_utf8parser_parse(gotpl_parser* parser, gotpl_input_stream* in);

#endif
