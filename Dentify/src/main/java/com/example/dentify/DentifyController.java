package com.example.dentify;

import com.example.dentify.Model.Cita;
import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Paciente;
import com.example.dentify.dao.PacienteDAO;

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
        // Configuracion de combos y selectores (Solo si existen en la pantalla actual)
        configurarSelectores();

        // Configuracion de la columna de acciones (Solo si la tabla está en la pantalla)
        if (colAcciones != null) {
            configurarColumnaAcciones();
        }

        // Cargar los pacientes (Solo si la tabla existe en la pantalla)
        if (tablaPacientes != null) {
            cargarPacientes();
        }
    }

    private void configurarSelectores() {
        // Ponemos un escudo: solo rellena las horas si el combo realmente existe en el FXML actual
        if (cboHora != null) {
            cboHora.getItems().addAll(
                    "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                    "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                    "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
                    "18:00", "18:30"
            );
        }

        // Lo mismo para los estados
        if (choiceBoxEstados != null) {
            choiceBoxEstados.getItems().addAll("PENDIENTE", "ACTIVO", "CANCELADO");
            choiceBoxEstados.setValue("PENDIENTE");
        }
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

    // ------ GESTION PACIENTES ------

    // Metodo
    public void prepararEdicion(Paciente p) {
        this.pacienteAEditar = p;
        lblTituloFormulario.setText("Editar Paciente");
        btnGuardar.setText("Actualizar datos");

        TFNombre.setText(p.getNombre());
        TFApellido.setText(p.getApellido());
        TFTelefono.setText(p.getTelefono());
        TFCorreo.setText(p.getCorreo_electronico());
        TFNacimiento.setValue(p.getFecha_nacimiento());
    }

    //Metodo para el boton guardar, mira si están intentando crear un paciente nuevo o editar los datos de uno que ya existe
    @FXML
    private void manejarGuardarPaciente() {
        // VALIDACION PARA QUE NO PUEDAN DEJAR LOS DATOS EN BLANCO
        if (TFNombre.getText().trim().isEmpty() || TFApellido.getText().trim().isEmpty() || TFTelefono.getText().trim().isEmpty()) {
            //ABRE UNA VENTANA DE ERROR
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, rellena los campos obligatorios: Nombre, Apellido y Teléfono.");
            alerta.showAndWait();
            return;
        }

        if (pacienteAEditar != null) {
            // Modo edición
            pacienteAEditar.setNombre(TFNombre.getText());
            pacienteAEditar.setApellido(TFApellido.getText());
            pacienteAEditar.setTelefono(TFTelefono.getText());
            pacienteAEditar.setCorreo_electronico(TFCorreo.getText());
            pacienteAEditar.setFecha_nacimiento(TFNacimiento.getValue());

            System.out.println("Paciente actualizado en memoria: " + pacienteAEditar.getNombre());
            // PENDIENTE: Cuando hagamos el metodo update, llamaremos aquí a PacienteDAO.updatePaciente(pacienteAEditar);
        } else {
            //Construimos el paciente
            Paciente nuevo = new Paciente(
                    0,
                    TFNombre.getText().trim(),
                    TFApellido.getText().trim(),
                    TFTelefono.getText().trim(),
                    TFCorreo.getText().trim(),
                    TFNacimiento.getValue()
            );

            //LLAMO AL METODO CREADO EN DAO (createPaciente)
            boolean insertadoOk = PacienteDAO.createPaciente(nuevo);

            if (insertadoOk) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Paciente guardado correctamente!");
                alerta.showAndWait();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al guardar el paciente en la base de datos.");
                alerta.showAndWait();
            }
        }

        // Refrescamos la tabla para que se vea el nuevo paciente y limpiamos los inputs
        cargarPacientes();
        limpiarFormulario();
    }

    //SE ENCARGA DE BORRAR LO QUE SE HAYA ESCRITO (limpia la interfaz)
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

    // Conectado con tu backend de forma limpia
    private void cargarPacientes() {
        List<Paciente> lista = PacienteDAO.getAllpacientes();
        tablaPacientes.getItems().setAll(lista);
    }


    // Gestión citas

    public void prepararEdicionCita(Cita cita){
        this.citaAEditar = cita;

        lblTituloCita.setText("Editar cita #" + cita.getIdCita());
        btnGuardarCita.setText("Guardar");

        DatePickerFecha.setValue(cita.getFecha());
        cboHora.setValue(DatePickerFecha.getValue().toString());
        choicePaciente.setValue(cita.getPaciente());
        choiceDoctor.setValue(cita.getDoctor());
        txtMotivo.setText(cita.getMotivo());
        // choiceBoxEstados.setValue();
    }
}