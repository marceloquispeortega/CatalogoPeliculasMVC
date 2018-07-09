/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.usfx.sis457.catalogopeliculasmvc.controladores;

import bo.usfx.sis457.catalogopeliculasmvc.modelos.ModeloUsuarios;
import bo.usfx.sis457.catalogopeliculasmvc.utilitarios.ConexionMySQL;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JPanelUsuariosFormulario;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JPanelUsuariosListar;
import java.awt.Component;
import java.awt.Dimension;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marcelo
 */
public class ControladorUsuarios implements ActionListener, KeyListener {
    ModeloUsuarios Modelo;
    
    JPanelUsuariosListar VistaListar;
    JPanelUsuariosFormulario VistaFormulario;
    JDialog Dialogo;
    
    public ControladorUsuarios(JFrame jFramePrincipal, ConexionMySQL conexionMySQL) {
        this.Modelo = new ModeloUsuarios(conexionMySQL);
        
        this.VistaListar = new JPanelUsuariosListar();
        this.VistaListar.setSize(jFramePrincipal.getWidth(), jFramePrincipal.getHeight());
        this.VistaListar.getJButtonNuevo().addActionListener(this);
        this.VistaListar.getJButtonModificar().addActionListener(this);
        this.VistaListar.getJButtonBorrar().addActionListener(this);
        this.VistaListar.getJButtonActualizar().addActionListener(this);
        this.VistaListar.getJTextFieldBusqueda().addKeyListener(this);
        this.VistaListar.getJButtonBusqueda().addActionListener(this);
        this.VistaListar.getJButtonLimpiar().addActionListener(this);
        
        
        
        this.VistaFormulario = new JPanelUsuariosFormulario();
        this.VistaFormulario.getJButtonGuardar().addActionListener(this);
        this.VistaFormulario.getJButtonCancelar().addActionListener(this);
        
        this.VistaFormulario.getJTextFieldNombre().addKeyListener(this);
        
        this.Dialogo = new JDialog(jFramePrincipal, true);
        
        cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        
        jFramePrincipal.getContentPane().removeAll();
        jFramePrincipal.getContentPane().add(VistaListar);
        jFramePrincipal.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Botón Nuevo pulsado desde el Panel de JPanelCategoriasListar
        if (e.getSource() == this.VistaListar.getJButtonNuevo()) {
            Dialogo.getContentPane().add(this.VistaFormulario);
            Dialogo.setTitle("Nuevo Usuario");
            Dialogo.setSize(600, 320);
            centrarVentana(this.VistaListar, Dialogo);
            Dialogo.setResizable(false);
            Dialogo.setVisible(true);
        }
        
        //Botón Modificar pulsado desde el Panel de JPanelCategoriasListar
        if (e.getSource() == this.VistaListar.getJButtonModificar()) {
            if (VistaListar.getJTableUsuarios().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(VistaListar, "Debe seleccionar un registro");
            } else {
                String[] usuario = Modelo.getUsuario(
                        Integer.parseInt(((DefaultTableModel)VistaListar.getJTableUsuarios().getModel())
                                .getValueAt(VistaListar.getJTableUsuarios().getSelectedRow(), 0).toString()));

                VistaFormulario.getJTextFieldId().setText("" + usuario[0]);
                VistaFormulario.getJTextFieldUsuario().setText(usuario[1]);
                VistaFormulario.getJTextFieldNombre().setText(usuario[2]);
                VistaFormulario.getJComboBoxRol().setSelectedItem(usuario[3]);
                if (usuario[4].equals("S")) {
                    VistaFormulario.getJCheckBoxHabilitado().setSelected(true);
                } else {
                    VistaFormulario.getJCheckBoxHabilitado().setSelected(false);
                }

                Dialogo.getContentPane().add(this.VistaFormulario);
                Dialogo.setTitle("Modificar Usuario");
                Dialogo.setSize(600, 320);
                centrarVentana(this.VistaListar, Dialogo);
                Dialogo.setResizable(false);
                Dialogo.setVisible(true);
            }
        }
        
        //Botón Borrar pulsado desde el Panel de JPanelCategoriasListar
        if (e.getSource() == this.VistaListar.getJButtonBorrar()) {
            if (VistaListar.getJTableUsuarios().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(VistaListar, "Debe seleccionar un registro");
            } else {
                int dialogoResultado = JOptionPane.showConfirmDialog(VistaListar, "¿Esta segur@ de borrar el registro de Usuario?", "Pregunta", JOptionPane.YES_NO_OPTION);
                if (dialogoResultado == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(
                            ((DefaultTableModel)VistaListar.getJTableUsuarios().getModel())
                                    .getValueAt(VistaListar.getJTableUsuarios().getSelectedRow(), 0).toString());
                    if (Modelo.borrar(id)) {
                        
                    } else {
                        JOptionPane.showMessageDialog(VistaListar, "El Registro no se pudo borrar");
                    }
                    cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
                }
            }
        }
        
        //Botón Actualizar pulsado desde el Panel de JPanelCategoriasListar
        if (e.getSource() == this.VistaListar.getJButtonActualizar()) {
            cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        }
        
        //Botón Buscar pulsado desde el Panel de JPanelCategoriasListar
        if (e.getSource() == this.VistaListar.getJButtonBusqueda()) {
            cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        }
        
        //Botón Limpiar pulsado desde el Panel de JPanelCategoriasListar
        if (e.getSource() == this.VistaListar.getJButtonLimpiar()) {
            VistaListar.getJTextFieldBusqueda().setText("");
            cargarDatos("");
        }
        
        //Botón Guardar pulsado desde el Panel de JPanelCategoriasFormulario
        if (e.getSource() == this.VistaFormulario.getJButtonGuardar()) {
            if (
                Modelo.guardar(
                    Integer.parseInt(this.VistaFormulario.getJTextFieldId().getText()),
                    this.VistaFormulario.getJTextFieldUsuario().getText(), 
                    this.VistaFormulario.getJPasswordFieldContrasenia().getText(),
                    this.VistaFormulario.getJTextFieldNombre().getText(), 
                    this.VistaFormulario.getJComboBoxRol().getSelectedItem().toString(),
                    this.VistaFormulario.getJCheckBoxHabilitado().isSelected()?"S":"N"
                )
            ) {
                Dialogo.setVisible(false);
                limpiarFormulario();
                cargarDatos("");
            } else {
                JOptionPane.showMessageDialog(Dialogo, "No se pudo guardar la Categoría", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        //Botón Cancelar pulsado desde el Panel de JPanelCategoriasFormulario
        if (e.getSource() == this.VistaFormulario.getJButtonCancelar()) {
            Dialogo.setVisible(false);
            limpiarFormulario();
            cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        if (e.getSource() == this.VistaFormulario.getJTextFieldNombre()) {
            if (Character.isDigit(c)) {
                getDefaultToolkit().beep();
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public void cargarDatos(String textoBusqueda) {
        this.VistaListar.setDatos(this.Modelo.getLista(textoBusqueda), this.Modelo.getTotal());
        //Ocultamos la columna del Id para que no se vea
        this.VistaListar.getJTableUsuarios().getColumnModel().getColumn(0).setWidth(0);
        this.VistaListar.getJTableUsuarios().getColumnModel().getColumn(0).setMinWidth(0);
        this.VistaListar.getJTableUsuarios().getColumnModel().getColumn(0).setMaxWidth(0); 
    }
    
    public void limpiarFormulario() {
        this.VistaFormulario.getJTextFieldId().setText("0");
        this.VistaFormulario.getJTextFieldUsuario().setText("");
        this.VistaFormulario.getJPasswordFieldContrasenia().setText("");
        this.VistaFormulario.getJTextFieldNombre().setText(""); 
        this.VistaFormulario.getJComboBoxRol().setSelectedIndex(0);
        this.VistaFormulario.getJCheckBoxHabilitado().setSelected(true);
    }
    
    public void centrarVentana(Component jFrame, Component dialogo) {
        Dimension tamanioVentana = jFrame.getSize();
        
        dialogo.setLocation(
          jFrame.getX() + ((tamanioVentana.width - dialogo.getSize().width) / 2),
          jFrame.getY() + ((tamanioVentana.height - dialogo.getSize().height) / 2));
    }
}
