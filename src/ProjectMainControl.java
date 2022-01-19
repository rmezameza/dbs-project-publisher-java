import java.util.Scanner;

public class ProjectMainControl {
    public static void main(String[] args) {
        DatabaseInserter databaseFiller = new DatabaseInserter();

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
                case 1:
                    System.out.println("Noch nicht fertig");
                    System.out.print("Test: Buch - Einträge: ");
                    System.out.print(databaseFiller.testConnection());
                    break;
                case 4:
                    System.out.println("Vielen Dank für die Benutzung des Dateimanagers.");
                    System.out.println("Bis zum nächsten Mal");
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Sie haben eine falsche Nummer ausgewählt. Bitte nur von 1 bis 4 eingeben.\n\n");
                    break;
            }
        }
    }
}
