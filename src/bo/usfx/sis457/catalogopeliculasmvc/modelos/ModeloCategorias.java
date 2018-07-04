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
public class ModeloCategorias {
    ConexionMySQL Conexion;
    int Total;

    public ModeloCategorias(ConexionMySQL conexion) {
        this.Conexion = conexion;
        this.Total = 0;
    }
    
    public String[] getCategoria(int id) {
        String[] categoria = new String[4];
        try {
            ResultSet resultado = Conexion.getDatos("SELECT * FROM categorias WHERE id = " + id);
            if (resultado.next()) {
                categoria[0] = "" + id;
                categoria[1] = resultado.getString("codigo");
                categoria[2] = resultado.getString("nombre");
                categoria[3] = resultado.getString("descripcion");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return categoria;
    }
    
    public String[] getCategoria(String nombre) {
        String[] categoria = new String[4];
        try {
            ResultSet resultado = Conexion.getDatos("SELECT * FROM categorias WHERE nombre = '" + nombre + "'");
            if (resultado.next()) {
                categoria[0] = resultado.getString("id");
                categoria[1] = resultado.getString("codigo");
                categoria[2] = nombre;
                categoria[3] = resultado.getString("descripcion");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return categoria;
    }
    
    public boolean guardar(int id, String codigo, String nombre, String descripcion) {
        if (id == 0) {
            String consulta = "INSERT INTO categorias "
                    + "(codigo, nombre, descripcion, creacion, creado_por) VALUES "
                    + "('" + codigo + "', '" + nombre + "', '" + descripcion + "', NOW(), 1)";
            if (Conexion.ejecutarConsulta(consulta)) {
                return true;
            } else {
                return false;
            }
        } else {
            String consulta = "UPDATE categorias "
                    + "SET codigo='" + codigo + "', nombre='" + nombre + "', descripcion='" + descripcion + "', "
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
        String consulta = "DELETE FROM categorias "
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
            String consulta = "SELECT * FROM categorias";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE codigo LIKE '%" + textoBusqueda + "%' OR nombre LIKE '%" + textoBusqueda + "%' OR descripcion LIKE '%" + textoBusqueda + "%'";
            }
            ResultSet resultado = Conexion.getDatos(consulta);
            
            // Se crea el array de columnas
            String[] columnas = {"Id", "Código", "Nombre", "Descripcion"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][4];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString("id");
                    datos[i][1] = resultado.getString("codigo");
                    datos[i][2] = resultado.getString("nombre");
                    datos[i][3] = resultado.getString("descripcion");
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
    
    public String[] getLista() {
        try {
            String consulta = "SELECT * FROM categorias";
            ResultSet resultado = Conexion.getDatos(consulta);

            resultado.last();
            //Se crea una matriz con tantas filas y columnas que necesite
            String[] datos = new String[resultado.getRow()];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i] = resultado.getString("nombre");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            return datos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public int getTotal() {
        return Total;
    }
    
    
    
    
}
