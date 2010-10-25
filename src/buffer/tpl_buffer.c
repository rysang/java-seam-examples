/*
 * tpl_buffer.c
 *
 *  Created on: 25 Oct 2010
 *      Author: id821436
 */

#include "tpl_buffer.h"

tpl_buffer_t* tpl_buffer_create(tpl_int32_t initial_size) {
	tpl_buffer_t * ret = tpl_alloc(sizeof(tpl_buffer_t));

	if (likely(ret!=0)) {
		ret->ptr = tpl_alloc(initial_size);
		if (likely(ret->ptr !=0)) {
			ret->count = 0;
			ret->size = initial_size;
			return ret;
		}

		tpl_free(ret);
	}
	return 0;
}

tpl_int32_t tpl_buffer_write(tpl_buffer_t* buffer, tpl_ptr ptr,
		tpl_int32_t size) {
	if (likely((buffer->count + size) < buffer->size)) {
		tpl_copy(buffer->ptr + buffer->count, ptr, size);
		buffer->count += size;
		return size;
	}

	tpl_ptr tmp = buffer->ptr;
	buffer->ptr = tpl_alloc(buffer->size + MEMORY_AVAILABILITY * size);
	if (unlikely(buffer->ptr != 0)) {
		buffer->ptr = tmp;
		return -1;
	}

	buffer->size += (MEMORY_AVAILABILITY * size);
	tpl_copy(buffer->ptr, tmp, buffer->count);
	tpl_copy(buffer->ptr + buffer->count, ptr, size);
	tpl_free(tmp);

	buffer->count += size;
	return size;
}

tpl_int32_t tpl_buffer_get_count(tpl_buffer_t* buffer) {
	return buffer->count;
}

tpl_ptr tpl_buffer_get(tpl_buffer_t* buffer) {
	return buffer->ptr;
}

void tpl_buffer_clear(tpl_buffer_t* buffer) {
	buffer->count = 0;
}

void tpl_buffer_destroy(tpl_buffer_t* buffer) {
	if (unlikely( buffer != 0)) {
		tpl_free(buffer->ptr);
		tpl_free(buffer);
	}
}
