package com.example.dentify;

import com.example.dentify.Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class DentifyController {

    // ------ Elementos de Citas ------(EN PROCESO CON FRANCELLA, CONSULTARME ANTES DE MODIFICAR)
    @FXML private ChoiceBox<Estado> choiceBoxEstados;
    @FXML private ChoiceBox<Paciente> choicePaciente;
    @FXML private ChoiceBox<Doctor> choiceDoctor;
    @FXML private ComboBox<String> cboHora;
    @FXML private Label lblTituloCita;
    @FXML private Button btnGuardarCita;
    @FXML private Button btnNuevo;//AGREGADO POR FRANCELLA
    @FXML private DatePicker DatePickerFecha;
    @FXML private TextArea txtMotivo;
    @FXML private TableView<Cita> tablaCitas;
    @FXML private TableColumn<Cita, Void> colAccionesCitas;
    @FXML private TableColumn<Cita, Integer> colId;
    @FXML private TableColumn<Cita, LocalDate> colFecha;
    @FXML private TableColumn<Cita, LocalDateTime> colHora;
    @FXML private TableColumn<Cita, String> colPaciente;
    @FXML private TableColumn<Cita, String> colDoctor;
    @FXML private TableColumn<Cita, String> colMotivo;
    @FXML private TableColumn<Cita, Estado> colEstado;


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
    @FXML private TableColumn<Paciente, String> colNombreDoctor;
    @FXML private TableColumn<Doctor, Especialidad> colEspecialidad;

    // Objetos auxiliares para saber si estamos editando
    private Paciente pacienteAEditar = null;
    private Cita citaAEditar = null;


    @FXML
    public void initialize() {
        configurarSelectores();
        configurarColumnaAcciones();
        configurarColumnaAccionesCitas();
        configurarColumnasCitas(); // AÑADIDO POR FRANCELLA
        cargarDatosDePruebaCitas();//AÑADIDO POR FRANCELLA


        btnNuevo.setOnAction(event -> manejarNuevaCita());//AÑADIDO POR FRANCELLA

    }

    private void configurarColumnaEspecialidad() {
        // Configurar cómo obtener el valor del enum desde el objeto Doctor
        colEspecialidad.setCellValueFactory(cellData -> {
            Especialidad esp = cellData.getValue().getEspecialidad();
            return new SimpleObjectProperty<>(esp);
        });

        // Configurar cómo se muestra visualmente el enum
        colEspecialidad.setCellFactory(column -> new TableCell<Doctor, Especialidad>() {
            @Override
            protected void updateItem(Especialidad item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    // Convertir el enum a texto legible
                    String textoMostrar = formatearEspecialidad(item);
                    setText(textoMostrar);

                    // Opcional: Aplicar colores según la especialidad
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

    // --- Pendiente de conectar con Backend (Descomentar cuando existan los DAOs) ---
    /*
    private void cargarPacientes() {
        PacienteDAO dao = new PacienteDAO();
        List<Paciente> lista = dao.getAllPacientes();
        tablaPacientes.getItems().setAll(lista);
    }
    */


    // Gestión citas
    //GESTION CITAS EN PROCESO CON FRANCELLA

    public void prepararEdicionCita(Cita cita) {
            this.citaAEditar = cita;

            lblTituloCita.setText("Editar cita #" + cita.getIdCita());
            btnGuardarCita.setText("Actualizar datos");

            DatePickerFecha.setValue(cita.getFecha());

            if (cita.getHora() != null) {
                String horaFormateada = String.format("%02d:%02d", cita.getHora().getHour(), cita.getHora().getMinute());
                cboHora.setValue(horaFormateada);
            }

            choicePaciente.setValue(cita.getPaciente());
            choiceDoctor.setValue(cita.getDoctor());
            txtMotivo.setText(cita.getMotivo());

            if (cita.getEstado() != null) {
                choiceBoxEstados.setValue(cita.getEstado());
            }
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
    }//CIERRE CONFIRMAR ELIMINAR CITA

    //METODO CONFIGURACION COLUMNAS CITAS

    private void configurarColumnasCitas() {
    // Vincular columnas con tipos de datos básicos y Enums
    colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdCita()));
    colFecha.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFecha()));
    colMotivo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMotivo()));
    colEstado.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEstado()));

    //  Configurar columna de Hora: Origen del dato bruto (LocalDateTime)
    colHora.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHora()));

    // Configurar columna de Hora: Factoría de celda para formatear visualmente a texto "HH:mm"
    colHora.setCellFactory(column -> new TableCell<Cita, LocalDateTime>() {
        @Override
        protected void updateItem(LocalDateTime item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
                setText(item.format(formatter));
            }
        }
    });

    // Mapear el objeto Paciente para extraer el String (Nombre + Apellido)
    colPaciente.setCellValueFactory(cellData -> {
        Paciente p = cellData.getValue().getPaciente();
        return new javafx.beans.property.SimpleStringProperty(p != null ? p.getNombre() + " " + p.getApellido() : "");
    });

    // Mapear el objeto Doctor para extraer el String (Nombre)
    colDoctor.setCellValueFactory(cellData -> {
        Doctor d = cellData.getValue().getDoctor();
        return new javafx.beans.property.SimpleStringProperty(d != null ? d.getNombre() : "");
    });
}  //CIERRE METODO CONFIGURAR COLUMNAS CITAS

    @FXML
    private void manejarNuevaCita() {
        // Pongo el formulario en modo "Agregar" limpiando cualquier residuo de edición
        limpiarFormularioCita();

        // Aquí irá la lógica para cambiar la vista al FXML del formulario (CitasForm.fxml)
        System.out.println("Cambiando a la vista de formulario de citas...");

    }//CIERRE METODO MANEJAR NUEVA CITA


    //METODO DE INYECCION DE DATOS PARA PRUEBAS DE CAMBIO DE INTERFAZ CON EL MISMO CONTROLLER

private void cargarDatosDePruebaCitas() {
    // 1. Datos simulados de Pacientes
    Paciente p1 = new Paciente(1, "Francella", "Rojas", "123456789",
            "fran@test.com", LocalDate.of(2000, 1, 1));
    Paciente p2 = new Paciente(2, "Juan", "David", "987654321",
            "juan@test.com", LocalDate.of(1998, 5, 10));

    // 2. Datos simulados de Doctores USO EL CONSTRUCTOR VACIO
    Doctor d1 = new Doctor();
    d1.setIdDoctor(1);
    d1.setNombre("Dr. Anuar");

    Doctor d2 = new Doctor();
    d2.setIdDoctor(2);
    d2.setNombre("Dra. Andrea");

    choicePaciente.getItems().addAll(p1, p2);
    choiceDoctor.getItems().addAll(d1, d2);

    // DATOS SIMULADOS CITAS USANDO CONSTRUCTOR VACIO
    LocalDateTime hora1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
    LocalDateTime hora2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30));

    Cita c1 = new Cita();
    c1.setIdCita(1);
    c1.setPaciente(p1);
    c1.setDoctor(d1);
    c1.setFecha(LocalDate.now());
    c1.setHora(hora1);
    c1.setMotivo("Revisión rutinaria");
    c1.setEstado(Estado.PENDIENTE);

    Cita c2 = new Cita();
    c2.setIdCita(2);
    c2.setPaciente(p2);
    c2.setDoctor(d2);
    c2.setFecha(LocalDate.now().plusDays(1));
    c2.setHora(hora2);
    c2.setMotivo("Ajuste de ortodoncia");
    c2.setEstado(Estado.ACTIVO); // ACTIVO ERA EL CORRECTO

    tablaCitas.getItems().addAll(c1, c2);
}

}//CIERRE DENTIFY CONTROLLER