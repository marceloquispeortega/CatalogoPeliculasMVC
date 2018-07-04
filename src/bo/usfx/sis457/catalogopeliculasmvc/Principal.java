/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.usfx.sis457.catalogopeliculasmvc;

import bo.usfx.sis457.catalogopeliculasmvc.controladores.ControladorPrincipal;
import bo.usfx.sis457.catalogopeliculasmvc.vistas.JFramePrincipal;

/**
 *
 * @author marcelo
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ControladorPrincipal(new JFramePrincipal()) ;
    }
    
}
