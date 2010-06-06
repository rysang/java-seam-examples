/*
 * lib_m3g_converters.c
 *
 *  Created on: 2 Jun 2010
 *      Author: id821436
 */

#include "lib_m3g.h"

Boolean objectToHeader(struct m3g_object* obj, struct m3g_header* objHeader);
Boolean objectToExternalRef(struct m3g_object* obj,
		struct m3g_external_ref* extRef);
Boolean objectToObject3D(struct m3g_object* obj, struct m3g_object_3d* obj3d);
Boolean objectToMesh(struct m3g_object* obj, struct m3g_mesh* mesh);
Boolean objectToTransformable(struct m3g_object* obj,
		struct m3g_transformable* transfObj);

struct m3g_object_converter* m3g_createConverter(pool_t pool) {
	struct m3g_object_converter* conv = p_alloc(pool,
			sizeof(struct m3g_object_converter));
	conv->toHeader = objectToHeader;
	conv->toExternalRef = objectToExternalRef;
	conv->toObject3D = objectToObject3D;
	conv->toTransformable = objectToTransformable;
	return conv;
}

Boolean objectToHeader(struct m3g_object* obj, struct m3g_header* objHeader) {
	if (obj == 0 || objHeader == 0 || obj->ObjectType != Type_HeaderObject) {
		return 0;
	}

	UInt32 count = 0;
	objHeader->VersionNumber[0] = *(obj->Data + count);
	count += sizeof(Byte);
	objHeader->VersionNumber[1] = *(obj->Data + count);
	count += sizeof(Byte);
	objHeader->hasExternalReferences = *(obj->Data + count);
	count += sizeof(Boolean);
	objHeader->TotalFileSize = readUInt32FromArray(obj->Data + count);
	count += sizeof(UInt32);
	objHeader->ApproximateContentSize = readUInt32FromArray(obj->Data + count);
	count += sizeof(UInt32);
	objHeader->AuthoringField = obj->Data + count;
	return 1;
}

Boolean objectToExternalRef(struct m3g_object* obj,
		struct m3g_external_ref* extRef) {
	if (obj == 0 || extRef == 0 || obj->ObjectType != Type_ExternalReference) {
		return 0;
	}
	extRef->URI = obj->Data;
	return 1;
}

Boolean objectToObject3D(struct m3g_object* obj, struct m3g_object_3d* obj3d) {
	if (obj != 0 || obj3d != 0 || (obj->ObjectType >= Type_AnimationController
			&& obj->ObjectType <= Type_World)) {

		obj3d->userID = readUInt32FromArray(obj->Data);
		obj->Data += sizeof(UInt32);
		obj3d->animationTracksLength = readUInt32FromArray(obj->Data);
		obj->Data += sizeof(UInt32);
		obj3d->animationTracks = (m3g_object_index*) obj->Data;
		obj->Data += sizeof(UInt32) * obj3d->animationTracksLength;
		obj3d->userParameterCount = readUInt32FromArray(obj->Data);
		obj->Data += sizeof(UInt32);

		if (obj3d->userParameterCount > 0) {
			obj3d->parameterMap = obj->Data;
			UInt32 i;
			for (i = 0; i < obj3d->userParameterCount; i++) {
				//readId
				obj->Data += sizeof(UInt32);
				UInt32 len = readUInt32FromArray(obj->Data);
				obj->Data += len;
			}
		}

		return 1;
	}
	return 0;
}

Boolean objectToMesh(struct m3g_object* obj, struct m3g_mesh* mesh) {
	if (obj == 0 || mesh == 0 || obj->ObjectType != Type_Mesh) {
		return 0;
	}

	return 1;
}

Boolean objectToTransformable(struct m3g_object* obj,
		struct m3g_transformable* transfObj) {

}

