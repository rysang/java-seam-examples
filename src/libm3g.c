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
#include "GL/gl.h"
#include "GL/glext.h"
#include "GL/glu.h"
#include "../../opengl/glut/glut-3.7.6-bin/glut.h"

void glTest();

struct m3g_input_stream* is;
pool_t pool;
pool_t sectionsPool;
Boolean firstTime = 1;
struct m3g_object_converter * converter;

static float angle = 0.0, ratio;
static float x = 0.0f, y = 1.75f, z = 5.0f;
static float lx = 0.0f, ly = 0.0f, lz = -1.0f;

void renderScene(void) {
	if (firstTime) {
		is = m3g_createFileInputStream(
				"f:\\libm3g\\m3g_models\\monkey_step1.m3g");

		pool = p_create_pool_size(1000000);
		sectionsPool = p_create_pool_size(1000000);

		printf("Created input stream \n");
		converter = m3g_createConverter(pool);

		firstTime = 0;
	}

	is->m_reset(is);
	p_reset(sectionsPool);

	glClear(GL_COLOR_BUFFER_BIT);
	glBegin(GL_TRIANGLES);

	glVertex3f(-0.5, -0.5, 0.0);
	glVertex3f(0.5, 0.0, 0.0);
	glVertex3f(0.0, 0.5, 0.0);

	int i;
	Byte magic[13];
	for (i = 0; i < sizeof(m3g_FileIdentifier) / sizeof(Byte); i++) {
		magic[i] = is->m_readNext(is);
	}
	magic[12] = '\0';
	printf("%s\n", magic);

	struct m3g_section_reader* reader = m3g_createSectionReader(is,
			sectionsPool);
	while (1) {
		struct m3g_section* section = reader->readNextSection(reader,
				sectionsPool);
		if (section != 0) {
			struct m3g_object_reader* objReader = m3g_createObjectReader(
					section, sectionsPool);
			UInt32 object_count = 0;
			while (1) {

				struct m3g_object m3g_o;
				struct m3g_vertex_array m3g_varr;
				struct m3g_camera m3g_cam;

				if (objReader->readNextObject(objReader, &m3g_o)) {
					printf("Object Index: %i\n", object_count);
					object_count++;
					printf("Object Type: %i\n", m3g_o.ObjectType);
					printf("Length of Object: %i\n", m3g_o.Length);
					printf("------------------------------\n");

					if (converter->toVertexArray(&m3g_o, &m3g_varr)) {
						printf("---------Vertex Array----------\n");
						printf("componentCount: %i\n", m3g_varr.componentCount);
						printf("componentSize: %i\n", m3g_varr.componentSize);
						printf("vertexCount: %i\n", m3g_varr.vertexCount);
						printf("encoding: %i\n", m3g_varr.encoding);

						if (m3g_varr.componentSize == 2) {
							struct m3g_vertex_reader* vReader =
									m3g_createVertexArrayDataReader(&m3g_varr,
											sectionsPool);
							struct m3g_vertex_data vData;

							while (vReader->readNextVertex(vReader, &vData)) {
								UInt16* cmps = (UInt16*) vData.components;
								printf("%i %i %i\n", cmps[0], cmps[1], cmps[2]);
								glVertex3f(cmps[0], cmps[1], cmps[2]);
							}

						}

						printf("-------End OBJECT 3D --------\n");
					}

					else if (converter->toCamera(&m3g_o, &m3g_cam)) {
						//m3g_cam.

						gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0f, 1.0f,
								0.0f);

					}

				} else {
					break;
				}
			}

			continue;
		}

		break;
	}

	glEnd();
	glFlush();
}

void glTest(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_SINGLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(320, 320);
	glutCreateWindow("3D Tech- GLUT Tutorial");
	glutDisplayFunc(renderScene);
	glutMainLoop();

}

int main(int argc, char** argv) {

	glTest(argc, argv);

	m3g_destroyFileInputStream(is);
	printf("Inainte de distrugere.\n");
	p_destroy_pool(pool);
	return EXIT_SUCCESS;
}
