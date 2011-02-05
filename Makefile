CFLAGS=-O2 -fomit-frame-pointer -march=native -mtune=native -pipe -Wall 
CC=gcc
APP_NAME=test
RM=rm

all: clean compile link

compile:
	$(CC) $(CFLAGS) -c src/*.c
link:
	$(CC) *.o -o $(APP_NAME)
clean:
	$(RM) -rfv *.o $(APP_NAME) $(APP_NAME).exe
	
