package com.example.dentify.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cita {

    // ATRIBUTOS
    private int idCita;
    private LocalDate fecha;
    private LocalDateTime hora;
    private String motivo;
    private Estado Estado; // Mantenemos el atributo según tu declaración

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

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    // MÉTODOS PUENTE CORREGIDOS CON LA NOMENCLATURA DE ANDREA
    public int getIdPaciente() {
        return (paciente != null) ? paciente.getId_paciente() : 0; // Cambiado a getId_paciente()
    }

    public int getIdDoctor() {
        return (doctor != null) ? doctor.getIdDoctor() : 0;
    }

    public int getIdTratamiento() { return idTratamiento; }
    public void setIdTratamiento(int idTratamiento) { this.idTratamiento = idTratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    // MÉTODO PARA VALIDAR
    public boolean esValida() {
        // 1. Verificaciones básicas de nulos
        if (this.fecha == null || this.hora == null) return false;
        if (this.motivo == null || this.motivo.trim().isEmpty()) return false;
        if (this.paciente == null || this.doctor == null) return false;
        if (this.getIdPaciente() <= 0 || this.getIdDoctor() <= 0) return false;

        // 2. Control del tiempo: No citas en el pasado real (Fecha y Hora juntas)
        if (this.hora.isBefore(LocalDateTime.now())) return false;

        // 3. Control de Horario Comercial (Ejemplo: 9:00 a 20:00)
        int horaInt = this.hora.getHour();
        if (horaInt < 9 || horaInt >= 20) return false;

        // 4. Control de fines de semana (Evitar Sábados y Domingos si la clínica cierra)
        java.time.DayOfWeek dia = this.fecha.getDayOfWeek();
        if (dia == java.time.DayOfWeek.SATURDAY || dia == java.time.DayOfWeek.SUNDAY) return false;

        return true;
    }

    // TOSTRING
    @Override
    public String toString() {
        return "Cita #" + idCita + " | Paciente: " + (paciente != null ? paciente.getNombre() : "N/A");
    }
}