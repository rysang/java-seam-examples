/*
 * lib_m3g_converters.c
 *
 *  Created on: 2 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g.h"

Boolean objectToHeader(struct m3g_object* obj, struct m3g_header* objHeader);
Boolean objectToExternalRef(struct m3g_object* obj,
		struct m3g_external_ref* extRef);
Boolean objectToObject3D(struct m3g_object* obj, struct m3g_object_3d* obj3d);
Boolean objectToMesh(struct m3g_object* obj, struct m3g_mesh* mesh);
Boolean objectToTransformable(struct m3g_object* obj,
		struct m3g_transformable* transfObj);
Boolean objectToNode(struct m3g_object* obj, struct m3g_node* nodeObj);
Boolean objectToCamera(struct m3g_object* obj, struct m3g_camera* cameraObj);
Boolean objectToMorphMesh(struct m3g_object* obj,
		struct m3g_morphing_mesh* morphMeshObj);
Boolean objectToSkinnedMesh(struct m3g_object* obj,
		struct m3g_skinned_mesh* skinnedMeshObj);
Boolean objectToLight(struct m3g_object* obj, struct m3g_light* lightObj);
Boolean objectToMaterial(struct m3g_object* obj,
		struct m3g_material* materialObj);
Boolean objectToVertexArray(struct m3g_object* obj,
		struct m3g_vertex_array* vertexArray);

struct m3g_object_converter* m3g_createConverter(pool_t pool) {
	struct m3g_object_converter* conv = p_alloc(pool,
			sizeof(struct m3g_object_converter));
	conv->toHeader = objectToHeader;
	conv->toExternalRef = objectToExternalRef;
	conv->toObject3D = objectToObject3D;
	conv->toTransformable = objectToTransformable;
	conv->toNode = objectToNode;
	conv->toMesh = objectToMesh;
	conv->toCamera = objectToCamera;
	conv->toMorphMesh = objectToMorphMesh;
	conv->toSkinnedMesh = objectToSkinnedMesh;
	conv->toLight = objectToLight;
	conv->toMaterial = objectToMaterial;
	conv->toVertexArray = objectToVertexArray;
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
		if (obj3d->animationTracksLength > 0) {
			obj3d->animationTracks = (m3g_object_index*) obj->Data;
			obj->Data += sizeof(UInt32) * obj3d->animationTracksLength;
		}
		obj3d->userParameterCount = readUInt32FromArray(obj->Data);
		obj->Data += sizeof(UInt32);

		if (obj3d->userParameterCount > 0) {
			obj3d->parameterMap = obj->Data;
			UInt32 i;
			for (i = 0; i < obj3d->userParameterCount; i++) {
				//readId
				obj->Data += sizeof(UInt32);
				UInt32 len = readUInt32FromArray(obj->Data);
				obj->Data += sizeof(UInt32);
				obj->Data += len;
			}
		}

		return 1;
	}
	return 0;
}

Boolean objectToTransformable(struct m3g_object* obj,
		struct m3g_transformable* transfObj) {
	if ((obj == 0 || transfObj == 0)) {
		return 0;
	}

	if (obj->ObjectType == Type_Texture2D || obj->ObjectType == Type_Camera
			|| obj->ObjectType == Type_Group || obj->ObjectType == Type_World
			|| obj->ObjectType == Type_Light || obj->ObjectType
			== Type_SkinnedMesh || obj->ObjectType == Type_MorphingMesh
			|| obj->ObjectType == Type_Sprite || obj->ObjectType == Type_Mesh
			|| obj->ObjectType == Type_Image2D) {

		if (!objectToObject3D(obj, &transfObj->obj3d)) {
			return 0;
		}

		transfObj->hasComponentTransform = *(obj->Data);
		obj->Data += sizeof(Boolean);

		if (transfObj->hasComponentTransform) {
			transfObj->translation.x = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
			transfObj->translation.y = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
			transfObj->translation.z = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);

			transfObj->scale.x = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
			transfObj->scale.y = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
			transfObj->scale.z = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);

			transfObj->orientationAngle = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);

			transfObj->orientationAxis.x = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
			transfObj->orientationAxis.y = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
			transfObj->orientationAxis.z = readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
		}

		transfObj->hasGeneralTransform = (Boolean) *(obj->Data);
		obj->Data += sizeof(Boolean);

		if (transfObj->hasGeneralTransform) {
			UInt32 i;
			UInt32 len = (sizeof(transfObj->transform.elements)
					/ sizeof(transfObj->transform.elements[0]));
			for (i = 0; i < len; i++) {
				transfObj->transform.elements[i] = readFloat32FromArray(
						obj->Data);
				obj->Data += sizeof(Float32);
				//printf("FLOAT VALUE: %.02f\n", transfObj->transform.elements[i]);
			}

		}

		return 1;
	}

	return 0;
}

Boolean objectToNode(struct m3g_object* obj, struct m3g_node* nodeObj) {
	if ((obj == 0 || nodeObj == 0)) {
		return 0;
	}

	if (obj->ObjectType == Type_Camera || obj->ObjectType == Type_Group
			|| obj->ObjectType == Type_World || obj->ObjectType == Type_Light
			|| obj->ObjectType == Type_SkinnedMesh || obj->ObjectType
			== Type_MorphingMesh || obj->ObjectType == Type_Sprite
			|| obj->ObjectType == Type_Mesh) {
		if (!objectToTransformable(obj, &nodeObj->transfObj)) {
			return 0;
		}

		nodeObj->enableRendering = *obj->Data;
		obj->Data += sizeof(Boolean);
		nodeObj->enablePicking = *obj->Data;
		obj->Data += sizeof(Boolean);
		nodeObj->alphaFactor = *obj->Data;
		obj->Data += sizeof(Byte);
		nodeObj->scope = readUInt32FromArray(obj->Data);
		obj->Data += sizeof(UInt32);
		nodeObj->hasAlignment = *obj->Data;
		obj->Data += sizeof(Boolean);

		if (nodeObj->hasAlignment) {
			nodeObj->zTarget = *obj->Data;
			obj->Data += sizeof(Byte);

			nodeObj->yTarget = *obj->Data;
			obj->Data += sizeof(Byte);

			nodeObj->zReference = readUInt32FromArray(obj->Data);
			obj->Data += sizeof(m3g_object_index);
			nodeObj->yReference = readUInt32FromArray(obj->Data);
			obj->Data += sizeof(m3g_object_index);
		}

		return 1;
	}

	return 0;
}

Boolean objectToMesh(struct m3g_object* obj, struct m3g_mesh* meshObj) {
	if (obj == 0 || meshObj == 0 || obj->ObjectType != Type_Mesh) {
		return 0;
	}

	if (!objectToNode(obj, &meshObj->nodeObj)) {
		return 0;
	}

	meshObj->vertexBuffer = readUInt32FromArray(obj->Data);
	obj->Data += sizeof(m3g_object_index);
	meshObj->submeshCount = readUInt32FromArray(obj->Data);
	obj->Data += sizeof(UInt32);

	meshObj->submeshData = obj->Data;

	return 1;
}

Boolean objectToCamera(struct m3g_object* obj, struct m3g_camera* cameraObj) {
	if (obj == 0 || cameraObj == 0 || obj->ObjectType != Type_Camera) {
		return 0;
	}

	if (!objectToNode(obj, &cameraObj->nodeObj)) {
		return 0;
	}

	cameraObj->projectionType = *obj->Data;
	obj->Data += sizeof(Byte);

	if (cameraObj->projectionType == ProjectionType_GENERIC) {
		UInt32 i;
		UInt32 len = (sizeof(cameraObj->nodeObj.transfObj.transform.elements)
				/ sizeof(cameraObj->nodeObj.transfObj.transform.elements[0]));

		for (i = 0; i < len; i++) {
			cameraObj->nodeObj.transfObj.transform.elements[i]
					= readFloat32FromArray(obj->Data);
			obj->Data += sizeof(Float32);
			//printf("FLOAT VALUE: %.02f\n",
			//		cameraObj->nodeObj.transfObj.transform.elements[i]);
		}
	} else {
		cameraObj->fovy = readFloat32FromArray(obj->Data);
		obj->Data += sizeof(Float32);
		cameraObj->AspectRatio = readFloat32FromArray(obj->Data);
		obj->Data += sizeof(Float32);
		cameraObj->near = readFloat32FromArray(obj->Data);
		obj->Data += sizeof(Float32);
		cameraObj->far = readFloat32FromArray(obj->Data);
		obj->Data += sizeof(Float32);
	}

	return 1;
}

Boolean objectToMorphMesh(struct m3g_object* obj,
		struct m3g_morphing_mesh* morphMeshObj) {
	if (obj == 0 || morphMeshObj == 0 || obj->ObjectType != Type_MorphingMesh) {
		return 0;
	}

	if (!objectToMesh(obj, &morphMeshObj->meshObj)) {
		return 0;
	}

	morphMeshObj->morphTargetCount = readUInt32FromArray(obj->Data);
	obj->Data += sizeof(UInt32);

	morphMeshObj->morphTargetData = obj->Data;
	return 1;
}

Boolean objectToSkinnedMesh(struct m3g_object* obj,
		struct m3g_skinned_mesh* skinnedMeshObj) {

	if (obj == 0 || skinnedMeshObj == 0 || obj->ObjectType != Type_SkinnedMesh) {
		return 0;
	}

	if (!objectToMesh(obj, &skinnedMeshObj->meshObj)) {
		return 0;
	}

	skinnedMeshObj->skeleton = readUInt32FromArray(obj->Data);
	obj->Data += sizeof(UInt32);

	skinnedMeshObj->transformReferenceCount = readUInt32FromArray(obj->Data);
	obj->Data += sizeof(UInt32);

	skinnedMeshObj->transfRefData = obj->Data;

	return 1;
}

Boolean objectToLight(struct m3g_object* obj, struct m3g_light* lightObj) {
	if (obj == 0 || lightObj == 0 || obj->ObjectType != Type_Light) {
		return 0;
	}

	if (!objectToNode(obj, &lightObj->nodeObj)) {
		return 0;
	}

	lightObj->attenuationConstant = readFloat32FromArray(obj->Data);
	obj->Data += sizeof(Float32);

	lightObj->attenuationLinear = readFloat32FromArray(obj->Data);
	obj->Data += sizeof(Float32);

	lightObj->attenuationQuadratic = readFloat32FromArray(obj->Data);
	obj->Data += sizeof(Float32);

	lightObj->color.red = *obj->Data;
	obj->Data += sizeof(Byte);
	lightObj->color.green = *obj->Data;
	obj->Data += sizeof(Byte);
	lightObj->color.blue = *obj->Data;
	obj->Data += sizeof(Byte);

	lightObj->mode = *obj->Data;
	obj->Data += sizeof(Byte);

	lightObj->intensity = readFloat32FromArray(obj->Data);
	obj->Data += sizeof(Float32);

	lightObj->spotAngle = readFloat32FromArray(obj->Data);
	obj->Data += sizeof(Float32);

	lightObj->spotExponent = readFloat32FromArray(obj->Data);
	obj->Data += sizeof(Float32);

	return 1;
}

Boolean objectToMaterial(struct m3g_object* obj,
		struct m3g_material* materialObj) {
	if (obj == 0 || materialObj == 0 || obj->ObjectType != Type_Material) {
		return 0;
	}

	if (!objectToObject3D(obj, &materialObj->obj3d)) {
		return 0;
	}

	materialObj->ambientColor.red = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->ambientColor.green = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->ambientColor.blue = *obj->Data;
	obj->Data += sizeof(Byte);

	materialObj->diffuseColor.rgb.red = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->diffuseColor.rgb.green = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->diffuseColor.rgb.blue = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->diffuseColor.alpha = *obj->Data;
	obj->Data += sizeof(Byte);

	materialObj->emissiveColor.red = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->emissiveColor.green = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->emissiveColor.blue = *obj->Data;
	obj->Data += sizeof(Byte);

	materialObj->specularColor.red = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->specularColor.green = *obj->Data;
	obj->Data += sizeof(Byte);
	materialObj->specularColor.blue = *obj->Data;
	obj->Data += sizeof(Byte);

	materialObj->shininess = readFloat32FromArray(obj->Data);
	obj->Data += sizeof(Float32);

	materialObj->vertexColorTrackingEnabled = *obj->Data;
	obj->Data += sizeof(Boolean);

	return 1;
}

Boolean objectToVertexArray(struct m3g_object* obj,
		struct m3g_vertex_array* vertexArray) {

	if (obj == 0 || vertexArray == 0 || obj->ObjectType != Type_VertexArray) {
		return 0;
	}

	if (!objectToObject3D(obj, &vertexArray->obj3d)) {
		return 0;
	}

	vertexArray->componentSize = *obj->Data;
	obj->Data += sizeof(Byte);
	vertexArray->componentCount = *obj->Data;
	obj->Data += sizeof(Byte);
	vertexArray->encoding = *obj->Data;
	obj->Data += sizeof(Byte);

	vertexArray->vertexCount = readUInt16FromArray(obj->Data);
	obj->Data += sizeof(UInt16);

	vertexArray->vertexData = obj->Data;

	return 1;
}
