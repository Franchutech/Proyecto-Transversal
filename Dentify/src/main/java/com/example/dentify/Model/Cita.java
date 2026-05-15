package com.example.dentify.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cita {

    // ATRIBUTOS
    private int idCita;
    private LocalDate fecha;
    private LocalDateTime hora;
    private String motivo;
    private int idEstado;

    // ATRIBUTOS DE COMPOSICIÓN
    private Paciente paciente;
    private Doctor doctor;

    // CAMPOS INTEGRADOS DE DETALLE CITA
    private int idTratamiento;
    private String observaciones;

    // CONSTRUCTOR
    public Cita(int idCita, LocalDate fecha, LocalDateTime hora, String motivo, int idEstado, Paciente paciente, Doctor doctor, int idTratamiento, String observaciones) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.idEstado = idEstado;
        this.paciente = paciente;
        this.doctor = doctor;
        this.idTratamiento = idTratamiento;
        this.observaciones = observaciones;
    }

    // GETTERS Y SETTERS
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalDateTime getHora() { return hora; }
    public void setHora(LocalDateTime hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    // GETTERS Y SETTERS DE OBJETOS COMPLETOS
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    // MÉTODOS PUENTE PARA EVITAR ERRORES DE ID


    public int getIdPaciente() {
        return (paciente != null) ? paciente.getIdPaciente() : 0;
    }

    public int getIdDoctor() {
        return (doctor != null) ? doctor.getIdDoctor() : 0;
    }

    public int getIdTratamiento() { return idTratamiento; }
    public void setIdTratamiento(int idTratamiento) { this.idTratamiento = idTratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    //METODOS DE VALIDACION

    // Mi método para validar si los datos de la cita tienen sentido con la nueva estructura
public boolean esValida() {
    // 1. Verifico que las fechas no sean nulas
    if (this.fecha == null || this.hora == null) return false;

    // 2. No permito citas en el pasado
    if (this.fecha.isBefore(LocalDate.now())) return false;

    // 3. Valido que el motivo tenga contenido real
    if (this.motivo == null || this.motivo.trim().isEmpty()) return false;

    // 4. NUEVA VALIDACIÓN: Los objetos Paciente y Doctor deben existir
    if (this.paciente == null || this.doctor == null) return false;

    // 5. Verifico que sus IDs internos sean correctos (usando tus métodos puente)
    return this.getIdPaciente() > 0 && this.getIdDoctor() > 0;

}//CIERRE METODO VALIDACION DATOS CITA



    // TOSTRING
    @Override
    public String toString() {
        return "Cita #" + idCita + " | Paciente: " + (paciente != null ? paciente.getNombre() : "N/A");
    }


}