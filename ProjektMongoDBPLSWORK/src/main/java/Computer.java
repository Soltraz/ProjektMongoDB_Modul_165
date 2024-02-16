import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class Computer {
    private MongoCollection<Document> collection;
    private Scanner scanner;

    public Computer(MongoDatabase database) {
        collection = database.getCollection("Computer");
        scanner = new Scanner(System.in);
    }

    public void displaySubMenu(String option) {
        while (true) {
            System.out.println("Sie haben " + option + " ausgewählt.");
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Alle Computer anzeigen");
            System.out.println("2. Computer bearbeiten");
            System.out.println("3. Neuen Computer erstellen");
            System.out.println("4. Computer löschen");
            System.out.println("5. Zurück zum Hauptmenü");
            System.out.println("6. Programm beenden");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    displayAllData();
                    break;
                case 2:
                    displayAllData();
                    displaySubSubMenu("Bearbeiten");
                    break;
                case 3:
                    createNewComputer();
                    break;
                case 4:
                    deleteComputer();
                    break;
                case 5:
                    return; // Zurück zum Hauptmenü
                case 6:
                    System.out.println("Programm wird beendet.");
                    System.exit(0); // Programm beenden
                default:
                    System.out.println("Ungültige Auswahl!");
            }
        }
    }

    private void displayAllData() {
        for (Document doc : collection.find()) {
            for (String key : doc.keySet()) {
                System.out.println(key + ": " + doc.get(key));
            }
            System.out.println();
        }
    }

    public void displaySubSubMenu(String option) {
        while (true) {
            System.out.println("Sie haben " + option + " ausgewählt.");
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Hersteller");
            System.out.println("2. Modell");
            System.out.println("3. Arbeitsspeicher [GB]");
            System.out.println("4. CPU");
            System.out.println("5. Massenspeicher [GB]");
            System.out.println("6. Typ");
            System.out.println("7. Einzelpreis");
            System.out.println("8. Schnittstellen");
            System.out.println("9. Alles");
            System.out.println("0. Zurück zum Hauptmenü");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    editField("Hersteller");
                    break;
                case 2:
                    editField("Modell");
                    break;
                case 3:
                    editField("Arbeitsspeicher [GB]");
                    break;
                case 4:
                    editField("CPU");
                    break;
                case 5:
                    editField("Massenspeicher [GB]");
                    break;
                case 6:
                    editField("Typ");
                    break;
                case 7:
                    editField("Einzelpreis");
                    break;
                case 8:
                    editField("Schnittstellen");
                    break;
                case 9:
                    editAllFields();
                    break;
                case 0:
                    return; // Zurück zum Hauptmenü
                default:
                    System.out.println("Ungültige Auswahl!");
            }
        }
    }

    private void editField(String fieldName) {
        System.out.println("Geben Sie den neuen Wert für " + fieldName + " ein:");
        String newValue = scanner.nextLine();

        System.out.println("Geben Sie den Modellnamen des Computers ein, den Sie bearbeiten möchten:");
        String computerModell = scanner.nextLine();

        Document query = new Document("Modell", computerModell);
        Document update = new Document("$set", new Document(fieldName, newValue));

        if (collection.updateOne(query, update).getModifiedCount() > 0) {
            System.out.println("Das Feld " + fieldName + " für den Computer " + computerModell + " wurde erfolgreich aktualisiert.");
        } else {
            System.out.println("Es wurde kein Computer mit dem Modell " + computerModell + " gefunden.");
        }
    }

    private void editAllFields() {
        System.out.println("Geben Sie den neuen Wert für jedes Feld ein:");

        System.out.println("Geben Sie das Modell des Computers ein, den Sie bearbeiten möchten:");
        String computerModell = scanner.nextLine();

        Document query = new Document("Modell", computerModell);
        Document update = new Document();

        System.out.println("Hersteller:");
        update.append("$set", new Document("Hersteller", scanner.nextLine()));

        System.out.println("Arbeitsspeicher [GB]:");
        update.append("$set", new Document("Arbeitsspeicher [GB]", scanner.nextLine()));

        System.out.println("CPU:");
        update.append("$set", new Document("CPU", scanner.nextLine()));

        System.out.println("Massenspeicher [GB]:");
        update.append("$set", new Document("Massenspeicher [GB]", scanner.nextLine()));

        System.out.println("Typ:");
        update.append("$set", new Document("Typ", scanner.nextLine()));

        System.out.println("Einzelpreis:");
        update.append("$set", new Document("Einzelpreis", scanner.nextLine()));

        System.out.println("Schnittstellen:");
        update.append("$set", new Document("Schnittstellen", scanner.nextLine()));

        if (collection.updateOne(query, update).getModifiedCount() > 0) {
            System.out.println("Alle Felder für den Computer " + computerModell + " wurden erfolgreich aktualisiert.");
        } else {
            System.out.println("Es wurde kein Computer mit dem Modell " + computerModell + " gefunden.");
        }
    }

    private void createNewComputer() {
        System.out.println("Neuen Computer erstellen:");

        System.out.println("Geben Sie den Hersteller ein:");
        String hersteller = scanner.nextLine();

        System.out.println("Geben Sie das Modell ein:");
        String modell = scanner.nextLine();

        System.out.println("Geben Sie den Arbeitsspeicher [GB] ein:");
        String arbeitsspeicher = scanner.nextLine();

        System.out.println("Geben Sie die CPU ein:");
        String cpu = scanner.nextLine();

        System.out.println("Geben Sie den Massenspeicher [GB] ein:");
        String massenspeicher = scanner.nextLine();

        System.out.println("Geben Sie den Typ ein (Desktop/Notebook):");
        String typ = scanner.nextLine();

        System.out.println("Geben Sie den Einzelpreis ein:");
        String einzelpreis = scanner.nextLine();

        System.out.println("Geben Sie die Schnittstellen ein:");
        String schnittstellen = scanner.nextLine();

        // Neuen Computer in der Datenbank erstellen
        Document newComputer = new Document("Hersteller", hersteller)
                .append("Modell", modell)
                .append("Arbeitsspeicher [GB]", arbeitsspeicher)
                .append("CPU", cpu)
                .append("Massenspeicher [GB]", massenspeicher)
                .append("Typ", typ)
                .append("Einzelpreis", einzelpreis)
                .append("Schnittstellen", schnittstellen);

        collection.insertOne(newComputer);

        System.out.println("Neuer Computer wurde erfolgreich erstellt.");
    }

    private void deleteComputer() {
        System.out.println("Computer löschen:");

        System.out.println("Geben Sie das Modell des Computers ein:");
        String modell = scanner.nextLine();

        // Prüfen, ob der Computer existiert
        Document query = new Document("Modell", modell);
        FindIterable<Document> result = collection.find(query);

        if (result.iterator().hasNext()) {
            // Computer gefunden, löschen
            collection.deleteOne(query);
            System.out.println("Computer mit dem Modell " + modell + " wurde erfolgreich gelöscht.");
        } else {
            // Computer nicht gefunden
            System.out.println("Computer mit dem Modell " + modell + " nicht gefunden. Bitte versuchen Sie es erneut.");
        }
    }
}
