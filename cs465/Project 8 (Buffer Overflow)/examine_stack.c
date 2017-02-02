#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int senior (int a, int b) {
  
  int cougars = 30000;

  return 12;

}

int junior (int x, short *y) {

  char name[10] = "cougars";
  senior (x, 2);
}

int sophomore (int a, int b) {

  short tiny = 237;

  junior (b, &tiny);
}

int freshman(int a, int b, int c) {

  char housing[30] = "Helaman Halls";
  sophomore (a + b, c);
}



int main()
{

  int year = 61162;
  int month = 61163;
  int day = 61164;

  int result = freshman (month, day, year);

  exit(0);
}
