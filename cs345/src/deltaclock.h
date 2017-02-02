/*
 * deltaclock.h
 *
 *  Created on: Jul 17, 2013
 *      Author: JRASM91
 */

#ifndef DELTACLOCK_H_
#define DELTACLOCK_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <setjmp.h>
#include <time.h>
#include <assert.h>
#include "os345.h"


typedef struct deltaclock
{
	int time;
	Semaphore* event;
} DeltaClock;

int numDeltaClock;

void initDeltaClock();
void insertDeltaClock(int time, Semaphore* event);
void decreaseDeltaClock(void);

#endif /* DELTACLOCK_H_ */
