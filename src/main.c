#include "gotpl/gotpl_io.h"
#include <stdio.h>

int main() {

	gotpl_input_stream is;
	gotpl_create_std_input_stream(&is, "test.txt", gotpl_enc_utf8);

	gotpl_i count;
	while (is.has_more(&is)) {
		count = is.read(&is);
		printf("%i", count);
	}
	return 0;
}
