/*
 * pool.c
 *
 *  Created on: 2 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g/pool.h"
#include <memory.h>
#include <stdlib.h>

//1024 bytes
#define INTIAL_POOL_SIZE 1024
#define MULTIPLY_FACTOR 4

struct __pool_struct_ {
	void * memory_addr;
	pool_size_t capacity;
	pool_size_t occupied;
};

pool_t p_create_pool() {
	struct __pool_struct_* pool = malloc(sizeof(struct __pool_struct_));
	pool->memory_addr = malloc(INTIAL_POOL_SIZE);
	pool->capacity = INTIAL_POOL_SIZE;
	pool->occupied = 0;

	return pool;
}

pool_t p_create_pool_size(UInt32 size) {
	struct __pool_struct_* pool = malloc(sizeof(struct __pool_struct_));
	pool->memory_addr = malloc(size);
	pool->capacity = size;
	pool->occupied = 0;

	return pool;
}

pool_seg_t p_reset_and_alloc(pool_t pool, pool_size_t size) {
	return p_alloc(p_reset(pool), size);
}

pool_seg_t p_alloc(pool_t pool, pool_size_t size) {
	struct __pool_struct_* p = (struct __pool_struct_*) pool;
	pool_seg_t ret = 0;
	if (size < (p->capacity - p->occupied)) {
		ret = p->memory_addr + p->occupied;
		p->occupied += size;

	}
	return ret;
}

pool_t p_reset(pool_t pool) {
	struct __pool_struct_* p = (struct __pool_struct_*) pool;
	p->occupied = 0;
	return pool;
}

void p_destroy_pool(pool_t pool) {
	struct __pool_struct_* p = (struct __pool_struct_*) pool;
	if (p != 0) {
		if (p->memory_addr != 0) {
			free(p->memory_addr);
			p->memory_addr = 0;
		}

		free(p);
	}
}
