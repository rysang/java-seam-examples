//Defining the types


#ifndef __GOTPL__H
#define __GOTPL__H

#define _GOTPL_DEBUG 1

#ifdef _GOTPL_DEBUG
#include <stdio.h>
#define GOTPL_DEBUG_I4(msg,i) printf("DEBUG: %s  %i line: %i file: %s\n", msg, i, __LINE__ ,__FILE__);fflush(stdin)
#define GOTPL_DEBUG(msg) printf("DEBUG: %s line: %i file: %s\n", msg, __LINE__ ,__FILE__);fflush(stdin)
#define GOTPL_ERROR(msg) fprintf(stderr,"ERROR: %s line: %i file: %s\n", msg, __LINE__ ,__FILE__);fflush(stderr)
#define GOTPL_ERROR_I4(msg,i) fprintf(stderr,"ERROR: %s  %i line: %i file: %s\n", msg, __LINE__ ,__FILE__);fflush(stderr)
#else
#define GOTPL_DEBUG_I4(msg,i)
#define GOTPL_DEBUG(msg)
#define GOTPL_ERROR(msg)
#define GOTPL_ERROR_I4(msg)
#endif

typedef unsigned char gotpl_ui8;
typedef signed char gotpl_i8;

typedef unsigned short gotpl_ui16;
typedef signed short gotpl_i16;

typedef unsigned int gotpl_ui32;
typedef signed int gotpl_i32;

typedef unsigned long long gotpl_ui64;
typedef signed long long gotpl_i64;

typedef float gotpl_f32;
typedef void gotpl_void;

//Defining complex types
typedef union {
	gotpl_ui16 m16;
	gotpl_ui8 m8[2];
} gotpl_cui16;

typedef union {
	gotpl_i16 m16;
	gotpl_i8 m8[2];
} gotpl_ci16;

typedef union {
	gotpl_ui32 m32;
	gotpl_ui8 m8[4];
} gotpl_cui32;

typedef union {
	gotpl_i32 m32;
	gotpl_i8 m8[4];
} gotpl_ci32;

typedef union {
	gotpl_ui64 m64;
	gotpl_ui8 m8[4];
} gotpl_cui64;

typedef union {
	gotpl_i64 m64;
	gotpl_i8 m8[4];
} gotpl_ci64;

typedef union {
	gotpl_f32 m32;
	gotpl_i8 m8[4];
} gotpl_cf32;

#ifdef _64_BITS_SYSTEM
typedef gotpl_ui64 gotpl_ui;
typedef gotpl_i64 gotpl_i;
typedef gotpl_cui64 gotpl_cui;
typedef gotpl_ci64 gotpl_ci;
#else
typedef gotpl_ui32 gotpl_ui;
typedef gotpl_i32 gotpl_i;
typedef gotpl_cui32 gotpl_cui;
typedef gotpl_ci32 gotpl_ci;
#endif

typedef gotpl_ui gotpl_bool;
#define gotpl_true 1
#define gotpl_false 0

//define complex types
typedef struct gotpl_object gotpl_object;
typedef struct gotpl_object_array gotpl_object_array;
typedef struct gotpl_object_list gotpl_object_list;
typedef struct gotpl_object_list_iterator gotpl_object_list_iterator;
typedef struct gotpl_object_map gotpl_object_map;

//allocation pool
typedef struct gotpl_pool gotpl_pool;

//generic stack
typedef struct gotpl_stack gotpl_stack;

//parser
typedef struct gotpl_parser gotpl_parser;

//define tag stuff.
typedef struct gotpl_tag gotpl_tag;
typedef struct gotpl_context gotpl_context;
typedef struct gotpl_tag_array gotpl_tag_array;
typedef struct gotpl_tag_list gotpl_tag_list;
typedef struct gotpl_tag_list_iterator gotpl_tag_list_iterator;
typedef struct gotpl_tag_map gotpl_tag_map;

//template stuff
typedef struct gotpl_template gotpl_template;

//Typedef for the function prototype
typedef gotpl_object* (*gotpl_function_t)(gotpl_object_array* params,
		gotpl_pool* pool);

typedef enum {
	gotpl_type_int,
	gotpl_type_uint,
	gotpl_type_float,
	gotpl_type_bool,
	gotpl_type_string,
	gotpl_type_array,
	gotpl_type_list,
	gotpl_type_map,
	gotpl_type_function

} gotpl_type;

typedef union {
	gotpl_i v_i;
	gotpl_ui v_ui;
	gotpl_f32 v_f32;
	gotpl_bool v_bool;
	gotpl_i8* v_str;
	gotpl_object_array* v_array;
	gotpl_object_list* v_list;
	gotpl_object_map* v_map;
	gotpl_function_t v_func;
} gotpl_value;

struct gotpl_object {
	gotpl_type o_type;
	gotpl_value o_value;

	//only if string otherwise 0
	gotpl_ui o_string_length;
};

//Hash maps array size
#define gotpl_default_map_size 1024
//parser buffer size for text chunks, they will be segmented to this max value of bytes.
#define gotpl_default_parser_buffer_size 1024
//Maximum size of an indirection it's allocated on the stack so be careful.
#define gotpl_default_indirection_size 64

#endif
