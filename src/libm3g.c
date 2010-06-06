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
			"E:\\libm3g\\m3g_models\\memory.m3g");

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
			while (1) {

				struct m3g_object m3g_o;
				struct m3g_header m3g_h;
				struct m3g_external_ref m3g_ref;
				struct m3g_object_3d m3g_obj3d;

				if (objReader->readNextObject(objReader, &m3g_o)) {
					printf("Object Type: %i\n", m3g_o.ObjectType);
					printf("Length of Object: %i\n", m3g_o.Length);
					printf("------------------------------\n");

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

					} else if (converter->toObject3D(&m3g_o, &m3g_obj3d)) {
						printf("---------OBJECT 3D ----------\n");
						printf("OBJ3D User ID: %i\n", m3g_obj3d.userID);
						printf("Animation Tracks Length: %i\n",
								m3g_obj3d.animationTracksLength);
						printf("userParameterCount: %i\n",
								m3g_obj3d.userParameterCount);
						if (m3g_obj3d.userParameterCount) {
							struct m3g_map_parameter_reader* preader =
									m3g_createMapParameterReader(&m3g_obj3d,
											pool);

							struct m3g_map_parameter param;
							while (preader->readNextMapParameter(preader,
									&param)) {
								printf("PARAM KEY: %i\n", param.parameterID);
								printf("PARAM VAL LEN: %i\n",
										param.parameterValueLength);
								printf("PARAM VAL : %s\n", param.parameterValue);
							}
						}
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
