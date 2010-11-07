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
        //PROMEDIO DURACION LLAMADAS POR HORA, POR SEXO, EDAD Y ESTADO CIVIL
       // Instances data = query.retrieveInstances("select d1.hora, d1.genero, d1.edad, d1.estado_civil, AVG(duracion_segundos) as promedio_llamada from (select HOUR(t1.fecha_inicio) as hora, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, hora");
        //NUMERO DE LLAMADAS POR HORA, POR SEXO, POR EDAD Y ESTADO CIVIL
        // Instances data = query.retrieveInstances("select d1.hora, d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select HOUR(t1.fecha_inicio) as hora, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, hora")
        //NUMERO DE LLAMADAS POR DIAS , POR SEXO, EDAD Y ESTADO CIVIL.
        // Instances data = query.retrieveInstances("select d1.dia, d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select DAY(t1.fecha_inicio) as dia, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, dia")
        //USO DE LA RED POR DIA, POR SEXO, POR EDAD Y POR ESTADO CIVIL
        // Instances data = query.retrieveInstances("select d1.dia, d1.genero, d1.edad, d1.estado_civil, SUM(d1.duracion_segundos) as total_segundos from (select DAY(t1.fecha_inicio) as dia, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, dia")
        //PROMEDIO DURACION LLAMADA POR SEXO, EDAD Y ESTADO CIVIL.

        //DESDE AQUI SE UTILIZALAS OPCIONES CON EL INDICE DE ATRIBUTO 5
        Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil");
        //Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.estado_civil, AVG(d1.duracion_segundos) as promedio_llamada from (select t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil");
        // NUMERO DE LLAMADAS POR SEXO EDAD ESTADO CIVIL A DESTINO
      //nota st arbol sisal muy gran entoncs lo dejo aqui por si algo con el zoom de j48 vsi se puede ver
         // Instances data = query.retrieveInstances("select d1.pais_destino, d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select t1.pais_destino, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by pais_destino, genero, edad, estado_civil");
      // Instances data = query.retrieveInstances("select d1.pais_destino, d1.genero, d1.edad, d1.estado_civil, AVG(d1.duracion_segundos) as promedio_llamada from (select t1.duracion_segundos, t1.pais_destino, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by pais_destino, genero, edad, estado_civil");

        //PROMEDIO DURACION LLAMADAS POR SEXO,EDAD,ESTADO CIVIL A DESTINO
        //este ARBOL TAMBIEN ES GRANDE PERO CON EL ZOMM DE J48 SE VE BIEN
        //ESTE SE UTILIZA EL FILTRO 3
       // Instances data = query.retrieveInstances("select d1.pais_destino, d1.genero, d1.edad, d1.estado_civil, AVG(d1.duracion_segundos) as promedio_llamada from (select t1.duracion_segundos, t1.pais_destino, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from llamada012008 as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by pais_destino, genero, edad, estado_civil");
      // PLANES DE VOZ POR SEXO EDAD Y ESTADO CIVIL
        //filtro 1
       // Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.nombre, d1.estado_civil, count(*) as total from (select t1.genero, t1.estado_civil, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t3.nombre from vista_cliente as t1, vista_contrato as t2, vista_plan_voz as t3 where t1.idcliente=t2.id_cliente and t2.id_plan_voz=t3.id_plan_voz) as d1 group by genero, edad, estado_civil");
       //PLANES DE DATOS POR SEXO EDAD Y ESTADO CIVIL
       //ESTE TAMBIEN ES GRANDECITO FILTRO 1;
        //Instances data = query.retrieveInstances("select d1.genero, d1.edad, d1.nombre, d1.estado_civil, count(*) as total from (select t1.genero, t1.estado_civil, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t3.nombre from vista_cliente as t1, vista_contrato as t2, vista_plan_datos as t3 where t1.idcliente=t2.id_cliente and t2.id_plan_datos=t3.id_plan_datos) as d1 group by genero, edad, estado_civil");
        //MOALIDAD SERVICIO POR SEXO ESTRATO Y EDAD
        //FILTRO 1


        //Instances instanciaSalida = new Instances(instanciaInterna);
        Instances instanciaSalida = new Instances(data);
        Discretize discretize = new Discretize();
    //   discretize.setBins(15);
       //discretize.setAttributeIndicesArray(new int[]{2});

     //   -B



       //discretize.setInvertSelection(true);

        try {
            discretize.setInputFormat(data);
           // discretize.setOptions(new String[]{"-R","5","-B","2","-V","false"});
            discretize.setOptions(new String[]{"-R","4","-B","2","-V","false"});
           // discretize.setOptions(new String[]{"-R","5","-B","2","-V","true"});


            instanciaSalida=Filter.useFilter(instanciaSalida, discretize);

            } catch (Exception e) {
            System.out.println(e.toString());
        }
      //  return instanciaSalida;






                weka.filters.unsupervised.attribute.NumericToNominal prueba= new weka.filters.unsupervised.attribute.NumericToNominal();



      //  System.out.println(data);



        Filter fil=new weka.filters.unsupervised.attribute.NumericToNominal();
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
    jf.setSize(500,400);
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
