package com.clinicadental.dao.impl;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class ConfigJDBC {
    private static final Logger LOG = Logger.getLogger(ConfigJDBC.class);

    public static void createDB() {
        try {
            Connection conexion = getConnection();
            assert conexion != null;
            PreparedStatement psCrearDB = getPreparedStatement(conexion,
                    StatementGenerator.getStatement("createDB"));
            assert psCrearDB != null;
            psCrearDB.execute();
            psCrearDB.close();

            LOG.info("Created Database");
        }
        catch (SQLException | ClassNotFoundException e){
            LOG.error("Failed to Created Database", e);
            System.out.println(e.getMessage());
        }
    }

//    public static Connection getConnection() throws SQLException, ClassNotFoundException {
//        try {
//            String DB_JBC_DRIVER = "org.h2.Driver";
//            Class.forName(DB_JBC_DRIVER);
//
//            String DB_URL = "jdbc:h2:~/db_clinicaOdontologica";
//            String DB_USER = "sa";
//            String DB_PASSWORD = "";
//            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//        }
//        catch (SQLException | ClassNotFoundException e){
//            LOG.error("Failed to Get Conection", e);
//        }
//        return null;
//    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        try {
            String DB_JBC_DRIVER = "org.h2.Driver";
            Class.forName(DB_JBC_DRIVER);

            String DB_URL = "jdbc:h2:~/db_clinicadental;INIT=RUNSCRIPT FROM 'src/main/resources/create.sql'";
            String DB_USER = "sa";
            String DB_PASSWORD = "";
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        catch (SQLException | ClassNotFoundException e){
            LOG.error("Failed to Get Conection", e);
        }
        return null;
    }

    public static PreparedStatement getPreparedStatement(Connection conexion, String sql) throws SQLException {
        try {
            return conexion.prepareStatement(sql);
        }
        catch (SQLException e) {
            LOG.error("Failed to get the PreparedStatement A", e);
        }
        return null;
    }

    public static PreparedStatement getPreparedStatement(Connection conexion, String statement, int returnGeneratedKeys) {
        try {
            return conexion.prepareStatement(statement, returnGeneratedKeys);
        }
        catch (SQLException e) {
            LOG.error("Failed to get the PreparedStatement B", e);
        }
        return null;
    }

    public static void closeConnection() {
        try {
            Objects.requireNonNull(getConnection()).close();
            LOG.debug("Close Connection successfully");
        }
        catch (SQLException | ClassNotFoundException e) {
            LOG.error("Failed to close connection", e);
        }
    }

}