package com.example.dentify;

import com.example.dentify.Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class DentifyController {

    // ------ Elementos de Citas ------
    @FXML private ChoiceBox<Estado> choiceBoxEstados;
    @FXML private ChoiceBox<Paciente> choicePaciente;
    @FXML private ChoiceBox<Doctor> choiceDoctor;
    @FXML private ComboBox<String> cboHora;
    @FXML private Label lblTituloCita;
    @FXML private Button btnGuardarCita;
    @FXML private DatePicker DatePickerFecha;
    @FXML private TextArea txtMotivo;
    @FXML private TableView<Cita> tablaCitas;
    @FXML private TableColumn<Cita, Void> colAccionesCitas;


    // ------ Elementos de Pacientes (Formulario) ------
    @FXML private Label lblTituloFormulario;
    @FXML private TextField TFNombre;
    @FXML private TextField TFApellido;
    @FXML private TextField TFTelefono;
    @FXML private TextField TFCorreo;
    @FXML private DatePicker TFNacimiento;
    @FXML private Button btnGuardar;
    @FXML private TableView<Paciente> tablaPacientes;
    @FXML private TableColumn<Paciente, Void> colAcciones;


    // ------- Elementos de Doctores ---------
    @FXML private TableView<Doctor> tablaDoctores;
    @FXML private TableColumn<Doctor, Integer> colIdDoctor;
    @FXML private TableColumn<Doctor, String> colNombreDoctor;
    @FXML private TableColumn<Doctor, Especialidad> colEspecialidad;
    @FXML private TextField TFNombreCompleto;
    @FXML private TextField TFNumColegiado;
    @FXML private TextField TFDireccion;
    @FXML private DatePicker TFNacimientoDoctor;
    @FXML private ChoiceBox<Especialidad> choiceBoxEspecialidad;

    // Objetos auxiliares para saber si estamos editando
    private Paciente pacienteAEditar = null;
    private Cita citaAEditar = null;
    private Doctor doctorAEditar = null;


    @FXML
    public void initialize() {
        configurarSelectores();
        configurarColumnaAcciones();
        configurarColumnaAccionesCitas();
        configurarColumnaEspecialidad();
    }

    //-------- Gestión Doctores -----------

    //onAction del boton para guardar los datos del doctor
    @FXML
    public void manejarGuardarDoctor(ActionEvent actionEvent) {

    }

    //onAction del boton para agregar doctor, boton que se encuentra en la ventanda de gestion de doctores
    public void manejarNuevoDoctor(ActionEvent actionEvent) {

    }

    //funcion para editar doctor
    public void prepararEdicionDoctores(){

    }

    private void configurarColumnaEspecialidad() {
        colEspecialidad.setCellValueFactory(cellData -> {
            Especialidad esp = cellData.getValue().getEspecialidad();
            return new SimpleObjectProperty<>(esp);
        });

        colEspecialidad.setCellFactory(column -> new TableCell<Doctor, Especialidad>() {
            @Override
            protected void updateItem(Especialidad item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    String textoMostrar = formatearEspecialidad(item);
                    setText(textoMostrar);

                    aplicarColorSegunEspecialidad(item);
                }
            }

            private String formatearEspecialidad(Especialidad esp) {
                switch (esp) {
                    case ENDODONCITAS:
                        return "Endodoncia";
                    case ORTODONCISTA:
                        return "Ortodoncia";
                    case CIRUGIA:
                        return "Cirugía Maxilofacial";
                    case GENERAL:
                        return "Odontología General";
                    case PERIODONCISTA:
                        return "Periodoncia";
                    default:
                        return esp.name();
                }
            }

            private void aplicarColorSegunEspecialidad(Especialidad esp) {
                switch (esp) {
                    case ENDODONCITAS:
                        setStyle("-fx-text-fill: #9C27B0; -fx-font-weight: bold;");
                        break;
                    case ORTODONCISTA:
                        setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
                        break;
                    case CIRUGIA:
                        setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold;");
                        break;
                    case GENERAL:
                        setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                        break;
                    case PERIODONCISTA:
                        setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold;");
                        break;
                    default:
                        setStyle("");
                }
            }
        });
    }

    //falta aregar botones de onAction para agregar pacientes y citas, no lo voy agregar para que no haya conflicto






    private void configurarSelectores() {
        // Horas
        cboHora.getItems().addAll(
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
                "18:00", "18:30"
        );

        choiceBoxEstados.getItems().setAll(Estado.values());
        choiceBoxEstados.setValue(Estado.PENDIENTE);
    }

    // REVISAR SI SE PUEDE REUTILIZAR LA FUNCION DE ACCIONES CON DOCTORES

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
                setGraphic(empty ? null : btnEditar);
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


    // ------ Gestión citas ------

    public void prepararEdicionCita(Cita cita) {
        this.citaAEditar = cita;
        lblTituloCita.setText("Editar cita #" + cita.getIdCita());
        btnGuardarCita.setText("Actualizar Cita");
        DatePickerFecha.setValue(cita.getFecha());

        if (cita.getHora() != null) {
            String horaString = cita.getHora().toLocalTime().toString();
            cboHora.setValue(horaString);
        }

        choicePaciente.setValue(cita.getPaciente());
        choiceDoctor.setValue(cita.getDoctor());
        txtMotivo.setText(cita.getMotivo());
        choiceBoxEstados.setValue(cita.getEstado());
    }

    @FXML
    private void manejarGuardarCita() {
        if (citaAEditar != null) {

            LocalDate fecha = DatePickerFecha.getValue();
            String horaSeleccionada = cboHora.getValue();

            if (fecha != null && horaSeleccionada != null) {
                LocalTime tiempo = LocalTime.parse(horaSeleccionada);
                LocalDateTime fechaHoraCompleta = LocalDateTime.of(fecha, tiempo);

                citaAEditar.setFecha(fecha);
                citaAEditar.setHora(fechaHoraCompleta);
            }

            citaAEditar.setPaciente(choicePaciente.getValue());
            citaAEditar.setDoctor(choiceDoctor.getValue());
            citaAEditar.setMotivo(txtMotivo.getText());
            citaAEditar.setEstado(choiceBoxEstados.getValue());

            System.out.println("Cita actualizada correctamente.");
            tablaCitas.refresh();
        }
        limpiarFormularioCita();
    }

    private void limpiarFormularioCita() {
        citaAEditar = null;
        DatePickerFecha.setValue(null);
        cboHora.setValue(null);
        choicePaciente.setValue(null);
        choiceDoctor.setValue(null);
        txtMotivo.clear();
        choiceBoxEstados.setValue(Estado.PENDIENTE);

        lblTituloCita.setText("Agregar cita");
        btnGuardarCita.setText("Guardar");
    }

    private void configurarColumnaAccionesCitas() {
        colAccionesCitas.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("✏️");
            private final Button btnEliminar = new Button("🗑️");
            private final HBox contenedor = new HBox(10, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("primary-button");
                btnEliminar.getStyleClass().add("danger-button");

                btnEditar.setOnAction(event -> {
                    Cita seleccionado = getTableView().getItems().get(getIndex());
                    prepararEdicionCita(seleccionado);
                });

                btnEliminar.setOnAction(event -> {
                    Cita seleccionado = getTableView().getItems().get(getIndex());
                    confirmarEliminarCita(seleccionado);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });
    }

    private void confirmarEliminarCita(Cita cita) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de eliminar la cita #" + cita.getIdCita() + "?");
        alert.setContentText("Esta acción no se puede deshacer.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Aquí se tiene que llamar a CitaDAO para eliminar
            tablaCitas.getItems().remove(cita);
            System.out.println("Cita eliminada: " + cita.getIdCita());
        }
    }



}