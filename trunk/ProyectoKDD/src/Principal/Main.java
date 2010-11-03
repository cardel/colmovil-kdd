/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Control.Controladora;
import FiltrosYAlgortimos.QuitaAtributos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Gema
 */
public class Main {

    public static void main(String args[]) throws ClassNotFoundException, InstantiationException {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            Controladora objControladora = new Controladora();
            objControladora.mostrarGUI();
            //objControladora.unirConsultaEstadisticas("estrato", "cliente");
//            objControladora.consultaNombreTablas();
//            objControladora.consultaNombreAtributos("equipo_celular");
//            objControladora.consultaTipoAtributo("nombre","cliente");
//            objControladora.consultaMax("estrato", "cliente");
//            objControladora.consultaMin("estrato", "cliente");
            //ConexionBDWeka fachadaBDWeka = new ConexionBDWeka();
            //fachadaBDWeka.conectarBaseWeka();
            //GuardarConsultarComoArff guardarConsultarComoArff = new GuardarConsultarComoArff();
            //guardarConsultarComoArff.guardarConsultarComoArff("select * from cliente where estrato <= 2", "prueba1.arff");
            //QuitaAtributos atributos = new QuitaAtributos();
            //atributos.quitarAtributos("select * from cliente where estrato <= 2");
            //ReemplazoNulos reemplazoNulos = new ReemplazoNulos();
            //reemplazoNulos.reemplazarNulos("select * from equipo_celular");
            //DatosStringANominales datosStringANominales = new DatosStringANominales();
            //datosStringANominales.convertirStringANominar("select * from plan_voz");
            //LlenarConMissing llenarConMissing = new LlenarConMissing();
            //llenarConMissing.llenarConMissingDatos("select * from equipo_celular");
   //         RecortarDatosEntrada recortarDatosEntrada = new RecortarDatosEntrada();
     //       recortarDatosEntrada.recontrarEntrada("select * from plan_datos", 60);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
