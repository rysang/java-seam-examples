//Defining the types


#ifndef __GOTPL__H
#define __GOTPL__H

#define _GOTPL_DEBUG 1

#ifdef _GOTPL_DEBUG
#include <stdio.h>
#define GOTPL_DEBUG(msg) printf("DEBUG: %s line: %i file: %s\n", msg, __LINE__ ,__FILE__);fflush(stdin)
#define GOTPL_ERROR(msg) fprintf(stderr,"ERROR: %s line: %i file: %s\n", msg, __LINE__ ,__FILE__);fflush(stderr)
#else
#define GOTPL_DEBUG(msg)
#define GOTPL_ERROR(msg)
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
typedef struct gotpl_object_array gotpl_object_array;
typedef struct gotpl_object_list gotpl_object_list;
typedef struct gotpl_object_list_iterator gotpl_object_list_iterator;
typedef struct gotpl_object_map gotpl_object_map;

//generic stack

typedef struct gotpl_stack gotpl_stack;

//define tag stuff.
typedef struct gotpl_tag gotpl_tag;
typedef struct gotpl_tag_context gotpl_tag_context;
typedef struct gotpl_tag_array gotpl_tag_array;
typedef struct gotpl_tag_map gotpl_tag_list;
typedef struct gotpl_tag_map gotpl_tag_map;

//template stuff
typedef struct gotpl_template gotpl_template;

typedef enum {
	gotpl_type_int,
	gotpl_type_uint,
	gotpl_type_float,
	gotpl_type_bool,
	gotpl_type_string,
	gotpl_type_array,
	gotpl_type_list,
	gotpl_type_map

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
} gotpl_value;

typedef struct {
	gotpl_type o_type;
	gotpl_value o_value;
} gotpl_object;

#endif
