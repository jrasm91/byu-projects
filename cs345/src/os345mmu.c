// os345mmu.c - LC-3 Memory Management Unit
// **************************************************************************
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
#include "os345lc3.h"

// ***********************************************************************
// mmu variables

// LC-3 memory
unsigned short int memory[LC3_MAX_MEMORY];

// statistics
int memAccess;						// memory accesses
int memHits;						// memory hits
int memPageFaults;					// memory faults
int nextPage;						// swap page size
int pageReads;						// page reads
int pageWrites;						// page writes

int getFrame(int);
int getAvailableFrame(void);
int swapOutFrame(int me);
void save(int address, int part1, int part2);

extern TCB tcb[MAX_ARGS];
extern int curTask;


// **************************************************************************
// **************************************************************************
// LC3 Memory Management Unit
// Virtual Memory Process
// **************************************************************************
//           ___________________________________Frame defined
//          / __________________________________Dirty frame
//         / / _________________________________Referenced frame
//        / / / ________________________________Pinned in memory
//       / / / /     ___________________________
//      / / / /     /                 __________frame # (0-1023) (2^10)
//     / / / /     /                 / _________page defined
//    / / / /     /                 / /       __page # (0-4096) (2^12)
//   / / / /     /                 / /       /
//  / / / /     / 	             / /       /
// F D R P - - f f|f f f f f f f f|S - - - p p p p|p p p p p p p p

int frame_test = FALSE;

int P4_getFrameTest(int argc, char* argv[])
{
	frame_test = TRUE;

	printf("\nFrame Test");
	P4_initMemory(argc, argv);
	int addrs[9] = {0x3000, 0x3001, 0x3040, 0x3041, 0xEF92, 0xD851, 0xD833, 0x3833, 0x3000};
	int i;
	for(i = 0; i < 9; i++)
	{
		printf("\ni:%d, va:0x%04x, ", i, addrs[i]);
		getMemAdr(addrs[i], 0);
	}
	printf("\nRoot Page Table 0");
	//	displayRPT(0);
	//	displayAllUPT(0);
	//	swapOutFrame(-1);

	frame_test = FALSE;
	return 0;
}

unsigned short int *getMemAdr(int va, int rwFlg)
{
	unsigned short int pa;
	int rpta, rpte1, rpte2;
	int upta, upte1, upte2;
	int uptFrame, rptFrame;

	rpta = TASK_RPT + RPTI(va);
	rpte1 = memory[rpta];
	rpte2 = memory[rpta+1];

	// turn off virtual addressing for system RAM
	if (va < 0x3000)
		return &memory[va];

	memAccess++;

	// checks first bit (15 mask) if it is a 1 or a 0 (of root page table)
	if (DEFINED(rpte1))
	{
		memHits++;
	}
	else
	{
		memPageFaults++;
		// not connected to a user page to finds it in memory or creates a new one
		rptFrame = getFrame(-1);
		rpte1 = SET_DEFINED(rptFrame);
		// will be pinned because it will have a new child
		rpte1 = SET_PINNED(rpte1);
		if (PAGED(rpte2))
			accessPage(SWAPPAGE(rpte2), rptFrame, PAGE_READ);
		else
		{
			rpte1 = SET_DIRTY(rpte1);
			rpte2 = 0;
			memset(&memory[(rptFrame<<6)], 0, 128);
		}
	}
	rpte1 = SET_REF(rpte1);
	// sets rtpa reference bit and saves change in memory
	save(rpta, rpte1, rpte2);

	// gets user page table address
	upta = (FRAME(rpte1)<<6) + UPTI(va);
	upte1 = memory[upta];
	upte2 = memory[upta+1];

	// checks first bit (15 mask) if it is a 1 or a 0 (of user page table)
	if (DEFINED(upte1))
	{
		memHits++;
	}
	else
	{
		memPageFaults++;
		// not already a frame so get it from memory or create one
		uptFrame = getFrame(rpta);
		upte1 = SET_DEFINED(uptFrame);
		upte1 = SET_DIRTY(upte1);
		if (PAGED(upte2))
			accessPage(SWAPPAGE(upte2), uptFrame, PAGE_READ);
		else
		{
//			upte1 = SET_DIRTY(upte1);
			upte2 = 0;
		}
		//			memset(&memory[uptFrame], 0, 128);
	}

	upte1 = SET_REF(upte1);
	// set reference bit, and save upta changes to memory
	save(upta, upte1, upte2);

	pa = (FRAME(upte1)<<6) + FRAMEOFFSET(va);
	if(frame_test)
		printf("pa:0x%04x", pa);
	return &memory[pa];
} // end getMemAdr


int getFrame(int me)
{
	int frame;
	frame = getAvailableFrame();
	// swap out a frame
	if(frame == -1)
		frame = swapOutFrame(me);
	if(frame == -1)
	{
		printf("we have a problem");
		exit(-4);
	}
	return frame;
}
int swapOutFrame(int myRPTA)
{
	int rpta, rpte1, rpte2;
	int upta, upte1, upte2;

	// iterate through all RPT's (checks for UPT's)
	while(TRUE)
	{
		int startRPT = TASK_RPT;
		int endRPT = startRPT + LC3_FRAME_SIZE;
		for(rpta = startRPT; rpta < endRPT; rpta += 2)
		{
			rpte1 = memory[rpta];
			rpte2 = memory[rpta+1];

			if(DEFINED(rpte1))
			{
				bool pinned = FALSE;
				int startUPT = FRAME(rpte1)<<6;
				int endUPT = startUPT + LC3_FRAME_SIZE;
				// iterates through all children of UPT
				for(upta = startUPT; upta < endUPT; upta+= 2)
				{
					upte1 = memory[upta];
					upte2 = memory[upta+1];

					if(DEFINED(upte1))
					{
						pinned = TRUE;
						if(REFERENCED(upte1))
						{
							upte1 = CLEAR_REF(upte1);
							memory[upta] = upte1;
							memory[rpta] = SET_DIRTY(rpte1);
						}
						else
						{
							int nextFrame = FRAME(upte1);
							if(DIRTY(upte1))
							{
								if(PAGED(upte2))
									upte2 = accessPage(
											SWAPPAGE(upte2),
											nextFrame,
											PAGE_OLD_WRITE);
								else
									upte2 = accessPage(
											SWAPPAGE(upte2),
											nextFrame,
											PAGE_NEW_WRITE);
								upte2 = SET_PAGED(upte2);
							}
							save(upta, 0, upte2);
							return nextFrame;
						}
					}
				}
				if(rpta == myRPTA)
					continue;

				if(!pinned)
				{
					rpte1 = CLEAR_PINNED(rpte1);
					memory[rpta] = rpte1;

					if (REFERENCED(rpte1))
					{
						rpte1 = CLEAR_REF(rpte1);
						rpte1 = SET_DIRTY(rpte1);
						memory[rpta] = rpte1;
					}
					else
					{
						int nextFrame = FRAME(rpte1);
						if(DIRTY(rpte1))
						{
							if(PAGED(rpte2))
								rpte2 = accessPage(
										SWAPPAGE(rpte2),
										nextFrame,
										PAGE_OLD_WRITE);
							else
								rpte2 = accessPage(
										SWAPPAGE(rpte2),
										nextFrame,
										PAGE_NEW_WRITE);
						}
						save(rpta, 0, SET_PAGED(rpte2));
						return nextFrame;
					}
				}
			}
		}
	}
	return -1;
}


// **************************************************************************
// **************************************************************************
// set frames available from sf to ef
//    flg = 0 -> clear all others
//        = 1 -> just add bits
//
void setFrameTableBits(int flg, int sf, int ef)
{	int i, data;
int adr = LC3_FBT-1;             // index to frame bit table
int fmask = 0x0001;              // bit mask

// 1024 frames in LC-3 memory
for (i=0; i<LC3_FRAMES; i++)
{	if (fmask & 0x0001)
{  fmask = 0x8000;
adr++;
data = (flg)?MEMWORD(adr):0;
}
else fmask = fmask >> 1;
// allocate frame if in range
if ( (i >= sf) && (i < ef)) data = data | fmask;
MEMWORD(adr) = data;
}
return;
} // end setFrameTableBits


// **************************************************************************
// get frame from frame bit table (else return -1)
int getAvailableFrame()
{
	int i, data;
	int adr = LC3_FBT - 1;				// index to frame bit table
	int fmask = 0x0001;					// bit mask

	for (i=0; i<LC3_FRAMES; i++)		// look thru all frames
	{	if (fmask & 0x0001)
	{  fmask = 0x8000;				// move to next work
	adr++;
	data = MEMWORD(adr);
	}
	else fmask = fmask >> 1;		// next frame
	// deallocate frame and return frame #
	if (data & fmask)
	{  MEMWORD(adr) = data & ~fmask;
	return i;
	}
	}
	return -1;
} // end getAvailableFrame

// **************************************************************************
// read/write to swap space
int accessPage(int pnum, int frame, int rwnFlg)
{
	static unsigned short int swapMemory[LC3_MAX_SWAP_MEMORY];
	if ((nextPage >= LC3_MAX_PAGE) || (pnum >= LC3_MAX_PAGE))
	{
		printf("\nVirtual Memory Space Exceeded!");
		printf("\npnum: (%d), lc3_max_page %d", pnum, LC3_MAX_PAGE);
		exit(-4);
	}
	switch(rwnFlg)
	{
	case PAGE_INIT:                    		// init paging
		nextPage = 0;
		return 0;

	case PAGE_GET_ADR:                    	// return page address
		return (int)(&swapMemory[pnum<<6]);

	case PAGE_NEW_WRITE:                   // new write (Drops thru to write old)
		pnum = nextPage++;

	case PAGE_OLD_WRITE:                   // write
		//printf("\n    (%d) Write frame %d (memory[%04x]) to page %d", p.PID, frame, frame<<6, pnum);
		memcpy(&swapMemory[pnum<<6], &memory[frame<<6], 1<<7);
		pageWrites++;
		return pnum;

	case PAGE_READ:                    // read
		//printf("\n    (%d) Read page %d into frame %d (memory[%04x])", p.PID, pnum, frame, frame<<6);
		memcpy(&memory[frame<<6], &swapMemory[pnum<<6], 1<<7);
		pageReads++;
		return pnum;

	case PAGE_FREE:                   // free page
		break;
	}
	return pnum;
} // end accessPage

void save(int address, int part1, int part2)
{
	memory[address] = part1;
	memory[address + 1] = part2;
}
