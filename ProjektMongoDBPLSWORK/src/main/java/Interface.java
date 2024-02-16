import com.mongodb.client.MongoDatabase;

import java.util.Scanner;

public class Interface {
    private Scanner scanner;

    public Interface() {
        scanner = new Scanner(System.in);
    }

    public void displayMenu(MongoDatabase database) {
        while (true) {
            System.out.println("Willkommen!");
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Kunde");
            System.out.println("2. Computer");
            System.out.println("3. Bestellungen");
            System.out.println("4. Leave");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Kunde kunde = new Kunde(database);
                    kunde.displaySubMenu("Kunde");
                    break;
                case 2:
                    Computer computer = new Computer(database);
                    computer.displaySubMenu("Computer");
                    break;
                case 3:
                    Bestellung bestellung = new Bestellung(database);
                    bestellung.displaySubMenu("Bestellungen");
                    break;
                case 4:
                    System.out.println("Auf Wiedersehen!");
                    return; // Programm beenden
                default:
                    System.out.println("Ungültige Auswahl!");
            }
        }
    }

}
