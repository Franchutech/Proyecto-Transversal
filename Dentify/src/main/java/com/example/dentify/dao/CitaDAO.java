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

                // Convierto el estado al Enum
                int idEstadoBBDD = rs.getInt("id_estado");
                Estado estadoEnum = Estado.values()[idEstadoBBDD];

                // Creo la Cita usando objeto estado
                Cita cita = new Cita(
                    rs.getInt("id_cita"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTimestamp("hora").toLocalDateTime(),
                    rs.getString("motivo"),
                    estadoEnum, // Paso objeto ENUM
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

    //METODOS DE VALIDACION

    // MÉTODO PARA VERIFICAR SI EL DOCTOR O EL PACIENTE YA TIENEN UNA CITA A ESA HORA
    public boolean estaOcupado(int idDoctor, int idPaciente, LocalDateTime hora) {
        // Buscamos si existe alguna cita a esa misma fecha y hora para ese doctor O ese paciente
        String sql = "SELECT COUNT(*) FROM cita WHERE (id_doctor = ? OR id_paciente = ?) AND hora = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idDoctor);
            pstmt.setInt(2, idPaciente);
            pstmt.setTimestamp(3, Timestamp.valueOf(hora));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Si el conteo es mayor a 0, significa que alguno de los dos ya tiene una cita
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar ocupación en el DAO: " + e.getMessage());
        }
        return true; // Por seguridad, si falla la consulta, asumimos que está ocupado para no duplicar

    }//CIERRE METODO ESTAOCUPADO

    // MÉTODO PARA INSERTAR UNA CITA VALIDANDO TODO

    public boolean insertarCita(Cita cita) {
        // 1. Valido todo lo de la clase Cita
        if (!cita.esValida()) {
            System.err.println("Error: Los datos de la cita no son válidos o están fuera de horario.");
            return false;
        }

        // 2. Valido que ni el doctor ni el paciente tengan otra cita a esa hora
        if (estaOcupado(cita.getIdDoctor(), cita.getIdPaciente(), cita.getHora())) {
            System.err.println("Error: El doctor o el paciente ya tienen una cita programada a esa hora.");
            return false;
        }

        // 3. Si todo está limpio, puedo insertar en la base de datos
        String sql = "INSERT INTO cita (fecha, hora, motivo, id_estado, id_paciente, id_doctor, id_tratamiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(cita.getFecha()));
            pstmt.setTimestamp(2, Timestamp.valueOf(cita.getHora()));
            pstmt.setString(3, cita.getMotivo());

            // Si nuestro Enum Estado está inicializado, guardo su posición numérica
            pstmt.setInt(4, cita.getEstado() != null ? cita.getEstado().ordinal() : 0);

            pstmt.setInt(5, cita.getIdPaciente());
            pstmt.setInt(6, cita.getIdDoctor());
            pstmt.setInt(7, cita.getIdTratamiento());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar la cita en la base de datos: " + e.getMessage());
            return false;
        }
    } //CIERRE METODO VALIDACION INSERTAR CITA

}//CIERRE CITA DAO