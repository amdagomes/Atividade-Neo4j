package com.ifpb.neo4j.connection;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class DriverFactory {
    
    private Driver driver;

    public DriverFactory() {
        String url = "bolt://localhost:7687";
        String usuario = "neo4j";
        String senha = "123";
        driver = GraphDatabase.driver(url, AuthTokens.basic(usuario, senha));
    }
    
    public Driver getDriver(){
        return driver;
    }
    
}
