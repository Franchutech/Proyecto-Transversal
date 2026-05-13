package com.example.dentify;

import Model.Doctor;
import Model.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class DentifyController {
    @FXML
    private Label welcomeText;

    @FXML
    private ChoiceBox<String> choiceBoxEstados;

    @FXML
    private ChoiceBox<Paciente> choicePaciente;

    @FXML
    private ChoiceBox<Doctor> choiceDoctor;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private ComboBox<String> cboHora;

    @FXML
    public void initialize() {
        // Lista simple de horas
        cboHora.getItems().addAll(
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
                "18:00", "18:30"
        );
        // Estados de la cita
        choiceBoxEstados.getItems().addAll("PENDIENTE", "ACTIVO", "CANCELADO");
        choiceBoxEstados.setValue("PENDIENTE"); // Estado por defecto

//        cargarPacientes();
//        cargarDoctores();
    }

    //--------------------------------------------------------------------------------


    //ESTOS METODOS SON PARA CARGAR LOS PACIENTES Y DOCTORES EN EL CHOICEBOX

//    private void cargarPacientes() {
//        // Si tienes un DAO
//        PacienteDAO dao = new PacienteDAO();
//        List<Paciente> pacientes = dao.getAllPacientes();
//
//        choicePaciente.getItems().clear();
//        choicePaciente.getItems().addAll(pacientes);
//
//        // Seleccionar el primero si hay
//        if (!choicePaciente.getItems().isEmpty()) {
//            choicePaciente.setValue(choicePaciente.getItems().get(0));
//        }
//    }
//
//    private void cargarDoctores() {
//        // Si tienes un DAO
//        DoctorDAO dao = new DoctorDAO();
//        List<Doctor> doctores = dao.getAllDoctores();
//
//        choiceDoctor.getItems().clear();
//        choiceDoctor.getItems().addAll(doctores);
//
//        // Seleccionar el primero si hay
//        if (!choiceDoctor.getItems().isEmpty()) {
//            choiceDoctor.setValue(choiceDoctor.getItems().get(0));
//        }
//    }

    //--------------------------------------------------------------------------------

    //metodo que podria funcionar para guardar cita, no es definido

//    private void guardarCita() {
//        String horaSeleccionada = cboHora.getValue();
//        if (horaSeleccionada != null) {
//            // Guardar la hora junto con los demás datos
//            System.out.println("Hora: " + horaSeleccionada);
//        }
//    }
}