# GreatReads

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

In the future, I plan on adding a prompt when first running the application to ask if the user would like to load a 
bookshelf from file, and another prompt when quitting to ask if the user would like to save the current bookshelf to 
file.

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
