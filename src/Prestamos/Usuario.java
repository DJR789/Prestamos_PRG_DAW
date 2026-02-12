package Prestamos;

import java.time.LocalDate;

public class Usuario {
    private String nombre;
    private String email;
    private String numeroSocio;
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;

    public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro) throws UsuarioInvalidoException {
        if (nombre==null) {
            throw new UsuarioInvalidoException("El nombre no puede estar vacío");
        }

        if (email==null || !email.matches(".+@.+\\..+")) {
            throw new UsuarioInvalidoException("El email no tiene un formato válido");
        }

        if (numeroSocio==null || !numeroSocio.matches("^SOC\\d{5}$")) {
            throw new UsuarioInvalidoException("El número de socio debe tener formato SOC + 5 dígitos");
        }

        if (fechaRegistro==null) {
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

    public void sancionar(int diasSancion, LocalDate fechaRegistro){
        this.sancionado=true;
        this.fechaFinSancion=fechaRegistro.plusDays(diasSancion);

    }
    public void lenvantarSancion(){
        this.sancionado=false;
        this.fechaFinSancion=null;
    }

    public boolean estaSancionado(){
        if(!sancionado || fechaFinSancion==null){
            return false;
        }
        return LocalDate.now().isBefore(fechaFinSancion);
    }

    public String toString(){
        String estado;
        if (estaSancionado()){
            estado=" Tiene sancion " + fechaFinSancion;
        }
        else {
            estado= " No tiene sancion ";
        }
        return "Usuario: "+
                "El nombre es: " + nombre +
                " El email es: " + email +
                " El numero de socio es: " + numeroSocio +
                " La fecha de registro es: " + fechaRegistro +
                estado;
    }
    public String getNumeroSocio(){
        return numeroSocio;
    }


}
