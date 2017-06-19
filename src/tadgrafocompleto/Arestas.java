package tadgrafocompleto;

/**
 *
 * @author hayhack
 */
public class Arestas extends InterfaceArestas {
    private Vertices verticeOrigem;
    private Vertices verticeDestino;
    private double valor;
    private boolean direcionada;
   
    /**
     * @param valor
     * @param direcionada
     * @param verticeOrigem
     * @param verticeDestino
     */
    public Arestas(Vertices verticeOrigem, Vertices verticeDestino,
                   double valor, boolean direcionada) {
        super();
        this.verticeOrigem=verticeOrigem;
        this.verticeDestino=verticeDestino;
        this.valor = valor;
        this.direcionada = direcionada;
    }
    /**
     * @return Returns the verticeDestino.
     */
    @Override
    public Vertices getVerticeDestino() {
        return verticeDestino;
    }
    /**
     * @param verticeDestino The verticeDestino to set.
     */
    @Override
    public void setVerticeDestino(Vertices verticeDestino) {
        this.verticeDestino = verticeDestino;
    }
    /**
     * @return Returns the verticeOrigem.
     */
    @Override
    public Vertices getVerticeOrigem() {
        return verticeOrigem;
    }
    /**
     * @param verticeOrigem The verticeOrigem to set.
     */
    @Override
    public void setVerticeOrigem(Vertices verticeOrigem) {
        this.verticeOrigem = verticeOrigem;
    }


    /**
     * @return  direcionada
     */
    @Override
    public boolean éDirecionada() {
        return direcionada;
    }

    /**
     * @param direcionada the direcionada to set
     */
    @Override
    public void setDirecionada(boolean direcionada) {
        this.direcionada = direcionada;
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

    /**
     * @return the verticeX
     */
    @Override
    public String toString(){
        return "["+valor+"]";
    }
    
}
