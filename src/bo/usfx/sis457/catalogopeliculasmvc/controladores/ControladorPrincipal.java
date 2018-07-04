/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.usfx.sis457.catalogopeliculasmvc.controladores;

import bo.usfx.sis457.catalogopeliculasmvc.utilitarios.ConexionMySQL;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JFramePrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author marcelo
 */
public class ControladorPrincipal implements ActionListener {
    ConexionMySQL Conexion;
    
    JFramePrincipal Vista;

    public ControladorPrincipal(JFramePrincipal jFramePrincipal) {
        this.Conexion = new ConexionMySQL();
        
        this.Vista = jFramePrincipal;
        this.Vista.setVisible(true);
        
        this.Vista.getJMenuItemCategorias().addActionListener(this);
        this.Vista.getJMenuItemPeliculas().addActionListener(this);
        this.Vista.getJMenuItemSalir().addActionListener(this);
        this.Vista.getJMenuItemAcercaDe().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.Vista.getJMenuItemCategorias()) {
            new ControladorCategorias(this.Vista, this.Conexion);
        }
        if (e.getSource() == this.Vista.getJMenuItemPeliculas()) {
            new ControladorPeliculas(this.Vista, this.Conexion);
        }
        if (e.getSource() == this.Vista.getJMenuItemSalir()) {
            System.exit(0);
        }
        if (e.getSource() == this.Vista.getJMenuItemAcercaDe()) {
            JOptionPane.showMessageDialog(Vista, "Desarrollado por Lucio Marcelo Quispe Ortega\ncomo ejemplo para la asignatura Programación Avanzada (SIS457)", "Acerca de...", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
}
