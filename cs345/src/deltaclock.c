/*
 * deltaclock.c
 *
 *  Created on: Jul 17, 2013
 *      Author: JRASM91
 */

#include "deltaclock.h"

DeltaClock deltaClock[MAX_TASKS];

extern Semaphore* dcChange;
extern TCB tcb[MAX_TASKS];
extern Semaphore* tics1sec;

int timeTaskID;

void initDeltaClock()
{
	numDeltaClock = 0;
	int i;
	for(i = 0; i < MAX_TASKS; i++)
	{
		deltaClock[i].time = 0;
		deltaClock[i].event = 0;
	}
}

Semaphore* popDeltaClock()
{
	Semaphore* result;
	if ( numDeltaClock == 0)
		result = NULL;
	else
	{
		numDeltaClock -= 1;
		result = deltaClock[numDeltaClock].event;
		deltaClock[numDeltaClock].event = 0;
		deltaClock[numDeltaClock].time = 0;
	}
	return result;
}

void decreaseDeltaClock()
{
	if(numDeltaClock != 0)
	{
		deltaClock[numDeltaClock - 1].time -= 1;
		if(deltaClock[numDeltaClock - 1].time <= 0)
		{
			semSignal(dcChange);
			Semaphore *sem = popDeltaClock();
			semSignal(sem);
		}
	}
}

void printDeltaClock()
{
	P3_dc(0, NULL);
}

void insertDeltaClock(int time, Semaphore* event)
{
	deltaClock[numDeltaClock].time = time;
	deltaClock[numDeltaClock].event = event;
	numDeltaClock += 1;

	if(numDeltaClock == 1)
		return;

	int i = numDeltaClock - 1;
	while(i != 0 && deltaClock[i - 1].time - deltaClock[i].time < 0)
	{
		deltaClock[i].time -= deltaClock[i - 1].time;

		int tempTime = deltaClock[i].time;
		Semaphore* tempEvent = deltaClock[i].event;

		deltaClock[i].time = deltaClock[i - 1].time;
		deltaClock[i].event = deltaClock[i - 1].event;

		deltaClock[i - 1].time = tempTime;
		deltaClock[i - 1].event = tempEvent;

		i--;
	}
	deltaClock[i - 1].time -= deltaClock[i].time;
}
// display time every tics1sec
int timeTask(int argc, char* argv[])
{
	char svtime[64];						// ascii current time
	while (1)
	{
		SEM_WAIT(tics1sec);
		printf("\nTime = %s", myTime(svtime));
	}
	return 0;
} // end timeTask

int dcMonitorTask(int argc, char* argv[])
{
	int i, flg;
	char buf[32];
	// create some test times for event[0-9]
	int mySize = 8;
	int ttime[8] = {64,4,2,16,32,8, 128, 256};
	Semaphore* event[mySize];
	for (i=0; i< mySize; i++)
	{
		sprintf(buf, "event[%d]", i);
		event[i] = createSemaphore(buf, BINARY, 0);
		insertDeltaClock(ttime[i], event[i]);
	}
	printDeltaClock();

	while (numDeltaClock > 0)
	{
		SEM_WAIT(dcChange)
														flg = 0;
		for (i=0; i<mySize; i++)
		{
			if (event[i]->state ==1)			{
				printf("\n  event[%d] signaled", i);
				event[i]->state = 0;
				flg = 1;
			}
		}
		if (flg) printDeltaClock();
	}
	printf("\nNo more events in Delta Clock");

	// kill dcMonitorTask
	tcb[timeTaskID].state = S_EXIT;
	return 0;
} // end dcMonitorTask


// delta clock command
int P3_dc(int argc, char* argv[])
{
	printf("\nDelta Clock");
	int i;
	for (i=numDeltaClock - 1; i >= 0; i--)
	{
		printf("\n%4d%4d  %-20s", i, deltaClock[i].time, deltaClock[i].event->name);
	}
	return 0;
}

// test delta clock
int P3_tdc(int argc, char* argv[])
{
	createTask( "DC task",			// task name
			dcMonitorTask,		// task
			10,					// task priority
			argc,					// task arguments
			argv);
	timeTaskID = createTask("timeTask",			// task name
			timeTask,		// task
			10,					// task priority
			argc,					// task arguments
			argv);

	return 0;

} // end P3_tdc
