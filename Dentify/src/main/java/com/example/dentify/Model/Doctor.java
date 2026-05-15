package com.example.dentify.Model;

import java.time.LocalDate;

public class Doctor {

    private int idDoctor;
    private String nombre;
    private LocalDate fechaNacimento;
    private String direccion;
    private String numColegiado;

    private Enum especialidad;

    public Doctor(int idDoctor, String nombre, LocalDate fechaNacimento, String direccion, String numColegiado, Enum especialidad) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.fechaNacimento = fechaNacimento;
        this.direccion = direccion;
        this.numColegiado = numColegiado;
        this.especialidad = especialidad;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimento() {
        return fechaNacimento;
    }

    public void setFechaNacimento(LocalDate fechaNacimento) {
        this.fechaNacimento = fechaNacimento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    public Enum getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Enum especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return nombre + " - " + especialidad;
    }

}
