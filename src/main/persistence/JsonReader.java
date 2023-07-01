package persistence;

import exceptions.DuplicateBookException;
import exceptions.InvalidEntryException;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.String.valueOf;

// source: JsonSerializationDemo
// represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads bookshelf from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Bookshelf read() throws IOException, InvalidEntryException, DuplicateBookException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookshelf(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses bookshelf from JSON object and returns it
    private Bookshelf parseBookshelf(JSONObject jsonObject) throws InvalidEntryException, DuplicateBookException {
        String name = jsonObject.getString("name");
        int goal = jsonObject.getInt("goal");
        EventLog.getInstance().logEvent(new Event("Loaded bookshelf from file."));
        Bookshelf bs = new Bookshelf(name);
        bs.setGoal(goal);
        addBooks(bs, jsonObject);
        return bs;
    }

    // MODIFIES: bs
    // EFFECTS: parses books from JSON object and adds them to bookshelf
    private void addBooks(Bookshelf bs, JSONObject jsonObject) throws InvalidEntryException, DuplicateBookException {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bs, nextBook);
        }
    }

    // MODIFIES: bs
    // EFFECTS: parses book from JSON object and adds it to the workroom
    private void addBook(Bookshelf bs, JSONObject jsonObject) throws InvalidEntryException, DuplicateBookException {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        String status = statusToString(BookStatus.valueOf(jsonObject.getString("status")));
        int rating = jsonObject.getInt("rating");
        Book book = new Book(title, author, status, rating);
        bs.shelveBook(book);
    }

    private String statusToString(BookStatus status) {
        String ret = null;
        if (status == BookStatus.READ) {
            ret = "r";
        } else if (status == BookStatus.TOBEREAD) {
            ret = "tbr";
        } else if (status == BookStatus.CURRENTLYREADING) {
            ret = "cr";
        }
        return ret;
    }
}
