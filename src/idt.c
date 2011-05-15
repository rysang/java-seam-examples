#include "headers/idt/idt.h"

extern void idt_flush(ui32);
// These extern directives let us access the addresses of our ASM ISR handlers.
extern void isr0();
extern void isr1();
extern void isr2();
extern void isr3();
extern void isr4();
extern void isr5();
extern void isr6();
extern void isr7();
extern void isr8();
extern void isr9();
extern void isr10();
extern void isr11();
extern void isr12();
extern void isr13();
extern void isr14();
extern void isr15();
extern void isr16();
extern void isr17();
extern void isr18();
extern void isr19();
extern void isr20();
extern void isr21();
extern void isr22();
extern void isr23();
extern void isr24();
extern void isr25();
extern void isr26();
extern void isr27();
extern void isr28();
extern void isr29();
extern void isr30();
extern void isr31();

static void idt_set_gate(ui8, ui32, ui16, ui8);

idt_entry_t idt_entries[256];
idt_ptr_t idt_ptr;

void isr_handler(regs regs) {

}

void idt_init() {
	idt_ptr.limit = sizeof(idt_entry_t) * 256 - 1;
	idt_ptr.base = (ui32) &idt_entries;

	memset(&idt_entries, 0, sizeof(idt_entry_t) * 256);

	idt_set_gate(0, (ui32) isr0, 0x08, 0x8E);
	idt_set_gate(1, (ui32) isr1, 0x08, 0x8E);
	idt_set_gate(2, (ui32) isr2, 0x08, 0x8E);
	idt_set_gate(3, (ui32) isr3, 0x08, 0x8E);
	idt_set_gate(4, (ui32) isr4, 0x08, 0x8E);
	idt_set_gate(5, (ui32) isr5, 0x08, 0x8E);
	idt_set_gate(6, (ui32) isr6, 0x08, 0x8E);
	idt_set_gate(7, (ui32) isr7, 0x08, 0x8E);
	idt_set_gate(8, (ui32) isr8, 0x08, 0x8E);
	idt_set_gate(9, (ui32) isr9, 0x08, 0x8E);
	idt_set_gate(10, (ui32) isr10, 0x08, 0x8E);
	idt_set_gate(11, (ui32) isr11, 0x08, 0x8E);
	idt_set_gate(12, (ui32) isr12, 0x08, 0x8E);
	idt_set_gate(13, (ui32) isr13, 0x08, 0x8E);
	idt_set_gate(14, (ui32) isr14, 0x08, 0x8E);
	idt_set_gate(15, (ui32) isr15, 0x08, 0x8E);
	idt_set_gate(16, (ui32) isr16, 0x08, 0x8E);
	idt_set_gate(17, (ui32) isr17, 0x08, 0x8E);
	idt_set_gate(18, (ui32) isr18, 0x08, 0x8E);
	idt_set_gate(19, (ui32) isr19, 0x08, 0x8E);
	idt_set_gate(20, (ui32) isr20, 0x08, 0x8E);
	idt_set_gate(21, (ui32) isr21, 0x08, 0x8E);
	idt_set_gate(22, (ui32) isr22, 0x08, 0x8E);
	idt_set_gate(23, (ui32) isr23, 0x08, 0x8E);
	idt_set_gate(24, (ui32) isr24, 0x08, 0x8E);
	idt_set_gate(25, (ui32) isr25, 0x08, 0x8E);
	idt_set_gate(26, (ui32) isr26, 0x08, 0x8E);
	idt_set_gate(27, (ui32) isr27, 0x08, 0x8E);
	idt_set_gate(28, (ui32) isr28, 0x08, 0x8E);
	idt_set_gate(29, (ui32) isr29, 0x08, 0x8E);
	idt_set_gate(30, (ui32) isr30, 0x08, 0x8E);
	idt_set_gate(31, (ui32) isr31, 0x08, 0x8E);

	idt_flush((ui32) &idt_ptr);
}

static void idt_set_gate(ui8 num, ui32 base, ui16 sel, ui8 flags) {
	idt_entries[num].base_lo = base & 0xFFFF;
	idt_entries[num].base_hi = (base >> 16) & 0xFFFF;

	idt_entries[num].sel = sel;
	idt_entries[num].always0 = 0;
	// We must uncomment the OR below when we get to using user-mode.
	// It sets the interrupt gate's privilege level to 3.
	idt_entries[num].flags = flags /* | 0x60 */;
}
