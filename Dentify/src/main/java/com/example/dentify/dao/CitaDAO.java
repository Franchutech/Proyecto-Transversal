package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Cita;
import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    // ATRIBUTOS
    private Connection connection;

    // CONSTRUCTOR
    public CitaDAO() {
        this.connection = SQLDataBaseManager.getConnection();
    }

    // MÉTODO PARA OBTENER TODAS LAS CITAS (ACTUALIZADO CON OBJETOS)
    public List<Cita> obtenerTodas() {
        List<Cita> lista = new ArrayList<>();
        // Hago JOIN con paciente, doctor y detalle_cita para tener toda la info de golpe
        String sql = "SELECT c.*, p.nombre AS nomP, p.apellido AS apeP, p.telefono, p.correo, p.fecha_nacimiento AS fecP, " +
                     "d.nombre AS nomD, d.num_colegiado, d.especialidad, d.direccion, d.fecha_nacimiento AS fecD, " +
                     "det.id_tratamiento, det.observaciones " +
                     "FROM cita c " +
                     "JOIN paciente p ON c.id_paciente = p.id_paciente " +
                     "JOIN doctor d ON c.id_doctor = d.id_doctor " +
                     "LEFT JOIN detalle_cita det ON c.id_cita = det.id_cita";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Instancio el objeto Paciente con los datos del JOIN
                Paciente paciente = new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nomP"),
                    rs.getString("apeP"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getDate("fecP").toLocalDate()
                );

                // Instancio el objeto Doctor con los datos del JOIN
                // Nota: He puesto null en especialidad porque en tu modelo es un Enum y habría que parsearlo
                Doctor doctor = new Doctor(
                    rs.getInt("id_doctor"),
                    rs.getString("nomD"),
                    rs.getDate("fecD").toLocalDate(),
                    rs.getString("direccion"),
                    rs.getString("num_colegiado"),
                    null
                );

                // Creo la Cita usando los objetos y los datos de detalle que ahora están en Cita
                Cita cita = new Cita(
                    rs.getInt("id_cita"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTimestamp("hora").toLocalDateTime(),
                    rs.getString("motivo"),
                    rs.getInt("id_estado"),
                    paciente,
                    doctor,
                    rs.getInt("id_tratamiento"),
                    rs.getString("observaciones")
                );

                lista.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las citas: " + e.getMessage());
        }
        return lista;
    }
}