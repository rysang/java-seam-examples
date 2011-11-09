#include "gotpl/gotpl_object_map.h"
#include "3rd_party/super_fast_hash.h"
#include <stdio.h>
#include <memory.h>

typedef struct gotpl_map_chunk gotpl_map_chunk;

struct gotpl_map_chunk {
	gotpl_object *object;
	gotpl_map_chunk* next;
	gotpl_ui hash;
};

struct gotpl_object_map {
	gotpl_ui element_count;
	gotpl_map_chunk* array;
	gotpl_ui array_length;
	gotpl_pool* pool;
};

gotpl_object_map* gotpl_object_map_create(gotpl_ui array_size, gotpl_pool* pool) {
	gotpl_object_map* map = (gotpl_object_map*) gotpl_pool_alloc(pool,
			sizeof(gotpl_object_map));
	if (map == 0) {
		GOTPL_ERROR("Failed to alloc map.");
		return 0;
	}
	map->pool = pool;
	map->element_count = 0;
	map->array_length = array_size;
	map->array = (gotpl_map_chunk*) gotpl_pool_alloc(pool,
			sizeof(gotpl_map_chunk) * array_size);
	if (map->array) {
		GOTPL_DEBUG("Returning map.");
		memset(map->array, '\0', sizeof(gotpl_map_chunk) * array_size);
		return map;
	}

	GOTPL_ERROR("Failed to alloc map.");
	return 0;
}

gotpl_ui gotpl_object_map_put(gotpl_object_map* owner, gotpl_i8* name,
		gotpl_object* obj) {

	gotpl_ui hash = super_fast_hash(name, strlen(name));
	gotpl_map_chunk* chunk = &owner->array[hash % owner->array_length];
	owner->element_count++;

	if (chunk->hash == 0) {
		GOTPL_DEBUG("Allocating first chunk in the array.");

		chunk->hash = hash;
		chunk->object = obj;
		return hash;

	} else if (chunk->hash == hash) {
		GOTPL_DEBUG("Entry exists, replacing it.");

		chunk->object = obj;
		return hash;
	}

	while (gotpl_true) {
		chunk = chunk->next;

		if (chunk == 0) {
			GOTPL_DEBUG("Allocating new chunk.");

			chunk = (gotpl_map_chunk *) gotpl_pool_alloc(owner->pool,
					sizeof(gotpl_map_chunk));

			if (chunk) {

				chunk->hash = hash;
				chunk->object = obj;
				return hash;

			}

			GOTPL_ERROR("Failed to allocate chunk.");
			return 0;

		} else if ((chunk->hash == 0) || (chunk->hash == hash)) {

			GOTPL_DEBUG("Entry exists or empty space found recycling.");

			chunk->hash = hash;
			chunk->object = obj;
			return hash;
		}
	}

	return hash;
}

gotpl_i gotpl_object_map_remove(gotpl_object_map* owner, gotpl_i8* name) {
	gotpl_ui hash = super_fast_hash(name, strlen(name));
	gotpl_map_chunk* chunk = &owner->array[hash % owner->array_length];

	if (chunk->hash == 0) {
		GOTPL_DEBUG("Key not found.");
		return -1;
	}

	while (gotpl_true) {

		if (chunk != 0) {

			if (chunk->hash == hash) {
				GOTPL_DEBUG("Removed object.");

				owner->element_count--;
				chunk->hash = 0;
				return 0;
			}

		} else {
			GOTPL_DEBUG("Key not found.");
			return -1;
		}

		chunk = chunk->next;
	}

	GOTPL_DEBUG("Key not found.");
	return -1;
}

gotpl_object* gotpl_object_map_get_ref(gotpl_object_map* owner, gotpl_i8* name) {
	gotpl_ui hash = super_fast_hash(name, strlen(name));
	gotpl_map_chunk* chunk = &owner->array[hash % owner->array_length];

	while (gotpl_true) {

		if (chunk != 0) {
			if (chunk->hash != hash) {
				GOTPL_DEBUG("Returning value.");
				return chunk->object;
			}
		} else {
			GOTPL_DEBUG("Value not found.");
			return 0;
		}

		chunk = chunk->next;
	}

	return 0;
}

gotpl_object* gotpl_object_map_get_copy(gotpl_object_map* owner, gotpl_i8* name) {
	gotpl_ui hash = super_fast_hash(name, strlen(name));
	gotpl_map_chunk* chunk = &owner->array[hash % owner->array_length];

	while (gotpl_true) {

		if (chunk != 0) {
			if (chunk->hash != hash) {
				gotpl_object* ret = (gotpl_object*) gotpl_pool_alloc(
						owner->pool, sizeof(gotpl_object));
				memcpy(ret, chunk->object, sizeof(gotpl_object));
				GOTPL_DEBUG("Returning value.");
				return ret;
			}
		} else {
			GOTPL_DEBUG("Value not found.");
			return 0;
		}

		chunk = chunk->next;
	}

	return 0;
}

gotpl_ui gotpl_object_map_element_count(gotpl_object_map* owner) {
	return owner->element_count;
}
