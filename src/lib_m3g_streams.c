/*
 * lib_m3g_fileStream.c
 *
 *  Created on: 1 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g.h"

struct tmp_file_handle {
	FILE * m_file;
	UInt32 m_fileSize;
	UInt32 m_fileCursor;
};

void file_close(struct m3g_input_stream* is);
Boolean file_hasMoreBytes(struct m3g_input_stream* is);
Boolean file_reset(struct m3g_input_stream* is);
Byte file_readNext(struct m3g_input_stream* is);

struct m3g_input_stream* m3g_createFileInputStream(const char* path) {
	struct tmp_file_handle* fh = malloc(sizeof(struct tmp_file_handle));
	if (fh) {
		struct m3g_input_stream* is = malloc(sizeof(struct m3g_input_stream));
		if (is) {
			is->m_private = fh;
			fh->m_file = fopen(path, "rb");
			fseek(fh->m_file, 0L, SEEK_END);
			fh->m_fileSize = ftell(fh->m_file);
			rewind(fh->m_file);
			fh->m_fileCursor = 0;

			is->m_hasMoreBytes = file_hasMoreBytes;
			is->m_readNext = file_readNext;
			is->m_reset = file_reset;

			return is;
		}
	}

	return 0;
}
;
void m3g_destroyFileInputStream(struct m3g_input_stream* is) {
	if (is != 0) {
		if (is->m_private != 0) {
			struct tmp_file_handle* fh = is->m_private;
			if (fh->m_file != 0) {
				fclose(fh->m_file);
			}
			free(is->m_private);
			is->m_private = 0;
		}
		free(is);
	}
}
;

Boolean file_hasMoreBytes(struct m3g_input_stream* is) {
	struct tmp_file_handle* fh = is->m_private;
	return ((fh->m_fileSize - fh->m_fileCursor) > 0);
}

Byte file_readNext(struct m3g_input_stream* is) {
	Byte b;
	struct tmp_file_handle* fh = is->m_private;
	fread(&b, sizeof(Byte), 1, fh->m_file);
	++fh->m_fileCursor;

	return b;
}

Boolean file_reset(struct m3g_input_stream* is) {
	struct tmp_file_handle* fh = is->m_private;
	rewind(fh->m_file);
	fh->m_fileCursor = 0;

	return 1;
}
