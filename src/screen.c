#include "headers/screen/screen.h"

// The VGA framebuffer starts at 0xB8000.
static ui16 *fbuffer_addr = (ui16 *) 0xB8000;

// Stores the cursor position.
static ui8 cursor_x = 0;
static ui8 cursor_y = 0;

static void move_cursor() {
	ui16 temp = cursor_y * 80 + cursor_x;

	outb(0x3D4, 14);
	outb(0x3D5, temp >> 8);
	outb(0x3D4, 15);
	outb(0x3D5, temp);
}

// Scrolls the text on the screen up by one line.
static void scroll() {

	// Get a space character with the default color attributes.
	ui8 attributeByte = (0 /*black*/<< 4) | (15 /*white*/& 0x0F);
	ui16 blank = 0x20 /* space */| (attributeByte << 8);

	// Row 25 is the end, this means we need to scroll up
	if (cursor_y >= 25) {
		// Move the current text chunk that makes up the screen
		// back in the buffer by a line
		ui32 i;
		for (i = 0 * 80; i < 24 * 80; i++) {
			fbuffer_addr[i] = fbuffer_addr[i + 80];
		}

		// The last line should now be blank. Do this by writing
		// 80 spaces to it.
		for (i = 24 * 80; i < 25 * 80; i++) {
			fbuffer_addr[i] = blank;
		}
		// The cursor should now be on the last line.
		cursor_y = 24;
	}
}

// Clears the screen, by copying lots of spaces to the framebuffer.
void clrscr() {
	// Make an attribute byte for the default colors
	ui8 attributeByte = (0 /*black*/<< 4) | (15 /*white*/& 0x0F);
	ui16 blank = 0x20 /* space */| (attributeByte << 8);

	ui32 i;
	for (i = 0; i < 80 * 25; i++) {
		fbuffer_addr[i] = blank;
	}

	// Move the hardware cursor back to the start.
	cursor_x = 0;
	cursor_y = 0;
	move_cursor();
}

// Writes a single character out to the screen.
void put(char c, ui8 backColor, ui8 foreColor) {
	// The background color is black (0), the foreground is white (15).
	backColor = 0;
	foreColor = 15;

	// The attribute byte is made up of two nibbles - the lower being the
	// foreground color, and the upper the background color.
	ui8 attributeByte = (backColor << 4) | (foreColor & 0x0F);
	// The attribute byte is the top 8 bits of the word we have to send to the
	// VGA board.
	ui16 attribute = attributeByte << 8;
	ui16 *location;

	// Handle a backspace, by moving the cursor back one space
	if (c == 0x08 && cursor_x) {
		cursor_x--;
	}

	// Handle a tab by increasing the cursor's X, but only to a point
	// where it is divisible by 8.
	else if (c == 0x09) {
		cursor_x = (cursor_x + 8) & ~(8 - 1);
	}

	// Handle carriage return
	else if (c == '\r') {
		cursor_x = 0;
	}

	// Handle newline by moving cursor back to left and increasing the row
	else if (c == '\n') {
		cursor_x = 0;
		cursor_y++;
	}
	// Handle any other printable character.
	else if (c >= ' ') {
		location = fbuffer_addr + (cursor_y * 80 + cursor_x);
		*location = c | attribute;
		cursor_x++;
	}

	// Check if we need to insert a new line because we have reached the end
	// of the screen.
	if (cursor_x >= 80) {
		cursor_x = 0;
		cursor_y++;
	}

	// Scroll the screen if needed.
	scroll();
	// Move the hardware cursor.
	move_cursor();
}

void write(char *c, ui8 backColor, ui8 foreColor) {
	ui32 i = 0;
	while (c[i]) {
		put(c[i++], backColor, foreColor);
	}
}
