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
    public abstract void inserirVertice(Vertices Vertice);

    public abstract void removerVertice(Vertices Vertice);

    public abstract Arestas insereArco(Vertices VerticeUm, Vertices VerticeDois,
            double valor, boolean eDirecionado);

    public abstract void removeArco(Arestas Aresta);

    public abstract int grau(Vertices Vertice);

    public abstract int ordem();

    public abstract ArrayList vertices();

    public abstract ArrayList arestas();

    public abstract ArrayList arestasIncidentes(Vertices vertice);

    public abstract ArrayList finalVertices(Arestas a);

    public abstract boolean éAdjacente(Vertices v, Vertices w);
    
}
