/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.usfx.sis457.catalogopeliculasmvc.modelos;

import bo.usfx.sis457.catalogopeliculasmvc.utilitarios.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marcelo
 */
public class ModeloUsuarios {
    ConexionMySQL Conexion;
    int Total;

    public ModeloUsuarios(ConexionMySQL conexion) {
        this.Conexion = conexion;
        this.Total = 0;
    }
    
    public String[] getUsuario(int id) {
        String[] usuario = new String[5];
        try {
            ResultSet resultado = Conexion.getDatos("SELECT * FROM usuarios WHERE id = " + id);
            if (resultado.next()) {
                usuario[0] = "" + id;
                usuario[1] = resultado.getString("login");
                usuario[2] = resultado.getString("nombre");
                usuario[3] = resultado.getString("rol");
                usuario[4] = resultado.getString("habilitado");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return usuario;
    }
    
    public String[] getAcceso(String login, String contrasenia) {
        String[] usuario = {"", "", "", "", ""};
        try {
            ResultSet resultado = Conexion.getDatos("SELECT * FROM usuarios WHERE login = '" + login + "' AND contrasenia=SHA1('" + contrasenia + "')");
            if (resultado.next()) {
                usuario[0] = resultado.getString("login");
                usuario[1] = resultado.getString("nombre");
                usuario[2] = resultado.getString("habilitado");
                usuario[3] = resultado.getString("ultimo_acceso");
                usuario[4] = resultado.getString("rol");
                Conexion.ejecutarConsulta("UPDATE usuarios SET ultimo_acceso=NOW() WHERE id=" + resultado.getString("id"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return usuario;
    }
    
    public boolean guardar(int id, String login, String contrasenia, String nombre, String rol, String habilitado) {
        if (id == 0) {
            String consulta = "INSERT INTO usuarios "
                    + "(login, contrasenia, nombre, rol, habilitado, creacion, creado_por) VALUES "
                    + "('" + login + "', SHA1('" + contrasenia + "'), '" + nombre + "', '" + rol + "', '" + habilitado + "', NOW(), 1)";
            if (Conexion.ejecutarConsulta(consulta)) {
                return true;
            } else {
                return false;
            }
        } else {
            String consulta = "UPDATE usuarios "
                    + "SET login='" + login + "', " + (contrasenia.isEmpty()?"":("contrasenia=SHA1('" + contrasenia + "'), ")) + "nombre='" + nombre + "', rol='" + rol + "', habilitado='" + habilitado + "',"
                    + "modificacion=NOW(), modificado_por=1 "
                    + "WHERE id=" + id;
            if (Conexion.ejecutarConsulta(consulta)) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean borrar(int id) {
        String consulta = "DELETE FROM usuarios "
                + " WHERE id = " + id;
        if (Conexion.ejecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }
    
    public DefaultTableModel getLista(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        try {
            String consulta = "SELECT * FROM usuarios";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE login LIKE '%" + textoBusqueda + "%' OR nombre LIKE '%" + textoBusqueda + "%'";
            }
            ResultSet resultado = Conexion.getDatos(consulta);
            
            // Se crea el array de columnas
            String[] columnas = {"Id", "Login", "Nombre", "Habilitado", "Ultimo Acceso"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][5];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString("id");
                    datos[i][1] = resultado.getString("login");
                    datos[i][2] = resultado.getString("nombre");
                    datos[i][3] = resultado.getString("habilitado").equals("S")?"Si":"No";
                    datos[i][4] = resultado.getString("ultimo_acceso");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            modeloTabla.setDataVector(datos, columnas);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return modeloTabla;
    }
    
    public int getTotal() {
        return Total;
    }
    
}
