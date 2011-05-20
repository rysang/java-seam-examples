#include <GL/gl.h>
#include <stdio.h>
#include <stdlib.h>

#ifndef OGLPOOL_H_
#define OGLPOOL_H_

typedef struct pool_unit pool_unit;

class oglPool {
private:
	GLuint chunkSize;
	GLuint destroyed;
	pool_unit* first;
	pool_unit* last;
	pool_unit* current;
public:
	oglPool();
	oglPool(GLuint initialSize);

	void setChunkSize(GLuint chunkSize);
	void* alloc(GLuint size);
	void clear();
	void destroy();

	virtual ~oglPool();
};

#endif /* OGLPOOL_H_ */
