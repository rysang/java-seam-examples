#include <GL/glut.h>

#ifndef OGLINIT_H_
#define OGLINIT_H_

class oglInit {
public:
	oglInit(int argc, char** argv);
	virtual ~oglInit();

	virtual void goMain();
protected:
	virtual void init();
};

#endif
