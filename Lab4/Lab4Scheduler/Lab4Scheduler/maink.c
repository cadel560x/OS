/* Javier Mantilla : Lab8 */
/* Student list           */
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

// Constants
#define OUTPUT_FILE "students.txt"

// Structs
struct student {
	char id[20];
	char name[40];
	int age;
	float average;
	char email[40];
	char mobile[40];
	struct student *NEXT;
};

typedef struct student student_t; // I saw this on Deitel & Deitel's book

// Functions' prototypes
void addStudentAtStart(char id[], student_t **headPtr);
void addStudent(char id[], student_t *headPtr);
int size(student_t *headPtr);
void display(student_t *headPtr);
void deleteLastStudent(student_t *headPtr);
void printToFile(student_t *headPtr);
int searchStudent(char needle[], student_t * hayStack);
void deleteFirstStudent(student_t *headPtr);
void addStudentAt(int position, char id[], student_t *headPtr);
void deleteStudentAt(int position, student_t *headPtr);

// Auxiliary functions' prototypes
student_t * createStudent(char id[]);
void printStudent(int position, student_t *headPtr);
int captureInteger(char *message);
int captureFloat(char *message);
void captureString(char * dst, char * message);
void printTitle(char *title);


int main(void) {
//	Variables
//	Input variables
	student_t *headPtr;
	char id[20];
	int option;
	int position;

//	Output variables
//	'*headPtr' as declared above
//	'position' as declared above

//  Initializing variables
	headPtr = NULL;

//	Process
	do {
		printTitle("Student List");
		puts("1) Add a student at the end of the list.");
		puts("2) Add a student at the top of the list.");
		puts("3) Number of students in the list.");
		puts("4) Delete a student.");
		puts("5) Display student list.");
		puts("6) Print student list to a file.");
		puts("7) Search student.");
		puts("8) Delete student form the start.");
		puts("9) Insert a student by location.");
		puts("10) Delete a student by location.");

		printf("Press -1 to exit: ");

		scanf("%d%*c", &option);

		switch (option) {
		case 1:
			printTitle("Add a student at the end");
			captureString(id, "Please enter new student's id: ");
			if (headPtr == NULL)
				addStudentAtStart(id, &headPtr);
			else
				addStudent(id, headPtr);
			break;
		case 2:
			printTitle("Add a student at the top");
			captureString(id, "Please enter new student's id: ");
			addStudentAtStart(id, &headPtr);
			break;
		case 3:
			printTitle("Number of students");
			if (headPtr == NULL)
				puts("The list is empty.");
			else
				printf("The size of the list is: %d\n", size(headPtr));
			break;
		case 4:
			printTitle("Delete the last student in the list");
			if (headPtr == NULL)
				puts("The list is empty. Nothing to delete");
			else if (size(headPtr) == 1)
				deleteFirstStudent(headPtr);
			else
				deleteLastStudent(headPtr);
			break;
		case 5:
			printTitle("Display list");
			if (headPtr == NULL)
				puts("The list is empty.");
			else
				display(headPtr);
			break;
		case 6:
			printTitle("Print to output file");
			if (headPtr == NULL)
				puts("The list is empty.");
			else
				printf("Printing output to file %s", OUTPUT_FILE);
			break;
		case 7:
			printTitle("Search for a student");
			if (headPtr == NULL)
				puts("The list is empty.");
			else {
				captureString(id, "Please enter the student's id: ");
				position = searchStudent(id, headPtr);
				if (position == 0)
					puts("Student not found");
				else {
					printf("Student found at position %d:\n", position);
					printStudent(position, headPtr);
				}
			}
			break;
		case 8:
			printTitle("Delete the first student");
			if (headPtr == NULL)
				puts("The list is empty.");
			else
				deleteFirstStudent(headPtr);
			break;
		case 9:
			printTitle("Add a student at arbitrary position");
			position = captureInteger("Please enter position of insertion: ");
			captureString(id, "Please enter new student's id: ");

			if (headPtr == NULL) {
                puts("Empty list. Adding student at the beginning");
				addStudentAtStart(id, &headPtr);
			}
			else if (position <= 0 || position > size(headPtr))
                puts("Incorrect position.");
            else
				addStudentAt(position, id, headPtr);
			break;
		case 10:
		    printTitle("Delete a student at arbitrary position");
		    position = captureInteger("Please enter position to delete: ");

			if (headPtr == NULL)
                puts("Empty list. Nothing to delete");
			else if (position <=0 || position > size(headPtr))
                puts("Incorrect position.");
            else
				deleteStudentAt(position, headPtr);
			break;
		case -1:
			break; // just exit
		default:
			puts("Invalid option please try again");
//			Clearing the input buffer so the next scanf will work as should do
			while ((option = getchar()) != '\n' && option != EOF); // As seen in StackOverflow
		}

	} while (option != -1);

	puts("\nThank you for using my student linked list. Bye-bye.");

	getchar();

	return EXIT_SUCCESS;

} // main




// Functions prototypes

void addStudentAtStart(char id[], student_t ** headPtr) {
//	Local variables
//	Local auxiliary local variables
	student_t *newNode;

//	Process
	if (searchStudent(id, *headPtr) == 0) {
//		This 'id' doesn't exists so we create a new node
		newNode = createStudent(id);
//		The '->NEXT' of the newly created node points where 'headPtr' points
		newNode->NEXT = *headPtr;
//		Now headPtr points to the newly created node
		*headPtr = newNode;
	}
	else
		puts("This student already exists.");

} // addStudentAtStart

void addStudent(char id[], student_t * headPtr) {
//	Local variables
//	Local auxiliary local variables
	student_t *temp;

//	Initialize local variables
	temp = headPtr;

//	Process
	if (searchStudent(id, headPtr)==0) {
//		Loop til the end of the list
		while (temp->NEXT != NULL)
			temp = temp->NEXT;

//		The last '->NEXT' is not 'NULL' anymore
		temp->NEXT = createStudent(id);
//		Now we iterate manually one more time, so now temp points to the newly created node
		temp = temp->NEXT;
//		And make the '->NEXT' of the newly created node 'NULL' because is the last one in the list
		temp->NEXT = NULL;
	}
	else
		puts("This student already exists.");

} // addStudent

int size(student_t * headPtr) {
//	Local variables
//	Local auxiliary local variables
	student_t *temp;
	int i;

//	Initialize local variables
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

void display(student_t * headPtr) {
//	Local variables
//	Local output local variables
	student_t *temp;

//	Initialize local variables
	temp = headPtr;

//	Process
//  Loop til the end of the list
	while (temp != NULL) {
		printf("Student id: %s\n", temp->id);
		printf("Student name: %s\n", temp->name);
		printf("Student age: %d\n", temp->age);
		printf("Student average: %.2f\n", temp->average);
		printf("Student email: %s\n", temp->email);
		printf("Student mobile: %s\n", temp->mobile);
		puts("");

		temp = temp->NEXT;
	}

} // display

void deleteLastStudent(student_t * headPtr) {
//	Local variables
//	Local output local variables
	student_t *oldTemp;
	student_t *temp;

//	Initialize local variables
	oldTemp = headPtr;
	temp = headPtr->NEXT;

//	Process
//  Loop til the end of the list
	while (temp->NEXT != NULL) {
		oldTemp = temp;
		temp = temp->NEXT;
	}

//	Output student about to be deleted
	puts("Deleting student:");
	printf("Student id: %s\n", temp->id);
	printf("Student name: %s\n", temp->name);

//	Set the previous "->NEXT" to 'NULL' so it is now the last node in the list
	oldTemp->NEXT = NULL;
//	Free the heap from the removed node
	free(temp);

} // deleteLastStudent

void printToFile(student_t * headPtr) {
//	Local variables
//	Local output local variables
	FILE *studentsFile;
//	Local auxiliary local variables
	student_t *temp;

//	Initialize local variables
	studentsFile = fopen(OUTPUT_FILE, "w");
	temp = headPtr;

//	Process
	if (studentsFile != NULL) {
//      Loop til the end of the list
		while (temp->NEXT != NULL) {
			temp = temp->NEXT;
			fprintf(studentsFile, "Student id: %s\n", temp->id);
			fprintf(studentsFile, "Student name: %s\n", temp->name);
			fprintf(studentsFile, "Student age: %d\n", temp->age);
			fprintf(studentsFile, "Student average: %.2f\n", temp->average);
			fprintf(studentsFile, "Student email: %s\n", temp->email);
			fprintf(studentsFile, "Student mobile: %s\n", temp->mobile);
			fprintf(studentsFile, "\n");
		}

		fclose(studentsFile);
	}
	else
		printf("Error writing to output file %s\n", OUTPUT_FILE);

} // printToFile

int searchStudent(char needle[], student_t * hayStack) {
//	Local variables
//	Local auxiliary local variables
	student_t *temp;
	int i;

//	Initialize local variables
	i = 0;
	temp = hayStack;

//	Process
    if (temp != NULL) {
        while (temp->NEXT != NULL) {
            if (strcmp(needle, temp->id) == 0)
                return i;

            ++i;
        }
    }

	return 0;

} // searchStudent

void deleteFirstStudent(student_t * headPtr) {
//	Local variables
//	Local auxiliary local variables
	student_t *temp;

//	Initialize local variables
	temp = headPtr;

//	Output student about to be deleted
	puts("Deleting student:");
	printf("Student id: %s\n", headPtr->id);
	printf("Student name: %s\n", headPtr->name);

//	Move 'headPtr' to the next node
	headPtr = headPtr->NEXT;
//	Free the heap from the removed node
	free(temp);

} // deleteFirstStudent

void addStudentAt(int position, char id[], student_t * headPtr) {
//	Local variables
//	Local auxiliary local variables
	student_t *newNode;
	student_t *temp;
	int i;

//	Initialize local variables
	temp = headPtr;

//	Process
//  If this 'id' doesn't exists, we loop to the desired position
	if (searchStudent(id, headPtr) == 0) {
		if (position == 1)
			addStudentAtStart(id, &headPtr);
		else {
//          Loop until we reach the point of insertion
			for (i = 1; i < position - 1; ++i)
				temp = temp->NEXT;

//          Create a new node
			newNode = createStudent(id);
//		    The '->NEXT' of the newly created node points where 'temp->NEXT' points
            newNode->NEXT = temp->NEXT;
//          Now the 'temp->NEXT' points to the newly created node
			temp->NEXT = newNode;
		}
	}
	else
		puts("This student already exists.");

} // addStudentAt

void deleteStudentAt(int position, student_t *headPtr) {
//	Local variables
//	Local auxiliary local variables
    student_t *oldTemp;
    student_t *temp;
    int i;

//	Initialize local variables
    oldTemp = headPtr;
    temp = headPtr->NEXT;

//	Process
//  We check the position
    if (position == 1)
        deleteFirstStudent(headPtr);
    else {
//      Loop until we reach the node before the chosen one
        for (i = 1; i < position - 1; ++i)
            temp = temp->NEXT;

//      Bypass the chosen node.
//      This is done by making the '->NEXT' of the previous node equals to the '->NEXT' of
//      the chosen node
        oldTemp->NEXT = temp->NEXT;
//      Free the chosen node
        free(temp);
    }


} // deleteStudentAt




// Auxiliary functions definitions

student_t * createStudent(char id[]) {
//	Local variables
//	Local auxiliary local variables
	student_t *newNode;

//	Initialize local variables
	newNode = (student_t *)malloc(sizeof(student_t));

//	Process
	strcpy(newNode->id, id);

	captureString(newNode->name, "Please enter new student's name: ");

	newNode->age = captureInteger("Please enter new student's age: ");
	newNode->average = captureFloat("Please enter new student's average: ");

	captureString(newNode->email, "Please enter new student's email: ");
	captureString(newNode->mobile, "Please enter new student's mobile: ");

	return newNode;

} // createStudent

void printStudent(int position, student_t *headPtr) {
//	Local variables
//	Local output variables
	student_t *temp;
//	Local auxiliary local variables
	int i;

//	Initialize local variables
	temp = headPtr;

//	Process
//	Loop until the desired position
	for (i = 1; i < position; ++i)
		temp = temp->NEXT;

//	Print the student
	printf("Student id: %s\n", temp->id);
	printf("Student name: %s\n", temp->name);
	printf("Student age: %d\n", temp->age);
	printf("Student average: %.2f\n", temp->average);
	printf("Student email: %s\n", temp->email);
	printf("Student mobile: %s\n", temp->mobile);
	puts("");

} // printStudent

int captureInteger(char *message) {
//	Local variables
//	Local input variables
	int integer;

//	Process
	printf(message);
	scanf("%d%*c", &integer);

	return integer;

} // captureInteger

int captureFloat(char * message) {
//	Local variables
//	Local input variables
	float decimal;

//	Process
	printf(message);
	scanf("%f%*c", &decimal);

	return decimal;

} // captureFloat

void captureString(char * dst, char * message) {
//	Process
	printf(message);
	gets(dst);

} // captureString

void printTitle(char *message) {
//	Local variables
//	Local auxiliary local variables
	int length;
	int i;

//	Initialize local variables
	length = strlen(message);

//	Process
	puts("");

	puts(message);
	for (i = 0; i < length; i++)
		printf("-");

	puts("");

} // printTitle
