import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * This is the Graphic User Interface for the library application.
 * Runs the application on launch.
 *
 * @author (Espen Otlo)
 * @version (0.5)
 */
public class Main {
    public static void main(String[] args) {

        //Creates a new object of the library class that will manage the books.
        Library library = new Library();

        //Creates the main frame for the application, and sets it so the application stops upon closing the window.
        JFrame frame = new JFrame("Biblioteket til Espen");
        frame.setSize(650,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Creates the panel that will hold the components of the GUI.
        JPanel mainPanel = (JPanel)frame.getContentPane();
        mainPanel.setLayout(null);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));

        // Creates a title banner for the application
        JLabel title = new JLabel("Velkommen til Biblioteket", SwingConstants.CENTER);
        Font displayFont = title.getFont().deriveFont(20.0f);
        title.setFont(displayFont);
        title.setOpaque(true);
        title.setBackground(Color.white);
        title.setBorder(new EtchedBorder());
        title.setBounds (10,10,620,40);

        // Creates a text area that will function as a log for user output.
        JTextArea log = new JTextArea(5,30);
        log.setFocusable(false);
        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        JScrollPane windowScroll = new JScrollPane(log);
        windowScroll.setBounds(170,60,220,300);
        windowScroll.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(),"Logg", 1, 3));

        // Creates a text area that will contain an updated list of all the books in the library.
        JTextArea bookList= new JTextArea();
        bookList.setFocusable(false);
        bookList.setEditable(false);
        JScrollPane bookListScroll = new JScrollPane(bookList);
        bookListScroll.setBounds(400,60,220,300);
        bookListScroll.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(),"Alle Bøker", 1, 3));

        // Creates a search field with a prompt text that hides itself upon receiving input from the user.
        HintTextField searchField = new HintTextField("Skriv inn søkeord her.");
        searchField.setBounds(170,370,450,25);
        searchField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        searchField.setColumns(3);

        // Creates a button to load example books into the library and update the book list.
        JButton loadLib = new JButton("Last inn demo");
        loadLib.addActionListener(e -> {
            if (!library.loadLib()) {
                log.append("Demo er allerede lastet inn!\n\n");
            }
            bookList.setText(library.getBookList());
        });

        // Creates a button to register a new book.
        // Attempts to add a new book to the library, with input from the user through jOptionPanes,
        // then updates the book list.
        JButton addBook = new JButton("Legg til bok");
        addBook.addActionListener(e -> {
            String returnString;
            try {
                String bookTitle = "";
                boolean validInput = false;
                while (!validInput) {
                    bookTitle = JOptionPane.showInputDialog("Angi boktittel");
                    if (bookTitle.length() > 0) {
                        validInput = true;
                    }
                }
                String bookAuthor = "";
                validInput = false;
                while (!validInput) {
                    bookAuthor = JOptionPane.showInputDialog("Angi forfatter");
                    if (bookAuthor.length() > 1) {
                        validInput = true;
                    }
                }
                String publisher = "";
                validInput = false;
                while (!validInput) {
                    publisher = JOptionPane.showInputDialog("Angi forlag");
                    if (publisher.length() > 1) {
                        validInput = true;
                    }
                }
                int yearOfRelease = 0;
                validInput = false;
                while (!validInput) {
                    String year = JOptionPane.showInputDialog("Angi utgivelsesår (4 siffer)");
                    if (year.matches("[0-9]{4,}")) {
                        validInput = true;
                        yearOfRelease = parseInt(year);
                    }
                }
                int numberOfPages = 0;
                validInput = false;
                while (!validInput) {
                    String pages = JOptionPane.showInputDialog("Angi sidetall (2-4 siffer)");
                    if (pages.matches("[0-9]{1,4}")) {
                        validInput = true;
                        numberOfPages = parseInt(pages);
                    }
                }
                String barCode = "";
                validInput = false;
                while (!validInput) {
                    barCode = (JOptionPane.showInputDialog("Angi strekkode (13 siffer)"));
                    if (barCode.matches("[0-9]{13,}")) {
                        validInput = true;
                    }
                }
                if (library.addBook(bookTitle, bookAuthor, publisher, yearOfRelease, numberOfPages, barCode)) {
                    returnString = (
                            "Bok opprettet:"
                                    + "\nTittel: " + bookTitle
                                    + "\nForfatter: " + bookAuthor
                                    + "\nForlag: " + publisher
                                    + "\nUtgivelsesår: " + yearOfRelease
                                    + "\nSidetall: " + numberOfPages
                                    + "\nEAN: " + barCode + "\n\n");
                } else {
                    returnString = "En bok med denne strekkoden finnes allerede i biblioteket.\nVennligst prøv på nytt.\n\n";
                }
            } catch (Exception NullPointerException) {
                returnString = "Oppretting avbrutt.\n\n";
            }
            log.append(returnString);
            bookList.setText(library.getBookList());
        });

        // Creates a button to remove a book by name and update the book list.
        //     * Searches the library for a book with the title given through a JOptionFrame,
        //     * asks the user if it should be deleted through a confirmDialog, and removes the book if positive.
        //     * Prints confirmation or error messages accordingly.
        JButton removeByTitle = new JButton("Slett (tittel)");
        removeByTitle.addActionListener(e -> {
            String returnString;
            String searchString = "";
            boolean validInput = false;
            while (!validInput) {
                searchString = JOptionPane.showInputDialog("Angi tittel på boken som skal slettes.");
                if (searchString == null || searchString.length() > 0) {
                    validInput = true;
                }
            }
            if (searchString == null) {
                returnString = ("Sletting avbrutt.\n\n");
            } else {
                Book i = library.getBookByTitle(searchString);
                if (null == i) {
                    returnString = "Finner ingen bok med tittelen " + searchString + ".\nVennligst prøv igjen.\n\n";
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Slette " + i.getTitle() + "?");
                    if (confirm == 0) {
                        if (library.removeByTitle(searchString)) {
                            returnString = "Slettet " + i.getTitle() + " fra biblioteket.\n\n";
                        } else {
                            returnString = "Noe gikk alvorlig galt. Verden gikk i stykker.\n\n";
                        }
                    } else {
                        returnString = "Sletting avbrutt.\n\n";
                    }
                }
            }
            log.append(returnString);
            bookList.setText(library.getBookList());
        });

        // Creates a button to remove a book by index and update the book list.
        //     * Searches the library for a book with the index given through a JOptionFrame,
        //     * asks the user if it should be deleted through a confirmDialog, and removes the book if positive.
        //     * Prints confirmation or error messages accordingly.
        JButton removeByIndex = new JButton("Slett (index)");
        removeByIndex.addActionListener(e -> {
            String returnString;
            String searchString = "";
            boolean validInput = false;
            while (!validInput) {
                searchString = JOptionPane.showInputDialog("Angi nummer til boken som skal slettes.");
                if (searchString == null || searchString.matches("[0-9]{1,2}")) {
                    validInput = true;
                }
            }
            if (searchString == null) {
                returnString = ("Sletting avbrutt.\n\n");
            } else {
                int searchInt = parseInt(searchString)-1;
                Book i = library.getBookByIndex(searchInt);
                if (null==i) {
                    returnString = "Finner ingen bok med det nummeret. Vennligst prøv igjen.\n\n";
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Slette " + i.getTitle() + "?");
                    if (confirm == 0) {
                        if (library.removeByIndex(searchInt)) {
                            returnString = "Slettet " + i.getTitle() + " fra biblioteket.\n\n";
                        } else {
                            returnString = "Noe gikk alvorlig galt. Verden gikk i stykker.\n\n";
                        }
                    } else {
                        returnString = "Sletting avbrutt.\n\n";
                    }
                }
            }
            log.append(returnString);
            bookList.setText(library.getBookList());
        });

        // Creates a button to loan out a book from the library.
        //      * Attempts to loan out the book with the title given through a JOptionPane.
        //      * Returns a confirmation or error messages accordingly.
        JButton loanBook = new JButton("Lån ut");
        loanBook.addActionListener(e -> {
            String returnString = null;
            String searchString = "";
            String loanedTo = "";
            boolean validInput = false;
            while (!validInput) {
                searchString = JOptionPane.showInputDialog("Angi boktittel:");
                if (searchString == null || searchString.length() > 1) {
                    validInput = true;
                }
            }
            if (searchString == null) {
                returnString = "Utlån avbrutt.\n\n";
            } else {
                validInput = false;
                while (!validInput) {
                    loanedTo = JOptionPane.showInputDialog("Angi hvem boken skal lånes ut til:");
                    if (loanedTo == null || loanedTo.length() > 1) {
                        validInput = true;
                    }
                }
                if (loanedTo == null) {
                    returnString = "Utlån avbrutt.\n\n";
                } else {
                    int loanResult = library.loanOut(searchString,loanedTo);
                    switch (loanResult) {
                        case 0 -> returnString = "Finner ingen bok med tittel " + searchString
                                + " i biblioteket. Vennligst prøv igjen.\n\n";
                        case 1 -> returnString = library.getBookByTitle(searchString).getTitle() + " ble lånt ut til " + loanedTo + ".\n\n";
                        case 2 -> returnString = library.getBookByTitle(searchString).getTitle() + " er allerede på utlån til "
                                + library.getBookByTitle(searchString).getLoanedTo() + ".\n\n";
                    }
                }
            }
            log.append(returnString);
        });

        // Creates a button to return a loaned book to the library.
        //      * Attempts to return to the library the book with the title given through a JOptionPane.
        //      * Prints confirmation or error messages accordingly.
        JButton returnBook = new JButton("Bokretur");
        returnBook.addActionListener(e -> {
            String returnString = null;
            String searchString = "";
            boolean validInput = false;
            while (!validInput) {
                searchString = JOptionPane.showInputDialog("Angi boktittel:");
                if (searchString == null || searchString.length() > 1) {
                    validInput = true;
                }
            }
            if (searchString == null) {
                returnString = "Utlån avbrutt.\n\n";
            } else {
                int returnResult = library.returnBook(searchString);
                switch (returnResult) {
                    case 0 -> returnString = "Finner ingen bok med tittel " + searchString
                            + " i biblioteket. Vennligst prøv igjen.\n\n";
                    case 1 -> returnString = library.getBookByTitle(searchString).getTitle() + " ble returnert til biblioteket.\n\n";
                    case 2 -> returnString = library.getBookByTitle(searchString).getTitle() + " er allerede på utlån til "
                            + library.getBookByTitle(searchString).getLoanedTo() + ".\n\n";
                }
            }
            log.append(returnString);
        });

        // Creates a toolbar for holding the menu buttons.
        JPanel toolBar = new JPanel();
        toolBar.setBounds(10,60,150,150);
        toolBar.setLayout(new GridLayout(0,1));

        // Adds all the menu buttons to the menu toolbar.
        toolBar.add(loadLib);
        toolBar.add(addBook);
        toolBar.add(removeByTitle);
        toolBar.add(removeByIndex);
        toolBar.add(loanBook);
        toolBar.add(returnBook);

        // Creates a button to search the library for a book by the title entered in the search field.
        // * Returns number of hits, as well as the full information of books found.
        // * Returns an error message if no input is given, or if no books are found.
        JButton searchTitle = new JButton("Tittelsøk");
        searchTitle.addActionListener(e -> {
            if (searchField.getText().equals("Skriv inn søkeord her.") || searchField.getText().length() < 3) {
                log.append("Vennligst angi et søkeord i feltet under og prøv igjen.\n\n");
            } else {
                ArrayList<Book> resultList = library.searchTitle(searchField.getText());
                if (resultList.size() == 0) {
                    log.append("Søket på " + searchField.getText() + " hadde ingen treff.\n\n");
                } else {
                    if (resultList.size() == 1) {
                        log.append("Søket på " + searchField.getText() + " hadde 1 treff:\n\n");
                    } else {
                        log.append("Søket på " + searchField.getText() + " hadde " + resultList.size() + " treff:\n\n");
                    }
                    for (Book book : resultList) {
                        log.append("Tittel: " + (book.getTitle() + "\nForfatter: " + book.getAuthor()
                                + "\nForlag: " + book.getPublisher() + "\nUtgivelsesår: " + book.getYearOfRelease()
                                + "\nSidetall: " + book.getNumberOfPages() + "\nEAN: "
                                + book.getBarCode() + "\nLånestatus: "));
                        if (null == book.getLoanedTo()) {
                            log.append("Tilgjengelig\n\n");
                        } else {
                            log.append("Lånt ut til " + book.getLoanedTo() + "\n\n");
                        }
                    }
                }
            }
        });
        searchTitle.setSize(150,25);

        // Creates a button to search the library for a book by the author entered in the search field.
        // * Prints number of hits, as well as the full information of books found.
        // * Returns an error message if no input is given, or if no books are found.
        JButton searchAuth = new JButton("Forfattersøk");
        searchAuth.addActionListener(e -> {
            if (searchField.getText().equals("Skriv inn søkeord her.") || searchField.getText().length() < 3) {
                log.append("Vennligst angi et søkeord i feltet under og prøv igjen.\n\n");
            } else {
                ArrayList<Book> resultList = library.searchAuthor(searchField.getText());
                if (resultList.size() == 0) {
                    log.append("Søket på " + searchField.getText() + " hadde ingen treff.\n\n");
                } else {
                    if (resultList.size() == 1) {
                        log.append("Søket på " + searchField.getText() + " hadde 1 treff:\n\n");
                    } else {
                        log.append("Søket på " + searchField.getText() + " hadde " + resultList.size() + " treff:\n\n");
                    }
                    for (Book book : resultList) {
                        log.append("Tittel: " + (book.getTitle() + "\nForfatter: " + book.getAuthor()
                                + "\nForlag: " + book.getPublisher() + "\nUtgivelsesår: " + book.getYearOfRelease()
                                + "\nSidetall: " + book.getNumberOfPages() + "\nEAN: "
                                + book.getBarCode() + "\nLånestatus: "));
                        if (null == book.getLoanedTo()) {
                            log.append("Tilgjengelig\n\n");
                        } else {
                            log.append("Lånt ut til " + book.getLoanedTo() + "\n\n");
                        }
                    }
                }
            }
        });
        searchAuth.setSize(150,25);

        // Creates a button to search the library for a book by the bar code entered in the search field.
        // * Returns number of hits, as well as the full information of books found.
        // * Returns an error message if no input is given, or if no books are found.
        JButton searchEAN = new JButton("EAN-søk");
        searchEAN.addActionListener(e -> {
            if (searchField.getText().equals("Skriv inn søkeord her.") || searchField.getText().length() < 3) {
                log.append("Vennligst angi et søkeord i feltet under og prøv igjen.\n\n");
            } else {
                ArrayList<Book> resultList = library.searchBarCode(searchField.getText());
                if (resultList.size() == 0) {
                    log.append("Søket på " + searchField.getText() + " hadde ingen treff.\n\n");
                } else {
                    if (resultList.size() == 1) {
                        log.append("Søket på " + searchField.getText() + " hadde 1 treff:\n\n");
                    } else {
                        log.append("Søket på " + searchField.getText() + " hadde " + resultList.size() + " treff:\n\n");
                    }
                    for (Book book : resultList) {
                        log.append("Tittel: " + (book.getTitle() + "\nForfatter: " + book.getAuthor()
                                + "\nForlag: " + book.getPublisher() + "\nUtgivelsesår: " + book.getYearOfRelease()
                                + "\nSidetall: " + book.getNumberOfPages() + "\nEAN: "
                                + book.getBarCode() + "\nLånestatus: "));
                        if (null == book.getLoanedTo()) {
                            log.append("Tilgjengelig\n\n");
                        } else {
                            log.append("Lånt ut til " + book.getLoanedTo() + "\n\n");
                        }
                    }
                }
            }
        });
        searchEAN.setSize(150,25);


        // Creates a toolbar for holding the search buttons.
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(0,1));
        searchPanel.setBounds(10,320,150,75);

        // Adds the search buttons to the search toolbar.
        searchPanel.add(searchTitle);
        searchPanel.add(searchAuth);
        searchPanel.add(searchEAN);

        // Adds all the components to the main panel.
        mainPanel.add(title);
        mainPanel.add(toolBar);
        mainPanel.add(windowScroll);
        mainPanel.add(bookListScroll);
        mainPanel.add(searchPanel);
        mainPanel.add(searchField);

        // Moves the application window to the center of the screen and makes it visible.
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }
}