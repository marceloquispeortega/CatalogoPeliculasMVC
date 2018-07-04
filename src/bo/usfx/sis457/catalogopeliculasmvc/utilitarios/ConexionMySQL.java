/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.usfx.sis457.catalogopeliculasmvc.utilitarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author marcelo
 */
public class ConexionMySQL {

    /* Datos para la Conexión */
    
    /**
     * Servidor de Base de datos
     */
    private String Servidor = "localhost";
    
    /**
     * Puerto por defecto de conexión del Servidor
     */
    private String Puerto = "3306";
    
    /**
     * Base de datos por defecto es test
     */
    private String BaseDatos = "sis457_bdpeliculas";
    /**
     * Usuario
     */
    private String Usuario = "usr_sis457";
    /**
     * Contraseña de MySql
     */
    private String Contrasenia = "t3JPnGzsDW4Owbzs";
    /**
     * Cadena de conexion
     */
    private String Url = "jdbc:mysql://" + Servidor + ":" + Puerto + "/" + BaseDatos;
    /**
     * Variable para trabajar con la conexion a la base de datos
     */
    private Connection Conexion = null;

    /**
     * Constructor de clase
     */
    public ConexionMySQL() {
        conectar();
    }
    
    public ConexionMySQL(String servidor, String puerto, String baseDatos, String usuario, String contrasenia) {
        this.Servidor = servidor;
        this.Puerto = puerto;
        this.BaseDatos = baseDatos;
        this.Usuario = usuario;
        this.Contrasenia = contrasenia;
        conectar();
    }
    
    public void conectar() {
        this.Url = "jdbc:mysql://" + this.Servidor + ":" + this.Puerto + "/" + this.BaseDatos;
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            Conexion = DriverManager.getConnection(this.Url, this.Usuario, this.Contrasenia);
            System.out.println("Conectado");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConexion() {
        return this.Conexion;
    }
    
    public boolean ejecutarConsulta(String consulta) {
        //se ejecuta la consulta
        try {
            PreparedStatement preparandoConsulta = this.getConexion().prepareStatement(consulta);
            preparandoConsulta.execute();
            preparandoConsulta.close();
//            System.out.println("Consulta ejecutada!");
            return true;
        }catch(SQLException e){
//            System.out.println("Consulta no ejecutada!");
            System.err.println( e.getMessage() );
        }
        return false;
    }
    
    public ResultSet getDatos(String consulta) {
        try {
            PreparedStatement preparandoConsulta = this.getConexion().prepareStatement(consulta);
            return preparandoConsulta.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
