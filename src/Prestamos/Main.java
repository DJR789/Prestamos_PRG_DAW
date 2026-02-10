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
            System.out.println(u);
            u.sancionar(10,LocalDate.now());
            System.out.println(u);
            u.lenvantarSancion();
            System.out.println(u);
        } catch (UsuarioInvalidoException e) {
            System.out.println(e.getMessage());
        }

    }
}


