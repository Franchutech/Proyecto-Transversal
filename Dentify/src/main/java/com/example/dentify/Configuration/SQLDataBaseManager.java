package com.example.dentify.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDataBaseManager {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String SCHEMA = "sistema_medico";
    private static final String USUARIO = "root";
    private static final String CLAVE = "daw12";

    private static Connection connection = null;

    private SQLDataBaseManager() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL + SCHEMA, USUARIO, CLAVE);
                System.out.println("🦷🦷🦷 ¡Encías sanas y conexión abierta!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("🦷 Error parece que el acceso al driver tiene una Caries: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println(" 🦷 Error parece que el acceso de SQL tiene una Caries: " + e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 [Database] Conexión cerrada: ¡Dientes limpios y servidor feliz!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al hacer la limpieza (cerrar conexión): " + e.getMessage());
        }
    }
}
