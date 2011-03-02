#include "gotpl/gotpl_stack.h"

typedef struct gotpl_stack_unit gotpl_stack_unit;

struct gotpl_stack_unit {

	gotpl_stack_unit* prev;
	gotpl_stack_unit* next;
	gotpl_i8* value;

};

struct gotpl_stack {

	gotpl_stack_unit* last;
	gotpl_stack_unit* first;
	gotpl_ui stack_size;
	gotpl_pool* pool;
};

static gotpl_stack_unit* gotpl_stack_create_unit(gotpl_pool* pool);

static gotpl_stack_unit* gotpl_stack_create_unit(gotpl_pool* pool) {
	gotpl_stack_unit* stack_unit = (gotpl_stack_unit*) gotpl_pool_alloc(pool,
			sizeof(gotpl_stack_unit));
	if (!stack_unit) {
		GOTPL_ERROR("Failed to alloc gotpl_stack_unit");
		return 0;
	}

	stack_unit->next = stack_unit->prev = stack_unit->value = 0;
	return stack_unit;
}

gotpl_stack* gotpl_stack_create(gotpl_pool* pool) {
	gotpl_stack* stack = (gotpl_stack*) gotpl_pool_alloc(pool,
			sizeof(gotpl_stack));

	if (!stack) {
		GOTPL_ERROR("Failed to alloc gotpl_stack");
		return 0;
	}

	GOTPL_DEBUG("Allocated stack");
	stack->stack_size = 1;
	stack->first = stack->last = gotpl_stack_create_unit(pool);
	stack->pool = pool;

	return stack;
}

gotpl_void gotpl_stack_push(gotpl_stack* stack, gotpl_i8* gvalue) {
	gotpl_stack_unit* unit = stack->last;

	if (!unit->next) {
		GOTPL_DEBUG("Next chunk is null trying to allocate.");
		unit->next = gotpl_stack_create_unit(stack->pool);
		if (!unit->next) {
			GOTPL_ERROR("Unable to allocate next chunk.");
			return;
		}

		stack->stack_size++;
	}

	GOTPL_DEBUG("Change last value.");
	unit->next->value = gvalue;
	unit->next->prev = unit;
	stack->last = unit->next;

}

gotpl_i8* gotpl_stack_pop(gotpl_stack* stack) {

	gotpl_stack_unit* unit = stack->last;
	if (!unit->prev) {
		return 0;
	}

	stack->last = stack->last->prev;
	return unit->value;
}

gotpl_ui gotpl_stack_size(gotpl_stack* stack) {
	return stack->stack_size;
}
