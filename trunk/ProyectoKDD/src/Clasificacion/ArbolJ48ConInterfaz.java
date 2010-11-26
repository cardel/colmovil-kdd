/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clasificacion;

/**
 *
 * @author Pc
 */
import java.awt.BorderLayout;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.Filter;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class ArbolJ48ConInterfaz {

    public ArbolJ48ConInterfaz() {
    }


    public String construirArbolJ48(int porcentaje, int indiceCombobox, Instances instanciaGeneral) throws Exception
    {
        RemovePercentage rp=new RemovePercentage();
        rp.setPercentage(50);
        rp.setInputFormat(instanciaGeneral);


       //ESTE ALGORITMO ELIMINA PORCENTAJE DE DATOS EN UN ARCHIVO
       //TIENE QUE SER UN DOUBLE O SI NO NO LO VA A TOMAR

        rp.setOptions(new String[]{"-P","5.0"});
           // -C col



        Remove remove;
        remove = new Remove();
       // remove.setAttributeIndices("");
        ///////////////////////////////////////////////////////////////
     //   remove.setInvertSelection(new Boolean("false").booleanValue());
      //  remove.setInputFormat(data);
      //  data = Filter.useFilter(data, remove);
      //  System.out.println(data);

        //Instances instanciaSalida = new Instances(instanciaInterna);
        Instances instanciaSalida = new Instances(instanciaGeneral);
        Discretize discretize = new Discretize();
        discretize.setInputFormat(instanciaGeneral);
        //   discretize.setBins(15);
        //discretize.setAttributeIndicesArray(new int[]{2});

        //   -B


        try {
            discretize.setInputFormat(instanciaGeneral);
            // discretize.setOptions(new String[]{"-R","5","-B","2","-V","false"});
            discretize.setOptions(new String[]{"-R", "6", "-B", "2", "-V", "false" });
            // discretize.setOptions(new String[]{"-R","5","-B","2","-V","true"});


            instanciaSalida = Filter.useFilter(instanciaSalida, discretize);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //  return instanciaSalida;

        weka.filters.unsupervised.attribute.NumericToNominal prueba = new weka.filters.unsupervised.attribute.NumericToNominal();
        //  System.out.println(data);

        Filter fil = new weka.filters.unsupervised.attribute.NumericToNominal();
        prueba.setInputFormat(instanciaSalida);


        //ESTAS SON LAS OPCIONES PARA MOSTRAR LOS ATRIBUTOS ADICIONALES Q SON VALORES EXTREMOS Y OUTLIERS
        //RECORDAR ESTE FILTRO CREA DOS NUEVOS ATRIBUTOS POR ATRIBUTO NUMERICO
        //Q SERIA OUTLIERS Y VALORES EXTREMOS
        // prueba.setOptions(new String[]{"-R", "first-last","-O" ,"3.0","-E", "6.0","-P","-M","-E-as-O"});

        prueba.setOptions(new String[]{"-R", "first-last"});


        //ACA SE USA EL FILTRO
        weka.core.Instances filteredData = fil.useFilter(instanciaSalida, prueba);

        //System.out.println(filteredData);

        // train classifier
        J48 cls = new J48();
        // Instances data = new Instances(new BufferedReader(new FileReader("./datos/weather.arff")));
        filteredData.setClassIndex(filteredData.numAttributes() - 1);
        cls.buildClassifier(filteredData);

        System.out.println(cls.binarySplitsTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.confidenceFactorTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.debugTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.getRevision());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.getTechnicalInformation().toString());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.getTechnicalInformation().getRevision());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.globalInfo());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        // System.out.println(cls.graph());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.minNumObjTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.numFoldsTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        // System.out.println(cls.prefix());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.reducedErrorPruningTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.saveInstanceDataTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.seedTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.subtreeRaisingTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.toString());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.toSummaryString());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.unprunedTipText());
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println(cls.useLaplaceTipText());

        // display classifier
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
        return cls.toString();
    }
}
