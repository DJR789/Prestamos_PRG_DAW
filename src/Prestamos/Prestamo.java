package Prestamos;

import java.time.LocalDate;

public class Prestamo {
    private String codigoLibro;
    private String tituloLibro;
    private Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

    public Prestamo(String codigoLibro, Usuario u, String tituloLibro, LocalDate fechaPrestamo) throws PrestamoInvalidoException, UsuarioInvalidoException {
        if(codigoLibro==null|| !codigoLibro.matches("^[A-Z]{3}\\d{4}$")){
            throw new PrestamoInvalidoException("El codigo de libro debe tener el formato correcto");
        }
        if (tituloLibro==null) {
            throw new PrestamoInvalidoException("El nombre del titulo no puede estar vacio");
        }
        if (fechaPrestamo==null || fechaPrestamo.isAfter(LocalDate.now())) {
            throw new PrestamoInvalidoException("La fecha no puede ser nula ni posterior a la actual");
        }

        this.codigoLibro=codigoLibro;
        this.socio=u;
        this.tituloLibro=tituloLibro;
        this.fechaPrestamo=fechaPrestamo;
        this.fechaDevolucionPrevista= fechaPrestamo.plusDays(14);
    }

    public void registrarDevolucion(LocalDate fecha) throws PrestamoInvalidoException{
        if (fecha==null || fecha.isBefore(fechaPrestamo)) {
            throw new PrestamoInvalidoException("La fecha no puede ser nula ni anterior a la fecha de prestamo");
        }
    }
    /*
    public int calcularDiasRetraso(){

    }
    public boolean estaRetrasado(){

    }
    public String toString(){

    }

     */
}
