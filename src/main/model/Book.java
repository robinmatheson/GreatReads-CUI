package model;

import exceptions.InvalidEntryException;
import exceptions.InvalidRatingException;
import exceptions.InvalidStatusException;
import org.json.JSONObject;
import persistence.Writable;

// represents a book with title, author name, reading status, and star rating
public class Book implements Writable {
    private String title;             // title of book
    private String author;            // name of author
    private BookStatus status;        // status is READ, CURRENTLYREADING, or TOBEREAD
    private int rating;               // star rating for read books between 1 and 5; 0 if not yet read

    /*
     * EFFECTS: title of book is set to given title;
     *          name of author is set to author;
     *          set status of book to one of READ (r), CURRENTLYREADING (cr), or TOBEREAD (tbr);
     *             else throws exception
     *          set star rating between 1 and 5, 0 if not yet read;
     *             else throws exception
     */
    public Book(String title, String author, String status, int rating) throws InvalidEntryException {
        this.title = title;
        this.author = author;
        this.status = convertStatus(status);
        if (rating < 0 || rating > 5) {
            throw new InvalidRatingException();
        } else {
            this.rating = rating;
        }
    }

    // EFFECTS: converts string to BookStatus
    private BookStatus convertStatus(String input) throws InvalidStatusException {
        BookStatus status = BookStatus.TOBEREAD;
        if (input.equals("r")) {
            status = BookStatus.READ;
        } else if (input.equals("cr")) {
            status = BookStatus.CURRENTLYREADING;
        } else if (input.equals("tbr")) {
            //status = BookStatus.TOBEREAD; //redundant since default to TBR
        } else {
            throw new InvalidStatusException();
        }
        return status;
    }

    // setters

    // REQUIRES: status is one of "read", "currently reading", or "to be read"; else throws exception
    // MODIFIES: this
    // EFFECTS: sets status as given status
    public void changeStatus(String status) throws InvalidStatusException {
        BookStatus stat = convertStatus(status);
        this.status = stat;
        EventLog.getInstance().logEvent(new Event("Changed status of " + title));
    }

    // REQUIRES: rating is between 1 and 5; 0 if book is not read
    // MODIFIES: this
    // EFFECTS: sets rating as given rating
    public void changeRating(int rating) throws InvalidRatingException {
        if (rating < 0 || rating > 5) {
            throw new InvalidRatingException();
        } else {
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

