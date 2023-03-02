#include <iostream>
#include <string>
#include <iomanip>
#include <fstream>

using namespace std;
const int n = 10; //width
int countbor = 0; // to store number of book borrowed
int countinsert = 0; // to store number of book inserted

/*
struct hashing - a stucture which holds operations performed on Books Information
*/
struct hashing{
int numberOf_bookIn_index(int index);
 static const int tableSize = 100000; // size of table
 
 /*
    struct - book 
    @title: title of the book
    @author: author of the book
    @subject: subject of the book
    @publisher: publisher of the book
    @edition: edition of the book
    @Pop: place of publication
    @callNo: call Number of the book
    @ISBN: international standart book number
    @shelf: shelf where the book is found
    @Yop: year of publication
    @copy: number of copy of one book
    @publisher: publisher of the book
    @fine: amount of money which the borrower of book pays if delay occur
    @next: pointer to the next book info

     Description: holds basic information of books inthe Library
    */
    struct book{
         string title, author, subject, publisher, PoP, callNo;
         int YoP, copy, edition;
         long long int ISBN;
        book *next; // ptr
    }; book *hashTable[tableSize]; // defining HT using array
    hashing();
   
    void insertBook(string Bcall, string Btitle, string Bauthor, string Bsubject, string Bpublisher, int yop, int Bedition, string pop, long long int isbn, int cpy);
    void printTable();
    void BookListtofile();
    void printBookInIndex(int index); //print out books that are contained in specified index
    void searchbyTitle(string callNo, string title);
    void searchbyAuthor(string callNo, string author);
    void borrowBook(string callNo, string title);
    int hash(string key);
    int BookInuse();
    int totalBook();
    void latestBook(string callNo);
    void front();
    void borrowBookfile();
    void returnBook(string callNo, string title);
    void latest();
};

/*
class hashing2 - a class which holds operations performed on Students Information
*/
struct hashing2{
	int numberOf_studentIn_index(int index);
    public:
 static const int tableSize = 100000; // size of table
 /*
    struct - student info
    @name: name of student
    @id: id of the student
    @program: program which the student is taking
    @next: holds address of the next student info

     Description: holds student basic information
*/
    struct student{
        string name, id, program;
        int borDate, retDate, fine;
        student *next;
     }; student *HT[tableSize]; // defining HT using array
    hashing2();
    void insertStdOut(string id, string name, int borDate, int retDate);
    void insertStdIn(string id, string name);
    void printStdOuttofile();
    void printStdOut();
    void searchById(string id);
    int hash2(string key);
    void printStdIn();
};

/*
hashing - costructor to initialise member variables of struct student
*/
hashing2:: hashing2()
{
    for (int i = 0; i < tableSize; i++)
    {
        HT[i] = new student;
        HT[i]->name = "empty";
        HT[i]->id = "empty";
        HT[i]->retDate = 0;
        HT[i]->borDate = 0;
        HT[i]->fine = 0;
        
        HT[i]->next = NULL;
    }
}

/*
hash2 - hash Function to compute index where student's infromation insert, search or delete

Return: index
*/
int hashing2::hash2(string key)
{
	 int hashVal = 0;
    int index;
    for (int i = 0; i < key.length(); i++)
    {
        hashVal+=key[i];
    }
    index = hashVal % tableSize;
    //cout<<"sum = "<<hashVal<<endl;
    return index;
}

/*
insertStdIn - inserts student's information who wants to read inthe library
@id: id of student
@name: name of student
*/
void hashing2::insertStdIn(string id, string name)
{
	system("cls");
    int index = hash2(id); // using call number of the book as key to insert book
// adding book for the first time
    if (HT[index]->id == "empty")
    {
        HT[index]->id = id;
        HT[index]->name = name;
    }
// updating list of the book 
    else
    {
        student *ptr = HT[index]; // pointes the beginning of the list
        student *newstudent = new student;
        newstudent->id = id;
        newstudent->name = name;
        newstudent->next = NULL;
    // traversing until the end of the list
        while (ptr->next != NULL)
        {
            ptr = ptr->next;
        }
    // when the end of the list reached link it to newly created book
        ptr->next = newstudent; 
    }
    cout<<"\nStudent data recorded Successfully!!!\n";
}

/*
insertStdOut - inserts student's information who wants borrow
@id: id of student
@name: name of student
@borDate: borrowed Date
@retDate: due date
*/
void hashing2::insertStdOut(string id, string name, int borDate, int retDate)
{
	system("cls");
    int index = hash2(id); // using call number of the book as key to insert book
// adding  first borrower
    if (HT[index]->id == "empty")
    {
        HT[index]->id = id;
        HT[index]->name = name;
        HT[index]->borDate = borDate;
        HT[index]->retDate = retDate;
    }
// updating list of the book 
    else
    {
        student *ptr = HT[index]; // pointes the beginning of the list
        student *newStd = new student;
       newStd->id = id;
       newStd->name = name;
       newStd->borDate = borDate;
       newStd->retDate = retDate;
       newStd->next = NULL;
    // traversing until the end of the list
        while (ptr->next != NULL)
        {
            ptr = ptr->next;
        }
    // when the end of the list reached link it to newly created student info
        ptr->next = newStd;
    }
    cout<<"\nStudent data recorded Successfully!!!\n"<<endl;
}


/*
 numberOf_studentIn_index - to display nubmer of collision
 @index: index of student

Return: number of student in one index;
*/
int hashing2:: numberOf_studentIn_index(int index){
    int count = 0;
    if (HT[index]->id == "empty")
    {
        return count; //returns 0
    }
    else
    {
        count++;
        student *ptr = HT[index];
        while (ptr->next != NULL)
        {
             count++;
            ptr = ptr->next;   
        }
    }
     return count;
}

/*
printStdOut - prints student's information
*/
void hashing2::printStdOut()
{
	fstream file;
	file.open("Borrower's_List.txt",ios::in);
	if (file.is_open()){
		string line;
		while (getline(file, line)){
			cout<<endl<<line<<endl;
		}
		file.close();
	}
}
/*
printStdOuttofile - writes Borrowers list information to file called Borrower's List
*/
void hashing2::printStdOuttofile(){
    int n = 15;
    int number; //number of student in each index
    fstream file;
    file.open("Borrower's_List.txt", ios::out);
    if (file.is_open())
    {
    file<<"\nBorrowers\n";
   file<<setw(n+7)<<"ID"<<setw(n)<<"Name"<<setw(n)<<"Borrow Date"<<setw(n)<<"Return Date";
    file<<setw(n)<<"fine"<<endl;
    for (int i = 0; i < tableSize; i++)
    {
        number = numberOf_studentIn_index(i);
       
        if (HT[i]->id != "empty")
        {
        file<<"\nStudent "<<i<<setw(n);
        file<<HT[i]->id<<setw(n);
        file<<HT[i]->name<<setw(n);
        file<<HT[i]->borDate<<setw(n);
        file<<HT[i]->retDate<<setw(n);
        file<<HT[i]->fine<<endl;
        if (number > 1){
            file<<" Here is the collision ";
        }
         if (HT[i]->borDate != 0 && HT[i] != 0)
        {
        	file<<"   Borrower ";
		}
		}
        //cout<<"\nNumber of Students in one index "<<number<<endl;
        
    }
     file.close();
    }
}

/*
searchById - search student by his or her id
@id: id of student and used as key
*/
void hashing2::searchById(string id)
{
	int n = 10; //width
	system("cls");
    int index = hash2(id); // computes hash function using book's title
    bool foundBook = false; // initially anything is found
    string name, program;
    int borDate, retDate, fine;
    student *ptr = HT[index]; // pointer to the first book inthe index
    while (ptr != NULL)
    {
        // checks if needed book's title matches to the books which are inthe list
        if (ptr->id == id)
        {
            foundBook = true;
        //grabbing the info inside the book that the ptr points to
            id = ptr->id;
            name = ptr->name;
            program = ptr->program;
            borDate = ptr->borDate;
            retDate = ptr->retDate;
            fine = ptr->fine;
        }
        ptr = ptr->next;
    }
    // if the book is inthe library
    if (foundBook == true)
    {
        cout<<"\n------------------------------------------------------------------------------------------------------------------------\n";
        cout<<"ID"<<setw(n)<<"Name"<<setw(n)<<"Program"<<setw(n)<<"Borrow Date"<<setw(n)<<"Return Date";
        cout<<setw(n)<<"fine"<<endl;
        cout<<"\n------------------------------------------------------------------------------------------------------------------------\n";
      cout<<id<<setw(n)<<name<<setw(n)<<program<<setw(n)<<borDate<<setw(n)<<retDate;
        cout<<setw(n)<<fine<<endl;
    }
    // if the book is not there
    else
    {
        cout<<endl<<id<<" is not found inthe library\n";
    }
}

/*
hashing - costructor to initialise member variables of struct book
*/
hashing:: hashing()
{
    for (int i = 0; i < tableSize; i++)
    {
        hashTable[i] = new book;
        hashTable[i]->author = "empty";
        hashTable[i]->title = "empty";
        hashTable[i]->callNo = "empty";
        hashTable[i]->copy = 0;
        hashTable[i]->edition = 0;
        hashTable[i]->ISBN = 0;
        hashTable[i]->PoP = "empty";
        hashTable[i]->publisher = "empty";
        hashTable[i]->subject = "empty";
        hashTable[i]->YoP = 0;
        hashTable[i]->next = NULL;
    }
}

/*
hash - hash function to compute index where book's information insert, search or delete
@key: call number

Return: index at which the element inserted
*/
int hashing:: hash(string key)
{
    int hashVal = 0;
    int index;
    for (int i = 0; i < key.length(); i++)
    {
        hashVal+=key[i];
    }
    index = hashVal % tableSize;
    //cout<<"sum = "<<hashVal<<endl;
    return index;
}

/*
insertBook - function to insert new book
@Bcall: call Number of the book
@Btitle: title of the book
@Bauthor: author of the book
@Bsubject: subject of the book
@Bpublisher: publisher of the book
@Bedition: edition of the book
@pop: place of publication
@isbn: international standart book number
@yop: year of publication
@cpy: number of copy of one book
@publisher: publisher of the book
*/
void hashing::insertBook(string Bcall, string Btitle, string Bauthor, string Bsubject, string Bpublisher, int yop, int Bedition, string pop, long long int isbn, int cpy)
{
    int index = hash(Bcall);
// adding book for the first time
    if (hashTable[index]->callNo == "empty")
    {
        hashTable[index]->callNo = Bcall;
        hashTable[index]->author = Bauthor;
        hashTable[index]->title = Btitle;
        hashTable[index]->copy = cpy;
        hashTable[index]->edition = Bedition;
        hashTable[index]->ISBN = isbn;
        hashTable[index]->PoP = pop;
        hashTable[index]->publisher = Bpublisher;
        hashTable[index]->subject = Bsubject;
        hashTable[index]->YoP = yop;
    }
// updating list of the book 
    else
    {
        book *ptr = hashTable[index]; // pointes the beginning of the list
        book *newBook = new book;
        newBook->author = Bauthor;
        newBook->title = Btitle;
        newBook->callNo = Bcall;
        newBook->copy = cpy;
        newBook->edition = Bedition;
        newBook->ISBN = isbn;
        newBook->PoP = pop;
        newBook->publisher = Bpublisher;
        newBook->subject = Bsubject;
        newBook->YoP = yop;
        newBook->next = NULL;
    // traversing until the end of the list
        while (ptr->next != NULL)
        {
            ptr = ptr->next;
        }
    // when the end of the list reached link it to newly created book
        ptr->next = newBook; 
    }
    cout<<"\nBooks inserted Successfully!!!\n";
    countinsert++;
}

/*
 numberOf_bookIn_index - to display nubmer of collision

Return: number of book in one index;
*/
int hashing:: numberOf_bookIn_index(int index){
    int count = 0;
    if (hashTable[index]->callNo == "empty")
    {
        return count; //returns 0
    }
    else
    {
        count++;
        book *ptr = hashTable[index];
        while (ptr->next != NULL)
        {
             count++;
            ptr = ptr->next;   
        }
    }
     return count;
}

/*
searchbyTitle - searches for a book by its title
@callNo: call number as key
@Title: title of the book
*/
void hashing::searchbyTitle(string callNo, string Title)
{
	
		system("cls");
    int index = hash(callNo);
    bool foundBook = false; // we didn't found anything
    string Subject;
    string Author; 
    string Publisher;
    int yoP;
    string Edition;
    string poP;
    string CallNo;
    long long int Isbn;
    int Cpy;
    book *ptr = hashTable[index]; // pointer to the first book inthe index
    while (ptr != NULL)
    {
        if (ptr->title == Title)
        {
            foundBook = true;
            //grabbing the info inside the book that the ptr points to
            Subject = ptr->subject;
            Author = ptr->author;
            Publisher = ptr->publisher;
            yoP = ptr->YoP;
            Edition = ptr->edition;
            poP = ptr->PoP;
            CallNo = ptr->callNo;
            Isbn = ptr->ISBN;
            Cpy = ptr->copy;
        }
        ptr = ptr->next;
    }
    // if the book is inthe library
    if (foundBook == true)
    {
    	int n = 10; // width
  		cout<<"\n--------------------------------------------------------------------------------------------------------------\n";
         cout<<"Call No"<<setw(n)<<"Title"<<setw(n)<<"Author"<<setw(n)<<"Subject";
        cout<<setw(n)<<"Edition"<<setw(n)<<"Publisher"<<setw(n);
        cout<<"PoP"<<setw(n)<<"YoP";
        cout<<setw(n)<<"ISBN"<<setw(n)<<"copy"<<endl;
          cout<<"\n--------------------------------------------------------------------------------------------------------------\n";
        cout<<CallNo<<setw(n)<<Title<<setw(n)<<Author<<setw(n)<<Subject<<setw(n)<<Edition;
        cout<<setw(n)<<Publisher<<setw(n)<<poP<<setw(n)<<yoP<<setw(n)<<Isbn<<setw(n)<<Cpy;
           cout<<"\n--------------------------------------------------------------------------------------------------------------\n";
        }
    // if the book is not there
    else
    {
        cout<<Title<<" is not found inthe library\n";
    }
}

/*
searchbyAuthor - searches for a book by its author
@callNo: call number as key
@Author: author of the book
*/
void hashing::searchbyAuthor(string callNo, string Author)
{
		system("cls");
    int index = hash(callNo);
    bool foundBook = false; // we didn't found anything
    string Subject,Title,Publisher;
    
    string Edition,poP, CallNo;
    long long int Isbn;
    int Cpy, yoP;
    book *ptr = hashTable[index]; // pointer to the first book inthe index
    while (ptr != NULL)
    {
        if (ptr->author == Author)
        {
            foundBook = true;
            //grabbing the info inside the book that the ptr points to
            Subject = ptr->subject;
            Title = ptr->title;
            Publisher = ptr->publisher;
            yoP = ptr->YoP;
            Edition = ptr->edition;
            poP = ptr->PoP;
            CallNo = ptr->callNo;
            Isbn = ptr->ISBN;
            Cpy = ptr->copy;
        }
        ptr = ptr->next;
    }
    // if the book is inthe library
    if (foundBook == true)
    {
    	int n = 10;
        cout<<"\n--------------------------------------------------------------------------------------------------------------\n";
        cout<<"Call No"<<setw(n)<<"Title"<<setw(n)<<"Author"<<setw(n)<<"Subject";
        cout<<setw(n)<<"Edition"<<setw(n)<<"Publisher"<<setw(n);
        cout<<"PoP"<<setw(n)<<"YoP";
        cout<<setw(n)<<"ISBN"<<setw(n)<<"copy"<<endl;
          cout<<"\n--------------------------------------------------------------------------------------------------------------\n";
         cout<<CallNo<<setw(n)<<Title<<setw(n)<<Author<<setw(n)<<Subject<<setw(n)<<Edition;
        cout<<setw(n)<<Publisher<<setw(n)<<poP<<setw(n)<<yoP<<setw(n)<<Isbn<<setw(n)<<Cpy;
           cout<<"\n--------------------------------------------------------------------------------------------------------------\n";
        }
    //if the book is not there
    else
    {
        cout<<Author<<"\'s Book is not found inthe library\n";
    }
}

/*
latestBook - newly published books
@callNo: call number of the book as key
*/
void hashing::latestBook(string callNo)
{
	      fstream file;
        file.open("latest_Books.txt", ios::out);
        if (file.is_open())
        {
        file<<setw(n+7)<<"Call No"<<setw(n)<<"Title"<<setw(n)<<"Author"<<setw(n)<<"Subject";
        file<<setw(n)<<"Edition"<<setw(n)<<"Publisher"<<setw(n);
        file<<"PoP"<<setw(n)<<"YoP";
        file<<setw(n)<<"ISBN"<<setw(n)<<"copy"<<endl;
             for (int i = 0; i < tableSize; i++)
    {
        if (hashTable[i]->edition > 3)
     {
        file<<"\nBook "<<i<<setw(n);
        file<<hashTable[i]->callNo<<setw(n);
        file<<hashTable[i]->author<<setw(n);
        file<<hashTable[i]->title<<setw(n);
        file<<hashTable[i]->subject<<setw(n);
        file<<hashTable[i]->edition<<setw(n);
        file<<hashTable[i]->publisher<<setw(n);
        file<<hashTable[i]->PoP<<setw(n);
        file<<hashTable[i]->YoP<<setw(n);
        file<<hashTable[i]->ISBN<<setw(n);
        file<<hashTable[i]->copy;
	 }
    }
     file.close();
 }
}

/*
printTable - print all list of the books
*/
void hashing:: BookListtofile()
{
    int number; //number of book in each index
        fstream file;
        file.open("Library.txt", ios::out | ios::ate);
        if (file.is_open())
        {
          file<<setw(n+7)<<"Call No"<<setw(n)<<"Title"<<setw(n)<<"Author"<<setw(n)<<"Subject";
        file<<setw(n)<<"Edition"<<setw(n)<<"Publisher"<<setw(n);
        file<<"PoP"<<setw(n)<<"YoP";
        file<<setw(n)<<"ISBN"<<setw(n)<<"copy"<<endl;
             for (int i = 0; i < tableSize; i++)
    {
        number = numberOf_bookIn_index(i);
         if(hashTable[i]->callNo != "empty")
        {
        	file<<"\nBook "<<i<<setw(n);
        file<<hashTable[i]->callNo<<setw(n);
        file<<hashTable[i]->author<<setw(n);
        file<<hashTable[i]->title<<setw(n);
        file<<hashTable[i]->subject<<setw(n);
        file<<hashTable[i]->edition<<setw(n);
        file<<hashTable[i]->publisher<<setw(n);
        file<<hashTable[i]->PoP<<setw(n);
        file<<hashTable[i]->YoP<<setw(n);
        file<<hashTable[i]->ISBN<<setw(n);
        file<<hashTable[i]->copy;
         if (number > 1){
    file<<" Here is the collision ";
		}
         }
    // cout<<"\nNumber of books in one index "<<i;
    }
    cout<<"\n--------------------------------------------------------------------------------------------------------------\n";
           
     file.close();
 }
}

/*
borrow book - checks if library is empty or not if not checks book's number of copy
    to decide whether there is sufficient book or not if not can't borrow otherwise borrow
@callNo: call number as key
@title: title of the book which the student wants to borrow
*/
void hashing::borrowBook(string callNo, string title)
{
	system("cls");
    int index = hash(callNo);
    bool foundBook = false; // we didn't found anything
    
    hashing2 student; // object for hashing2 structure
    
    string Subject,Title,Publisher, Author;
    string poP, CallNo;
    long long int Isbn;
    int Cpy, yoP, Edition;	


	string name, id;
	int borDate, retDate;

	 book *ptr = hashTable[index]; // pointer to the first book inthe index
    while (ptr != NULL)
    {
        if (ptr->callNo == callNo)
        {
            foundBook = true;
            //grabbing the info inside the book that the ptr points to
            Subject = ptr->subject;
            Title = ptr->title;
            Author = ptr->author;
            Publisher = ptr->publisher;
            yoP = ptr->YoP;
            Edition = ptr->edition;
            poP = ptr->PoP;
            CallNo = ptr->callNo;
            Isbn = ptr->ISBN;
            Cpy = ptr->copy;
        }
        ptr = ptr->next;
    }
    // if the book is inthe library
    if (foundBook == true)
    {
    if (hashTable[index]->copy < 5)
	{
		cout<<"\nSorry this book can't be borrowed because of it's defficiency\n";
		return;
	}	
	//record std info
	cout<<"\nRecord Student's Information\n";
	cout<<"\nID: ";cin>>id;
	cout<<"\nName: ";cin>>name;
	cout<<"\nBorrowed Date: ";cin>>borDate;
	cout<<"\nReturned Date: ";cin>>retDate;
	
	student.insertStdOut(id,name, borDate,retDate);
	student.printStdOuttofile();
	fstream file;
		file.open("Borrowed_Book.txt", ios::out | ios::app);
		if (file.is_open())
		{ 
			file<<endl<<CallNo<<setw(n);
			file<<Title<<setw(n);
			file<<Author<<setw(n);
			file<<Edition<<setw(n);
			file<<Publisher<<setw(n);
            file<<poP<<setw(n);
			file<<yoP<<setw(n);
            file<<Isbn<<setw(n);
            file<<Cpy<<setw(n);
            cout<<"\nBorrowed Successfully!!!\n";
            countbor++;
		}
		file.close();
		//decrease the copy of the book by 1 and update the book list
		
		hashTable[index]->copy--;
 BookListtofile();
		} 
    //if the book is not there
    else
    { 
        cout<<title<<" is not found inthe library\n";
    } 

}

/*
returnBook - to return the borrowed book
*/
void hashing::returnBook(string callNo, string title)
{ 	
		system("cls");
		hashing2 stud;
		
      int index = hash(callNo);
    book *del, *ptr1, *ptr2;
    bool found = false; // we didn't find anything
    
    
    string CallNo, Subject, Author, Publisher, poP, Isbn;
    int Edition, yoP, Cpy;
    
    string id;
    
    book *ptr = hashTable[index]; // pointer to the first book inthe index
    while (ptr != NULL)
    {
        if (ptr->title == title)
        {
            found = true;
            //grabbing the info inside the book that the ptr points to
            Subject = ptr->subject;
            Author = ptr->author;
            Publisher = ptr->publisher;
            yoP = ptr->YoP;
            Edition = ptr->edition;
            poP = ptr->PoP;
            CallNo = ptr->callNo;
            Isbn = ptr->ISBN;
            Cpy = ptr->copy;
        }
        ptr1=ptr;
        ptr = ptr->next;
    }
   if (found == false){
   	cout<<"\n%^&***((*(\n";
   	return;
   }
    // if list has only one book information
     if (hashTable[index]->title == title && hashTable[index]->next == NULL)
     {
        hashTable[index]->callNo = "empty";
        hashTable[index]->title = "empty";
        hashTable[index]->author = "empty";
        hashTable[index]->subject = "empty";
        hashTable[index]->publisher = "empty";
        hashTable[index]->edition = 0;
        hashTable[index]->ISBN = 0;
        hashTable[index]->PoP = "empty";
        hashTable[index]->YoP = 0;
        hashTable[index]->copy = 0;
     }
    // if list has many item and the first match
    else if (hashTable[index]->title == title)
    {
        del = hashTable[index]; // pointing to that very first student
        hashTable[index] = hashTable[index]->next; 
        delete del;
    }
    // if list has many item
    else
    {
        
        ptr1 = hashTable[index]->next; //points to the second student
        ptr2 = hashTable[index]; // behind ptr1
         while (ptr1 != NULL && ptr1->title != title)
         {
            ptr2 = ptr1;
            ptr1 = ptr1->next;
         }
         fstream myfile;
    	myfile.open("Borrowed_Book.txt",ios::out);
		if(myfile.is_open()){
		myfile<<endl<<ptr1->callNo<<"    ";
		myfile<<ptr1->author<<"    ";
		myfile<<ptr1->title<<"    ";
		myfile<<ptr1->edition<<"    ";
		myfile<<ptr1->PoP<<"    ";
		myfile<<ptr1->YoP<<"    ";
		myfile<<ptr1->ISBN<<"    ";
		myfile<<ptr1->copy<<"    ";
		myfile.close();
		}
         // if there is no match
         if (ptr1 == NULL)
         {
            cout<<"\nEmpty List\n";
            return;
         }
         // if match is found
         else
         {
            
            ptr2->next =ptr1->next;
            delete ptr1;
         }
         
    }
    
    fstream myfile;
    	myfile.open("Borrowed_Book.txt",ios::out);
		if(myfile.is_open()){
		myfile<<endl<<ptr1->callNo<<"    ";
		myfile<<ptr1->author<<"    ";
		myfile<<ptr1->title<<"    ";
		myfile<<ptr1->edition<<"    ";
		myfile<<ptr1->PoP<<"    ";
		myfile<<ptr1->YoP<<"    ";
		myfile<<ptr1->ISBN<<"    ";
		myfile<<ptr1->copy<<"    ";
		myfile.close();
	}
    cout<<"\nReturned successfully\n";
    countbor--;

   
}
    
/*
borrowBookfile - prints borrowed book to consol
*/
void hashing::borrowBookfile()
{
fstream file;
file.open("Borrowed_Book.txt", ios::out);
if (file.is_open()){
	string line;
	while (getline(file, line)){
		cout<<endl<<line<<endl;
	}
	file.close();
}	
}

/*
BookInuse - counts number of books which are in use

Return: returns the counted number of books
 */
int hashing::BookInuse()
{
	return countbor;
}

/*
totalBook - total number of Books inthe Library

Return: total number of Books
*/
int hashing::totalBook()
{
	int sum = 0;
	for (int i = 0; i < tableSize; i++)
	{
		sum += hashTable[i]->copy;
	}
	return sum;
}

/*
front - display welcome page
*/
void hashing::front()
{
	system("cls");
	cout<<setw(75)<<"*****************************************\n";
		cout<<endl<<setw(75)<<"*     WELCOME TO OUR MINI LIBRARY       *\n";
		cout<<endl<<setw(75)<<"*****************************************\n";
}

/*
latest - prints latest books
*/
 void hashing::latest()
 {
 	fstream file;
 	file.open("latest_Books.txt", ios::in);
 	if (file.is_open()){
 		string line;
 		while (getline(file, line)){
 			
 			cout<<endl<<line<<endl;
 			
		 }
		 file.close();
	 }
 }

/*
printTable - prints list of books to console
*/
void hashing::printTable()
{
	fstream file;
file.open("Library.txt", ios::in);
if (file.is_open())
{
	string line;
	while(getline(file,line))
	{
		cout<<endl<<line<<endl;
	}
	file.close();
   } 
   else{
   	cout<<"\nfile not opeded\n";
   }
}

/*
main - Entry

Return: always 0
*/
int main()
{
    hashing hashobj; //object for the structure which takes book info
    hashing2 student;  //object for the structure which takes student info
    
    // to store Book informaions
    string callNo, PoP, publisher, title, author, subject;
    long long int isbn;
    int YoP, copy, edition;
    
    // to store Student's information
    string name, id;
    int retDate, borDate, fine;
    
    int index;
    string exit; // for inserting book
     string yn; // to exit from searching
    int choice; // outer switch 
	int optout; // to exit from outer switch
	
	// inserting books for the first time
		hashobj.insertBook("45-HH", "Jenna-William","C-programming",  "Reference", "NewDelhi", 2014, 4, "Netherland", 4783473, 3);
        hashobj.insertBook("46-HH", "Arebu","Extrem",  "Reference", "AsterNega", 2000, 2, "Addis", 4783473, 5);
        hashobj.insertBook("22-HH",  "Hadis","FkrEskeMekabr", "Fiction", "Aster", 1930, 1, "Addis", 47832473, 8);
        hashobj.insertBook("48-HH", "David","HeadFirst",  "Reference", "AsterNega", 1940, 11, "Addis", 47853473, 3);
        hashobj.insertBook("78-oo", "William", "LoveofUniverse", "Fiction","Oxford", 2001, 5,"Newyork",  38439893, 9);
        hashobj.insertBook("78-hh", "Thunder", "Thefnihadash", "Fiction", "Oxford",2005, 7, "Boston",  3788228, 6);
        hashobj.insertBook("47-oo", "Charles","Database",  "Reference", "Oxfordd", 2008, 6, "London", 38938984,8);
        
        
        

  do{
   	system("cls");
   	cout<<endl<<setw(10)<<" MAIN MENU\n\n";
   	cout<<"\n1. Insert New Book\n\n2. Search for a Book\n\n3. Borrow Book\n\n4. Return Book\n\n5. Books in use\n\n";
   	cout<<"6. Total Books\n\n7. Latest Books\n\n8. Book List\n\n9. Search for Student\n\n10. Student List\n\n11. Help\n\n";
   cout<<"\nEnter here: ";cin>>choice;
   switch(choice)
   {
   	case 1:
   		system("cls");
   		   	hashobj.front();
        cout<<"\n\nINSERTING BOOK\n";
         exit = "";
         while (exit != "no")
             {
         cout<<"\nPlease insert Book information...\n";
         cout<<"\nCall number: ";cin>>callNo;
         cout<<"\nTitle: ";cin>>title;
         cout<<"\nAuthor: ";cin>>author;
         cout<<"\nSubject: ";cin>>subject;
         cout<<"\nPublisher: ";cin>>publisher;
         cout<<"\nYear of pulibcation: ";cin>>YoP;
         cout<<"\nEdition: ";cin>>edition;
         cout<<"\nPlace of Publication: ";cin>>PoP;
         cout<<"\nISBN: ";cin>>isbn;
         cout<<"\nNumber of copy: ";cin>>copy;
             if (exit != "no")
             {
                         hashobj.insertBook(callNo, title, author, subject, publisher, YoP, edition, PoP, isbn, copy);
             }
             	system("cls");
             cout<<"\nDo you want to insert more?  yes or no: ";
             cin>>exit;
             }
        break;
        case 2:
        	system("cls");
        	hashobj.front();
        	cout<<"\n\nSEARCHING BOOK...\n";
        char x; // inner switch
        do{
        
        cout<<"\na. search by title\nb. search by author\n";
        cout<<"\nEnter here: ";
        cin>>x;
        switch (x)
        {
            case 'a':   
				system("cls");     
            cout<<"Enter Title and Call number of the Book you want\n";
            cout<<"\nTitle: ";cin>>title;
            cout<<"\nCall Number: ";cin>>callNo;
                hashobj.searchbyTitle(callNo, title);
            break;
            case 'b':
            		system("cls");
            	cout<<"Enter Author's name and Call number of the Book you want\n";
            cout<<"\nAuthor: ";cin>>author;
            cout<<"\nCall Number: ";cin>>callNo;
                hashobj.searchbyAuthor(callNo, author);
            break;
            default:
            cout<<"\nIn valid choice\n";
            
        } cout<<"\nDo you want to continue searching? yes or no: ";
       
        cin>>yn;
        }while (yn != "no");
        break;
        case 3:
        	system("cls");
        	hashobj.front();
        	cout<<"\n\nGIVING BOOK\n";
        	cout<<"\nInsert Title and Call number of the Book...\n";
            cout<<"\nTitle: ";cin>>title;
            cout<<"\nCall Number: ";cin>>callNo;
            hashobj.borrowBook(callNo, title);
            break;
        case 4:
        	system("cls");
        	hashobj.front();
        	cout<<"\nRETURNING BOOK\n";
        	cout<<"\nEnter Title and Call number of a book\n";
        	cout<<"\nTitle: ";cin>>title;
        	cout<<"\nCall Number: ";cin>>callNo;
   		hashobj.returnBook(callNo, title);
       	break;
       	case 5:
       		system("cls");
       		hashobj.front();
       		cout<<"\nBooks in use: "<<hashobj.BookInuse();
       		break;
       	case 6:
       		system("cls");
       		hashobj.front();
       		cout<<"\nTotal Books: "<<hashobj.totalBook()<<endl;
       		break;
       	case 7:
       		system("cls");
       		hashobj.front();
       		hashobj.latestBook(hashobj.hashTable[index]->callNo);
       		hashobj.latest();
       		break;
       	case 8:
       		system("cls");
       		hashobj.front();
       		cout<<"\nBook List\n";
       		hashobj.BookListtofile();
       		hashobj.printTable();
       		break;
       	case 9:
       		system("cls");
       		hashobj.front();
       		cout<<"\n\nSEARCHING STUDENT'S INFORMATION\n";
       		cout<<"\nEnter ID of Student You want to search\n";
            cout<<"\nID: ";cin>>id;
            student.searchById(id);
            break;	
        
        case 10:
        	system("cls");
        	hashobj.front();
        	student.printStdOut();
        	break;
        case 11:
        	system("cls");
        	hashobj.front();
        	cout<<"\nHelp\n---------------------------------------------------------\n";
        	cout<<"\nThis is Library management system which is designed to simplify Librarists work. This system organize ";
			cout<<" Books by their call number. By using it you can insert new books and updating the content of the library";
        	cout<<" and also You are able to record Student's information who wants to borrow the Books after checking the book's sufficiency easily. ";
        	cout<<" Again using this system makes your life easier by telling how many books are in Library and also how many of them are in use.";
        	cout<<" You can also search Books by their author or title.\n";
        	cout<<"\n\n   Usage:\n\n ->To insert new books you should have the necessary book information like Call number, Title, ISBN, ...\n";
        	cout<<"\n ->To search for a book you can use ways:-1)by their title and 2)by their author therefore you should have to ibsert the proper title/author and call numver the book you want to search\n";
        	cout<<"\n\n ->To borrow a Book this system will allow you to check whether the book is sufficient or not. if it is sufficient you have to recieve the proper information ";
        	cout<<" about student and also you have to assign the proper return date because this system will assign fine if the book is not returned ontime.\n";
        	cout<<"\n\n ->To return the borrowed book it is necessary to erase student's information which is recorded during borrowing time and returning the book at it's position is the system's work ";
        	cout<<" after you insert the necessary information about the returned book\n";
        	break;
        default:
        	 	system("cls");
        cout<<"\n\nSorry You can't get this service. but We are onthe process of improving our Library stay tuned\n\n";
		cout<<"                              Thank You for using \n";
    }
    cout<<"\n\n  If you want to get other services click 1, if not click any key: ";
    cin>>optout;
   } while (optout == 1);
    
    return 0;
}
