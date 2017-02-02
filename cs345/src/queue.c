/*
 * queue.c
 *
 *  Created on: Jul 18, 2013
 *      Author: JRASM91
 */
#include "queue.h"
#include "os345.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <setjmp.h>
#include <assert.h>
#include <time.h>

extern TCB tcb[MAX_TASKS];
extern int scheduler_mode;
bool debug = FALSE;

void initQueue(Queue *q)
{
	q->size = 0;
	int i = 0;

	for(; i < MAX_TASKS; i++)
	{
		q->pq[i].priority = -1;
		q->pq[i].taskid = -1;
	}
}
void dumpQueue(Queue *q)
{
	int i;
	if(debug)
		printf("\nLowest Priority -> Highest Priority");
	for(i = 0; i < q->size; i++)
	{
		if(debug)
			printf("\nTask id %d, task priority %d", q->pq[i].taskid, q->pq[i].priority);
		else
		{
			int id = q->pq[i].taskid;
			printf("\n%4d/%-4d%20s%4d%4d%-4s", id, tcb[id].parent,
					tcb[id].name, tcb[id].priority, tcb[id].time, "");
			if (tcb[i].signal & mySIGSTOP) printf("Paused");
			else if (tcb[id].state == S_NEW) printf("New");
			else if (tcb[id].state == S_READY) printf("Ready");
			else if (tcb[id].state == S_RUNNING) printf("Running");
			else if (tcb[id].state == S_BLOCKED) printf("Blocked");
			else if (tcb[id].state == S_EXIT) printf("Exiting");
		}
	}
}

// takes all elements out of queue and requeues them
void reorganizeQueue(Queue *q)
{
	int size = q->size;
	int ids[size];
	int i;
	for(i = 0; i < size; i++)
		ids[i] = dequeue(q);

	for(i = 0; i < size; i++)
		enqueue(ids[i] , q);
}


void enqueue(int taskid, Queue *q)
{
	int priority = -1;

	if(scheduler_mode == ROUND_ROBIN)
		priority = tcb[taskid].priority;
	else if(scheduler_mode == TIME_SLICE)
		priority = tcb[taskid].time;

	int i;
	for(i = q->size; i >= 0; i--)
	{
		if(i == 0 || priority > q->pq[i-1].priority)
		{
			q->pq[i].taskid = taskid;
			q->pq[i].priority = priority;
			break;
		}
		else
			q->pq[i] = q->pq[i-1];
	}
	q->size = q->size + 1;
}
int dequeue(Queue *q)
{
	if(q->size == 0)
		return -1; // no elements in the queue
	q->size = q->size - 1;
	int nextTaskId = q->pq[q->size].taskid; // next task
	q->pq[q->size].priority = -1; // set dequeued task spot to be -1
	q->pq[q->size].taskid = -1;
	return nextTaskId;
}
void takeout(int taskid, Queue *q)
{
	if(q->size == 0)
	{
		return; // no elements in the queue
	}
	int i;
	bool found = FALSE;
	for(i = 0; i < q->size; i++)
	{
		if(taskid == q->pq[i].taskid)
			found = TRUE;
		if(found)
		{
			q->pq[i] = q->pq[i+1];
			q->pq[i+1].taskid = -1;
			q->pq[i+1].priority = -1;
		}
	}
	if(found)
		q->size = q->size - 1;
}
int peek(Queue *q)
{
	if(q->size == 0)
		return -1;
	return q->pq[q->size - 1].taskid;
}

int queueTest(int argc, char* argv[])
{
	debug = TRUE;
	char buff[32];
	Queue newQueue;
	initQueue(&newQueue);
	int i;
	for(i = 1; i < 10; i++)
	{
		int id = i;
		int priority = 5; //rand() % 100;
		sprintf(buff, "task %d, priority %d", id, priority);
		enqueue(id , &newQueue);
		dumpQueue(&newQueue);
	}
	printf("\nInitial Queue:");
	dumpQueue(&newQueue);
	for(i = 0; i < 10; i--)
	{
		sprintf(buff, "\n  Removing taskid %d...", i);
		takeout(i, &newQueue);
		printf(buff);
		dumpQueue(&newQueue);
	}
	debug = FALSE;

	return 0;
}

