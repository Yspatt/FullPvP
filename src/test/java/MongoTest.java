import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class MongoTest {

    public static void main(String[] args) {
        String CONNECTION_URL = "mongodb+srv://root:CBqajz4uagD1pYXN@cluster0.mzsd7.gcp.mongodb.net/FullPvP?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(CONNECTION_URL);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("FULLPVP_DATA");
        MongoCollection<Document> collection = mongoDatabase.getCollection("Users");

        String user = "Null gay";

        Document user_data = new Document("NAME",user);
        Document found = (Document) collection.find(user_data).first();
        if (found == null){
            user_data.append("SURNAME","Doe");
            user_data.append("AGE",19);
            user_data.append("COUNTRY","Brazil");
            collection.insertOne(user_data);
            System.out.println("Criado novo documento!");
        }else{
            String name = found.getString("NAME");
            String surname = found.getString("SURNAME");
            int age = found.getInteger("AGE");
            String country = found.getString("COUNTRY");
            System.out.println("Usuario encontrado! " + name + " " + surname + ", " + age + " anos de idade, mora em '" + country + "'");
        }
    }
}
