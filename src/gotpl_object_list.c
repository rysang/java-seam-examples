#include "gotpl/gotpl_object_list.h"
#include "gotpl/gotpl_pool.h"

typedef struct gotpl_list_chunk gotpl_list_chunk;

struct gotpl_object_list_iterator {
	gotpl_object_list* parent;
	gotpl_list_chunk* current;
};

struct gotpl_list_chunk {
	gotpl_object object;
	gotpl_list_chunk* next;
};

struct gotpl_object_list {
	gotpl_ui count;
	gotpl_list_chunk* first_chunk;
	gotpl_list_chunk* last_chunk;
	gotpl_pool* pool;
};

gotpl_object_list* gotpl_object_list_create(gotpl_pool* pool) {
	gotpl_object_list* list = gotpl_pool_alloc(pool, sizeof(gotpl_object_list));
	if (list) {
		GOTPL_DEBUG("Allocated list object.");
		list->count = 0;
		list->pool = pool;

		return list;
	}

	GOTPL_ERROR("Failed to create list.");
	return 0;
}

gotpl_i gotpl_object_list_add(gotpl_object_list* owner, gotpl_object* obj) {
	if (owner->count == 0) {
		owner->first_chunk = owner->last_chunk = gotpl_pool_alloc(owner->pool,
				sizeof(gotpl_list_chunk));
		if (owner->first_chunk) {
			GOTPL_DEBUG("Allocated first chunk.");

			owner->last_chunk->object = *obj;
			owner->last_chunk->next = 0;
			return (owner->count++);
		}
	}

	gotpl_list_chunk* last_chunk = owner->last_chunk;
	last_chunk->next = gotpl_pool_alloc(owner->pool, sizeof(gotpl_list_chunk));
	if (last_chunk->next) {
		GOTPL_DEBUG("Allocated chunk.");

		owner->last_chunk = last_chunk->next;
		owner->last_chunk->object = *obj;
		owner->last_chunk->next = 0;
		return (owner->count++);
	}

	GOTPL_ERROR("Failed to alloc chunk.");
	return -1;
}
