#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_TAG_LIST__H
#define __GOTPL_TAG_LIST__H

gotpl_tag_list* gotpl_tag_list_create(gotpl_pool* pool);
gotpl_i gotpl_tag_list_add(gotpl_tag_list* owner, gotpl_tag* obj);
gotpl_i gotpl_tag_list_remove(gotpl_tag_list* owner, gotpl_ui index);
gotpl_tag* gotpl_tag_list_get(gotpl_tag_list* owner, gotpl_ui index);
gotpl_i gotpl_tag_list_insert(gotpl_tag_list* owner, gotpl_ui index,
		gotpl_tag* obj);
gotpl_ui gotpl_tag_list_length(gotpl_tag_list* owner);

/*
 *
 * Iterator
 *
 */
gotpl_tag_list_iterator* gotpl_tag_list_iterator_create(gotpl_tag_list* list);
gotpl_tag* gotpl_tag_list_iterator_next(gotpl_tag_list_iterator* iterator);
gotpl_bool gotpl_tag_list_iterator_has_more(gotpl_tag_list_iterator* iterator);
gotpl_void gotpl_tag_list_iterator_reset(gotpl_tag_list_iterator* iterator);

#endif
