import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class Bestellung {
    private MongoCollection<Document> collection;
    private Scanner scanner;

    public Bestellung(MongoDatabase database) {
        collection = database.getCollection("Bestellungen");
        scanner = new Scanner(System.in);
    }

    public void displaySubMenu(String option) {
        while (true) {
            System.out.println("Sie haben " + option + " ausgewählt.");
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Alle Bestellungen anzeigen");
            System.out.println("2. Bestellung bearbeiten");
            System.out.println("3. Neue Bestellung erstellen");
            System.out.println("4. Bestellung löschen");
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
                    createNewOrder();
                    break;
                case 4:
                    deleteOrder();
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
            System.out.println("1. Bestellnummer");
            System.out.println("2. Bestelldatum");
            System.out.println("3. Kunde");
            System.out.println("4. Bestelloptionen");
            System.out.println("5. Total");
            System.out.println("6. Alles");
            System.out.println("0. Zurück zum Hauptmenü");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    editField("Bestellnummer");
                    break;
                case 2:
                    editField("Bestelldatum");
                    break;
                case 3:
                    editField("Kunde");
                    break;
                case 4:
                    editField("Bestelloptionen");
                    break;
                case 5:
                    editField("Total");
                    break;
                case 6:
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

        System.out.println("Geben Sie den Namen der Bestellung ein, die Sie bearbeiten möchten:");
        String bestellName = scanner.nextLine();

        Document query = new Document("Bestellnummer", bestellName);
        Document update = new Document("$set", new Document(fieldName, newValue));

        if (collection.updateOne(query, update).getModifiedCount() > 0) {
            System.out.println("Das Feld " + fieldName + " für die Bestellung " + bestellName + " wurde erfolgreich aktualisiert.");
        } else {
            System.out.println("Es wurde keine Bestellung mit der Nummer " + bestellName + " gefunden.");
        }
    }

    private void editAllFields() {
        System.out.println("Geben Sie den neuen Wert für jedes Feld ein:");

        System.out.println("Geben Sie die Bestellnummer der Bestellung ein, die Sie bearbeiten möchten:");
        String bestellNummer = scanner.nextLine();

        Document query = new Document("Bestellnummer", bestellNummer);
        Document update = new Document();

        System.out.println("Bestelldatum:");
        update.append("$set", new Document("Bestelldatum", scanner.nextLine()));

        System.out.println("Kunde:");
        update.append("$set", new Document("Kunde", scanner.nextLine()));

        System.out.println("Bestelloptionen:");
        update.append("$set", new Document("Bestelloptionen", scanner.nextLine()));

        System.out.println("Total:");
        update.append("$set", new Document("Total", scanner.nextLine()));

        if (collection.updateOne(query, update).getModifiedCount() > 0) {
            System.out.println("Alle Felder für die Bestellung " + bestellNummer + " wurden erfolgreich aktualisiert.");
        } else {
            System.out.println("Es wurde keine Bestellung mit der Nummer " + bestellNummer + " gefunden.");
        }
    }

    private void createNewOrder() {
        System.out.println("Neue Bestellung erstellen:");

        System.out.println("Geben Sie die Bestellnummer ein:");
        String bestellNummer = scanner.nextLine();

        System.out.println("Geben Sie das Bestelldatum ein:");
        String bestelldatum = scanner.nextLine();

        System.out.println("Geben Sie den Kunden ein:");
        String kunde = scanner.nextLine();

        System.out.println("Geben Sie die Bestelloptionen ein:");
        String bestelloptionen = scanner.nextLine();

        System.out.println("Geben Sie das Total ein:");
        String total = scanner.nextLine();

        // Neue Bestellung in der Datenbank erstellen
        Document newOrder = new Document("Bestellnummer", bestellNummer)
                .append("Bestelldatum", bestelldatum)
                .append("Kunde", kunde)
                .append("Bestelloptionen", bestelloptionen)
                .append("Total", total);

        collection.insertOne(newOrder);

        System.out.println("Neue Bestellung wurde erfolgreich erstellt.");
    }

    private void deleteOrder() {
        System.out.println("Bestellung löschen:");

        System.out.println("Geben Sie die Bestellnummer der Bestellung ein:");
        String bestellNummer = scanner.nextLine();

        // Prüfen, ob die Bestellung existiert
        Document query = new Document("Bestellnummer", bestellNummer);
        FindIterable<Document> result = collection.find(query);

        if (result.iterator().hasNext()) {
            // Bestellung gefunden, löschen
            collection.deleteOne(query);
            System.out.println("Bestellung " + bestellNummer + " wurde erfolgreich gelöscht.");
        } else {
            // Bestellung nicht gefunden
            System.out.println("Bestellung nicht gefunden. Bitte versuchen Sie es erneut.");
        }

    }
}
