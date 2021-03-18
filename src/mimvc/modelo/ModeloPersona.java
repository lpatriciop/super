/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimvc.modelo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.postgresql.util.Base64;

/**
 *
 * @author Patricio
 */
public class ModeloPersona  extends Persona{
    private static ConexionPG con=new ConexionPG();
    public ModeloPersona() {
    }

    public ModeloPersona(String idPersona, String nombre, String apellido, Date fechaNacimiento) {
        super(idPersona, nombre, apellido, fechaNacimiento);
    }
    
    public boolean Guardar(){
        
        //Transformo image a base64 encode para postgresl
        String ef=null;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
         try {
             BufferedImage img=toBufferedImage(getFoto());
             ImageIO.write(img, "PNG", bos);
             byte[] imgb=bos.toByteArray();
             ef=Base64.encodeBytes(imgb);  
         } catch (IOException ex) {
             Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
         }
        
       String noQuery="insert into persona(idpersona,nombres,apellidos)values('"+getIdPersona()+"','"+getNombre()+"','"+getApellido()+"')";
      if (con.noQuery(noQuery)==null){
          return true;
      }else{
          return false;
      }
    }
    
    public static List<Persona> buscar(String aguja){
        try {
            String query="SELECT * from persona WHERE ";
                   query+="UPPER(idpersona) like UPPER('%"+aguja+"%') OR ";
                   query+="UPPER(nombres) like UPPER('%"+aguja+"%') OR ";
                   query+="UPPER(apellidos) like UPPER('%"+aguja+"%')";
            ResultSet rs=con.query(query);
            List<Persona> lista = new ArrayList<Persona>();
            while(rs.next()){
                Persona persona=new Persona();
                persona.setIdPersona(rs.getString("idpersona"));
                persona.setNombre(rs.getString("nombres"));
                persona.setApellido(rs.getString("apellidos"));
                lista.add(persona);
            } 
        rs.close();
        return lista;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static List<Persona> listaPersonas(){
        try {
            String query="select * from persona";

            ResultSet rs=con.query(query);
            List<Persona> lista = new ArrayList<Persona>();
            while(rs.next()){
                Persona persona=new Persona();
                persona.setIdPersona(rs.getString("idpersona"));
                persona.setNombre(rs.getString("nombres"));
                persona.setApellido(rs.getString("apellidos"));
                lista.add(persona);
            } 
        rs.close();
        return lista;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //FOTO
    
     public static BufferedImage toBufferedImage(Image img){
    if (img instanceof BufferedImage)
    {
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage
        (img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
         Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
} 
   
   private Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("png");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {
            param.setSourceSubsampling(4, 4, 0, 0);
        }
        return reader.read(0, param);
    }
}
