/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clasificacion;

import Clustering.RecortarDatosEntrada;
import Persistencia.FachadaBDConWeka;
import java.awt.BorderLayout;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.filters.Filter;

/**
 *
 * @author Carlos
 */
public class ArbolJ48ConInterfaz {

    Instances instancia;

    public ArbolJ48ConInterfaz() {
        instancia = null;
    }

    public void cargarConsulta(String consulta) {
        try {
            Persistencia.FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            instancia = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
        } catch (Exception e) {
        }

    }

    public String construirArbolJ48(int porcentaje, int indiceCombobox) {

        String salida = "";
        System.out.println("inice "+indiceCombobox);
        Instances instanciaSalida = new Instances(instancia);
        Discretize discretize = new Discretize();

        try {

            if (porcentaje < 100) {
                RecortarDatosEntrada recortarDatosEntrada = new RecortarDatosEntrada();
                instanciaSalida = recortarDatosEntrada.recontrarEntrada(instancia, porcentaje);
            }
             discretize.setInputFormat(instancia);

            if(indiceCombobox==4 || indiceCombobox==5 || indiceCombobox==6 || indiceCombobox==7)
            {
                //FILTRO 1
                discretize.setOptions(new String[]{"-R","5","-B","2S","-V","false"});
                System.out.println("llegooooooooooooooooooo filtro uno");
            }
             else
                 if(indiceCombobox==8 || indiceCombobox==9)
                 {
                      System.out.println("llegooooooooooooooooooo filtro dos");
                     //FILTRO 2
                     discretize.setOptions(new String[]{"-R","4","-B","2","-V","false"});
                 }else
                     if(false)
                     {
                          System.out.println("llegooooooooooooooooooo filtro tres");
                      //FILTRO 3
                      discretize.setOptions(new String[]{"-R","5","-B","2","-V","true"});
                     }

           
            //discretize.setOptions(new String[]{"-B", "5", "-V", "false"});
            instanciaSalida = Filter.useFilter(instanciaSalida, discretize);
            weka.filters.unsupervised.attribute.NumericToNominal prueba = new weka.filters.unsupervised.attribute.NumericToNominal();
            Filter fil = new weka.filters.unsupervised.attribute.NumericToNominal();
            prueba.setInputFormat(instanciaSalida);
            prueba.setOptions(new String[]{"-R", "first-last"});
            weka.core.Instances filteredData = fil.useFilter(instanciaSalida, prueba);

            // train classifier
            J48 cls = new J48();
            filteredData.setClassIndex(filteredData.numAttributes() - 1);
            cls.buildClassifier(filteredData);

            salida += "EJECUCION ALGORITMO J48\n";
            salida += "---------------------------------------------\n";
            salida += "\n" + instanciaSalida.toString();
            salida += "---------------------------------------------\n";
            salida += "\n" + cls.toString();

            final javax.swing.JFrame jf =
                    new javax.swing.JFrame("VISUALIZADOR DE ARBOL WEKA J48");
            jf.setSize(500, 400);
            jf.getContentPane().setLayout(new BorderLayout());
            TreeVisualizer tv = new TreeVisualizer(null,
                    cls.graph(),
                    new PlaceNode2());
            jf.getContentPane().add(tv, BorderLayout.CENTER);
            jf.addWindowListener(new java.awt.event.WindowAdapter() {

                public void windowClosing(java.awt.event.WindowEvent e) {
                    jf.dispose();
                }
            });

            jf.setVisible(true);
            tv.fitToScreen();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return salida;
    }
}
