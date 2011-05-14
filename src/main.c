#include "headers/boot/boot.h"
#include "headers/screen/screen.h"

int main(multiboot *mboot_ptr) {
	clrscr();
	write("Hello World OS.", BLACK, BLUE);

	return 0;
}
