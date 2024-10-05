import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class App {
    //Declaracion de variables usadas en el codigo
    static String nameEmpresa;
    static int idfactura;
    static int idfacturaUser;
    static double preciofactura;
    static String fechaFactura ;
    static String nombreFactura ;
    static String tipoFactura;
    static String refFactura;
    static String fechaString;
    static double precioActivos;
    static String nombreIngreso;
    static String nombreGasto;
    static String tipoIngreso;
    static String tipoGasto;
    static Boolean pasar=false;
    static String url = "jdbc:mysql://localhost:3306/CONAD";
    static String user = "root";
    static String password = "root";
    static int code;
    static String correo;
    static int edad;
    static double ingreso;
    static String refIngreso;
    static double montoCaja;
    static double montoBanco;
    static double montoInventario;
    static double montoCPC;
    static double montoIACP;
    static double montoIALP;
    static double montoOANC;
    static double montoOAC;
    static String refGasto;
    static double montoCPP;
    static double montoPACP;
    static double montoHAP;
    static double montoDPP;
    static double montoProveedores;
    static double montoAcreedores;
    static double montoOPNC;
    static double montoOPC;
    static int opcingreso;
    static int gasto;
    static Date fecha;
    static ResultSet resultado;
    static ResultSet resultado2;
    static ResultSet resultado3;
    static ResultSet resultado4;
    static Statement st;
    static Scanner scn = new Scanner(System.in);
    static String consulta;
    static String
            consulta2;
    static String consulta3;
    static String consulta4;
    static String consulta5;
    private static double montoCapitaSocial;
    private static int identificacionCompra;
    private static int refUser;

    //metodo de bienvenida, primer metodo usado en el codigo
    static void bienvenida() {
        System.out.println();
        System.out.println();
        System.out.println(
                        "                                                                                   ░█████╗░░█████╗░███╗░░██╗░█████╗░██████╗░  \uD83C\uDD63\uD83C\uDD5C\n" +
                        "                                                                                   ██╔══██╗██╔══██╗████╗░██║██╔══██╗██╔══██╗\n" +
                        "                                                                                   ██║░░╚═╝██║░░██║██╔██╗██║███████║██║░░██║\n" +
                        "                                                                                   ██║░░██╗██║░░██║██║╚████║██╔══██║██║░░██║\n" +
                        "                                                                                   ╚█████╔╝╚█████╔╝██║░╚███║██║░░██║██████╔╝\n" +
                        "                                                                                   ░╚════╝░░╚════╝░╚═╝░░╚══╝╚═╝░░╚═╝╚═════╝░");
        System.out.println();
        System.out.println("                                                                               Sistema de gestion y administracion y contabilidad");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }


//metodo que le sigue a bienvenida, segundo metodo llamado en el main
    static void logearOregistrar() {
        System.out.println("Que deseas hacer: \n" +
                "1.- Login \n" +
                "2.- Registrar usuario");
        String opc = scn.next();
        if (opc.equals("1")) {
            loggear();
        } else if (opc.equals("2")) {
            registrar();
        } else {
            System.out.println("Opcion no valida");
            logearOregistrar();
        }
    }

    //metodo para logearte en caso de escoger la opcion 1 en el x2metodo logearOregistrar
    static void loggear() {
        System.out.println("Escribe tu correo electronico");
         correo = scn.next();

        consulta = "select * from users where usuario='" + correo + "'";

        consultaMetodo();
    }

    //metodo para verificar constrasena una vez verificado el usuario
    static void loggearpswd(){
        System.out.println("Escribe tu contraseña");
        String contrasena = scn.next();
        consulta2 = "select * from users where contrasena='" + contrasena + "'";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();

            resultado2 = st.executeQuery(consulta2);

            if (!resultado2.next()) {
                System.out.println("Contrasena incorrecta, por favor vuelve a introducir los datos.");
                pasar=false;
                logearOregistrar();
            } else {
                System.out.println("Bienvenido " + correo);
                menu();

            }

            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
    }


    //metodo para registrarte en caso de escoger la opcion 2 en el metodo logearOregistrar
    static void registrar() {
        System.out.println("Gracias por usar nuestro servicio por primera vez \n" +
                "Ecribe tu correo electronico");
        String newcorreo = scn.next();
        if (!newcorreo.contains("@")) {
            System.out.println("Esscribe un correo electornico valido");
            logearOregistrar();
        }
        System.out.println("Escribe tu contraseña");
        String newcontrasena = scn.next();
        System.out.println("Escribe el nombre de tu empresa");
        String nombreEmpresa = scn.next();
        consulta2 = "INSERT INTO users(usuario, contrasena, fecha_inicio, Empresa) VALUES ('" + newcorreo + "', '" + newcontrasena + "', NOW() , '"+ nombreEmpresa+"')";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();

            //dato interesantem en vez de usar st.executeQuery se usa st,executeUpdate
            int filasAfectadas = st.executeUpdate(consulta2);

            if (filasAfectadas > 0) {
                System.out.println("Registro exitoso. Bienvenido " + newcorreo);
                logearOregistrar();
            } else {
                System.out.println("No se pudo realizar el registro.");
                logearOregistrar();
            }
            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    //metoo para ejecutar consulta en el login, verificar usuario y pasar al metodo loggearpswd
    static void consultaMetodo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();

            resultado = st.executeQuery(consulta);

            if (!resultado.next()) {
                System.out.println("Usuario no encontrado. Por favor, verifica tu correo electrónico.");
                logearOregistrar();
            } else {
                loggearpswd();
            }

            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    static void menu(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(
                        "                                                                                   ░█████╗░░█████╗░███╗░░██╗░█████╗░██████╗░  \uD83C\uDD63\uD83C\uDD5C\n" +
                        "                                                                                   ██╔══██╗██╔══██╗████╗░██║██╔══██╗██╔══██╗\n" +
                        "                                                                                   ██║░░╚═╝██║░░██║██╔██╗██║███████║██║░░██║\n" +
                        "                                                                                   ██║░░██╗██║░░██║██║╚████║██╔══██║██║░░██║\n" +
                        "                                                                                   ╚█████╔╝╚█████╔╝██║░╚███║██║░░██║██████╔╝\n" +
                        "                                                                                   ░╚════╝░░╚════╝░╚═╝░░╚══╝╚═╝░░╚═╝╚═════╝░");
        System.out.println();
        System.out.println("                                                                               Sistema de gestion y administracion y contabilidad");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
     menu2();
    }
    static void menu2(){
        System.out.println("Que deseas hacer \n" +
                "1.- Crear factura electronica \n" +  //METODO: HACER UNA CONSULTA DONDE EL ID_USER SEA IGUAL A CODE, IMPRIMIR TODAS LAS VENTAS Y GASTOS Y APARTIR E AHI HACER LA FACTURA
                "2.- Registro de personas \n" +     //probablemente no se haga, pero debe estar en eldiagrama
                "3.- Registrar ingreso o gasto \n" +
                "4.- Reporte financiero (Balance General) \n" +
                "5.- Reporte financiero (Estado de Resultados) \n" +
                "6.- Salir del programa");
        
        int opc = scn.nextInt();
        switch (opc){
            case 1:
                crearFactura();
                break;
            case 2:
                registrarPersona();
                break;
            case 3:
                registrarIngresoOGasto();
                break;
            case 4:
                balanceGeneral();
                break;
            case 5:
                estadoResultado();
                break;
            case 6:
                System.out.println("Gracias por usar nuestro servicio");
                break;
            default:
                System.out.println("Opcion no valida");
                menu();
                break;
        }
    }

    private static void registrarPersona() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta3 = "select * from users where usuario='" + correo + "'";
            resultado3 = st.executeQuery(consulta3);

            while (resultado3.next()) {
                code = resultado3.getInt("ID");}
            refUser=code;
            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }

        System.out.println("Registraras a: \n" +
                "1. Cliente \n" +
                "2. Proveedor");
        int opc = scn.nextInt();
        if (opc == 1) {

            System.out.println("Escribe el nombre de la persona");
            String nombreReg = scn.next();
            scn.nextLine();
            System.out.println("Escribe el apellido de la persona");
            String apellidoReg = scn.nextLine();

            System.out.println("Escribe el correo de la persona");
            String correoReg = scn.nextLine();


            System.out.println("Escribe el telefono de la persona");
            String telefonoReg = scn.nextLine();


            System.out.println("Escribe la direccion de la persona");
            String direccionReg = scn.nextLine();



            consulta2 = "insert into clientes ( ref_user, nombre, apellido, correo, telefono, direccion) values ('" + refUser + "','" + nombreReg + "','" + apellidoReg + "','" + correoReg + "','" + telefonoReg + "','" + direccionReg + "')";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conexion = DriverManager.getConnection(url, user, password);
                st = conexion.createStatement();

                int filasAfectadas = st.executeUpdate(consulta2);
                st.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Class not found.");
            } catch (SQLException e) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("SQL Error: " + e.getMessage());
            }
            System.out.println();
            System.out.println();
            System.out.println("Se ha añadido correctamente al cliente");
            System.out.println("Deseas volver al menu? 1.Si 2.No");
            int volver = scn.nextInt();
            if (volver == 1) {
                menu();
            } else {
                System.out.println("Gracias por usar nuestro programa");
                System.exit(0);
            }
        }else if (opc == 2) {

            System.out.println("Escribe el nombre deL proveedor");
            String nombreReg = scn.next();
            scn.nextLine();

            System.out.println("Escribe el correo del provedor");
            String correoReg = scn.nextLine();


            System.out.println("Escribe el telefono del proveedor");
            String telefonoReg = scn.nextLine();


            System.out.println("Escribe la direccion del proveedor");
            String direccionReg = scn.nextLine();


            consulta2 = "insert into proveedores ( Ref_user, nombre, correo, telefono, direccion) values ('" + refUser + "','" + nombreReg + "','" + correoReg + "','" + telefonoReg + "','" + direccionReg + "')";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conexion = DriverManager.getConnection(url, user, password);
                st = conexion.createStatement();

                int filasAfectadas = st.executeUpdate(consulta2);
                st.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Class not found.");
            } catch (SQLException e) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("SQL Error: " + e.getMessage());
            }
            System.out.println();
            System.out.println();
            System.out.println("Se ha añadido correctamente al proveedor");
            System.out.println("Deseas volver al menu? 1.Si 2.No");
            int volver = scn.nextInt();
            if (volver == 1) {
                menu();
            } else {
                System.out.println("Gracias por usar nuestro programa");
                System.exit(0);
            }
        }else {
            System.out.println("Opcion no valida");
            menu();
        }


    }

    private static void registrarIngresoOGasto() {
        System.out.println("Que deseas registrar: \n" +
                "1. Ingreso \n" +
                "2. Gasto");
         int opc = scn.nextInt();
         switch (opc){
             case 1:
                 registrarIngreso();
                 break;
             case 2:
                 registrarGasto();
                 break;
             default:
                 System.out.println("Opcion no valida");
                 registrarIngresoOGasto();
                 break;
         }
    }



//Metodo para registrar ingreso vinculandolo con el usuario actual, y separando entre activos corrientes y no corrientes
    static void registrarIngreso(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta3 = "select * from users where usuario='" + correo + "'";
            resultado3 = st.executeQuery(consulta3);

            while (resultado3.next()) {
                code = resultado3.getInt("ID");}
            System.out.println("Escribe el monto del ingreso");
            ingreso=scn.nextDouble();
            System.out.println("Escrbie la fecha del Ingreso en este formato 'YYYY-MM-DD'");
            scn.nextLine();
             fechaString = scn.nextLine();
            System.out.println("Escribe el nombre del ingreso");
            nombreIngreso=scn.nextLine();
            System.out.println("Qué tipo de ingreso fue: \n" +
                    "1.- Caja    2.- Banco \n" +
                    "3.- Inventario    4.- Cuenta por cobrar \n" +
                    "5.- Inversión a corto plazo    6.- Inversión a largo plazo \n" +
                    "7.- Otro activo no corriente    8.- Otro activo corriente \n" +
                    "9.- Capital Social");



            opcingreso=scn.nextInt();

            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }

        switch (opcingreso){
            case 1:
                tipoIngreso="Caja";
                refIngreso="AC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                    consulta5 = "Select* from ingresos where id = '" +code+" '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4= st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");}
                    System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                    System.out.println();




                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }break;
                case 2:
                    tipoIngreso="Banco";
                    refIngreso="AC";
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conexion = DriverManager.getConnection(url, user, password);
                        st = conexion.createStatement();
                        consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                        consulta5 = "Select* from ingresos where id = '" +code+" '";
                        int filasAfectadas = st.executeUpdate(consulta4);
                        resultado4= st.executeQuery(consulta5);
                        while (resultado4.next()) {
                            code = resultado4.getInt("id");}
                        System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                        System.out.println();




                        st.close();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Class not found.");
                    } catch (SQLException e) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("SQL Error: " + e.getMessage());
                    }break;
            case 3:
                tipoIngreso="Inventario";
                refIngreso="AC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                    consulta5 = "Select* from ingresos where id = '" +code+" '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4= st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");}
                    System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                    System.out.println();




                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }break;
            case 4:
                tipoIngreso="Cuenta por cobrar";
                refIngreso="AC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                    consulta5 = "Select* from ingresos where id = '" +code+" '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4= st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");}
                    System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                    System.out.println();




                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }break;
            case 5:
                tipoIngreso="Inversion a corto plazo";
                refIngreso="AC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                    consulta5 = "Select* from ingresos where id = '" +code+" '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4= st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");}
                    System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                    System.out.println();




                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }break;
            case 6:
                tipoIngreso="Ingreso a largo plazo";
                refIngreso="ANC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                    consulta5 = "Select* from ingresos where id = '" +code+" '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4= st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");}
                    System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                    System.out.println();




                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }break;
            case 7:
                tipoIngreso="Otro activo no corriente";
                refIngreso="ANC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                    consulta5 = "Select* from ingresos where id = '" +code+" '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4= st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");}
                    System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                    System.out.println();




                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }break;
            case 8:
                tipoIngreso="Otro activo corriente";
                refIngreso="AC";
                break;
            case 9:
                tipoIngreso="Capital Social";
                refIngreso="Patrimonio";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO ingresos(id_user, precio, fecha, Nombre_Ingreso, Tipo_ingreso, REF) VALUES ('" + code + "', '" + ingreso + "','"+fechaString+"', '"+nombreIngreso+ "','"+tipoIngreso+"','"+refIngreso+ "')";
                    consulta5 = "Select* from ingresos where id = '" +code+" '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4= st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");}
                    System.out.println("Ingreso creado exitosamente, numero de identificacion de compra es: " + code );
                    System.out.println();





                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }break;
            default:
                System.out.println("Opcion no valida");
                menu();

        }

        menu();
    }

    //Metodo para registrar gasto vinculandolo con el usuario actual, y separando entre pasivos corrientes y no corrientes

    static void registrarGasto(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta3 = "select * from users where usuario='" + correo + "'";
            resultado3 = st.executeQuery(consulta3);

            while (resultado3.next()) {
                code = resultado3.getInt("ID");}
            System.out.println("Escribe el monto del Gasto");
            ingreso=scn.nextDouble();
            System.out.println("Escrbie la fecha del Gasto en este formato 'YYYY-MM-DD'");
            scn.nextLine();
            fechaString = scn.nextLine();
            System.out.println("Escribe el nombre del Gasto");
            nombreGasto=scn.nextLine();
            System.out.println("Qué tipo de Gasto fue: \n" +
                    "1.- Cuenta por pagar    2.- Prestamo a corto plazo \n" +
                    "3.- Hipoteca a pagar    4.- Documentos por pagar \n" +
                    "5.- Proveedores    6.- Acreedores diversos \n" +
                    "7.- Otro pasivo no corriente    8.- Otro pasivo corriente");



            opcingreso=scn.nextInt();

            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
        switch (opcingreso) {
            case 1:
                tipoGasto = "Cuenta por pagar";
                refGasto = "PC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            case 2:
                tipoGasto="Perstamo a corto plazo";
                refGasto="PC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            case 3:
                tipoGasto="Hipoteca a Pagar";
                refGasto="PNC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            case 4:
                tipoGasto="Documentos por pagar";
                refGasto="PC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            case 5:
                tipoGasto="Proveedores";
                refGasto="PC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            case 6:
                tipoGasto="Acreedores Diversos";
                refGasto="PC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            case 7:
                tipoGasto="Otro pasivo no corriente";
                refGasto="PNC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            case 8:
                tipoGasto="Otro pasivo corriente";
                refGasto="PC";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(url, user, password);
                    st = conexion.createStatement();
                    consulta4 = "INSERT INTO gastos(id_user, precio, fecha, Nombre_Gasto, Tipo_Gasto, REF) VALUES ('" + code + "', '" + ingreso + "','" + fechaString + "', '" + nombreGasto + "','" + tipoGasto + "','" + refGasto + "')";
                    consulta5 = "Select* from gastos where id = '" + code + " '";
                    int filasAfectadas = st.executeUpdate(consulta4);
                    resultado4 = st.executeQuery(consulta5);
                    while (resultado4.next()) {
                        code = resultado4.getInt("id");
                    }
                    System.out.println("Ingreso creado exitosamente, numero de identificacion del gasto es: " + code);
                    System.out.println();


                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class not found.");
                } catch (SQLException e) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("SQL Error: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Opcion no valida");
                menu();
        }
        menu();
    }

    private static void estadoResultado() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta3 = "select * from users where usuario='" + correo + "'";
            resultado3 = st.executeQuery(consulta3);

            while (resultado3.next()) {
                code = resultado3.getInt("ID");
                nameEmpresa = resultado3.getString("Empresa");
            }

        System.out.println("Conoces el total de tus ventas, el costo de ventas, y los gastos administrativos? 1.Si , 2. No");
        int opcion=scn.nextInt();
        if (opcion==1) {
            System.out.println("Escribe tu total de ventas");
            double totalVentas=scn.nextDouble();
            System.out.println("Escribe el costo de ventas");
            double costoVentas=scn.nextDouble();
            double utilidad=totalVentas-costoVentas;
            System.out.println("Escribe el total de tus gastos administrativos");
            double gastosAdministrativos=scn.nextDouble();
            System.out.println("Escribe las gastos por intereses, si no tienes escribe 0");
            double gastosIntereses=scn.nextDouble();
            double utilidadOperativa= utilidad-gastosAdministrativos;
            double UAI= utilidadOperativa-gastosIntereses;


            System.out.println("                                                                     "+nameEmpresa);
            System.out.println("                                                              Estado de Resultados 2024");
            System.out.println("                                                       Del 1ro de enero al 31 de diciembre");
            System.out.println("Ventas "+"                                                                                                                           "+totalVentas);
            System.out.println("Costo de ventas "+"                                                                                                                  "+costoVentas);
            System.out.println();
            System.out.println("Gastos administrativos "+"                                                                                                           "+gastosAdministrativos);
            System.out.println();
            System.out.println("Utilidad Operativa "+"                                                                                                               "+ utilidadOperativa);
            System.out.println("Utilidad antes de impuestos "+"                                                                                                      "+ UAI);
            System.out.println("Utilidad neta "+"                                                                                                                    "+ (UAI*0.25 ));

        }else if (opcion==2){
            System.out.println("Estamos trabajando para mejorar el programa, en futuras actualizaciones se podra realizar sin problema");
            menu();
        }else {
            System.out.println("Opcion no valida");
            menu();
        }
            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
        System.out.println();
        System.out.println();
        System.out.println("Deseas volver al menu? 1.Si 2.No");
        int volver = scn.nextInt();
        if (volver == 1) {
            menu();
        } else {
            System.out.println("Gracias por usar nuestro programa");
            System.exit(0);
        }
    }


    private static void balanceGeneral() {
        //parte para los activos
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta3 = "select * from users where usuario='" + correo + "'";
            resultado3 = st.executeQuery(consulta3);

            while (resultado3.next()) {
                code = resultado3.getInt("ID");
                  nameEmpresa = resultado3.getString("Empresa");
            }


            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta="select* from ingresos where id_user= '"+code+"'";
            System.out.println();
            resultado3 = st.executeQuery(consulta);

            while (resultado3.next()) {
               String tipoActivo = resultado3.getString("Tipo_ingreso");
                precioActivos = resultado3.getDouble("precio");

                switch (tipoActivo) {
                   case "Caja":
                       montoCaja+=precioActivos;
                       break;
                    case "Banco":
                        montoBanco+=precioActivos;
                        break;
                    case "Cuenta por cobrar":
                        montoCPC+=precioActivos;
                        break;
                    case "Inventario":
                        montoInventario+=precioActivos;
                        break;
                    case "Inversion a corto plazo":
                        montoIACP+=precioActivos;
                        break;
                    case "Inversion a largo plazo":
                        montoIALP+=precioActivos;
                        break;
                    case "Otro activo no corriente":
                        montoOANC+=precioActivos;
                        break;
                    case "Otro activo corriente":
                        montoOAC+=precioActivos;
                        break;
                    case "Capital Social":
                        montoCapitaSocial+=precioActivos;
                        System.out.println("motno capital social 1 :" + montoCapitaSocial);
                        System.out.println("monto caja 1 : " +montoCaja );
                        montoCaja+=precioActivos;
                        System.out.println("Monto caja despiues: "+ montoCaja);
                        break;

               }
            }

            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta="select* from gastos where id_user= '"+code+"'";
            resultado3 = st.executeQuery(consulta);

            while (resultado3.next()) {
                String tipoPasivo = resultado3.getString("Tipo_Gasto");
                double precio = resultado3.getDouble("precio");


                switch (tipoPasivo) {
                    case"Cuenta por pagar":
                        montoCPP+=precio;
                        break;
                    case"Hipoteca a pagar":
                        montoHAP+=precio;
                        break;
                    case"Prestamo a corto plazo":
                        montoPACP+=precio;
                        break;
                    case"Documentos por pagar":
                        montoDPP+=precio;
                        break;
                    case"Acreedores diversos":
                        montoAcreedores+=precio;
                        break;
                    case "Proveedores":
                        montoProveedores+=precio;
                        montoInventario+=montoProveedores;
                        break;
                    case "Otro pasivo no corriente":
                        montoOPNC+=precio;
                        break;
                    case "Otro pasivo corriente":
                        montoOPC+=precio;
                        break;
                }
            }

            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }

        //este seria la estructura del balance general

        System.out.println("                                                                     "+nameEmpresa);
        System.out.println("                                                              Balance General 2024");
        System.out.println("                                                              Del 1ro de enero al 31 de diciembre");
        System.out.println("                            Activos corrientes                                                      Pasivos corrientes");
        System.out.println("                            "+prt("Caja",montoCaja)+"                                                          "+prt("   Cuenta por pagar",montoCPP));
        System.out.println("                            "+prt("Banco",montoBanco)+"                                                          "+prt("   Prestamo a corto plazo",montoPACP));
        System.out.println("                            "+prt("Inventario",montoInventario)+"                                                        "+prt(" Documentos por pagar",montoDPP));
        System.out.println("                            "+prt("Cuenta por cobrar",montoCPC)+"                                                  "+prt("Proveedores",montoProveedores));
        System.out.println("                            "+prt("Inversion a corto plazo",montoIACP)+"                                            "+prt("Acreedores Diversos",montoAcreedores));
        System.out.println("                            "+prt("Otro activo corriente",montoOAC)+"                                                        "+prt(" Otro pasivo corriente",montoOPC)                                                                           );
        System.out.println("                            Total activos corrientes " +sumaa(montoCaja,montoBanco,montoInventario,montoCPC,montoOAC,montoIACP)+"                                           Total Pasivos corrientes "+pasivs(montoCPP,montoPACP,montoOPC,montoDPP,montoProveedores,montoAcreedores));
        System.out.println("");
        System.out.println("");
        System.out.println("                            Activos no corrientes                                                   Pasivos no corrientes");
        System.out.println("                            "+prt("Inversion a largo plazo",montoIALP)+"                                            "+prt("Hipoteca a Pagar",montoHAP));
        System.out.println("                            "+prt("Otro activo no corriente",montoOANC)+"                                           "+prt("Otro pasivo no corriente",montoOPNC));
        System.out.println("                            Total activos no corrientes" +pasivsnc(montoIALP,montoOANC)+"                                          Total Pasivos no corrientes "+pasivsnc(montoHAP,montoOPNC));
        System.out.println();
        System.out.println();
        System.out.println("                            Total activos " +(sumaa(montoCaja,montoBanco,montoInventario,montoCPC,montoOAC,montoIACP)+pasivsnc(montoIALP,montoOANC ))+"                                          "+"        Total Pasivos "+ ( pasivsnc(montoHAP,montoOPNC)+ pasivs(montoCPP,montoPACP,montoOPC,montoDPP,montoProveedores,montoAcreedores)));
        System.out.println();
        System.out.println("                                                                                                    Patrimonio");
        System.out.println("                                                                                                    Capital Social "+montoCapitaSocial);


        System.out.println();
        System.out.println("                                           "+"                                           "+"              Total Pasivos + Patrimonio "+ ( pasivsnc(montoHAP,montoOPNC)+ pasivs(montoCPP,montoPACP,montoOPC,montoDPP,montoProveedores,montoAcreedores)+montoCapitaSocial));

//                                         Y ASI HASTA TERMINAR LOS ACTIVOS CORRIENTES Y NO CORRIENTES Y PASIVOS CORRIENTES Y NO CORRIENTES
        System.out.println();
        System.out.println();
        montoCaja=0; montoBanco=0; montoInventario=0; montoCPC=0; montoOAC=0; montoIACP=0; montoIALP=0; montoOANC=0; montoHAP=0; montoOPNC=0; montoCPP=0; montoPACP=0; montoOPC=0; montoDPP=0; montoProveedores=0; montoAcreedores=0; montoCapitaSocial=0;
        System.out.println("Deseas volver al menu? 1.Si 2.No");
        int volver = scn.nextInt();
        if (volver == 1) {
            menu();
        } else {
            System.out.println("Gracias por usar nuestro programa");
            System.exit(0);
        }
    }

    private static String prt(String s, double d){


        if(d>0){
return s+": "+d;
        }else{
            return "";
        }
    }

    //para ocultar los datos que sean 0
    /*
    private static String prt(String s, double d){
        return s+": "+d;
    /* if(d>0){

        }else{
            return "";
        }*/

    public static double sumaa(double d, double d1, double d2, double d3, double d4, double d5) {
        double suma = 0.0;
        if (d > 0) suma += d;
        if (d1 > 0) suma += d1;
        if (d2 > 0) suma += d2;
        if (d3 > 0) suma += d3;
        if (d4 > 0) suma += d4;
        if (d5 > 0) suma += d5;

        return suma;
    }
    public static double pasivs(double d, double d1, double d2, double d3, double d4,double d5) {
        double suma = 0.0;
        if (d > 0) suma += d;
        if (d1 > 0) suma += d1;
        if (d2 > 0) suma += d2;
        if (d3 > 0) suma += d3;
        if (d4 > 0) suma += d4;
        if (d5 > 0) suma += d5;

        return suma;
    }
    public static double pasivsnc(double d, double d1) {
        double suma = 0.0;
        if (d > 0) suma += d;
        if (d1 > 0) suma += d1;


        return suma;
    }

    private static void crearFactura() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            consulta3 = "select * from users where usuario='" + correo + "'";
            resultado3 = st.executeQuery(consulta3);
            while (resultado3.next()) {
                code = resultado3.getInt("ID");
                nameEmpresa = resultado3.getString("Empresa");
            }
            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }

        System.out.println("Ingrese el nombre del ente remitente:");
        String nombreRemitente = scn.next();
        scn.nextLine();

        System.out.println("Ingrese el nombre del ente destinatario:");
        String nombreDestinatario = scn.nextLine();
        System.out.println("La factura es de ingreso o gasto? 1. Ingreso, 2. Gasto");
        int definirIngresoOgasto = scn.nextInt();

        if (definirIngresoOgasto==1){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conexion = DriverManager.getConnection(url, user, password);
                st = conexion.createStatement();
;
                consulta3 = "select * from ingresos where id_user='" + code + "'";
                resultado3 = st.executeQuery(consulta3);

                while (resultado3.next()) {
                     idfactura = resultado3.getInt("ID");
                     idfacturaUser = resultado3.getInt("ID_user");
                     preciofactura = resultado3.getDouble("precio");
                     fechaFactura = resultado3.getString("fecha");
                     nombreFactura = resultado3.getString("Nombre_ingreso");
                     tipoFactura = resultado3.getString("tipo_ingreso");
                     refFactura = resultado3.getString("ref");

                    System.out.println("ID factura: "+ idfactura+ ", ID_user "+ idfacturaUser+ ", Precio: "+ preciofactura+", Fecha: "+ fechaFactura+", Nombre_Ingreso: "+ nombreFactura+", Tipo_Ingreso: "+ tipoFactura+", Ref: "+ refFactura);

                    //Crear factura, datos que falten y las variables ya tienen los datos como nombre precion nombre destinatario y remitente, falta vincular el nombre de la empresa
                }

                st.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Class not found.");
            } catch (SQLException e) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("SQL Error: " + e.getMessage());
            }
        } else if (definirIngresoOgasto==2) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conexion = DriverManager.getConnection(url, user, password);
                st = conexion.createStatement();
                ;
                consulta3 = "select * from gastos where id_user='" + code + "'";
                resultado3 = st.executeQuery(consulta3);

                while (resultado3.next()) {
                    idfactura = resultado3.getInt("ID");
                    idfacturaUser = resultado3.getInt("ID_user");
                    preciofactura = resultado3.getDouble("precio");
                    fechaFactura = resultado3.getString("fecha");
                    nombreFactura = resultado3.getString("Nombre_Gasto");
                    tipoFactura = resultado3.getString("tipo_gasto");
                    refFactura = resultado3.getString("ref");

                    System.out.println("ID factura: " + idfactura + ", ID_user " + idfacturaUser + ", Precio: " + preciofactura + ", Fecha: " + fechaFactura + ", Nombre_Gasto: " + nombreFactura + ", Tipo_Gasto: " + tipoFactura + ", Ref: " + refFactura);
                }




                st.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Class not found.");
            } catch (SQLException e) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("SQL Error: " + e.getMessage());
            }
        }else {
            System.out.println("Opcion no valida");
            menu();
        }
        if (definirIngresoOgasto == 1) {

            System.out.println("Escribe el numero de identificacion de la compra");
            identificacionCompra = scn.nextInt();

        } else if (definirIngresoOgasto==2) {
            System.out.println("Escribe el numero de identificacion deL gasto");
            identificacionCompra = scn.nextInt();
        }

        //Crear factura, datos que falten y las variables ya tienen los datos como nombre precio nombre destinatario y remitente, falta vincular el nombre de la empresa
        //escribe el tipo de documento
        System.out.println("Listo, espera un momento mientras se genera tu factura");
        System.out.println(".");
        System.out.println("..");
        System.out.println("...");



        System.out.println("....");
        System.out.println("                                                      "+ nameEmpresa);
        System.out.println();
        System.out.println();
        System.out.println("Remitente: " + nombreRemitente);
        System.out.println("Destinatario: " + nombreDestinatario);
        System.out.println();
        System.out.println("Fecha: " + fechaFactura);
        System.out.println();
        System.out.println("Codigo unico: "+ identificacionCompra );
        System.out.println();
        System.out.println();
        System.out.println("Descripcion"+"                                                                                          "+"Importe Precio Unitario");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(nombreFactura+"                                                                                          "+preciofactura);
        System.out.println();
        System.out.println();
        System.out.println("                                             Total: " +preciofactura);


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            ;
            consulta2 =  "INSERT INTO FACTURAS(Nombre_Remitente, Nombre_Destinatario, Codigo_Unico, Fecha_Emision, Descripcion, Total, Id_User) VALUES ('" +nombreRemitente   + "', '" + nombreDestinatario + "', '"+identificacionCompra+"', NOW() ,'"+nombreFactura+"' ,'"+preciofactura+"','"+idfacturaUser+"')";

            int filasAfectadas = st.executeUpdate(consulta2);






            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
        //escribe el numero de documento
        //escribe el tipo de moneda
        //escribe el tipo de cambio
        //escribe el total de la factura
        //escribe el estado de la factura
        //escribe el tipo de pago
        //escribe el tipo de entrega
        //escribe el tipo de envio
        //escribe el tipo de factura
        //escribe el estado de la factura


        //crear tabla factura

        //try catch con la consulta y la fecha se pondra con un now() como esta en el metodo registrar, consulta 2
        //Tipo de Documento 1- Cliente, 2-Proveedor
        //establecimiento = 001
        //persona: nombre de la persona(en un futuro se vinculara cuando se haga el punto agregar persona en el menu)
        //if tipo documento= proveedor {proveedor = conad} else escribe el nombre del vendedor
        System.out.println("Deseas volver al menu? 1.Si 2.No");
        int volver = scn.nextInt();
        if (volver == 1) {
            menu();
        } else {
            System.out.println("Gracias por usar nuestro programa");
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        bienvenida();
logearOregistrar();

    }
}

