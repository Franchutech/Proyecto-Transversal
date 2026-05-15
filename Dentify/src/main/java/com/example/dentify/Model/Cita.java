package com.example.dentify.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cita {

    // ATRIBUTOS
    private int idCita;
    private LocalDate fecha;
    private LocalDateTime hora;
    private String motivo;
    private Estado Estado;

    // ATRIBUTOS DE COMPOSICIÓN
    private Paciente paciente;
    private Doctor doctor;

    // CAMPOS INTEGRADOS DE DETALLE CITA
    private int idTratamiento;
    private String observaciones;

    // CONSTRUCTOR
    public Cita(int idCita, LocalDate fecha, LocalDateTime hora, String motivo, Estado Estado, Paciente paciente, Doctor doctor, int idTratamiento, String observaciones) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.Estado = Estado;
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

    public Estado getEstado() { return Estado; }
    public void setEstado(Estado Estado) { this.Estado = Estado; }

    // GETTERS Y SETTERS DE OBJETOS COMPLETOS
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    // MÉTODOS PUENTE PARA EVITAR ERRORES DE ID
    // Estos devuelven el ID entrando en el objeto, así no rompes el código antiguo
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

    // TOSTRING
    @Override
    public String toString() {
        return "Cita #" + idCita + " | Paciente: " + (paciente != null ? paciente.getNombre() : "N/A");
    }
}