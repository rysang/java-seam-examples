/*
 * test_utf8.c
 *
 *  Created on: Oct 23, 2010
 *      Author: price
 */
#include <stdio.h>
#include <stdlib.h>
#include "utf8_decode.h"

int main() {
	FILE *f = fopen("/home/price/Desktop/decode", "rb");
	fseek(f, 0, SEEK_END);
	long size = ftell(f);
	fseek(f, 0, SEEK_SET);

	tpl_ptr text = malloc(size);
	fread(text, size, 1, f);
	fclose(f);

	utf8_source source;
	utf8_decode_init(text, size, &source);
	tpl_int32_t c = utf8_decode_next(&source);
	fprintf(stdout, "%c", c);
	c = utf8_decode_next(&source);
	fprintf(stdout, "%c", c);
	c = utf8_decode_next(&source);
	fprintf(stdout, "%c", c);

	fflush(stdout);
	return 0;
}
