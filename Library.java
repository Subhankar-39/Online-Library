import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class RegistrationException extends Exception {
    @Override
    public String getMessage() {
        return "You are not a member of the library";
    }
}

class LibraryManager {
    private Map<String, String> Books;
    private Map<String, Integer> dict;
    private Map<String, Integer> faculty;
    Map<String, String> updatedBookStock = new HashMap<>();
    Map<String, String> connectMap = new HashMap<>();

    public LibraryManager(Map<String, String> books, Map<String, Integer> members, Map<String, Integer> authorizedFaculty) {
        Books = books;
        dict = members;
        faculty = authorizedFaculty;
    }

    public void loginAndManageLibrary(Scanner scanner) {
        System.out.println("Official Log-in:");
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your password: ");
        int pass = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (!faculty.containsKey(name)) {
            System.out.println("You are not authorized.");
        } else {
            if (faculty.get(name) != pass) {
                System.out.println("Password is incorrect.");
            } else {
                while (true) {
                    System.out.println("1) New Registration\n2) Check Members\n3) Check Available Books\n4) Issue Books\n5) Add Books\n6) Return Book\n-----SELECT-----");
                    int select = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (select) {
                        case 1:
                            registerNewMember(scanner, dict);
                            break;
                        case 2:
                            checkMemberDetails(scanner, dict);
                            break;
                        case 3:
                            checkBookDetails(scanner, Books);
                            break;
                        case 4:
                            issueBook(scanner, Books, dict);
                            break;
                        case 5:
                            addBook(scanner, Books);
                            break;
                        default:
                            System.out.println("Invalid selection");
                    }
                }
            }
        }
    }

    public void registerNewMember(Scanner scanner, Map<String, Integer> dict) {
        System.out.println("Enter the new applicant name: ");
        String newName = scanner.nextLine();
        System.out.println("Set the password: ");
        int newPassword = scanner.nextInt();
        dict.put(newName, newPassword);
        System.out.println("The new member is added!");
        System.out.println(dict);
    }

    private void checkMemberDetails(Scanner scanner, Map<String, Integer> dict) {
        System.out.println(dict.keySet());
        System.out.println("Do you want to know the history of a specific member? Press 0");
        int press2 = scanner.nextInt();
        if (press2 == 0) {
            System.out.println("Enter the member name: ");
            String checkMember = scanner.next();
            if (dict.containsKey(checkMember)) {
                System.out.println(checkMember); // Have to enter the date and book name
            } else {
                System.out.println("No member found");
            }
        } else {
            System.out.println("No member found");
        }
    }

    private void checkBookDetails(Scanner scanner, Map<String, String> Books) {
        System.out.println(Books.keySet());
        System.out.println("Do you want to know about any specific book? Press 0");
        int press = scanner.nextInt();
        if (press == 0) {
            System.out.println("Enter the book name: ");
            scanner.nextLine();
            String checkBookName = scanner.nextLine();
            if (Books.containsKey(checkBookName)) {
                System.out.println(Books.get(checkBookName));
            } else {
                System.out.println("The book is not available");
            }
        }
    }

    public void issueBook(Scanner scanner, Map<String, String> Books, Map<String, Integer> dict) {
        System.out.println("Enter your work:\n1) Issue Book\n2) Return Book");
        int studentWork = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (studentWork == 1) {
            System.out.println("Enter your name:");
            String issueName = scanner.nextLine();
            System.out.println("Enter the book name to issue:");
            String issueBook = scanner.nextLine();

            if (Books.containsKey(issueBook) && dict.containsKey(issueName)) {
                String removedDesc = Books.get(issueBook);
                Books.remove(issueBook, removedDesc);
                System.out.println("The book " + issueBook + " has been issued by " + issueName);
                updatedBookStock.put(issueBook, removedDesc);
                connectMap.put(issueName, issueBook);
                System.out.println(updatedBookStock);
            } else {
                System.out.println("No book found or member not registered.");
            }

        } else if (studentWork == 2) {
            System.out.println("Enter your name:");
            String returnName = scanner.nextLine();
            System.out.println("Enter the book name to return:");
            String returnBook = scanner.nextLine();

            if (updatedBookStock.containsKey(returnBook) && connectMap.containsKey(returnName)) {
                Books.put(returnBook, updatedBookStock.get(returnBook));
                updatedBookStock.remove(returnBook);
                System.out.println("The book " + returnBook + " has been returned by " + returnName);
            } else {
                System.out.println("Book not found or member not registered.");
            }
        } else {
            System.out.println("Wrong choice");
        }
    }

    private void addBook(Scanner scanner, Map<String, String> Books) {
        System.out.println("Enter the book name to add:");
        String addBook = scanner.nextLine();
        System.out.println("Enter the description about the book:");
        String description = scanner.nextLine();
        Books.put(addBook, description);
        System.out.println("The book was successfully added!");
        System.out.println(Books);
    }
}

public class Library {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, String> Books = new HashMap<>();
        Map<String, Integer> dict = new HashMap<>();
        Map<String, Integer> faculty = new HashMap<>();
        faculty.put("Subhankar", 39039820);
        faculty.put("Moinak", 39039821);
        dict.put("Champak", 29);
        dict.put("Ankita", 8);

        Books.put("Macbeth", "Description of Macbeth...");
        Books.put("Data Structure using C", "Description of Data Structure using C...");

        LibraryManager libraryManager = new LibraryManager(Books, dict, faculty);
        libraryManager.loginAndManageLibrary(scanner);
    }
}
