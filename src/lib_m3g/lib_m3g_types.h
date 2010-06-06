/*
 * lib_m3g_types.h
 *
 *  Created on: 3 Jun 2010
 *      Author: id821436
 */

#ifndef LIB_M3G_TYPES_H_
#define LIB_M3G_TYPES_H_

//Data types definition
typedef unsigned char Byte;

typedef short Int16;
typedef unsigned short UInt16;
typedef long Int32;
typedef unsigned long UInt32;

typedef float Float32;

typedef char* String;
typedef Byte Boolean;

//Compound Data Types
struct m3g_matrix {
	Float32 elements[16];
};

struct m3g_vector_3d {
	Float32 x;
	Float32 y;
	Float32 z;
};

struct m3g_color_rgb {
	Byte red;
	Byte green;
	Byte blue;
};

struct m3g_color_rgba {
	struct m3g_color_rgb rgb;
	Byte alpha;
};

typedef UInt32 m3g_object_index;

#endif /* LIB_M3G_TYPES_H_ */
