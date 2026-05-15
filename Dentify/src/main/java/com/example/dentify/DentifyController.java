package com.example.dentify;

import com.example.dentify.Model.Cita;
import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class DentifyController {

    // ------ Elementos de Citas ------
    @FXML private ChoiceBox<String> choiceBoxEstados;
    @FXML private ChoiceBox<Paciente> choicePaciente;
    @FXML private ChoiceBox<Doctor> choiceDoctor;
    @FXML private ComboBox<String> cboHora;
    @FXML private Label lblTituloCita;
    @FXML private Button btnGuardarCita;
    @FXML private DatePicker DatePickerFecha;
    @FXML private TextArea txtMotivo;


    // ------ Elementos de Pacientes (Formulario) ------
    @FXML private Label lblTituloFormulario;
    @FXML private TextField TFNombre;
    @FXML private TextField TFApellido;
    @FXML private TextField TFTelefono;
    @FXML private TextField TFCorreo;
    @FXML private DatePicker TFNacimiento;
    @FXML private Button btnGuardar;

    // ------ Tabla de Pacientes ------
    @FXML private TableView<Paciente> tablaPacientes;
    @FXML private TableColumn<Paciente, Void> colAcciones;

    // Objeto auxiliar para saber si estamos editando
    private Paciente pacienteAEditar = null;
    private Cita  citaAEditar = null;

    @FXML
    public void initialize() {
        // Configuracion de combos y selectores
        configurarSelectores();

        // Configuracion de la columna de acciones (Botón Editar)
        configurarColumnaAcciones();

        // cargarPacientes();
    }

    private void configurarSelectores() {
        // Horas
        cboHora.getItems().addAll(
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
                "18:00", "18:30"
        );
        // Estados
        choiceBoxEstados.getItems().addAll("PENDIENTE", "ACTIVO", "CANCELADO");
        choiceBoxEstados.setValue("PENDIENTE");
    }

    private void configurarColumnaAcciones() {
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.getStyleClass().add("primary-button");
                btnEditar.setOnAction(event -> {
                    Paciente seleccionado = getTableView().getItems().get(getIndex());
                    prepararEdicion(seleccionado);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEditar);
                }
            }
        });
    }

    // ------ Gestión pacientes ------

    public void prepararEdicion(Paciente p) {
        this.pacienteAEditar = p;
        lblTituloFormulario.setText("Editar Paciente");
        btnGuardar.setText("Actualizar datos");

        TFNombre.setText(p.getNombre());
        TFApellido.setText(p.getApellido());
        TFTelefono.setText(p.getTelefono());
        TFCorreo.setText(p.getCorreoElectronico());
        TFNacimiento.setValue(p.getFechaNacimiento());

    }

    @FXML
    private void manejarGuardarPaciente() {
        if (pacienteAEditar != null) {
            pacienteAEditar.setNombre(TFNombre.getText());
            pacienteAEditar.setApellido(TFApellido.getText());
            pacienteAEditar.setTelefono(TFTelefono.getText());
            pacienteAEditar.setCorreoElectronico(TFCorreo.getText());
            pacienteAEditar.setFechaNacimiento(TFNacimiento.getValue());

            System.out.println("Paciente actualizado: " + pacienteAEditar.getNombre());
        } else {
            System.out.println("Creando nuevo paciente...");
        }

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        pacienteAEditar = null;
        TFNombre.clear();
        TFApellido.clear();
        TFTelefono.clear();
        TFCorreo.clear();
        TFNacimiento.setValue(null);

        lblTituloFormulario.setText("Agregar paciente");
        btnGuardar.setText("Guardar");
    }

    // --- Pendiente de conectar con Backend (Descomentar cuando existan los DAOs) ---
    /*
    private void cargarPacientes() {
        PacienteDAO dao = new PacienteDAO();
        List<Paciente> lista = dao.getAllPacientes();
        tablaPacientes.getItems().setAll(lista);
    }
    */


    // Gestión citas

    public void prepararEdicionCita(Cita cita){
        this.citaAEditar = cita;

        lblTituloCita.setText("Editar cita #" + cita.getIdCita());
        btnGuardarCita.setText("Guardar");

        DatePickerFecha.setValue(cita.getFecha());
        
    }
}