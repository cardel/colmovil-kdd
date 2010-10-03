/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Principal;

import Control.Controladora;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Gema
 */
public class Main {
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ColmovilGUI objColmovilGUI= new ColmovilGUI();
//        objColmovilGUI.setVisible(true);

        Controladora objControladora= new Controladora();
        objControladora.mostrarGUI();
        //objControladora.consultaNombreAtributos();
        //objControladora.consultaTipoAtributo("nombre","cliente");

    }
}
