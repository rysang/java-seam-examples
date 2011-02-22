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
	gotpl_list_chunk* prev;
};

struct gotpl_object_list {
	gotpl_ui count;
	gotpl_list_chunk* first_chunk;
	gotpl_list_chunk* last_chunk;
	gotpl_pool* pool;
};

gotpl_object_list* gotpl_object_list_create(gotpl_pool* pool) {
	gotpl_object_list* list = (gotpl_object_list*) gotpl_pool_alloc(pool,
			sizeof(gotpl_object_list));
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
		owner->first_chunk = owner->last_chunk
				= (gotpl_list_chunk*) gotpl_pool_alloc(owner->pool,
						sizeof(gotpl_list_chunk));
		if (owner->first_chunk) {
			GOTPL_DEBUG("Allocated first chunk.");

			owner->last_chunk->object = *obj;
			owner->last_chunk->prev = owner->last_chunk->next = 0;
			return (owner->count++);
		}
	}

	gotpl_list_chunk* last_chunk = owner->last_chunk;
	last_chunk->next = (gotpl_list_chunk*) gotpl_pool_alloc(owner->pool,
			sizeof(gotpl_list_chunk));
	if (last_chunk->next) {
		GOTPL_DEBUG("Allocated chunk.");

		owner->last_chunk = last_chunk->next;
		owner->last_chunk->prev = last_chunk;
		owner->last_chunk->next = 0;
		owner->last_chunk->object = *obj;
		return (owner->count++);
	}

	GOTPL_ERROR("Failed to alloc chunk.");
	return -1;
}

gotpl_i gotpl_object_list_remove(gotpl_object_list* owner, gotpl_ui index) {
	if (owner->count < index) {
		GOTPL_ERROR("Index out of bounds.");
		return -1;
	}

	gotpl_ui current_index = 0;
	gotpl_list_chunk* current_chunk = owner->first_chunk;

	while (gotpl_true) {
		if (current_index == index) {

			GOTPL_DEBUG("Checking for next element.");

			if (current_chunk->next) {
				GOTPL_DEBUG("Next element found.");

				if (current_chunk->prev) {
					current_chunk->prev->next = current_chunk->next;
				} else {
					GOTPL_DEBUG("This is the first element.");

					owner->first_chunk = current_chunk->next;
				}
			} else {
				GOTPL_DEBUG("No next element.")
				;
			}

			owner->count--;
			return index;
		}

		if (current_chunk->next) {
			current_chunk = current_chunk->next;
			current_index++;
		} else {
			break;
		}
	}

	GOTPL_ERROR("Index out of bounds.");
	return -1;
}

gotpl_object* gotpl_object_list_get(gotpl_object_list* owner, gotpl_ui index) {
	if (owner->count < index) {
		GOTPL_ERROR("Index out of bounds.");
		return 0;
	}

	gotpl_ui current_index = 0;
	gotpl_list_chunk* current_chunk = owner->first_chunk;

	while (gotpl_true) {
		if (current_index == index) {
			return &current_chunk->object;
		}

		if (current_chunk->next) {
			current_chunk = current_chunk->next;
			current_index++;
		} else {
			break;
		}
	}

	GOTPL_ERROR("Index out of bounds.");
	return 0;
}

gotpl_i gotpl_object_list_insert(gotpl_object_list* owner, gotpl_ui index,
		gotpl_object* obj) {
	if (owner->count < index) {
		GOTPL_ERROR("Index out of bounds.");
		return 0;
	}

	gotpl_ui current_index = 0;
	gotpl_list_chunk* current_chunk = owner->first_chunk;

	while (gotpl_true) {
		if (current_index == index) {
			current_chunk->object = *obj;
			return current_index;
		}

		if (current_chunk->next) {
			current_chunk = current_chunk->next;
			current_index++;
		} else {
			break;
		}
	}

	GOTPL_ERROR("Index out of bounds.");
	return 0;
}

gotpl_ui gotpl_object_list_length(gotpl_object_list* owner) {
	return owner->count;
}

gotpl_object_list_iterator* gotpl_object_list_iterator_create(
		gotpl_object_list* list) {
	gotpl_object_list_iterator* list_it =
			(gotpl_object_list_iterator*) gotpl_pool_alloc(list->pool,
					sizeof(gotpl_object_list_iterator));
	if (list_it == 0) {
		GOTPL_ERROR("Could not alloc iterator.");
		return 0;
	}

	list_it->parent = list;
	list_it->current = list->first_chunk;

	return list_it;
}

gotpl_object* gotpl_object_list_iterator_next(
		gotpl_object_list_iterator* iterator) {
	if (iterator->current->next) {
		iterator->current = iterator->current->next;
		return &iterator->current->object;
	}

	return 0;
}

gotpl_bool gotpl_object_list_iterator_has_more(
		gotpl_object_list_iterator* iterator) {
	return iterator->current->next != 0;
}

gotpl_void gotpl_object_list_iterator_reset(
		gotpl_object_list_iterator* iterator) {
	iterator->current = iterator->parent->first_chunk;
}
