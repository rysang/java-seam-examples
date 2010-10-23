/* utf8_decode.c */

/* 2009-02-13 */

/*
 Copyright (c) 2005 JSON.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

#include "../include/libctempl.h"

#ifndef UTF8_DECODE_H_
#define UTF8_DECODE_H_

#define UTF8_END   -1
#define UTF8_ERROR -2

/**
 * This code has been modified a little to my needs.
 */
typedef struct {
	tpl_int32_t index;
	tpl_int32_t length;
	tpl_int32_t chr;
	tpl_int32_t b;
	tpl_ptr input;
} utf8_source;

extern int utf8_decode_at_byte(utf8_source *source);
extern int utf8_decode_at_character(utf8_source *source);
extern void utf8_decode_init(tpl_ptr p, tpl_int32_t length, utf8_source* source);
extern int utf8_decode_next(utf8_source *source);
extern int utf8_decode_next_loose(utf8_source *source);

#endif /* UTF8_DECODE_H_ */
