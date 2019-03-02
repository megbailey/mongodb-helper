package database;

//import com.mongodb.*;
import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;

public class MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private String host;
    private String name;
    private int port;
    private MongoCredential credential;

    /*
    *  public constructors
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

    public void connect() throws DatabaseNotFoundException {
        if (this.name != null) {
            this.mongoClient =  new MongoClient( "localhost" , 27017 );
            MongoDatabase database = mongoClient.getDatabase(this.name);
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

    /*
    public ListDatabasesIterable getDatabases() throws DatabaseNotFoundException {
        if (this.mongoClient != null) {
            return this.mongoClient.listDatabases();
        } else {
            throw new DatabaseNotFoundException("Database not found");
        }
        
    }

    public void getCollections() throws DatabaseNotFoundException {
        if (this.database != null) {
            this.database.getCollectionNames().forEach(System.out::println);
        } else {
            throw new DatabaseNotFoundException("Database not found");
        }
    }
    */
    
    public void setName(String newName) {
        this.name = newName;
    }
    public void setPort(String newPort) {
        this.name = newPort;
    }
}


class DatabaseNotFoundException extends Exception {
      public DatabaseNotFoundException() {}
      public DatabaseNotFoundException(String message) {
        super(message);
      }
}
