/*
 * lib_m3g_objects3d.h
 *
 *  Created on: 3 Jun 2010
 *      Author: cpriceputu
 */

#ifndef LIB_M3G_OBJECTS3D_H_
#define LIB_M3G_OBJECTS3D_H_
#include "lib_m3g_protos.h"

typedef void* hash_map_t;
typedef void* mutable_t;
typedef void* data_ptr_t;

//Explicit objects
typedef Boolean (*m3g_toHeader)(struct m3g_object* obj,
		struct m3g_header* objHeader);
typedef Boolean (*m3g_toExternalRef)(struct m3g_object* obj,
		struct m3g_external_ref* extRef);
typedef Boolean (*m3g_toObject3D)(struct m3g_object* obj,
		struct m3g_object_3d* obj3d);
typedef Boolean (*m3g_toTransformable)(struct m3g_object* obj,
		struct m3g_transformable* transfObj);
typedef Boolean (*m3g_toNode)(struct m3g_object* obj, struct m3g_node* nodeObj);
typedef Boolean (*m3g_toMesh)(struct m3g_object* obj, struct m3g_mesh* meshObj);
typedef Boolean (*m3g_toCamera)(struct m3g_object* obj,
		struct m3g_camera* cameraObj);
typedef Boolean (*m3g_toMorphMesh)(struct m3g_object* obj,
		struct m3g_morphing_mesh* meshObj);
typedef Boolean (*m3g_toSkinnedMesh)(struct m3g_object* obj,
		struct m3g_skinned_mesh* meshObj);
typedef Boolean (*m3g_toLight)(struct m3g_object* obj,
		struct m3g_light* lightObj);
typedef Boolean (*m3g_toMaterial)(struct m3g_object* obj,
		struct m3g_material* materialObj);
typedef Boolean (*m3g_toVertexArray)(struct m3g_object* obj,
		struct m3g_vertex_array* vertexArray);

struct m3g_object_converter {
	m3g_toHeader toHeader;
	m3g_toExternalRef toExternalRef;
	m3g_toMesh toMesh;
	m3g_toObject3D toObject3D;
	m3g_toTransformable toTransformable;
	m3g_toNode toNode;
	m3g_toCamera toCamera;
	m3g_toMorphMesh toMorphMesh;
	m3g_toSkinnedMesh toSkinnedMesh;
	m3g_toLight toLight;
	m3g_toMaterial toMaterial;
	m3g_toVertexArray toVertexArray;
};

struct m3g_object_converter* m3g_createConverter(pool_t pool);

//Magic file id
const static Byte m3g_FileIdentifier[12] = { '«', 'J', 'S', 'R', '1', '8', '4',
		'»', '\r', '\n', '\x1A', '\n' };

enum m3g_CameraProjectionType {
	ProjectionType_GENERIC = 48,
	ProjectionType_PARALLEL = 49,
	ProjectionType_PERSPECTIVE = 50
};

enum m3g_ObjectType {
	Type_HeaderObject = 0,
	Type_AnimationController = 1,
	Type_AnimationTrack = 2,
	Type_Appearance = 3,
	Type_Background = 4,
	Type_Camera = 5,
	Type_CompositingMode = 6,
	Type_Fog = 7,
	Type_PolygonMode = 8,
	Type_Group = 9,
	Type_Image2D = 10,
	Type_TriangleStripArray = 11,
	Type_Light = 12,
	Type_Material = 13,
	Type_Mesh = 14,
	Type_MorphingMesh = 15,
	Type_SkinnedMesh = 16,
	Type_Texture2D = 17,
	Type_Sprite = 18,
	Type_KeyframeSequence = 19,
	Type_VertexArray = 20,
	Type_VertexBuffer = 21,
	Type_World = 22,
	Type_ExternalReference = 255
};

struct m3g_header {
	Byte VersionNumber[2];
	Boolean hasExternalReferences;
	UInt32 TotalFileSize;
	UInt32 ApproximateContentSize;
	String AuthoringField;
};

struct m3g_external_ref {
	String URI;
};

//OBJECT_3D
typedef Boolean (*m3g_readNextMapParameter)(
		struct m3g_map_parameter_reader* reader,
		struct m3g_map_parameter* parameter);

struct m3g_object_3d {
	UInt32 userID;
	UInt32 animationTracksLength;
	m3g_object_index* animationTracks;
	UInt32 userParameterCount;
	hash_map_t parameterMap;
};

struct m3g_map_parameter {
	UInt32 parameterID;
	UInt32 parameterValueLength;
	Byte* parameterValue;
};

struct m3g_map_parameter_reader {
	hash_map_t parameterMap;
	UInt32 currentIndex;
	UInt32 count;
	m3g_readNextMapParameter readNextMapParameter;
};

struct m3g_map_parameter_reader* m3g_createMapParameterReader(
		struct m3g_object_3d* object3d, pool_t pool);

struct m3g_transformable {
	struct m3g_object_3d obj3d;
	Boolean hasComponentTransform;

	//IF hasComponentTransform==TRUE, THEN
	struct m3g_vector_3d translation;
	struct m3g_vector_3d scale;
	Float32 orientationAngle;
	struct m3g_vector_3d orientationAxis;
	//END

	Boolean hasGeneralTransform;

	//IF hasGeneralTransform==TRUE, THEN

	struct m3g_matrix transform;

//END
};

struct m3g_node {
	struct m3g_transformable transfObj;

	Boolean enableRendering;
	Boolean enablePicking;
	Byte alphaFactor;
	UInt32 scope;

	Boolean hasAlignment;

	//IF hasAlignment==TRUE, THEN

	Byte zTarget;
	Byte yTarget;

	m3g_object_index zReference;
	m3g_object_index yReference;

//END
};

struct m3g_mesh {
	struct m3g_node nodeObj;
	m3g_object_index vertexBuffer;
	UInt32 submeshCount;
	data_ptr_t submeshData;
};

typedef Boolean (*m3g_readNextSubMesh)(struct m3g_submesh_reader* reader,
		struct m3g_submesh_data* subMData);

struct m3g_submesh_data {
	m3g_object_index indexBuffer;
	m3g_object_index appearance;
};

struct m3g_submesh_reader {
	data_ptr_t submeshData;
	UInt32 currentIndex;
	UInt32 count;
	m3g_readNextSubMesh readNextSubMesh;
};

struct m3g_submesh_reader* m3g_createSubMeshReader(struct m3g_mesh* mesh,
		pool_t pool);

struct m3g_camera {
	struct m3g_node nodeObj;
	Byte projectionType;

	//IF projectionType==GENERIC, THEN

	struct m3g_matrix projectionMatrix;

	//ELSE

	Float32 fovy;
	Float32 AspectRatio;
	Float32 near;
	Float32 far;

//END
};

typedef Boolean (*m3g_readNextMorphTarget)(
		struct m3g_morph_target_reader* reader,
		struct m3g_morph_target_data* morphTargetData);

struct m3g_morphing_mesh {
	struct m3g_mesh meshObj;

	UInt32 morphTargetCount;
	data_ptr_t morphTargetData;
};

struct m3g_morph_target_reader {
	data_ptr_t morphTargetData;
	UInt32 currentIndex;
	UInt32 count;
	m3g_readNextMorphTarget readNextMorphTarget;
};

struct m3g_morph_target_data {
	m3g_object_index morphTarget;
	Float32 initialWeight;
};

struct m3g_morph_target_reader* m3g_createMorphTargetReader(
		struct m3g_morphing_mesh* mesh, pool_t pool);

typedef Boolean (*m3g_readNextTransfRef)(struct m3g_transf_ref_reader* reader,
		struct m3g_transf_ref_data* transfRefData);

struct m3g_skinned_mesh {
	struct m3g_mesh meshObj;

	m3g_object_index skeleton;
	UInt32 transformReferenceCount;
	data_ptr_t transfRefData;
};

struct m3g_transf_ref_reader {
	data_ptr_t transfRefData;
	UInt32 currentIndex;
	UInt32 count;
	m3g_readNextTransfRef readNextTransfRef;
};

struct m3g_transf_ref_data {
	m3g_object_index transformNode;
	UInt32 firstVertex;
	UInt32 vertexCount;
	Int32 weight;
};

struct m3g_transf_ref_reader* m3g_createTransfRefReader(
		struct m3g_skinned_mesh* mesh, pool_t pool);

struct m3g_light {
	struct m3g_node nodeObj;
	Float32 attenuationConstant;
	Float32 attenuationLinear;
	Float32 attenuationQuadratic;
	struct m3g_color_rgb color;
	Byte mode;
	Float32 intensity;
	Float32 spotAngle;
	Float32 spotExponent;
};

struct m3g_material {
	struct m3g_object_3d obj3d;
	struct m3g_color_rgb ambientColor;
	struct m3g_color_rgba diffuseColor;
	struct m3g_color_rgb emissiveColor;
	struct m3g_color_rgb specularColor;
	Float32 shininess;
	Boolean vertexColorTrackingEnabled;
};

//Vertex Arrray.
typedef Boolean (*m3g_readNextVertex)(struct m3g_vertex_reader* reader,
		struct m3g_vertex_data* vertexData);

struct m3g_vertex_array {
	struct m3g_object_3d obj3d;

	Byte componentSize;
	Byte componentCount;
	Byte encoding;

	UInt16 vertexCount;
	data_ptr_t vertexData;
};

struct m3g_vertex_reader {
	data_ptr_t vertexData;
	UInt32 currentIndex;
	UInt32 count;
	Byte size;
	Byte encoding;
	Byte componentCount;
	m3g_readNextVertex readNextVertex;
};

struct m3g_vertex_data {
	UInt32 count;
	UInt32 size;
	//IF encoding==0, THEN
	Byte* components;
	//ELSE IF encoding==1, THEN
	Byte* componentDeltas;
//END
};

struct m3g_vertex_reader* m3g_createVertexArrayDataReader(
		struct m3g_vertex_array* vArray, pool_t pool);

#endif /* LIB_M3G_OBJECTS3D_H_ */
