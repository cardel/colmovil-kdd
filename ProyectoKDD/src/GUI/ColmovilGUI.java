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

import Asociacion.AplicarAsociacion;
import Clasificacion.ArbolJ48ConInterfaz;
import Clustering.AplicarClustering;
import Control.ConsultaNulos;
import Control.ConsultasPredefinidas;
import Control.ConsultasVistas;
import Control.Controladora;
import Discretizacion.DiscretizeCardel;
import Persistencia.FachadaBDConWeka;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import weka.core.Instances;

/**
 *
 * @author Gema
 */
public class ColmovilGUI extends javax.swing.JFrame {

    BufferedImage imagenDelGrafico = null;
    BufferedImage imagenDelGraficoDispersion = null;
    Vector<Vector> vectorNombreAtributos;//es una tabla
    Vector<String> vectorNombreColumnaTablaAtributos;
    Vector<String> vectorNombreTablas = new Vector<String>();
    Vector<Vector> vectorEstadidticas = new Vector<Vector>();
    Vector<String> vectorNombreColumnaTablaEstadisticas;
    Vector<String> vectorAtributo;
    Vector<String> vectorAtributosSeleccionados;
    String nombreTabla;
    String consultaAsociacion;
    ModeloTablaAtributos modeloTablaAtributos;
    ConsultasVistas objConsultasVistas;
    DiscretizarGUI objDiscretizarGUI;
    LimpiarOutliersGUI objLimpiarOutliersGUI;
    int indice;
    int valorIntervaloDiscretizacion;// este es el rango de discretizacion
    /*
     * INICIO CLUSTERING
     */
    AplicarClustering aplicarClustering = new AplicarClustering();
    /*
     * FINAL CLUSTERING
     */
    /*
     * INICIO ASOCIACION
     */
    AplicarAsociacion aplicarAsociacion;
    /*
     * FINAL ASOCIACION
     */
    /*
     * INICIO CLASIFICACION
     */
    ArbolJ48ConInterfaz arbolJ48ConInterfaz = new ArbolJ48ConInterfaz();

    /*
     * FIN CLASIFICACION
     */
    /*
     * Instancia general
     */
    Instances instanciaGeneral;
    Instances instanciaSinDiscretizar;// esta instancia la usa heberth

    /** Creates new form ColmovilGUI */
    public ColmovilGUI() {
//        objConsultasVistas= new ConsultasVistas();
//        objConsultasVistas.borrarVistas();
//        objConsultasVistas.crearVistas();
        vectorNombreAtributos = new Vector<Vector>();
        vectorEstadidticas = new Vector<Vector>();
        //vectorNombreColumnaTablaEstadisticas= new Vector<String>();
        initComponents();
        inicializarNombreColumnasTablaEstaditicasDatoNumerico();

//        jTableAtributos.setModel(new DefaultTableModel(vectorNombreAtributos, vectorNombreColumnaTablaAtributos));
//        jTableEstadistica.setModel(new DefaultTableModel(vectorEstadidticas, vectorNombreColumnaTablaEstadisticas));
        modeloTablaAtributos = new ModeloTablaAtributos();
        jTableAtributos.setModel(modeloTablaAtributos);
        //jTableAtributos.removeColumnSelectionInterval(2, 2);
        jComboBoxNombreTablas.setEnabled(false);
        //jButtonLimpiarNulos.setEnabled(false);
        //jButtonCargarPerfilPreproc.setEnabled(false);
        setLocationRelativeTo(null);
        objDiscretizarGUI= new DiscretizarGUI();

        /*
         * Instancia general
         */
        instanciaGeneral = null;
        valorIntervaloDiscretizacion=0;
    }

    //*********************************************  MODELO DE LA TABLA ********************************************
    class ModeloTablaAtributos extends AbstractTableModel {

        private String[] columnNames = {"No.",
            "",
            "Atributos"};
        private Object data[][];

        public ModeloTablaAtributos() {
            data = new Object[vectorNombreAtributos.size()][3];
            //System.out.println("*****  entra: ");
            //System.out.println("*****  tamaño de vector de vectores: "+vectorNombreAtributos.size());
            if (vectorNombreAtributos.size() > 0) {
                //System.out.println("*****  pos 0,0: "+vectorNombreAtributos.elementAt(0).elementAt(0));
                for (int i = 0; i < vectorNombreAtributos.size(); i++) {
                    for (int j = 0; j < 3; j++) {
                        data[i][j] = vectorNombreAtributos.elementAt(i).elementAt(j);
                        //System.out.println("*****  vector: "+ data[i][j]);
                    }
                }
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == 1) {
                return true;
            } else {
                return false;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

        public void seleccionarTodosLosAtributos() {
            for (int i = 0; i < vectorNombreAtributos.size(); i++) {
                //System.out.println("data: "+data[i][1]);
                data[i][1] = true;
            }
            fireTableRowsUpdated(0, vectorNombreAtributos.size() - 1);
        }

        public void DeseleccionarTodosLosAtributos() {
            for (int i = 0; i < vectorNombreAtributos.size(); i++) {
                //System.out.println("data: "+data[i][1]);
                data[i][1] = false;
            }
            fireTableRowsUpdated(0, vectorNombreAtributos.size() - 1);
        }

//        public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
//        {
//                    if(columnIndex==0 || columnIndex==1)
//                        return;
//        }
    }
    //**************************************************************************** FIN MODELO DE LA TABLA  **************

    public void actualizarTabla() {
        //jTableAtributos.setModel(new DefaultTableModel(vectorNombreAtributos, vectorNombreColumnaTablaAtributos));
        modeloTablaAtributos = new ModeloTablaAtributos();
        jTableAtributos.setModel(modeloTablaAtributos);
        jTableAtributos.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTableAtributos.getColumnModel().getColumn(1).setPreferredWidth(30);
        jTableAtributos.getColumnModel().getColumn(2).setPreferredWidth(200);
    }

    public void actualizarComboBox() {
        vectorNombreTablas.remove("llamada");
        jComboBoxNombreTablas.setModel(new DefaultComboBoxModel(vectorNombreTablas));
    }

    public void inicializarNombreColumnasTablaEstaditicasDatoNumerico() {
        vectorNombreColumnaTablaEstadisticas = new Vector<String>();
        vectorNombreColumnaTablaEstadisticas.addElement("Estadística");
        vectorNombreColumnaTablaEstadisticas.addElement("Valor");
    }

    public void inicializarNombreColumnasTablaEstaditicasDatoNominal() {
        vectorNombreColumnaTablaEstadisticas = new Vector<String>();
        vectorNombreColumnaTablaEstadisticas.addElement("Atributo");
        vectorNombreColumnaTablaEstadisticas.addElement("Cantidad");
    }

    public void actualizarTablaEstadisticas() {
        jTableEstadistica.setModel(new DefaultTableModel(vectorEstadidticas, vectorNombreColumnaTablaEstadisticas));
        jTableEstadistica.getColumnModel().getColumn(0).setPreferredWidth(200);
        jTableEstadistica.getColumnModel().getColumn(1).setPreferredWidth(80);
    }

    public void llenarTablaAtributos(String nombreTabla) {
        //vectorNombreAtributos.clear();
        //String nombreVista = "vista_" + nombreTabla;
        String nombreVista="";
        //System.out.println("++++++++++++++  nombre tabla: "+nombreTabla.substring(0,7));
        if(nombreTabla.substring(0,7).equals("llamada") || nombreTabla.substring(0,7).equals("recarga"))
        {
            nombreVista=nombreTabla;
        }
        else
        {
            nombreVista = "vista_" + nombreTabla;
        }
        
        vectorAtributo = new Vector<String>();
        Controladora objControladora = new Controladora();
        //System.out.println(objControladora.consultaNombreAtributos());
        vectorAtributo = objControladora.consultaNombreAtributos(nombreVista);

        for (int i = 0; i < vectorAtributo.size(); i++) {
            Vector<Object> nuevoVector = new Vector<Object>();
            nuevoVector.addElement(Integer.toString(i + 1));
            nuevoVector.addElement(new Boolean(false));
            nuevoVector.addElement(vectorAtributo.elementAt(i));
            //nuevoVector.add("hola");


            //System.out.println("nuevo vector: "+ nuevoVector);
            vectorNombreAtributos.addElement(nuevoVector);
            //nuevoVector.clear();
            //System.out.println("vector tabla atributos: "+ vectorNombreAtributos);
        }
        //System.out.println("vector tabla: "+ vectorNombreAtributos);

    }

    public void llenarComboBoxNombreTablas() {
        Controladora objControladora = new Controladora();
        vectorNombreTablas = objControladora.consultaNombreTablas();
        System.out.println("********* entra");
//        for(int i=0; i<vector.size(); i++)
//        {
//            jComboBoxNombreTablas.addItem(vector.elementAt(i));
//        }

    }

    public void llenarTablaEstadisticasDatoNumerico(String nombreAtributo, String nombreTabla) {
        Vector<String> vectorNombreEstadisticas = new Vector<String>();
        //String nombreVista = "vista_" + nombreTabla;
        String nombreVista="";
        if(nombreTabla.substring(0,7).equals("llamada") || nombreTabla.substring(0,7).equals("recarga"))
        {
            nombreVista=nombreTabla;
        }
        else
        {
            nombreVista = "vista_" + nombreTabla;
        }
        //se crea el vector de la columna de nombres de la tabla estadisticas
        vectorNombreEstadisticas.add("Maximo");
        vectorNombreEstadisticas.add("Minimo");
        vectorNombreEstadisticas.add("Promedio");
        vectorNombreEstadisticas.add("Desv Est");

        Vector<String> valoresEstadisticas = new Vector<String>();
        Vector<String> vectorTotalEstadisticas = new Vector<String>();
        Controladora objControladora = new Controladora();
        vectorTotalEstadisticas = objControladora.unirConsultaEstadisticas(nombreAtributo, nombreVista);// se unió todas las consultas en una sola
        //se crea el vector con los valores de las estadisitcas
        valoresEstadisticas.add(vectorTotalEstadisticas.elementAt(0));// en la posicion 0 esta en maximo
        valoresEstadisticas.add(vectorTotalEstadisticas.elementAt(1));// en la pos 1 esta el minimo
        valoresEstadisticas.add(vectorTotalEstadisticas.elementAt(2));// en la pos 2 esta el promedio
        valoresEstadisticas.add(vectorTotalEstadisticas.elementAt(3));// en la pos 3 esta la desv. estandar


        for (int i = 0; i < vectorNombreEstadisticas.size(); i++) {
            Vector<Object> nuevoVector = new Vector<Object>();
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

        jScrollPanePrincipal = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButtonSeleccionarTodo = new javax.swing.JButton();
        jButtonNinguno = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAtributos = new javax.swing.JTable();
        jButtonEliminarAtributos = new javax.swing.JButton();
        jButtonDeshacer = new javax.swing.JButton();
        jButtonEliminarOutliers = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButtonConexionBD = new javax.swing.JButton();
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
        jTextFieldNulosTabla = new javax.swing.JTextField();
        jPanelGraficoBarras = new javax.swing.JPanel();
        labelGrafico = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jComboBoxNombreTablas = new javax.swing.JComboBox();
        jPanelGraficoDispersion = new javax.swing.JPanel();
        jLabelGraficoDispersion = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabelPerfilPreproc = new javax.swing.JLabel();
        jSpinnerMesPreproc = new javax.swing.JSpinner();
        jLabelMesPreproc = new javax.swing.JLabel();
        jComboBoxPerfilPreproc = new javax.swing.JComboBox();
        jButtonCargarPerfilPreproc = new javax.swing.JButton();
        jLabelConsPersonPreproc = new javax.swing.JLabel();
        jTextFieldConsultaSQLPreproc = new javax.swing.JTextField();
        jButtonConsultaSQL = new javax.swing.JButton();
        jButtonDiscretizar = new javax.swing.JButton();
        jButtonLimpiarNulos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanelSalidaAsoc = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaAsoc = new javax.swing.JTextArea();
        jLabelTituloAsoc = new javax.swing.JLabel();
        jPanelParametrosAsoc = new javax.swing.JPanel();
        jLabelAlgoritmoAsoc = new javax.swing.JLabel();
        jComboBoxAlgoritmoAsoc = new javax.swing.JComboBox();
        jLabelPorcentajeAsoc = new javax.swing.JLabel();
        jSpinnerPorcentajeAsoc = new javax.swing.JSpinner();
        jButtonEjecutarAsoc = new javax.swing.JButton();
        jButtonLimpiarAsoc = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        confianzaMinima = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        botonLimpiarAreaClasificacion = new javax.swing.JButton();
        botonEjecutarClasificacion = new javax.swing.JButton();
        porcentajeDatosClasificacion = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        comboAlgortimoClasificacion = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        areaMostrarResultadosAsociacion = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        comboAlgortimo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        botonLimpiarArea = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        botonEjecutar = new javax.swing.JButton();
        seleccionNumeroClusters = new javax.swing.JSpinner();
        porcentajeDatos = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaMostrarResultados = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(3276, 3276));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonSeleccionarTodo.setText("Seleccionar Todo");
        jButtonSeleccionarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarTodoActionPerformed(evt);
            }
        });

        jButtonNinguno.setText("Ninguno");
        jButtonNinguno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNingunoActionPerformed(evt);
            }
        });

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
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        */
        jTableAtributos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAtributosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableAtributos);

        jButtonEliminarAtributos.setText("Eliminar Atributos");
        jButtonEliminarAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarAtributosActionPerformed(evt);
            }
        });

        jButtonDeshacer.setText("Deshacer");
        jButtonDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeshacerActionPerformed(evt);
            }
        });

        jButtonEliminarOutliers.setText("Eliminar Outliers");
        jButtonEliminarOutliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarOutliersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonSeleccionarTodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonEliminarAtributos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jButtonNinguno)
                                .addGap(32, 32, 32)
                                .addComponent(jButtonDeshacer))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jButtonEliminarOutliers)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSeleccionarTodo)
                    .addComponent(jButtonNinguno)
                    .addComponent(jButtonDeshacer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEliminarAtributos)
                    .addComponent(jButtonEliminarOutliers))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonConexionBD.setText("Conexion BD");
        jButtonConexionBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConexionBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(jButtonConexionBD)
                .addGap(96, 96, 96))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jButtonConexionBD))
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

        jTextFieldNulosTabla.setEditable(false);
        jTextFieldNulosTabla.setText("%");
        jTextFieldNulosTabla.setBorder(null);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(jTextFieldTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextFieldNulosTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
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
                    .addComponent(jTextFieldNulosTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelGraficoBarras.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelGraficoBarrasLayout = new javax.swing.GroupLayout(jPanelGraficoBarras);
        jPanelGraficoBarras.setLayout(jPanelGraficoBarrasLayout);
        jPanelGraficoBarrasLayout.setHorizontalGroup(
            jPanelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelGrafico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        jPanelGraficoBarrasLayout.setVerticalGroup(
            jPanelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
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
            .addComponent(jLabelGraficoDispersion, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        jPanelGraficoDispersionLayout.setVerticalGroup(
            jPanelGraficoDispersionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelGraficoDispersion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Carga de Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabelPerfilPreproc.setText("Perfil");

        jSpinnerMesPreproc.setModel(new javax.swing.SpinnerListModel(new String[] {"012008", "022008", "032008", "042008", "052008", "062008", "072008", "082008", "092008", "102008", "112008", "122008", "012009", "022009", "032009", "042009", "052009", "062009", "072009", "082009", "092009", "102009", "112009", "122009"}));

        jLabelMesPreproc.setText("Mes");

        jComboBoxPerfilPreproc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nùmero llamadas por hora", "Promedio duracion llamadas por hora", "Numero llamadas por dia", "Uso de la red por dia", "Nùmero llamadas por hora por sexo, edad y estado civil", "Promedio duracion llamadas por hora por sexo, edad y estado civil", "Numero llamadas por dia por sexo, edad y estado civil", "Uso de la red por dia por sexo, edad y estado civil", "Número llamadas por sexo, edad y estado civil", "Promedio duración llamadas por sexo, edad y estado civil", "Número llamadas por sexo, edad y estado civil a destino", "Promedio duración llamadas por sexo, edad y estado civil a destino", "Planes de voz por sexo, edad y estado civil", "Planes de datos por sexo, edad y estado civil ", "Modalidad servicio por sexo, estrato y edad", "Modalidad servicio por sexo, edad, estrato y estado civil", "Numero de recargas por medio por día", "Valor promedio por recargas por medio por día", "Causa retiro por sexo, edad y estado civil", "Causa retiro por sexo, edad y estado civil por mes en todos los años", "Causa retiro por sexo, edad y estado civil por mes en cada año", "Perfil plan prepago" }));
        jComboBoxPerfilPreproc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPerfilPreprocActionPerformed(evt);
            }
        });

        jButtonCargarPerfilPreproc.setText("Cargar Perfil");
        jButtonCargarPerfilPreproc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargarPerfilPreprocActionPerformed(evt);
            }
        });

        jLabelConsPersonPreproc.setText("Consulta Personalizada");

        jButtonConsultaSQL.setText("Consulta SQL");
        jButtonConsultaSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultaSQLActionPerformed(evt);
            }
        });

        jButtonDiscretizar.setText("Discretizar");
        jButtonDiscretizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDiscretizarActionPerformed(evt);
            }
        });

        jButtonLimpiarNulos.setText("Limpiar Nulos");
        jButtonLimpiarNulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarNulosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPerfilPreproc)
                            .addComponent(jLabelMesPreproc))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jComboBoxPerfilPreproc, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(jButtonDiscretizar, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonLimpiarNulos))
                            .addComponent(jSpinnerMesPreproc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabelConsPersonPreproc)
                        .addGap(10, 10, 10)
                        .addComponent(jTextFieldConsultaSQLPreproc, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButtonCargarPerfilPreproc))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButtonConsultaSQL)))
                .addGap(51, 51, 51))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPerfilPreproc)
                    .addComponent(jComboBoxPerfilPreproc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDiscretizar)
                    .addComponent(jButtonLimpiarNulos)
                    .addComponent(jButtonCargarPerfilPreproc))
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinnerMesPreproc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMesPreproc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldConsultaSQLPreproc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonConsultaSQL))
                    .addComponent(jLabelConsPersonPreproc))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelGraficoDispersion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jPanelGraficoBarras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(401, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelGraficoBarras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelGraficoDispersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(345, 345, 345))
        );

        jTabbedPane1.addTab("Preprocesamiento", jPanel1);

        jPanelSalidaAsoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jTextAreaAsoc.setColumns(20);
        jTextAreaAsoc.setRows(5);
        jScrollPane3.setViewportView(jTextAreaAsoc);

        javax.swing.GroupLayout jPanelSalidaAsocLayout = new javax.swing.GroupLayout(jPanelSalidaAsoc);
        jPanelSalidaAsoc.setLayout(jPanelSalidaAsocLayout);
        jPanelSalidaAsocLayout.setHorizontalGroup(
            jPanelSalidaAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
        );
        jPanelSalidaAsocLayout.setVerticalGroup(
            jPanelSalidaAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSalidaAsocLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelTituloAsoc.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelTituloAsoc.setText("Procesamiento de datos utilizando Asociación");

        jPanelParametrosAsoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parámetros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabelAlgoritmoAsoc.setText("Seleccione Algoritmo");

        jComboBoxAlgoritmoAsoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "APriori", "FPGrowth" }));
        jComboBoxAlgoritmoAsoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAlgoritmoAsocActionPerformed(evt);
            }
        });

        jLabelPorcentajeAsoc.setText("Porcentaje Datos");

        jSpinnerPorcentajeAsoc.setModel(new javax.swing.SpinnerNumberModel(10, 10, 100, 10));

        jButtonEjecutarAsoc.setText("Ejecutar");
        jButtonEjecutarAsoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEjecutarAsocActionPerformed(evt);
            }
        });

        jButtonLimpiarAsoc.setText("Limpiar");
        jButtonLimpiarAsoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarAsocActionPerformed(evt);
            }
        });

        jLabel14.setText("Confianza minima");

        confianzaMinima.setModel(new javax.swing.SpinnerNumberModel(0.5d, 0.1d, 1.0d, 0.05d));

        javax.swing.GroupLayout jPanelParametrosAsocLayout = new javax.swing.GroupLayout(jPanelParametrosAsoc);
        jPanelParametrosAsoc.setLayout(jPanelParametrosAsocLayout);
        jPanelParametrosAsocLayout.setHorizontalGroup(
            jPanelParametrosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametrosAsocLayout.createSequentialGroup()
                .addGroup(jPanelParametrosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelParametrosAsocLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelParametrosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerPorcentajeAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAlgoritmoAsoc)
                            .addComponent(jComboBoxAlgoritmoAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPorcentajeAsoc)))
                    .addGroup(jPanelParametrosAsocLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addGroup(jPanelParametrosAsocLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanelParametrosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonEjecutarAsoc)
                            .addComponent(jButtonLimpiarAsoc, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))))
                .addContainerGap(92, Short.MAX_VALUE))
            .addGroup(jPanelParametrosAsocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(confianzaMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        jPanelParametrosAsocLayout.setVerticalGroup(
            jPanelParametrosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametrosAsocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAlgoritmoAsoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxAlgoritmoAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelPorcentajeAsoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSpinnerPorcentajeAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confianzaMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButtonEjecutarAsoc)
                .addGap(18, 18, 18)
                .addComponent(jButtonLimpiarAsoc)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanelParametrosAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jPanelSalidaAsoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(418, 418, 418))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(225, Short.MAX_VALUE)
                .addComponent(jLabelTituloAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(560, 560, 560))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabelTituloAsoc)
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelSalidaAsoc, 0, 385, Short.MAX_VALUE)
                    .addComponent(jPanelParametrosAsoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(521, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Asociación", jPanel3);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel15.setText("Procesamiento de datos utilizando clasificación");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parametros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel21.setText("Limpiar area de texto");

        jLabel19.setText("Rango Discretizacion");

        botonLimpiarAreaClasificacion.setText("Limpiar");
        botonLimpiarAreaClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarAreaClasificacionActionPerformed(evt);
            }
        });

        botonEjecutarClasificacion.setText("Ejecutar");
        botonEjecutarClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarClasificacionActionPerformed(evt);
            }
        });

        porcentajeDatosClasificacion.setModel(new javax.swing.SpinnerNumberModel(100, 1, 100, 1));

        jLabel20.setText("Ejecutar algortimo");

        jLabel18.setText("Seleccione algortimo clustering");

        comboAlgortimoClasificacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "J48" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboAlgortimoClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(porcentajeDatosClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel20)
                        .addComponent(jLabel19)))
                .addContainerGap(55, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addContainerGap(99, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonLimpiarAreaClasificacion)
                    .addComponent(botonEjecutarClasificacion))
                .addGap(78, 78, 78))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboAlgortimoClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(porcentajeDatosClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(botonEjecutarClasificacion)
                .addGap(31, 31, 31)
                .addComponent(jLabel21)
                .addGap(28, 28, 28)
                .addComponent(botonLimpiarAreaClasificacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        areaMostrarResultadosAsociacion.setColumns(20);
        areaMostrarResultadosAsociacion.setRows(5);
        jScrollPane5.setViewportView(areaMostrarResultadosAsociacion);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel15))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(43, 43, 43)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(415, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(518, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clasificación", jPanel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setText("Procesamiento de datos utilizando clustering");

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parametros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        comboAlgortimo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "K-Means", "DBScan" }));
        comboAlgortimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAlgortimoActionPerformed(evt);
            }
        });

        jLabel8.setText("Ejecutar algortimo");

        botonLimpiarArea.setText("Limpiar");
        botonLimpiarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarAreaActionPerformed(evt);
            }
        });

        jLabel12.setText("Número de clusters (Sólo K-Means)");

        jLabel9.setText("Limpiar area de texto");

        jLabel6.setText("Seleccione algortimo clustering");

        botonEjecutar.setText("Ejecutar");
        botonEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarActionPerformed(evt);
            }
        });

        seleccionNumeroClusters.setModel(new javax.swing.SpinnerNumberModel(4, 2, 50, 1));

        porcentajeDatos.setModel(new javax.swing.SpinnerNumberModel(100, 1, 100, 1));

        jLabel7.setText("Porcentaje datos");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboAlgortimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(seleccionNumeroClusters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(139, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(porcentajeDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(134, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(119, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonLimpiarArea)
                    .addComponent(botonEjecutar))
                .addGap(71, 71, 71))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(comboAlgortimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(seleccionNumeroClusters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(porcentajeDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEjecutar)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(botonLimpiarArea)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        areaMostrarResultados.setColumns(20);
        areaMostrarResultados.setRows(5);
        jScrollPane4.setViewportView(areaMostrarResultados);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel3)))
                .addContainerGap(418, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(527, 527, 527))
        );

        jTabbedPane1.addTab("Clustering", jPanel8);

        jScrollPanePrincipal.setViewportView(jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPanePrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanePrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSeleccionarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarTodoActionPerformed
        // TODO add your handling code here:
        modeloTablaAtributos.seleccionarTodosLosAtributos();
    }//GEN-LAST:event_jButtonSeleccionarTodoActionPerformed

    private void jButtonConexionBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConexionBDActionPerformed
        // TODO add your handling code here:
        jComboBoxNombreTablas.setEnabled(true);
        llenarComboBoxNombreTablas();
        actualizarComboBox();
    }//GEN-LAST:event_jButtonConexionBDActionPerformed

    private void jTableAtributosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAtributosMouseClicked
        // TODO add your handling code here:
       // String nombreVista = "vista_" + nombreTabla;
        String nombreVista="";
        if(nombreTabla.substring(0,7).equals("llamada") || nombreTabla.substring(0,7).equals("recarga"))
        {
            nombreVista=nombreTabla;
        }
        else
        {
            nombreVista = "vista_" + nombreTabla;
        }
        int fila = jTableAtributos.getSelectedRow();
        int columna=jTableAtributos.getSelectedColumn();
        int porcentajeNulosPorAtributo = 0;
        //int porcentajeNulosPorRegistro = 0;
        if(columna==2)
        {
            Controladora objControladora = new Controladora();
            ConsultaNulos objConsultaNulos = new ConsultaNulos();
            String nombreAtributo = jTableAtributos.getValueAt(fila, 2).toString();
            Vector<String> tipoAtributo = new Vector<String>();
            Vector<String> distinto = new Vector<String>();
            Vector<Integer> vectorDatosNumericos = new Vector<Integer>();
            tipoAtributo = objControladora.consultaTipoAtributo(nombreAtributo, nombreVista);
            distinto = objControladora.consultaDistintos(nombreAtributo, nombreVista);
            porcentajeNulosPorAtributo = objConsultaNulos.porcentajeValoresNulosPorAtributo(nombreVista, nombreAtributo);
            //porcentajeNulosPorRegistro = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreTabla);
            //JOptionPane.showMessageDialog(null, "fila No.: "+fila);
            jTextFieldNombre.setText(nombreAtributo);
            jTextFieldDistinto.setText(distinto.elementAt(0));
            jTextFieldNulos.setText(Integer.toString(porcentajeNulosPorAtributo) + "%");
            //jTextField1.setText(Integer.toString(porcentajeNulosPorRegistro) + "%");
            int cantidadNulos = objConsultaNulos.contarValoresNulosPorAtributo(nombreVista, nombreAtributo);
            int cantidadRegistros = objConsultaNulos.totalRegistros(nombreVista);
            //*********************  mostrar grafico de barras
            StackedBarChart grafico = new StackedBarChart();
            imagenDelGrafico = grafico.createStackedBarChart(cantidadNulos, cantidadRegistros);
            labelGrafico.setIcon(new ImageIcon(imagenDelGrafico));
            //**********************************************************************
            if (tipoAtributo.elementAt(0).equals("bigint") || tipoAtributo.elementAt(0).equals("int") || tipoAtributo.elementAt(0).equals("float"))
            {
                jTextFieldTipo.setText("Numerico");
                //**********  llenar tabla de estadísitcas para atributos numéricos
                inicializarNombreColumnasTablaEstaditicasDatoNumerico();
                vectorEstadidticas.clear();
                llenarTablaEstadisticasDatoNumerico(nombreAtributo, nombreTabla);
                //llenarTablaEstadisticasDatoNumerico(nombreAtributo, nombreVista);
                actualizarTablaEstadisticas();
                //***************************  mostrar Grafico de Dispersion
                GraficoDispersion objGraficoDispersion = new GraficoDispersion();
                vectorDatosNumericos = objControladora.consultaGraficoDispersion(nombreAtributo, nombreVista);
                imagenDelGraficoDispersion = objGraficoDispersion.crearGraficodispersion(vectorDatosNumericos, nombreAtributo);
                jLabelGraficoDispersion.setIcon(new ImageIcon(imagenDelGraficoDispersion));

            } else {
                if (tipoAtributo.elementAt(0).equals("varchar") || tipoAtributo.elementAt(0).equals("datetime") || tipoAtributo.elementAt(0).equals("date")) {
                    jTextFieldTipo.setText("Nominal");
                    //***********  llenar tabla de estadísitcas para atributos nominales
                    inicializarNombreColumnasTablaEstaditicasDatoNominal();
                    vectorEstadidticas.clear();
                    vectorEstadidticas = objControladora.consultaTablaEstadisticasAtributoNominal(nombreAtributo, nombreVista);
                    actualizarTablaEstadisticas();

                } else {
                    jTextFieldTipo.setText(tipoAtributo.elementAt(0));
                }
            }

        }

    }//GEN-LAST:event_jTableAtributosMouseClicked

    private void jComboBoxNombreTablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNombreTablasActionPerformed
        // TODO add your handling code here:
        nombreTabla = jComboBoxNombreTablas.getSelectedItem().toString();
        String nombreVista="";
        if(nombreTabla.substring(0,7).equals("llamada") || nombreTabla.substring(0,7).equals("recarga"))
        {
            nombreVista=nombreTabla;
        }
        else
        {
            nombreVista = "vista_" + nombreTabla;
        }
        vectorNombreAtributos.clear();
        llenarTablaAtributos(nombreTabla);
        actualizarTabla();
        ConsultaNulos objConsultaNulos = new ConsultaNulos();
        int porcentajeNulosTabla = 0;
        porcentajeNulosTabla = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreVista);
        jTextFieldNulosTabla.setText(Integer.toString(porcentajeNulosTabla) + "%");

    }//GEN-LAST:event_jComboBoxNombreTablasActionPerformed

    private void jTextFieldDistintoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDistintoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDistintoActionPerformed

    private void botonLimpiarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarAreaActionPerformed
        areaMostrarResultados.setText("");
    }//GEN-LAST:event_botonLimpiarAreaActionPerformed

    private void botonEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarActionPerformed
        areaMostrarResultados.append(aplicarClustering.aplicarClustering(comboAlgortimo.getSelectedIndex(), Integer.parseInt(seleccionNumeroClusters.getValue().toString()), Integer.parseInt(porcentajeDatos.getValue().toString()), instanciaGeneral));
    }//GEN-LAST:event_botonEjecutarActionPerformed

    private void jButtonNingunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNingunoActionPerformed
        // TODO add your handling code here:
        modeloTablaAtributos.DeseleccionarTodosLosAtributos();
    }//GEN-LAST:event_jButtonNingunoActionPerformed

    private void jButtonEliminarAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarAtributosActionPerformed
        // TODO add your handling code here:
        String nombreVista = "";
        if(nombreTabla.substring(0,7).equals("llamada") || nombreTabla.substring(0,7).equals("recarga"))
        {
            nombreVista=nombreTabla;
        }
        else
        {
            nombreVista = "vista_" + nombreTabla;
        }
        boolean estaSeleccionado = false;
        String atributoSeleccionado;
        String atributoNoSeleccionado;
        Vector<String> vectorAtributosSeleccionados = new Vector<String>();
        Vector<String> vectorAtributosNoSeleccionados = new Vector<String>();
        int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar los Atributos Seleccionados?", "Eliiminar Atributos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == 0) {
            for (int i = 0; i < vectorNombreAtributos.size(); i++) {
                estaSeleccionado = Boolean.parseBoolean(jTableAtributos.getValueAt(i, 1).toString());
                if (estaSeleccionado == true) {
                    atributoSeleccionado = jTableAtributos.getValueAt(i, 2).toString();
                    vectorAtributosSeleccionados.addElement(atributoSeleccionado);
                } else {
                    //atributoNoSeleccionado = atributoSeleccionado = jTableAtributos.getValueAt(i, 2).toString();
                    atributoNoSeleccionado = jTableAtributos.getValueAt(i, 2).toString();
                    vectorAtributosNoSeleccionados.addElement(atributoNoSeleccionado);
                }
            }
            Controladora objControladora = new Controladora();
            objControladora.eliminarAtributosSeleccionados(vectorAtributosNoSeleccionados, nombreVista);
            //************ actualizar JTable con la vista
            vectorNombreAtributos.clear();
            llenarTablaAtributos(nombreTabla);
            actualizarTabla();
            //*************** actualizar porcentaje de la vista
            ConsultaNulos objConsultaNulos = new ConsultaNulos();
            int porcentajeNulosTabla = 0;
            porcentajeNulosTabla = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreVista);
            jTextFieldNulosTabla.setText(Integer.toString(porcentajeNulosTabla) + "%");
        }

    }//GEN-LAST:event_jButtonEliminarAtributosActionPerformed

   public Vector<String> retornarAtributosNoSeleccionados()
   {
       String atributoNoSeleccionado;
       boolean estaSeleccionado=false;
       Vector<String> vectorAtributosNoseleccionados= new Vector<String>();
       for(int i=0; i< vectorNombreAtributos.size(); i++)
       {
           estaSeleccionado = Boolean.parseBoolean(jTableAtributos.getValueAt(i, 1).toString());
           if(estaSeleccionado==false)
           {
                atributoNoSeleccionado = jTableAtributos.getValueAt(i, 2).toString();
                vectorAtributosNoseleccionados.addElement(atributoNoSeleccionado);
           }
       }

       return vectorAtributosNoseleccionados;
   }


   public void retornarAtributosSeleccionados()
   {
       String atributoSeleccionado;
       boolean estaSeleccionado=false;
       vectorAtributosSeleccionados= new Vector<String>();
       for(int i=0; i< vectorNombreAtributos.size(); i++)
       {
           estaSeleccionado = Boolean.parseBoolean(jTableAtributos.getValueAt(i, 1).toString());
           //System.out.println("esta seleccionado: "+estaSeleccionado);
           if(estaSeleccionado==true)
           {
                atributoSeleccionado = jTableAtributos.getValueAt(i, 2).toString();
                vectorAtributosSeleccionados.addElement(atributoSeleccionado);
           }
       }
//       for(int i=0; i< vectorAtributosSeleccionados.size(); i++)
//       {
//           System.out.println("atibuto seleccionado: "+vectorAtributosSeleccionados.elementAt(i));
//       }
   }

    private void jButtonDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeshacerActionPerformed
        // TODO add your handling code here:
        String nombreVista="";
        if(nombreTabla.substring(0,7).equals("llamada") || nombreTabla.substring(0,7).equals("recarga"))
        {
            nombreVista=nombreTabla;
        }
        else
        {
            nombreVista = "vista_" + nombreTabla;
        }
        ConsultasVistas objConsultasVistas2= new ConsultasVistas();
        objConsultasVistas2.borrarVistas();
        objConsultasVistas2.borrarVistaCliente();
        objConsultasVistas2.crearVistas();
        objConsultasVistas2.crearVistaCliente();
        vectorNombreAtributos.clear();
        llenarTablaAtributos(nombreTabla);
        actualizarTabla();
        //*************** actualizar porcentaje de la vista
        ConsultaNulos objConsultaNulos = new ConsultaNulos();
        int porcentajeNulosTabla = 0;
        porcentajeNulosTabla = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreVista);
        jTextFieldNulosTabla.setText(Integer.toString(porcentajeNulosTabla) + "%");
    }//GEN-LAST:event_jButtonDeshacerActionPerformed

    private void jButtonEjecutarAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEjecutarAsocActionPerformed
        // TODO add your handling code here:
        aplicarAsociacion = new AplicarAsociacion();
        System.out.println("Algoritmo seleccionado "+jComboBoxAlgoritmoAsoc.getSelectedIndex());
        
        jTextAreaAsoc.append(aplicarAsociacion.aplicarAsociacionWeka(instanciaGeneral, jComboBoxAlgoritmoAsoc.getSelectedIndex(),Integer.parseInt(jSpinnerPorcentajeAsoc.getValue().toString()),Double.parseDouble(confianzaMinima.getValue().toString())));
        
    }//GEN-LAST:event_jButtonEjecutarAsocActionPerformed

    private void jButtonLimpiarAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarAsocActionPerformed
        // TODO add your handling code here:
        jTextAreaAsoc.setText("");
    }//GEN-LAST:event_jButtonLimpiarAsocActionPerformed

    private void botonEjecutarClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarClasificacionActionPerformed
        // TODO add your handling code here:
        indice = comboAlgortimoClasificacion.getSelectedIndex();
        areaMostrarResultadosAsociacion.setText(arbolJ48ConInterfaz.construirArbolJ48(Integer.parseInt(porcentajeDatosClasificacion.getValue().toString()), 4, instanciaGeneral));
    }//GEN-LAST:event_botonEjecutarClasificacionActionPerformed

    private void botonLimpiarAreaClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarAreaClasificacionActionPerformed
        // TODO add your handling code here:
        areaMostrarResultadosAsociacion.setText("");
    }//GEN-LAST:event_botonLimpiarAreaClasificacionActionPerformed

    private void jButtonConsultaSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaSQLActionPerformed
        // TODO add your handling code here:
        aplicarAsociacion.realizarConsultaABaseDeDatosTipoWekaInstances(jTextFieldConsultaSQLPreproc.getText());
        //Instancia genera para todos los algortimos de clustering, asociacion y clasificacion
        //----->instanciaGeneral = new Instances(salida);
    }//GEN-LAST:event_jButtonConsultaSQLActionPerformed

    private void jButtonCargarPerfilPreprocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargarPerfilPreprocActionPerformed
        // TODO add your handling code here:
        valorIntervaloDiscretizacion=objDiscretizarGUI.getValorIntervalo();
        System.out.println("valor Disc: "+valorIntervaloDiscretizacion);
        String consulta = "";
        DiscretizeCardel objDiscretizeCardel = new DiscretizeCardel();
        ConsultasPredefinidas consultasPredefinidas = new ConsultasPredefinidas();
        System.out.println("indice"+jComboBoxPerfilPreproc.getSelectedIndex());
        //consulta = consultasPredefinidas.retornarConsulta(jComboBoxPerfilAsoc.getSelectedIndex(), jSpinnerMesAsoc.getValue().toString());
        consulta = consultasPredefinidas.retornarConsulta(jComboBoxPerfilPreproc.getSelectedIndex(), jSpinnerMesPreproc.getValue().toString());
        FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
        try {
            Instances instancia = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
            instanciaSinDiscretizar= new Instances(instancia);// instancia que utiliza heberth
            //Instances salida = objDiscretizeCardel.discretizar(instancia, 10);
            Instances salida = objDiscretizeCardel.discretizar(instancia, valorIntervaloDiscretizacion);
            //Instancia genera para todos los algortimos de clustering, asociacion y clasificacion
            instanciaGeneral = new Instances(salida);
          
        } catch (Exception ex) {
            Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Se cargaron los datos correctamente", "Carga de datos", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonCargarPerfilPreprocActionPerformed

    private void jButtonDiscretizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDiscretizarActionPerformed
        // TODO add your handling code here:
        jButtonLimpiarNulos.setEnabled(true);
         int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Discretizar los Atributos Numéricos?", "Discretizar Atributos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
         if(opcion==0)
         {
             try {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    objDiscretizarGUI= new DiscretizarGUI();
                    objDiscretizarGUI.setVisible(true);

            } catch (Exception ex) {
                Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }//GEN-LAST:event_jButtonDiscretizarActionPerformed

    private void jButtonLimpiarNulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarNulosActionPerformed
        // TODO add your handling code here:
        jButtonCargarPerfilPreproc.setEnabled(true);
        
    }//GEN-LAST:event_jButtonLimpiarNulosActionPerformed

    private void jButtonEliminarOutliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarOutliersActionPerformed
        // TODO add your handling code here:
        //String nombreVista="vista_"+nombreTabla;
        String nombreVista="";
        String tipoAtributo="";
        Controladora objControladora= new Controladora();
        if(nombreTabla.substring(0,7).equals("llamada") || nombreTabla.substring(0,7).equals("recarga"))
        {
            nombreVista=nombreTabla;
        }
        else
        {
            nombreVista = "vista_" + nombreTabla;
        }
        retornarAtributosSeleccionados();
        if(vectorAtributosSeleccionados.size()>1 || vectorAtributosSeleccionados.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Seleccione un solo atributo", "Un atributo", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
             tipoAtributo = objControladora.consultaTipoAtributo(vectorAtributosSeleccionados.firstElement(), nombreVista).firstElement();
             if(tipoAtributo.equals("varchar") || tipoAtributo.equals("datetime") || tipoAtributo.equals("date"))
             {
                JOptionPane.showMessageDialog(null, "Solo para atributos numericos", "Error de Seleccion", JOptionPane.ERROR_MESSAGE);
             }
             else
             {
                 if(vectorAtributosSeleccionados.firstElement().substring(0, 3).equals("id_"))
                 {
                    JOptionPane.showMessageDialog(null, "No se pueden eliminar identificadores", "Error de Seleccion", JOptionPane.ERROR_MESSAGE);
                 }
                 else
                 {
                     int opcion = JOptionPane.showConfirmDialog(null, "Los registros cuyo atributo seleccionado se encuentren fuera del rango serán eliminados."+"\n" +"¿Esta seguro de eliminar?", "Eliminar Registros con Outliers", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                     if(opcion==0)
                     {
                         try {
                                try {
                                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (UnsupportedLookAndFeelException ex) {
                                    Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                System.out.println("nombre Tabla :"+ nombreTabla);
                                objLimpiarOutliersGUI= new LimpiarOutliersGUI(nombreVista, vectorAtributo, vectorAtributosSeleccionados);
                                objLimpiarOutliersGUI.setVisible(true);



                        } catch (Exception ex) {
                            Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     }
                 }
             }
             
        }
         
    }//GEN-LAST:event_jButtonEliminarOutliersActionPerformed

    private void jComboBoxPerfilPreprocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPerfilPreprocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxPerfilPreprocActionPerformed

    private void jComboBoxAlgoritmoAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAlgoritmoAsocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAlgoritmoAsocActionPerformed

    private void comboAlgortimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAlgortimoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboAlgortimoActionPerformed
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
    private javax.swing.JTextArea areaMostrarResultados;
    private javax.swing.JTextArea areaMostrarResultadosAsociacion;
    private javax.swing.JButton botonEjecutar;
    private javax.swing.JButton botonEjecutarClasificacion;
    private javax.swing.JButton botonLimpiarArea;
    private javax.swing.JButton botonLimpiarAreaClasificacion;
    private javax.swing.JComboBox comboAlgortimo;
    private javax.swing.JComboBox comboAlgortimoClasificacion;
    private javax.swing.JSpinner confianzaMinima;
    private javax.swing.JButton jButtonCargarPerfilPreproc;
    private javax.swing.JButton jButtonConexionBD;
    private javax.swing.JButton jButtonConsultaSQL;
    private javax.swing.JButton jButtonDeshacer;
    private javax.swing.JButton jButtonDiscretizar;
    private javax.swing.JButton jButtonEjecutarAsoc;
    private javax.swing.JButton jButtonEliminarAtributos;
    private javax.swing.JButton jButtonEliminarOutliers;
    private javax.swing.JButton jButtonLimpiarAsoc;
    private javax.swing.JButton jButtonLimpiarNulos;
    private javax.swing.JButton jButtonNinguno;
    private javax.swing.JButton jButtonSeleccionarTodo;
    private javax.swing.JComboBox jComboBoxAlgoritmoAsoc;
    private javax.swing.JComboBox jComboBoxNombreTablas;
    private javax.swing.JComboBox jComboBoxPerfilPreproc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAlgoritmoAsoc;
    private javax.swing.JLabel jLabelConsPersonPreproc;
    private javax.swing.JLabel jLabelDistinto;
    private javax.swing.JLabel jLabelGraficoDispersion;
    private javax.swing.JLabel jLabelMesPreproc;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNulos;
    private javax.swing.JLabel jLabelPerfilPreproc;
    private javax.swing.JLabel jLabelPorcentajeAsoc;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JLabel jLabelTituloAsoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelGraficoBarras;
    private javax.swing.JPanel jPanelGraficoDispersion;
    private javax.swing.JPanel jPanelParametrosAsoc;
    private javax.swing.JPanel jPanelSalidaAsoc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPanePrincipal;
    private javax.swing.JSpinner jSpinnerMesPreproc;
    private javax.swing.JSpinner jSpinnerPorcentajeAsoc;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableAtributos;
    private javax.swing.JTable jTableEstadistica;
    private javax.swing.JTextArea jTextAreaAsoc;
    private javax.swing.JTextField jTextFieldConsultaSQLPreproc;
    private javax.swing.JTextField jTextFieldDistinto;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldNulos;
    private javax.swing.JTextField jTextFieldNulosTabla;
    private javax.swing.JTextField jTextFieldTipo;
    private javax.swing.JLabel labelGrafico;
    private javax.swing.JSpinner porcentajeDatos;
    private javax.swing.JSpinner porcentajeDatosClasificacion;
    private javax.swing.JSpinner seleccionNumeroClusters;
    // End of variables declaration//GEN-END:variables
}
