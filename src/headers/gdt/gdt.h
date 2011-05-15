#include "../base.h"

#ifndef GDT_H_
#define GDT_H_

struct gdt_entry_struct {
	ui16 limit_low; // The lower 16 bits of the limit.
	ui16 base_low; // The lower 16 bits of the base.
	ui8 base_middle; // The next 8 bits of the base.
	ui8 access; // Access flags, determine what ring this segment can be used in.
	ui8 granularity;
	ui8 base_high; // The last 8 bits of the base.
}__attribute__((packed));

struct gdt_ptr_struct {
	ui16 limit; // The upper 16 bits of all selector limits.
	ui32 base; // The address of the first gdt_entry_t struct.
}__attribute__((packed));

typedef struct gdt_ptr_struct gdt_ptr_t;
typedef struct gdt_entry_struct gdt_entry_t;

#endif
