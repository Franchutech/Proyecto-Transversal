package com.example.dentify.Model;

public class DetalleCita {

    private int idDetalleCita;
    private int idCita;
    private int idTratamiento;
    private String observaciones;

    public DetalleCita(int idDetalleCita, int idCita, int idTratamiento, String observaciones) {
        this.idDetalleCita = idDetalleCita;
        this.idCita = idCita;
        this.idTratamiento = idTratamiento;
        this.observaciones = observaciones;
    }

    public int getIdDetalleCita() {
        return idDetalleCita;
    }

    public void setIdDetalleCita(int idDetalleCita) {
        this.idDetalleCita = idDetalleCita;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

@Override
public String toString() {
    return "DetalleCita{" +
            "idDetalle=" + idDetalleCita +
            ", idTratamiento=" + idTratamiento +
            ", observaciones='" + observaciones + '\'' +
            '}';
}

}
