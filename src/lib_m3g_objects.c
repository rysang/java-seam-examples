/*
 * m3g_sectionReader.c
 *
 *  Created on: 1 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g.h"

Boolean
readNextObject(struct m3g_object_reader* reader, struct m3g_object* obj);

struct m3g_object_reader* m3g_createObjectReader(struct m3g_section* section,
		pool_t pool) {
	struct m3g_object_reader* obj_reader = p_alloc(pool,
			sizeof(struct m3g_object_reader));
	obj_reader->readNextObject = readNextObject;
	obj_reader->section = section;
	obj_reader->count = 0;

	return obj_reader;
}

Boolean readNextObject(struct m3g_object_reader* reader, struct m3g_object* obj) {
	if (obj == 0)
		return 0;

	if (reader->section->UncompressedLength > reader->count) {
		obj ->ObjectType = *(reader->section->Objects + reader->count);
		reader->count += sizeof(Byte);
		obj->Length = readUInt32FromArray(reader->section->Objects
				+ reader->count);
		reader->count += sizeof(UInt32);
		obj->Data = reader->section->Objects + reader->count;
		reader->count += obj->Length;
		return 1;
	}

	return 0;
}
