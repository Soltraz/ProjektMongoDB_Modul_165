import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Arrays;


public class Main {
    public static void main(String[] args){
        // MongoDB-Verbindung herstellen
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(uri);

        // Datenbank ProjektDB auswählen oder erstellen
        MongoDatabase database = mongoClient.getDatabase("ProjektDB");

        // Collections erstellen (falls nicht vorhanden)
        MongoUtils.createCollection(database, "Kunde");
        MongoUtils.createCollection(database, "Bestellungen");
        MongoUtils.createCollection(database, "Computer");


        // Interface anzeigen
        Interface myInterface = new Interface();
        myInterface.displayMenu(database);

        // Verbindung schließen
        mongoClient.close();
    }

}
