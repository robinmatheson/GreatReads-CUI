package ui;

import exceptions.*;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    public BookshelfApp() throws Exception {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBookshelfApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // source: TellerApp
    private void runBookshelfApp() throws Exception {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenuRoot();
            command = input.next();

            if (command.equals("q")) {
                displayPromptSave();
                command = input.next();
                processPromptSave(command);
                printLog();
                keepGoing = false;
            } else {
                processCommandRoot(command);
            }
        }
        System.out.println("Happy Reading!");
    }

    // MODIFIES: this
    // EFFECTS: initializes bookshelf
    // source: TellerApp
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        displayPromptLoad();
        String command = input.nextLine();
        processPromptLoad(command);
    }

    // EFFECTS: displays option to load bookshelf from file
    private void displayPromptLoad() {
        System.out.println("Do you want to load a bookshelf from file?");
        System.out.println("y -> yes");
        System.out.println("n -> no, I want to create a new one");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processPromptLoad(String command) {
        if (command.equals("y")) {
            loadBookshelf();
        } else if (command.equals("n")) {
            createBookshelf();
        } else {
            System.out.println("Invalid selection. Please create a new bookshelf.");
            createBookshelf();
        }
    }

    private void createBookshelf() {
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
    private void processCommandRoot(String command) throws Exception {
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
    private void addBook() {
        String command = input.nextLine();
        System.out.println("Enter title of book:");
        String title = input.nextLine();

        System.out.println("Enter the name of the author:");
        String author = input.nextLine();

        try {
            System.out.println("Enter the status of the book: r = read, cr = currently reading, tbr = to be read:");
            command = input.next();
            String status = command;
            checkValidStatus(status); // want exception caught here if invalid status

            System.out.println("Enter the star rating of the book out of 5; 0 if not yet read:");
            int rating = input.nextInt();
            checkValidRating(rating);// want exception caught here if invalid rating

            Book b = new Book(title, author, status, rating);
            bs.shelveBook(b);
            System.out.println(title + " has been successfully added to your bookshelf.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS: throws exception if rating is invalid
    private void checkValidRating(int rat) throws InvalidRatingException {
        if (rat < 0 || rat > 5) {
            throw new InvalidRatingException();
        } else {
            // do nothing
        }
    }

    // EFFECTS: throws exception if status is invalid
    private void checkValidStatus(String stat) throws InvalidStatusException {
        if (!stat.equals("r") && !stat.equals("cr") && !stat.equals("tbr")) {
            throw new InvalidStatusException();
        } else {
            // do nothing
        }
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
        System.out.println("status -> view books of a certain status");
        System.out.println("rating -> view books of a certain rating");
        System.out.println("all -> view all books on your bookshelf");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    // source: TellerApp
    private void processCommandView(String command) {
        if (command.equals("book")) {
            System.out.println("Enter title of book:");
            String title = input.next();
            if (bs.inBookshelf(title)) {
                Book book = bs.getBook(title);
                if (book.getRating() == 1) {
                    System.out.println(book.getTitle() + " by " + book.getAuthor()
                            + ", " + statusToNiceString(book.getStatus()) + ", 1 star");
                } else {
                    System.out.println(book.getTitle() + " by " + book.getAuthor()
                            + ", " + statusToNiceString(book.getStatus()) + ", " + book.getRating() + " stars");
                }
            } else {
                System.out.println("No book matching that title is in the bookshelf.");
            }

        } else if (command.equals("status")) {
            System.out.println("Enter status of books you would like to view: "
                    + "r =  read, cr = currently reading, tbr = to be read");
            String status = input.next();
            try {
                ArrayList<String> bks = bs.getBooksOfStatus(status);
                if (bks.isEmpty()) {
                    System.out.println("There are no books of the given status on your bookshelf.");
                }
                for (String t : bks) {
                    System.out.println(t);
                }
            } catch (InvalidStatusException e) {
                System.out.println(e.getMessage());
            }

        } else if (command.equals("rating")) {
            System.out.println("Enter rating of books you would like to view; between 0 and 5:");
            int rating = input.nextInt();
            try {
                ArrayList<String> bks = bs.getBooksOfRating(rating); // will throw invalid rating exception
                if (bks.isEmpty()) {
                    System.out.println("There are no books of the given rating on your bookshelf.");
                }
                for (String i : bks) {
                    System.out.println(i);
                }
            } catch (InvalidRatingException e) {
                System.out.println(e.getMessage());
            }

        } else if (command.equals("all")) {
            if (bs.getBooks().isEmpty()) {
                System.out.println("Your bookshelf is empty.");
            }
            for (String l : bs.getAllBooks()) {
                System.out.println(l);
            }
        } else {
            System.out.println("Not a valid input.");
        }
    }

    private String statusToNiceString(BookStatus status) {
        String ret = null;
        if (status == BookStatus.READ) {
            ret = "read";
        } else if (status == BookStatus.TOBEREAD) {
            ret = "to be read";
        } else {
            ret = "read";
        }
        return ret;
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

    // MODIFIES: this
    // EFFECTS: processes user command
    // source: TellerApp
    private void processCommandChange(String command) {
        System.out.println("Enter the title of the book you would like to alter:");
        String title = input.next();
        if (bs.inBookshelf(title)) {
            Book b = bs.getBook(title);

            if (command.equals("status")) {
                System.out.println("Enter new status: r =  read, cr = currently reading, tbr = to be read:");
                String status = input.next();
                try {
                    b.changeStatus(status);
                    System.out.println("Status changed successfully.");
                } catch (InvalidStatusException e) {
                    System.out.println(e.getMessage());
                }

            } else if (command.equals("rating")) {
                System.out.println("Enter new rating between 1 and 5; 0 if not yet read:");
                int rating = input.nextInt();
                try {
                    b.changeRating(rating);
                    System.out.println("Rating changed successfully.");
                } catch (InvalidRatingException e) {
                    System.out.println(e.getMessage());
                }
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
        try {
            bs.setGoal(num);
            if (num == 1) {
                System.out.println("Reading goal set to " + num + " book!");
            } else {
                System.out.println("Reading goal set to " + num + " books!");
            }
        } catch (InvalidGoalException e) {
            System.out.println(e.getMessage());
        }
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
        if (num == 1) {
            System.out.println("Your bookshelf is called " + name + " and has 1 book on it!");
        } else {
            System.out.println("Your bookshelf is called " + name + " and has " + num + " books on it!");
        }
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
        } catch (Exception e) { // should never occur since loading valid bookshelf from scratch
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS: prints a log of every action taken in the session with time stamps
    private void printLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    // EFFECTS: displays option to save bookshelf
    private void displayPromptSave() {
        System.out.println("Do you want to save " + bs.getName() + " to file?");
        System.out.println("y -> yes");
        System.out.println("n -> no");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processPromptSave(String command) {
        if (command.equals("y")) {
            saveBookshelf();
        } else if (!command.equals("n")) {
            System.out.println("Invalid selection. Bookshelf not saved to file.");
        }
    }

}
