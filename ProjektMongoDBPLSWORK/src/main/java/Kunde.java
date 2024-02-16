import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class Kunde {
    private MongoCollection<Document> collection;
    private Scanner scanner;

    public Kunde(MongoDatabase database) {
        collection = database.getCollection("Kunde");
        scanner = new Scanner(System.in);
    }

    public void displaySubMenu(String option) {
        while (true) {
            System.out.println("Sie haben " + option + " ausgewählt.");
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Alle Kundendaten anzeigen");
            System.out.println("2. Kundendaten bearbeiten");
            System.out.println("3. Neuen Kunden erstellen");
            System.out.println("4. Kundendaten löschen");
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
                    createNewCustomer();
                    break;
                case 4:
                    deleteCustomer();
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
            System.out.println(doc.toJson());
        }
    }




    public void displaySubSubMenu(String option) {
        while (true) {
            System.out.println("Sie haben " + option + " ausgewählt.");
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Geschlecht");
            System.out.println("2. Nachname");
            System.out.println("3. Vorname");
            System.out.println("4. Adresse");
            System.out.println("5. Telefon");
            System.out.println("6. E-Mail");
            System.out.println("7. Sprache");
            System.out.println("8. Geburtsdatum");
            System.out.println("9. Alles");
            System.out.println("0. Zurück zum Hauptmenü");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    editField("Geschlecht");
                    break;
                case 2:
                    editField("Nachname");
                    break;
                case 3:
                    editField("Vorname");
                    break;
                case 4:
                    editField("Adresse");
                    break;
                case 5:
                    editField("Telefon");
                    break;
                case 6:
                    editField("E-Mail");
                    break;
                case 7:
                    editField("Sprache");
                    break;
                case 8:
                    editField("Geburtsdatum");
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

        System.out.println("Geben Sie den Nachnamen des Kunden ein, den Sie bearbeiten möchten:");
        String kundeName = scanner.nextLine();

        Document query = new Document("Nachname", kundeName);
        Document update = new Document("$set", new Document(fieldName, newValue));

        if (collection.updateOne(query, update).getModifiedCount() > 0) {
            System.out.println("Das Feld " + fieldName + " für den Kunden " + kundeName + " wurde erfolgreich aktualisiert.");
        } else {
            System.out.println("Es wurde kein Kunde mit dem Namen " + kundeName + " gefunden.");
        }
    }

    private void editAllFields() {
        System.out.println("Geben Sie den neuen Wert für jedes Feld ein:");

        System.out.println("Geben Sie den Nachnamen des Kunden ein, den Sie bearbeiten möchten:");
        String kundeName = scanner.nextLine();

        Document query = new Document("Nachname", kundeName);
        Document update = new Document();

        System.out.println("Geschlecht:");
        update.append("$set", new Document("Geschlecht", scanner.nextLine()));

        System.out.println("Vorname:");
        update.append("$set", new Document("Vorname", scanner.nextLine()));

        System.out.println("Adresse:");
        update.append("$set", new Document("Adresse", scanner.nextLine()));

        System.out.println("Telefon:");
        update.append("$set", new Document("Telefon", scanner.nextLine()));

        System.out.println("E-Mail:");
        update.append("$set", new Document("E-Mail", scanner.nextLine()));

        System.out.println("Sprache:");
        update.append("$set", new Document("Sprache", scanner.nextLine()));

        System.out.println("Geburtsdatum:");
        update.append("$set", new Document("Geburtsdatum", scanner.nextLine()));

        if (collection.updateOne(query, update).getModifiedCount() > 0) {
            System.out.println("Alle Felder für den Kunden " + kundeName + " wurden erfolgreich aktualisiert.");
        } else {
            System.out.println("Es wurde kein Kunde mit dem Namen " + kundeName + " gefunden.");
        }
    }
    private void createNewCustomer() {
        System.out.println("Neuen Kunden erstellen:");

        System.out.println("Geben Sie den Nachnamen des Kunden ein:");
        String nachname = scanner.nextLine();

        System.out.println("Geben Sie den Vornamen des Kunden ein:");
        String vorname = scanner.nextLine();

        System.out.println("Geben Sie die Adresse des Kunden ein:");
        String adresse = scanner.nextLine();

        System.out.println("Geben Sie die Telefonnummer des Kunden ein:");
        String telefon = scanner.nextLine();

        System.out.println("Geben Sie die E-Mail-Adresse des Kunden ein:");
        String email = scanner.nextLine();

        System.out.println("Geben Sie die Sprache des Kunden ein:");
        String sprache = scanner.nextLine();

        System.out.println("Geben Sie das Geburtsdatum des Kunden ein:");
        String geburtsdatum = scanner.nextLine();

        // Neuen Kunden in der Datenbank erstellen
        Document newCustomer = new Document("Nachname", nachname)
                .append("Vorname", vorname)
                .append("Adresse", adresse)
                .append("Telefon", telefon)
                .append("E-Mail", email)
                .append("Sprache", sprache)
                .append("Geburtsdatum", geburtsdatum);

        collection.insertOne(newCustomer);

        System.out.println("Neuer Kunde wurde erfolgreich erstellt.");
    }

    private void deleteCustomer() {
        System.out.println("Kunde löschen:");

        System.out.println("Geben Sie den Nachnamen des Kunden ein:");
        String nachname = scanner.nextLine();

        // Prüfen, ob der Kunde existiert
        Document query = new Document("Nachname", nachname);
        FindIterable<Document> result = collection.find(query);

        if (result.iterator().hasNext()) {
            // Kunden gefunden, löschen
            collection.deleteOne(query);
            System.out.println("Kunde " + nachname + " wurde erfolgreich gelöscht.");
        } else {
            // Kunden nicht gefunden
            System.out.println("Kunde nicht gefunden. Bitte versuchen Sie es erneut.");
        }
    }
}
