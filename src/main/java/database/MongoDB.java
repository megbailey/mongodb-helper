package database;

import com.mongodb.*;

public class MongoDB {
    private MongoClient mongoClient;
    private DB database;
    private String host;
    private String name;
    private int port;

    /*
    *  public constructors
    */
    public MongoDB(String host, int port) {
        this.host = host;
        this.port = port;
        this.name = null;
        this.mongoClient = null;
        this.database = null;
    }

    public MongoDB(String host, int port, String name) {
        this.host = host;
        this.name = name;
        this.port = port;
        this.mongoClient = null;
        this.database = null;
    }

    public void connect() throws DatabaseNotFoundException {
        if (this.name != null) {
            this.mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:" + this.port));
            DB database = mongoClient.getDB(this.name);
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

    public void auth(DB database, String username, String password) {
        boolean auth = database.authenticate(username, password.toCharArray());
    }

    public void getDatabases() {
        this.mongoClient.getDatabaseNames().forEach(System.out::println);
    }

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
