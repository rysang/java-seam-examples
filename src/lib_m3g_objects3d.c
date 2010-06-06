/*
 * lib_m3g_objects3d.c
 *
 *  Created on: 5 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g.h"

Boolean readNextMapParameter(struct m3g_map_parameter_reader* reader,
		struct m3g_map_parameter* parameter);

struct m3g_map_parameter_reader* m3g_createMapParameterReader(
		struct m3g_object_3d* object3d, pool_t pool) {
	struct m3g_map_parameter_reader* reader = p_alloc(pool,
			sizeof(struct m3g_map_parameter_reader));
	reader->count = object3d->userParameterCount;
	reader->currentIndex = 0;
	reader->parameterMap = object3d->parameterMap;
	reader->readNextMapParameter = readNextMapParameter;

	return reader;
}

Boolean readNextMapParameter(struct m3g_map_parameter_reader* reader,
		struct m3g_map_parameter* parameter) {
	if (reader->currentIndex < reader->count) {
		parameter->parameterID = readUInt32FromArray(reader->parameterMap);
		reader->parameterMap += sizeof(UInt32);
		parameter->parameterValueLength = readUInt32FromArray(
				reader->parameterMap);
		reader->parameterMap += sizeof(UInt32);
		parameter->parameterValue = reader->parameterMap;
		reader->parameterMap += parameter->parameterValueLength;
		reader->currentIndex++;
		return 1;
	}

	return 0;
}

