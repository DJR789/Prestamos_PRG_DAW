package Prestamos;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Usuario u = null;
        try {
            u = new Usuario(
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
        try {
            Prestamo p1=new Prestamo("LIB0002",u,"Principito", LocalDate.of(2025,05,12));
            Prestamo p2=new Prestamo("LIB0002",u,"", LocalDate.now());
            System.out.println(p1);
            System.out.println(p2);
        } catch (PrestamoInvalidoException | UsuarioInvalidoException j) {
            System.out.println(j.getMessage());
        }

    }
}


