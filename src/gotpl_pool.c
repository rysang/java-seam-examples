#include "gotpl/gotpl_pool.h"
#include <stdio.h>
#include <stdlib.h>

typedef struct gotpl_pool_unit gotpl_pool_unit;

struct gotpl_pool {
	gotpl_ui chunk_size;
	gotpl_pool_unit* first;
	gotpl_pool_unit* last;
	gotpl_pool_unit* current;
};

struct gotpl_pool_unit {
	gotpl_ui size;
	gotpl_ui allocated;
	gotpl_ui8* address;
	gotpl_pool_unit* next;
	gotpl_pool_unit* prev;
};

static gotpl_pool_unit* gopl_pool_create_unit(gotpl_ui chunk_size) {
	gotpl_pool_unit* unit = malloc(sizeof(gotpl_pool_unit) + chunk_size);
	if (unit) {
		GOTPL_DEBUG("Allocated chunk");
		unit->size = chunk_size;
		unit->allocated = 0;
		unit->prev = unit->next = 0;
		unit->address = ((gotpl_ui8*) unit) + sizeof(gotpl_pool_unit);
	}

	return unit;
}

static gotpl_void gopl_pool_destroy_unit(gotpl_pool_unit* unit) {
	GOTPL_DEBUG("Freed chunk");
	free((gotpl_void*) unit);
}

gotpl_bool gotpl_pool_create(gotpl_pool** pool, gotpl_ui chunk_size) {
	if (*pool == 0) {
		return gotpl_false;
	}

	*pool = (gotpl_pool *) malloc(sizeof(gotpl_pool));
	if (*pool) {
		(*pool)->chunk_size = chunk_size;
		(*pool)->first = (*pool)->last = (*pool)->current
				= gopl_pool_create_unit(chunk_size);
		if (!(*pool)->first) {
			if (*pool) {
				free(*pool);
				*pool = 0;
			}
			return gotpl_false;
		}

		gotpl_pool_set_chunk_size(*pool, chunk_size);
		GOTPL_DEBUG("Pool allocated.");
		return gotpl_true;
	}

	GOTPL_ERROR("Failed to create pool.");
	return gotpl_false;
}

gotpl_i gotpl_pool_set_chunk_size(gotpl_pool * pool, gotpl_ui chunk_size) {
	GOTPL_DEBUG("Set chunk size.");
	return (pool->chunk_size = chunk_size);
}

gotpl_ui8* gotpl_pool_alloc(gotpl_pool * pool, gotpl_ui size) {
	if (pool->current) {

		if ((pool->current->size - pool->current->allocated) > size) {
			gotpl_ui8* ret = pool->current->address + pool->current->allocated;
			pool->current->allocated += size;
			return ret;
		} else {

			gotpl_pool_unit* currentUnit = pool->first;
			while (currentUnit) {

				if ((currentUnit->size - currentUnit->allocated) > size) {

					gotpl_ui8* ret = currentUnit->address
							+ currentUnit->allocated;
					currentUnit->allocated += size;
					pool->current = currentUnit;
					GOTPL_DEBUG("Chunk allocated from pool.");
					return ret;

				}

				currentUnit = currentUnit->next;
			}

			GOTPL_DEBUG("Available chunk not found. Allocation new one.");
			gotpl_pool_unit* newUnit;
			pool->last->next = newUnit = gopl_pool_create_unit(
					(pool->chunk_size > size) ? pool->chunk_size : size);
			if (!newUnit) {
				GOTPL_ERROR("Not enough memory to create new chunk.");
				return 0;
			}

			newUnit->size = (pool->chunk_size > size) ? pool->chunk_size : size;
			newUnit->allocated += size;
			newUnit->prev = pool->last;

			pool->last = newUnit;
			pool->current = newUnit;
			return newUnit->address;
		}

	} else {
		GOTPL_ERROR("Pool not init.");
	}

	return 0;
}
