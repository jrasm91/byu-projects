// os345p3.c - Jurassic Park
// ***********************************************************************
// **   DISCLAMER ** DISCLAMER ** DISCLAMER ** DISCLAMER ** DISCLAMER   **
// **                                                                   **
// ** The code given here is the basis for the CS345 projects.          **
// ** It comes "as is" and "unwarranted."  As such, when you use part   **
// ** or all of the code, it becomes "yours" and you are responsible to **
// ** understand any algorithm or method presented.  Likewise, any      **
// ** errors or problems become your responsibility to fix.             **
// **                                                                   **
// ** NOTES:                                                            **
// ** -Comments beginning with "// ??" may require some implementation. **
// ** -Tab stops are set at every 3 spaces.                             **
// ** -The function API's in "OS345.h" should not be altered.           **
// **                                                                   **
// **   DISCLAMER ** DISCLAMER ** DISCLAMER ** DISCLAMER ** DISCLAMER   **
// ***********************************************************************
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <setjmp.h>
#include <time.h>
#include <assert.h>
#include "os345.h"
#include "os345park.h"
#include "deltaclock.h"

// Jurassic Park
extern JPARK myPark;
extern Semaphore* parkMutex;						// protect park access
extern Semaphore* fillSeat[NUM_CARS];			// (signal) seat ready to fill
extern Semaphore* seatFilled[NUM_CARS];		// (wait) passenger seated
extern Semaphore* rideOver[NUM_CARS];			// (signal) ride over

Semaphore* enterPark;
Semaphore* needTicket;
Semaphore* getTicket;
Semaphore* ticketAvailable;
Semaphore* enterMuseum;
Semaphore* enterGiftShop;

Semaphore* carMutex;
Semaphore* carNeed;
Semaphore* carReceived;
Semaphore* carReady;

Semaphore* carDriverMutex;
Semaphore* carDriverNeed;
Semaphore* carDriverReceived;
Semaphore* carDriverReady;

Semaphore* carExit[NUM_CARS];

Semaphore* getPassanger;
Semaphore* getDriver;
Semaphore* wakeUpDriver;

int globalCarId;
int globalDriverId;

void initSemaphores();
void createTasks();

int P3_project3(int argc, char* argv[])
{
	char buf[32];
	char* newArgv[2];
	srand(time(NULL));

	// start park
	sprintf(buf, "jurassicPark");
	newArgv[0] = buf;
	createTask( buf,				// task name
			jurassicTask,				// task
			MED_PRIORITY,				// task priority
			1,								// task count
			newArgv);					// task argument

	while (!parkMutex) SWAP;
	printf("\nStart Jurassic Park...");

	initSemaphores();
	createTasks();
	return 0;
} // end project3

void initSemaphores()
{
	enterPark = createSemaphore("enterPark", COUNTING, MAX_IN_PARK);
	getTicket = createSemaphore("getTicket", COUNTING, MAX_TICKETS);
	needTicket = createSemaphore("needTicket", BINARY, 0);
	enterMuseum = createSemaphore("enterMuseum", COUNTING, MAX_IN_MUSEUM);
	enterGiftShop = createSemaphore("enterGiftShop", COUNTING, MAX_IN_GIFTSHOP);

	carMutex = createSemaphore("carMutex", BINARY, 1);
	carNeed = createSemaphore("carNeed", BINARY, 0);
	carReady = createSemaphore("carReady", BINARY, 0);
	carReceived = createSemaphore("carReceived", BINARY, 0);

	carDriverMutex = createSemaphore("carDriverMutex", BINARY, 1);
	carDriverNeed = createSemaphore("carDriverNeed", BINARY, 0);
	carDriverReady = createSemaphore("carDriverReady", BINARY, 0);
	carDriverReceived = createSemaphore("carDriverReceived", BINARY, 0);

	getPassanger = createSemaphore("getPassanger", BINARY, 0);
	getDriver = createSemaphore("getDriver", BINARY, 0);
	wakeUpDriver = createSemaphore("wakeUpDriver", BINARY, 0);

	ticketAvailable = createSemaphore("tickedA", BINARY, 0);

	char buf[32];
	int i;
	for(i = 0; i < NUM_CARS; i++)
	{
		sprintf(buf, "exitCar%d", i);
		carExit[i] = createSemaphore(buf, BINARY, 0);
	}
}

void createTasks()
{
	char buf1[32];
	char buf2[1];
	char* arg[2];
	arg[0] = buf1;
	arg[1] = buf2;
	int i;
	for(i = 0; i < NUM_CARS; i++)
	{
		sprintf(arg[0], "carTask[%d]", i);
		sprintf(arg[1], "%d", i);
		createTask(arg[0], P3_carTask, MED_PRIORITY, 2, arg);
	}
	for(i = 0; i < NUM_VISITORS; i++)
	{
		sprintf(arg[0], "visitorTask[%d]", i);
		sprintf(arg[1], "%d", i);
		createTask(arg[0], P3_visitorTask, MED_PRIORITY, 2, arg);
	}
	for(i = 0; i < NUM_DRIVERS; i++)
	{
		sprintf(arg[0], "driverTask[%d]", i);
		sprintf(arg[1], "%d", i);
		createTask(arg[0], P3_driverTask, MED_PRIORITY, 2, arg);
	}
}

int P3_carTask(int argc, char* argv[])
{
	int id = INTEGER(argv[1]);									SWAP
	int i;														SWAP
	while(1){
		for(i = 0; i < NUM_SEATS; i++)
		{
			SEM_WAIT(fillSeat[id]);								SWAP

			SEM_SIGNAL(getPassanger);							SWAP
			SEM_WAIT(carNeed);									SWAP

			SEM_WAIT(carMutex);									SWAP
			globalCarId = id;									SWAP
			SEM_SIGNAL(carReady);								SWAP
			SEM_WAIT(carReceived);								SWAP
			SEM_SIGNAL(carMutex);								SWAP

			SEM_SIGNAL(seatFilled[id]);							SWAP
		}

		SEM_SIGNAL(getDriver);									SWAP
		SEM_SIGNAL(wakeUpDriver);								SWAP
		SEM_WAIT(carDriverNeed);								SWAP

		SEM_WAIT(carDriverMutex);								SWAP
		globalDriverId = id;									SWAP
		SEM_SIGNAL(carDriverReady);								SWAP
		SEM_WAIT(carDriverReceived);							SWAP
		SEM_SIGNAL(carDriverMutex);								SWAP

		SEM_WAIT(rideOver[id]);

		for(i = 0; i < NUM_SEATS + 1; i++)
			SEM_SIGNAL(carExit[id]);							SWAP
	}
	return 0;
}

int P3_visitorTask(int argc, char* argv[])
{
	int id = INTEGER(argv[1]);									SWAP
	char buf[32];												SWAP
	sprintf(buf, "visitorCountingSem%d", id);					SWAP
	Semaphore* countingSem = createSemaphore(buf, BINARY, 0);	SWAP

	// wait to appear outside park (10 seconds)
	insertDeltaClock(rand() % 100, countingSem);				SWAP
	SEM_WAIT(countingSem);										SWAP
	SEM_WAIT(parkMutex);										SWAP
	myPark.numOutsidePark += 1;									SWAP
	SEM_SIGNAL(parkMutex);										SWAP

	// wait outside park (3 seconds)
	insertDeltaClock(rand() % 30, countingSem);					SWAP
	SEM_WAIT(countingSem);										SWAP

	// enter park get in ticket line
	SEM_WAIT(enterPark);										SWAP
	SEM_WAIT(parkMutex);										SWAP
	myPark.numOutsidePark -= 1;									SWAP
	myPark.numInPark += 1;										SWAP
	myPark.numInTicketLine += 1;								SWAP
	SEM_SIGNAL(parkMutex);										SWAP

	// wait in ticket line (3 seconds)
	insertDeltaClock(rand() % 30, countingSem);					SWAP
	SEM_WAIT(countingSem);										SWAP

	// move from ticket line to museum line (wait getTicket)
	SEM_WAIT(getTicket);										SWAP
	SEM_SIGNAL(needTicket);										SWAP
	SEM_SIGNAL(wakeUpDriver);									SWAP
	SEM_WAIT(ticketAvailable);									SWAP
	SEM_WAIT(parkMutex);										SWAP
	myPark.numInTicketLine -= 1;								SWAP
	myPark.numTicketsAvailable -= 1;							SWAP
	myPark.numInMuseumLine += 1;								SWAP
	SEM_SIGNAL(parkMutex);										SWAP


	// wait in museum line
	insertDeltaClock(rand() % 30, countingSem);					SWAP
	SEM_WAIT(countingSem);										SWAP

	// move from museum line into museum  (wait museum)
	SEM_WAIT(enterMuseum);										SWAP
	SEM_WAIT(parkMutex);										SWAP
	myPark.numInMuseumLine -= 1;								SWAP
	myPark.numInMuseum += 1;									SWAP
	SEM_SIGNAL(parkMutex);										SWAP

	// wait in museum (3 seconds)
	insertDeltaClock(rand() % 30, countingSem);					SWAP
	SEM_WAIT(countingSem);										SWAP

	// move from museum to car line (signal museum)
	SEM_WAIT(parkMutex);										SWAP
	SEM_SIGNAL(enterMuseum);									SWAP
	myPark.numInMuseum -= 1;									SWAP
	myPark.numInCarLine += 1;									SWAP
	SEM_SIGNAL(parkMutex);										SWAP

	//*****************************************************************************
	SEM_WAIT(getPassanger);										SWAP
	SEM_SIGNAL(carNeed);										SWAP
	SEM_WAIT(carReady);											SWAP
	int myCarId = globalCarId;									SWAP
	SEM_WAIT(parkMutex);										SWAP
	myPark.numInCarLine -= 1;									SWAP
	myPark.numInCars += 1;										SWAP
	SEM_SIGNAL(parkMutex);										SWAP
	SEM_SIGNAL(carReceived);									SWAP
	//*****************************************************************************

	// wait for ride to end and move to gift line (wait mySignalRideIsOver)
	SEM_WAIT(carExit[myCarId]);

	SEM_WAIT(parkMutex);										SWAP
	myPark.numInCars -= 1; 										SWAP
	myPark.numInGiftLine += 1; 									SWAP
	myPark.numTicketsAvailable += 1;							SWAP
	SEM_SIGNAL(getTicket);										SWAP
	SEM_SIGNAL(parkMutex);										SWAP

	// wait in gift line (3 seconds)
	insertDeltaClock(rand() % 30, countingSem);					SWAP
	SEM_WAIT(countingSem);										SWAP

	// move from gift line to gift shop (wait gift shop)
	SEM_WAIT(enterGiftShop);									SWAP
	SEM_WAIT(parkMutex);										SWAP
	myPark.numInGiftLine -= 1;									SWAP
	myPark.numInGiftShop += 1;									SWAP
	SEM_SIGNAL(parkMutex);										SWAP

	// wait in gift shop (3 seconds)
	insertDeltaClock(rand() % 30, countingSem);					SWAP
	SEM_WAIT(countingSem);										SWAP

	// leave gift shop and exit park (signal ticket, gift shop)
	SEM_WAIT(parkMutex);										SWAP
	SEM_SIGNAL(enterGiftShop);									SWAP
	myPark.numInGiftShop -= 1;									SWAP
	SEM_SIGNAL(enterPark);										SWAP
	myPark.numInPark -= 1;										SWAP
	myPark.numExitedPark += 1;									SWAP
	SEM_SIGNAL(parkMutex);										SWAP

	return 0;
}

int P3_driverTask(int argc, char* argv[])
{
	while(1)
	{
		SEM_WAIT(wakeUpDriver);									SWAP
		if(semTryLock(getDriver))
		{
			//*****************************************************************************
			SEM_SIGNAL(carDriverNeed);							SWAP
			SEM_WAIT(carDriverReady);							SWAP
			int myCarId = globalDriverId;						SWAP
			SEM_SIGNAL(carDriverReceived);						SWAP
			SEM_WAIT(carExit[myCarId]);							SWAP
			//*****************************************************************************
		}
		if(semTryLock(needTicket))
		{
			SEM_SIGNAL(ticketAvailable);						SWAP
		}
		SWAP
	}
	return 0;
}


