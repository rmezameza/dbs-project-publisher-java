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
            System.out.println("2. Tabellen einzeln befüllen");
            System.out.println("3. Datenbank löschen");

            System.out.println("Bitte geben Sie die Optionsnummer (1-4) ein:");
            int userChoiceNumber = userChoice.nextInt();

            switch (userChoiceNumber) {
                case 1 -> {
                    // First insert n (number defined by user) employees
                    // System.out.println("Anzahl der Mitarbeiter:innen:");
                    // userChoiceNumber = userChoice.nextInt();
                    // databaseInserter.insertEmployees(userChoiceNumber);
                    //int totalEmployees = tableChecker.checkNumberOfInserts("mitarbeiter");
                    //System.out.println("Es wurden " + totalEmployees + " eingefügt");

                    // Second insert internal and external employees
                    //int numberOfPermanentEmployees = (totalEmployees / 2) - 250;
                    //databaseInserter.insertInternalEmployees(numberOfPermanentEmployees);
                    //System.out.println("Es wurden (Gesamt Mitarbeiter:innen / 2) - 250) fix Angestellte hinzugefügt: " + tableChecker.checkNumberOfInserts("intern"));
                    //System.out.println("Es wurden (Gesamt Mitarbeiter:innen - fix Angestellte) freie Dienstnehmer:innen hinzugefügt: " + tableChecker.checkNumberOfInserts("extern"));

                    // Insert books


                    // Insert authors
                    databaseInserter.insertAuthors();
                }
                case 4 -> {
                    System.out.println("Vielen Dank für die Benutzung des Dateimanagers.");
                    System.out.println("Bis zum nächsten Mal");
                    continueProgram = false;
                }
                default -> System.out.println("Sie haben eine falsche Nummer ausgewählt. Bitte nur von 1 bis 4 eingeben.\n\n");
            }
        }
    }
}
