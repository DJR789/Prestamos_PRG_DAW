package Prestamos;

import java.time.LocalDate;

public class Usuario {
    protected String nombre;
    protected String email;
    protected String numeroSocio;
    protected LocalDate fechaRegistro;
    protected boolean sancionado;
    protected LocalDate fechaFinSancion;

    public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro) throws UsuarioInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El nombre no puede estar vacío");
        }

        if (email == null || !email.matches(".+@.+\\..+")) {
            throw new UsuarioInvalidoException("El email no tiene un formato válido");
        }

        if (numeroSocio == null || !numeroSocio.matches("^SOC\\d{5}$")) {
            throw new UsuarioInvalidoException("El número de socio debe tener formato SOC + 5 dígitos");
        }

        if (fechaRegistro == null) {
            throw new UsuarioInvalidoException("La fecha de registro no puede ser nula");
        }

        if (fechaRegistro.isAfter(LocalDate.now())) {
            throw new UsuarioInvalidoException("La fecha de registro no puede ser futura");
        }

        this.nombre = nombre;
        this.email = email;
        this.numeroSocio = numeroSocio;
        this.fechaRegistro = fechaRegistro;

    }

    public void setSancionado(boolean sancionado, LocalDate fechaFinSancion){
        if (sancionado){
            this.fechaFinSancion=fechaFinSancion;
        }
        else {
            this.fechaFinSancion= null;
        }
    }

    public void sancionar(){

    }
    public void lenvantarSancion(){

    }
    /*
    public boolean estaSancionado(){
        if (sancionado=){
            return sancionado=true;
        }
        else{
            return sancionado=null;
        }

    }

     */
}
