/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimvc.controlador;

import mimvc.modelo.ModeloPersona;
import mimvc.vista.VistaMP;
import mimvc.vista.VistaPersona;

/**
 *
 * @author Patricio
 */
public class ControlMP {
    private VistaMP vista;

    public ControlMP(VistaMP v) {
        this.vista = v;
        vista.setVisible(true);
    }
    public void iniciaControl(){
        vista.getMnuClienteMantenimiento().addActionListener(l->crudPersona());
        vista.getTlbMC().addActionListener(l->crudPersona());
    
    }
    private void crudPersona(){
        ModeloPersona m=new ModeloPersona();
        VistaPersona v= new VistaPersona();
        vista.getDesktop().add(v);
        ControlPersona c=new ControlPersona(m, v);
        c.iniciarControl();
    }
    
    
}
