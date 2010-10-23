/*
 * libtemplate.c
 *
 *  Created on: Oct 22, 2010
 *      Author: price
 */

#include "include/libctempl.h"

tpl_template* compile_template(tpl_string json_string, apr_pool_t* mpool) {
	tpl_template *template = apr_palloc(mpool, sizeof(tpl_template));
	template->mem_pool = mpool;
	template->root = apr_hash_make(mpool);

	return template;
}
