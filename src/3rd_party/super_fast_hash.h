/*
 * super_fast_hash.h
 *
 *	© Copyright 2004-2008 by Paul Hsieh
 *  http://www.azillionmonkeys.com/qed/hash.html
 */
#include "../gotpl/gotpl.h"
#ifndef SUPER_FAST_HASH_H_
#define SUPER_FAST_HASH_H_

#undef get16bits
#if (defined(__GNUC__) && defined(__i386__)) || defined(__WATCOMC__) \
  || defined(_MSC_VER) || defined (__BORLANDC__) || defined (__TURBOC__)
#define get16bits(d) (*((const gotpl_ui16 *) (d)))
#endif

#if !defined (get16bits)
#define get16bits(d) ((((gotpl_ui32)(((const gotpl_ui8 *)(d))[1])) << 8)\
                       +(gotpl_ui32)(((const gotpl_ui8 *)(d))[0]) )
#endif
gotpl_ui32 super_fast_hash(const gotpl_i8 * data, gotpl_ui len);

#endif /* SUPER_FAST_HASH_H_ */
