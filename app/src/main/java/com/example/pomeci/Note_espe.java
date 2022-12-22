package com.example.pomeci;

public class Note_espe {
    String Foto,Nombre,TipoConsultorio,Direccion,Especialidad;
    String Descripcion,Telefono,Correo,Cedula,Experiencia,Horario;

    public Note_espe(){}

    public Note_espe(String foto, String nombre, String tipoConsultorio, String direccion, String especialidad,
                     String descripcion, String telefono, String correo,String cedula,String experiencia,String horario) {
        Foto = foto;
        Nombre = nombre;
        TipoConsultorio = tipoConsultorio;
        Direccion = direccion;
        Especialidad = especialidad;
        Descripcion = descripcion;
        Telefono = telefono;
        Correo = correo;
        Cedula=cedula;
        Experiencia=experiencia;
        Horario=horario;
    }

    public String getFoto() {
        return Foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getTipoConsultorio() {
        return TipoConsultorio;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getCedula() { return Cedula; }

    public String getExperiencia() {
        return Experiencia;
    }

    public String getHorario() {
        return Horario;
    }
}
