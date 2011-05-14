#include "../base.h"

#ifndef SCREEN_H_
#define SCREEN_H_

// Clear the screen to all black.
void clrscr();

//Print one char.
void put(char c, ui8 backColor, ui8 foreColor);

// Output a null-terminated ASCII string to the monitor.
void write(char *c, ui8 backColor, ui8 foreColor);

typedef enum {
	BLACK = 0,
	BLUE = 1,
	GREEN = 2,
	CYAN = 3,
	RED = 4,
	MAGENTA = 5,
	BROWN = 6,
	LIGHT_GREY = 7,
	DARK_GREY = 8,
	LIGHT_BLUE = 9,
	LIGHT_GREEN = 10,
	LIGHT_CYAN = 11,
	LIGHT_RED = 12,
	LIGHT_MAGENTA = 13,
	LIGHT_BROWN = 14,
	WHITE = 15
} COLOR;

#endif
