package model;

import model.Book;
import model.BookStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// represents all the books that are added to the user's bookshelf
public class Bookshelf implements Writable {

    private String name;
    private ArrayList<Book> books;
    private int goal;

    // EFFECTS: constructs a bookshelf with given name and owner's name
    public Bookshelf(String name) {
        this.name = name;
        this.goal = 0;
        this.books = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Made bookshelf called " + getName()));
    }

    // REQUIRES: goal >= 0
    // MODIFIES: this
    // EFFECTS: sets goal to int given
    public void setGoal(int goal) {
        this.goal = goal;
    }

    // MODIFIES: this
    // EFFECTS: changes bookshelf name to one given
    public void setName(String newName) {
        this.name = newName;
        EventLog.getInstance().logEvent(new Event("Changed name of bookshelf to " + getName()));
    }

    // MODIFIES: this
    // EFFECTS: adds book to list of books in the bookshelf
    public void shelveBook(Book book) {
        if (!inBookshelf(book)) {
            books.add(book);
        }
        EventLog.getInstance().logEvent(new Event("Added " + book.getTitle() + " to bookshelf"));
    }

    // EFFECTS: returns true if the book is in the bookshelf
    public boolean inBookshelf(Book book) {
        return books.contains(book);
    }

    // EFFECTS: returns true if the book is in the bookshelf
    public boolean inBookshelf(String title) {
        boolean isInBookshelf = false;
        for (Book b : books) {
            if (Objects.equals(b.getTitle(), title)) {
                isInBookshelf = true;
                break;
            }
        }
        return isInBookshelf;
    }

    // MODIFIES: this
    // EFFECTS: removes book from the bookshelf
    public void burnBook(String title) {
        for (int i = 0; i < getBooks().size(); i++) {
            Book book = getBooks().get(i);
            if (book.getTitle().equals(title)) {
                getBooks().remove(i);
                break;
            }
        }
        EventLog.getInstance().logEvent(new Event("Removed " + title + " from bookshelf"));
    }

    // getters

    // REQUIRES: book is already in bookshelf
    // EFFECTS: returns given book in bookshelf
    public Book getBook(String title) {
        ArrayList<Book> hereItIs = new ArrayList<>();
        for (Book b : books) {
            if (Objects.equals(b.getTitle(), title)) {
                hereItIs.add(b);
            }
        }
        return hereItIs.get(0);
    }

    // EFFECTS: returns list of titles of all the books in the bookshelf
    public ArrayList<String> getAllBooks() {
        ArrayList<String> allBooks = new ArrayList<>();
        for (Book b : books) {
            allBooks.add(b.getTitle());
        }
        return allBooks;
    }

    // EFFECTS: returns list titles of all the books in the bookshelf of given status
    public ArrayList<String> getBooksOfStatus(BookStatus status) {
        ArrayList<String> allBooksOfStatus = new ArrayList<>();
        if (status == BookStatus.TOBEREAD) {
            for (Book b : books) {
                if (b.getStatus() == BookStatus.TOBEREAD) {
                    allBooksOfStatus.add(b.getTitle());
                }
            }
        }
        if (status == BookStatus.CURRENTLYREADING) {
            for (Book b : books) {
                if (b.getStatus() == BookStatus.CURRENTLYREADING) {
                    allBooksOfStatus.add(b.getTitle());
                }
            }
        }
        if (status == BookStatus.READ) {
            for (Book b : books) {
                if (b.getStatus() == BookStatus.READ) {
                    allBooksOfStatus.add(b.getTitle());
                }
            }
        }
        return allBooksOfStatus;
    }

    // REQUIRES: rating must be an integer between 0 and 5, inclusive
    // EFFECTS: returns titles of all books with given rating
    public ArrayList<String> getBooksOfRating(int rating) {
        ArrayList<String> allBooksOfRating = new ArrayList<>();
        if (rating == 0) {
            for (Book b : books) {
                if (b.getRating() == 0) {
                    allBooksOfRating.add(b.getTitle());
                }
            }
        }
        if (rating == 1) {
            for (Book b : books) {
                if (b.getRating() == 1) {
                    allBooksOfRating.add(b.getTitle());
                }
            }
        }
        if (rating == 2) {
            for (Book b : books) {
                if (b.getRating() == 2) {
                    allBooksOfRating.add(b.getTitle());
                }
            }
        }
        if (rating == 3) {
            for (Book b : books) {
                if (b.getRating() == 3) {
                    allBooksOfRating.add(b.getTitle());
                }
            }
        }
        if (rating == 4) {
            for (Book b : books) {
                if (b.getRating() == 4) {
                    allBooksOfRating.add(b.getTitle());
                }
            }
        }
        if (rating == 5) {
            for (Book b : books) {
                if (b.getRating() == 5) {
                    allBooksOfRating.add(b.getTitle());
                }
            }
        }
        return allBooksOfRating;
    }

    // EFFECTS: returns a string with the number of books read out of the goal number read
    public String getGoalProgress() {
        int read = getNumberRead();
        int goal = getGoal();
        return "You have read " + read + " books out of your goal of " + goal + "!";
    }

    // EFFECTS: returns number of books on the bookshelf
    public int getCardinality() {
        EventLog.getInstance().logEvent(new Event("Retrieved number of books in bookshelf"));
        return books.size();
    }

    // EFFECTS: returns the number of books of status READ on the bookshelf
    public int getNumberRead() {
        int count = 0;
        for (Book b : books) {
            if (b.getStatus() == BookStatus.READ) {
                count++;
            }
        }
        return count;
    }


    // EFFECTS: returns owner's goal for number of books they want to read
    public int getGoal() {
        return this.goal;
    }


    // EFFECTS: returns a string stating the name of the bookshelf
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the list of books in the bookshelf
    public ArrayList<Book> getBooks() {
        return this.books;
    }

    // source for all following methods: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        json.put("goal", goal);
        return json;
    }
    
    // EFFECTS: return books in this workroom as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();
        
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }

}
