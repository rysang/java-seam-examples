#include "gotpl/gotpl_io.h"
#include "gotpl/gotpl_pool.h"
#include "gotpl/gotpl_object_array.h"
#include "gotpl/gotpl_object_list.h"
#include "3rd_party/super_fast_hash.h"
#include "gotpl/gotpl_object_map.h"
#include <stdio.h>

int main() {
	gotpl_input_stream is;
	if (!gotpl_create_std_input_stream(&is, "test.txt", gotpl_enc_utf8)) {
		GOTPL_ERROR("Failed to create stream.");
		return -1;
	}

	gotpl_i count;
	while (is.has_more(&is)) {
		count = is.read(&is);
		printf("%i", count);
	}
	printf("\r\n");
	is.close(&is);

	gotpl_pool* pool = 0;
	gotpl_pool_create(&pool, 1024);
	gotpl_pool_alloc(pool, 50);
	gotpl_pool_alloc(pool, 50);
	gotpl_pool_alloc(pool, 150);
	gotpl_pool_alloc(pool, 50);
	gotpl_pool_alloc(pool, 5);
	gotpl_pool_clear(pool);

	gotpl_object_list* list = gotpl_object_list_create(pool);
	gotpl_object obj;
	obj.o_type = gotpl_type_int;
	obj.o_value.v_i = 1000;

	gotpl_object_list_add(list, &obj);
	gotpl_object_list_add(list, &obj);
	gotpl_object_list_add(list, &obj);
	gotpl_object_list_add(list, &obj);
	printf("List size: %i \n", gotpl_object_list_length(list));
	gotpl_object_list_remove(list, 0);

	gotpl_object_map* map = gotpl_object_map_create(1024, pool);
	gotpl_object_map_put(map, "test", &obj);
	gotpl_object_map_put(map, "test1", &obj);
	gotpl_object_map_put(map, "test12", &obj);
	gotpl_object_map_put(map, "test123", &obj);
	gotpl_object_map_put(map, "test123sdkjfhjksdfhsdkfhsdjfhsdjkfdjsfhsjkdfsdjkf", &obj);
	gotpl_object_map_remove(map, "test");
	gotpl_object_map_remove(map, "test");

	gotpl_pool_destroy(&pool);

	return 0;
}
