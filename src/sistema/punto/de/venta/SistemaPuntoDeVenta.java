/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.punto.de.venta;

import Views.Sistema;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author ASUS
 */
public class SistemaPuntoDeVenta {

    /**
     * @param args the command line arguments 
     */
    public static void main(String[] args) {
       // try {
        // TODO code application logic here
//        UIManager.setLookAndFeel("com.sun.java.swing.plaf.NimbusLookAndFeel");
     //   } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
       // Logger.getLogger(SistemaPuntoDeVenta.class.getName()).log(Level.SEVERE, null, ex);
       // }
        Sistema sistema = new Sistema ();
        sistema.setExtendedState(MAXIMIZED_BOTH);
        sistema.setVisible(true);
    }
    
    
}
