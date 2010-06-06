/*
 * lib_m3g_section_reader.c
 *
 *  Created on: 1 Jun 2010
 *      Author: cpriceputu
 */

#include "lib_m3g.h"
#include <memory.h>

struct m3g_section* readNextSection(struct m3g_section_reader* reader,
		pool_t pool);

struct m3g_section_reader* m3g_createSectionReader(struct m3g_input_stream* is,
		pool_t pool) {
	struct m3g_section_reader* reader = p_alloc(pool,
			sizeof(struct m3g_section_reader));
	reader->inputStream = is;
	reader->readNextSection = readNextSection;
	return reader;
}

//TODO make here also for compressed sections
struct m3g_section* readNextSection(struct m3g_section_reader* reader,
		pool_t pool) {
	UInt32 i;

	if (reader->inputStream->m_hasMoreBytes(reader->inputStream)) {
		struct m3g_section* section = p_alloc(pool, sizeof(struct m3g_section));
		memset(section, '\0', sizeof(struct m3g_section));
		section->CompressionScheme = reader->inputStream->m_readNext(
				reader->inputStream);

		section->TotalSectionLength = readUInt32(reader->inputStream);
		section->UncompressedLength = readUInt32(reader->inputStream);
		//alocate memory to objects.


		section->Objects = p_alloc(pool, section->UncompressedLength);
		for (i = 0; i < section->UncompressedLength; i++) {
			section->Objects[i] = reader->inputStream->m_readNext(
					reader->inputStream);
		}

		section->Checksum = readUInt32(reader->inputStream);

		return section;
	}

	return 0;
}

UInt32 readUInt32(struct m3g_input_stream* is) {
	UInt32 retVal;
	retVal = ((UInt32) is->m_readNext(is));
	retVal |= ((UInt32) is->m_readNext(is)) << 8;
	retVal |= ((UInt32) is->m_readNext(is)) << 16;
	retVal |= ((UInt32) is->m_readNext(is)) << 24;

	return retVal;
}

UInt32 readUInt32FromArray(Byte* b) {
	UInt32 retVal;
	retVal = ((UInt32) b[0]);
	retVal |= ((UInt32) b[1]) << 8;
	retVal |= ((UInt32) b[2]) << 16;
	retVal |= ((UInt32) b[3]) << 24;

	return retVal;
}
