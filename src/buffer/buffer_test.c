/*
 ============================================================================
 Name        : buffer_test.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "tpl_buffer.h"

int main(void) {
	tpl_buffer_t * buffer = tpl_buffer_create(100);
	const char c[100];
	tpl_buffer_write(buffer, c, sizeof(c));
	printf("%i\n", buffer->size);
	tpl_buffer_write(buffer, c, sizeof(c));
	tpl_buffer_write(buffer, c, sizeof(c));
	tpl_buffer_write(buffer, c, sizeof(c));
	tpl_buffer_write(buffer, c, sizeof(c));
	printf("%i\n", buffer->count);

	tpl_buffer_destroy(buffer);
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */
	return EXIT_SUCCESS;
}
