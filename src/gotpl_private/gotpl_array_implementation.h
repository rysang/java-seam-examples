#include "gotpl.h"

#ifndef __GOTPL_ARRAY_DEFINES__H
#define __GOTPL_ARRAY_DEFINES__H

#define IMPLEMENT_ARRAY(name ,type)\
\
struct gotpl_##type##_array {\
	gotpl_ui chunk_size;\
	gotpl_ui count;\
	gotpl_ui size;\
	type* objects;\
	gotpl_pool* pool;\
};\
\
static type* gotpl_array_create_chunk(gotpl_ui element_count,\
		gotpl_pool* pool) {\
	type* array = gotpl_pool_alloc(pool, sizeof(type)\
			* element_count);\
\
	if (!array) {\
		GOTPL_ERROR("Error creating array chunk.");\
		return 0;\
	}\
\
	GOTPL_DEBUG("Allocated chunk.");\
	return array;\
}\
\
gotpl_##type##_array* gotpl_##type##_array_create(gotpl_i initial_size,\
		gotpl_pool* pool) {\
	gotpl_##type##_array* array = gotpl_pool_alloc(pool,\
			sizeof(gotpl_##type##_array));\
\
	if (!array) {\
		GOTPL_ERROR("Error creating array.");\
		return 0;\
	}\
\
	array->objects = gotpl_array_create_chunk(initial_size, pool);\
	array->chunk_size = initial_size;\
	array->pool = pool;\
\
	if (array->objects) {\
		GOTPL_DEBUG("Allocated array.");\
\
		array->count = 0;\
		array->size = initial_size;\
		return array;\
	}\
\
	GOTPL_ERROR("Array failure.");\
	return 0;\
}\
\
gotpl_i gotpl_##type##_array_add(gotpl_##type##_array* owner, type* obj) {\
	gotpl_i index = -1;\
\
	if ((owner->size - owner->count) > 0) {\
		GOTPL_DEBUG("Array big enough to add one more element.");\
\
		index = owner->count;\
		owner->count++;\
		owner->objects[index] = *obj;\
		return index;\
	}\
	GOTPL_DEBUG("Array not big enough to add one more element. "\
			"Doing the hard work and wasting memory to make room.");\
\
	type* new_objects = gotpl_pool_alloc(owner->pool, owner->size\
			+ owner->chunk_size);\
	if (new_objects) {\
		GOTPL_DEBUG("Copying objects from old array to new one.");\
\
		memcpy(new_objects, owner->objects, sizeof(type) * owner->size);\
		owner->size += owner->chunk_size;\
\
		GOTPL_DEBUG("Switching new array with old one. Memory is wasted. "\
				"Can be recovered by clearing the pool.");\
\
		owner->objects = new_objects;\
\
		return gotpl_##type##_array_add(owner, obj);\
	}\
\
	GOTPL_ERROR("Failed to allocate new chunk for the array.");\
	return index;\
}\
\
gotpl_i gotpl_##type##_array_remove(gotpl_##type##_array* owner, gotpl_ui index) {\
	if (index > owner->count) {\
		GOTPL_ERROR("Index out of bounds.");\
		return -1;\
	}\
\
	/*TODO: Really test this one for bugs.*/\
	gotpl_ui i;\
	gotpl_ui rest = owner->count - index;\
\
	for (i = index; i < rest; i++) {\
		GOTPL_DEBUG("Reindexing array.");\
		owner->objects[i - 1] = owner->objects[i];\
	}\
\
	return (owner->count--);\
}\
\
type* gotpl_##type##_array_get(gotpl_##type##_array* owner, gotpl_ui index) {\
	if (index > owner->count) {\
		GOTPL_ERROR("Index out of bounds.");\
		return 0;\
	}\
\
	GOTPL_DEBUG("Returning the object at index.");\
	return &owner->objects[index];\
}\
\
gotpl_i gotpl_##type##_array_insert(gotpl_##type##_array* owner, gotpl_ui index,\
		type* obj) {\
	if (index > owner->count) {\
		GOTPL_ERROR("Index out of bounds.");\
		return -1;\
	}\
\
	GOTPL_DEBUG("Inserting the object at index.");\
	owner->objects[index] = *obj;\
\
	return index;\
}\
\
gotpl_ui gotpl_##type##_array_length(gotpl_##type##_array* owner) {\
	return owner->count;\
}\

#endif
