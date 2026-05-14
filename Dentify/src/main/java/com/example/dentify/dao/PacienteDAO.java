package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PacienteDAO {

    //CREAR, LEER, ACTUALIZAR, BORRAR
    //getAllPacientes():Lo necesitas para cargar la lista completa en cuanto se abre la ventana.
    //getPacientesByFilter(String texto): Usará una sentencia SQL con varios OR y el operador LIKE para buscar en las tres columnas a la vez.


    //METODO PARA BUSCAR PACIENTE POR NOMBRE, APELLIDO O TELÉFONO
    public static List <Paciente> getpacientesByNameSurnamePhone(String texto){
        List <Paciente> pacientes = new ArrayList<>();

        String sqlpacientes = "SELECT * FROM pacientes WHERE nombre LIKE ? OR apellido LIKE ? OR telefono LIKE ? ";

        try(Connection connection = SQLDataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlpacientes)) {

            String filtro = "%" + texto + "%";
            statement.setString(1, filtro);
            statement.setString(2, filtro);
            statement.setString(3, filtro);

            ResultSet resultSets = statement.executeQuery();

            while (resultSets.next()) {
                int idPaciente = resultSets.getInt(1);
                String nombre = resultSets.getNString(2);
                String apellido = resultSets.getNString(3);
                String telefono = resultSets.getNString(4);
                String correoElectronico = resultSets.getNString(5);
                LocalDate fechaNacimiento = resultSets.getObject(6, LocalDate.class);

                // Usamos el constructor que creamos antes
                Paciente p = new Paciente(idPaciente, nombre, apellido, telefono, correoElectronico, fechaNacimiento);
                pacientes.add(p);

            }
        }catch (SQLException e) {
            System.err.println("Error en el filtro " + e.getMessage());
        }
        return pacientes;
    }

    //METODO PARA AÑADIR UN NUEVO PACIENTE
    public static int insertPacientes(Paciente pacientes){

        int response = -1;

        String sqlStatement = "INSERT INTO pacientes (idPaciente, nombre, apellido, telefono, correoElectronico, fechaNacimiento) + " VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = SQLDataBaseManager.getConnection();
        PreparedStatement sqlStatement = connection.prepareStatement(sqlStatement)){

            statement.setInt(1, pacientes.getIdPaciente());
            statement.setNString(2, pacientes.getNombre());
            statement.setNString(3, pacientes.getApellido());
            statement.setNString(4, pacientes.getTelefono());
            statement.setNString(5, pacientes.getCorreoElectronico());
            statement.setObject(6, pacientes.getFechaNacimiento());

            response = statement.executeUpdate();
        }catch(SQLException e)
    }




}
