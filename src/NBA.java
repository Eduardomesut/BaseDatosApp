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
            num = Integer.parseInt(sc.nextLine());
            if (num != 4){
                if (num == 1){
                    consultaJugadores(sc);
                }

            }

        }while (num != 4);
    }
    public static void consultaJugadores (Scanner sc){
        //ESTO ES PRIEBA HACER LA BUSQUEDA COMPLEJA
        String estado;
        System.out.println("------COLSULTA JUGADORES-------");
        System.out.print("Selecciona el estado para buscar jugadores: ");
        estado = sc.nextLine();

        String sql = "SELECT Nombre, Procedencia FROM jugadores where Procedencia = ?;";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NBA", "root", ""); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, estado);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                System.out.println("------------------------------------------------------");
                System.out.println(rs.getString(1) + "---" + rs.getString(2));
            }

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }
        //Falta poner los finally

    }
}
