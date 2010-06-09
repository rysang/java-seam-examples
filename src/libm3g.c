/*
 ============================================================================
 Name        : libm3g.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include "lib_m3g.h"

int main(void) {

	struct m3g_input_stream* is = m3g_createFileInputStream(
			"f:\\libm3g\\m3g_models\\monkey_step1.m3g");

	pool_t pool = p_create_pool_size(1000000);
	printf("Created input stream \n");
	struct m3g_object_converter* converter = m3g_createConverter(pool);

	int i;
	Byte magic[13];
	for (i = 0; i < sizeof(m3g_FileIdentifier) / sizeof(Byte); i++) {
		magic[i] = is->m_readNext(is);
	}
	magic[12] = '\0';
	printf("%s\n", magic);

	struct m3g_section_reader* reader = m3g_createSectionReader(is, pool);
	while (1) {
		struct m3g_section* section = reader->readNextSection(reader, pool);
		if (section != 0) {
			struct m3g_object_reader* objReader = m3g_createObjectReader(
					section, pool);
			UInt32 object_count = 0;
			while (1) {

				struct m3g_object m3g_o;
				struct m3g_header m3g_h;
				struct m3g_external_ref m3g_ref;
				struct m3g_mesh m3g_m;
				struct m3g_camera m3g_cam;
				struct m3g_morphing_mesh m3g_mm;
				struct m3g_light m3g_l;
				struct m3g_material m3g_mat;

				if (objReader->readNextObject(objReader, &m3g_o)) {
					printf("Object Index: %i\n", object_count);
					object_count++;
					printf("Object Type: %i\n", m3g_o.ObjectType);
					printf("Length of Object: %i\n", m3g_o.Length);
					printf("------------------------------\n");

					memset(&m3g_m, '\0', sizeof(m3g_m));
					if (converter->toHeader(&m3g_o, &m3g_h)) {
						printf("----------Header------------\n");
						printf("Version: %x\n", (UInt16) *(m3g_h.VersionNumber));
						printf("Authoring: %s\n", m3g_h.AuthoringField);
						printf("File Size: %i\n", m3g_h.TotalFileSize);
						printf("ApproximateContentSize: %i\n",
								m3g_h.ApproximateContentSize);
						printf("Has External Ref: %i\n",
								m3g_h.hasExternalReferences);
						printf("----------End Header------------\n");

					} else if (converter->toExternalRef(&m3g_o, &m3g_ref)) {
						printf("---------External Reference ----------\n");
						printf("External Ref URI: %s\n", m3g_ref.URI);
						printf("-------End External Reference --------\n");

					} else if (converter->toMesh(&m3g_o, &m3g_m)) {
						printf("---------MESH 3D ----------\n");
						printf("Submeshes Count: %i\n", m3g_m.submeshCount);
						struct m3g_submesh_reader *sb_reader =
								m3g_createSubMeshReader(&m3g_m, pool);
						struct m3g_submesh_data sb_data;
						sb_reader->readNextSubMesh(sb_reader, &sb_data);
						printf("%i %i\n", sb_data.indexBuffer,
								sb_data.appearance);
						printf("-------End OBJECT 3D --------\n");
					}

					else if (converter->toCamera(&m3g_o, &m3g_cam)) {
						printf("---------CAMERA 3D ----------\n");
						printf("Camera Ptr: %x\n", &m3g_cam);
						printf("Camera Far: %.02f\n", m3g_cam.far);
						printf("Camera AspectRatio: %.02f\n",
								m3g_cam.AspectRatio);
						printf("Camera fovy: %.02f\n", m3g_cam.fovy);
						printf("Camera near: %.02f\n", m3g_cam.near);
						printf("-------End OBJECT 3D --------\n");
					}

					else if (converter->toMorphMesh(&m3g_o, &m3g_mm)) {
						printf("---------Morph Mesh 3D ----------\n");
						printf("Morph Count: %i\n", m3g_mm.morphTargetCount);
						printf("-------End OBJECT 3D --------\n");
					}

					else if (converter->toLight(&m3g_o, &m3g_l)) {
						printf("---------Light 3D ----------\n");
						printf("Color: %i %i %i\n", m3g_l.color.red,
								m3g_l.color.green, m3g_l.color.blue);
						printf("Intensity: %.02f\n", m3g_l.intensity);
						printf("Spot Angle: %.02f\n", m3g_l.spotAngle);
						printf("Spot Exponent: %.02f\n", m3g_l.spotExponent);
						printf("attenuationQuadratic: %.02f\n",
								m3g_l.attenuationQuadratic);
						printf("attenuationLinear: %.02f\n",
								m3g_l.attenuationLinear);
						printf("attenuationConstant: %.02f\n",
								m3g_l.attenuationConstant);
						if (m3g_l.nodeObj.transfObj.hasComponentTransform) {
							printf("X: %.02f\n",
									m3g_l.nodeObj.transfObj.orientationAxis.x);
							printf("Y: %.02f\n",
									m3g_l.nodeObj.transfObj.orientationAxis.y);
							printf("Z: %.02f\n",
									m3g_l.nodeObj.transfObj.orientationAxis.z);
						} else {
							UInt32 i;
							for (i = 0; i < 16; i++) {
								printf(
										"Matrix %i: %.02f\n",
										i,
										m3g_l.nodeObj.transfObj.transform.elements[i]);
							}
						}
						printf("-------End OBJECT 3D --------\n");
					} else if (converter->toMaterial(&m3g_o, &m3g_mat)) {
						printf("---------Material 3D ----------\n");
						printf("shininess: %.02f\n", m3g_mat.shininess);
						printf("vertexColorTrackingEnabled: %.02f\n",
								m3g_mat.vertexColorTrackingEnabled);
						printf("ambientColor: %i %i %i\n",
								m3g_mat.ambientColor.red,
								m3g_mat.ambientColor.green,
								m3g_mat.ambientColor.blue);
						printf("diffuseColor: %i %i %i %i\n",
								m3g_mat.diffuseColor.rgb.red,
								m3g_mat.diffuseColor.rgb.green,
								m3g_mat.diffuseColor.rgb.blue,
								m3g_mat.diffuseColor.alpha);
						printf("emissiveColor: %i %i %i\n",
								m3g_mat.emissiveColor.red,
								m3g_mat.emissiveColor.green,
								m3g_mat.emissiveColor.blue);
						printf("specularColor: %i %i %i\n",
								m3g_mat.specularColor.red,
								m3g_mat.specularColor.green,
								m3g_mat.specularColor.blue);
						printf("-------End OBJECT 3D --------\n");
					}

				} else {
					break;
				}
			}

			continue;
		}

		break;
	}

	m3g_destroyFileInputStream(is);
	printf("Inainte de distrugere.\n");
	p_destroy_pool(pool);
	return EXIT_SUCCESS;
}
