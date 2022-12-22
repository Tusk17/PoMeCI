package com.example.pomeci;

public class Cita {

    private String CitasID,CorreoCliente,CorreoEspe,Edad,Fecha,Genero,Horario,Motivo,Nombre;

    public Cita(String citasID, String correoCliente, String correoEspe, String edad, String fecha, String genero, String horario, String motivo, String nombre) {
        CitasID = citasID;
        CorreoCliente = correoCliente;
        CorreoEspe = correoEspe;
        Edad = edad;
        Fecha = fecha;
        Genero = genero;
        Horario = horario;
        Motivo = motivo;
        Nombre = nombre;
    }

    public Cita() {

    }

    public String getCitasID() {
        return CitasID;
    }

    public void setCitasID(String citasID) {
        CitasID = citasID;
    }

    public String getCorreoCliente() {
        return CorreoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        CorreoCliente = correoCliente;
    }

    public String getCorreoEspe() {
        return CorreoEspe;
    }

    public void setCorreoEspe(String correoEspe) {
        CorreoEspe = correoEspe;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
