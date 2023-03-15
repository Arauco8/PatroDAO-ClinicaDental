package com.clinicadental.model;

import java.util.Objects;

public class Odontologo {

    private Integer id;
    private String matricula;
    private String nombre;
    private String apellido;


    public Odontologo() {}

    public Odontologo(String matricula, String apellido, String nombre ) {
        this.matricula = matricula;
        this.apellido = apellido;
        this.nombre = nombre;


    }

    public Odontologo(Integer id, String matricula, String apellido, String nombre) {
        this.id = id;
        this.matricula = matricula;
        this.apellido = apellido;
        this.nombre = nombre;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odontologo that = (Odontologo) o;
        return id.equals(that.id) && matricula.equals(that.matricula) && apellido.equals(that.apellido) && nombre.equals(that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matricula, apellido, matricula);
    }

    @Override
    public String toString() {
        return "\tId: '" + id + '\'' +"\tNombre: '" + nombre + '\'' + "\tApellido: '" + apellido + '\'' + "\tMatricula: " + matricula;
    }
}
