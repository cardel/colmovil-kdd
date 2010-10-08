/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ColmovilGUI.java
 *
 * Created on 30/09/2010, 04:46:18 PM
 */

package GUI;

import Control.ConsultaNulos;
import Control.Controladora;
import Papelera.FachadaBD;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.print.DocFlavor.STRING;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gema
 */
public class ColmovilGUI extends javax.swing.JFrame {

    BufferedImage imagenDelGrafico= null;
    BufferedImage imagenDelGraficoDispersion= null;
    Vector <Vector> vectorNombreAtributos;//es una tabla
    Vector<String> vectorNombreColumnaTablaAtributos;
    Vector<String> vectorNombreTablas= new Vector<String>();
    Vector<Vector> vectorEstadidticas= new Vector<Vector>();
    Vector<String> vectorNombreColumnaTablaEstadisticas;
    String nombreTabla;
    /** Creates new form ColmovilGUI */
    public ColmovilGUI() {
        vectorNombreAtributos= new Vector<Vector>();
        vectorEstadidticas= new Vector<Vector>();
        //vectorNombreColumnaTablaEstadisticas= new Vector<String>();
        initComponents();
        inicializarNombreColumnasTablaAtributos();
        inicializarNombreColumnasTablaEstaditicasDatoNumerico();

        
//        StackedBarChart grafico= new StackedBarChart();
//        imagenDelGrafico= grafico.createStackedBarChart();
//        labelGrafico.setIcon(new ImageIcon(imagenDelGrafico));

        //Tabla
//        Object[][] data={
//            {"1", "", "equipo"},
//            {"1", "", "precio"},
//            {"1", "", "hola"}
//        };
//        String[] columnas={"No.", "boll", "Atributo"};
//        jTableAtributos.setModel(new DefaultTableModel(data, columnas));

//        Vector<String> nuevoVector=  new  Vector<String>();
//        nuevoVector.add("1");
//        nuevoVector.add("");
//        //nuevoVector.add(vectorAtributo.elementAt(i));
//        nuevoVector.add("hola");
//        vectorNombreAtributos.addElement(nuevoVector);

       jTableAtributos.setModel(new DefaultTableModel(vectorNombreAtributos, vectorNombreColumnaTablaAtributos));
       jTableEstadistica.setModel(new DefaultTableModel(vectorEstadidticas, vectorNombreColumnaTablaEstadisticas));
       jComboBoxNombreTablas.setEnabled(false);
       setLocationRelativeTo(null);
    }

    public void actualizarTabla()
    {
        jTableAtributos.setModel(new DefaultTableModel(vectorNombreAtributos, vectorNombreColumnaTablaAtributos));
        jTableAtributos.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTableAtributos.getColumnModel().getColumn(1).setPreferredWidth(30);
        jTableAtributos.getColumnModel().getColumn(2).setPreferredWidth(200);
    }

    public void actualizarComboBox()
    {
        jComboBoxNombreTablas.setModel(new DefaultComboBoxModel(vectorNombreTablas));
    }
    public void inicializarNombreColumnasTablaAtributos()
    {
        vectorNombreColumnaTablaAtributos = new Vector<String>();
        vectorNombreColumnaTablaAtributos.addElement("No.");
        vectorNombreColumnaTablaAtributos.addElement(" ");
        vectorNombreColumnaTablaAtributos.addElement("Atributos");
    }

    public void inicializarNombreColumnasTablaEstaditicasDatoNumerico()
    {
        vectorNombreColumnaTablaEstadisticas= new Vector<String>();
        vectorNombreColumnaTablaEstadisticas.addElement("Estadística");
        vectorNombreColumnaTablaEstadisticas.addElement("Valor");
    }
    public void inicializarNombreColumnasTablaEstaditicasDatoNominal()
    {
        vectorNombreColumnaTablaEstadisticas= new Vector<String>();
        vectorNombreColumnaTablaEstadisticas.addElement("Atributo");
        vectorNombreColumnaTablaEstadisticas.addElement("Cantidad");
    }

    public void actualizarTablaEstadisticas()
    {
        jTableEstadistica.setModel(new DefaultTableModel(vectorEstadidticas, vectorNombreColumnaTablaEstadisticas));
        jTableEstadistica.getColumnModel().getColumn(0).setPreferredWidth(200);
        jTableEstadistica.getColumnModel().getColumn(1).setPreferredWidth(80);
    }
    
    public void llenarTablaAtributos(String nombreTabla)
    {
        //vectorNombreAtributos.clear();
        Vector<String> vectorAtributo=  new  Vector <String> ();
        Controladora objControladora= new Controladora();
        //System.out.println(objControladora.consultaNombreAtributos());
        vectorAtributo = objControladora.consultaNombreAtributos(nombreTabla);
        
        for(int i=0; i<vectorAtributo.size(); i++)
        {
            Vector<Object> nuevoVector=  new  Vector<Object>();
            nuevoVector.addElement(Integer.toString(i+1));
            nuevoVector.addElement("");
            nuevoVector.addElement(vectorAtributo.elementAt(i));
            //nuevoVector.add("hola");
            

           //System.out.println("nuevo vector: "+ nuevoVector);
            vectorNombreAtributos.addElement(nuevoVector);
            //nuevoVector.clear();
            //System.out.println("vector tabla: "+ vectorNombreAtributos);
       }
        //System.out.println("vector tabla: "+ vectorNombreAtributos);

    }

    public void llenarComboBoxNombreTablas()
    {
        Controladora objControladora= new Controladora();
        vectorNombreTablas=objControladora.consultaNombreTablas();
//        for(int i=0; i<vector.size(); i++)
//        {
//            jComboBoxNombreTablas.addItem(vector.elementAt(i));
//        }
        
    }

    public void llenarTablaEstadisticasDatoNumerico(String nombreAtributo,String nombreTabla)
    {
      Vector<String> vectorNombreEstadisticas= new Vector<String>();
      //se crea el vector de la columna de nombres de la tabla estadisticas
      vectorNombreEstadisticas.add("Maximo");
      vectorNombreEstadisticas.add("Minimo");
      vectorNombreEstadisticas.add("Promedio");
      vectorNombreEstadisticas.add("Desv Est");
      Vector<String> vectorMax= new Vector<String>();
      Vector<String> vectorMin= new Vector<String>();
      Vector<String> vectorPromedio= new Vector<String>();
      Vector<String> vectorDesvEst= new Vector<String>();
      Vector<String> valoresEstadisticas= new Vector<String>();
      Controladora objControladora= new Controladora();
      vectorMax= objControladora.consultaMax(nombreAtributo, nombreTabla);
      vectorMin= objControladora.consultaMin(nombreAtributo, nombreTabla);
      vectorPromedio= objControladora.consultaPromedio(nombreAtributo, nombreTabla);
      vectorDesvEst= objControladora.consultaDesvEstandar(nombreAtributo, nombreTabla);
      //se crea el vector con los valores de las estadisitcas
      valoresEstadisticas.add(vectorMax.elementAt(0));
      valoresEstadisticas.add(vectorMin.elementAt(0));
      valoresEstadisticas.add(vectorPromedio.elementAt(0));
      valoresEstadisticas.add(vectorDesvEst.elementAt(0));
      for(int i=0; i<vectorNombreEstadisticas.size(); i++)
      {
          Vector<Object> nuevoVector= new Vector<Object>();
          nuevoVector.addElement(vectorNombreEstadisticas.elementAt(i));
          nuevoVector.addElement(valoresEstadisticas.elementAt(i));
          //lleno el vector de vectores
          vectorEstadidticas.addElement(nuevoVector);
          //System.out.println("vector tabla estadisticas: "+ vectorEstadidticas);
      }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButtonSeleccionarTodo = new javax.swing.JButton();
        jButtonNinguno = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAtributos = new javax.swing.JTable();
        jButtonEliminar = new javax.swing.JButton();
        jButtonLimpiar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButtonAbrir = new javax.swing.JButton();
        jButtonConexionBD = new javax.swing.JButton();
        jButtonGuardar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEstadistica = new javax.swing.JTable();
        jLabelNombre = new javax.swing.JLabel();
        jLabelNulos = new javax.swing.JLabel();
        jLabelTipo = new javax.swing.JLabel();
        jLabelDistinto = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jTextFieldNulos = new javax.swing.JTextField();
        jTextFieldTipo = new javax.swing.JTextField();
        jTextFieldDistinto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanelGraficoBarras = new javax.swing.JPanel();
        labelGrafico = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jComboBoxNombreTablas = new javax.swing.JComboBox();
        jPanelGraficoDispersion = new javax.swing.JPanel();
        jLabelGraficoDispersion = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setMaximumSize(new java.awt.Dimension(3276, 3276));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonSeleccionarTodo.setText("Seleccionar Todo");
        jButtonSeleccionarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarTodoActionPerformed(evt);
            }
        });

        jButtonNinguno.setText("Ninguno");

        /*
        jTableAtributos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        */
        jTableAtributos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAtributosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableAtributos);

        jButtonEliminar.setText("Eliminar");

        jButtonLimpiar.setText("Limpiar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jButtonEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                        .addComponent(jButtonLimpiar))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jButtonSeleccionarTodo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(jButtonNinguno)))
                .addGap(27, 27, 27))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSeleccionarTodo)
                    .addComponent(jButtonNinguno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEliminar)
                    .addComponent(jButtonLimpiar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonAbrir.setText("Abrir");
        jButtonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbrirActionPerformed(evt);
            }
        });

        jButtonConexionBD.setText("Conexion BD");
        jButtonConexionBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConexionBDActionPerformed(evt);
            }
        });

        jButtonGuardar.setText("Guardar");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAbrir)
                .addGap(18, 18, 18)
                .addComponent(jButtonConexionBD)
                .addGap(18, 18, 18)
                .addComponent(jButtonGuardar)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAbrir)
                    .addComponent(jButtonConexionBD)
                    .addComponent(jButtonGuardar)))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        /*
        jTableEstadistica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Datos Estadísticos", "Valor"
            }
        ));
        */
        jScrollPane2.setViewportView(jTableEstadistica);

        jLabelNombre.setText("Nombre:");

        jLabelNulos.setText("% Nulos Atributo: ");

        jLabelTipo.setText("Tipo:");

        jLabelDistinto.setText("Distinto:");

        jTextFieldNombre.setEditable(false);
        jTextFieldNombre.setText("Ninguno");
        jTextFieldNombre.setBorder(null);

        jTextFieldNulos.setEditable(false);
        jTextFieldNulos.setText("%");
        jTextFieldNulos.setBorder(null);

        jTextFieldTipo.setEditable(false);
        jTextFieldTipo.setText("Ninguno");
        jTextFieldTipo.setBorder(null);

        jTextFieldDistinto.setEditable(false);
        jTextFieldDistinto.setBorder(null);
        jTextFieldDistinto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDistintoActionPerformed(evt);
            }
        });

        jLabel1.setText("%Nulos Tabla:");

        jTextField1.setEditable(false);
        jTextField1.setText("%");
        jTextField1.setBorder(null);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelNulos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNulos, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTipo)
                            .addComponent(jLabelDistinto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldDistinto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelNombre)
                                .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelNulos)
                                .addComponent(jTextFieldNulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabelTipo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabelDistinto)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextFieldTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldDistinto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanelGraficoBarras.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelGraficoBarrasLayout = new javax.swing.GroupLayout(jPanelGraficoBarras);
        jPanelGraficoBarras.setLayout(jPanelGraficoBarrasLayout);
        jPanelGraficoBarrasLayout.setHorizontalGroup(
            jPanelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
        );
        jPanelGraficoBarrasLayout.setVerticalGroup(
            jPanelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccionar Tabla"));

        /*
        jComboBoxNombreTablas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        */
        jComboBoxNombreTablas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNombreTablasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jComboBoxNombreTablas, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBoxNombreTablas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelGraficoDispersion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelGraficoDispersionLayout = new javax.swing.GroupLayout(jPanelGraficoDispersion);
        jPanelGraficoDispersion.setLayout(jPanelGraficoDispersionLayout);
        jPanelGraficoDispersionLayout.setHorizontalGroup(
            jPanelGraficoDispersionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelGraficoDispersion, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
        );
        jPanelGraficoDispersionLayout.setVerticalGroup(
            jPanelGraficoDispersionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelGraficoDispersion, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelGraficoDispersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelGraficoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelGraficoBarras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelGraficoDispersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Preprocesamiento", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 711, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Asociación", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 711, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Clasificación", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbrirActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jButtonAbrirActionPerformed

    private void jButtonSeleccionarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarTodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSeleccionarTodoActionPerformed

    private void jButtonConexionBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConexionBDActionPerformed
        // TODO add your handling code here:
        jComboBoxNombreTablas.setEnabled(true);
        llenarComboBoxNombreTablas();
        actualizarComboBox();
    }//GEN-LAST:event_jButtonConexionBDActionPerformed

    private void jTableAtributosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAtributosMouseClicked
        // TODO add your handling code here:
        int fila= jTableAtributos.getSelectedRow();
        int porcentajeNulosPorAtributo=0;
        int porcentajeNulosPorRegistro=0;
        Controladora objControladora= new Controladora();
        ConsultaNulos objConsultaNulos= new ConsultaNulos();
        String nombreAtributo=jTableAtributos.getValueAt(fila, 2).toString();
        Vector<String> tipoAtributo=new Vector<String>();
        Vector<String> distinto= new Vector<String>();
        Vector<Integer> vectorDatosNumericos= new Vector<Integer>();
        tipoAtributo=objControladora.consultaTipoAtributo(nombreAtributo,nombreTabla);
        distinto= objControladora.consultaDistintos(nombreAtributo, nombreTabla);
        porcentajeNulosPorAtributo=objConsultaNulos.porcentajeValoresNulosPorAtributo(nombreTabla, nombreAtributo);
        porcentajeNulosPorRegistro=objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreTabla);
        //JOptionPane.showMessageDialog(null, "fila No.: "+fila);
        jTextFieldNombre.setText(nombreAtributo);
        jTextFieldDistinto.setText(distinto.elementAt(0));
        jTextFieldNulos.setText(Integer.toString(porcentajeNulosPorAtributo)+"%");
        jTextField1.setText(Integer.toString(porcentajeNulosPorRegistro)+"%");
        int cantidadNulos=objConsultaNulos.contarValoresNulosPorAtributo(nombreTabla, nombreAtributo);
        int cantidadRegistros= objConsultaNulos.totalRegistros(nombreTabla);
        //*********************  mostrar grafico de barras
        StackedBarChart grafico= new StackedBarChart();
        imagenDelGrafico= grafico.createStackedBarChart(cantidadNulos, cantidadRegistros);
        labelGrafico.setIcon(new ImageIcon(imagenDelGrafico));
        //**********************************************************************
        if(tipoAtributo.elementAt(0).equals("bigint") || tipoAtributo.elementAt(0).equals("int") || tipoAtributo.elementAt(0).equals("float"))
        {
            jTextFieldTipo.setText("Numerico");
            //**********  llenar tabla de estadísitcas para atributos numéricos
            inicializarNombreColumnasTablaEstaditicasDatoNumerico();
            vectorEstadidticas.clear();
            llenarTablaEstadisticasDatoNumerico(nombreAtributo, nombreTabla);
            actualizarTablaEstadisticas();
            //***************************  mostrar Grafico de Dispersion
            GraficoDispersion objGraficoDispersion= new GraficoDispersion();
            vectorDatosNumericos= objControladora.consultaGraficoDispersion(nombreAtributo, nombreTabla);
            imagenDelGraficoDispersion= objGraficoDispersion.crearGraficodispersion(vectorDatosNumericos, nombreAtributo);
            jLabelGraficoDispersion.setIcon(new ImageIcon(imagenDelGraficoDispersion));

        }
        else
        {
            if(tipoAtributo.elementAt(0).equals("varchar") || tipoAtributo.elementAt(0).equals("date"))
            {
                jTextFieldTipo.setText("Nominal");
                //***********  llenar tabla de estadísitcas para atributos nominales
                inicializarNombreColumnasTablaEstaditicasDatoNominal();
                vectorEstadidticas.clear();
                vectorEstadidticas=objControladora.consultaTablaEstadisticasAtributoNominal(nombreAtributo, nombreTabla);
                actualizarTablaEstadisticas();
                
            }
            else
            {
                jTextFieldTipo.setText(tipoAtributo.elementAt(0));
            }
        }
        
    }//GEN-LAST:event_jTableAtributosMouseClicked

    private void jComboBoxNombreTablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNombreTablasActionPerformed
        // TODO add your handling code here:
        nombreTabla=jComboBoxNombreTablas.getSelectedItem().toString();
        vectorNombreAtributos.clear();
        llenarTablaAtributos(nombreTabla);
        actualizarTabla();
    }//GEN-LAST:event_jComboBoxNombreTablasActionPerformed

    private void jTextFieldDistintoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDistintoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDistintoActionPerformed


    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ColmovilGUI().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAbrir;
    private javax.swing.JButton jButtonConexionBD;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonLimpiar;
    private javax.swing.JButton jButtonNinguno;
    private javax.swing.JButton jButtonSeleccionarTodo;
    private javax.swing.JComboBox jComboBoxNombreTablas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDistinto;
    private javax.swing.JLabel jLabelGraficoDispersion;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNulos;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelGraficoBarras;
    private javax.swing.JPanel jPanelGraficoDispersion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableAtributos;
    private javax.swing.JTable jTableEstadistica;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldDistinto;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldNulos;
    private javax.swing.JTextField jTextFieldTipo;
    private javax.swing.JLabel labelGrafico;
    // End of variables declaration//GEN-END:variables

}
