#include "gotpl/gotpl_object_array.h"
#include <stdio.h>
#include <memory.h>

struct gotpl_object_array {
	gotpl_ui chunk_size;
	gotpl_ui count;
	gotpl_ui size;
	gotpl_object* objects;
	gotpl_pool* pool;
};

static gotpl_object* gotpl_array_create_chunk(gotpl_ui element_count,
		gotpl_pool* pool) {
	gotpl_object* array = gotpl_pool_alloc(pool, sizeof(gotpl_object)
			* element_count);

	if (!array) {
		GOTPL_ERROR("Error creating array chunk.");
		return 0;
	}

	GOTPL_DEBUG("Allocated chunk.");
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
	array->chunk_size = initial_size;
	array->pool = pool;

	if (array->objects) {
		GOTPL_DEBUG("Allocated array.");

		array->count = 0;
		array->size = initial_size;
		return array;
	}

	GOTPL_ERROR("Array failure.");
	return 0;
}

gotpl_i gotpl_object_array_add(gotpl_object_array* owner, gotpl_object* obj) {
	gotpl_i index = -1;

	if ((owner->size - owner->count) > 0) {
		GOTPL_DEBUG("Array big enough to add one more element.");

		index = owner->count;
		owner->count++;
		owner->objects[index] = *obj;
		return index;
	}
	GOTPL_DEBUG("Array not big enough to add one more element. "
			"Doing the hard work and wasting memory to make room.");

	gotpl_object* new_objects = gotpl_pool_alloc(owner->pool, owner->size
			+ owner->chunk_size);
	if (new_objects) {
		GOTPL_DEBUG("Copying objects from old array to new one.");

		memcpy(new_objects, owner->objects, sizeof(gotpl_object) * owner->size);
		owner->size += owner->chunk_size;

		GOTPL_DEBUG("Switching new array with old one. Memory is wasted. "
				"Can be recovered by clearing the pool.");

		owner->objects = new_objects;

		return gotpl_object_array_add(owner, obj);
	}

	GOTPL_ERROR("Failed to allocate new chunk for the array.");
	return index;
}

gotpl_i gotpl_object_array_remove(gotpl_object_array* owner, gotpl_ui index) {
	if (index > owner->count) {
		GOTPL_ERROR("Index out of bounds.");
		return -1;
	}

	//TODO: Really test this one for bugs.
	gotpl_ui i;
	gotpl_ui rest = owner->count - index;

	for (i = index; i < rest; i++) {
		GOTPL_DEBUG("Reindexing array.");
		owner->objects[i - 1] = owner->objects[i];
	}

	return (owner->count--);
}

gotpl_object* gotpl_object_array_get(gotpl_object_array* owner, gotpl_ui index) {
	if (index > owner->count) {
		GOTPL_ERROR("Index out of bounds.");
		return 0;
	}

	GOTPL_DEBUG("Returning the object at index.");
	return &owner->objects[index];
}

gotpl_i gotpl_object_array_insert(gotpl_object_array* owner, gotpl_ui index,
		gotpl_object* obj) {
	if (index > owner->count) {
		GOTPL_ERROR("Index out of bounds.");
		return -1;
	}

	GOTPL_DEBUG("Inserting the object at index.");
	owner->objects[index] = *obj;

	return index;
}

gotpl_ui gotpl_object_array_length(gotpl_object_array* owner) {
	return owner->count;
}
