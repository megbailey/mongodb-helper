package database;

import org.bson.Document; 

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
        this.port = 0;
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

    public void connect() throws DatabaseNotFoundException {
        try {
            this.mongoClient =  new MongoClient(this.host , this.port);
            this.database = mongoClient.getDatabase(this.name);
            System.out.println("Connected to the database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found or credentials incorrect");
        } 
    }

    public void close() throws DatabaseNotFoundException {
        try {
            mongoClient.close();
            System.out.println("Client closed successfully"); 
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseNotFoundException("Database not found");
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

    public void addDocument(String json) throws DatabaseNotFoundException {
        MongoCollection<Document> collection = database.getCollection(this.name); 
        Document doc = Document.parse(json);
        collection.insertOne(doc);
    }
   
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
