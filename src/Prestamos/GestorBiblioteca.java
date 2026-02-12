package Prestamos;

import java.time.LocalDate;

public class GestorBiblioteca {
    private static  final int Max_usuarios=50;
    private static  final int Max_prestamos=200;
    private  Usuario[] usuarios;
    private Prestamo[] prestamos;
    private int numeroUsuarios;
    private int numeroPrestamos;

    public GestorBiblioteca(){
        usuarios= new Usuario[Max_usuarios];
        prestamos= new Prestamo[Max_prestamos];
        this.numeroUsuarios=0;
        this.numeroPrestamos=0;
    }
    public void registrarUsuario(Usuario u)throws UsuarioInvalidoException{
        for (int i=0; i<numeroUsuarios; i++){
            if(usuarios[i].getNumeroSocio().equals(u.getNumeroSocio())){
                throw new UsuarioInvalidoException("El usuario ya esta registrado");
            }
        }
            if (numeroUsuarios<Max_usuarios){
            usuarios[numeroUsuarios]=u;
            numeroUsuarios++;
        }
    }
    public void realizarPrestamo(String codigolibro, String titulolibro, LocalDate fechaPrestamo, Usuario u)throws PrestamoInvalidoException, UsuarioInvalidoException, LibroNoDisponibleException{

    }
    /*
    public boolean devolverLibro(){

    }

     */
    /*
    public Usuario buscarUsuario(String numeroSocio){

    }

     */
    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public Prestamo[] getPrestamos() {
        return prestamos;
    }
    /*
    public String toString(){

    }

     */
}
