#include "gotpl.h"

#ifndef __GOTPL_EXPRESSIONS__H
#define __GOTPL_EXPRESSIONS__H

typedef struct gotpl_expression gotpl_expression;
typedef gotpl_object
* (*gotpl_evaluate_t)(gotpl_expression* expr, gotpl_object_map* context,
		gotpl_object_array* params);

struct gotpl_expression {
	gotpl_pool* pool;
	gotpl_i8* _private;
	gotpl_evaluate_t evaluate;
};

//retrieves something similar to ${someVar.text}
gotpl_expression* gotpl_expr_create_context_var(gotpl_pool* pool);
gotpl_expression* gotpl_expr_create_scalar(gotpl_pool* pool);
gotpl_expression* gotpl_expr_create_sum(gotpl_pool* pool);


#endif
