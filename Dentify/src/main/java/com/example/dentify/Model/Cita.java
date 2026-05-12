package com.example.dentify.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
public class Cita {
    private int idCita;
    private LocalDate fecha;
    private LocalDateTime hora;
    private String motivo;
    private int idEstado;
    private int idPaciente;
    private int idDoctor;

    // LA UNIÓN: La cita ahora conoce su detalle
    private DetalleCita detalle;

    // Constructor actualizado
    public Cita(int idCita, LocalDate fecha, LocalDateTime hora, String motivo, int idEstado, int idPaciente, int idDoctor) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.idEstado = idEstado;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
    }

    // Getter y Setter para el detalle
    public DetalleCita getDetalle() { return detalle; }
    public void setDetalle(DetalleCita detalle) { this.detalle = detalle; }

    // ... el resto de tus getters y setters
}