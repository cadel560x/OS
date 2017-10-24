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
	cpuTime processTime;
} process_t;

//

int main(void) {
	// Variables
	// Input variables
	unsigned short int numberOfProcesses;
	unsigned short int menuOption;

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

	printf("Enter process name: ");
	scanf("%d", (processesArray + i)->name );

	//for (i = 0; i < numberOfProcesses; i++ ) {
	//	printf("Enter process name: ");
	//	gets((processesArray + i)->name );
	//	printf("Enter the burst time for the process: ");
	//	scanf("%d", &(processesArray + i)->processTime.burst);
	//	puts("");
	//}

	// Scheduler menu
	puts("Please select a scheduler algorithm:");
	do {
		puts("Press 1 for First Come First Served (FCFS)");
		puts("Press 2 for Shortest Job First (SJF)");
		puts("Press 3 for Round Robin (RR)");
		puts("Press 4 to exit.");
		printf("Option: ");
		scanf("%d", &menuOption);

		switch (menuOption) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		default:
			puts("Invalid option. Please verify and try again.");
		} // switch

	} while ( menuOption != 4 );

	puts("Bye-bye");

	free(processesArray);

	getch();

	return 0;

} // main