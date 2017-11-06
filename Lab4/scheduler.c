/* Javier Mantilla : Lab4 */
/* Job Scheduler */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//#include<conio.h>
//#ifdef DEBUG
//#undef DEBUG
//#endif

// Data structures
typedef struct {
	unsigned int burst;
	unsigned int wait;
    
} cpuTime;


struct process {
	char name[10];
	cpuTime processTime;
    struct process *NEXT;
};

typedef struct process process_t;


// Functions' prototypes
void add_first_node(process_t **headPtr, char name[], unsigned int burst_time);
void add_node(process_t *head_ptr, char name[], unsigned int burst_time);
process_t * create_node(char name[], unsigned int burst_time);
int search_node(char needle[], process_t *hay_stack);
void display_list(process_t *headPtr);
void capture_string(char * dst, const char *message);
int capture_integer(const char *message);
void print_title(const char *title);


int main(void) {
	// Variables
	// Input variables
	unsigned short int numberOfProcesses;
    unsigned short int input_burst_time;
	unsigned short int menuOption;
    char input_proc_name[10];

	// Output variables
    process_t * head_ptr;

	// Auxiliary variables
	unsigned short i;

	//  Initializing variables


	// Start program
	puts("Job Scheduler\n-------------\n");

	printf("Please enter the number of processes: ");
	scanf("%hd", &numberOfProcesses);
	puts("");

    // Start the linked list of processes
    printf("Enter process name: ");
    scanf("%s", input_proc_name );
    printf("Enter the burst time for the process: ");
    scanf("%hd", &input_burst_time);
    puts("");
    add_first_node(&head_ptr, input_proc_name, input_burst_time);
    i = 1;
    
	while ( i < numberOfProcesses ) {
		printf("Enter process name: ");
		scanf("%s", input_proc_name );
		printf("Enter the burst time for the process: ");
		scanf("%hd", &input_burst_time);
        add_node(head_ptr, input_proc_name, input_burst_time);
		puts("");
        
        i++;
	}

#ifdef DEBUG
    printTitle("++DEBUG: Display list");
    if (head_ptr == NULL) {
        puts("The list is empty.");
    }
    else {
        display_list(head_ptr);
    }
#endif
    
	// Scheduler menu
	puts("Please select a scheduler algorithm:");
	do {
		puts("Press 1 for First Come First Served (FCFS)");
		puts("Press 2 for Shortest Job First (SJF)");
		puts("Press 3 for Round Robin (RR)");
		puts("Press 4 to exit.");
		printf("Option: ");
		scanf("%hd", &menuOption);

		switch (menuOption) {
		case 1:
                print_title("First Come First Served");
//                fcfs(head_ptr);
			break;
		case 2:
                print_title("Shortest Job First");
//                sjf(head_ptr);
			break;
		case 3:
                print_title("Round Robin");
//                round_robin(head_ptr);
			break;
        case 4:
            // Do nothing, just exit.
            break;
		default:
			puts("Invalid option. Please verify and try again.");
		} // switch
        
        puts("");

	} while ( menuOption != 4 );

	puts("Bye-bye");

//	free(processesArray);

//	getch();

	return EXIT_SUCCESS;

} // main




// Functions definitions

void add_first_node(process_t **head_ptr, char name[], unsigned int burst_time) {
    //	Local variables
    //	Local auxiliary local variables
	process_t *new_node;
    
    //	Process
	if ( search_node(name, *head_ptr) == 0 ) {
        //	This 'id' doesn't exists so we create a new node
		new_node = create_node(name, burst_time);
        //	The '->NEXT' of the newly created node points where 'headPtr' points
		new_node->NEXT = *head_ptr;
        //	Now headPtr points to the newly created node
		*head_ptr = new_node;
	}
	else
		puts("This entry already exists.");
    
} // add_first_node

void add_node(process_t * head_ptr, char name[], unsigned int burst_time) {
    //	Local variables
    //	Local auxiliary local variables
	process_t *temp;
    
    //	Initialize local variables
	temp = head_ptr;
    
    //	Process
	if ( search_node(name, head_ptr) == 0 ) {
        // Loop til the end of the list
		while (temp->NEXT != NULL) {
			temp = temp->NEXT;
        }
        
        //	The last '->NEXT' is not 'NULL' anymore
		temp->NEXT = create_node(name, burst_time);
        //	Now we iterate manually one more time, so now temp points to the newly created node
		temp = temp->NEXT;
        //	And make the '->NEXT' of the newly created node 'NULL' because is the last one in the list
		temp->NEXT = NULL;
	}
	else
		puts("This entry already exists.");
    
} // add_node


process_t * create_node(char name[], unsigned int burst_time) {
    //	Local variables
    //	Local auxiliary local variables
	process_t *new_node;
    
    //	Initialize local variables
	new_node = (process_t *)malloc(sizeof(process_t));
    if (new_node == NULL) {
		puts("Memory allocation failed.");
		exit(1);
	}
    
    //	Process
	strcpy(new_node->name, name);
    
//	capture_string(new_node->name, "Please enter new process' name: ");
//	new_node->processTime.burst = capture_integer("Please enter new process' burst time: ");
    new_node->processTime.burst = burst_time;
    
	return new_node;
    
} // create_node

int search_node(char needle[], process_t * hay_stack) {
    //	Local variables
    //	Local auxiliary local variables
	process_t *temp;
    int found;
	int i;
    
    //	Initialize local variables
    temp = hay_stack;
    found = 0;
	i = 0;
	
    //	Process
    if (temp != NULL) {
        while (temp != NULL) {
            ++i;
            if (strcmp(needle, temp->name) == 0) {
                return found = i;
            }
            
            temp = temp->NEXT;
        }
    }
    
	return found;
    
} // search_node


void display_list(process_t *head_ptr) {
    //	Local variables
    //	Local output local variables
	process_t *temp;
    
    //	Initialize local variables
	temp = head_ptr;
    
    //	Process
    //  Loop til the end of the list
	while ( temp != NULL ) {
		printf("Process name: %s\n", temp->name);
		printf("Process burst time: %d\n", temp->processTime.burst);
		printf("Process wait time: %d\n", temp->processTime.wait);
		puts("");
        
		temp = temp->NEXT;
	}
    
} // display_list


void capture_string(char * dst, const char *message) {
    //	Process
	printf("%s", message);
	gets(dst);
    
} // capture_string


int capture_integer(const char *message) {
    //	Local variables
    //	Local input variables
	int integer;
    
    //	Process
	printf("%s", message);
	scanf("%d%*c", &integer);
    
	return integer;
    
} // capture_integer


void print_title(const char *message) {
    //	Local variables
    //	Local auxiliary local variables
	unsigned long length;
	int i;
    
    //	Initialize local variables
	length = strlen(message);
    
    //	Process
	puts("");
    
	puts(message);
	for (i = 0; i < length; i++)
		printf("-");
    
	puts("");
    
} // print_title