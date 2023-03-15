package com.clinicadental.dao.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatementGenerator {

    private static final Logger LOG = Logger.getLogger(StatementGenerator.class);

    private static List<String> columnNamesToQuestionMark(List<String> columnNames) {
        List<String> columnNamesWithQuestionMark = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            columnNamesWithQuestionMark.add("?");
        }
        return columnNamesWithQuestionMark;
    }

    private static List<String> columnNamesWhitQuestionMark(List<String> columnNames) {
        List<String> columnNamesWithQuestionMark = new ArrayList<>();
        for (String columnName : columnNames) {
            columnNamesWithQuestionMark.add(columnName + " = ?");
        }
        return columnNamesWithQuestionMark;
    }
    public static String getStatement(String statementType, String tableName, List<String> columnNames) {
        return switch (statementType) {
            case "guardar" -> "INSERT INTO " + tableName + " (" + String.join(", ", columnNames) + ") " +
                    "VALUES (" + String.join(", ", columnNamesToQuestionMark(columnNames)) + ")";
            case "selectWhereId" -> "SELECT * FROM " + tableName + " WHERE id = ?";
            case "selectAll" -> "SELECT * FROM " + tableName;
            case "update" -> "UPDATE " + tableName + " SET " +
                    String.join(", ", columnNamesWhitQuestionMark(columnNames)) +
                    " WHERE id = ?";
            case "eliminar" -> "DELETE FROM " + tableName + " WHERE id = ?";
            default -> null;
        };
    }

    public static String getStatement(String statementType) {
        if (Objects.equals(statementType, "createDB")) {
            return  "DROP TABLE IF EXISTS pacientes;" +
                    "create table pacientes(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "apellido varchar(255)," +
                    "nombre varchar (255)," +
                    "documento varchar (255)," +
                    "fechaIngreso TIMESTAMP without time zone," +
                    "domicilioId int);" +

                    "DROP TABLE IF EXISTS domicilios;" +
                    "create table domicilios(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "calle varchar(255)," +
                    "numero varchar (255)," +
                    "localidad varchar (255)," +
                    "provincia varchar (255));" +

                    "DROP TABLE IF EXISTS odontologos;" +
                    "CREATE TABLE odontologos (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "nombre VARCHAR(255)," +
                    "apellido VARCHAR(255)," +
                    "matricula VARCHAR(255)); " +


                    // DOCTOR'S DATE
                    " INSERT INTO DOMICILIOS(calle, numero, localidad, provincia)" +
                    " VALUES('JARAMILLO','85','LA RIOJA','LA RIOJA'), ('LAVALLE','3015','CABA','BS AS');" +
                    " INSERT INTO PACIENTES (nombre, apellido, cedula, fechaIngreso, domicilioId)" +
                    " VALUES('DIEGO','MARADONA  ','123','2023-02-13','1'),('LEO', 'MESSI','456', '2021-10-22','2')" +
                    " INSERT INTO odontologos (nombre, apellido, matricula)" +
                    " VALUES('JOSE','DE SAN MARTIN','1357'),('JUAN FELIPE','IBARRA','1113')";
        }
        return null;
    }
}