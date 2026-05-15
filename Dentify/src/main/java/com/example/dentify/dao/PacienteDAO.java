package com.example.dentify.dao;

import com.example.dentify.Configuration.SQLDataBaseManager;
import com.example.dentify.Model.Paciente;
import com.mysql.cj.xdevapi.PreparableStatement;
import com.mysql.cj.xdevapi.Result;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
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
                int id_paciente = resultSets.getInt(1);
                String nombre = resultSets.getNString(2);
                String apellido = resultSets.getNString(3);
                String telefono = resultSets.getNString(4);
                String correo_electronico = resultSets.getNString(5);
                LocalDate fecha_nacimiento = resultSets.getObject(6, LocalDate.class);

                // Usamos el constructor que creamos antes
                Paciente p = new Paciente(id_paciente, nombre, apellido, telefono, correo_electronico, fecha_nacimiento);
                pacientes.add(p);

            }
        }catch (SQLException e) {
            System.err.println("Error en el filtro " + e.getMessage());
        }
        return pacientes;
    }

    //METODO PARA AÑADIR UN NUEVO PACIENTE
    public static boolean createPaciente(Paciente p){

        boolean result = false;

        String sqlInsertPacientes = "INSERT INTO paciente (nombre, apellido, telefono, correoElectronico, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection connection = SQLDataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlInsertPacientes)){

            statement.setNString(2, p.getNombre());
            statement.setNString(3, p.getApellido());
            statement.setNString(4, p.getTelefono());
            statement.setNString(5, p.getCorreoElectronico());
            if(p.getFechaNacimiento()!=null){
                statement.setDate(5, java.sql.Date.valueOf(p.getFechaNacimiento()));
            }else {
                statement.setNull(5, java.sql.Types.DATE);
            }

            statement.execute();
            result = true;

        }catch(SQLException e){
            System.err.println("Error en createPaciente " + e.getMessage());
        }
        return result;
    }

    //METODO PARA ELIMINAR UN PACIENTE POR SU ID
    public static boolean deletePaciente(int id_paciente){
        boolean result = false;
        String sqlDeletePacientes = "DELETE FROM paciente WHERE id_paciente = ?";

        try(Connection connection = SQLDataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlDeletePacientes)) {

            statement.setInt(1, id_paciente);
            statement.execute();
            result = true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar paciente " + e.getMessage());
        }
        return result;
    }

    //METODO PARA MOSTRAR TODOS LOS PACIENTES
    public static List<Paciente> getAllpacientes(){
        List<Paciente> pacientes = new LinkedList<>();

        // Consulta SQL para traer todas las filas de la tabla 'paciente'
        String sqlpacientes = "SELECT * FROM paciente";

        try(Connection connection = SQLDataBaseManager.getConnection();
            Statement stament = connection.createStatement();
            ResultSet resultSets = stament.executeQuery(sqlpacientes)) {

            while (resultSets.next()) {
                int id_paciente = resultSets.getInt(1);
                String nombre = resultSets.getNString(2);
                String apellido = resultSets.getNString(3);
                String telefono = resultSets.getNString(4);
                String correo_electronico = resultSets.getNString(5);
                LocalDate fecha_nacimiento = resultSets.getObject(6, LocalDate.class);

                Paciente p = new Paciente(id_paciente, nombre, apellido, telefono, correo_electronico, fecha_nacimiento);
                pacientes.add(p);
            }
        }catch (SQLException e) {
            System.err.println("Error al listar pacientes " + e.getMessage());
        }
        return pacientes;
    }




}
