package Prestamos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner in= new Scanner(System.in);
    private static final DateTimeFormatter formato_Fecha= DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        GestorBiblioteca gestor= new GestorBiblioteca();
        int opcion=0;
        while (opcion!=8){
            mostrarMenu();
            try{
                System.out.println("Escribe tu opcion: ");
                opcion=Integer.parseInt(in.nextLine());

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
                    System.out.println("Debes introducir un numero del 1 al 8");

                }
            }catch (NumberFormatException nfe){
                System.out.println("Debes introducir un numero del 1 al 8");
            }
        }
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
        System.out.println("Nombre: ");
        String nombre=in.nextLine();
        System.out.println("Email: ");
        String email=in.nextLine();
        System.out.println("Numero de socio (SOC00001): ");
        String numeroSocio=in.nextLine();
        System.out.println("Fecha registro (dd/mm/aaaa): ");
        String fecharegistro =in.nextLine();
        try {
            Usuario u= new Usuario(nombre,email, numeroSocio, LocalDate.parse(fecharegistro,formato_Fecha));
            gestor.registrarUsuario(u);
            System.out.println("Usuario correctamente registrado.");
        } catch (UsuarioInvalidoException uie) {
            System.out.println("Error: "+ uie.getMessage());
        }catch (UsuarioRepetidoException ure){
            System.out.println("Error: "+ ure.getMessage());
        }catch (Exception e){
            System.out.println("Error de fecha, usa el formato correcto");
        }
    }

    private static void realizarPrestamo(GestorBiblioteca gestor) {
        System.out.println("Realizar préstamo de libro ");
        System.out.println("Código libro (LIB0001): ");
        String codigoLibro = in.nextLine();
        System.out.println("Titulo: ");
        String titulo = in.nextLine();;
        System.out.println("Numero de socio: ");
        String numeroSocio= in.nextLine();

        Usuario u = gestor.buscarUsuario(numeroSocio);
        if (u == null) {
            System.out.println("Error: No existe un usuario con ese número de socio");
            return;
        }

        System.out.println("Fecha de préstamo (dd/mm/aaaa): ");
        String fechaPrestamo= in.nextLine();

        try {
            Prestamo p = gestor.realizarPrestamo(codigoLibro, titulo, LocalDate.parse(fechaPrestamo,formato_Fecha), u);
            System.out.println("Préstamo realizado.");
            System.out.println("Devolución prevista: " + p.getFechaDevolucionPrevista());
        } catch (PrestamoInvalidoException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (UsuarioSancionadoException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (LibroNoDisponibleException e) {
            System.out.println("ERROR: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error de fecha, usa el formato correcto");
        }
    }

    private static void devolverLibro(GestorBiblioteca gestor){
        System.out.println("Devolver libro");
        System.out.println("Código libro (LIB0001): ");
        String codigoLibro = in.nextLine();
        System.out.println("Fecha devolución (dd/mm/aaaa): ");
        String fechaDevolucion = in.nextLine();
        try {
            boolean devuelto= gestor.devolverLibro(codigoLibro, LocalDate.parse(fechaDevolucion,formato_Fecha));
            if (!devuelto) {
                System.out.println("No se ha podido devolver (no existen prestamos activos con ese codigo)");
                return;
            }
            Prestamo p=buscarPrestamoPorCodigo(gestor, codigoLibro);
            if (p!=null){
                int diasRetraso = p.calcularDiasRetraso();
                if(diasRetraso >0){
                    System.out.println("Devolucion registrada con " + diasRetraso + " dias de retraso" );
                    System.out.println("Usuario sancionado por " + diasRetraso + " dias (hasta el " +  p.getSocio().getFechaFinSancion() +")");
                } else {
                    System.out.println("Devolucion registrada sin retraso");
                }
            } else{
                System.out.println("Devolucion registrada");
            }
        } catch (PrestamoInvalidoException pie){
            System.out.println("Error: " + pie.getMessage());
        }catch (Exception e){
            System.out.println("Error de fecha, usa el formato correcto");
        }
    }

    private static void consultarEstadoUsuario(GestorBiblioteca gestor){
        System.out.println("Consultar estado de usuario");
        System.out.println("Numero de socio: ");
        String numeroSocio=in.nextLine();
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
        Usuario[] usuarios = gestor.getUsuarios();
        int levantadas = 0;
        for (Usuario u : usuarios) {
            if (u == null) continue;
            LocalDate fin = u.getFechaFinSancion();
            if (fin != null && fin.isBefore(LocalDate.now())) {
                u.lenvantarSancion();
                levantadas++;
            }
        }
        System.out.println("Sanciones actualizadas. Sanciones levantadas: " + levantadas);
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