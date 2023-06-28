package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a book with title, author name, reading status, and star rating
public class Book implements Writable {
    private String title;             // title of book
    private String author;            // name of author
    private BookStatus status;        // status is READ, CURRENTLYREADING, or TOBEREAD
    private int rating;               // star rating for read books between 1 and 5; 0 if not yet read

    /*
     * REQUIRES: status is one of READ, CURRENTLYREADING, or TOBEREAD;
     *          star rating is between 1 and 5, or 0 if not yet read
     * EFFECTS: title of book is set to title and cannot be assigned to any other book;
     *          name of author is set to author;
     *          set status of book to one of READ, CURRENTLYREADING, or TOBEREAD;
     *          set star rating between 1 and 5, 0 if not yet read.
     */
    public Book(String title, String author, BookStatus status, int rating) {
        this.title = title;
        this.author = author;
        this.status = status;
        this.rating = rating;
    }

    // setters

    // REQUIRES: status is one of "read", "currently reading", or "to be read"
    // MODIFIES: this
    // EFFECTS: sets status as given status
    public void changeStatus(BookStatus status) {
        if (this.status != status) {
            this.status = status;
        }
        EventLog.getInstance().logEvent(new Event("Changed status of " + title));
    }

    // REQUIRES: rating is between 1 and 5; 0 if book is not read
    // MODIFIES: this
    // EFFECTS: sets rating as given rating
    public void changeRating(int rating) {
        if (this.rating != rating) {
            this.rating = rating;
        }
        EventLog.getInstance().logEvent(new Event("Changed rating of " + title));
    }

    // getters
    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public BookStatus getStatus() {
        return this.status;
    }

    public int getRating() {
        return this.rating;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("status", status);
        json.put("rating", rating);
        return json;
    }
}

