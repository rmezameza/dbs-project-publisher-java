import java.util.Scanner;

public class ProjectMainControl {
    public static void main(String[] args) {
        DatabaseInserter databaseInserter = new DatabaseInserter();
        TableChecker tableChecker = new TableChecker();

        Scanner userChoice = new Scanner(System.in);
        boolean continueProgram = true;


            System.out.println("Willkommen bei Bahoe Books Datenmanager");
            System.out.println("Dieser befüllt automatisch die Datenbank mit Daten");
            System.out.println("Die Daten sind pseudo-randomisiert und dienen für Testzwecke");

        while(continueProgram) {
            System.out.println("Bitte wählen Sie eines der Optionen aus:");
            System.out.println("1. Datenbank komplett befüllen");

            System.out.println("Bitte geben Sie die Optionsnummer (1-2) ein:");
            int userChoiceNumber = userChoice.nextInt();

            switch (userChoiceNumber) {
                case 1 -> {
                    int tableCounter = 0;
                    int totalCounter = 0;
                    // First insert n (number defined by user) employees
                    System.out.println("Anzahl der Mitarbeiter:innen:");
                    userChoiceNumber = userChoice.nextInt();
                    databaseInserter.insertEmployees(userChoiceNumber);
                    int totalEmployees = tableChecker.checkNumberOfInserts("mitarbeiter");

                    totalCounter += 2 * totalEmployees;
                    System.out.println("Es wurden " + totalEmployees + " eingefügt");

                    // Second insert internal and external employees
                    int numberOfPermanentEmployees = (totalEmployees / 2) - 250;
                    databaseInserter.insertInternalEmployees(numberOfPermanentEmployees);
                    System.out.println("Es wurden (Gesamt Mitarbeiter:innen / 2) - 250) fix Angestellte hinzugefügt: " + tableChecker.checkNumberOfInserts("intern"));
                    System.out.println("Es wurden (Gesamt Mitarbeiter:innen - fix Angestellte) freie Dienstnehmer:innen hinzugefügt: " + tableChecker.checkNumberOfInserts("extern"));

                    // Insert books
                    databaseInserter.insertBooks();

                    tableCounter = tableChecker.checkNumberOfInserts("buch");
                    totalCounter += tableCounter;
                    System.out.println("Es wurden insgesamt: " + tableCounter + " Bücher eingefügt.");

                    // Insert authors
                    databaseInserter.insertAuthors();
                    tableCounter = tableChecker.checkNumberOfInserts("autor");
                    totalCounter += tableCounter;
                    System.out.println("Es wurden insgesamt: " + tableCounter + " Autor:innen eingefügt.");

                    // Connect authors to books
                    databaseInserter.insertBooksToAuthors();
                    tableCounter = tableChecker.checkNumberOfInserts("schreibt");
                    totalCounter += tableCounter;
                    System.out.println("Es wurden insgesamt: " + tableCounter + " Daten eingefügt");

                    // Insert bookstores
                    databaseInserter.insertBookstores();
                    tableCounter = tableChecker.checkNumberOfInserts("buchlager");
                    totalCounter += tableCounter;
                    System.out.println("Es wurden insgesamt: " + tableCounter + " Buchlager eingefügt");

                    // Insert racks
                    databaseInserter.insertRacks();
                    tableCounter = tableChecker.checkNumberOfInserts("regal");
                    totalCounter += tableCounter;
                    System.out.println("Es wurden insgesamt: " + tableCounter + " Regale eingefügt");

                    // Insert books in racks
                    databaseInserter.insertBooksInRacks();
                    tableCounter = tableChecker.checkNumberOfInserts("gelagert");
                    totalCounter += tableCounter;
                    System.out.println("Es wurden insgesamt: " + tableCounter + " Bücher in die Regale eingefügt");

                    // Insert book reviewer
                    databaseInserter.insertBookReviewers();
                    tableCounter = tableChecker.checkNumberOfInserts("buchkritiker");
                    totalCounter += tableCounter;

                    System.out.println("Es wurden insgesamt: " + tableCounter + " Buchkritiker:innen eingefügt");

                    // Insert recensions
                    databaseInserter.insertBookReviews();
                    tableCounter = tableChecker.checkNumberOfInserts("rezension");
                    totalCounter += tableCounter;
                    System.out.println("Es wurden insgesamt: " + tableCounter + " Rezensionen eingefügt");

                    // Insert ternary relationship reviews - reviewer - books
                    databaseInserter.insertBookReviews();
                    tableCounter = tableChecker.checkNumberOfInserts("rezension");
                    totalCounter += tableCounter;

                    System.out.println("Es wurden insgesamt: " + tableCounter + " in die ternäre Beziehung eingefügt\n");

                    System.out.println("Es wurden insgesamt >> " + totalCounter + " << Daten eingefügt!");

                }
                case 2 -> {
                    System.out.println("Vielen Dank für die Benutzung des Dateimanagers.");
                    System.out.println("Bis zum nächsten Mal");
                    continueProgram = false;
                }
                default -> System.out.println("Sie haben eine falsche Nummer ausgewählt. Bitte nur von 1 bis 2 eingeben.\n\n");
            }
        }
    }
}
