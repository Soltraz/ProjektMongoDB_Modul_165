import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoUtils {
    public static void createCollection(MongoDatabase database, String collectionName) {
        if (!collectionExists(database, collectionName)) {
            database.createCollection(collectionName);
            System.out.println("Collection '" + collectionName + "' erstellt.");
        } else {
            System.out.println("Collection '" + collectionName + "' existiert bereits.");
        }
    }

    private static boolean collectionExists(MongoDatabase database, String collectionName) {
        for (String name : database.listCollectionNames()) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }
}
