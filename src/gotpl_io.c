#include "gotpl/gotpl_io.h"
#include <stdio.h>

static gotpl_i os_write(gotpl_output_stream* os, gotpl_void* buff,
		gotpl_ui length);
static gotpl_void os_flush(gotpl_output_stream* os);
static gotpl_void os_close(gotpl_output_stream* os);

static gotpl_i is_read(gotpl_input_stream* is);
static gotpl_i is_read_binary(gotpl_input_stream* is);
static gotpl_i is_read_utf8(gotpl_input_stream* is);
static gotpl_bool has_more(gotpl_input_stream* is);
static gotpl_void is_close(gotpl_input_stream* is);

gotpl_bool gotpl_create_std_output_stream(gotpl_output_stream* os,
		gotpl_i8 * path) {
	if (os != 0) {
		os->_private = (gotpl_ui*) fopen(path, "wb");
		if (os->_private) {
			os->last_error_code = 0;
			os->write = os_write;
			os->flush = os_flush;
			os->close = os_close;
			return gotpl_true;
		}

		os->last_error_code = -1;
		return gotpl_false;
	}
	return gotpl_false;
}

static gotpl_i os_write(gotpl_output_stream* os, gotpl_void* buff,
		gotpl_ui length) {
	if (os != 0) {
		return fwrite(buff, length, 1, (FILE*) os->_private);
	}

	return -1;
}

static gotpl_void os_flush(gotpl_output_stream* os) {
	if (os != 0) {
		fflush((FILE*) os->_private);
		return;
	}
}

static gotpl_void os_close(gotpl_output_stream* os) {
	if (os != 0) {
		fclose((FILE*) os->_private);
	}
}

// Input stream stuff

gotpl_bool gotpl_create_std_input_stream(gotpl_input_stream* is,
		gotpl_i8 * path, gotpl_encoding encoding) {
	if (is == 0) {
		return gotpl_false;
	}

	is->_private = (gotpl_ui*) fopen(path, "rb");
	if (is->_private) {
		is->last_error_code = 0;
		is->read = is_read;
		is->has_more = has_more;
		is->close = is_close;
		is->current_encoding = encoding;
		return gotpl_true;
	}

	is->last_error_code = -1;
	return gotpl_false;

}

static gotpl_i is_read(gotpl_input_stream* is) {
	if (is != 0) {
		switch (is->current_encoding) {
		case gotpl_enc_binary:
			return is_read_binary(is);
		case gotpl_enc_utf8:
			return is_read_utf8(is);
		}

	}

	is->last_error_code = -1;
	return -1;
}

static gotpl_bool has_more(gotpl_input_stream* is) {
	if (is == 0) {
		return gotpl_true;
	}
	return !feof((FILE*) is->_private);
}

static gotpl_void is_close(gotpl_input_stream* is) {
	if (is != 0) {
		fclose((FILE*) is->_private);
	}
}

static gotpl_i is_read_binary(gotpl_input_stream* is) {
	if (is != 0) {
		return fread(&is->current_char.m8[0], sizeof(is->current_char.m8[0]),
				1, (FILE*) is->_private);
	}

	return 0;
}
static gotpl_i is_read_utf8(gotpl_input_stream* is) {
	if (is == 0) {
		is->last_error_code = -1;
		return 0;
	}

	gotpl_i c;
	FILE* f = (FILE*) is->_private;
	fread(&c, sizeof(gotpl_i8), 1, f);

	is->current_char.m8[0] = c;

	if ((c & 0x80) == 0) {
		is->last_error_code = 0;
		return 1;
	}

	if ((c & 0xE0) == 0xC0) {
		gotpl_i c1;
		fread(&c1, sizeof(gotpl_i8), 1, f);

		if (c1 < 0) {
			is->last_error_code = -1;
			return 0;
		}

		is->current_char.m8[1] = c1;
		is->last_error_code = 0;
		return 2;
	}

	if ((c & 0xF0) == 0xE0) {
		gotpl_i c1, c2;
		fread(&c1, sizeof(gotpl_i8), 1, f);
		fread(&c2, sizeof(gotpl_i8), 1, f);

		if (c1 < 0 || c2 < 0) {
			is->last_error_code = -1;
			return 0;
		}

		is->current_char.m8[1] = c1;
		is->current_char.m8[2] = c2;
		is->last_error_code = 0;
		return 3;
	}

	if ((c & 0xF8) == 0xF0) {
		gotpl_i c1, c2, c3;
		fread(&c1, sizeof(gotpl_i8), 1, f);
		fread(&c2, sizeof(gotpl_i8), 1, f);
		fread(&c3, sizeof(gotpl_i8), 1, f);

		if (c1 < 0 || c2 < 0 || c3 < 0) {
			is->last_error_code = -1;
			return 0;
		}

		is->current_char.m8[1] = c1;
		is->current_char.m8[2] = c2;
		is->current_char.m8[3] = c3;
		is->last_error_code = 0;
		return 4;
	}

	is->last_error_code = -1;
	return 0;
}
