#include "oglPool.h"

struct pool_unit {
	GLuint size;
	GLuint allocated;
	GLubyte* address;
	pool_unit* next;
	pool_unit* prev;
};

static pool_unit* pool_create_unit(GLuint chunk_size) {
	pool_unit* unit = (pool_unit*) malloc(sizeof(pool_unit) + chunk_size);
	if (unit) {

		unit->size = chunk_size;
		unit->allocated = 0;
		unit->prev = unit->next = 0;
		unit->address = ((GLubyte*) unit) + sizeof(pool_unit);
	}

	return unit;
}

static void pool_destroy_unit(pool_unit* unit) {
	free((void*) unit);
}

oglPool::oglPool() :
	chunkSize(1024), destroyed(0) {
	this->first = this->last = this->current = pool_create_unit(chunkSize);
}

oglPool::oglPool(GLuint initialSize) :
	chunkSize(initialSize), destroyed(0) {
	this->first = this->last = this->current = pool_create_unit(chunkSize);
}

void oglPool::setChunkSize(GLuint chunkSize) {
	this->chunkSize = chunkSize;
}

void* oglPool::alloc(GLuint size) {
	if (this->current) {

		if ((this->current->size - this->current->allocated) >= size) {
			GLubyte* ret = this->current->address + this->current->allocated;
			this->current->allocated += size;
			return ret;

		} else {

			pool_unit* currentUnit = this->first;
			while (currentUnit) {

				if ((currentUnit->size - currentUnit->allocated) > size) {

					GLubyte* ret = currentUnit->address
							+ currentUnit->allocated;
					currentUnit->allocated += size;
					this->current = currentUnit;
					return ret;

				}

				currentUnit = currentUnit->next;
			}

			pool_unit* newUnit;
			this->last->next = newUnit = pool_create_unit(
					(this->chunkSize > size) ? this->chunkSize : size);
			if (!newUnit) {
				return 0;
			}

			newUnit->size = (this->chunkSize > size) ? this->chunkSize : size;
			newUnit->allocated += size;
			newUnit->prev = this->last;

			this->last = newUnit;
			this->current = newUnit;
			return newUnit->address;
		}

	}

	return 0;
}

void oglPool::clear() {
	pool_unit* currentUnit = this->first;
	while (currentUnit) {
		currentUnit->allocated = 0;
		currentUnit = currentUnit->next;
	}

	this->current = this->first;
}

void oglPool::destroy() {
	if (!destroyed) {
		pool_unit* currentUnit = this->last;
		while (currentUnit) {
			pool_unit* prev = currentUnit->prev;
			pool_destroy_unit(currentUnit);
			currentUnit = prev;
		}

		this->first = this->current = this->last = 0;
		destroyed = 1;
	}
}

oglPool::~oglPool() {
	this->destroy();
}
