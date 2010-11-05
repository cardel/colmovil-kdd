/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Discretizacion;

import Control.ConsultasPredefinidas;
import Persistencia.FachadaBDConWeka;
import javax.swing.JOptionPane;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.Filter;

/**
 *
 * @author Carlos
 */
public class DiscretizeCardel {

    public Instances discretizar(Instances instanciaInterna, int numeroDeRangos) {
        Instances instanciaSalida = new Instances(instanciaInterna);
        Discretize discretize = new Discretize();
        discretize.setBins(numeroDeRangos);

        try {
            discretize.setInputFormat(instanciaInterna);
            
            instanciaSalida=Filter.useFilter(instanciaSalida, discretize);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return instanciaSalida;
    }

    public static void main(String args[]) {
        try {

            int numeroConsulta = 20;
            ConsultasPredefinidas consultasPredefinidas = new ConsultasPredefinidas();
            String consulta = consultasPredefinidas.retornarConsulta(numeroConsulta, null);
            FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            Instances instancia = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
            JOptionPane.showMessageDialog(null, "Datos cargados exitosamente");

            DiscretizeCardel discretizeCardel = new DiscretizeCardel();
            //El 10 indica que quiero 10 rangos.
            Instances salida=discretizeCardel.discretizar(instancia, 10);

            System.out.println("NORMAL");
            System.out.println(instancia.toString());

            System.out.println("DISCRETIZADO");
            System.out.println(salida.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema al cargar los datos, favor verifque su consulta");
            System.out.println(e.toString());
        }

    }
}
