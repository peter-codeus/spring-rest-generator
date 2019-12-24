/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator.Entities;

/**
 *
 * @author PC
 */
public class DatabaseConnection {
    String host, username, password, dbName;
    Integer port;

    public DatabaseConnection() {
    }

    
    public DatabaseConnection(String host, String username, String password,String dbName, Integer port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.dbName = dbName;
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
