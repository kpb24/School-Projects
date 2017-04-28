=gcc
CFLAGS=-m32 -c

custom_malloc: mymalloc.o mallocdrv.o
        $(CC) -m32 -o custom_malloc mymalloc.o mallocdrv.o

mymalloc.o: mymalloc.c mymalloc.h
        $(CC) $(CFLAGS) -o mymalloc.o mymalloc.c

mallocdrv.o: mallocdrv.c mymalloc.h
        $(CC) $(CFLAGS) -o mallocdrv.o mallocdrv.c
