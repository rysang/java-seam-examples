#include "../base.h"

#ifndef IDT_H_
#define IDT_H_

typedef struct {
	ui32 ds; // Data segment selector
	ui32 edi, esi, ebp, esp, ebx, edx, ecx, eax; // Pushed by pusha.
	ui32 int_no, err_code; // Interrupt number and error code (if applicable)
	ui32 eip, cs, eflags, useresp, ss; // Pushed by the processor automatically.
} regs;

// A struct describing an interrupt gate.
typedef struct {
	ui16 base_lo; // The lower 16 bits of the address to jump to when this interrupt fires.
	ui16 sel; // Kernel segment selector.
	ui8 always0; // This must always be zero.
	ui8 flags; // More flags. See documentation.
	ui16 base_hi; // The upper 16 bits of the address to jump to.
}__attribute__((packed)) idt_entry_t;

// A struct describing a pointer to an array of interrupt handlers.
// This is in a format suitable for giving to 'lidt'.
typedef struct {
	ui16 limit;
	ui32 base; // The address of the first element in our idt_entry_t array.
}__attribute__((packed)) idt_ptr_t;

void idt_init();

#endif
