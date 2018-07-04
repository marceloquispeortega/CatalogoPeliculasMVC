/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.usfx.sis457.catalogopeliculasmvc.controladores;

import bo.usfx.sis457.catalogopeliculasmvc.modelos.ModeloUsuarios;
import bo.usfx.sis457.catalogopeliculasmvc.utilitarios.ConexionMySQL;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JFramePrincipal;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JPanelLogin;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author marcelo
 */
public class ControladorPrincipal implements ActionListener, KeyListener {
    ConexionMySQL Conexion;
    
    ModeloUsuarios modeloUsuarios;
    
    JFramePrincipal Vista;
    JDialog Dialogo;
    JPanelLogin VistaLogin;

    public ControladorPrincipal(JFramePrincipal jFramePrincipal) {
        this.Conexion = new ConexionMySQL();
        this.modeloUsuarios = new ModeloUsuarios(Conexion);
        
        this.Vista = jFramePrincipal;
        this.Vista.setVisible(true);
        
        VistaLogin = new JPanelLogin();
        this.Dialogo = new JDialog(jFramePrincipal, true);
        
        VistaLogin.getJButtonIngresar().addActionListener(this);
        VistaLogin.getJButtonSalir().addActionListener(this);
        VistaLogin.getJTextFieldLogin().addKeyListener(this);
        VistaLogin.getJPasswordFieldContrasenia().addKeyListener(this);
        
        Dialogo.getContentPane().add(VistaLogin);
        Dialogo.setTitle("Datos de Acceso");
        Dialogo.setSize(340, 150);
        Dialogo.setUndecorated(true);
        centrarVentana(jFramePrincipal, Dialogo);
        Dialogo.setResizable(false);
        Dialogo.setVisible(true);
        
        this.Vista.getJMenuItemCategorias().addActionListener(this);
        this.Vista.getJMenuItemPeliculas().addActionListener(this);
        this.Vista.getJMenuItemUsuarios().addActionListener(this);
        this.Vista.getJMenuItemSalir().addActionListener(this);
        this.Vista.getJMenuItemAcercaDe().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Vista.getJMenuItemCategorias()) {
            new ControladorCategorias(Vista, this.Conexion);
        }
        if (e.getSource() == Vista.getJMenuItemPeliculas()) {
            new ControladorPeliculas(Vista, this.Conexion);
        }
        if (e.getSource() == Vista.getJMenuItemUsuarios()) {
            new ControladorUsuarios(Vista, this.Conexion);
        }
        if (e.getSource() == Vista.getJMenuItemSalir()) {
            System.exit(0);
        }
        if (e.getSource() == Vista.getJMenuItemAcercaDe()) {
            JOptionPane.showMessageDialog(Vista, "Desarrollado por Lucio Marcelo Quispe Ortega\ncomo ejemplo para la asignatura Programación Avanzada (SIS457)", "Acerca de...", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (e.getSource() == VistaLogin.getJButtonIngresar()) {
            String[] usuario = modeloUsuarios.getAcceso(VistaLogin.getJTextFieldLogin().getText(), VistaLogin.getJPasswordFieldContrasenia().getText());
            if (usuario[0].isEmpty()) {
                JOptionPane.showMessageDialog(VistaLogin, "EL usuario o contraseña no son válidos", "Acceso a Usuarios", JOptionPane.OK_OPTION);
            } else {
                if (usuario[2].equals("S")) {
                    Vista.setTitle("Catálogo de Películas [Usuario: " + usuario[0] + ", Nombre: " + usuario[1] + ", Último acceso: " + usuario[3] + "]");
                    if (usuario[4].equals("operador")) {
                        Vista.getJSeparador1().setVisible(false);
                        Vista.getJMenuItemUsuarios().setVisible(false);
                    } else if (usuario[4].equals("administrador")) {
                        Vista.getJMenuItemCategorias().setVisible(false);
                        Vista.getJMenuItemPeliculas().setVisible(false);
                        Vista.getJSeparador1().setVisible(false);
                    }
                    Dialogo.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(VistaLogin, "EL usuario no está habilitado", "Acceso a Usuarios", JOptionPane.OK_OPTION);
                }
            }
        }
        if (e.getSource() == VistaLogin.getJButtonSalir()) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            VistaLogin.getJButtonIngresar().doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public void centrarVentana(Component jFrame, Component dialogo) {
        Dimension tamanioVentana = jFrame.getSize();
        
        dialogo.setLocation(
          jFrame.getX() + ((tamanioVentana.width - dialogo.getSize().width) / 2),
          jFrame.getY() + ((tamanioVentana.height - dialogo.getSize().height) / 2));
    }
}
