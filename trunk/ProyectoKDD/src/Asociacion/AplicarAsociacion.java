/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Asociacion;

import Clustering.RecortarDatosEntrada;
import Persistencia.FachadaBDConWeka;
//import java.util.logging.Filter;
import javax.swing.JOptionPane;
import weka.core.Instances;
import weka.associations.Apriori;
import weka.associations.FPGrowth;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 *
 * @author Dayana
 */
public class AplicarAsociacion {

    Instances instancia;

    public AplicarAsociacion() {
        instancia = null;

    }

    public void realizarConsultaABaseDeDatosTipoWekaInstances(String consulta) {
        try {

            FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            instancia = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
            JOptionPane.showMessageDialog(null, "Datos cargados exitosamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema al cargar los datos, favor verifque su consulta");
            System.out.println(e.toString());
        }

    }

    public String aplicarAprioriWeka(Instances instanciaInterna,int algoritmo, int porcentaje, Double confianzaMinima) {
        String salida = "";
        this.instancia = instanciaInterna;
        if (porcentaje < 100) {
            RecortarDatosEntrada recortarDatosEntrada = new RecortarDatosEntrada();
            instanciaInterna = recortarDatosEntrada.recontrarEntrada(instancia, porcentaje);
        }

        switch (algoritmo) {
            case 0:
                salida = algoritmoApriori(instanciaInterna, confianzaMinima);
                break;
            case 1:
                salida = algoritmoFPGrowth(instanciaInterna, confianzaMinima);
                System.out.println("llega a FPG");
                break;
            default:
                break;

        }

        return salida;
    }

    public String algoritmoApriori(Instances instanciaInterna, Double confianzaMinima) {
        String salida = "";

        Apriori objApriori = new Apriori();
        objApriori.setMinMetric(confianzaMinima);

        try {
            objApriori.buildAssociations(instanciaInterna);

            salida = "RESULTADOS ASOCIACIÓN CON APRIORI";
            salida += "\n" + instanciaInterna.toString();
            salida += "\n" + objApriori.outputItemSetsTipText();
            salida += "\n" + objApriori.toString();
            salida += "\n--------------------------\n";
            salida += "\n";
            salida += "\n";
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return salida;
    }

    public String algoritmoFPGrowth(Instances instanciaInterna, Double confianzaMinima)
    {
        System.out.println("llega otra vez a FPG");
        String salida = "";
        FPGrowth objFPGrowth = new FPGrowth();
        objFPGrowth.setMinMetric(confianzaMinima);

        Instances instanciaSalida = new Instances(instanciaInterna);
        NominalToBinary objNominalToBinary = new NominalToBinary();

        NumericToNominal  objNumericToNominal = new NumericToNominal();

        try {
            objNominalToBinary.setInputFormat(instanciaInterna);
            instanciaSalida = Filter.useFilter(instanciaSalida, objNominalToBinary);
            objNumericToNominal.setInputFormat(instanciaSalida);
            instanciaSalida = Filter.useFilter(instanciaSalida, objNumericToNominal);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

            try {
            objFPGrowth.buildAssociations(instanciaSalida);

            salida = "RESULTADOS ASOCIACIÓN CON FPGrowth";
            salida += "\n" + instanciaInterna.toString();
            salida += "\n" + objFPGrowth.positiveIndexTipText();
            salida += "\n" + objFPGrowth.toString();
            salida += "\n--------------------------\n";
            salida += "\n";
            salida += "\n";
        } catch (Exception e) {
            System.out.println(e.toString());
        }


        return salida;

    }
}
