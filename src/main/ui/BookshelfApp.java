package ui;

import exceptions.*;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Bookshelf application with a console UI using a Scanner

// Note: I have included "MODIFIES: Scanner" in every method that has 'input.next()' and 'input.nextLine()' since those
//       methods alter the Scanner class fields
public class BookshelfApp {

    private static final String JSON_STORE = "./data/bookshelf.json";
    private Bookshelf bs;
    private Scanner input;

    // EFFECTS: runs the Bookshelf Application
    // source: TellerApp
    public BookshelfApp() {
        runBookshelfApp();
    }

    // MODIFIES: this, Scanner
    // EFFECTS: checks and maintains app running/quitting when necessary
    // source: TellerApp
    private void runBookshelfApp() {
        boolean keepGoing = true;

        init();

        while (keepGoing) {
            displayMenuRoot();
            String command = input.next();

            if (command.equals("q")) {
                displayPromptSave();
                processPromptSave(input.next());
                printLog();
                keepGoing = false;
            } else {
                processCommandRoot(command);
            }
        }
        System.out.println("Happy Reading!");
    }

    // MODIFIES: this, Scanner
    // EFFECTS: initializes bookshelf
    // source: TellerApp
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        displayPromptLoad();
        processPromptLoad(input.nextLine());
    }

    // EFFECTS: displays option to load bookshelf from file
    private void displayPromptLoad() {
        System.out.println("Do you want to load a bookshelf from file?");
        System.out.println("y -> yes");
        System.out.println("n -> no, I want to create a new one");
    }

    // processes user command of opening prompt
    // MODIFIES: this
    // EFFECTS: loads previous or creates new bookshelf; defaults to new bookshelf with invalid input
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

    // creates new bookshelf
    // MODIFIES: this
    // EFFECTS: instantiates new bookshelf with inputted name
    private void createBookshelf() {
        System.out.println("Name your bookshelf:");
        bs = new Bookshelf(input.nextLine());
    }

    // main menu
    // EFFECTS: displays first menu of options (root) to user
    private void displayMenuRoot() {
        System.out.println("a -> add book");
        System.out.println("b -> view books");
        System.out.println("c -> change rating or status of a book");
        System.out.println("g -> set reading goal");
        System.out.println("p -> view reading goal progress");
        System.out.println("r -> remove book");
        System.out.println("v -> view bookshelf characteristics");
        System.out.println("s -> save bookshelf to file");
        System.out.println("l -> load bookshelf from file");
        System.out.println("q -> quit");
    }

    // EFFECTS: processes user command from main menu
    private void processCommandRoot(String command) {
        switch (command) {
            case "a":
                addBook();
                break;
            case "b":
                viewBooks();
                break;
            case "c":
                changeABook();
                break;
            case "g":
                doSetGoal();
                break;
            case "p":
                viewGoalProgress();
                break;
            case "r":
                removeBook();
                break;
            case "v":
                viewBookshelfChar();
                break;
            case "s":
                saveBookshelf();
                break;
            case "l":
                loadBookshelf();
                break;
            default:
                System.out.println("Selection not valid. Please select another option.");
                break;
        }
    }

    // MODIFIES: Scanner, Bookshelf
    // EFFECTS: adds a book to the bookshelf with inputted specifications, or throws exception due to invalid input,
    //          duplicate book title
    private void addBook() {
        // title
        System.out.println("Enter title of book:");
        input.nextLine();
        String title = input.nextLine();

        // author
        System.out.println("Enter the name of the author:");
        String author = input.nextLine();

        try {
            // status
            System.out.println("Enter the status of the book: r = read, cr = currently reading, tbr = to be read:");
            String status = input.next();
            checkValidStatus(status); // want exception thrown here if invalid status

            // rating
            System.out.println("Enter the star rating of the book out of 5; 0 if not yet read:");
            int rating = input.nextInt();
            checkValidRating(rating); // want exception thrown here if invalid rating

            // shelving
            bs.shelveBook(new Book(title, author, status, rating));
            System.out.println(title + " has been successfully added to your bookshelf.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS: throws exception if rating is invalid
    private void checkValidRating(int rat) throws InvalidRatingException {
        if (rat < 0 || rat > 5) {
            throw new InvalidRatingException();
        }
    }

    // EFFECTS: throws exception if status is invalid
    private void checkValidStatus(String stat) throws InvalidStatusException {
        if (!stat.equals("r") && !stat.equals("cr") && !stat.equals("tbr")) {
            throw new InvalidStatusException();
        }
    }

    // MODIFIES: Scanner
    // EFFECTS: calls methods for menu and processing input for viewing books
    private void viewBooks() {
        displayMenuView();
        processCommandView(input.next());
    }

    // EFFECTS: displays menu of options for viewing books
    private void displayMenuView() {
        System.out.println("book -> view a specific book");
        System.out.println("status -> view books of a certain status");
        System.out.println("rating -> view books of a certain rating");
        System.out.println("all -> view all books on your bookshelf");
    }

    // MODIFIES: Scanner
    // EFFECTS: processes user command from the menu for viewing books, or throws exception due to invalid status or
    //          rating
    private void processCommandView(String command) {
        switch (command) {

            // viewing a single book
            case "book":
                System.out.println("Enter title of book:");
                String title = input.next();
                if (bs.inBookshelf(title)) {
                    printAllBookData(bs.getBook(title));
                } else {
                    System.out.println("No book matching that title is in the bookshelf.");
                }
                break;

            // viewing all books of a certain status
            case "status":
                System.out.println("Enter status of books you would like to view: "
                        + "r =  read, cr = currently reading, tbr = to be read");
                String status = input.next();
                try {
                    ArrayList<Book> bks = bs.getBooksOfStatus(status); // can throw invalid status exception
                    if (bks.isEmpty()) {
                        System.out.println("There are no books of the given status on your bookshelf.");
                    }
                    for (Book b : bks) {
                        printAllBookData(b);
                    }
                } catch (InvalidStatusException e) {
                    System.out.println(e.getMessage());
                }
                break;

            // viewing all books of a certain rating
            case "rating":
                System.out.println("Enter rating of books you would like to view; between 0 and 5:");
                int rating = input.nextInt();
                try {
                    ArrayList<Book> bks = bs.getBooksOfRating(rating); // can throw invalid rating exception
                    if (bks.isEmpty()) {
                        System.out.println("There are no books of the given rating on your bookshelf.");
                    }
                    for (Book b : bks) {
                        printAllBookData(b);
                    }
                } catch (InvalidRatingException e) {
                    System.out.println(e.getMessage());
                }
                break;

            // viewing all books currently on the bookshelf
            case "all":
                if (bs.getBooks().isEmpty()) {
                    System.out.println("Your bookshelf is empty.");
                }
                for (Book b : bs.getAllBooks()) {
                    printAllBookData(b);
                }
                break;
            // invalid menu choice inputted
            default:
                System.out.println("Not a valid input.");
                break;
        }
    }

    // EFFECTS: prints to console all the data of the given book
    private void printAllBookData(Book b) {
        System.out.println(b.getTitle() + " by " + b.getAuthor()
                + ", " + statusToNiceString(b.getStatus()) + ", " + convertRatingToEmojis(b.getRating()));
    }

    // EFFECTS: converts BookStatus to string to display
    private String statusToNiceString(BookStatus status) {
        if (status == BookStatus.READ) {
            return "read";
        } else if (status == BookStatus.TOBEREAD) {
            return "to be read";
        } else {
            return "currently reading";
        }
    }

    // EFFECTS: converts rating to a string of star emojis; empty string if rating is 0
    private String convertRatingToEmojis(int rating) {
        String ret = "";
        String star = "\u2B50";
        for (int i = 1; i <= rating; i++) {
            ret += star;
        }
        return ret;
    }

    // MODIFIES: Scanner
    // EFFECTS: calls methods for menu and processing input for changing a book
    private void changeABook() {
        displayMenuChange();
        processCommandChange(input.next());
    }

    // EFFECTS: displays menu of options for changing a book
    private void displayMenuChange() {
        System.out.println("status -> change status");
        System.out.println("rating -> change rating");
    }

    // MODIFIES: Scanner, Book
    // EFFECTS: processes user command for changing a book
    private void processCommandChange(String command) {
        System.out.println("Enter the title of the book you would like to alter:");
        String title = input.next();
        // checking if book is in bookshelf
        if (bs.inBookshelf(title)) {
            // if so, retrieve it
            Book b = bs.getBook(title);

            // changing status
            if (command.equals("status")) {
                System.out.println("Enter new status: r =  read, cr = currently reading, tbr = to be read:");
                try {
                    b.changeStatus(input.next());
                    System.out.println("Status changed successfully.");
                } catch (InvalidStatusException e) {
                    System.out.println(e.getMessage());
                }

            // changing rating
            } else if (command.equals("rating")) {
                System.out.println("Enter new rating between 1 and 5; 0 if not yet read:");
                try {
                    b.changeRating(input.nextInt());
                    System.out.println("Rating changed successfully.");
                } catch (InvalidRatingException e) {
                    System.out.println(e.getMessage());
                }
            }
        // book with given title is not in bookshelf
        } else {
            System.out.println("Book with given title is not in bookshelf.");
        }
    }

    // MODIFIES: Bookshelf, Scanner
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

    // MODIFIES: Bookshelf, Scanner
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
    // source: JsonSerializationDemo
    private void saveBookshelf() {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);

        try {
            jsonWriter.open();
            jsonWriter.write(bs);
            jsonWriter.close();
            System.out.println("Saved " + bs.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, Bookshelf
    // EFFECTS: loads bookshelf from file
    private void loadBookshelf() {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        try {
            bs = jsonReader.read();
            System.out.println("Loaded " + bs.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (Exception e) { // should never occur since loading valid bookshelf
            System.out.println(e.getMessage());
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

    // EFFECTS: prints a log of every action taken in the session with time stamps
    private void printLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

}
