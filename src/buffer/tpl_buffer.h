/*
 * tpl_buffer.h
 *
 *  Created on: 25 Oct 2010
 *      Author: id821436
 */

#include <stdio.h>
#include <stdlib.h>

#ifndef TPL_BUFFER_H_
#define TPL_BUFFER_H_

#define tpl_alloc malloc
#define tpl_free free
#define tpl_copy memcpy

#ifdef __GNUC__
#define likely(x)       __builtin_expect((x),1)
#define unlikely(x)     __builtin_expect((x),0)
#else
#define likely
#define unlikely
#endif

#define MEMORY_AVAILABILITY 2

typedef unsigned char tpl_byte_t;

typedef short tpl_int16_t;
typedef unsigned short tpl_uint16_t;

typedef int tpl_int32_t;
typedef unsigned int tpl_uint32_t;

typedef long tpl_int64_t;
typedef unsigned long tpl_uint64_t;

typedef const char* tpl_string_const;
typedef char* tpl_string;

typedef char* tpl_ptr;

typedef struct {
	tpl_int32_t size;
	tpl_int32_t count;
	tpl_ptr ptr;
} tpl_buffer_t;

tpl_buffer_t* tpl_buffer_create(tpl_int32_t initial_size);
tpl_int32_t tpl_buffer_write(tpl_buffer_t* buffer, tpl_ptr ptr,
		tpl_int32_t size);
tpl_ptr tpl_buffer_get(tpl_buffer_t* buffer);
void tpl_buffer_clear(tpl_buffer_t* buffer);
tpl_int32_t tpl_buffer_get_count(tpl_buffer_t* buffer);
void tpl_buffer_destroy(tpl_buffer_t* buffer);

#endif /* TPL_BUFFER_H_ */

