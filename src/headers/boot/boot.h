#include "../base.h"

#ifndef BOOT_H_
#define BOOT_H_

typedef struct {
	ui32 flags;
	ui32 mem_lower;
	ui32 mem_upper;
	ui32 boot_device;
	ui32 cmdline;
	ui32 mods_count;
	ui32 mods_addr;
	ui32 num;
	ui32 size;
	ui32 addr;
	ui32 shndx;
	ui32 mmap_length;
	ui32 mmap_addr;
	ui32 drives_length;
	ui32 drives_addr;
	ui32 config_table;
	ui32 boot_loader_name;
	ui32 apm_table;
	ui32 vbe_control_info;
	ui32 vbe_mode_info;
	ui32 vbe_mode;
	ui32 vbe_interface_seg;
	ui32 vbe_interface_off;
	ui32 vbe_interface_len;
}__attribute__((packed)) multiboot;

#endif
