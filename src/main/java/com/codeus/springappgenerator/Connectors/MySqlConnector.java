/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator.Connectors;

import com.codeus.springappgenerator.Entities.*;
import com.codeus.springappgenerator.Service.DatabaseConnector;
import com.codeus.springappgenerator.Util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author PC
 */
public class MySqlConnector implements DatabaseConnector {

    private Connection connection;
    private DatabaseMetaData md;
    private List<DatabaseTable> databaseTables = new ArrayList<>();

    public MySqlConnector() {
        connection = null;
    }

    public MySqlConnector(DatabaseConnection dbConnection) throws Exception {
        init(dbConnection);
    }

    @Override
    public void init(DatabaseConnection dbConnection) throws Exception {
        String connectionString = "jdbc:mysql://" + dbConnection.getHost() + ":" + dbConnection.getPort() + "/" + dbConnection.getDbName();
        connection = DriverManager.getConnection(connectionString, dbConnection.getUsername(), dbConnection.getPassword());
    }

    @Override
    public List<DatabaseTable> findTables() {
        String tableName, table;
        DatabaseColumn dbColumn;
        DatabaseTable databaseTable;
        List<DatabaseColumn> columns;

        try {
            md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);

            while (rs.next()) {
                tableName = rs.getString(3);
                table = rs.getString(3);
                
//                tableName = Util.transformCase(tableName, Case.CAMEL);
                databaseTable = new DatabaseTable(tableName);

                //find column values here
                ResultSet columnRs = md.getColumns(null, null, table, "%");

                ResultSet primaryKeysRs = md.getPrimaryKeys(null, null, table);
                ResultSet importedKeysRs = md.getImportedKeys(null, null, table);
                ResultSet indexInfoRs = md.getIndexInfo(null, null, table, true, true);

                columns = new ArrayList<>();

                while (columnRs.next()) {
                    dbColumn = new DatabaseColumn();
                    dbColumn.setName(columnRs.getString("COLUMN_NAME"));
                    dbColumn.setDataType(convertDbTypeToJava(columnRs.getString("TYPE_NAME")));
                    dbColumn.setNullable(columnRs.getBoolean("NULLABLE"));
                    dbColumn.setDefaultValue(columnRs.getString("COLUMN_DEF"));
                    dbColumn.setAutoIncrement(isAutoIncrementCheck(columnRs.getString("IS_AUTOINCREMENT")));

                    //check primary key
                    while (primaryKeysRs.next()) {
                        
                        if (Util.transformCase(primaryKeysRs.getString("COLUMN_NAME"), Case.CAMEL).equals(dbColumn.getName())) {
                            dbColumn.setKey(DatabaseKey.PRIMARY);
                            break;
                        }
                    }

                    columns.add(dbColumn);

                    //Displaying Detail Column Information
//                    for (int i = 1; i <= columnRs.getMetaData().getColumnCount(); i++) {
//                        System.out.println(columnRs.getMetaData().getColumnLabel(i) + ": " + columnRs.getString(i));
//                        System.out.println("");
//                    }
//                    System.out.println("\n\n");
                }

//              //check unique
                while (indexInfoRs.next()) {
                    for (DatabaseColumn c : columns) {
                        if (c.getKey() != DatabaseKey.PRIMARY) {
                            if (Util.transformCase(indexInfoRs.getString("COLUMN_NAME"), Case.CAMEL).equals(c.getName())) {
                                c.setKey(DatabaseKey.UNIQUE);
                                break;
                            }
                        }
                    }
                }

//              check foreign
                while (importedKeysRs.next()) {
                    for (DatabaseColumn c : columns) {
                        if (Util.transformCase(importedKeysRs.getString("FKCOLUMN_NAME"), Case.CAMEL).equals(c.getName())) {
                            c.setKey(DatabaseKey.FOREIGN);
                            break;
                        }
                    }
                }

                //Attribute Transformation to Attribute case
//                for (int i = 0; i < columns.size(); i++) {
//                    columns.get(i).setName(transformCase(columns.get(i).getName(), Case.ATTRIBUTE));
//                }

                databaseTable.setColumns(columns);
                databaseTables.add(databaseTable);

//                Display Tables
//                System.out.println(databaseTable);
            }

            return databaseTables;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String convertDbTypeToJava(String dbType) {
        switch (dbType) {
            case "VARCHAR":
                return "String";
            case "INT":
                return "Integer";
            case "DOUBLE":
                return "Double";
            case "BIT":
                return "Boolean";
            case "BIGINT":
                return "BigInteger";
            case "BLOB":
                return "Byte[]";
            case "LONGBLOB":
                return "Byte[]";
            default:
                return dbType;
        }
    }

    public Boolean isAutoIncrementCheck(String dbValue) {
        return (dbValue.equalsIgnoreCase("YES"));
    }

    @Override
    public Boolean tableExist(DatabaseTable dbTable) {
        if (dbTable.getName() == null || dbTable.getName().trim().isEmpty()) {
            return false;
        }

        Iterator<DatabaseTable> dbTableIterator = databaseTables.iterator();

        while (dbTableIterator.hasNext()) {
            if (dbTableIterator.next().getName().equals(dbTable.getName().trim())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void disconnect() {
        try {
            connection.close();
            System.out.println("Database Disconnected ...");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
