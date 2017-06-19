/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafocompleto;

/**
 *
 * @author hayhack
 */
public abstract class InterfaceVertices {
    //private int chave;
    //private double valor;
    //public Vertices(int chave, double valor) 
    public abstract int getChave();

    /**
     * @param chave the chave to set
     */
    public abstract void setChave(int chave);

    /**
     * @return the valor
     */
    public abstract double getValor();

    /**
     * @param valor the valor to set
     */
    public abstract void setValor(double valor);
    
}
