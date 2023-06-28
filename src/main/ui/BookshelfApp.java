package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

// Bookshelf application
public class BookshelfApp {
    private static final String JSON_STORE = "./data/bookshelf.json";
    private Bookshelf bs;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Bookshelf Application
    // source: TellerApp
    public BookshelfApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBookshelfApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // source: TellerApp
    private void runBookshelfApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenuRoot();
            command = input.next();

            if (command.equals("q")) {
                printLog();
                keepGoing = false;
            } else {
                processCommandRoot(command);
            }
        }
        System.out.println("Happy Reading!");
    }

    // MODIFIER: this
    // EFFECTS: initializes bookshelf
    // source: TellerApp
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Name your bookshelf:");
        String name = input.nextLine();
        bs = new Bookshelf(name);
    }

    // EFFECTS: displays first menu of options (root) to user
    // source: TellerApp
    private void displayMenuRoot() {
        System.out.println("a -> add book"); // gui
        System.out.println("b -> view books"); // gui
        System.out.println("c -> change rating or status of a book");
        System.out.println("g -> set reading goal");
        System.out.println("p -> view reading goal progress");
        System.out.println("r -> remove book");
        System.out.println("v -> view bookshelf characteristics");
        System.out.println("s -> save bookshelf to file"); // gui
        System.out.println("l -> load bookshelf from file"); // gui
        System.out.println("q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from main menu
    // source: TellerApp
    private void processCommandRoot(String command) {
        if (command.equals("a")) {
            addBook();
        } else if (command.equals("b")) {
            viewBooks();
        } else if (command.equals("c")) {
            changeABook();
        } else if (command.equals("g")) {
            doSetGoal();
        } else if (command.equals("p")) {
            viewGoalProgress();
        } else if (command.equals("r")) {
            removeBook();
        } else if (command.equals("v")) {
            viewBookshelfChar();
        } else if (command.equals("s")) {
            saveBookshelf();
        } else if (command.equals("l")) {
            loadBookshelf();
        } else {
            System.out.println("Selection not valid. Please select another option.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a book to the bookshelf
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void addBook() {
        String command = input.nextLine();
        System.out.println("Enter title of book:");
        String title = input.nextLine();

        System.out.println("Enter the name of the author:");
        String author = input.nextLine();


        System.out.println("Enter the status of the book: r =  read, cr = currently reading, tbr = to be read:");
        command = input.next();
        BookStatus status = BookStatus.TOBEREAD;
        if (command.equals("r")) {
            status = BookStatus.READ;
        } else if (command.equals("cr")) {
            status = BookStatus.CURRENTLYREADING;
        } else if (command.equals("tbr")) {
            status = BookStatus.TOBEREAD;
        } else {
            System.out.println("Not a valid reading status.");
        }

        System.out.println("Enter the star rating of the book out of 5; 0 if not yet read:");
        int rating = input.nextInt();

        Book b = new Book(title, author, status, rating);
        bs.shelveBook(b);
        if (bs.inBookshelf(b)) {
            System.out.println(title + " has been successfully added to your bookshelf.");
        } else {
            System.out.println(title + " could not be added to your bookshelf.");
        }
    }

    // EFFECTS: converts string to BookStatus
    private BookStatus convertStatus(String input) {
        BookStatus status = BookStatus.TOBEREAD;
        if (input.equals("r")) {
            status = BookStatus.READ;
        } else if (input.equals("cr")) {
            status = BookStatus.CURRENTLYREADING;
        } else if (input.equals("tbr")) {
            status = BookStatus.TOBEREAD;
        } else {
            System.out.println("Not a valid reading status.");
        }
        return status;
    }

    // EFFECTS: prints new menu for user to view different sets of books

    // source: TellerApp
    private void viewBooks() {
        String command = null;
        displayMenuView();
        command = input.next();
        processCommandView(command);
    }

    // EFFECTS: displays menu of options for viewing books
    // source: TellerApp
    private void displayMenuView() {
        System.out.println("book -> view a specific book");
        System.out.println("status -> view books of a certain status"); // gui
        System.out.println("rating -> view books of a certain rating");
        System.out.println("all -> view all books on your bookshelf");
    }

    // REQUIRES: books are in bookshelf
    // MODIFIES: this
    // EFFECTS: processes user command
    // source: TellerApp
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processCommandView(String command) {
        if (command.equals("book")) {
            System.out.println("Enter title of book:");
            String title = input.next();
            if (bs.inBookshelf(title)) {
                Book book = bs.getBook(title);
                System.out.println(book.getTitle() + ", " + book.getAuthor()
                        + ", " + book.getStatus() + ", " + book.getRating());
            } else {
                System.out.println("No book matching that title is in the bookshelf.");
            }
        } else if (command.equals("status")) {
            System.out.println("Enter status of books you would like to view: "
                    + "r =  read, cr = currently reading, tbr = to be read");
            command = input.next();
            BookStatus status = convertStatus(command);
            for (String t : bs.getBooksOfStatus(status)) {
                System.out.println(t);
            }
        } else if (command.equals("rating")) {
            System.out.println("Enter rating of books you would like to view; between 0 and 5:");
            int rating = input.nextInt();
            if ((rating < 0) || (rating > 5)) {
                System.out.println("Not a valid rating.");
            } else {
                for (String i : bs.getBooksOfRating(rating)) {
                    System.out.println(i);
                }
            }
        } else if (command.equals("all")) {
            for (String l : bs.getAllBooks()) {
                System.out.println(l);
            }
        } else {
            System.out.println("Not a valid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the rating or status of a single book
    // source: TellerApp
    private void changeABook() {
        String command = null;
        displayMenuChange();
        command = input.next();
        processCommandChange(command);
    }

    // EFFECTS: displays menu of options for changing a book
    // source: TellerApp
    private void displayMenuChange() {
        System.out.println("status -> change status");
        System.out.println("rating -> change rating");
    }

    // REQUIRES: book is in bookshelf
    // MODIFIES: this
    // EFFECTS: processes user command
    // source: TellerApp
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processCommandChange(String command) {
        System.out.println("Enter the title of the book you would like to alter:");
        String title = input.next();
        if (bs.inBookshelf(title)) {
            Book b = bs.getBook(title);
            BookStatus status;

            if (command.equals("status")) {
                System.out.println("Enter new status: r =  read, cr = currently reading, tbr = to be read:");
                command = input.next();
                if (command.equals("r")) {
                    status = BookStatus.READ;
                    b.changeStatus(status);
                    System.out.println("Status changed successfully.");
                } else if (command.equals("cr")) {
                    status = BookStatus.CURRENTLYREADING;
                    b.changeStatus(status);
                    System.out.println("Status changed successfully.");
                } else if (command.equals("tbr")) {
                    status = BookStatus.TOBEREAD;
                    b.changeStatus(status);
                    System.out.println("Status changed successfully.");
                } else {
                    System.out.println("Not a valid reading status.");
                }

            } else if (command.equals("rating")) {
                System.out.println("Enter new rating between 1 and 5; 0 if not yet read:");
                int rating = input.nextInt();
                if ((rating < 0) || (rating > 5)) {
                    System.out.println("Not a valid rating.");
                } else {
                    b.changeRating(rating);
                    System.out.println("Rating changed successfully.");
                }
            } else {
                System.out.println("Not a valid input.");
            }

        } else {
            System.out.println("Book with given title is not in bookshelf.");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets a goal for the bookshelf
    private void doSetGoal() {
        System.out.println("Enter the number of books you would like to read:");
        int num = input.nextInt();
        bs.setGoal(num);
        System.out.println("Reading goal set to " + num + " books!");
    }

    // EFFECTS: prints string with reading goal progress
    private void viewGoalProgress() {
        System.out.println(bs.getGoalProgress());
    }

    // MODIFIES: this
    // EFFECTS: removes a book to the bookshelf
    private void removeBook() {
        System.out.println("Enter title of book you would like to remove:");
        String title = input.next();
        if (bs.inBookshelf(title)) {
            bs.burnBook(title);
            System.out.println(title + " successfully removed from your bookshelf.");
        } else {
            System.out.println("Book with given title is already not in your bookshelf.");
        }
    }

    // EFFECTS: prints a string with bookshelf name and number of books shelved
    private void viewBookshelfChar() {
        String name = bs.getName();
        int num = bs.getCardinality();
        System.out.println("Your bookshelf is called " + name + " and has " + num + " books on it!");
    }

    // EFFECTS: saves the bookshelf to file
    // source:JsonSerializationDemo
    private void saveBookshelf() {
        try {
            jsonWriter.open();
            jsonWriter.write(bs);
            jsonWriter.close();
            System.out.println("Saved " + bs.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads bookshelf from file
    private void loadBookshelf() {
        try {
            bs = jsonReader.read();
            System.out.println("Loaded " + bs.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
    // EFFECTS: prints a log of every action taken in the session with time stamps
    private void printLog() {
        Iterator<Event> it = EventLog.getInstance().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString() + "\n");
        }
    }

}
