/*
 * lib_m3g_objects.h
 *
 *  Created on: 3 Jun 2010
 *      Author: cpriceputu
 */
#include "lib_m3g_protos.h"

#ifndef LIB_M3G_OBJECTS_H_
#define LIB_M3G_OBJECTS_H_

//General File Objects
struct m3g_object {
	Byte ObjectType;
	UInt32 Length;
	Byte* Data;
};

//End Explicit objects
typedef Boolean (*m3g_readNextObject)(struct m3g_object_reader* objReader,
		struct m3g_object* obj);
struct m3g_object_reader {
	UInt32 count;
	struct m3g_section* section;
	m3g_readNextObject readNextObject;
};

//Allocs an m3g_object_reader object on the heap
struct m3g_object_reader* m3g_createObjectReader(struct m3g_section* section,
		pool_t pool);

#endif /* LIB_M3G_OBJECTS_H_ */
