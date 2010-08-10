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
Boolean readNextMorphTarget(struct m3g_morph_target_reader* reader,
		struct m3g_morph_target_data* morphTargetData);
Boolean readNextTransfRef(struct m3g_transf_ref_reader* reader,
		struct m3g_transf_ref_data* transfRefData);
Boolean readNextVertexData(struct m3g_vertex_reader* reader,
		struct m3g_vertex_data* vertexData);

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

struct m3g_morph_target_reader* m3g_createMorphTargetReader(
		struct m3g_morphing_mesh* mesh, pool_t pool) {

	struct m3g_morph_target_reader* reader = p_alloc(pool,
			sizeof(struct m3g_morph_target_reader));
	reader->count = mesh->morphTargetCount;
	reader->currentIndex = 0;
	reader->morphTargetData = mesh->morphTargetData;
	reader->readNextMorphTarget = readNextMorphTarget;

	return reader;
}

Boolean readNextMorphTarget(struct m3g_morph_target_reader* reader,
		struct m3g_morph_target_data* morphTargetData) {

	if (reader->currentIndex < reader->count) {
		morphTargetData->morphTarget = readUInt32FromArray(
				reader->morphTargetData);
		reader->morphTargetData += sizeof(m3g_object_index);
		morphTargetData->initialWeight = readFloat32FromArray(
				reader->morphTargetData);
		reader->morphTargetData += sizeof(Float32);
		reader->currentIndex++;
		return 1;
	}

	return 0;
}

struct m3g_transf_ref_reader* m3g_createTransfRefReader(
		struct m3g_skinned_mesh* mesh, pool_t pool) {

	struct m3g_transf_ref_reader* reader = p_alloc(pool,
			sizeof(struct m3g_transf_ref_reader));
	reader->count = mesh->transformReferenceCount;
	reader->currentIndex = 0;
	reader->transfRefData = mesh->transfRefData;
	reader->readNextTransfRef = readNextTransfRef;

	return reader;
}

Boolean readNextTransfRef(struct m3g_transf_ref_reader* reader,
		struct m3g_transf_ref_data* transfRefData) {

	if (reader->currentIndex < reader->count) {
		transfRefData->transformNode = readUInt32FromArray(
				reader->transfRefData);
		reader->transfRefData += sizeof(m3g_object_index);

		transfRefData->firstVertex = readUInt32FromArray(reader->transfRefData);
		reader->transfRefData += sizeof(UInt32);

		transfRefData->vertexCount = readUInt32FromArray(reader->transfRefData);
		reader->transfRefData += sizeof(UInt32);

		transfRefData->weight = (Int32) readUInt32FromArray(
				reader->transfRefData);
		reader->transfRefData += sizeof(UInt32);

		reader->currentIndex++;
		return 1;
	}

	return 0;
}

Boolean readNextVertexData(struct m3g_vertex_reader* reader,
		struct m3g_vertex_data* vertexData) {

	if (reader->currentIndex < reader->count) {
		vertexData->size = reader->size;
		if (reader->encoding == 0) {
			vertexData->components = reader->vertexData;
			vertexData->componentDeltas = 0;

		} else if (reader->encoding == 1) {
			vertexData->componentDeltas = reader->vertexData;
			vertexData->components = 0;
		}

		vertexData->count = reader->componentCount;
		reader->vertexData += (vertexData->count * vertexData->size);

		reader->currentIndex++;
		return 1;
	}

	return 0;
}

struct m3g_vertex_reader* m3g_createVertexArrayDataReader(
		struct m3g_vertex_array* vArray, pool_t pool) {

	struct m3g_vertex_reader* reader = p_alloc(pool,
			sizeof(struct m3g_vertex_reader));
	reader->count = vArray->vertexCount;
	reader->currentIndex = 0;
	reader->vertexData = vArray->vertexData;
	reader->readNextVertex = readNextVertexData;
	reader->size = vArray->componentSize;
	reader->componentCount = vArray->componentCount;
	reader->encoding = vArray->encoding;

	return reader;
}
