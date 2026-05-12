package com.example.dentify.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cita {

    private int idCita;
    private LocalDate fecha;
    private LocalDateTime hora;
    private String motivo;

    //fk
    private int idEstado;
    private int idPaciente;
    private int idDoctor;

    public Cita(int idCita, LocalDate fecha, LocalDateTime hora, String motivo, int idEstado, int idPaciente, int idDoctor) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.idEstado = idEstado;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }
}
