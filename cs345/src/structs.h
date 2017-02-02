/*
 * structs.h
 *
 *  Created on: Jul 31, 2013
 *      Author: JRASM91
 */

#ifndef STRUCTS_H_
#define STRUCTS_H_

typedef int bool;						// boolean value
typedef int TID;						// task id

typedef struct semaphore			// semaphore
{
	struct semaphore* semLink;		// semaphore link
	char* name;							// semaphore name
	int state;							// semaphore state
	int type;							// semaphore type
	int taskNum;						// semaphore creator task #
	Queue bq;
} Semaphore;

typedef struct							// task control block
{
	char* name;							// task name
	int (*task)(int,char**);		// task address
	int state;							// task state
	int priority;						// task priority (project 2)
	int argc;							// task argument count (project 1)
	char** argv;						// task argument pointers (project 1)
	int signal;							// task signals (project 1)
	void (*sigContHandler)(void);	// task mySIGCONT handler
	void (*sigIntHandler)(void);	// task mySIGINT handler
	void (*sigTermHandler)(void);	// task mySIGTERM handler
	void (*sigTstpHandler)(void);	// task mySIGTSTP handler
	TID parent;							// task parent
	int time;
	int RPT;								// task root page table (project 5)
	int cdir;							// task directory (project 6)
	Semaphore *event;					// blocked task semaphore
	void* stack;						// task stack
	jmp_buf context;					// task context pointer
} TCB;

typedef struct
{
	char* line;
} Line;

typedef struct
{
	int from;                  // source
	int to;                    // destination
	char* msg;						// msg
} Message;

#endif /* STRUCTS_H_ */
