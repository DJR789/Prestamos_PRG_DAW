package Prestamos;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            Usuario u = new Usuario(
                    "Juan",
                    "juan@mail.com",
                    "SOC00123",
                    LocalDate.now()
            );
        } catch (UsuarioInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }
}


