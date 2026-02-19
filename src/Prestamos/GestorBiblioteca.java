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

    public void registrarUsuario(Usuario u)throws UsuarioInvalidoException, UsuarioRepetidoException{
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

    public Prestamo realizarPrestamo(String codigolibro, String titulolibro, LocalDate fechaPrestamo, Usuario usuario)
            throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException, UsuarioInvalidoException {
        if ((usuario!=null && usuario.estaSancionado())){
            throw new UsuarioSancionadoException("El usuario ya esta sancionado y no puede realizar prestamos");
        }
        for (int i =0; i<numeroPrestamos; i++){
            if (prestamos[i]!=null && prestamos[i].getCodigoLibro().equals(codigolibro) && !prestamos[i].estaDevuelto()){
        throw new LibroNoDisponibleException("El libro no esta disponible, ya esta prestado");
            }
        }
        Prestamo nuevoPrestamo= new Prestamo(codigolibro,usuario,titulolibro,fechaPrestamo);
        if (numeroPrestamos < Max_prestamos){
            prestamos[numeroPrestamos]= nuevoPrestamo;
            numeroPrestamos++;
        }
        return nuevoPrestamo;
    }

    public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion) throws PrestamoInvalidoException{
        for (int i=0; i<numeroPrestamos; i++){
            if (prestamos[i]!=null && prestamos[i].getCodigoLibro().equals(codigoLibro) && !prestamos[i].estaDevuelto()){
                prestamos[i].registrarDevolucion(fechaDevolucion);
                int diasRetraso = prestamos[i].calcularDiasRetraso();
                if(diasRetraso>0){
                    Usuario socio=prestamos[i].getSocio();
                    socio.sancionar(diasRetraso,fechaDevolucion);
                }
                return true;
            }
        }
        return false;
    }

    public Usuario buscarUsuario(String numeroSocio){
        for (int i=0; i<numeroUsuarios; i++){
            if (usuarios[i]!=null && usuarios[i].getNumeroSocio().equals(numeroSocio)){
                return usuarios[i];
            }
        }
        return  null;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public Prestamo[] getPrestamos() {
        return prestamos;
    }

    @Override
    public String toString() {
        String resultado = "Gestor Biblioteca: ";

        resultado += "Usuarios: ";
        for (int i = 0; i < numeroUsuarios; i++) {
            resultado += usuarios[i] + " usuarios";
        }

        resultado += "Prestamos: ";
        for (int i = 0; i < numeroPrestamos; i++) {
            resultado += prestamos[i] + " prestamos";
        }
        return resultado;
    }



}
