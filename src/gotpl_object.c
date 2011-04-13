#include "gotpl/gotpl.h"
#include "gotpl/gotpl_util.h"

gotpl_object* gotpl_create_string(gotpl_i8* str, gotpl_pool* pool) {
	return gotpl_create_string_withlen(str, strlen(str), pool);
}
gotpl_object* gotpl_create_string_withlen(gotpl_i8* str, gotpl_ui length,
		gotpl_pool* pool) {

	gotpl_object* retStr = gotpl_pool_alloc(pool, sizeof(gotpl_object));
	retStr->o_string_length = length;
	retStr->o_type = gotpl_type_string;
	retStr->o_value.v_str = gotpl_pool_alloc(pool, retStr->o_string_length);

	memcpy(retStr->o_value.v_str, str, length);

	return retStr;
}
