#include "headers/base.h"

// Write a byte out to the specified port.
void outb(ui16 port, ui8 value) {
	asm volatile ("outb %1, %0" : : "dN" (port), "a" (value));
}

ui8 inb(ui16 port) {
	ui8 ret;
	asm volatile("inb %1, %0" : "=a" (ret) : "dN" (port));
	return ret;
}

ui16 inw(ui16 port) {
	ui16 ret;
	asm volatile ("inw %1, %0" : "=a" (ret) : "dN" (port));
	return ret;
}
