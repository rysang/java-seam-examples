#include "gotpl.h"
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

#endif
