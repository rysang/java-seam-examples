// common.c -- Defines some global functions.
//             From JamesM's kernel development tutorials.

#include "common.h"

// Write a byte out to the specified port.
void outb(u16int port, u8int value)
{
    asm volatile ("outb %1, %0" : : "dN" (port), "a" (value));
}

u8int inb(u16int port)
{
    u8int ret;
    asm volatile("inb %1, %0" : "=a" (ret) : "dN" (port));
    return ret;
}

u16int inw(u16int port)
{
    u16int ret;
    asm volatile ("inw %1, %0" : "=a" (ret) : "dN" (port));
    return ret;
}

u32int strlen(const char *string) {
	const char *s;

	s = string;
	while (*s)
		s++;
	return s - string;
}

char *strrev(char *str) {
	char *p1, *p2;

	if (!str || !*str)
		return str;

	for (p1 = str, p2 = str + strlen(str) - 1; p2 > p1; ++p1, --p2) {
		*p1 ^= *p2;
		*p2 ^= *p1;
		*p1 ^= *p2;
	}

	return str;
}

char *itoa(int n, char *s, int b) {
	static char digits[] = "0123456789abcdefghijklmnopqrstuvwxyz";
	int i=0, sign;
    
	if ((sign = n) < 0)
		n = -n;

	do {
		s[i++] = digits[n % b];
	} while ((n /= b) > 0);

	if (sign < 0)
		s[i++] = '-';
	s[i] = '\0';

	return strrev(s);
}

// Copy len bytes from src to dest.
void memcpy(u8int *dest, const u8int *src, u32int len)
{
    // TODO: implement this yourself!
}

// Write len copies of val into dest.
void memset(u8int *dest, u8int val, u32int len)
{
    // TODO: implement this yourself!
}

// Compare two strings. Should return -1 if 
// str1 < str2, 0 if they are equal or 1 otherwise.
int strcmp(char *str1, char *str2)
{
    // TODO: implement this yourself!
}

// Copy the NULL-terminated string src into dest, and
// return dest.
char *strcpy(char *dest, const char *src)
{
    // TODO: implement this yourself!
}

// Concatenate the NULL-terminated string src onto
// the end of dest, and return dest.
char *strcat(char *dest, const char *src)
{
    // TODO: implement this yourself!
}
