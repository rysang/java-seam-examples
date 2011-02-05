#include "gotpl/gotpl_pool.h"
#include <stdio.h>
#include <stdlib.h>

typedef struct gotpl_pool_unit gotpl_pool_unit;

struct gotpl_pool {
	gotpl_ui chunk_size;
	gotpl_pool_unit* first;
	gotpl_pool_unit* last;
	gotpl_pool_unit* current;
	gotpl_pool_unit* current;
};

struct gotpl_pool_unit {
	gotpl_ui size;
	gotpl_ui allocated;
	gotpl_i8* address;
	gotpl_pool_unit* next;
	gotpl_pool_unit* prev;
};

static gotpl_pool_unit* gopl_pool_create_unit(gotpl_ui chunk_size) {
	gotpl_pool_unit* unit = malloc(sizeof(gotpl_pool_unit) + chunk_size);
	if (unit) {
		unit->size = chunk_size;
		unit->allocated = 0;
		unit->prev = unit->next = 0;
		unit->address = ((gotpl_i8*) unit) + sizeof(gotpl_pool_unit);
	}

	return unit;
}

static gotpl_void gopl_pool_destroy_unit(gotpl_pool_unit* unit) {
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

		return gotpl_true;
	}
	return gotpl_false;
}
