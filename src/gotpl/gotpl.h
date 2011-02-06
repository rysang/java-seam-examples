//Defining the types
#define _GOTPL_DEBUG 1

#ifdef _GOTPL_DEBUG
#include <stdio.h>
#define GOTPL_DEBUG(msg) fprintf(stdin,"DEBUG: %s line: %i file: %s", msg, __LINE__ ,__FILE__)
#define GOTPL_ERROR(msg) fprintf(stderr,"ERROR: %s line: %i file: %s", msg, __LINE__ ,__FILE__)
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
