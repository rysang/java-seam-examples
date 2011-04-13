#include "gotpl.h"
#include "gotpl_pool.h"

#ifndef __GOTPL_UTIL__H
#define __GOTPL_UTIL__H

#define gotpl_define_int(var,value) \
	gotpl_object var; \
	var.o_type = gotpl_type_int; \
	var.o_value.v_i = value;

#define gotpl_define_uint(var,value) \
	gotpl_object var; \
	var.o_type = gotpl_type_uint; \
	var.o_value.v_ui = value;

#define gotpl_define_float(var,value) \
	gotpl_object var; \
	var.o_type = gotpl_type_float; \
	var.o_value.v_f32 = value;

gotpl_object* gotpl_create_string(gotpl_i8* str, gotpl_pool* pool);
gotpl_object* gotpl_create_string_withlen(gotpl_i8* str, gotpl_ui length,
		gotpl_pool* pool);

#endif
