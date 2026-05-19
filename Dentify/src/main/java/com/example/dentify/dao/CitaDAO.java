package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Cita;
import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Paciente;
import com.example.dentify.Model.Estado; // Asegura este import para el Enum

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

    // MÉTODO PARA OBTENER TODAS LAS CITAS
    public List<Cita> obtenerTodas() {
        List<Cita> lista = new ArrayList<>();

        // SQL corregido con los nombres exactos de las columnas de Andrea (correo_electronico, fecha_nacimiento)
        String sql = "SELECT c.*, p.nombre AS nomP, p.apellido AS apeP, p.telefono, p.correo_electronico AS corrP, p.fecha_nacimiento AS fecP, " +
                     "d.nombre AS nomD, d.num_colegiado, d.especialidad, d.direccion, d.fecha_nacimiento AS fecD, " +
                     "det.id_tratamiento, det.observaciones " +
                     "FROM cita c " +
                     "JOIN paciente p ON c.id_paciente = p.id_paciente " +
                     "JOIN doctor d ON c.id_doctor = d.id_doctor " +
                     "LEFT JOIN detalle_cita det ON c.id_cita = det.id_cita";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Instancio el objeto Paciente con los datos del JOIN usando su orden de constructor
                Paciente paciente = new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nomP"),
                    rs.getString("apeP"),
                    rs.getString("telefono"),
                    rs.getString("corrP"),
                    rs.getDate("fecP").toLocalDate()
                );

                // Instancio el objeto Doctor
                Doctor doctor = new Doctor(
                    rs.getInt("id_doctor"),
                    rs.getString("nomD"),
                    rs.getDate("fecD").toLocalDate(),
                    rs.getString("direccion"),
                    rs.getString("num_colegiado"),
                    null
                );

                // CORRECCIÓN CRÍTICA: Convertimos el entero id_estado al Enum Estado
                int idEstadoBBDD = rs.getInt("id_estado");
                Estado estadoEnum = Estado.values()[idEstadoBBDD];

                // Creo la Cita usando el objeto Estado en vez del int
                Cita cita = new Cita(
                    rs.getInt("id_cita"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTimestamp("hora").toLocalDateTime(),
                    rs.getString("motivo"),
                    estadoEnum, // Pasamos el objeto Enum real
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