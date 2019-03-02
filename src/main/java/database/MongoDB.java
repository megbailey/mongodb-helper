package database;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
    * Public MongoDB Helper functions
    *
    * =================================================================
    */

    public void connect() throws DatabaseNotFoundException {
        if (this.name != null) {
            this.mongoClient =  new MongoClient( "localhost" , 27017 );
            this.database = mongoClient.getDatabase(this.name);
            System.out.println("Connected to the database successfully");
        } else {
            throw new DatabaseNotFoundException("Database not found");
        } 
    }

    public void close() throws DatabaseNotFoundException {
        if (this.mongoClient != null) {

        } else {
            throw new DatabaseNotFoundException("Database not found");
        }
    }

   
    public void createCollection(String name) throws DatabaseNotFoundException {
        if (this.database != null) {
            this.database.createCollection(name); 
            System.out.println("Collection created successfully"); 
        } else {
            throw new DatabaseNotFoundException("Database not found");
        }
    }

    public MongoCollection<Document> getCollection(String name) throws DatabaseNotFoundException{
        if (this.database != null) {
            MongoCollection<Document> collection = database.getCollection(name);
            return collection;
        } else {
            throw new DatabaseNotFoundException("Database not found");
        }
    }

    public void addDocument(String json) throws DatabaseNotFoundException {
        MongoCollection<Document> collection = database.getCollection(this.name); 
        Document doc = Document.parse(json);
        List<Document> list = new ArrayList<>();
        list.add(doc);
    }
   

    public void setName(String newName) {
        this.name = newName;
    }
    public void setPort(String newPort) {
        this.name = newPort;
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
