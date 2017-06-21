package tadgrafocompleto;

/**
 *
 * @author hayhack
 */
import static java.lang.System.out;
import java.util.*;

public class TADGrafoCompleto extends InterfaceGrafo {
    private int qtdVertices;
    private final ArrayList<Vertices> vertices;
    public ArrayList<Arestas> matrizAdj[][] = null;
    public TADGrafoCompleto(){
        qtdVertices=0;
        vertices=new ArrayList();
        
    }
    /**
     * ATENÇÃO: A CHAVE É A INFORMAÇÃO DO VÉRTICE O VALOR É QUALQUER COISA Q SEI LÁ
     */
    
    /**
     *
     * @param vertice
     */
    @Override
    public void inserirVertice(Vertices vertice){
        // aumenta a quantidade de vértices
        // o índice do vértice é representado pelo atributo 'chave' do vértice
        // adiciona o vértice na lista de vértices do grafo
        qtdVertices++;
        
        if(matrizAdj==null)
        {
//            out.println(qtdVertices);
            matrizAdj = new ArrayList[qtdVertices][qtdVertices];
//            matrizAdj[0][1] = new ArrayList();
//            out.println(matrizAdj[0][1]);
        } else {
            ArrayList tempMatrizAdj[][]=new ArrayList[qtdVertices][qtdVertices];
            int l, c;
            for(l = 0; l < qtdVertices-1; l++){
                for(c = 0; c < qtdVertices-1; c++){
                    tempMatrizAdj[l][c] = matrizAdj[l][c];
                }
            }
            matrizAdj = tempMatrizAdj;
        }
        
        vertices.add(vertice);
     }
        
    /**
     *
     * @param vertice
     */
    @Override
    public void removerVertice(Vertices vertice){        
        qtdVertices--;
        int indice=achaIndice(vertice.getChave());
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
     * @param ehDirecionado
     * @return
     */
    @Override
    public Arestas insereArco(Vertices verticeUm, Vertices verticeDois, double valor, boolean ehDirecionado){     
//        out.println(verticeUm + " " + verticeDois + " " + valor + " " + ehDirecionado);
        Arestas A=new Arestas(verticeUm, verticeDois, valor, ehDirecionado);         
        
//        out.println(verticeUm.getChave());
        int ind1=achaIndice(verticeUm.getChave());
        int ind2=achaIndice(verticeDois.getChave());
        
        // consulta a arrayList referente ao nó início e fim
        if(matrizAdj[ind1][ind2]!=null){
           matrizAdj[ind1][ind2].add(A);
        } else {
//            out.println("entrou no ==");
            matrizAdj[ind1][ind2] = new ArrayList<>();
            matrizAdj[ind1][ind2].add(A);
//            out.println(matrizAdj[ind1][ind2]);
        }
//        out.println(matrizAdj[ind1][ind2]);
//        ArrayList<Arestas> grupoArestas = matrizAdj[ind1][ind2];
        
        // adiciona na lista desse nó o número de vértices
//        grupoArestas.add(A); // grafo orientado
        return A;
    }
    
    /**
     *
     * @param verticeUm
     * @param verticeDois
     * @param valor
     * @return
     */
    @Override
    public Arestas insereArcoSemDirecao (Vertices verticeUm, Vertices verticeDois, double valor) {
        return insereArco(verticeUm, verticeDois, valor, false);
    
    }
    
    /**
     *
     * @param vert1
     * @param vert2
     * @param chave
     */
    @Override
    public void removeArco(Vertices vert1, Vertices vert2, int chave){   // grafo orientado     
        int ind1 = achaIndice(vert1.getChave());
        int ind2 = achaIndice(vert2.getChave());
        
        ArrayList grupoArestas = matrizAdj[ind1][ind2];
        Arestas arestaEncontrada = getAresta(vert1, vert2, chave);
        if(arestaEncontrada == null){
            out.println("Aresta no valor "+chave+" não encontrada entre os vertices "+vert1+" e "+vert2+"!");
        } else {
            grupoArestas.remove(arestaEncontrada);
        }
        
    }
    
    public void mostraVertices(){
        for(int f=0;f<vertices.size();f++){
            System.out.print(vertices.get(f)+",");  
        }
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
     * @param vertice
     * @return
     */
    @Override
    public int grau(Vertices vertice){
        // o grau é o contador da quantidade de valores diferentes de 0 na linha que representa o vértice
        int indiceDoVertice = achaIndice(vertice.getChave());
        int grauDoVertice = 0;
        
        for (int l = 0; l < qtdVertices; l++) {
            if ( matrizAdj[l][indiceDoVertice] != null ) {
                grauDoVertice += matrizAdj[l][indiceDoVertice].size();
            }
        }
        
        for(int c = 0; c < qtdVertices; c++){
            if ( matrizAdj[indiceDoVertice][c] != null ) {
                grauDoVertice += matrizAdj[indiceDoVertice][c].size();
            }
        }
//        out.println(grauDoVertice);
        return grauDoVertice;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int ordem(){
        return qtdVertices;
    }
    
    private int achaIndice(int chave){
        Iterator I=vertices.iterator();
        int ind=0;        
        while(I.hasNext()){
            Vertices v=(Vertices)(I.next());   
            
            if(v.getChave()== chave){// achei
//                out.println(ind);
                return ind;
            }
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

    // vai sair arrays dentro de arrays

    ArrayList v=new ArrayList();
    for(int l=0;l<qtdVertices;l++)
        for(int c=0;c<qtdVertices;c++)                
            v.add(matrizAdj[l][c]);
    return v;
    }
    
    @Override
    public void arestasIncidentes(Vertices vertice){
        // as arestas incidentes é representada pelo contador 
        // de valores diferentes de zero na linha que representa o vértice
        int indiceDoVertice = achaIndice(vertice.getChave());
        for (int g = 0; g < qtdVertices; g++) {
            if ( matrizAdj[indiceDoVertice][g] != null ) {
                out.println(matrizAdj[indiceDoVertice][g]);
            }
        }
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
    public boolean ehAdjacente(Vertices v, Vertices w){
        int ind1=achaIndice(v.getChave());
        int ind2=achaIndice(w.getChave());
        return (matrizAdj[ind1][ind2])!= null;
        //se retornar null, significa que os vértices não são adjacentes um ao outro
        
    }
    
    public Arestas getAresta(Vertices v, Vertices w, int chave){
        int ind1=achaIndice(v.getChave());
        int ind2=achaIndice(w.getChave());
        ArrayList<Arestas> grupoArestas = matrizAdj[ind1][ind2];
        //nesse caso, como agora o grupo de arestas é representado em um arrayList
        //uma vez que possa existir arestas paralelas,
        //o retorno deve ser uma ArrayList
        //se vier vazia, significa que não tem nenhuma aresta entre os vértices
        if(grupoArestas == null){
            return null;
        } else {
            int tamanho = grupoArestas.size(), cont = 0;
            while(tamanho>0){
                if(grupoArestas.get(cont).getValor() == chave){
                    return grupoArestas.get(cont);
                }
                cont++;
                tamanho--;
            }
            return null;
        }
        
    }
    
    public boolean verificarCaminhoEuleriano(){
        int tam = qtdVertices, cont = 0, impares = 0;
        while(tam!=0){
            int grau = grau(vertices.get(cont));
            out.println(grau);
            if(grau%2!=0){
                impares++;
//                out.println("s");
            }
            cont++;
            tam--;
        }
        
        if(impares>2){
            out.println("Não existe caminhos eulerianos!!!");
            return false;
        } else {
            out.println("Existe caminhos eulerianos!!!");
            return true;
        }
    }
    
    public static void main(String[] args) {
        
        
        Vertices a = new Vertices(55, 0);
        Vertices b = new Vertices(44, 0);
        Vertices c = new Vertices(33, 0);
//        Vertices d = new Vertices(22, 0);
//        Vertices e = new Vertices(11, 0);
        
        TADGrafoCompleto grafo = new TADGrafoCompleto();
        
        grafo.inserirVertice(a);
        grafo.inserirVertice(b);
        grafo.inserirVertice(c);
//        grafo.inserirVertice(d);
//        grafo.inserirVertice(e);
        
        
        System.out.println(grafo.vertices());   
        grafo.mostraVertices();
        System.out.println(grafo.ordem());
//        System.out.println(grafo.achaIndice(a.getChave()));
//        System.out.println(grafo.achaIndice(b.getChave()));
//        System.out.println(grafo.achaIndice(c.getChave()));
//        System.out.println(grafo.achaIndice(d.getChave()));
//        System.out.println(grafo.achaIndice(e.getChave()));
//        
        
        
        grafo.insereArco(a, b, 500, true);
        grafo.insereArco(a, b, 200, true);
        grafo.insereArco(a, b, 76, true);
        grafo.removeArco(a,b,500);
        grafo.insereArcoSemDirecao(b, c, 200);
        grafo.insereArco(c, a, 7, true);
//        grafo.insereArco(b, d, 27, true);
//        grafo.insereArco(c, e, 200, true);
//        grafo.insereArco(e, a, 12, false);
//        grafo.insereArco(e, a, 14, false);
//        grafo.insereArco(b, e, 14, false);
//        grafo.insereArco(a, c, 14, false);
//        grafo.removerVertice(e);
//        grafo.removeArco(a,b,200);
//        out.println(grafo.grau(e));
//        grafo.arestasIncidentes(e);
//        grafo.arestas();
//        out.println(grafo.finalVertices(grafo.getAresta(a, b, 500)));
//        out.println(grafo.ehAdjacente(a, c));

        System.out.println(grafo.arestas());
        grafo.verificarCaminhoEuleriano();
        
    }
    
}
