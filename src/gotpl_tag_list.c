#include "gotpl/gotpl_tag.h"
#include "gotpl/gotpl_tag_list.h"
#include "gotpl/gotpl_pool.h"

typedef struct gotpl_list_chunk gotpl_list_chunk;

struct gotpl_tag_list_iterator {
	gotpl_tag_list* parent;
	gotpl_list_chunk* current;
};

struct gotpl_list_chunk {
	gotpl_tag tag;
	gotpl_list_chunk* next;
	gotpl_list_chunk* prev;
};

struct gotpl_tag_list {
	gotpl_ui count;
	gotpl_list_chunk* first_chunk;
	gotpl_list_chunk* last_chunk;
	gotpl_pool* pool;
};

gotpl_tag_list* gotpl_tag_list_create(gotpl_pool* pool) {
	gotpl_tag_list* list = (gotpl_tag_list*) gotpl_pool_alloc(pool,
			sizeof(gotpl_tag_list));
	if (list) {
		GOTPL_DEBUG("Allocated list tag.");
		list->count = 0;
		list->pool = pool;

		return list;
	}

	GOTPL_ERROR("Failed to create list.");
	return 0;
}

gotpl_i gotpl_tag_list_add(gotpl_tag_list* owner, gotpl_tag* obj) {
	if (owner->count == 0) {
		owner->first_chunk = owner->last_chunk
				= (gotpl_list_chunk*) gotpl_pool_alloc(owner->pool,
						sizeof(gotpl_list_chunk));
		if (owner->first_chunk) {
			GOTPL_DEBUG("Allocated first chunk.");

			owner->last_chunk->tag = *obj;
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
		owner->last_chunk->tag = *obj;
		return (owner->count++);
	}

	GOTPL_ERROR("Failed to alloc chunk.");
	return -1;
}

gotpl_i gotpl_tag_list_remove(gotpl_tag_list* owner, gotpl_ui index) {
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

gotpl_tag* gotpl_tag_list_get(gotpl_tag_list* owner, gotpl_ui index) {
	if (owner->count < index) {
		GOTPL_ERROR("Index out of bounds.");
		return 0;
	}

	gotpl_ui current_index = 0;
	gotpl_list_chunk* current_chunk = owner->first_chunk;

	while (gotpl_true) {
		if (current_index == index) {
			return &current_chunk->tag;
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

gotpl_i gotpl_tag_list_insert(gotpl_tag_list* owner, gotpl_ui index,
		gotpl_tag* obj) {
	if (owner->count < index) {
		GOTPL_ERROR("Index out of bounds.");
		return 0;
	}

	gotpl_ui current_index = 0;
	gotpl_list_chunk* current_chunk = owner->first_chunk;

	while (gotpl_true) {
		if (current_index == index) {
			current_chunk->tag = *obj;
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

gotpl_ui gotpl_tag_list_length(gotpl_tag_list* owner) {
	return owner->count;
}

gotpl_tag_list_iterator* gotpl_tag_list_iterator_create(gotpl_tag_list* list) {
	gotpl_tag_list_iterator* list_it =
			(gotpl_tag_list_iterator*) gotpl_pool_alloc(list->pool,
					sizeof(gotpl_tag_list_iterator));
	if (list_it == 0) {
		GOTPL_ERROR("Could not alloc iterator.");
		return 0;
	}

	list_it->parent = list;
	list_it->current = list->first_chunk;

	return list_it;
}

gotpl_tag* gotpl_tag_list_iterator_next(gotpl_tag_list_iterator* iterator) {
	if (iterator->current->next) {
		iterator->current = iterator->current->next;
		return &iterator->current->tag;
	}

	return 0;
}

gotpl_bool gotpl_tag_list_iterator_has_more(gotpl_tag_list_iterator* iterator) {
	return iterator->current->next != 0;
}

gotpl_void gotpl_tag_list_iterator_reset(gotpl_tag_list_iterator* iterator) {
	iterator->current = iterator->parent->first_chunk;
}
