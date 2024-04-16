/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;

/**
 *
 * @author dev
 */
public class Ejercicio1 {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {

            System.out.println("Error");
            System.exit(1);
        }

        Scanner sc = new Scanner(System.in);

        menu(sc);

    }

    public static void menu(Scanner sc) {

        int num;
        do {
            System.out.println("Pulsa 1 para insertar datos");
            System.out.println("Pulsa 2 para hacer diferentes consultas");
            System.out.println("Pulsa 3 para salir");
            num = Integer.parseInt(sc.nextLine());
            if (num != 3) {
                if (num == 1) {
                    insertarDatos(sc);
                } else if (true) {
                    consultas(sc);
                } else {
                    System.out.println("Introduce un número correcto");
                }

            }

        } while (num != 3);

    }

    public static void insertarDatos(Scanner sc) {
        int num;
        do {
            System.out.println("Pulsa 1 para insertar un Empleado");
            System.out.println("Pulsa 2 para insertar un Vehiculo");
            System.out.println("Pulsa 3 para insertar un proyecto");
            System.out.println("Pulsa 4 para asignar empleado a proyecto");
            System.out.println("Pulsa 5 para salir");
            num = Integer.parseInt(sc.nextLine());
            if (num != 5) {
                if (num == 1) {
                    insertarDatosEmpleado(sc);
                } else if (num == 2) {
                    insertarDatosVehiculo(sc);

                } else if (num == 3) {
                    insertarProyecto(sc);

                } else if (num == 4) {
                    insertarAsignacion(sc);

                } else {

                    System.out.println("Introduce un número correcto");
                }

            }

        } while (num != 5);

    }

    public static void insertarDatosEmpleado(Scanner sc) {
        System.out.println("---------INSERTAR EMPLEADO-------------");

        int id;
        String nombre;
        String dni;
        int jefe;

        String sql = "INSERT INTO EMPLEADO VALUES (?, ?, ?, ?);";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(sql);) {
            System.out.print("ID: ");
            id = Integer.parseInt(sc.nextLine());
            System.out.print("Nombre: ");
            nombre = sc.nextLine();
            System.out.print("DNI: ");
            dni = sc.nextLine();
            System.out.print("Jefe:");
            jefe = Integer.parseInt(sc.nextLine());

            stm.setInt(1, id);
            stm.setString(2, nombre);
            stm.setString(3, dni);
            stm.setInt(4, jefe);
            int r = stm.executeUpdate();
            System.out.println("Registros afectados: " + r);

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }

    }

    public static void insertarDatosVehiculo(Scanner sc) {
        System.out.println("-------INSERTAR VEHICULO------------");
        String matricula;
        String marca;
        String modelo;
        int persona;

        String sql = "INSERT INTO VEHICULO VALUES (?, ?, ?, ?);";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(sql);) {
            System.out.print("Matrícula: ");
            matricula = sc.nextLine();
            System.out.print("Marca: ");
            marca = sc.nextLine();
            System.out.print("Modelo: ");
            modelo = sc.nextLine();
            System.out.print("Persona: ");
            persona = Integer.parseInt(sc.nextLine());

            stm.setString(1, matricula);
            stm.setString(2, marca);
            stm.setString(3, modelo);
            stm.setInt(4, persona);
            int r = stm.executeUpdate();
            System.out.println("Registros afectados: " + r);

        } catch (Exception e) {
            System.out.println("Error " + e.getStackTrace());
        }
    }

    public static void consultas(Scanner sc) {

        int num;
        do {
            System.out.println("Pulsa 1 para Consulta de empleados (junto al nombre de su jefe si tuviera)");
            System.out.println("Pulsa 2 para Detalles de un empleado (con los datos de su jefe, vehículos y proyectos)");
            System.out.println("Pulsa 3 para Consulta de proyectos");
            System.out.println("Pulsa 4 para Detalles de proyecto (con los datos de los empleados asignados al proyecto)");
            System.out.println("Pulsa 5 para Consulta de vehículos (todos, junto al empleado asignado si lo tuviera)");
            System.out.println("Pulsa 6 para Nombre y código de proyecto del proyecto que tenga más personal asignado");
            System.out.println("Pulsa 7 para salir");
            num = Integer.parseInt(sc.nextLine());
            if (num != 7) {
                if (num == 1) {
                    consultaEmpleados(sc);
                } else if (num == 2) {
                    detalleEmpleado(sc);

                } else if (num == 3) {
                    consultaProyectos(sc);

                } else if (num == 4) {
                    detallesProyecto(sc);

                } else if (num == 5) {
                    consultaVehiculos(sc);

                } else if (num == 6) {
                    // masPersonal(sc);

                }
                {

                    System.out.println("Introduce un número correcto");
                }

            }

        } while (num != 7);

    }

    public static void insertarProyecto(Scanner sc) {

        System.out.println("-------INSERTAR PROYECTO------------");
        int codigo;
        String nombre;

        String sql = "INSERT INTO PROYECTO VALUES (?, ?);";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(sql);) {
            System.out.print("Codigo: ");
            codigo = Integer.parseInt(sc.nextLine());
            System.out.print("Nombre: ");
            nombre = sc.nextLine();
            stm.setInt(1, codigo);
            stm.setString(2, nombre);

            int r = stm.executeUpdate();
            System.out.println("Registros afectados: " + r);

        } catch (Exception e) {
            System.out.println("Error " + e.getStackTrace());
        }
    }

    public static void insertarAsignacion(Scanner sc) {

        System.out.println("-------INSERTAR ASIGNACION------------");
        int id;
        int codigo;

        String sql = "INSERT INTO ASIGNACION VALUES (?, ?);";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(sql);) {
            System.out.print("ID para asignar: ");
            id = Integer.parseInt(sc.nextLine());
            System.out.print("Codigo proyecto a asignar: ");
            codigo = Integer.parseInt(sc.nextLine());
            stm.setInt(1, id);
            stm.setInt(2, codigo);

            int r = stm.executeUpdate();
            System.out.println("Registros afectados: " + r);

        } catch (Exception e) {
            System.out.println("Error " + e.getStackTrace());
        }

    }

    public static void consultaEmpleados(Scanner sc) {
        System.out.println("--------LISTA DE EMPLEADOS-----------");

        String sql = "select e.id, e.nombre, e.dni, j.nombre from EMPLEADO AS e LEFT JOIN EMPLEADO AS j ON e.jefe = j.id;";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {
            while (rs.next()) {
                System.out.println("------------------------------------------------------");
                System.out.println(rs.getInt("id") + "---" + rs.getString(2) + "---" + rs.getString("dni") + "---" + rs.getString(4));
            }

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }
    }

    public static void detalleEmpleado(Scanner sc) {

        System.out.println("--------DETALLES DE EMPLEADOS-----------");
        int id;
        System.out.print("Selecciona el id del empleado ");
        id = Integer.parseInt(sc.nextLine());

        String emple = "select id, nombre, dni, jefe from EMPLEADO WHERE id = ?;";
        String vehi = "select v.matricula, v.marca, v.modelo from VEHICULO as v JOIN EMPLEADO as e on v.persona = e.id WHERE v.persona = ?;";
        String proy = "select p.nombre from PROYECTO as p JOIN ASIGNACION as a on p.codigo = a.codigo WHERE a.id = ?;";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(emple); PreparedStatement stmV = con.prepareStatement(vehi);
               PreparedStatement stmP = con.prepareStatement(proy); 
              ) {
            stm.setInt(1, id);                                    
            ResultSet de = stm.executeQuery();
            stmV.setInt(1, id);                                    
            ResultSet rv = stmV.executeQuery();
            stmP.setInt(1,id);
            ResultSet rp = stmP.executeQuery();
            System.out.println("Información empleado:");
            System.out.println("-------------------------------------------------------");

            while (de.next()) {

                System.out.println(de.getInt(1) + "---" + de.getString(2) + "---" + de.getString(3) + "---");
            }
            
            System.out.println("Información vehiculos del empleado:");
            System.out.println("-------------------------------------------------------");
            while(rv.next()){

                System.out.println(rv.getString(1) + "---" + rv.getString(2) + "---" + rv.getString(3) + "---");
                
            }
            System.out.println("Información de los proyectos del empleado: ");
            System.out.println("------------------------------------------------------");
            while (rp.next()){

                System.out.println("Proyecto " + rp.getString(1) + " ");

            }
            System.out.println("--------------------------------------------------------");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }

    }
    
    public static void consultaProyectos(Scanner sc){
        System.out.println("----------LISTA DE PROYECTOS----------");

        String sql = "select codigo, nombre FROM PROYECTO;";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery();) {
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "---" + rs.getString(2));
            }
            System.out.println("-----------------------------------------------------------");

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }

    }

    public static void detallesProyecto(Scanner sc){
        System.out.println("-----------DETALES DE PROYECTO--------------");
        int id;
        System.out.print("Selecciona un id de un proyecto: ");
        id = Integer.parseInt(sc.nextLine());
        String queryPro = "SELECT e.nombre, e.dni from EMPLEADO as e JOIN ASIGNACION as a ON e.id = a.id WHERE a.codigo = ?;";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(queryPro);
              ) {
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            System.out.println("----------EMPLEADOS PROYECTO " + id + "----------");
            while (rs.next()) {

                System.out.println(rs.getString(1) + "---" + rs.getString(2));
            }
            System.out.println("-----------------------------------------------------------");

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }
    }
    public static void consultaVehiculos(Scanner sc){
        System.out.println("----------LISTA DE VEHICULOS----------");
        String sql = "select v.matricula, v.marca, v.modelo, e.nombre FROM VEHICULO AS v LEFT JOIN EMPLEADO AS e ON v.persona = e.id;";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", ""); PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery();) {
            while (rs.next()) {

                System.out.println(rs.getString(1) + "---" + rs.getString(2)+ "---" + rs.getString(3)+ "---" + rs.getString(4));
            }
            System.out.println("-----------------------------------------------------------");

        } catch (Exception e) {

            System.out.println("Errorazo " + e.getClass().getSimpleName() + ": " + e.getCause());
        }

    }
}
