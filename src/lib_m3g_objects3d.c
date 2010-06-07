/*
 * lib_m3g_objects3d.c
 *
 *  Created on: 5 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g.h"

Boolean readNextMapParameter(struct m3g_map_parameter_reader* reader,
		struct m3g_map_parameter* parameter);
Boolean readNextSubMesh(struct m3g_submesh_reader* reader,
		struct m3g_submesh_data* subMData);

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

struct m3g_submesh_reader* m3g_createSubMeshReader(struct m3g_mesh* mesh,
		pool_t pool) {
	struct m3g_submesh_reader* reader = p_alloc(pool,
			sizeof(struct m3g_submesh_reader));
	reader->count = mesh->submeshCount;
	reader->currentIndex = 0;
	reader->submeshData = mesh->submeshData;
	reader->readNextSubMesh = readNextSubMesh;

	return reader;
}

Boolean readNextSubMesh(struct m3g_submesh_reader* reader,
		struct m3g_submesh_data* subMData) {

	if (reader->currentIndex < reader->count) {
		subMData->indexBuffer = readUInt32FromArray(reader->submeshData);
		reader->submeshData += sizeof(UInt32);
		subMData->appearance = readUInt32FromArray(reader->submeshData);
		reader->submeshData += sizeof(UInt32);
		reader->currentIndex++;
		return 1;
	}

	return 0;
}

