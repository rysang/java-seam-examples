#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_OBJECT_MAP__H
#define __GOTPL_OBJECT_MAP__H

gotpl_object_map
		* gotpl_object_map_create(gotpl_ui array_size, gotpl_pool* pool);
gotpl_ui gotpl_object_map_put(gotpl_object_map* owner, gotpl_i8* name,
		gotpl_object* obj);
gotpl_i gotpl_object_map_remove(gotpl_object_map* owner, gotpl_i8* name);
gotpl_object* gotpl_object_map_get(gotpl_object_map* owner, gotpl_i8* name);
gotpl_ui gotpl_object_map_element_count(gotpl_object_map* owner);

#endif
