/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimvc.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patricio
 */
public class ConexionPG {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    private String cadenaConexion="jdbc:postgresql://localhost:5432/mvc";
    private String usuarioPG="postgres";
    private String passPG="ista";

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ConexionPG() {
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            con=DriverManager.getConnection(cadenaConexion,usuarioPG,passPG);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
     public SQLException noQuery(String nsql){
        System.out.println(nsql);
        try {
            st=con.createStatement();
            st.execute(nsql);
            st.close();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }
    }
    
    public ResultSet query(String sql){
        System.out.println(sql);
        try {
            st=con.createStatement();
            ResultSet rs = st.executeQuery(sql);
         
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
            
    }
}
