/*
 * libctempl.h
 *
 *  Created on: Oct 22, 2010
 *      Author: price
 */

#include <apr/apr.h>
#include <apr/apr_general.h>
#include <apr/apr_hash.h>
#include <apr/apr_strings.h>

#ifndef LIBCTEMPL_H_
#define LIBCTEMPL_H_

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

typedef enum {
	tpl_type_number,
	tpl_type_string,
	tpl_type_array,
	tpl_type_map,
	tpl_type_boolean
} tpl_object_type;

typedef struct {
	tpl_object_type obj_type;
	tpl_string name;
	tpl_ptr obj_data;
} tpl_object_model;

typedef struct {
	apr_pool_t *mem_pool;
	apr_hash_t *root;
	tpl_uint32_t is_compiled;
} tpl_template;

typedef struct tpl_destination* tpl_destination_ptr;

typedef tpl_uint32_t (*write_ptr)(tpl_ptr dest_buffer, tpl_uint32_t length,
		tpl_destination_ptr dest);
typedef tpl_uint32_t (*close_ptr)(tpl_destination_ptr dest);

typedef struct {
	tpl_ptr param;
	write_ptr write;
	close_ptr close;
} tpl_destination;

tpl_ptr tpl_initialize();
void tpl_terminate();

tpl_template* compile_template(tpl_string json_string, apr_pool_t* mpool);
tpl_uint32_t execute_template(tpl_string json_string, tpl_destination* dest);

#endif /* LIBCTEMPL_H_ */
