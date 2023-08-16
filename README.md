# GreatReads (Console UI)

*GreatReads* is an application where users can keep track of the books in their library. Users are able to add a book 
with a title, reading status, and star rating to their bookshelf, where they can keep track of their books, their 
progress towards their reading goal, and view different groupings of the books on their shelf.

I created *GreatReads* as a way to learn Java, IntelliJ, GitHub, and software construction. As an avid reader, I wanted 
to try my hand at an application where I could store my books and the main information that I am interested in
(their status and rating).

Building this project from scratch taught me a lot about the process of developing an application, such as:
- brainstorming and planning out which functionalities I wanted and if it would be feasible with the language and program I was 
using
- implementing my functionalities effectively
- debugging and writing tests that cover all code usage
- creating and debugging a console UI using a Scanner
- creating and refactoring code to include exceptions
- refactoring code to use a more appropriate data structure for the bookshelf
- implementing persistence with Json
- implementing an action log that prints when the application is closed
- object-oriented programming, data abstraction, encapsulation, single responsibility principle, decreasing class 
- coupling, increasing class cohesion, etc.

### Features
First, you can create a bookshelf with a custom name.
##### Then, you can:
- add a book with the title, author name, read status, and star rating
- remove a book
- change the rating or status of a book already shelved
- view a specific book
- view the list of all the books
- view the list of all the books with a given reading status or star rating
- view the total number of books you have
- set a goal for number of books you want to read, with read books contributing to progress
- view progress in reading goal for the year
- view its characteristics (name and number of books shelved)
- save my bookshelf to file
- open a saved bookshelf from file
- upon quitting a log of your events is printed to the console

### Future plans
- refactor implementation of the HashMap and hashcode generation to allow two books with the same title to be on the bookshelf
- throw an exception when the inputted rating of a book is not an int

### How to use
1. Clone this repo
2. Open the project in your favourite code editor with a Java compiler
3. Run the executable file 'Main' in the ui package (src/main/ui/Main.java)

   <img width="225" alt="Screenshot 2023-08-09 at 1 40 51 PM" src="https://github.com/robinmatheson/GreatReads-Console-UI/assets/125790030/aa10eed2-da23-499d-9ddf-ffcb4140f148">
4. The console should open up with a prompt to choose between loading a bookshelf from file or creating a new one
   <img width="1410" alt="Screenshot 2023-08-09 at 1 42 21 PM" src="https://github.com/robinmatheson/GreatReads-Console-UI/assets/125790030/6e41742f-9d26-412a-b08b-57fd7b96a9e3">
5. Following every prompt, input your answer followed by the 'Enter' key
6. Have fun tracking all the books on your bookshelf (or library if you've reached 1000 books!)

   *Here is a use example:*
   
   <video width="200" src="https://github.com/robinmatheson/GreatReads-Console-UI/assets/125790030/2b14730f-633a-4d36-87be-f9e1d1ce7a2f"/>


