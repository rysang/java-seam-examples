#include "gotpl.h"
#include "gotpl_pool.h"
#include "gotpl_io.h"

#ifndef __GOTPL_TEMPLATE_H
#define __GOTPL_TEMPLATE_H

gotpl_template* gotpl_template_create(gotpl_pool* pool, gotpl_tag* addons,
		gotpl_ui addons_count);
gotpl_bool gotpl_template_execute(gotpl_template* template,
		gotpl_output_stream* out);
gotpl_void gotpl_template_destroy(gotpl_template* template);

#endif

