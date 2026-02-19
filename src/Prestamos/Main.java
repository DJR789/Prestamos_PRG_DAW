package Prestamos;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner in= new Scanner(System.in);
    private static final DateTimeFormatter formato_Fecha= DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        GestorBiblioteca gestor= new GestorBiblioteca();
        int opcion;
        do {
            mostrarMenu();
            opcion= leerEntero("Escribe tu opcion: ", 1 , 8);

            switch (opcion) {
                case 1:
                    registrarNuevoUsuario(gestor);
                    break;
                case 2:
                    realizarPrestamo(gestor);
                    break;
                case 3:
                    devolverLibro(gestor);
                    break;
                case 4:
                    consultarEstadoUsuario(gestor);
                    break;
                case 5:
                    mostrarPrestamosActivos(gestor);
                    break;
                case 6:
                    mostrarUsuariosSancionados(gestor);
                    break;
                case 7:
                    actualizarSanciones(gestor);
                    break;
                case 8:
                    System.out.println("Has salido del programa correctamente");
                    break;
            }

            if (opcion!=8) {
                System.out.println("Pulsa ENTER para continuar");
                in.nextLine();
            }
        }while (opcion!=8);
    }

    private static void mostrarMenu(){
        System.out.println("=== SISTEMA GESTION BIBLIOTECA ===");
        System.out.println("1. Registrar nuevo usuario");
        System.out.println("2. Realizar prestamo de libro");
        System.out.println("3. Devolver libro");
        System.out.println("4. Consultar estado de usuario");
        System.out.println("5. Mostrar prestamos activos");
        System.out.println("6. Mostrar usuarios sancionados");
        System.out.println("7. Actualizar sanciones");
        System.out.println("8. Salir");
    }

        private static void registrarNuevoUsuario(GestorBiblioteca gestor){
        System.out.println("Registrar nuevo usuario");
        String nombre= leerTextoNoVacio("Nombre: ");
        String email= leerTextoNoVacio("Email: ");
        String numeroSocio= leerTextoNoVacio("Numero de socio (SOC00001): ");
        LocalDate fecharegistro= leerFecha("Fecha registro (dd/mm/aaaa): ");
        try {
            Usuario u= new Usuario(nombre,email, numeroSocio, fecharegistro);
            gestor.registrarUsuario(u);
            System.out.println("Usuario correctamente registrado.");
        } catch (UsuarioInvalidoException uie) {
            System.out.println("Error: "+ uie.getMessage());
        }catch (UsuarioRepetidoException ure){
            System.out.println("Error: "+ ure.getMessage());
        }
    }

        private static void realizarPrestamo(GestorBiblioteca gestor){
            System.out.println("Realizar prestamo de libro");
            String codigoLibro= leerTextoNoVacio("Codigo libro (LIB0001): ");
            String titulo= leerTextoNoVacio("Titulo: ");
            String numeroSocio= leerTextoNoVacio("Numero de socio (SOC00001): ");
            LocalDate fechaPrestamo = leerFecha("Fecha de prestamo (dd/mm/aaaa): ");
            Usuario u=gestor.buscarUsuario(numeroSocio);
            if (u==null){
                System.out.println("Error: No existe un usuario con ese numero de socio");
                return;
            }
            try {
                Prestamo p= gestor.realizarPrestamo(codigoLibro, titulo, fechaPrestamo, u);
                System.out.println("Prestamo realizado");
                System.out.println("Devolucion prevista: " + p.getFechaDevolucionPrevista());
            }catch (PrestamoInvalidoException pie){
                System.out.println("Error: " + pie.getMessage());
            }catch (UsuarioSancionadoException use){
                System.out.println("Error: " + use.getMessage());
            }catch (LibroNoDisponibleException lnde) {
                System.out.println("Error: " + lnde.getMessage());
            }catch (Exception e){
                System.out.println("Error inesperado");
            }
        }

        private static void devolverLibro(GestorBiblioteca gestor){
            System.out.println("Devolver libro");
            String codigoLibro= leerTextoNoVacio("Codigo libro: ");
            LocalDate fechaDevolucion= leerFecha("Fecha devolucion (dd/mm/aaaa):");
            try {
                boolean devuelto= gestor.devolverLibro(codigoLibro, fechaDevolucion);
                if (!devuelto) {
                    System.out.println("No se ha podido devolver (no existe prestamos acitov con ese codigo)");
                    return;
                }
                Prestamo p=buscarPrestamoPorCodigo(gestor, codigoLibro);
                if (p!=null){
                    int diasRetraso = p.calcularDiasRetraso();
                    if(diasRetraso >0){
                        System.out.println("Devolucion registrada con " + diasRetraso + " dias de retraso" );
                        System.out.println("Usuario sancionado por " + diasRetraso + " dias ");
                    } else {
                        System.out.println("Devolucion registrada sin retraso");
                    }
                    } else{
                        System.out.println("Devolucion registrada");
                    }
                } catch (PrestamoInvalidoException pie){
                System.out.println("Error: " + pie.getMessage());
            }
        }

        private static void consultarEstadoUsuario(GestorBiblioteca gestor){
            System.out.println("Consultar estado de usuario");
            String numeroSocio= leerTextoNoVacio("Numero de socio: ");
            Usuario u= gestor.buscarUsuario(numeroSocio);
            if (u==null){
                System.out.println("No existe un usuario con ese numero de socio");
                return;
            }
            System.out.println(u);
        }

        private static void mostrarPrestamosActivos(GestorBiblioteca gestor){
            System.out.println("Prestamos activos");
             Prestamo[] prestamos= gestor.getPrestamos();
             boolean hay=false;
             for (Prestamo p: prestamos){
                 if(p!=null &&!p.estaDevuelto()){
                     System.out.println(p);
                     hay=true;
                 }
            }
             if (!hay){
                 System.out.println("No hay prestamos activos");
             }
        }

        private static void mostrarUsuariosSancionados(GestorBiblioteca gestor){
            System.out.println("Usuarios sancionados");
                Usuario[] usuarios=gestor.getUsuarios();
                boolean hay=false;
                for (Usuario u:usuarios){
                    if (u!=null &&u.estaSancionado()){
                        System.out.println(u);
                        hay=true;
                    }
                }
                if (!hay){
                    System.out.println("No hay usuarios sanciondos");
                }
        }
        private static void actualizarSanciones(GestorBiblioteca gestor){
            System.out.println("Actualizar Sanciones");
        }


        private static int leerEntero(String mensaje, int min, int max) {
            while (true) {
                System.out.println(mensaje);
                String linea = in.nextLine().trim();
                try {
                    int n = Integer.parseInt(linea);
                    if (n < min || n > max) {
                        System.out.println("Introduce un numero entre el " + min + " y " + max);
                        continue;
                    }
                    return n;
                } catch (NumberFormatException nfe) {
                    System.out.println("Tienes que escribir un numero");
                }
            }
        }

        private static String leerTextoNoVacio(String mensaje){
        while (true){
            System.out.println(mensaje);
            String texto=in.nextLine();
            if (texto !=null && !texto.trim().isEmpty()){
                return texto.trim();
            }
            System.out.println("No puede estar vacio");
        }
    }

        private static LocalDate leerFecha(String mensaje){
        while (true){
            System.out.println(mensaje);
            String texto= in.nextLine().trim();
            try {
                return LocalDate.parse(texto, formato_Fecha);
            }catch (DateTimeException dte){
                System.out.println("Fecha invalida. Usa el formato correcto");
            }
        }
    }

        private static Prestamo buscarPrestamoPorCodigo(GestorBiblioteca gestor, String codigoLibro){
        Prestamo [] prestamos= gestor.getPrestamos();
        Prestamo encontrado=null;
        for (Prestamo p:prestamos){
            if (p !=null && p.getCodigoLibro().equals(codigoLibro)){
                encontrado=p;
            }
        }
        return encontrado;
    }
}




