#include "gotpl/gotpl_io.h"
#include "gotpl/gotpl_pool.h"
#include <stdio.h>

int main() {

	gotpl_input_stream is;
	gotpl_create_std_input_stream(&is, "test.txt", gotpl_enc_utf8);

	gotpl_i count;
	while (is.has_more(&is)) {
		count = is.read(&is);
		printf("%i", count);
	}
	printf("\r\n");
	is.close(&is);

	gotpl_pool* pool = 0;
	gotpl_pool_create(&pool, 100);
	gotpl_pool_alloc(pool, 50);
	gotpl_pool_alloc(pool, 50);
	gotpl_pool_alloc(pool, 150);
	gotpl_pool_alloc(pool, 50);
	gotpl_pool_alloc(pool, 5);
	gotpl_pool_clear(pool);
	gotpl_pool_destroy(&pool);

	return 0;
}
