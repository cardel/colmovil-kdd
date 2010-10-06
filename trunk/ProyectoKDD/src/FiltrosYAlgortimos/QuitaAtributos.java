/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltrosYAlgortimos;

/**
 *
 * @author Carlos
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import java.awt.BorderLayout;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class QuitaAtributos {

    /**
     * takes an ARFF file as first argument, the number of indices to remove
     * as second and thirdly whether to invert or not (true/false).
     * Dumps the generated data to stdout.
     */
    public void quitarAtributos(String consulta) {

        try {
            Instances instNew;
            Remove remove;

            InstanceQuery query = new InstanceQuery();
            query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
            query.setUsername("root");
            query.setPassword("");
            query.connectToDatabase();

            Instances data = query.retrieveInstances(consulta);

            remove = new Remove();
            //ESTOS SERAN LOS ATRIBUTOS QUE SE ELIMINARAN
            remove.setAttributeIndices("1,3,4,5,6,8,9");
            ///////////////////////////////////////////////////////////////
            remove.setInvertSelection(new Boolean("false").booleanValue());
            remove.setInputFormat(data);
            instNew = Filter.useFilter(data, remove);


            for (int i = 0; i < 4; i++) {
                System.out.println(data.instance(i));
            }

            System.out.println("----------------");
            System.out.println("Datos sin atributos");
            System.out.println("----------------");

            for (int i = 0; i < 4; i++) {
                System.out.println(instNew.instance(i));
            }


            PanelListaAtributos asp = new PanelListaAtributos();
            final javax.swing.JFrame jf =
                    new javax.swing.JFrame("PANEL DE LISTA DE ATRIBUTOS");
            jf.getContentPane().setLayout(new BorderLayout());
            jf.getContentPane().add(asp, BorderLayout.CENTER);
            jf.addWindowListener(new java.awt.event.WindowAdapter() {

                public void windowClosing(java.awt.event.WindowEvent e) {
                    jf.dispose();
                    System.exit(0);
                }
            });
            jf.pack();
            jf.setVisible(true);
            asp.setInstances(data);


            //////////////////////////////////////////////////HASTA AQUI ES PARA ELIMINAR ATRIBUTOS////////////////////////////////////////////////
            /*J48 cls = new J48();
            //Instances data = new Instances(new BufferedReader(new FileReader("./datos/copia1prueba.arff")));
            instNew.setClassIndex(instNew.numAttributes() - 1);
            cls.buildClassifier(instNew);

            // display classifier
            final javax.swing.JFrame jf =
            new javax.swing.JFrame("Visualizacion del Clasificador Algoritmo: J48");
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
            tv.fitToScreen();*/
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }
}
