#include "gotpl/gotpl_object_array.h"

struct gotpl_object_array {
	gotpl_ui count;
	gotpl_ui size;
	gotpl_object* objects;
};

static gotpl_object* gotpl_array_create_chunk(gotpl_ui element_count,
		gotpl_pool* pool) {
	gotpl_object* array = gotpl_pool_alloc(pool, sizeof(gotpl_object)
			* element_count);

	if (!array) {
		GOTPL_ERROR("Error creating array chunk.");
		return 0;
	}

	return array;
}

gotpl_object_array* gotpl_object_array_create(gotpl_i initial_size,
		gotpl_pool* pool) {
	gotpl_object_array* array = gotpl_pool_alloc(pool,
			sizeof(gotpl_object_array));

	if (!array) {
		GOTPL_ERROR("Error creating array.");
		return 0;
	}

	array->objects = gotpl_array_create_chunk(initial_size, pool);
	if (array->objects) {
		array->count = 0;
		array->size = initial_size;
		return array;
	}

	GOTPL_ERROR("Array failure.");
	return 0;
}
