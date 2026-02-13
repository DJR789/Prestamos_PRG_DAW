package Prestamos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner in= new Scanner(System.in);
    private static final DateTimeFormatter formato_Fecha= DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    private static int leerEntero(String mensaje, int min, int max){
        while (true){
            System.out.println(mensaje);
            String linea= in.nextLine().trim();
            try {
                int n= Integer.parseInt(linea);
                if (n<min || n>max){
                    System.out.println("Introduce un numero entre el " + min + " y " + max);
                    continue;
                }
                return  n;
            } catch (NumberFormatException nfe){
                System.out.println("Tienes que escribir un numero");
            }
        }
    }

    private static void registrarNuevoUsuario(GestorBiblioteca gestor){

    }

    public static void main(String[] args) {
        GestorBiblioteca gestor= new GestorBiblioteca();
        int opcion;
        do {
            mostrarMenu();
            opcion= leerEntero("Escribe tu opcion: ", 1 , 8);

            switch (opcion){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    System.out.println("Has salido del programa correctamente");
            }

        }while (opcion!=8);



    }
}


