#include "gotpl.h"

typedef struct gotpl_input_stream gotpl_input_stream;
typedef struct gotpl_output_stream gotpl_output_stream;

typedef gotpl_i (* gotpl_is_read_t)(gotpl_input_stream* is);
typedef gotpl_bool (* gotpl_is_has_more_t)(gotpl_input_stream* is);
typedef gotpl_void (* gotpl_is_close_t)(gotpl_input_stream* is);

typedef gotpl_i (* gotpl_os_write_t)(gotpl_output_stream* os, gotpl_void* buff,
		gotpl_ui length);
typedef gotpl_void (* gotpl_os_flush_t)(gotpl_output_stream* os);
typedef gotpl_void (* gotpl_os_close_t)(gotpl_output_stream* os);

typedef enum {
	gotpl_enc_binary, gotpl_enc_utf8
} gotpl_encoding;

struct gotpl_input_stream {
	gotpl_ui* _private;
	gotpl_i last_error_code;
	gotpl_ci current_char;
	gotpl_encoding current_encoding;
	gotpl_is_read_t read;
	gotpl_is_has_more_t has_more;
	gotpl_is_close_t close;
};

struct gotpl_output_stream {
	gotpl_ui* _private;
	gotpl_i last_error_code;
	gotpl_os_write_t write;
	gotpl_os_flush_t flush;
	gotpl_os_close_t close;
};

gotpl_bool gotpl_create_std_output_stream(gotpl_output_stream* os,
		gotpl_i8 * path);
gotpl_bool gotpl_create_std_input_stream(gotpl_input_stream* is,
		gotpl_i8 * path, gotpl_encoding encoding);
