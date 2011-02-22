#include "gotpl/gotpl_object_map.h"
#include "3rd_party/super_fast_hash.h"
#include <stdio.h>
#include <memory.h>

typedef struct gotpl_map_chunk gotpl_map_chunk;

struct gotpl_map_chunk {
	gotpl_object object;
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
		memset(map->array, '\0', sizeof(gotpl_map_chunk) * array_size);
		return map;
	}

	GOTPL_ERROR("Failed to alloc map.");
	return 0;
}

gotpl_ui gotpl_object_map_put(gotpl_object_map* owner, gotpl_i8* name,
		gotpl_object* obj) {
	gotpl_ui hash = super_fast_hash(name, strlen(name));
	gotpl_map_chunk * chunk = &owner->array[hash % owner->array_length];

	if (chunk->hash == 0) {
		chunk->hash = hash;
		chunk->object = *obj;
		return hash;
	}

	while (gotpl_true) {
		if (!chunk->next) {
			chunk->next = (gotpl_map_chunk*) gotpl_pool_alloc(pool,
					sizeof(gotpl_map_chunk));
			chunk->next->hash = 0;
			chunk->next->next = 0;
		}

		chunk = chunk->next;
		if (chunk->hash == 0 || chunk->hash == hash) {
			chunk->hash = hash;
			chunk->object = *obj;
			return hash;
		}
	}

	return hash;
}
