/*
 * pool.h
 *
 *  Created on: 2 Jun 2010
 *      Author: id821436
 */

#ifndef POOL_H_
#define POOL_H_

typedef void* pool_t;
typedef void* pool_seg_t;
typedef unsigned long pool_size_t;

pool_t p_create_pool();
pool_seg_t p_alloc(pool_t pool, pool_size_t size);
pool_t p_reset(pool_t pool);
pool_seg_t p_reset_and_alloc(pool_t pool, pool_size_t size);
void p_destroy_pool(pool_t pool);

#endif /* POOL_H_ */
