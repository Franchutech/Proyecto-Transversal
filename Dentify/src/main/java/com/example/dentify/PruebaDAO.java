package com.example.dentify;

import com.example.dentify.dao.CitaDAO;
import com.example.dentify.Model.Cita;
import java.util.List;

public class PruebaDAO {
    public static void main(String[] args) {
        CitaDAO dao = new CitaDAO();
        List<Cita> citas = dao.obtenerTodas();

        System.out.println(" PRUEBA DE CONEXIÓN Y DAO ");
        if (citas.isEmpty()) {
            System.out.println(" No hay citas en la BBDD o la conexión falló.");
        } else {
            for (Cita c : citas) {
                System.out.println(c.toString());
            }
            System.out.println("¡Éxito! Los datos han llegado al modelo Cita.");
        }
    }
}