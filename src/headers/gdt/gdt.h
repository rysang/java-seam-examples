#include "../base.h"

#ifndef GDT_H_
#define GDT_H_

typedef struct {
	ui16 limit_low; // The lower 16 bits of the limit.
	ui16 base_low; // The lower 16 bits of the base.
	ui8 base_middle; // The next 8 bits of the base.
	ui8 access; // Access flags, determine what ring this segment can be used in.
	ui8 granularity;
	ui8 base_high; // The last 8 bits of the base.
}__attribute__((packed)) gdt_entry_t;

typedef struct {
	ui16 limit; // The upper 16 bits of all selector limits.
	ui32 base; // The address of the first gdt_entry_t struct.
}__attribute__((packed)) gdt_ptr_t;

typedef struct {
	// Segment type - code segment / data segment.
	ui8 type :4;
	// Descriptor type
	ui8 dt :1;
	// Descriptor privilege level - Ring 0 - 3.
	ui8 dpl :2;
	// Is segment present? (1 = Yes)
	ui8 p :1;
}__attribute__((packed)) gdt_access_byte_t;

typedef struct {
	// Segment length.
	ui8 seg_len :4;
	// Available for system use (always zero).
	ui8 a :1;
	// Should always be zero.
	ui8 o :1;
	// Operand size (0 = 16bit, 1 = 32bit)
	ui8 d :1;
	// Granularity (0 = 1 byte, 1 = 1kbyte)
	ui8 g :1;
}__attribute__((packed)) gdt_granularity_byte_t;

void gdt_init();

#endif
