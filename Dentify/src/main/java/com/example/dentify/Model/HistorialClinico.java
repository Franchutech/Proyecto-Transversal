package com.example.dentify.Model;

import java.time.LocalDate;

public class HistorialClinico {

    // ATRIBUTOS
    private int idHistorialClinico;
    private Paciente paciente; // Cambio de int a objeto Paciente
    private LocalDate fechaCreacion;
    private String grupoSanguineo;

    // CONSTRUCTOR
    public HistorialClinico(int idHistorialClinico, Paciente paciente, LocalDate fechaCreacion, String grupoSanguineo) {
        this.idHistorialClinico = idHistorialClinico;
        this.paciente = paciente;
        this.fechaCreacion = fechaCreacion;
        this.grupoSanguineo = grupoSanguineo;
    }

    //CONSTRUCTOR VACIO-PARA PERSISTENCIA DEL DAO
    public HistorialClinico() {
    }

    // GETTERS Y SETTERS
    public int getIdHistorialClinico() {
        return idHistorialClinico;
    }

    public void setIdHistorialClinico(int idHistorialClinico) {
        this.idHistorialClinico = idHistorialClinico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // MÉTODO PUENTE (PARA EVITAR ERRORES CON EL ID)
    public int getIdPaciente() {
        return (paciente != null) ? paciente.getId_paciente(): 0;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    // TOSTRING
    @Override
    public String toString() {
        return "Historial #" + idHistorialClinico + " - Paciente: " +
               (paciente != null ? paciente.getNombre() + " " + paciente.getApellido() : "Sin asignar");
    }
}