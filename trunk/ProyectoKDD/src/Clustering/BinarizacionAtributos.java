/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clustering;

import weka.core.Instances;

/**
 *
 * @author Carlos
 */
public class BinarizacionAtributos {

    Instances instancia;

    public BinarizacionAtributos() {
    }

    /*
     * Binarizar atributos estaticos
     */
    public Instances binarizar(Instances instanciaIn, int posicionAtributo, String discriminador) {
        instancia = instanciaIn;
        for(int i = 0; i < instancia.numInstances(); i++) {
            System.out.println(instancia.instance(i).stringValue(posicionAtributo));
            if (instancia.instance(i).stringValue(posicionAtributo).equals(discriminador)) {
                instancia.instance(i).setValue(posicionAtributo, 0.0);
                System.out.println("YES");
            } else {
                instancia.instance(i).setValue(posicionAtributo, 1.0);
                System.out.println("NO");
            }
        }

        return instancia;
    }
}
