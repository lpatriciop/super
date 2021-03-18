/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimvc;

import mimvc.controlador.ControlMP;
import mimvc.controlador.ControlPersona;
import mimvc.modelo.ModeloPersona;
import mimvc.vista.VistaMP;
import mimvc.vista.VistaPersona;

/**
 *
 * @author Patricio
 */
public class MiMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        VistaMP v = new VistaMP();
        ControlMP c = new ControlMP(v);
        c.iniciaControl();
    
    }
    
}
