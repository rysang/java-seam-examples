#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_OBJECT_ARRAY__H
#define __GOTPL_OBJECT_ARRAY__H

gotpl_object_array* gotpl_object_array_create(gotpl_i initial_size,
		gotpl_pool* pool);
gotpl_i gotpl_object_array_add(gotpl_object_array* owner, gotpl_object* obj);
gotpl_i gotpl_object_array_remove(gotpl_object_array* owner, gotpl_ui index);
gotpl_object* gotpl_object_array_get(gotpl_object_array* owner, gotpl_ui index);
gotpl_i gotpl_object_array_insert(gotpl_object_array* owner, gotpl_ui index,
		gotpl_object* obj);
gotpl_ui gotpl_object_array_length(gotpl_object_array* owner);

#endif
