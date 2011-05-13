// main.c -- Defines the C-code kernel entry point, calls initialisation routines.
//           Made for JamesM's tutorials <www.jamesmolloy.co.uk>

#include "monitor.h"
#include "common.h"

int main(struct multiboot *mboot_ptr)
{
    char buf[20];
    // Initialise the screen (by clearing it)
    monitor_clear();
    // Write out a sample string
    monitor_write("Hello, world!\n");
    monitor_write(itoa(mboot_ptr->mem_upper,buf,10));
    
    return 0;
}
