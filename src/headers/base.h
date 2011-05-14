#ifndef BASE_H_
#define BASE_H_

typedef unsigned int ui32;
typedef int i32;
typedef unsigned short ui16;
typedef short i16;
typedef unsigned char ui8;
typedef char i8;

void outb(ui16 port, ui8 value);
ui8 inb(ui16 port);
ui16 inw(ui16 port);

#endif
