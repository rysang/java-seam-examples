#include "headers/boot/boot.h"
#include "headers/screen/screen.h"
#include "headers/gdt/gdt.h"

int main(multiboot *mboot_ptr) {
	clrscr();
	write("Hello World OS.", BLACK, BLUE);

	gdt_init();
	return 0;
}
