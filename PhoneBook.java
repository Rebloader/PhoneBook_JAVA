import java.util.*;
import java.io.*;

public class PhoneBook {
    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> phoneBook = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Print PhoneBook");
            System.out.println("3. Contact search");
            System.out.println("4. Load file");
            System.out.println("5. Save changes in file");
            System.out.println("6. Exit");
            System.out.print("Choose action: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Add contact name: ");
                    String name = scanner.nextLine();
                    System.out.print("Add phone number: ");
                    String phoneNumber = scanner.nextLine();
                    addContact(phoneBook, name, phoneNumber);
                    break;
                case 2:
                    System.out.println("PhoneBook: ");
                    printPhoneBook(phoneBook);
                    break;
                case 3:
                    System.out.print("Input a name to search: ");
                    String searchName = scanner.nextLine();
                    searchContacts(phoneBook, searchName);
                    break;
                case 4:
                    phoneBook = loadPhoneBook();
                    System.out.println("Phonebook successfully loaded");
                    break;
                case 5:
                    savePhoneBook(phoneBook);
                    System.out.println("Successfully saved!");
                    break;
                case 6:
                    System.out.println("PhoneBook is closed");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Incorrect input. Try again!");
            }
        }
    }

    public static void addContact(HashMap<String, ArrayList<String>> phoneBook, String name, String phoneNumber) {
        if (!phoneBook.containsKey(name)) {
            phoneBook.put(name, new ArrayList<>());
        }
        phoneBook.get(name).add(phoneNumber);
    }

    public static void printPhoneBook(HashMap<String, ArrayList<String>> phoneBook) {
        List<Map.Entry<String, ArrayList<String>>> sortedEntries = new ArrayList<>(phoneBook.entrySet());
        sortedEntries.sort((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()));

        for (Map.Entry<String, ArrayList<String>> entry : sortedEntries) {
            String name = entry.getKey();
            ArrayList<String> phoneNumbers = entry.getValue();

            System.out.println(name + ": " + phoneNumbers.size() + " phone number(s)");
            for (String phoneNumber : phoneNumbers) {
                System.out.println("\t" + phoneNumber);
            }
        }
    }
    public static void searchContacts(HashMap<String, ArrayList<String>> phoneBook, String name) {
        if (phoneBook.containsKey(name)) {
            ArrayList<String> phoneNumbers = phoneBook.get(name);
            System.out.println("Found contact " + name + ":");
            for (String phoneNumber : phoneNumbers) {
                System.out.println(phoneNumber);
            }
        } else {
            System.out.println("Contact " + name + " doesn't found.");
        }
    }

    public static HashMap<String, ArrayList<String>> loadPhoneBook() {
        HashMap<String, ArrayList<String>> phoneBook = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Phonebook.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    addContact(phoneBook, name, phoneNumber);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phoneBook;
    }
    public static void savePhoneBook(HashMap<String, ArrayList<String>> phoneBook) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Phonebook.txt"))) {
            for (Map.Entry<String, ArrayList<String>> entry : phoneBook.entrySet()) {
                String name = entry.getKey();
                ArrayList<String> phoneNumbers = entry.getValue();
                for (String phoneNumber : phoneNumbers) {
                    writer.println(name + "," + phoneNumber);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
