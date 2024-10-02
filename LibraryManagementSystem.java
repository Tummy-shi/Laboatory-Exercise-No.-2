import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

class Book {
    String title;
    String author;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author;
    }
}

class Library {
    private ArrayList<Book> books;
    private Stack<Book> deletedBooks;

    public Library() {
        books = new ArrayList<>();
        deletedBooks = new Stack<>();
    }

    // Add a book
    public void addBook(String title, String author) {
        books.add(new Book(title, author));
    }

    // View all books
    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Selection sort to sort books by title
   
            }
            // Swap
            Book temp = books.get(minIdx);
            books.set(minIdx, books.get(i));
            books.set(i, temp);
        }
        System.out.println("Books sorted by title.");
    }

    // Remove a book and push to stack
    public void removeBook(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).title.equalsIgnoreCase(title)) {
                deletedBooks.push(books.remove(i));
                System.out.println("Removed book: " + title);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    // Restore last removed book
    public void restoreLastRemoved() {
        if (!deletedBooks.isEmpty()) {
            Book book = deletedBooks.pop();
            books.add(book);
            System.out.println("Restored book: " + book.title);
        } else {
            System.out.println("No books to restore.");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Sort Books");
            System.out.println("4. Remove Book");
            System.out.println("5. Restore Last Removed Book");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    library.viewBooks();
                    break;
                case 3:
                    library.sortBooks();
                    break;
                case 4:
                    System.out.print("Enter book title to remove: ");
                    title = scanner.nextLine();
                    library.removeBook(title);
                    break;
                case 5:
                    library.restoreLastRemoved();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
