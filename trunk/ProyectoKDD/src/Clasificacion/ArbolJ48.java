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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.filters.unsupervised.attribute.Remove;
import java.io.FileReader;
import weka.filters.Filter;
import weka.core.Instances;


import weka.experiment.InstanceQuery;

import javax.swing.JFrame;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class ArbolJ48 {

    public static void main(String args[]) throws Exception {

        String nombre;
        String password;
        InstanceQuery query;

        nombre = "colmovil";
        password = "colmovil";
        query = new InstanceQuery();
        query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
        query.setUsername(nombre);
        query.setPassword(password);
        query.connectToDatabase();


        ///SE VA O NO SE VA
        // Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by genero, edad, causa, estado_civil");
        //  Instances data = query.retrieveInstances("select d1.fechaM, d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, causa, estado_civil");
        //  Instances data = query.retrieveInstances("select d1.fechaY, d1.fechaM, d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select YEAR(t1.fecha) as fechaY, MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, fechaY, causa, estado_civil");



        // ESTE ES EL VERDADERO SE VA O NO SE VA
        // Instances data = query.retrieveInstances("select d1.fechaM, d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, causa, estado_civil");
        // discretize.setOptions(new String[]{"-R","3","-B","4","-V","false"});
        //remove.setAttributeIndices("1,6");


        ///PERFIL PLANES

        //Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.tipo_plan, d1.estrato,count(*) as total from (select t1.genero, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t2.tipo_plan,t1.estrato from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente) as d1 group by genero, edad, tipo_plan,estrato");
        //Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.tipo_plan, d1.estado_civil,d1.estrato, count(*) as total from (select t1.genero, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t2.tipo_plan, t1.estado_civil,t1.estrato from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente) as d1 group by genero, edad, tipo_plan, estado_civil, estrato");
      //  Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.tipo_plan, d1.estado_civil  from (select t1.genero, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t2.tipo_plan, t1.estado_civil,t1.estrato from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente) as d1 group by genero, edad, tipo_plan, estado_civil, estrato");




        //PRUEBAS SE VAN
      //  Instances data = query.retrieveInstances("select DISTINCT genero,estado_civil,estrato,edad,se_va FROM cliente_2 WHERE edad < 20");

        // Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estado_civil,t1.estrato,t1.se_va,t1.edad,t3.nombre FROM cliente_3 t1, llamada012008 t2, operador t3, contrato t4 WHERE t1.edad < 20 AND t1.idcliente = t4.id_cliente AND t4.id_contrato = t2.id_contrato AND t2.id_operador_destino = t3.id_operador");
        //Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estado_civil,t1.se_va,t1.edad,t3.nombre FROM cliente_3 t1, llamada012008 t2, operador t3, contrato t4 WHERE t1.edad < 20 AND t1.idcliente = t4.id_cliente AND t4.id_contrato = t2.id_contrato AND t2.id_operador_destino = t3.id_operador");
        //Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estrato,t1.se_va,t1.edad,t3.nombre FROM cliente_3 t1, llamada012008 t2, operador t3, contrato t4 WHERE t1.edad < 20 AND t1.idcliente = t4.id_cliente AND t4.id_contrato = t2.id_contrato AND t2.id_operador_destino = t3.id_operador");
        //Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estado_civil,t1.tipo_identificacion,t1.estrato,t1.se_va,t1.edad,t3.nombre FROM cliente_3 t1, llamada012008 t2, operador t3, contrato t4 WHERE t1.edad < 20 AND t1.idcliente = t4.id_cliente AND t4.id_contrato = t2.id_contrato AND t2.id_operador_destino = t3.id_operador");

        //solo caracteristicas del cliente
        Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estado_civil,t1.estrato,t1.se_fue,t1.edad FROM vista_cliente_se_van3 t1");
        //Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estado_civil,t1.estrato_nominal,t1.se_fue,t1.edad_nominal FROM tabla_cliente_se_van3 t1");
        System.out.println("************datos: "+data);
         //********esta
        // Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estado_civil,t1.estrato,t1.se_fue,t1.edad,t3.nombre FROM vista_cliente_se_van3 t1, llamada012008 t2, vista_operador t3, vista_contrato t4 WHERE t1.edad < 20 AND t1.id_cliente = t4.id_cliente AND t4.id_contrato = t2.id_contrato AND t2.id_operador_destino = t3.id_operador ");

        // LOCALIZACION
        //Instances data = query.retrieveInstances( "SELECT t1.genero,t1.estado_civil,t1.estrato,t1.se_fue,t1.edad,t3.ciudad  FROM vista_cliente_se_van3 t1, vista_oficina t2, vista_localizacion t3, vista_contrato t4  WHERE t1.edad < 20 AND t1.id_cliente = t4.id_cliente AND t4.id_oficina = t2.id_oficina AND t2.id_localizacion = t3.idlocalizacion ");




        RemovePercentage rp=new RemovePercentage();
            rp.setPercentage(50);



            rp.setInputFormat(data);

           // RemovePercentage(data, (new String[]{"-M"}));
                  //  = new weka.filters.unsupervised.attribute.StringToNominal();
           // stringToNominal.setInputFormat(data);


       //ESTE ALGORITMO ELIMINA PORCENTAJE DE DATOS EN UN ARCHIVO
       //TIENE QUE SER UN DOUBLE O SI NO NO LO VA A TOMAR

            rp.setOptions(new String[]{"-P","5.0"});
           // -C col

          // data = RemovePercentage.useFilter(data, rp);


        Remove remove;
        remove = new Remove();
       // remove.setAttributeIndices("");
        ///////////////////////////////////////////////////////////////
     //   remove.setInvertSelection(new Boolean("false").booleanValue());
      //  remove.setInputFormat(data);
      //  data = Filter.useFilter(data, remove);
      //  System.out.println(data);

        //Instances instanciaSalida = new Instances(instanciaInterna);
        Instances instanciaSalida = new Instances(data);
        Discretize discretize = new Discretize();
        discretize.setInputFormat(data);
        //   discretize.setBins(15);
        //discretize.setAttributeIndicesArray(new int[]{2});

        //   -B



        //discretize.setInvertSelection(true);

        try {
            discretize.setInputFormat(data);
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
    }
}
