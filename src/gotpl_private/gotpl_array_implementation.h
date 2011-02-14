#include "gotpl.h"

#ifndef __GOTPL_ARRAY_DEFINES__H
#define __GOTPL_ARRAY_DEFINES__H

#define IMPLEMENT_ARRAY(name ,type) \
gotpl_##name##_array* gotpl_##name##_array_create(gotpl_i initial_size,gotpl_pool* pool) \
{ \
	\
};\
gotpl_i gotpl_##name##_array_add(gotpl_##name##_array* owner,type obj); \
gotpl_i gotpl_##name##_array_remove(gotpl_##name##_array* owner,gotpl_ui index); \
type gotpl_##name##_array_get(gotpl_##name##_array* owner,gotpl_ui index); \
gotpl_i gotpl_##name##_array_insert(gotpl_##name##_array* owner,gotpl_ui index ,type obj); \
gotpl_ui gotpl_##name##_array_length(gotpl_##name##_array* owner);

#endif
