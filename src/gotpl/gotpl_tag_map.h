#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_TAG_MAP__H
#define __GOTPL_TAG_MAP__H

gotpl_tag_map
		* gotpl_tag_map_create(gotpl_ui array_size, gotpl_pool* pool);
gotpl_ui gotpl_tag_map_put(gotpl_tag_map* owner, gotpl_i8* name,
		gotpl_tag* obj);
gotpl_i gotpl_tag_map_remove(gotpl_tag_map* owner, gotpl_i8* name);
gotpl_tag* gotpl_tag_map_get(gotpl_tag_map* owner, gotpl_i8* name);
gotpl_ui gotpl_tag_map_element_count(gotpl_tag_map* owner);

#endif
