package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Cita;
import com.example.dentify.Model.DetalleCita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    private Connection connection;

    public CitaDAO() {
        this.connection = SQLDataBaseManager.getConnection();
    }


    public boolean insertarCita(Cita cita) {
        String sql = "INSERT INTO cita (fecha, hora, motivo, id_estado, id_paciente, id_doctor) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(cita.getFecha()));
            pstmt.setTimestamp(2, Timestamp.valueOf(cita.getHora()));
            pstmt.setString(3, cita.getMotivo());
            pstmt.setInt(4, cita.getIdEstado());
            pstmt.setInt(5, cita.getIdPaciente());
            pstmt.setInt(6, cita.getIdDoctor());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cita: " + e.getMessage());
            return false;
        }
    }

    public List<Cita> obtenerTodas() {
    List<Cita> lista = new ArrayList<>();
    // IMPORTANTE: El SQL debe pedir datos de AMBAS tablas
    String sql = "SELECT c.*, d.id_detalle, d.id_tratamiento, d.observaciones " +
                 "FROM CITA c " +
                 "LEFT JOIN DETALLE_CITA d ON c.id_cita = d.id_cita";

    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Cita cita = new Cita(
                rs.getInt("id_cita"),
                rs.getDate("fecha").toLocalDate(),
                rs.getTimestamp("hora").toLocalDateTime(),
                rs.getString("motivo"),
                rs.getInt("id_estado"),
                rs.getInt("id_paciente"),
                rs.getInt("id_doctor")
            );

            // Rescatamos el ID del detalle para ver si existe
            int idDetalle = rs.getInt("id_detalle");

            if (idDetalle > 0) {
                // Creamos el objeto detalle y lo metemos DENTRO de la cita
                DetalleCita detalle = new DetalleCita(
                    idDetalle,
                    rs.getInt("id_cita"),
                    rs.getInt("id_tratamiento"),
                    rs.getString("observaciones")
                );
                cita.setDetalle(detalle); // <--- ESTA ES LA CLAVE
            }
            lista.add(cita);
        }
    } catch (SQLException e) {
        System.err.println("Error en el DAO: " + e.getMessage());
    }
    return lista;
}

    public boolean actualizarCita(Cita cita) {
        String sql = "UPDATE cita SET fecha = ?, hora = ?, motivo = ?, id_estado = ? WHERE id_cita = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(cita.getFecha()));
            pstmt.setTimestamp(2, Timestamp.valueOf(cita.getHora()));
            pstmt.setString(3, cita.getMotivo());
            pstmt.setInt(4, cita.getIdEstado());
            pstmt.setInt(5, cita.getIdCita());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCita(int idCita) {
        String sql = "DELETE FROM cita WHERE id_cita = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idCita);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }

    }

    public Cita obtenerPorId(int id) {
    String sql = "SELECT * FROM citas WHERE id_cita = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new Cita(
                    rs.getInt("id_cita"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTimestamp("hora").toLocalDateTime(),
                    rs.getString("motivo"),
                    rs.getInt("id_estado"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_doctor")
                );
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar cita por ID: " + e.getMessage());
    }
    return null;
} //OBTENER POR ID


    //UNIDICAR CON DETALLE CITAS ALGUNAS CONSULTAS

    //VER EL HISTORICO DE UN PACIENTE ESPECIFICO EN ORDEN CRONOLOGICO

    public List<Cita> obtenerPorPaciente(int idPaciente) {
    List<Cita> lista = new ArrayList<>();
    String sql = "SELECT * FROM cita WHERE id_paciente = ? ORDER BY fecha DESC";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, idPaciente);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cita(
                    rs.getInt("id_cita"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTimestamp("hora").toLocalDateTime(),
                    rs.getString("motivo"),
                    rs.getInt("id_estado"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_doctor")
                ));
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al filtrar por paciente: " + e.getMessage());
    }
    return lista;
}

}//CIERRE CITA DAO