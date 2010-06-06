/*
 * lib_m3g.h
 *
 *  Created on: 1 Jun 2010
 *      Author: id821436
 */

#include <stdio.h>
#include <stdlib.h>
#include "lib_m3g/pool.h"
#include "lib_m3g/lib_m3g_types.h"
#include "lib_m3g/lib_m3g_protos.h"
#include "lib_m3g/lib_m3g_streams.h"
#include "lib_m3g/lib_m3g_sections.h"
#include "lib_m3g/lib_m3g_objects.h"
#include "lib_m3g/lib_m3g_objects3d.h"

#ifndef LIB_M3G_H_
#define LIB_M3G_H_

//structs prototypes
struct m3g_section_reader;
struct m3g_input_stream;
struct m3g_section;
struct m3g_header;
struct m3g_object_converter;
struct m3g_external_ref;
struct m3g_object_reader;



//utils
UInt32 readUInt32(struct m3g_input_stream* is);
UInt32 readUInt32FromArray(Byte* b);
#endif /* LIB_M3G_H_ */
