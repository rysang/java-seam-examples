#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_PARSERS__H
#define __GOTPL_PARSERS__H

typedef struct gotpl_parser gotpl_parser;

gotpl_bool gotpl_parser_is_cwhitespace(gotpl_ci utf8Char);

gotpl_parser* gotpl_utf8parser_create(gotpl_pool* pool);

#endif
