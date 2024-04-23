
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBA {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {

            System.out.println("Error");
            System.exit(1);
        }
        menu(sc);
    }

    public static void menu(Scanner sc) {
        int num;

        do {
            System.out.println("-----------MENU NBA-------------");
            System.out.println("Pulsa 1 para Consulta de jugadores de de NBA");
            System.out.println("Pulsa 2 para Detalle de un jugador");
            System.out.println("Pulsa 3 para Número de victorias y derrotas para un equipo en una temporada");
            System.out.println("Pulsa 4 consulta de jugadores de de NBA (código, nombre y equipo)");
            System.out.println("Pulsa 5 para salir");
            num = Integer.parseInt(sc.nextLine());
            if (num != 5) {
                if (num == 1) {
                    consultaJugadores(sc);
                } else if (num == 2) {
                    detallesJugador(sc);
                } else if (num == 3) {
                    estadisticasEquipo(sc);
                } else if (num == 4) {
                    consultaJugadorEquipo(sc);
                }

            }

        } while (num != 5);
    }

    public static void consultaJugadores(Scanner sc) {
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
        filtro(nombre, estado, altura, peso, posicion, nombre_equipo);

    }

    public static void filtro(String nombre, String estado, String altura, String peso, String posicion, String nombre_equipo) {
        int c = 0;
        String sql = "SELECT Nombre, Procedencia, Altura, Peso, Posicion, Nombre_equipo FROM jugadores where 1 = 1 ";
        if (nombre != null && !nombre.equals("")) {
            sql = sql + " AND Nombre LIKE ? ";
        }
        if (estado != null && !estado.equals("")) {
            sql = sql + " AND Procedencia LIKE ? ";
        }
        if (altura != null && !altura.equals("")) {
            sql = sql + " AND Altura = ? ";
        }
        if (peso != null && !peso.equals("")) {
            sql = sql + " AND Peso = ? ";
        }
        if (posicion != null && !posicion.equals("")) {
            sql = sql + " AND Posicion LIKE ? ";
        }
        if (nombre_equipo != null && !nombre_equipo.equals("")) {
            sql = sql + " AND Nombre_equipo LIKE ? ";
        }

        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NBA", "root", ""); PreparedStatement stm = con.prepareStatement(sql)) {
            if (nombre != null && !nombre.equals("")) {
                c++;
                stm.setString(c, nombre + "%");
            }
            if (estado != null && !estado.equals("")) {
                c++;
                stm.setString(c, estado + "%");
            }
            if (altura != null && !altura.equals("")) {
                c++;
                stm.setString(c, altura);
            }
            if (peso != null && !peso.equals("")) {
                c++;
                stm.setString(c, peso);
            }
            if (posicion != null && !posicion.equals("")) {
                c++;
                stm.setString(c, posicion + "%");
            }
            if (nombre_equipo != null && !nombre_equipo.equals("")) {
                c++;
                stm.setString(c, nombre_equipo + "%");
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                System.out.println("------------------------------------------------------");
                System.out.println(rs.getString(1) + "---" + rs.getString(2) + "---" + rs.getString(3) + "---" + rs.getString(4) + "---" + rs.getString(5) + "---" + rs.getString(6));
            }

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el Resulset");
                }
            }
        }

    }

    public static void detallesJugador(Scanner sc) {
        int codigo;
        System.out.println("------------ESTADISTICAS DE JUGADORES-------------");
        System.out.print("Selecciona el código del jugador: ");
        codigo = Integer.parseInt(sc.nextLine());
        String jug = "SELECT Nombre, Procedencia, Altura, Peso, Posicion, Nombre_equipo FROM jugadores where codigo = ?; ";
        String jugStats = "SELECT temporada,Puntos_por_partido, Asistencias_por_partido, Tapones_por_partido, Rebotes_por_partido FROM estadisticas WHERE jugador = ? ORDER BY temporada DESC; ";
        ResultSet rsj = null;
        ResultSet rss = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NBA", "root", ""); PreparedStatement stm = con.prepareStatement(jug); PreparedStatement stmS = con.prepareStatement(jugStats)) {
            stm.setInt(1, codigo);
            rsj = stm.executeQuery();
            stmS.setInt(1, codigo);

            rss = stmS.executeQuery();
            System.out.println("DATOS JUGADOR");
            System.out.println("-------------------");
            while (rsj.next()) {
                System.out.println(rsj.getString(1) + "----" + rsj.getString(2) + "----" + rsj.getString(3) + "----" + rsj.getString(4) + "----" + rsj.getString(5) + "----" + rsj.getString(6));

            }
            System.out.println("ESTADISTICAS POR TEMPORADA");
            System.out.println("-----------------------");
            System.out.println("TEMP-----PTS-----AST----TPS---RBS----");
            while (rss.next()) {
                System.out.println(rss.getString(1) + "----" + rss.getString(2) + "----" + rss.getString(3) + "----" + rss.getString(4) + "----" + rss.getString(5));
            }

        } catch (Exception e) {

            System.out.println("Error en la lectura de la base de datos");
        } finally {
            if (rsj != null) {
                try {
                    rsj.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NBA.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (rss != null) {
                try {
                    rss.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NBA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public static void estadisticasEquipo(Scanner sc) {

        System.out.println("-------------VICTORIAS Y DERROTAS POR EQUIPO--------------");
        String equipo;
        String temporada;
        System.out.print("Elecciona el nombre del equipo: ");
        equipo = sc.nextLine();
        System.out.print("Elecciona la temporada que consultar (YY/YY): ");
        temporada = sc.nextLine();

        int victorias = win(equipo, temporada);
        int derrotas = loss(equipo, temporada);
        System.out.println("El equipo: " + equipo + " ha ganado " + victorias + " partidos y perdido " + derrotas + " partidos en la temporada " + temporada);

    }

    public static int win(String equipo, String temporada) {
        int victorias = 0;

        String vicL = "SELECT COUNT(*) FROM partidos WHERE ((puntos_local > puntos_visitante AND equipo_local = ?) OR (puntos_local < puntos_visitante AND equipo_visitante = ?)) AND temporada = ?;";

        ResultSet rsj = null;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NBA", "root", ""); PreparedStatement stm = con.prepareStatement(vicL);) {

            stm.setString(1, equipo);
            stm.setString(2, equipo);
            stm.setString(3, temporada);
            rsj = stm.executeQuery();
            while (rsj.next()) {
                victorias = rsj.getInt(1);

            }

        } catch (Exception e) {
            System.out.println("Error en la lectura de la base de datos");
        } finally {
            if (rsj != null) {
                try {
                    rsj.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NBA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return victorias;
    }

    public static int loss(String equipo, String temporada) {
        int derrotas = 0;
        String vicL = "SELECT COUNT(*) FROM partidos WHERE ((puntos_local < puntos_visitante AND equipo_local = ?) OR (puntos_local > puntos_visitante AND equipo_visitante = ?)) AND temporada = ?;";

        ResultSet rsj = null;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NBA", "root", ""); PreparedStatement stm = con.prepareStatement(vicL);) {

            stm.setString(1, equipo);
            stm.setString(2, equipo);
            stm.setString(3, temporada);
            rsj = stm.executeQuery();
            while (rsj.next()) {
                derrotas = rsj.getInt(1);

            }

        } catch (Exception e) {
            System.out.println("Error en la lectura de la base de datos");
        } finally {
            if (rsj != null) {
                try {
                    rsj.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NBA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return derrotas;
    }

    public static void consultaJugadorEquipo(Scanner sc) {
        System.out.println("--------CONSULTA DE JUGADORES-----------");
        int c = 0;
        String nombreEquipo;
        String ciudad;
        String conferencia;
        String division;
        System.out.print("Nombre del equipo: ");
        nombreEquipo = sc.nextLine();
        System.out.print("Nombre ciudad: ");
        ciudad = sc.nextLine();
        System.out.print("Conferencia: ");
        conferencia = sc.nextLine();
        System.out.print("División: ");
        division = sc.nextLine();
        String sql = "SELECT j.codigo, j.Nombre,j.Nombre_equipo FROM jugadores as j JOIN equipos as e ON j.Nombre_equipo = e.nombre WHERE 1 = 1 ";
        if (nombreEquipo != null && !nombreEquipo.equals("")) {
            sql = sql + " AND e.Nombre LIKE ? ";
        }
        if (ciudad != null && !ciudad.equals("")) {
            sql = sql + " AND e.Ciudad LIKE ? ";
        }
        if (conferencia != null && !conferencia.equals("")) {
            sql = sql + " AND e.Conferencia LIKE ? ";
        }
        if (division != null && !division.equals("")) {
            sql = sql + " AND e.Division LIKE ? ";
        }
        ResultSet rse = null;
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NBA", "root", "");
        PreparedStatement pre = con.prepareStatement(sql)){
            if (nombreEquipo != null && !nombreEquipo.equals("")) {
                c++;
                pre.setString(c, nombreEquipo+ "%");
            }
            if (ciudad != null && !ciudad.equals("")) {
                c++;
                pre.setString(c, ciudad+ "%");
            }
            if (conferencia != null && !conferencia.equals("")) {
                c++;
                pre.setString(c, conferencia+ "%");
            }
            if (division != null && !division.equals("")) {
                c++;
                pre.setString(c, division+ "%");
            }
            rse = pre.executeQuery();
            while (rse.next()){

                System.out.println(rse.getString(1) + "----" + rse.getString(2) + "----" + rse.getString(3));

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rse != null){
                try {
                    rse.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el ResulSet");
                }
            }
        }

    }

}
