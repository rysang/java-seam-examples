#include "gotpl.h"

#ifndef __GOTPL_POOL__H
#define __GOTPL_POOL__H

gotpl_bool gotpl_pool_create(gotpl_pool** pool, gotpl_ui chunk_size);
gotpl_i gotpl_pool_set_chunk_size(gotpl_pool * pool, gotpl_ui chunk_size);
gotpl_ui8* gotpl_pool_alloc(gotpl_pool * pool, gotpl_ui size);
gotpl_void gotpl_pool_clear(gotpl_pool * pool);
gotpl_void gotpl_pool_destroy(gotpl_pool** pool);

#endif
