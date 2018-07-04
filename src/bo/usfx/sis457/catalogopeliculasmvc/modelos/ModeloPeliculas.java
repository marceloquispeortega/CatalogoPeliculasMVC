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
public class ModeloPeliculas {
    ConexionMySQL Conexion;
    int Total;

    public ModeloPeliculas(ConexionMySQL conexion) {
        this.Conexion = conexion;
        this.Total = 0;
    }
    
    public String[] getPelicula(int id) {
        String[] pelicula = new String[7];
        try {
            ResultSet resultado = Conexion.getDatos("SELECT P.id, P.codigo, P.titulo, P.descripcion, "
                    + "P.duracion, P.anio, C.nombre AS categoria FROM peliculas P "
                    + "INNER JOIN categorias C ON C.id = P.categoria_id WHERE P.id=" + id);
            if (resultado.next()) {
                pelicula[0] = "" + id;
                pelicula[1] = resultado.getString("codigo");
                pelicula[2] = resultado.getString("titulo");
                pelicula[3] = resultado.getString("descripcion");
                pelicula[4] = resultado.getString("duracion");
                pelicula[5] = resultado.getString("anio");
                pelicula[6] = resultado.getString("categoria");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return pelicula;
    }
    
    public boolean guardar(int id, String codigo, String nombre, String descripcion, String duracion, int anio, int categoriaId) {
        if (id == 0) {
            String consulta = "INSERT INTO peliculas "
                    + "(codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id) VALUES "
                    + "('" + codigo + "', '" + nombre + "', '" + descripcion + "', '" + duracion + "', " + anio + ", NOW(), 1, " + categoriaId + ")";
            if (Conexion.ejecutarConsulta(consulta)) {
                return true;
            } else {
                return false;
            }
        } else {
            String consulta = "UPDATE peliculas "
                    + " SET codigo='" + codigo + "', titulo='" + nombre + "', descripcion='" + descripcion + "', "
                    + " duracion='" + duracion + "', anio=" + anio + ", categoria_id=" + categoriaId + ", "
                    + " modificacion=NOW(), modificado_por=1 "
                    + " WHERE id=" + id;
            if (Conexion.ejecutarConsulta(consulta)) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean borrar(int id) {
        String consulta = "DELETE FROM peliculas "
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
            String consulta = "SELECT P.id, P.codigo, P.titulo, P.descripcion, "
                    + "P.duracion, P.anio, C.nombre AS categoria FROM peliculas P "
                    + "INNER JOIN categorias C ON C.id = P.categoria_id";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE P.codigo LIKE '%" + textoBusqueda + "%' OR P.titulo LIKE '%" + textoBusqueda + "%' OR P.descripcion LIKE '%" + textoBusqueda + "%'";
            }
//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);
            
            // Se crea el array de columnas
            String[] columnas = {"Id", "Código", "Título", "Descripcion", "Duración", "Año", "Categoría"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][7];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString("id");
                    datos[i][1] = resultado.getString("codigo");
                    datos[i][2] = resultado.getString("titulo");
                    datos[i][3] = resultado.getString("descripcion");
                    datos[i][4] = resultado.getString("duracion");
                    datos[i][5] = resultado.getString("anio");
                    datos[i][6] = resultado.getString("categoria");
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
