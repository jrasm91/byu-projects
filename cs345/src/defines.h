/*
 * defines.h
 *
 *  Created on: Jul 31, 2013
 *      Author: JRASM91
 */

#ifndef DEFINES_H_
#define DEFINES_H_

// ***********************************************************************
//
#define STARTUP_MSG	"CS345 Su2013"
#define SWAP swapTask();

// ***********************************************************************
// Thread-safe extensions: delete _s if desired
//#define SPRINTF sprintf_s
//#define STRCAT strcat_s

// ***********************************************************************
// Semaphore directives
#define SEM_WAIT(s)			semWait(s);
#define SEM_SIGNAL(s)		semSignal(s);
#define SEM_TRYLOCK(s)		semTryLock(s);

#define INTEGER(s)	((s)?(isdigit(*s)||(*s=='-')?(int)strtol(s,0,0):0):0)

#define MAX_TASKS 			127
#define MAX_STRING_SIZE		127
#define MAX_ARGS			50
#define MAX_HIST			50
#define STACK_SIZE			(64*1024/sizeof(int))
#define MAX_CYCLES			CLOCKS_PER_SEC/2
#define NUM_MESSAGES		500
#define INBUF_SIZE			256
#define ONE_TENTH_SEC		(CLOCKS_PER_SEC/10)
#define TEN_SEC				(CLOCKS_PER_SEC * 10)

#define LOW_PRIORITY		1
#define MED_PRIORITY		5
#define HIGH_PRIORITY		10
#define VERY_HIGH_PRIORITY	20
#define HIGHEST_PRIORITY	99

#define SHELL_TID			0
#define ALL_TID				-1

#define S_NEW				0
#define S_READY				1
#define S_RUNNING			2
#define S_BLOCKED			3
#define S_EXIT				4

#define FALSE				0
#define TRUE				1

#define BINARY				0
#define COUNTING			1

#define PAGE_FREE      		4
#define PAGE_GET_ADR   		3
#define PAGE_NEW_WRITE 		2
#define PAGE_OLD_WRITE 		1
#define PAGE_READ      		0
#define PAGE_INIT     		-1

#define SIG_SIGNAL(t,s)		sigSignal(t,s);
#define mySIGCONT			0x0001
#define mySIGINT			0x0002
#define mySIGKILL			0x0004
#define mySIGTERM			0x0008
#define mySIGTSTP			0x0010
#define mySIGSTOP			0x8000

#define POWER_UP					0
#define POWER_DOWN_ERROR      1
#define POWER_DOWN_QUIT       -2
#define POWER_DOWN_RESTART    -1

#define ROUND_ROBIN 0
#define TIME_SLICE 1

#define MAX_MESSAGE_SIZE		64

#define CDIR		tcb[curTask].cdir
#define TASK_RPT	tcb[curTask].RPT
#define DOS	1						// DOS
#define GCC	0						// UNIX/Linux
#define PPC	0						// Power PC
#define MAC	0						// Mac
#define NET	0						// .NET

#define INIT_OS
#define GET_CHAR		(kbhit()?getch():0)
#define SET_STACK(s)	__asm("movl _temp,%esp");
#define RESTORE_OS
#define LITTLE	1
#define CLEAR_SCREEN	system("cls");

#define SWAP_BYTES(v) 1?v:((((v)>>8)&0x00ff))|((v)<<8)
#define SWAP_WORDS(v) LITTLE?v:((SWAP_BYTES(v)<<16))|(SWAP_BYTES((v)>>16))

#endif /* DEFINES_H_ */
