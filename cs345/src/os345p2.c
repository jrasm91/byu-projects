// os345p2.c - Multi-tasking
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
#include <assert.h>
#include "os345.h"

#define my_printf	printf

// ***********************************************************************
// project 2 variables
static Semaphore* s1Sem;					// task 1 semaphore
static Semaphore* s2Sem;					// task 2 semaphore

extern TCB tcb[];								// task control block
extern int curTask;							// current task #
extern Semaphore* semaphoreList;			// linked list of active semaphores
extern jmp_buf reset_context;				// context of kernel stack

// ***********************************************************************
// project 2 functions and tasks

int signalTask(int, char**);
int ImAliveTask(int, char**);
int tenSeconds(int, char**);

// ***********************************************************************
// ***********************************************************************
// project2 command
int P2_project2(int argc, char* argv[])
{
	static char* s1Argv[] = {"signal1", "s1Sem"};
	static char* s2Argv[] = {"signal2", "s2Sem"};
	static char* aliveArgv[] = {"I'm Alive", "3"};

	printf("\nStarting Project 2");
	SWAP;

	int i;
	for(i = 0; i < 10; i++)
	{
		char temp[32];
		char* temp2[] = {"timeSeconds", ""};
		sprintf(temp, "Ten Seconds [%d]", i);
		temp2[1] = &temp[12];
		createTask(temp,				// task name
				tenSeconds,			// task
				MED_PRIORITY,			// task priority
				2,							// task argc
				temp2);				// task argument pointers
	}
	// start tasks looking for sTask semaphores
	createTask("signal1",				// task name
			signalTask,				// task
			VERY_HIGH_PRIORITY,	// task priority
			2,							// task argc
			s1Argv);					// task argument pointers

	createTask("signal2",				// task name
			signalTask,				// task
			VERY_HIGH_PRIORITY,	// task priority
			2,							// task argc
			s2Argv);					// task argument pointers

	createTask("I'm Alive",				// task name
			ImAliveTask,			// task
			LOW_PRIORITY,			// task priority
			2,							// task argc
			aliveArgv);				// task argument pointers

	createTask("I'm Alive",				// task name
			ImAliveTask,			// task
			LOW_PRIORITY,			// task priority
			2,							// task argc
			aliveArgv);				// task argument pointers

	return 0;
} // end P2_project2


extern Queue READY_QUEUE;
extern int scheduler_mode;
// ***********************************************************************
// ***********************************************************************
// list tasks command
int P2_listTasks(int argc, char* argv[])
{
	printf("\nScheduling Type: %s", ((scheduler_mode == ROUND_ROBIN)? "ROUND:ROBIN" : "TIME:SLICE"));
	if(argc != 1)
	{
		printf("\nReadyQueue [%d] queue", READY_QUEUE.size);
		dumpQueue(&READY_QUEUE);
		Semaphore* sem = semaphoreList;
		while(sem)
		{
			if( sem->bq.size != 0 || argc == 3)
			{
				printf("\n%s [%d] queue", sem->name, sem->bq.size);
				dumpQueue(&sem->bq);
			}
			sem = (Semaphore*)sem->semLink;
		}
		return 0;
	}

	//	?? 1) List all tasks in all queues
	// ?? 2) Show the task stake (new, running, blocked, ready)
	// ?? 3) If blocked, indicate which semaphore
	else
	{
		int i;
		Queue ALL;
		for(i = 0; i < MAX_TASKS; i++)
			if (tcb[i].name)
				enqueue(i, &ALL);
		printf("\nEverything [%d]", ALL.size);
		dumpQueue(&ALL);
		return 0;
	}
} // end P2_listTasks


int sem_test1(int argc, char* argv[]);
int sem_test2(int argc, char* argv[]);
Semaphore *semMutex;
int semaphoreTest(int argc, char* argv[])
{
	semMutex = createSemaphore("semMutex", BINARY, 1);
	createTask("semTest1",  sem_test1, MED_PRIORITY, argc, argv);
	createTask("semTest2",  sem_test2, MED_PRIORITY, argc, argv);
	while(TRUE)
	{
		SWAP;
	}

	return 1;
}

extern Semaphore* tics1sec;
int sem_test1(int argc, char* argv[])
{
	while(TRUE)
	{
		SEM_WAIT(tics1sec); 		//SWAP;
		SEM_WAIT(semMutex); 		//SWAP;
		printf("\ngot mutex 1"); 	//SWAP;
		SEM_SIGNAL(semMutex); 		//SWAP;
	}
	return 0;
}

int sem_test2(int argc, char* argv[])
{
	while(TRUE)
	{
		SEM_WAIT(tics1sec); 		//SWAP;
		SEM_WAIT(semMutex); 		SWAP;
		printf("\ngot mutex 2");	//SWAP;
		SEM_SIGNAL(semMutex); 		//SWAP;
	}
	return 0;
}


// ***********************************************************************
// ***********************************************************************
// list semaphores command
//
int match(char* mask, char* name)
{
	int i,j;

	// look thru name
	i = j = 0;
	if (!mask[0]) return 1;
	while (mask[i] && name[j])
	{
		if (mask[i] == '*') return 1;
		if (mask[i] == '?') mask[i] = '?';
		else if ((mask[i] != toupper(name[j])) && (mask[i] != tolower(name[j]))) return 0;
		i++;
		j++;
	}
	if (mask[i] == name[j]) return 1;
	return 0;
} // end match

int P2_listSems(int argc, char* argv[])				// listSemaphores
{
	Semaphore* sem = semaphoreList;
	while(sem)
	{
		if ((argc == 1) || match(argv[1], sem->name))
		{
			printf("\n%20s  %c  %d  %s", sem->name, (sem->type?'C':'B'), sem->state,
					tcb[sem->taskNum].name);
		}
		sem = (Semaphore*)sem->semLink;
	}
	return 0;
} // end P2_listSems



// ***********************************************************************
// ***********************************************************************
// reset system
int P2_reset(int argc, char* argv[])						// reset
{
	longjmp(reset_context, POWER_DOWN_RESTART);
	// not necessary as longjmp doesn't return
	return 0;

} // end P2_reset



// ***********************************************************************
// ***********************************************************************
// kill task

int P2_killTask(int argc, char* argv[])			// kill task
{
	int taskId = INTEGER(argv[1]);					// convert argument 1

	if ((taskId > 0) && tcb[taskId].name)			// check for single task
	{
		my_printf("\nKill Task %d", taskId);
		tcb[taskId].state = S_EXIT;
		killTask(taskId);
		return 0;
	}
	else if (taskId < 0)									// check for all tasks
	{
		printf("\nKill All Tasks");
		for (taskId=1; taskId<MAX_TASKS; taskId++)
		{
			if (tcb[taskId].name)
			{
				my_printf("\nKill Task %d", taskId);
				tcb[taskId].state = S_EXIT;
				killTask(taskId);
			}
		}
	}
	else														// invalid argument
	{
		my_printf("\nIllegal argument or Invalid Task");
	}
	return 0;
} // end P2_killTask



// ***********************************************************************
// ***********************************************************************
// signal command
void sem_signal(Semaphore* sem)		// signal
{
	if (sem)
	{
		printf("\nSignal %s", sem->name);
		SEM_SIGNAL(sem);
	}
	else my_printf("\nSemaphore not defined!");
	return;
} // end sem_signal



// ***********************************************************************
int P2_signal1(int argc, char* argv[])		// signal1
{
	SEM_SIGNAL(s1Sem);
	return 0;
} // end signal

int P2_signal2(int argc, char* argv[])		// signal2
{
	SEM_SIGNAL(s2Sem);
	return 0;
} // end signal



// ***********************************************************************
// ***********************************************************************
// signal task
//
#define COUNT_MAX	5
//
int signalTask(int argc, char* argv[])
{
	int count = 0;					// task variable

	// create a semaphore
	Semaphore** mySem = (!strcmp(argv[1], "s1Sem")) ? &s1Sem : &s2Sem;
	*mySem = createSemaphore(argv[1], 0, 0);

	// loop waiting for semaphore to be signaled
	while(count < COUNT_MAX)
	{
		SEM_WAIT(*mySem);			// wait for signal
		printf("\n%s  Task[%d], count=%d", tcb[curTask].name, curTask, ++count);
	}
	return 0;						// terminate task
} // end signalTask



// ***********************************************************************
// ***********************************************************************
// I'm alive task
int ImAliveTask(int argc, char* argv[])
{
	int i;							// local task variable
	while (1)
	{
		printf("\n(%d) I'm Alive!", curTask);
		for (i=0; i<100000; i++) swapTask();

	}
	return 0;						// terminate task
} // end ImAliveTask

// I'm alive task
extern Semaphore* tics1sec;
int tenSeconds(int argc, char* argv[])
{
	while(1)
	{
		semWait(tics1sec);
		printf("\ntenSeconds %s", argv[1]);
	}
	return 0;

} // end tenSeconds task