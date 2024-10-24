import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OfflineDictionary {

    private static final String DICTIONARY_FILE = "dictionary.txt"; //hackterms
    private static Map<String, String> dictionary = new HashMap<>();

    public static void main(String[] args) {
        loadDictionary();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Offline Dictionary for Educational Terms ---");
            System.out.println("1. Add a new term");
            System.out.println("2. Search for a term");
            System.out.println("3. List all terms");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    addTerm(scanner);
                    break;
                case 2:
                    searchTerm(scanner);
                    break;
                case 3:
                    listTerms();
                    break;
                case 4:
                    saveDictionary();
                    System.out.println("Exiting the program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    // Function to load the dictionary from file
    private static void loadDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2);  // Split the term and definition
                if (parts.length >= 2) {
                    String term = parts[0].trim();
                    String definition = parts[1].trim();
                    dictionary.put(term, definition);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing dictionary file found. Starting fresh.");
        }
    }

    // Function to save the dictionary to file
    private static void saveDictionary() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DICTIONARY_FILE))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
            System.out.println("Dictionary saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving dictionary: " + e.getMessage());
        }
    }

    // Function to add a new term to the dictionary
    private static void addTerm(Scanner scanner) {
        System.out.print("Enter the new term: ");
        String term = scanner.nextLine().trim();

        if (dictionary.containsKey(term)) {
            System.out.println("Term '" + term + "' already exists.");
        } else {
            System.out.print("Enter the definition for '" + term + "': ");
            String definition = scanner.nextLine().trim();
            dictionary.put(term, definition);
            System.out.println("Term '" + term + "' added with definition: " + definition);
        }
    }

    // Function to search for a term in the dictionary
    private static void searchTerm(Scanner scanner) {
        System.out.print("Enter the term to search for: ");
        String term = scanner.nextLine().trim();

        if (dictionary.containsKey(term)) {
            System.out.println("Definition of '" + term + "': " + dictionary.get(term));
        } else {
            System.out.println("Term '" + term + "' not found in the dictionary.");
        }
    }

    // Function to list all terms in the dictionary
    private static void listTerms() {
        if (dictionary.isEmpty()) {
            System.out.println("The dictionary is empty.");
        } else {
            System.out.println("\n--- List of Terms ---");
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("---------------------");
        }
    }
}
