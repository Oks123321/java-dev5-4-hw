package com.goit.javacore5.feature.storage;

import com.goit.javacore5.feature.prefs.Prefs;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitService {

    public void initDb(String connectionUrl) {
//String connectionUrl = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);
        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway
                .configure()
                .dataSource(connectionUrl, null, null)
                .load();

        // Start the migration
        flyway.migrate();


    }
}



