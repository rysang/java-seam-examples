#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_OBJECT_LIST__H
#define __GOTPL_OBJECT_LIST__H

gotpl_object_list* gotpl_object_list_create(gotpl_pool* pool);
gotpl_i gotpl_object_list_add(gotpl_object_list* owner, gotpl_object* obj);
gotpl_i gotpl_object_list_remove(gotpl_object_list* owner, gotpl_ui index);
gotpl_object* gotpl_object_list_get(gotpl_object_list* owner, gotpl_ui index);
gotpl_i gotpl_object_list_insert(gotpl_object_list* owner, gotpl_ui index,
		gotpl_object* obj);
gotpl_ui gotpl_object_list_length(gotpl_object_list* owner);

/*
 *
 * Iterator
 *
 */
gotpl_object_list_iterator* gotpl_object_list_iterator_create(
		gotpl_object_list* list);
gotpl_object* gotpl_object_list_iterator_next(
		gotpl_object_list_iterator* iterator);
gotpl_bool gotpl_object_list_iterator_has_more(
		gotpl_object_list_iterator* iterator);
gotpl_void gotpl_object_list_iterator_reset(
		gotpl_object_list_iterator* iterator);

#endif
