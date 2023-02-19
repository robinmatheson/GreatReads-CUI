package ui;

import model.Book;
import model.BookStatus;
import model.Bookshelf;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;

// opens a GUI that  allows you to create a bookshelf and add books to it
public class BookshelfGUI extends JFrame implements ActionListener {

    private JPanel panel;
    private JFrame frame;

    private JLabel windowTitle;

    private JTextField fieldTitle;
    private JTextField fieldAuthor;
    private JComboBox<String> statusCB;
    private JComboBox<String> ratingCB;
    private JTextField fieldChangeName;
    private JLabel labelCard;
    private JTextField fieldBurnBook;

    private GridBagConstraints ctitleLabel = new GridBagConstraints();
    private GridBagConstraints ctitleField = new GridBagConstraints();
    private GridBagConstraints cauthorLabel = new GridBagConstraints();
    private GridBagConstraints cauthorField = new GridBagConstraints();
    private GridBagConstraints cstatusLabel = new GridBagConstraints();
    private GridBagConstraints cstatusCB = new GridBagConstraints();
    private GridBagConstraints cratingLabel = new GridBagConstraints();
    private GridBagConstraints cratingCB = new GridBagConstraints();
    private GridBagConstraints caddBook = new GridBagConstraints();
    private GridBagConstraints cicon = new GridBagConstraints();
    private GridBagConstraints cchangeNameLabel = new GridBagConstraints();
    private GridBagConstraints cchangeNameField = new GridBagConstraints();
    private GridBagConstraints cchangeNameButton = new GridBagConstraints();
    private GridBagConstraints cwindowTitle = new GridBagConstraints();
    private GridBagConstraints csave = new GridBagConstraints();
    private GridBagConstraints cload = new GridBagConstraints();
    private GridBagConstraints cdisplayShelf = new GridBagConstraints();
    private GridBagConstraints clabelCard = new GridBagConstraints();
    private GridBagConstraints cgetCardButton = new GridBagConstraints();
    private GridBagConstraints clabelBurnBook = new GridBagConstraints();
    private GridBagConstraints cfieldBurnBook = new GridBagConstraints();
    private GridBagConstraints cburnBookButton = new GridBagConstraints();

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/bookshelf.json";

    private Bookshelf bs;

    // EFFECTS: calls JFrame super and method to begin building GUI
    public BookshelfGUI() {
        super("");
        initializeGUI();
    }

    // EFFECTS: opens pop-up window and constructs bookshelf
    private void initializeGUI() {
        String inputName = showInputDialog("Please enter the name of your bookshelf:");
        if (inputName == null) {
            bs = new Bookshelf("My Bookshelf");
        } else {
            bs = new Bookshelf(inputName);
        }
        mainMenu();
    }

    // EFFECTS: opens a panel with the main menu
    private void mainMenu() {
        setUpMainMenu();
        setUpIcon();
        setUpAddBook();
        setUpBurnBook();
        setUpGetCardinality();
        setUpChangeBookshelfName();
        setUpPersistence();
        setUpShelfDisplay();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: frame
    // EFFECTS: builds and adds frame and components
    private void setUpMainMenu() {
        frame = new JFrame();
        frame.setTitle(bs.getName());
        panel = new JPanel();
        add(panel);
        setUpClosingFunctions();
        setMinimumSize(new Dimension(700, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());
        setUpGridBagConstraints();
    }

    // MODIFIES: frame
    // EFFECTS: puts icon in main menu frame
    private void setUpIcon() {
        ImageIcon imageIcon = new ImageIcon(
                getClass().getResource("/resources/img.png"));
        Image img = imageIcon.getImage();
        Image newImage = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        JLabel mmIcon = new JLabel(image);
        panel.add(mmIcon, cicon);
        addNameTitle();
    }

    // MODIFIES: frame
    // EFFECTS: adds bookshelf name as JLabel at top of window
    private void addNameTitle() {
        windowTitle = new JLabel(bs.getName());
        panel.add(windowTitle, cwindowTitle);
    }

    // MODIFIES: frame
    // EFFECTS: constructs labels and fields for adding book functionality, adds them to fame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"}) // it's only 26 lines!!
    private void setUpAddBook() {
        JLabel labelTitle = new JLabel("Title:");
        add(labelTitle, ctitleLabel);
        fieldTitle = new JTextField(15);
        add(fieldTitle, ctitleField);
        JLabel labelAuthor = new JLabel("Author:");
        add(labelAuthor, cauthorLabel);
        fieldAuthor = new JTextField(15);
        add(fieldAuthor, cauthorField);
        JLabel labelStatus = new JLabel("Reading Status:");
        add(labelStatus, cstatusLabel);
        String[] statusChoices = new String[]{"to be read", "currently reading", "read"};
        statusCB = new JComboBox<>(statusChoices);
        statusCB.setSelectedItem("to be read");
        add(statusCB, cstatusCB);
        JLabel labelRating = new JLabel("Rating:");
        add(labelRating, cratingLabel);
        String[] ratingChoices = new String[]{"0", "1", "2", "3", "4", "5"};
        ratingCB = new JComboBox<>(ratingChoices);
        ratingCB.setSelectedItem("0");
        add(ratingCB, cratingCB);
        JButton addBookButton = new JButton("Add Book");
        addBookButton.setActionCommand("addBookButton");
        addBookButton.addActionListener(this);
        add(addBookButton, caddBook);
    }

    // MODIFIES: frame
    // EFFECTS: constructs label and button for displaying cardinality of bookshelf
    private void setUpGetCardinality() {
        labelCard = new JLabel("");
        add(labelCard, clabelCard);
        JButton getCardButton = new JButton("Get Cardinality");
        add(getCardButton, cgetCardButton);
        getCardButton.setActionCommand("getCardButton");
        getCardButton.addActionListener(this);
    }

    // MODIFIES: frame
    // EFFECTS: constructs labels and field to remove a book from the bookshelf
    private void setUpBurnBook() {
        JLabel labelBurnBook = new JLabel("Enter title of the book you want to remove:");
        add(labelBurnBook, clabelBurnBook);
        fieldBurnBook = new JTextField(15);
        add(fieldBurnBook, cfieldBurnBook);
        JButton burnBookButton = new JButton("Burn Book");
        add(burnBookButton, cburnBookButton);
        burnBookButton.setActionCommand("burnBookButton");
        burnBookButton.addActionListener(this);
    }


    // MODIFIES: frame
    // EFFECTS: constructs labels and fields for changing the name of the bookshelf, adds them to frame
    private void setUpChangeBookshelfName() {
        JLabel labelChangeName = new JLabel("Enter new bookshelf name:");
        add(labelChangeName, cchangeNameLabel);
        fieldChangeName = new JTextField(15);
        add(fieldChangeName, cchangeNameField);
        JButton changeNameButton = new JButton("Enter");
        add(changeNameButton, cchangeNameButton);
        changeNameButton.setActionCommand("changeNameButton");
        changeNameButton.addActionListener(this);
    }

    // MODIFIES: frame
    // EFFECTS: constructs labels and fields for persistence, adds them to frame
    private void setUpPersistence() {
        JButton saveBookshelfButton = new JButton("Save Bookshelf");
        add(saveBookshelfButton, csave);
        saveBookshelfButton.setActionCommand("saveBookshelf");
        saveBookshelfButton.addActionListener(this);
        JButton loadBookshelfButton = new JButton("Load Bookshelf");
        add(loadBookshelfButton, cload);
        loadBookshelfButton.setActionCommand("loadBookshelf");
        loadBookshelfButton.addActionListener(this);
    }

    // MODIFIES: frame
    // EFFECTS: constructs labels and fields for viewing list of books in the bookshelf, adds them to frame
    private void setUpShelfDisplay() {
        JButton openShelfDisplayButton = new JButton("View Bookshelf");
        add(openShelfDisplayButton, cdisplayShelf);
        openShelfDisplayButton.setActionCommand("viewBookshelf");
        openShelfDisplayButton.addActionListener(this);
    }

    // EFFECTS: calls methods to set up grid bag constraints for each functionality
    private void setUpGridBagConstraints() {
        setUpIconConstraints();
        setUpAddBookConstraints();
        setUpChangeBookshelfNameConstraints();
        setUpPersistenceConstraints();
        setUpShelfDisplayConstraints();
        setUpBurnBookConstraints();
        setUpGetCardinalityConstraints();
    }

    private void setUpGetCardinalityConstraints() {
        clabelCard.gridx = 2;
        clabelCard.gridy = 7;
        cgetCardButton.gridx = 1;
        cgetCardButton.gridy = 7;
    }

    private void setUpBurnBookConstraints() {
        clabelBurnBook.gridx = 0;
        clabelBurnBook.gridy = 6;
        cfieldBurnBook.gridx = 1;
        cfieldBurnBook.gridy = 6;
        cburnBookButton.gridx = 2;
        cburnBookButton.gridy = 6;
    }

    // EFFECTS: sets constraints for icon and bookshelf name
    private void setUpIconConstraints() {
        cicon.gridx = 0;
        cicon.gridy = 0;
        cwindowTitle.gridx = 1;
        cwindowTitle.gridy = 0;
    }

    // EFFECTS: sets constraints for adding book to bookshelf
    private void setUpAddBookConstraints() {
        ctitleLabel.gridx = 0;
        ctitleLabel.gridy = 1;
        ctitleField.gridx = 1;
        ctitleField.gridy = 1;
        cauthorLabel.gridx = 0;
        cauthorLabel.gridy = 2;
        cauthorField.gridx = 1;
        cauthorField.gridy = 2;
        cstatusLabel.gridx = 0;
        cstatusLabel.gridy = 3;
        cstatusCB.gridx = 1;
        cstatusCB.gridy = 3;
        cratingLabel.gridx = 0;
        cratingLabel.gridy = 4;
        cratingCB.gridx = 1;
        cratingCB.gridy = 4;
        caddBook.gridx = 1;
        caddBook.gridy = 5;
    }

    // EFFECTS: sets constraints for changing the name of the bookshelf
    private void setUpChangeBookshelfNameConstraints() {
        cchangeNameLabel.gridx = 0;
        cchangeNameLabel.gridy = 8;
        cchangeNameField.gridx = 1;
        cchangeNameField.gridy = 8;
        cchangeNameButton.gridx = 2;
        cchangeNameButton.gridy = 8;
    }

    // EFFECTS: sets constraints for persistence functionality
    private void setUpPersistenceConstraints() {
        csave.gridx = 1;
        csave.gridy = 9;
        cload.gridx = 1;
        cload.gridy = 10;
    }

    // EFFECTS: sets constraints for button to view bookshelf
    private void setUpShelfDisplayConstraints() {
        cdisplayShelf.gridx = 1;
        cdisplayShelf.gridy = 11;
    }

    // EFFECTS: opens new scroller with list of books in bookshelf
    private void openShelfDisplay() {
        DefaultListModel<String> booksInfo = new DefaultListModel<>();
        for (int i = 0; i < bs.getCardinality(); i++) {
            Book book = bs.getBooks().getElementAt(i);
            String info = book.getTitle() + ", " + book.getAuthor() + ", " + book.getStatus() + ", " + book.getRating()
                    + " stars";
            booksInfo.addElement(info);
        }
        JList list = new JList(booksInfo);
        JFrame frame1 = new JFrame();
        JPanel panel1 = new JPanel();
        JScrollPane scroller = new JScrollPane(list);
        panel1.add(scroller);
        frame1.add(panel1);
        frame1.setPreferredSize(new Dimension(300, 180));
        frame1.pack();
        frame1.setLocationRelativeTo(null);
        frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame1.setVisible(true);
    }

    // EFFECTS: listens for actions performed and executes methods when detected
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addBookButton")) {
            String title = fieldTitle.getText();
            String author = fieldAuthor.getText();
            BookStatus status = convertStatus(statusCB.getSelectedItem().toString());
            int rating = parseInt(ratingCB.getSelectedItem().toString());
            Book book = new Book(title, author, status, rating);
            bs.shelveBook(book);
        }
        if (e.getActionCommand().equals("changeNameButton")) {
            bs.setName(fieldChangeName.getText());
            windowTitle.setText(fieldChangeName.getText());
        }
        if (e.getActionCommand().equals("saveBookshelf")) {
            try {
                jsonWriter = new JsonWriter(JSON_STORE);
                jsonWriter.open();
                jsonWriter.write(bs);
                jsonWriter.close();
            } catch (FileNotFoundException exc) {
                //
            }
        }
        if (e.getActionCommand().equals("loadBookshelf")) {
            try {
                jsonReader = new JsonReader(JSON_STORE);
                bs = jsonReader.read();
                windowTitle.setText(bs.getName());
            } catch (IOException exc) {
                //
            }
        }
        if (e.getActionCommand().equals("viewBookshelf")) {
            openShelfDisplay();
        }
        if (e.getActionCommand().equals("getCardButton")) {
            labelCard.setText(String.valueOf(bs.getCardinality()));
        }
        if (e.getActionCommand().equals("burnBookButton")) {
            String titleToBurn = fieldBurnBook.getText();
            bs.burnBook(titleToBurn);
        }
    }

    // EFFECTS: converts status of book from string to BookStatus
    private BookStatus convertStatus(String s) {
        BookStatus stat = BookStatus.TOBEREAD;
        if (s.equals("read")) {
            stat = BookStatus.READ;
        } else if (s.equals("currently reading")) {
            stat = BookStatus.CURRENTLYREADING;
        }
        return stat;
    }

    public void setUpClosingFunctions() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                printLog();
            }
        });
    }

    private void printLog() {
        Iterator<Event> it = EventLog.getInstance().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString() + "\n");
        }
        frame.dispose();
        System.exit(0);
    }

}

