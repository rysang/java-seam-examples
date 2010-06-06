/*
 * lib_m3g_streams.h
 *
 *  Created on: 3 Jun 2010
 *      Author: id821436
 */
#include  "lib_m3g_protos.h"

#ifndef LIB_M3G_STREAMS_H_
#define LIB_M3G_STREAMS_H_

//methods
typedef void(*m3g_close)(struct m3g_input_stream* is);
typedef Boolean(*m3g_hasMoreBytes)(struct m3g_input_stream* is);
typedef Boolean(*m3g_reset)(struct m3g_input_stream* is);
typedef Byte(*m3g_readNext)(struct m3g_input_stream* is);

//Input stream structure, used by the lib to read from any kind of stream
struct m3g_input_stream {
	//private, to be used by implemented api.
	void* m_private;
	//should be used if there are additional cleanup routines.
	m3g_close m_close;
	//checks to see if is end of stream, in case yes should return false.
	m3g_hasMoreBytes m_hasMoreBytes;
	//Should reposition pointer to the start of the stream if not return false.
	m3g_reset m_reset;
	//should return next available byte.
	m3g_readNext m_readNext;
};

//File input stream
struct m3g_input_stream* m3g_createFileInputStream(const char* path);
void m3g_destroyFileInputStream(struct m3g_input_stream* is);

#endif /* LIB_M3G_STREAMS_H_ */
