/*
 * lib_m3g_sections.h
 *
 *  Created on: 3 Jun 2010
 *      Author: cpriceputu
 */
#include "lib_m3g_protos.h"

#ifndef LIB_M3G_SECTIONS_H_
#define LIB_M3G_SECTIONS_H_

enum m3g_CompressionScheme {
	m3g_Uncompressed = 0, m3g_Zlib_Compressed = 1
};

//File section
struct m3g_section {
	Byte CompressionScheme;
	UInt32 TotalSectionLength;
	UInt32 UncompressedLength;
	Byte* Objects;
	UInt32 Checksum;
};

//Section tools
typedef struct m3g_section* (*m3g_readNextSection)(
		struct m3g_section_reader* reader, pool_t pool);
struct m3g_section_reader {
	struct m3g_input_stream *inputStream;
	m3g_readNextSection readNextSection;
};

struct m3g_section_reader* m3g_createSectionReader(struct m3g_input_stream* is,
		pool_t p);

#endif /* LIB_M3G_SECTIONS_H_ */
