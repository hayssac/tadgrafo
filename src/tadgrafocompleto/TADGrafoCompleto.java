package tadgrafocompleto;

/**
 *
 * @author hayhack
 */
import java.util.*;

public class TADGrafoCompleto extends InterfaceGrafo {
    private int qtdVertices;
    private final ArrayList vertices;
    private ArrayList matrizAdj[][];
    public TADGrafoCompleto(){
        qtdVertices=0;
        vertices=new ArrayList();        
    }
    
    /**
     *
     * @param Vertice
     */
    @Override
    public void inserirVertice(Vertices Vertice){
        qtdVertices = qtdVertices++; // aumenta a informação de vértices no grafo
        Vertice.setChave(qtdVertices); // o índice desse vértice será o número atual de vértices
        vertices.add(Vertice); // adiciona o vértice na lista de grafo
        ArrayList matrizAdj[][] = new ArrayList[qtdVertices][qtdVertices]; //cria a matriz de adjacência QUADRADA
    }
        
    /**
     *
     * @param vertice
     */
    @Override
    public void removerVertice(Vertices vertice){        
        qtdVertices--;
        int indice=achaÍndice(vertice.getChave());
        vertices.remove(indice);  // remove o vértice do vector    
        // remove linhas e colunas da matriz de adjacência
        ArrayList tempMatrizAdj[][]=new ArrayList[qtdVertices][qtdVertices];
        int ff=0,gg;
        for(int f=0;f<qtdVertices+1;f++){
            gg=0;
            for(int g=0;g<qtdVertices+1;g++){
                if(f!=indice && g!=indice){
                  tempMatrizAdj[ff][gg]= matrizAdj[f][g];                  
                  if(g!=indice)
                      gg++;                  
                }                
            }
            if(f!=indice)
                ff++;
        }
        matrizAdj=tempMatrizAdj;
    }
    
    /**
     *
     * @param verticeUm
     * @param verticeDois
     * @param valor
     * @param eDirecionado
     * @return
     */
    @Override
    public Arestas insereArco(Vertices verticeUm, Vertices verticeDois, double valor, boolean eDirecionado){
        Arestas A=new Arestas(verticeUm, verticeDois, valor, eDirecionado);         
        int ind1=achaÍndice(verticeUm.getChave());
        int ind2=achaÍndice(verticeDois.getChave());
        
        
        // consulta a arrayList referente ao nó início e fim
        ArrayList grupoArestas = matrizAdj[ind1][ind2];
        // adiciona na lista desse nó o número de vértices
        grupoArestas.add(A); // grafo orientado
        return A;
    }

    /**
     *
     * @param verticeUm
     * @param verticeDois
     * @param eDirecionado
     * @return
     */
    public Arestas insereArco(Vertices verticeUm, Vertices verticeDois, boolean eDirecionado){
        return insereArco(verticeUm, verticeDois, 0, eDirecionado);
    }
    
    /**
     *
     * @param aresta
     */
    @Override
    public void removeArco(Arestas aresta){   // grafo orientado     
        Vertices vert1 = aresta.getVerticeOrigem();
        Vertices vert2 = aresta.getVerticeDestino();
        int ind1 = achaÍndice(vert1.getChave());
        int ind2 = achaÍndice(vert2.getChave());
        
        ArrayList grupoArestas = matrizAdj[ind1][ind2];
        grupoArestas.remove(aresta);
    }
    
    public void mostraVertices(){
        for(int f=0;f<vertices.size();f++)
            System.out.print(vertices.get(f)+",");        
    }
    
    public void mostraMatriz(){
        for(int f=0;f<qtdVertices;f++){
            for(int g=0;g<qtdVertices;g++) {
                System.out.print(matrizAdj[f][g]+" ");
            }
            System.out.println();
        }        
    }
    
    /**
     *
     * @param Vertice
     * @return
     */
    @Override
    public int grau(Vertices Vertice){
        // o grau é a quantidade de 1 na coluna que representa o vértice        
    }
    
    /**
     *
     * @return
     */
    @Override
    public int ordem(){
        return qtdVertices;
    }
    
    private int achaÍndice(int chave){
        Iterator I=vertices.iterator();
        int ind=0;        
        while(I.hasNext()){     
            Vertices v=(Vertices)(I.next());            
            if(v.getChave()==chave)// achei
                return ind;
            ind++;
        }
        return -1; // nao achei
    }
    
    /**
     *
     * @return
     */
    @Override
    public ArrayList vertices(){
        return vertices;
    }

    @Override
    public ArrayList arestas(){
        
        // refazer
        
//        ArrayList v=new ArrayList();
//        for(int l=0;l<qtdVertices;l++)
//            for(int c=0;c<qtdVertices;c++)                
//                v.add(matrizAdj[l][c]);
//        return v;
    }
    
    @Override
    public ArrayList arestasIncidentes(Vertices vertice){
        // a quantidade de arestas incidentes é representada pelo contador 
        // de valores diferentes de zero na linha que representa o vértice        

    }
    
    /**
     *
     * @param a
     * @return
     */
    @Override
    public ArrayList finalVertices(Arestas a){
        ArrayList v=new ArrayList();
        v.add(a.getVerticeOrigem());
        v.add(a.getVerticeDestino());
        return v;
    }
    
    /**
     *
     * @param v
     * @param w
     * @return
     */
    @Override
    public boolean éAdjacente(Vertices v, Vertices w){
        int ind1=achaÍndice(v.getChave());
        int ind2=achaÍndice(w.getChave());
        return (matrizAdj[ind1][ind2])!=null;
        
    }
    
    public ArrayList getAresta(Vertices v, Vertices w){
        int ind1=achaÍndice(v.getChave());
        int ind2=achaÍndice(w.getChave());
        ArrayList grupoArestas = matrizAdj[ind1][ind2];
        //nesse caso, como agora o grupo de arestas é representado em um arrayList
        //uma vez que possa existir arestas paralelas,
        //o retorno deve ser uma ArrayList
        
        return grupoArestas;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
