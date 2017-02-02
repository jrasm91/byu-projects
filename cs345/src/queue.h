/*
 * queue.h
 *
 *  Created on: Jul 18, 2013
 *      Author: JRASM91
 */

#ifndef QUEUE_H_
#define QUEUE_H_

#define MAX_QUEUE 127

typedef struct element
{
	int priority;
	int taskid;
} Element;

typedef struct queue
{
	Element pq[MAX_QUEUE];
	int size;
} Queue;

void initQueue(Queue *q);
int peek(Queue *q);
int dequeue(Queue *q);
void takeout(int taskid, Queue *q);
void enqueue(int taskid, Queue *q);
int queueTest(int, char**);
void dumpQueue(Queue *q);
void reorganizeQueue(Queue *q);

#endif /* QUEUE_H_ */
