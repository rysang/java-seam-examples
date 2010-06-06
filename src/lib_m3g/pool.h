/*
 * pool.h
 *
 *  Created on: 2 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g_types.h"
#ifndef POOL_H_
#define POOL_H_

typedef void* pool_t;
typedef void* pool_seg_t;
typedef unsigned long pool_size_t;

//Rudimentary pool system, more like a reusage memory system.
pool_t p_create_pool();
pool_t p_create_pool_size(UInt32 size);
pool_seg_t p_alloc(pool_t pool, pool_size_t size);
pool_t p_reset(pool_t pool);
pool_seg_t p_reset_and_alloc(pool_t pool, pool_size_t size);
void p_destroy_pool(pool_t pool);

#endif /* POOL_H_ */
