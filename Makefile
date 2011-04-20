#VERBOSE=-v -Q -Wall 
CFLAGS=$(VERBOSE) -O3 -fomit-frame-pointer -march=native -mtune=native -pipe 
CC=gcc
APP_NAME=test
RM=rm
ARCH=-m32

all: clean compile link

compile:
	$(CC) $(CFLAGS) $(ARCH) -c src/*.c src/tags/*.c 
link:
	$(CC) $(ARCH) *.o -o $(APP_NAME)
clean:
	$(RM) -rfv *.o $(APP_NAME) $(APP_NAME).exe
	
