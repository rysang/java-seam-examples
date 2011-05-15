SOURCES=boot.o main.o screen.o base.o gdt_flush.o gdt.o idt_flush.o interrupts.o idt.o memory.o

CFLAGS=-nostdlib -nostdinc -fno-builtin -fno-stack-protector
LDFLAGS=-Tlink.ld
ASFLAGS=-felf

all: $(SOURCES) link

clean:
	rm -rf *.o kernel *~

link:
	ld $(LDFLAGS) -o kernel $(SOURCES)

.s.o:
	nasm $(ASFLAGS) $<
