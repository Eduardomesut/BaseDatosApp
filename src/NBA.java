import java.sql.*;
import java.util.Scanner;

public class NBA {

    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {

            System.out.println("Error");
            System.exit(1);
        }
        menu(sc);
    }

    public static void menu (Scanner sc){
        int num;

        do {
            System.out.println("-----------MENU NBA-------------");
            System.out.println("Pulsa 1 para Consulta de jugadores de de NBA");
            System.out.println("Pulsa 2 para Detalle de un jugador");
            System.out.println("Pulsa 3 para Número de victorias y derrotas para un equipo en una temporada");
            System.out.println("Pulsa 4 para salir");
            num = Integer.parseInt(sc.nextLine());
            if (num != 4){
                if (num == 1){
                    consultaJugadores(sc);
                }

            }

        }while (num != 4);
    }
    public static void consultaJugadores (Scanner sc){
        String nombre;
        String estado;
        String altura;
        String peso;
        String posicion;
        String nombre_equipo;

        System.out.println("------COLSULTA JUGADORES-------");
        System.out.print("Nombre del jugador: ");
        nombre = sc.nextLine();
        System.out.print("Selecciona el estado para buscar jugadores: ");
        estado = sc.nextLine();
        System.out.print("Altura en pies del jugador: ");
        altura = sc.nextLine();
        System.out.print("Peso en libras del jugador: ");
        peso = sc.nextLine();
        System.out.print("Posición del jugador: ");
        posicion = sc.nextLine();
        System.out.println("Nombre del equipo del jugador: ");
        nombre_equipo = sc.nextLine();
        filtro (nombre,estado, altura, peso, posicion,nombre_equipo);

    }

    public static void filtro (String nombre, String estado, String altura, String peso, String posicion,String nombre_equipo){
        int c = 0;
        String sql = "SELECT Nombre, Procedencia, Altura, Peso, Posicion, Nombre_equipo FROM jugadores where 1 = 1 ";
        if (nombre != null && !nombre.equals("")){
            sql = sql + " AND Nombre LIKE ? ";
        }
        if (estado != null && !estado.equals("")){
            sql = sql + " AND Procedencia LIKE ? ";
        }
        if (altura != null && !altura.equals("")){
            sql = sql + " AND Altura = ? ";
        }
        if (peso != null && !peso.equals("")){
            sql = sql + " AND Peso = ? ";
        }
        if (posicion != null && !posicion.equals("")){
            sql = sql + " AND Posicion LIKE ? ";
        }
        if (nombre_equipo != null && !nombre_equipo.equals("")){
            sql = sql + " AND Nombre_equipo LIKE ? ";
        }

        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NBA", "root", ""); PreparedStatement stm = con.prepareStatement(sql)) {
            if (nombre != null && !nombre.equals("")){
                c++;
                stm.setString(c, nombre+ "%");
            }
            if (estado != null && !estado.equals("")){
                c++;
                stm.setString(c,estado+ "%");
            }
            if (altura != null && !altura.equals("")){
                c++;
                stm.setString(c,altura);
            }
            if (peso != null && !peso.equals("")){
                c++;
                stm.setString(c,peso);
            }
            if (posicion != null && !posicion.equals("")){
                c++;
                stm.setString(c,posicion+ "%");
            }
            if (nombre_equipo != null && !nombre_equipo.equals("")){
                c++;
                stm.setString(c,nombre_equipo + "%");
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                System.out.println("------------------------------------------------------");
                System.out.println(rs.getString(1) + "---" + rs.getString(2) + "---" + rs.getString(3)+ "---" + rs.getString(4)+ "---" + rs.getString(5)+ "---" + rs.getString(6));
            }

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el Resulset");
                }
            }
        }

    }

}
