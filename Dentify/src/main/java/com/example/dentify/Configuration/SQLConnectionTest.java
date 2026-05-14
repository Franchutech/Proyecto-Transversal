package com.example.dentify.Configuration;

import java.sql.Connection;

public class SQLConnectionTest {
    public static void main(String[] args) {
        System.out.println("--- TEST AISLADO DEL SINGLETON ---");

        // Primera llamada
        Connection c1 = SQLDataBaseManager.getConnection();
        // Segunda llamada
        Connection c2 = SQLDataBaseManager.getConnection();

        if (c1 != null && c1 == c2) {
            System.out.println("OK: El Singleton devuelve la misma conexión.");
            System.out.println("Instancia 1: " + c1);
            System.out.println("Instancia 2: " + c2);
        } else {
            System.out.println("ERROR: Las conexiones son distintas o nulas.");
        }
    }
}