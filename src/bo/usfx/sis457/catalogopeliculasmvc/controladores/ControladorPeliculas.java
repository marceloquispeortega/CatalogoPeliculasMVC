/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.usfx.sis457.catalogopeliculasmvc.controladores;

import bo.usfx.sis457.catalogopeliculasmvc.modelos.ModeloCategorias;
import bo.usfx.sis457.catalogopeliculasmvc.modelos.ModeloPeliculas;
import bo.usfx.sis457.catalogopeliculasmvc.utilitarios.ConexionMySQL;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JPanelPeliculasListar;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JPanelPeliculasFormulario;
import java.awt.Component;
import java.awt.Dimension;
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
public class ControladorPeliculas implements ActionListener, KeyListener {
    ModeloPeliculas Modelo;
    ModeloCategorias ModeloCategorias;
    
    JPanelPeliculasListar VistaListar;
    JPanelPeliculasFormulario VistaFormulario;
    JDialog Dialogo;
    
    public ControladorPeliculas(JFrame jFramePrincipal, ConexionMySQL conexionMySQL) {
        this.Modelo = new ModeloPeliculas(conexionMySQL);
        this.ModeloCategorias = new ModeloCategorias(conexionMySQL);
        
        this.VistaListar = new JPanelPeliculasListar();
        this.VistaListar.setSize(jFramePrincipal.getWidth(), jFramePrincipal.getHeight());
        this.VistaListar.getJButtonNuevo().addActionListener(this);
        this.VistaListar.getJButtonModificar().addActionListener(this);
        this.VistaListar.getJButtonBorrar().addActionListener(this);
        this.VistaListar.getJButtonActualizar().addActionListener(this);
        this.VistaListar.getJTextFieldBusqueda().addKeyListener(this);
        this.VistaListar.getJButtonBusqueda().addActionListener(this);
        this.VistaListar.getJButtonLimpiar().addActionListener(this);
        
        this.VistaFormulario = new JPanelPeliculasFormulario();
        this.VistaFormulario.getJButtonGuardar().addActionListener(this);
        this.VistaFormulario.getJButtonCancelar().addActionListener(this);
        
        this.Dialogo = new JDialog(jFramePrincipal, true);
        
        cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        
        jFramePrincipal.getContentPane().removeAll();
        jFramePrincipal.getContentPane().add(VistaListar);
        jFramePrincipal.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Botón Nuevo pulsado desde el Panel de JPanelPeliculasListar
        if (e.getSource() == this.VistaListar.getJButtonNuevo()) {
            VistaFormulario.getJComboBoxCategoriaId().setModel(new javax.swing.DefaultComboBoxModel<>(ModeloCategorias.getLista()));
            
            Dialogo.getContentPane().add(this.VistaFormulario);
            Dialogo.setTitle("Nueva Película");
            Dialogo.setSize(600, 310);
            centrarVentana(this.VistaListar, Dialogo);
            Dialogo.setResizable(false);
            Dialogo.setVisible(true);
        }
        
        //Botón Modificar pulsado desde el Panel de JPanelPeliculasListar
        if (e.getSource() == this.VistaListar.getJButtonModificar()) {
            if (VistaListar.getJTablePeliculas().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(VistaListar, "Debe seleccionar un registro");
            } else {
                String[] pelicula = Modelo.getPelicula(
                        Integer.parseInt(((DefaultTableModel)VistaListar.getJTablePeliculas().getModel())
                                .getValueAt(VistaListar.getJTablePeliculas().getSelectedRow(), 0).toString()));

                VistaFormulario.getJComboBoxCategoriaId().setModel(new javax.swing.DefaultComboBoxModel<>(ModeloCategorias.getLista()));
                
                VistaFormulario.getJTextFieldId().setText("" + pelicula[0]);
                VistaFormulario.getJTextFieldCodigo().setText(pelicula[1]);
                VistaFormulario.getJTextFieldTitulo().setText(pelicula[2]);
                VistaFormulario.getJTextAreaDescripcion().setText(pelicula[3]);
                VistaFormulario.getJTextFieldDuracion().setText(pelicula[4]);
                VistaFormulario.getJTextFieldAnio().setText(pelicula[5]);
                VistaFormulario.getJComboBoxCategoriaId().setSelectedItem(pelicula[6]);

                Dialogo.getContentPane().add(this.VistaFormulario);
                Dialogo.setTitle("Modificar Película");
                Dialogo.setSize(600, 310);
                centrarVentana(this.VistaListar, Dialogo);
                Dialogo.setResizable(false);
                Dialogo.setVisible(true);
            }
        }
        
        //Botón Borrar pulsado desde el Panel de JPanelPeliculasListar
        if (e.getSource() == this.VistaListar.getJButtonBorrar()) {
            if (VistaListar.getJTablePeliculas().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(VistaListar, "Debe seleccionar un registro");
            } else {
                int dialogoResultado = JOptionPane.showConfirmDialog(VistaListar, "¿Esta segur@ de borrar el registro de Película?", "Pregunta", JOptionPane.YES_NO_OPTION);
                if (dialogoResultado == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(
                            ((DefaultTableModel)VistaListar.getJTablePeliculas().getModel())
                                    .getValueAt(VistaListar.getJTablePeliculas().getSelectedRow(), 0).toString());
                    if (Modelo.borrar(id)) {
                        
                    } else {
                        JOptionPane.showMessageDialog(VistaListar, "El Registro no se pudo borrar");
                    }
                    cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
                }
            }
        }
        
        //Botón Actualizar pulsado desde el Panel de JPanelPeliculasListar
        if (e.getSource() == this.VistaListar.getJButtonActualizar()) {
            cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        }
        
        //Botón Buscar pulsado desde el Panel de JPanelPeliculasListar
        if (e.getSource() == this.VistaListar.getJButtonBusqueda()) {
            cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        }
        
        //Botón Limpiar pulsado desde el Panel de JPanelPeliculasListar
        if (e.getSource() == this.VistaListar.getJButtonLimpiar()) {
            VistaListar.getJTextFieldBusqueda().setText("");
            cargarDatos("");
        }
        
        //Botón Guardar pulsado desde el Panel de JPanelPeliculasFormulario
        if (e.getSource() == this.VistaFormulario.getJButtonGuardar()) {
            String[] categoria = ModeloCategorias.getCategoria(this.VistaFormulario.getJComboBoxCategoriaId().getSelectedItem().toString());
            if (
                Modelo.guardar(
                    Integer.parseInt(this.VistaFormulario.getJTextFieldId().getText()),
                    this.VistaFormulario.getJTextFieldCodigo().getText(), 
                    this.VistaFormulario.getJTextFieldTitulo().getText(), 
                    this.VistaFormulario.getJTextAreaDescripcion().getText(),
                    this.VistaFormulario.getJTextFieldDuracion().getText(),
                    Integer.parseInt(this.VistaFormulario.getJTextFieldAnio().getText()),
                    Integer.parseInt(categoria[0])
                )
            ) {
                Dialogo.setVisible(false);
                limpiarFormulario();
                cargarDatos("");
            } else {
                JOptionPane.showMessageDialog(Dialogo, "No se pudo guardar la Película", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        //Botón Cancelar pulsado desde el Panel de JPanelPeliculasFormulario
        if (e.getSource() == this.VistaFormulario.getJButtonCancelar()) {
            Dialogo.setVisible(false);
            limpiarFormulario();
            cargarDatos(VistaListar.getJTextFieldBusqueda().getText());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
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
        this.VistaListar.getJTablePeliculas().getColumnModel().getColumn(0).setWidth(0);
        this.VistaListar.getJTablePeliculas().getColumnModel().getColumn(0).setMinWidth(0);
        this.VistaListar.getJTablePeliculas().getColumnModel().getColumn(0).setMaxWidth(0); 
    }
    
    public void limpiarFormulario() {
        this.VistaFormulario.getJTextFieldId().setText("0");
        this.VistaFormulario.getJTextFieldCodigo().setText("");
        this.VistaFormulario.getJTextFieldTitulo().setText(""); 
        this.VistaFormulario.getJTextAreaDescripcion().setText("");
    }
    
    public void centrarVentana(Component jFrame, Component dialogo) {
        Dimension tamanioVentana = jFrame.getSize();
        
        dialogo.setLocation(
          jFrame.getX() + ((tamanioVentana.width - dialogo.getSize().width) / 2),
          jFrame.getY() + ((tamanioVentana.height - dialogo.getSize().height) / 2));
    }
}
