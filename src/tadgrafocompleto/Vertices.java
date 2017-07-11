package tadgrafocompleto;

/**
 *
 * @author hayhack
 */
public class Vertices extends InterfaceVertices {
    private int chave;
    private double valor;
    private int marcado;
    /**
     * @param chave
     * @param valor
     */
    public Vertices(int chave, double valor) {
        super();
        this.chave = chave;
        this.valor = valor;
    }
    /**
     * @return the chave
     */
    @Override
    public int getChave() {
        return chave;
    }
    /**
     * @param chave the chave to set
     */
    @Override
    public void setChave(int chave) {
        this.chave = chave;
    }
    /**
     * @return the valor
     */
    @Override
    public double getValor() {
        return valor;
    }
    /**
     * @param valor the valor to set
     */
    @Override
    public void setValor(double valor) {
        this.valor = valor;
    }
    public int getMarcado() {
        return marcado;
    }

    public void setMarcado(int marcado) {
        this.marcado = marcado;
    }
     
    @Override
    public String toString(){        
        //return "["+chave+" - "+valor+"]";
        return "["+chave+"]";
    }
    
}
