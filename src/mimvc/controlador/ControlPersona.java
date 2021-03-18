/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimvc.controlador;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import mimvc.modelo.ConexionPG;
import mimvc.modelo.ModeloPersona;
import mimvc.modelo.Persona;
import mimvc.vista.VistaPersona;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author Patricio
 */
public class ControlPersona {
    private ModeloPersona modelo;
    private VistaPersona vista;

    public ControlPersona(ModeloPersona modelo, VistaPersona vista) {
        this.modelo = modelo;
        this.vista = vista;
     //   cargaLista();
        vista.setVisible(true);
    }
    public void iniciarControl(){
        //Crear Key Listener
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
            //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                cargaBusqueda(vista.getTxtBuscar().getText());
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        vista.getBtnRecargar().addActionListener(l->cargaLista());
        vista.getTxtBuscar().addKeyListener(kl);
        vista.getBtnExaminar().addActionListener(l->obtieneImagen());
        vista.getBtnNuevo().addActionListener(l->muestraDialogo());
        vista.getBtnImprimir().addActionListener(l->imprimeReporte());
      
    }

    
    private void imprimeReporte(){
        ConexionPG con = new ConexionPG();
        try {
            JasperReport jr = (JasperReport)JRLoader.loadObject(getClass().getResource("/mimvc/vista/reportes/personas/rptPersonas.jasper"));
            JasperPrint jp= JasperFillManager.fillReport(jr, null,con.getCon());
            JasperViewer jv = new JasperViewer(jp);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControlPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void obtieneImagen() {
        vista.getLblFoto().setIcon(null);
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = j.showOpenDialog(null);
        if (estado == JFileChooser.APPROVE_OPTION) {
            try {
                Image icono = ImageIO.read(j.getSelectedFile()).getScaledInstance(
                        vista.getLblFoto().getWidth(), 
                        vista.getLblFoto().getHeight(), 
                        Image.SCALE_DEFAULT);
                vista.getLblFoto().setIcon(new ImageIcon(icono));
                vista.getLblFoto().updateUI();
            } catch (IOException ex) {
                Logger.getLogger(ControlPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void muestraDialogo(){
    vista.getjDialog1().setSize(650,300);
    vista.getjDialog1().setTitle("NUEVA PERSONA");
    vista.getjDialog1().setLocationRelativeTo(vista);
//    vista.getTxtID().setText("");
//    vista.getTxtNombres().setText("");
//    vista.getTxtApellidos().setText("");
    vista.getjDialog1().setVisible(true);
    }
    private void cargaLista(){
        DefaultTableModel tableModel;
        tableModel=(DefaultTableModel)vista.getTblPersonas().getModel();
        tableModel.setNumRows(0);
        List<Persona> lista=ModeloPersona.listaPersonas();
        lista.stream().forEach(p1->{
            String[] persona={p1.getIdPersona(),p1.getNombre(),p1.getApellido()};
            tableModel.addRow(persona);
        });
    }
    private void cargaBusqueda(String aguja){
        DefaultTableModel tableModel;
        tableModel=(DefaultTableModel)vista.getTblPersonas().getModel();
        tableModel.setNumRows(0);
        List<Persona> lista=ModeloPersona.buscar(aguja);
        lista.stream().forEach(p1->{
            String[] persona={p1.getIdPersona(),p1.getNombre(),p1.getApellido()};
            tableModel.addRow(persona);
        });
    }
}
