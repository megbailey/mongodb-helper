package database;

import java.util.*;

import org.bson.*;

//import com.mongodb.client.model.Filters;
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase; 
import com.mongodb.client.MongoCollection;


public class MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private String host;
    private String name;
    private int port;
    private MongoCredential credential;

    /*
    * =================================================================
    * Public MongoDB Constructors
    *
    * =================================================================
    */

    public MongoDB() {
        this.host = null;
        this.port = 27017;
        this.name = null;
        this.mongoClient = null;
        this.database = null;
        this.credential = null;
    }

    public MongoDB(String host, int port) {
        this.host = host;
        this.port = port;
        this.name = null;
        this.mongoClient = null;
        this.database = null;
        this.credential = null;
    }

    public MongoDB(String host, int port, String name) {
        this.host = host;
        this.name = name;
        this.port = port;
        this.mongoClient = null;
        this.database = null;
        this.credential = null;
    }

    public MongoDB(String host, String name) {
        this.host = host;
        this.name = name;
        this.port = 27017;
        this.mongoClient = null;
        this.database = null;
        this.credential = null;
    }

    public MongoDB(String host, int port, String name, String username, String password) {
        this.host = host;
        this.name = name;
        this.port = port;
        this.mongoClient = null;
        this.database = null;
        this.credential = MongoCredential.createCredential(username, this.name, password.toCharArray()); 
    }

    /*
    * =================================================================
    * Public MongoDB Helper Functions
    *
    * =================================================================
    */

    public void client() throws ClientNotAvailableException {
        try {
            this.mongoClient =  new MongoClient(this.host , this.port);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientNotAvailableException("Database not found or credentials incorrect");
        } 
    }

    public void connect() throws DatabaseNotFoundException {
        try {
            if (this.mongoClient == null) {
                client();
            }
            this.database = this.mongoClient.getDatabase(this.name);
            System.out.println("Connected to the database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found or credentials incorrect");
        } 
    }

    public void close() throws ClientNotAvailableException {
        try {
            mongoClient.close();
            System.out.println("Client closed successfully"); 
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientNotAvailableException("Database not found");
        }
    }

    public void createCollection(String name) throws DatabaseNotFoundException {
        try {
            this.database.createCollection(name); 
            System.out.println("Collection created successfully"); 
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found");
        }
    }

    public MongoCollection<Document> getCollection(String name) throws DatabaseNotFoundException {
        try {
            MongoCollection<Document> collection = database.getCollection(name);
            return collection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found");
        }
    }

    public void listCollections() throws DatabaseNotFoundException {
        try {
            for (String name : database.listCollectionNames()) 
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found");
        }
    }

    public Document createDocument(String json) throws DatabaseNotFoundException {
        try {
        Document doc = Document.parse(json);
        return doc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found");
        }
    }

    public void addDocument(Document doc) throws DatabaseNotFoundException {
        try {
        MongoCollection<Document> collection = database.getCollection(this.name); 
        collection.insertOne(doc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found");
        }
    }

    public void addManyDocuments(List<Document> documents) throws DatabaseNotFoundException {
        try {
        MongoCollection<Document> collection = database.getCollection(this.name); 
        collection.insertMany(documents);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found");
        }
    }
/*
    public void deleteDocument(String field, String value) throws DatabaseNotFoundException {
        MongoCollection<Document> collection = database.getCollection(this.name);
        collection.deleteOne(eq(field, value));
    }
*/
    

   
    /*
    * =================================================================
    * Public MongoDB Setter Functions
    *
    * =================================================================
    */

    public void setHost(String newHost) {
        this.mongoClient = null;
        this.database = null;
        this.host = newHost;
    }
    public void setName(String newName) {
        this.mongoClient = null;
        this.database = null;
        this.name = newName;
    }
    public void setPort(int newPort) {
        this.mongoClient = null;
        this.database = null;
        this.port = newPort;
    }
    public void setCredentials(String username, String password) {
        this.credential = MongoCredential.createCredential(username, this.name, password.toCharArray()); 
    }
}

/*
* =================================================================
* MongoDB Exceptions
*
* =================================================================
*/

class DatabaseNotFoundException extends Exception {
      public DatabaseNotFoundException() {}
      public DatabaseNotFoundException(String message) {
        super(message);
      }
}
class ClientNotAvailableException extends Exception {
    public ClientNotAvailableException() {}
      public ClientNotAvailableException(String message) {
        super(message);
      }
}
