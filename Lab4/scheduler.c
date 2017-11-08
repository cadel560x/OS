/* Javier Mantilla : Lab4 */
/* Job Scheduler */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//#include <conio.h>

#undef DEBUG
//#ifndef DEBUG
//	#define DEBUG
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

typedef struct process node_t;




// Functions' prototypes
// The following return the average wait time
float fcfs(node_t *fcfs_list);
float sjf(node_t *sjf_list);
float round_robin(const node_t *head_ptr, node_t *rr_list, unsigned short int quantum);

void fcfs_wrapper(const node_t *head_ptr);
void sjf_wrapper(const node_t *head_ptr);
void round_robin_wrapper(const node_t *head_ptr);


// Linked List prototypes
void sort(node_t *head_ptr);
void add_first_node(node_t **headPtr, char name[], unsigned int burst_time);
void add_node(node_t *head_ptr, char name[], unsigned int burst_time);
node_t * copy_list(const node_t *src_list);
void display_list(const node_t *head_ptr);
void delete_list(node_t *head_ptr);
unsigned short int size(const node_t * headPtr);
node_t * create_node(const char name[], unsigned int burst_time);
int search_node(char needle[], node_t *hay_stack);
void copy_node(node_t *dst, const node_t *src);

// Utilities prototypes
void capture_string(char * dst, const char *message);
int capture_integer(const char *message);
unsigned short int capture_short_uint(const char *message);
void print_title(const char *title);




int main(void) {
	// Variables
	// Input variables
	unsigned short int numberOfProcesses;
    unsigned short int input_burst_time;
	unsigned short int menuOption;
    char input_proc_name[10];

	// Output variables
    node_t * head_ptr;

	// Auxiliary variables
	unsigned short i;

	//  Initializing variables
	head_ptr = NULL;


	// Start program
	print_title("\nJob Scheduler");

	numberOfProcesses = capture_short_uint("Please enter the number of processes: ");
	puts("");

    // Start the linked list of processes
    // Capture first node
	if (head_ptr == NULL) {
        capture_string(input_proc_name, "Enter process name: ");
        input_burst_time = capture_short_uint("Enter the burst time for the process: ");
		puts("");
        
		add_first_node(&head_ptr, input_proc_name, input_burst_time);
		i = 1;
	}
	else {
		puts("Linked list pointer definition failed.");
		exit(2);
	}
    
    // Capture the rest of the nodes
	while ( i < numberOfProcesses ) {
		capture_string(input_proc_name, "Enter process name: ");
        input_burst_time = capture_short_uint("Enter the burst time for the process: ");
        add_node(head_ptr, input_proc_name, input_burst_time);
		puts("");
        
        i++;
	}

#ifdef DEBUG
    print_title("++DEBUG: main: Display list");
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
                fcfs_wrapper(head_ptr);
			break;
		case 2:
                sjf_wrapper(head_ptr);
			break;
		case 3:
                round_robin_wrapper(head_ptr);
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

//	getch();

	return EXIT_SUCCESS;

} // main




// Functions definitions

float fcfs(node_t *fcfs_list) {
	// Local variables
	// Auxiliary local variables
	unsigned short int number_processes;
	unsigned int start_time;
	unsigned int total_wait_time;
	node_t *temp;
	
	//	Initialize local variables
	start_time = 0;
	number_processes = 0;
	total_wait_time = 0;
	temp = fcfs_list;

	

	// Process
    // Print the heading row
    print_title("Process Name    Start Time    Wait Time");
    
    
	// Traverse 'fcfs_list'
	while (temp != NULL) {
        
		// The 'wait' time of the current process is value that is in 'accum_execution_time' so far
		temp->processTime.wait = start_time;
        
        // Process stamp display:
        printf("%12s    %10d    %9d\n", temp->name, start_time, temp->processTime.wait);
        
		// Add the 'burst' time of the current process to the 'accum_execution_time'
		start_time += temp->processTime.burst;
        
		// 'total_wait_time' for average wait time caculation
		total_wait_time += temp->processTime.wait;

		// Go to next node
		temp = temp->NEXT;
		// Keep the process count
		number_processes++;
	}

#ifdef DEBUG
	print_title("++DEBUG: fcfs: Display fcfs_list");
	if (fcfs_list == NULL) {
		puts("The list is empty.");
	}
	else {
		display_list(fcfs_list);
		printf("++DEBUG: fcfs: Accumulated execution time: %d\n", start_time);
		printf("++DEBUG: fcfs: Total wait time: %d\n", total_wait_time);
		printf("++DEBUG: fcfs: Number of processes: %d\n", number_processes);
	}
#endif

	// Calculate average wait time
	return (float)total_wait_time/(float)number_processes;

} // fcfs


float sjf(node_t *sjf_list)
{
	// Process
	// Sort the copylist
	sort(sjf_list);

#ifdef DEBUG
	print_title("++DEBUG: sfj: Display sjf_list");
	if (sjf_list == NULL) {
		puts("The list is empty.");
	}
	else {
		display_list(sjf_list);
	}
#endif
    
    // Return
	return fcfs(sjf_list);
    
} // sfj


float round_robin(const node_t *head_ptr, node_t *rr_list, unsigned short int quantum) {
	// Local variables
	// Auxiliary local variables
	unsigned short int number_processes;
    unsigned short int real_exec_time;
	unsigned int start_time;
	unsigned int total_wait_time;
    unsigned int list_loops;
    const node_t *orig_list;
    node_t *rest_of_nodes;
    unsigned int flag;
	char sentinel;
	node_t *temp;
	
	//	Initialize local variables
	number_processes = size(rr_list);
    
    temp = rr_list;
    rest_of_nodes = NULL;
    orig_list = head_ptr;
    
	total_wait_time = 0;
    real_exec_time = 0;
    start_time = 0;
    list_loops = 0;
	sentinel = 't'; // It's a boolean
    flag = 0;

	// Process
    // Print the heading row
    print_title("Process Name    Start Time    Remaining Time    Wait Time");
    
    // 'temp' is pointing to the head pointer of the 'rr_list'
    if (temp != NULL) {
        rest_of_nodes = temp->NEXT;
    }
    
	// Traverse 'rr_list'
	while ( sentinel == 't' && temp != NULL ) {
        
        // Check if this process is finished or if it has to be processed
		if (temp->processTime.burst != 0) {
            
            // Determine 'real_exec_time': 'burst' or 'quantum'
			// Avoid negative burst times. Set them to '0'
			if ( temp->processTime.burst < quantum ) {
                
                real_exec_time = temp->processTime.burst;
                
				temp->processTime.burst = 0;
			} else {
                
                real_exec_time = quantum;
                
                // The process' new 'burst' time is its 'burst' time minus the 'quantum'
                temp->processTime.burst -= quantum;
            } // if - else
			
			// The previous 'accum_execution_time' is added to the current process's 'wait' time
			// temp->processTime.wait = start_time;
            
            if (rest_of_nodes == NULL) {
                
                // Go back to the begining of the list
                rest_of_nodes = rr_list;
            }
            
            // Add the 'real_exec_time' to the 'wait' time of the 'rest_of_nodes' that are not finished
            while ( strcmp(rest_of_nodes->name, temp->name) != 0 ) { // Remember: 'rest_of_nodes' starts at the 'NEXT' node of the current processed node and it ends on this current node.
                
                // Check if the process is finished
                if (rest_of_nodes->processTime.burst > 0) {
                    rest_of_nodes->processTime.wait += real_exec_time;
                }
                
                rest_of_nodes = rest_of_nodes->NEXT;
                
                // Check if we are at the end of the list
                if (rest_of_nodes == NULL) {
                    
                    // Go back to the begining of the list
                    rest_of_nodes = rr_list;
                }
            } // while

			// Process stamp display:
			// Pname       Start Time              Remaining time                Wait time
			//             start_time        (temp->processTime.burst)    (temp->processTime.wait)
			//                                     [burst - quantums]
            printf("%12s    %10d    %14d    %9d\n", temp->name, start_time, temp->processTime.burst, temp->processTime.wait);
            
            // Add a 'real_exec_time' to the 'start_time'
			start_time += real_exec_time;
            
		} // if
        
        // Update 'flag's state, that is, sum of the 'burst' times
        flag += temp->processTime.burst;

		// Check if we are at the end of the list
		if (temp->NEXT == NULL) {
            
			// Go back to the begining of the list
			temp = rr_list;
            rest_of_nodes = temp->NEXT;
            
            
            // If 'flag' is not zero, there are still processes to be run
            if (flag) {
                
                // There are still processes to be finished, reset flag
                flag = 0;
            } else {
                
                // All processes are finished, exit 'while' loop
                sentinel = 'f';
            } // if - else
		}
        else {
            // This is not the last node, just move on
            temp = temp->NEXT;
            rest_of_nodes = temp->NEXT;
            
        }// if - else
	} // while

	// Restore original 'burst' times into 'rr_list' and get 'total_wait_time'
    temp = rr_list;
    
    while (orig_list != NULL) {
        temp->processTime.burst = orig_list->processTime.burst;
        total_wait_time += temp->processTime.wait;
        
        temp = temp->NEXT;
        orig_list = orig_list->NEXT;
    }

#ifdef DEBUG
	print_title("++DEBUG: rr_list: Display rr_list");
	if (rr_list == NULL) {
		puts("The list is empty.");
	}
	else {
		display_list(rr_list);
	}
#endif

    return (float)total_wait_time / (float)number_processes;;
    
} //round_robin


void fcfs_wrapper(const node_t *head_ptr) {
    // Local variables
    // Output local variables
    float average_wait_time;
    
	// Auxiliary local variables
	node_t *fcfs_list;
    
	//	Initialize local variables
	// Defensive copy, deep copy from the original list
	fcfs_list = copy_list(head_ptr);
    
	// Process
    print_title("First Come First Served");
    average_wait_time = fcfs(fcfs_list);
    printf("Average wait time: %.2f\n", average_wait_time);
    
} // fcfs_wrapper


void sjf_wrapper(const node_t *head_ptr) {
    // Local variables
	// Output local variables
    float average_wait_time;
    
    // Auxiliary local variables
	node_t *sjf_list;
    
	//	Initialize local variables
	// Defensive copy, deep copy from the original list
	sjf_list = copy_list(head_ptr);
    
	// Process
	print_title("Shortest Job First");
    average_wait_time = sjf(sjf_list);
    printf("Average wait time: %.2f\n", average_wait_time);
    
} // sjf_wrapper


void round_robin_wrapper(const node_t *head_ptr) {
    // Local variables
    // Input local variables
    unsigned short int quantum;
    
	// Output local variables
    float average_wait_time;
    
    // Auxiliary local variables
	node_t *rr_list;
    
    //	Initialize local variables
    // Defensive copy, deep copy from the original list
	rr_list = copy_list(head_ptr);
    
    //Process
    print_title("Round Robin");
    puts("");
    quantum = capture_short_uint("Enter a quantum: ");
    
    average_wait_time = round_robin(head_ptr, rr_list, quantum);
    printf("Average wait time:  %.2f\n", average_wait_time);
    
} // round_robin_wrapper




// Linked List implementation

void sort(node_t *head_ptr) {
	//	Local variables
	//	Local auxiliary local variables
	node_t *temp;
	node_t *count;
	node_t *temporal_node;

	//	Initialise local variables
	temporal_node = (node_t *)malloc(sizeof(node_t));
	temp = head_ptr;

	//  Process
	//  Selection sort
	for (; temp->NEXT != NULL; temp = temp->NEXT)
		for (count = temp->NEXT; count != NULL; count = count->NEXT)
			if (temp->processTime.burst > count->processTime.burst) {
				// Sorting criteria met
				copy_node(temporal_node, temp);
				copy_node(temp, count);
				copy_node(count, temporal_node);
			}

} // sort


void add_first_node(node_t **head_ptr, char name[], unsigned int burst_time) {
    //	Local variables
    //	Local auxiliary local variables
	node_t *new_node;
    
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

void add_node(node_t * head_ptr, char name[], unsigned int burst_time) {
    //	Local variables
    //	Local auxiliary local variables
	node_t *temp;
    
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


node_t * copy_list(const node_t *src_list)
{
	// Local variables
	// Local auxiliary local variables
	node_t *temp_src, *temp_dst;
	node_t *dst_list;

	// Initialize local variables
	dst_list = NULL;

	// Process
	// Add a first dummy node to the 'new_list' and make it a copy of the first node of 'head_prt' list
	add_first_node(&dst_list, "", 0);
	copy_node(dst_list, src_list);

	// Set the 'temp_src' to the second node in the 'src_list' because the first node has been already copied
	temp_src = src_list->NEXT;

	// 'temp_dst' is one node behind 'temp_src'
	temp_dst = dst_list;

	// Traverse the 'src_list'
	while (temp_src != NULL) {
		// Create a dummy node in 'dst_list' and copy the current node in 'src_list' into it
		temp_dst->NEXT = create_node("", 0);
		copy_node(temp_dst->NEXT, temp_src);

		// Move forward in the lists
		temp_src = temp_src->NEXT;
		temp_dst = temp_dst->NEXT;
	}

#ifdef DEBUG
	print_title("++DEBUG: copy_list: Display src_list");
	if (src_list == NULL) {
		puts("The list is empty.");
	}
	else {
		display_list(src_list);
	}

	print_title("++DEBUG: copy_list: Display dst_list");
	if (dst_list == NULL) {
		puts("The list is empty.");
	}
	else {
		display_list(dst_list);
	}
#endif


	return dst_list;

} // copy_list


void display_list(const node_t *head_ptr) {
	//	Local variables
	//	Local output local variables
	const node_t *temp;

	//	Initialize local variables
	temp = head_ptr;

	//	Process
	//  Loop til the end of the list
	while (temp != NULL) {
		printf("Process name: %s\n", temp->name);
		printf("Process burst time: %d\n", temp->processTime.burst);
		printf("Process wait time: %d\n", temp->processTime.wait);
		puts("");

		temp = temp->NEXT;
	}

} // display_list


void delete_list(node_t * head_ptr)
{
	//	Local variables
	//	Local auxiliary variables
	node_t *temp;
	node_t *previous_node;

	// Initialize local variables
	// Set to the first node
	previous_node = head_ptr;
	temp = head_ptr->NEXT;

	//	Process
	//  Loop til the end of the list
	while (temp != NULL) {
		// Free the node
		free(previous_node);

		// Move forward
		previous_node = temp;
		temp = temp->NEXT;
	}

	head_ptr = NULL;

} // delete_list


unsigned short int size(const node_t * headPtr) {
	//	Local variables
	//	Local auxiliary local variables
	const node_t *temp;
	int i;

	//	Initialise local variables
	temp = headPtr;
	i = 0;

	//	Process
	//  Loop til the end of the list
	while (temp != NULL) {
		temp = temp->NEXT;
		++i;
	}

	return i;

} // size


node_t * create_node(const char name[], unsigned int burst_time) {
    //	Local variables
    //	Local auxiliary local variables
	node_t *new_node;
    
    //	Initialize local variables
	new_node = (node_t *)malloc(sizeof(node_t));
    if (new_node == NULL) {
		puts("Memory allocation failed.");
		exit(1);
	}
    
    //	Process
	strcpy(new_node->name, name);
    new_node->processTime.burst = burst_time;
	new_node->processTime.wait = 0;
	new_node->NEXT = NULL;
    
	return new_node;
    
} // create_node

int search_node(char needle[], node_t * hay_stack) {
    //	Local variables
    //	Local auxiliary local variables
	node_t *temp;
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


void copy_node(node_t *dst, const node_t *src) {
	//  Process
	strcpy(dst->name, src->name);
	dst->processTime.burst = src->processTime.burst;
	dst->processTime.wait = src->processTime.wait;

} // copy_node




// Utilities implementation

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


unsigned short int capture_short_uint(const char *message) {
    //	Local variables
    //	Local input variables
	unsigned short int integer;
    
    //	Process
	printf("%s", message);
	scanf("%hd%*c", &integer);
    
	return integer;
    
} // capture_short_uint


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