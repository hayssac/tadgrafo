package tadgrafocompleto;

import java.util.ArrayList;

/**
 *
 * @author hayhack
 */
public abstract class InterfaceGrafo {
    //private int qtdVertices;
    //private Vector vertices;
    //private Arestas matrizAdj[][];
    //public GrafoSimples()
    public abstract void inserirVertice(Vertices Vertice); // Ok

    public abstract void removerVertice(Vertices Vertice); // Ok
    
    public abstract Arestas insereAresta(Vertices VerticeUm, Vertices VerticeDois, double valor, boolean ehDirecionado);

    public abstract Arestas insereArcoDirecionado(Vertices VerticeUm, Vertices VerticeDois,
            double valor); // Ok
    
    public abstract Arestas insereArcoNaoDirecionado (Vertices verticeUm, Vertices verticeDois, double valor); // Ok 

    public abstract void removeArco(Vertices vert1, Vertices vert2, int chave); // Ok

    public abstract int grau(Vertices Vertice); // Ok

    public abstract int ordem(); // Ok

    public abstract ArrayList vertices(); // Ok

    public abstract ArrayList arestas(); // Ok

    public abstract void arestasIncidentes(Vertices vertice); // Ok

    public abstract ArrayList finalVertices(Arestas a); // Ok

    public abstract boolean ehAdjacente(Vertices v, Vertices w); // Ok
    
}
