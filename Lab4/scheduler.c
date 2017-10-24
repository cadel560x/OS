/* Javier Mantilla : Lab4 */
/* Job Scheduler */
#include<stdio.h>
#include<conio.h>

// Data structures
typedef struct {
	unsigned int burst;
	unsigned int wait;
} cpuTime;


typedef struct {
	char name[10];
	cpuTime time;
} process_t;

//

int main(void) {
	// Variables
	// Input variables
	unsigned short int numberOfProcesses;

	// Output variables


	// Auxiliary variables
	process_t * processesArray;
	unsigned short i;

	//  Initializing variables

	// Start program
	puts("Job Scheduler\n-------------\n");

	printf("Please enter the number of processes: ");
	scanf("%d", &numberOfProcesses);
	puts("");

	processesArray = (process_t *)malloc(numberOfProcesses * sizeof(process_t));
	if (processesArray == NULL) {
		puts("Memory allocation failed.");
		exit(1);
	}

	for (i = 0; i < numberOfProcesses; i++ ) {
		printf("Enter process name: ");
		scanf("%s", (processesArray + i)->name );
		printf("Enter the burst time for the process: ");
		scanf("%d", &(processesArray + i)->time.burst);
		puts("");
	}

	// Scheduler menu



	free(processesArray);

	getch();

	return 0;

} // main