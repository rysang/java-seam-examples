#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_STACK__H
#define __GOTPL_STACK__H

gotpl_stack* gotpl_stack_create(gotpl_pool* pool);
gotpl_void gotpl_stack_push(gotpl_stack* stack, gotpl_i8* gvalue);
gotpl_i8* gotpl_stack_pop(gotpl_stack* stack);
gotpl_i8* gotpl_stack_peek(gotpl_stack* stack);
gotpl_ui gotpl_stack_size(gotpl_stack* stack);

#endif
