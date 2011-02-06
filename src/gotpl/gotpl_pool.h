#include "gotpl.h"

typedef struct gotpl_pool gotpl_pool;

gotpl_bool gotpl_pool_create(gotpl_pool** pool, gotpl_ui chunk_size);
gotpl_i gotpl_pool_set_chunk_size(gotpl_pool * pool, gotpl_ui chunk_size);
gotpl_ui8* gotpl_pool_alloc(gotpl_pool * pool, gotpl_ui size);
gotpl_void gotpl_pool_clear(gotpl_pool * pool);
gotpl_void gotpl_pool_destroy(gotpl_pool** pool);
