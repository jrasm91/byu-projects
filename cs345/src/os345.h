// os345.h
#ifndef __os345_h__
#define __os345_h__

#include <setjmp.h>
#include <conio.h>

#include "defines.h"
#include "queue.h"
#include "structs.h"

int createTask(char*, int (*)(int, char**), int, int, char**);
int killTask(int taskId);
void powerDown(int code);
void swapTask(void);

int getMessage(int from, int to, Message* msg);
int postMessage(int from, int to, char* msg);
char* myTime(char*);

Semaphore* createSemaphore(char* name, int type, int state);
bool deleteSemaphore(Semaphore** semaphore);

void semSignal(Semaphore*);
int semWait(Semaphore*);
int semTryLock(Semaphore*);

int sigAction(void (*sigHandler)(void), int sig);
int sigSignal(int taskId, int sig);
void initLC3Memory(int startFrame, int endFrame);

int atmTask(int, char**);
int bankTask(int, char**);
int dmeTask(int, char**);
int lc3Task(int, char**);

unsigned short int *getMemAdr(int va, int rwFlg);
void outPTE(char* s, int pte);
int accessPage(int pnum, int frame, int rwnFlg);

void recalculateTime();
int recalculateTimeCommand(int argc, char* argv[]);

int P1_project1(int, char**);
int P1_shellTask(int, char**);
int P1_help(int, char**);
int P1_quit(int, char**);
int P1_lc3(int, char**);
int P1_add(int, char**);

int P2_project2(int, char**);
int P2_killTask(int, char**);
int P2_listSems(int, char**);
int P2_listTasks(int, char**);
int P2_reset(int, char**);
int P2_signal1(int, char**);
int P2_signal2(int, char**);
int ImAliveTask1(int argc, char* argv[]);
int ImAliveTask2(int argc, char* argv[]);
int semaphoreTest(int argc, char* argv[]);

int P3_project3(int, char**);
int P3_dc(int, char**);
int P3_tdc(int, char**);
int P3_carTask(int argc, char* argv[]);
int P3_diverTask(int argc, char* argv[]);
int P3_visitorTask(int argc, char* argv[]);
int P3_driverTask(int argc, char* argv[]);

int P4_project4(int, char**);
int P4_dumpFrame(int, char**);
int P4_dumpFrameTable(int, char**);
int P4_dumpLC3Mem(int, char**);
int P4_dumpPageMemory(int, char**);
int P4_dumpVirtualMem(int, char**);
int P4_initMemory(int, char**);
int P4_rootPageTable(int, char**);
int P4_userPageTable(int, char**);
int P4_vmaccess(int, char**);
int P4_virtualMemStats(int, char**);
int P4_crawler(int, char**);
int P4_memtest(int, char**);
int P4_getFrameTest(int, char**);
void displayAllUPT(int rptNum);

int P5_project5(int, char**);
int P5_stress1(int, char**);
int P5_stress2(int, char**);

int P6_project6(int, char**);
int P6_cd(int, char**);
int P6_dir(int, char**);
int P6_dfat(int, char**);
int P6_mount(int, char**);
int P6_run(int, char**);
int P6_space(int, char**);
int P6_type(int, char**);
int P6_dumpSector(int, char**);
int P6_fileSlots(int, char**);

int P6_copy(int, char**);
int P6_define(int, char**);
int P6_del(int, char**);
int P6_mkdir(int, char**);
int P6_unmount(int, char**);
int P6_chkdsk(int, char**);
int P6_finalTest(int, char**);

int P6_open(int, char**);
int P6_read(int, char**);
int P6_write(int, char**);
int P6_seek(int, char**);
int P6_close(int, char**);

/*
int P5_atm(int, char**);
int P5_listMessages(int, char**);
 */

#endif // __os345_h__
