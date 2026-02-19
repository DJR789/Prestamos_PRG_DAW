package Prestamos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
        if (tituloLibro==null || tituloLibro.isEmpty()) {
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
        if (fecha==null ){
            throw new PrestamoInvalidoException("La fecha no puede ser nula");
        }
        if(fecha.isBefore(fechaPrestamo)){
            throw new PrestamoInvalidoException("La fecha no puede ser anterior a la de prestamo");
        }
        this.fechaDevolucionReal=fecha;
    }

    public int calcularDiasRetraso(){
        LocalDate referencia;
        if(fechaDevolucionReal!=null) {
            referencia = fechaDevolucionReal;
        }
        else{
            referencia=LocalDate.now();
        }
        long dias= ChronoUnit.DAYS.between(fechaDevolucionPrevista,referencia);
        return (int) Math.max(dias,0);
    }

    public boolean estaRetrasado(){
        return LocalDate.now().isAfter((fechaDevolucionPrevista)) && fechaDevolucionReal == null;
    }

    public String toString(){
        String estadoDevolucion;
        if(fechaDevolucionReal==null){
            estadoDevolucion="No esta devuelto";
        }
        else {
            estadoDevolucion=fechaDevolucionReal.toString();
        }
        return "Prestamo: " +
                "Codigo de Libro: " +codigoLibro +
                " Título: " + tituloLibro +
                " Socio: " +socio +
                " Fecha del Prestamo: " + fechaPrestamo +
                " Fecha de devolucion prevista: " +fechaDevolucionPrevista +
                " Fecha de devolución real: " + estadoDevolucion +
                " Dias de retraso: " + calcularDiasRetraso();

    }
    public String getCodigoLibro(){
        return codigoLibro;
    }
    public Usuario getSocio(){
        return socio;
    }
    public boolean estaDevuelto(){
        return fechaDevolucionReal!=null;
    }

    public String getFechaDevolucionPrevista() {
        DateTimeFormatter formato= DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaDevolucionPrevista.format(formato);
    }
}
