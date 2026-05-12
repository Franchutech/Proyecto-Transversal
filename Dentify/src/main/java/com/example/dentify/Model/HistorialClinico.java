package com.example.dentify.Model;

import java.time.LocalDate;

public class HistorialClinico {

    private int idHistorialClinico;
    private int idPaciente;
    private LocalDate fechaCreacion;
    private String grupoSanguineo;

    public HistorialClinico(int idHistorialClinico, int idPaciente, LocalDate fechaCreacion, String grupoSanguineo) {
        this.idHistorialClinico = idHistorialClinico;
        this.idPaciente = idPaciente;
        this.fechaCreacion = fechaCreacion;
        this.grupoSanguineo = grupoSanguineo;
    }

    public int getIdHistorialClinico() {
        return idHistorialClinico;
    }

    public void setIdHistorialClinico(int idHistorialClinico) {
        this.idHistorialClinico = idHistorialClinico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
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
}
