/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clasificacion;

import Clustering.RecortarDatosEntrada;
import Persistencia.FachadaBDConWeka;
import java.awt.BorderLayout;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author Carlos
 */
public class ArbolJ48ConInterfaz {



    public ArbolJ48ConInterfaz() {

    }


    public String construirArbolJ48(int porcentaje, int indiceCombobox, Instances instanciaGeneral) {

        Instances instancia=instanciaGeneral;
        Instances instanciaSalida = new Instances(instancia);
        String salida = "";
        System.out.println("inice "+ indiceCombobox);
        System.out.println("porcentaje "+ indiceCombobox);
        System.out.println("instanciaGeneral "+ instanciaGeneral);
        //Discretize discretize = new Discretize();

        try {
/*
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
            instanciaSalida = Filter.useFilter(instanciaSalida, discretize);*/


            Instances data =instanciaGeneral;

            if(indiceCombobox==21)
            {
                Remove remove;
                remove = new Remove();
               // Instances data = query.retrieveInstances("select d1.fechaM, d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, causa, estado_civil");
                

              //  discretize.setOptions(new String[]{"-R","3","-B","4","-V","false"});
                remove.setAttributeIndices("1,6");
                remove.setInvertSelection(new Boolean("false").booleanValue());
                remove.setInputFormat(data);
                data = Filter.useFilter(data, remove);
                instanciaSalida = new Instances(data);



                Discretize discretize = new Discretize();
                discretize.setInputFormat(data);
                discretize.setOptions(new String[]{"-R", "4", "-B", ""+porcentaje, "-V", "false"});
                instanciaSalida = Filter.useFilter(instanciaSalida, discretize);
            }
               // ESTA ES PARA SI LA CONSULTA ES PERSONALIZADA
           /* if(personalizado=="si")
            {

                Discretize discretize = new Discretize();
                discretize.setInputFormat(data);
                discretize.setOptions(new String[]{"-R", ""+data.numAttributes(), "-B", ""+porcentaje, "-V", "false"});
                instanciaSalida = Filter.useFilter(instanciaSalida, discretize);
            }*/

           










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
            //System.out.println(e.toString());
        }
        return salida;
    }
}
