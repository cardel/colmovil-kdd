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
    String nombreTabla;
    String consultaAsociacion;
    ModeloTablaAtributos modeloTablaAtributos;
    ConsultasVistas objConsultasVistas;
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
    AplicarAsociacion aplicarAsociacion= new AplicarAsociacion();
    /*
     * FINAL ASOCIACION
     */

    /** Creates new form ColmovilGUI */
    public ColmovilGUI() 
    {
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
        jComboBoxNombreTablas.setEnabled(false);
        setLocationRelativeTo(null);
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
        Vector<String> vectorAtributo = new Vector<String>();
        Controladora objControladora = new Controladora();
        //System.out.println(objControladora.consultaNombreAtributos());
        vectorAtributo = objControladora.consultaNombreAtributos(nombreTabla);

        for (int i = 0; i < vectorAtributo.size(); i++) {
            Vector<Object> nuevoVector = new Vector<Object>();
            nuevoVector.addElement(Integer.toString(i + 1));
            nuevoVector.addElement(new Boolean(false));
            nuevoVector.addElement(vectorAtributo.elementAt(i));
            //nuevoVector.add("hola");


            //System.out.println("nuevo vector: "+ nuevoVector);
            vectorNombreAtributos.addElement(nuevoVector);
            //nuevoVector.clear();
            //System.out.println("vector tabla: "+ vectorNombreAtributos);
        }
        //System.out.println("vector tabla: "+ vectorNombreAtributos);

    }

    public void llenarComboBoxNombreTablas() {
        Controladora objControladora = new Controladora();
        vectorNombreTablas = objControladora.consultaNombreTablas();
//        for(int i=0; i<vector.size(); i++)
//        {
//            jComboBoxNombreTablas.addItem(vector.elementAt(i));
//        }
        
    }

    public void llenarTablaEstadisticasDatoNumerico(String nombreAtributo, String nombreTabla) {
        Vector<String> vectorNombreEstadisticas = new Vector<String>();
        //se crea el vector de la columna de nombres de la tabla estadisticas
        vectorNombreEstadisticas.add("Maximo");
        vectorNombreEstadisticas.add("Minimo");
        vectorNombreEstadisticas.add("Promedio");
        vectorNombreEstadisticas.add("Desv Est");

        Vector<String> valoresEstadisticas = new Vector<String>();
        Vector<String> vectorTotalEstadisticas = new Vector<String>();
        Controladora objControladora = new Controladora();
        vectorTotalEstadisticas = objControladora.unirConsultaEstadisticas(nombreAtributo, nombreTabla);// se unió todas las consultas en una sola
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButtonSeleccionarTodo = new javax.swing.JButton();
        jButtonNinguno = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAtributos = new javax.swing.JTable();
        jButtonLimpiar = new javax.swing.JButton();
        jButtonEliminarAtributos = new javax.swing.JButton();
        jButtonDiscretizar = new javax.swing.JButton();
        jButtonDeshacer = new javax.swing.JButton();
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
        jTextFieldNulosTabla = new javax.swing.JTextField();
        jPanelGraficoBarras = new javax.swing.JPanel();
        labelGrafico = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jComboBoxNombreTablas = new javax.swing.JComboBox();
        jPanelGraficoDispersion = new javax.swing.JPanel();
        jLabelGraficoDispersion = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanelSalidaAsoc = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaAsoc = new javax.swing.JTextArea();
        jPanelCargaDatosAsoc = new javax.swing.JPanel();
        jLabelPerfilAsoc = new javax.swing.JLabel();
        jComboBoxPerfilAsoc = new javax.swing.JComboBox();
        jLabelMesAsoc = new javax.swing.JLabel();
        jSpinnerMesAsoc = new javax.swing.JSpinner();
        jButtonCargarPerfilAsoc = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldConsultaSQLAsoc = new javax.swing.JTextField();
        jButtonConsultaSqlAsoc = new javax.swing.JButton();
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
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textoConsultaSQL = new javax.swing.JTextField();
        botonConsultaSQL = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaMostrarResultados = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboAlgortimo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        porcentajeDatos = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        botonEjecutar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        botonLimpiarArea = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        seleccionPerfil = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        seleccionMes = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        seleccionNumeroClusters = new javax.swing.JSpinner();

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

        jButtonLimpiar.setText("Limpiar");
        jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarActionPerformed(evt);
            }
        });

        jButtonEliminarAtributos.setText("Eliminar Atributos");
        jButtonEliminarAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarAtributosActionPerformed(evt);
            }
        });

        jButtonDiscretizar.setText("Discretizar");

        jButtonDeshacer.setText("Deshacer");
        jButtonDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeshacerActionPerformed(evt);
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonSeleccionarTodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonEliminarAtributos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonNinguno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonDeshacer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonDiscretizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(jButtonLimpiar)
                    .addComponent(jButtonEliminarAtributos)
                    .addComponent(jButtonDiscretizar))
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

        jTextFieldNulosTabla.setEditable(false);
        jTextFieldNulosTabla.setText("%");
        jTextFieldNulosTabla.setBorder(null);

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
                            .addComponent(jTextFieldTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextFieldNulosTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTextFieldNulosTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jLabelGraficoDispersion, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanelSalidaAsoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTextAreaAsoc.setColumns(20);
        jTextAreaAsoc.setRows(5);
        jScrollPane3.setViewportView(jTextAreaAsoc);

        javax.swing.GroupLayout jPanelSalidaAsocLayout = new javax.swing.GroupLayout(jPanelSalidaAsoc);
        jPanelSalidaAsoc.setLayout(jPanelSalidaAsocLayout);
        jPanelSalidaAsocLayout.setHorizontalGroup(
            jPanelSalidaAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );
        jPanelSalidaAsocLayout.setVerticalGroup(
            jPanelSalidaAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
        );

        jPanelCargaDatosAsoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Carga de Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabelPerfilAsoc.setText("Perfil");

        jComboBoxPerfilAsoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nùmero llamadas por hora", "Promedio duracion llamadas por hora", "Numero llamadas por dia", "Uso de la red por dia", "Nùmero llamadas por hora por sexo, edad y estado civil", "Promedio duracion llamadas por hora por sexo, edad y estado civil", "Numero llamadas por dia por sexo, edad y estado civil", "Uso de la red por dia por sexo, edad y estado civil", "Número llamadas por sexo, edad y estado civil", "Promedio duración llamadas por sexo, edad y estado civil", "Número llamadas por sexo, edad y estado civil a destino", "Promedio duración llamadas por sexo, edad y estado civil a destino", "Planes de voz por sexo, edad y estado civil", "Planes de datos por sexo, edad y estado civil ", "Modalidad servicio por sexo, estrato y edad", "Modalidad servicio por sexo, edad, estrato y estado civil", "Numero de recargas por medio por día", "Valor promedio por recargas por medio por día", "Causa retiro por sexo, edad y estado civil", "Causa retiro por sexo, edad y estado civil por mes en todos los años", "Causa retiro por sexo, edad y estado civil por mes en cada año" }));

        jLabelMesAsoc.setText("Mes");

        jSpinnerMesAsoc.setModel(new javax.swing.SpinnerListModel(new String[] {"012008", "022008", "032008", "042008", "052008", "062008", "072008", "082008", "092008", "102008", "112008", "122008", "012009", "022009", "032009", "042009", "052009", "062009", "072009", "082009", "092009", "102009", "112009", "122009"}));

        jButtonCargarPerfilAsoc.setText("Cargar Perfil");
        jButtonCargarPerfilAsoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargarPerfilAsocActionPerformed(evt);
            }
        });

        jLabel13.setText("Consulta Personalizada");

        jTextFieldConsultaSQLAsoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldConsultaSQLAsocActionPerformed(evt);
            }
        });

        jButtonConsultaSqlAsoc.setText("Consulta SQL");
        jButtonConsultaSqlAsoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultaSqlAsocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCargaDatosAsocLayout = new javax.swing.GroupLayout(jPanelCargaDatosAsoc);
        jPanelCargaDatosAsoc.setLayout(jPanelCargaDatosAsocLayout);
        jPanelCargaDatosAsocLayout.setHorizontalGroup(
            jPanelCargaDatosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCargaDatosAsocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCargaDatosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCargaDatosAsocLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldConsultaSQLAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonConsultaSqlAsoc))
                    .addGroup(jPanelCargaDatosAsocLayout.createSequentialGroup()
                        .addComponent(jLabelPerfilAsoc)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxPerfilAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jButtonCargarPerfilAsoc))
                    .addGroup(jPanelCargaDatosAsocLayout.createSequentialGroup()
                        .addComponent(jLabelMesAsoc)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinnerMesAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanelCargaDatosAsocLayout.setVerticalGroup(
            jPanelCargaDatosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCargaDatosAsocLayout.createSequentialGroup()
                .addGroup(jPanelCargaDatosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxPerfilAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPerfilAsoc)
                    .addComponent(jButtonCargarPerfilAsoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCargaDatosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMesAsoc)
                    .addComponent(jSpinnerMesAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCargaDatosAsocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldConsultaSQLAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConsultaSqlAsoc))
                .addContainerGap())
        );

        jLabelTituloAsoc.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelTituloAsoc.setText("Procesamiento de datos utilizando Asociación");

        jPanelParametrosAsoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parámetros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabelAlgoritmoAsoc.setText("Seleccione Algoritmo");

        jComboBoxAlgoritmoAsoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "APriori", "FPGrowth" }));

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
                            .addComponent(jButtonLimpiarAsoc, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(jPanelParametrosAsocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(confianzaMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
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
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCargaDatosAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanelParametrosAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanelSalidaAsoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(191, Short.MAX_VALUE)
                .addComponent(jLabelTituloAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTituloAsoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelCargaDatosAsoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSalidaAsoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelParametrosAsoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Asociación", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 773, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Clasificación", jPanel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setText("Procesamiento de datos utilizando clustering");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel4.setText("Carga de datos");

        botonConsultaSQL.setText("Consulta SQL");
        botonConsultaSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultaSQLActionPerformed(evt);
            }
        });

        areaMostrarResultados.setColumns(20);
        areaMostrarResultados.setRows(5);
        jScrollPane4.setViewportView(areaMostrarResultados);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel5.setText("Parámetros");

        jLabel6.setText("Seleccione algortimo clustering");

        comboAlgortimo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "K-Means", "DBScan" }));

        jLabel7.setText("Porcentaje datos");

        porcentajeDatos.setModel(new javax.swing.SpinnerNumberModel(100, 1, 100, 1));

        jLabel8.setText("Ejecutar algortimo");

        botonEjecutar.setText("Ejecutar");
        botonEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarActionPerformed(evt);
            }
        });

        jLabel9.setText("Limpiar area de texto");

        botonLimpiarArea.setText("Limpiar");
        botonLimpiarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarAreaActionPerformed(evt);
            }
        });

        jLabel2.setText("Consulta personalizada");

        jLabel10.setText("Perfil");

        seleccionPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nùmero llamadas por hora", "Promedio duracion llamadas por hora", "Numero llamadas por dia", "Uso de la red por dia", "Nùmero llamadas por hora por sexo, edad y estado civil", "Promedio duracion llamadas por hora por sexo, edad y estado civil", "Numero llamadas por dia por sexo, edad y estado civil", "Uso de la red por dia por sexo, edad y estado civil", "Número llamadas por sexo, edad y estado civil", "Promedio duración llamadas por sexo, edad y estado civil", "Número llamadas por sexo, edad y estado civil a destino", "Promedio duración llamadas por sexo, edad y estado civil a destino", "Planes de voz por sexo, edad y estado civil", "Planes de datos por sexo, edad y estado civil ", "Modalidad servicio por sexo, estrato y edad", "Modalidad servicio por sexo, edad, estrato y estado civil", "Numero de recargas por medio por día", "Valor promedio por recargas por medio por día", "Causa retiro por sexo, edad y estado civil", "Causa retiro por sexo, edad y estado civil por mes en todos los años", "Causa retiro por sexo, edad y estado civil por mes en cada año" }));
        seleccionPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionPerfilActionPerformed(evt);
            }
        });

        jLabel11.setText("Mes: Aplica sólo para recargas y llamadas");

        seleccionMes.setModel(new javax.swing.SpinnerListModel(new String[] {"012008", "022008", "032008", "042008", "052008", "062008", "072008", "082008", "092008", "102008", "112008", "122008", "012009", "022009", "032009", "042009", "052009", "062009", "072009", "082009", "092009", "102009", "112009", "122009"}));

        jButton1.setText("Cargar perfil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setText("Número de clusters (Sólo K-Means)");

        seleccionNumeroClusters.setModel(new javax.swing.SpinnerNumberModel(4, 2, 50, 1));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1))
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(textoConsultaSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(52, 52, 52)
                                    .addComponent(botonConsultaSQL))
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                            .addGap(85, 85, 85)
                                            .addComponent(jLabel5))
                                        .addComponent(comboAlgortimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6)
                                            .addComponent(botonLimpiarArea)
                                            .addComponent(botonEjecutar))
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel8)
                                        .addComponent(porcentajeDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel12)
                                        .addComponent(seleccionNumeroClusters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                    .addComponent(seleccionPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(seleccionMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addGap(253, 253, 253)
                                    .addComponent(jLabel11)))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel3)))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(seleccionPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(seleccionMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)
                        .addComponent(textoConsultaSQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(botonConsultaSQL)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel5)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboAlgortimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(seleccionNumeroClusters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(porcentajeDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(botonEjecutar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(botonLimpiarArea))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clustering", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbrirActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jButtonAbrirActionPerformed

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
        int fila = jTableAtributos.getSelectedRow();
        int porcentajeNulosPorAtributo = 0;
        //int porcentajeNulosPorRegistro = 0;
        Controladora objControladora = new Controladora();
        ConsultaNulos objConsultaNulos = new ConsultaNulos();
        String nombreAtributo = jTableAtributos.getValueAt(fila, 2).toString();
        Vector<String> tipoAtributo = new Vector<String>();
        Vector<String> distinto = new Vector<String>();
        Vector<Integer> vectorDatosNumericos = new Vector<Integer>();
        tipoAtributo = objControladora.consultaTipoAtributo(nombreAtributo, nombreTabla);
        distinto = objControladora.consultaDistintos(nombreAtributo, nombreTabla);
        porcentajeNulosPorAtributo = objConsultaNulos.porcentajeValoresNulosPorAtributo(nombreTabla, nombreAtributo);
        //porcentajeNulosPorRegistro = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreTabla);
        //JOptionPane.showMessageDialog(null, "fila No.: "+fila);
        jTextFieldNombre.setText(nombreAtributo);
        jTextFieldDistinto.setText(distinto.elementAt(0));
        jTextFieldNulos.setText(Integer.toString(porcentajeNulosPorAtributo) + "%");
        //jTextField1.setText(Integer.toString(porcentajeNulosPorRegistro) + "%");
        int cantidadNulos = objConsultaNulos.contarValoresNulosPorAtributo(nombreTabla, nombreAtributo);
        int cantidadRegistros = objConsultaNulos.totalRegistros(nombreTabla);
        //*********************  mostrar grafico de barras
        StackedBarChart grafico = new StackedBarChart();
        imagenDelGrafico = grafico.createStackedBarChart(cantidadNulos, cantidadRegistros);
        labelGrafico.setIcon(new ImageIcon(imagenDelGrafico));
        //**********************************************************************
        if (tipoAtributo.elementAt(0).equals("bigint") || tipoAtributo.elementAt(0).equals("int") || tipoAtributo.elementAt(0).equals("float")) {
            jTextFieldTipo.setText("Numerico");
            //**********  llenar tabla de estadísitcas para atributos numéricos
            inicializarNombreColumnasTablaEstaditicasDatoNumerico();
            vectorEstadidticas.clear();
            llenarTablaEstadisticasDatoNumerico(nombreAtributo, nombreTabla);
            actualizarTablaEstadisticas();
            //***************************  mostrar Grafico de Dispersion
            GraficoDispersion objGraficoDispersion = new GraficoDispersion();
            vectorDatosNumericos = objControladora.consultaGraficoDispersion(nombreAtributo, nombreTabla);
            imagenDelGraficoDispersion = objGraficoDispersion.crearGraficodispersion(vectorDatosNumericos, nombreAtributo);
            jLabelGraficoDispersion.setIcon(new ImageIcon(imagenDelGraficoDispersion));

        } else {
            if (tipoAtributo.elementAt(0).equals("varchar") || tipoAtributo.elementAt(0).equals("date")) {
                jTextFieldTipo.setText("Nominal");
                //***********  llenar tabla de estadísitcas para atributos nominales
                inicializarNombreColumnasTablaEstaditicasDatoNominal();
                vectorEstadidticas.clear();
                vectorEstadidticas = objControladora.consultaTablaEstadisticasAtributoNominal(nombreAtributo, nombreTabla);
                actualizarTablaEstadisticas();

            } else {
                jTextFieldTipo.setText(tipoAtributo.elementAt(0));
            }
        }

    }//GEN-LAST:event_jTableAtributosMouseClicked

    private void jComboBoxNombreTablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNombreTablasActionPerformed
        // TODO add your handling code here:
        nombreTabla = jComboBoxNombreTablas.getSelectedItem().toString();
        vectorNombreAtributos.clear();
        llenarTablaAtributos(nombreTabla);
        actualizarTabla();
        ConsultaNulos objConsultaNulos = new ConsultaNulos();
        int porcentajeNulosTabla = 0;
        porcentajeNulosTabla = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreTabla);
        jTextFieldNulosTabla.setText(Integer.toString(porcentajeNulosTabla) + "%");

    }//GEN-LAST:event_jComboBoxNombreTablasActionPerformed

    private void jTextFieldDistintoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDistintoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDistintoActionPerformed

    private void botonConsultaSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultaSQLActionPerformed
        // TODO add your handling code here:
        aplicarClustering.realizarConsultaABaseDeDatosTipoWekaInstances(textoConsultaSQL.getText());
    }//GEN-LAST:event_botonConsultaSQLActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String consulta = "";
        ConsultasPredefinidas consultasPredefinidas = new ConsultasPredefinidas();
        consulta = consultasPredefinidas.retornarConsulta(seleccionPerfil.getSelectedIndex(), seleccionMes.getValue().toString());
        aplicarClustering.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonLimpiarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarAreaActionPerformed
        areaMostrarResultados.setText("");
    }//GEN-LAST:event_botonLimpiarAreaActionPerformed

    private void botonEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarActionPerformed
        areaMostrarResultados.append(aplicarClustering.aplicarClustering(comboAlgortimo.getSelectedIndex(), Integer.parseInt(seleccionNumeroClusters.getValue().toString()), Integer.parseInt(porcentajeDatos.getValue().toString())));
    }//GEN-LAST:event_botonEjecutarActionPerformed

    private void jButtonNingunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNingunoActionPerformed
        // TODO add your handling code here:
        modeloTablaAtributos.DeseleccionarTodosLosAtributos();
    }//GEN-LAST:event_jButtonNingunoActionPerformed

    private void seleccionPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seleccionPerfilActionPerformed

    private void jButtonEliminarAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarAtributosActionPerformed
        // TODO add your handling code here:
        String nombreVista="vista_"+nombreTabla;
        boolean estaSeleccionado=false;
        String atributoSeleccionado;
        String atributoNoSeleccionado;
        Vector<String> vectorAtributosSeleccionados=new Vector<String>();
        Vector<String> vectorAtributosNoSeleccionados= new Vector<String>();
        int opcion=JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar los Atributos Seleccionados?", "Eliiminar Atributos" , JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(opcion==0)
        {
            for(int i=0; i<vectorNombreAtributos.size();i++)
            {
                estaSeleccionado=Boolean.parseBoolean(jTableAtributos.getValueAt(i, 1).toString());
                if(estaSeleccionado==true)
                {
                    atributoSeleccionado=jTableAtributos.getValueAt(i, 2).toString();
                    vectorAtributosSeleccionados.addElement(atributoSeleccionado);
                }
                else
                {
                    atributoNoSeleccionado=atributoSeleccionado=jTableAtributos.getValueAt(i, 2).toString();
                    vectorAtributosNoSeleccionados.addElement(atributoNoSeleccionado);
                }
            }
            Controladora objControladora= new Controladora();
            objControladora.eliminarAtributosSeleccionados(vectorAtributosNoSeleccionados, nombreVista);
            //************ actualizar JTable con la vista
            vectorNombreAtributos.clear();
            llenarTablaAtributos(nombreVista);
            actualizarTabla();
            //*************** actualizar porcentaje de la vista
            ConsultaNulos objConsultaNulos = new ConsultaNulos();
            int porcentajeNulosTabla = 0;
            porcentajeNulosTabla = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreVista);
            jTextFieldNulosTabla.setText(Integer.toString(porcentajeNulosTabla) + "%");
        }
        
    }//GEN-LAST:event_jButtonEliminarAtributosActionPerformed

    private void jButtonDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeshacerActionPerformed
        // TODO add your handling code here:
        vectorNombreAtributos.clear();
        llenarTablaAtributos(nombreTabla);
        actualizarTabla();
        //*************** actualizar porcentaje de la vista
        ConsultaNulos objConsultaNulos = new ConsultaNulos();
        int porcentajeNulosTabla = 0;
        porcentajeNulosTabla = objConsultaNulos.porcentajeValoresNulosPorRegistro(nombreTabla);
        jTextFieldNulosTabla.setText(Integer.toString(porcentajeNulosTabla) + "%");
    }//GEN-LAST:event_jButtonDeshacerActionPerformed

    private void jButtonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonLimpiarActionPerformed

    private void jButtonEjecutarAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEjecutarAsocActionPerformed
        // TODO add your handling code here:
        jTextAreaAsoc.append(consultaAsociacion);
    }//GEN-LAST:event_jButtonEjecutarAsocActionPerformed

    private void jButtonCargarPerfilAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargarPerfilAsocActionPerformed
        // TODO add your handling code here:
        String consulta = "";
        DiscretizeCardel objDiscretizeCardel = new DiscretizeCardel();
        ConsultasPredefinidas consultasPredefinidas = new ConsultasPredefinidas();
        //consulta = consultasPredefinidas.retornarConsulta(jComboBoxPerfilAsoc.getSelectedIndex(), jSpinnerMesAsoc.getValue().toString());
       consulta = consultasPredefinidas.retornarConsulta(jComboBoxPerfilAsoc.getSelectedIndex(), jSpinnerMesAsoc.getValue().toString());
       FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
        try {
            Instances instancia = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
            Instances salida=objDiscretizeCardel.discretizar(instancia, 10);
            consultaAsociacion = aplicarAsociacion.algoritmoApriori(salida,Double.parseDouble(confianzaMinima.getValue().toString()) );
        } catch (Exception ex) {
            Logger.getLogger(ColmovilGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //aplicarAsociacion.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
    }//GEN-LAST:event_jButtonCargarPerfilAsocActionPerformed

    private void jTextFieldConsultaSQLAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldConsultaSQLAsocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldConsultaSQLAsocActionPerformed

    private void jButtonLimpiarAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarAsocActionPerformed
        // TODO add your handling code here:
        jTextAreaAsoc.setText("");
    }//GEN-LAST:event_jButtonLimpiarAsocActionPerformed

    private void jButtonConsultaSqlAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaSqlAsocActionPerformed
        // TODO add your handling code here:
        aplicarAsociacion.realizarConsultaABaseDeDatosTipoWekaInstances(jTextFieldConsultaSQLAsoc.getText());
    }//GEN-LAST:event_jButtonConsultaSqlAsocActionPerformed
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
    private javax.swing.JButton botonConsultaSQL;
    private javax.swing.JButton botonEjecutar;
    private javax.swing.JButton botonLimpiarArea;
    private javax.swing.JComboBox comboAlgortimo;
    private javax.swing.JSpinner confianzaMinima;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAbrir;
    private javax.swing.JButton jButtonCargarPerfilAsoc;
    private javax.swing.JButton jButtonConexionBD;
    private javax.swing.JButton jButtonConsultaSqlAsoc;
    private javax.swing.JButton jButtonDeshacer;
    private javax.swing.JButton jButtonDiscretizar;
    private javax.swing.JButton jButtonEjecutarAsoc;
    private javax.swing.JButton jButtonEliminarAtributos;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonLimpiar;
    private javax.swing.JButton jButtonLimpiarAsoc;
    private javax.swing.JButton jButtonNinguno;
    private javax.swing.JButton jButtonSeleccionarTodo;
    private javax.swing.JComboBox jComboBoxAlgoritmoAsoc;
    private javax.swing.JComboBox jComboBoxNombreTablas;
    private javax.swing.JComboBox jComboBoxPerfilAsoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAlgoritmoAsoc;
    private javax.swing.JLabel jLabelDistinto;
    private javax.swing.JLabel jLabelGraficoDispersion;
    private javax.swing.JLabel jLabelMesAsoc;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNulos;
    private javax.swing.JLabel jLabelPerfilAsoc;
    private javax.swing.JLabel jLabelPorcentajeAsoc;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JLabel jLabelTituloAsoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelCargaDatosAsoc;
    private javax.swing.JPanel jPanelGraficoBarras;
    private javax.swing.JPanel jPanelGraficoDispersion;
    private javax.swing.JPanel jPanelParametrosAsoc;
    private javax.swing.JPanel jPanelSalidaAsoc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSpinner jSpinnerMesAsoc;
    private javax.swing.JSpinner jSpinnerPorcentajeAsoc;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableAtributos;
    private javax.swing.JTable jTableEstadistica;
    private javax.swing.JTextArea jTextAreaAsoc;
    private javax.swing.JTextField jTextFieldConsultaSQLAsoc;
    private javax.swing.JTextField jTextFieldDistinto;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldNulos;
    private javax.swing.JTextField jTextFieldNulosTabla;
    private javax.swing.JTextField jTextFieldTipo;
    private javax.swing.JLabel labelGrafico;
    private javax.swing.JSpinner porcentajeDatos;
    private javax.swing.JSpinner seleccionMes;
    private javax.swing.JSpinner seleccionNumeroClusters;
    private javax.swing.JComboBox seleccionPerfil;
    private javax.swing.JTextField textoConsultaSQL;
    // End of variables declaration//GEN-END:variables
}
