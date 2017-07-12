package tadgrafocompleto;

/**
 *
 * @author hayhack
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.*;

public class TADGrafoCompleto extends InterfaceGrafo {
    private int[][] matriz;
    private int rows = 0;
    private int columns = 0;
    private int qtdVertices;
    private static ArrayList<Vertices> vertices;
    private static Vertices matrizVertices[][] = null;
    public ArrayList<Arestas> matrizAdj[][] = null;
        
    public TADGrafoCompleto(){
        qtdVertices=0;
        vertices=new ArrayList();
        
    }
    
    /**
     *
     * @param vertice
     */
    @Override
    public void inserirVertice(Vertices vertice){
        qtdVertices++;
        
        if(matrizAdj==null)
        {
            matrizAdj = new ArrayList[qtdVertices][qtdVertices];
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
    public Arestas insereAresta(Vertices verticeUm, Vertices verticeDois, double valor, boolean ehDirecionado) {
        Arestas A=new Arestas(verticeUm, verticeDois, valor, ehDirecionado);         

        int ind1=achaIndice(verticeUm.getChave());
        int ind2=achaIndice(verticeDois.getChave());
        
//        No caso do labirinto que estamos desenhados, os índices são exatamente representados pelas chaves dos vértices

//        int ind1=verticeUm.getChave();
//        int ind2=verticeDois.getChave();
        
//        out.println(ind1);
//        out.println(ind2);
//        
        // consulta a arrayList referente ao nó início e fim
        if(matrizAdj[ind1][ind2]!=null){
            matrizAdj[ind1][ind2].add(A);
        } else {
            matrizAdj[ind1][ind2] = new ArrayList<>();
            matrizAdj[ind1][ind2].add(A);
//            out.println(matrizAdj[ind1][ind2]);
        }
                
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
    public Arestas insereArcoDirecionado(Vertices verticeUm, Vertices verticeDois, double valor){     
        return insereAresta(verticeUm, verticeDois, valor, true);
       
    }
    
    /**
     *
     * @param verticeUm
     * @param verticeDois
     * @param valor
     * @return
     */
    @Override
    public Arestas insereArcoNaoDirecionado (Vertices verticeUm, Vertices verticeDois, double valor) {
        return insereAresta(verticeUm, verticeDois, valor, false);
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
        
        if (ind1 == -1 || ind2 == -1) {
            return ;
        } else {
            ArrayList grupoArestas = matrizAdj[ind1][ind2];
            Arestas arestaEncontrada = getAresta(vert1, vert2, chave);
            if(arestaEncontrada == null){
                out.println("Aresta no valor "+chave+" não encontrada entre os vertices "+vert1+" e "+vert2+"!");
            } else {
                grupoArestas.remove(arestaEncontrada);
            }            
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
        ArrayList v=new ArrayList();
        for(int l=0;l<qtdVertices;l++)
            for(int c=0;c<qtdVertices;c++)                
                v.add(matrizAdj[l][c]);
        return v;
    }
    
    @Override
    public void arestasIncidentes(Vertices vertice){
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
        
//        int ind1= (v.getChave());
//        int ind2= (w.getChave());
        return (matrizAdj[ind1][ind2])!= null;
//        return(matrizVertices[][])
        
    }
    
    public Arestas getAresta(Vertices v, Vertices w, int chave){
        int ind1=achaIndice(v.getChave());
        int ind2=achaIndice(w.getChave());
        ArrayList<Arestas> grupoArestas = matrizAdj[ind1][ind2];
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
    
    
    
    
    
    
    // Esses métodos são para resolução do labirinto
    
    public Arestas getAresta(Vertices v, Vertices w){
//        int ind1=(v.getChave());
//        int ind2=(w.getChave());

        int ind1=(achaIndice(v.getChave()));
        int ind2=(achaIndice(w.getChave()));

        if (matrizAdj[ind1][ind2] == null) {
            return null;
        } else {
            return matrizAdj[ind1][ind2].get(0);
        } 
    }
    
    public ArrayList<Vertices> algoritmoDijkstra(Vertices inicio, ArrayList<Vertices> destino) {
        ArrayList<Vertices> naovisitados = new ArrayList<>();
        
        for (Vertices v : vertices) {
            naovisitados.add(v);
        }
          

        Map<Vertices, Double> D = new HashMap<>();
        Map<Vertices, Vertices> antecessor = new HashMap<>();
        
        naovisitados.remove(inicio); // Reconhecendo posição inicial e removendo dos arraylist de vértices não visitados
        
        
        D.put(inicio, 0.0);

        for (Vertices v : naovisitados) {
            if (ehAdjacente(inicio, v) == true) {
                D.put(v, getAresta(inicio, v).getValor());
            } else {
                D.put(v, Double.POSITIVE_INFINITY);
            }
        }
        
        
//        out.println(naovisitados);
//
//        out.println(D);
        while(!naovisitados.isEmpty()) {
            Vertices w = verticeMenorCusto(naovisitados, D);
            
            out.println(w);
            naovisitados.remove(w);
            for (Vertices v : naovisitados) {
                
                Double custoAnterior = Double.POSITIVE_INFINITY;
                if (ehAdjacente(w, v) == true) {
                   custoAnterior = getAresta(w, v).getValor();
                }
                
                
                if ((D.get(w) + custoAnterior) < D.get(v)) {
                    antecessor.put(v, w);
                }
                D.put(v, Math.min(D.get(v), D.get(w) + custoAnterior));
                if (destino.contains(w)) {
                    //Mais de um destino, portanto, a gente com certeza pega o destino mais próximo
                    out.println("k3k");
                    return organizarCaminho(antecessor, w, inicio);
                }
            }
        }
//        out.println(antecessor);
        // Aqui, a gente tem garantia que só existe UM destino
        return organizarCaminho(antecessor, destino.get(0), inicio);

    }
    
    private ArrayList<Vertices> organizarCaminho(Map<Vertices, Vertices> antecessor, Vertices w, Vertices inicio) {
        ArrayList<Vertices> caminho = new ArrayList<>();
        
        while( w != null  ) {
            caminho.add(w);            
            Vertices anterior = antecessor.remove(w);
//            out.println(w);
            w = anterior;
            
        }
        caminho.add(inicio);        
        Collections.reverse(caminho);
        return caminho;
    }
    
    private Vertices verticeMenorCusto(ArrayList<Vertices> naovisitados, Map<Vertices, Double> D) {
        Double menorValor = Double.POSITIVE_INFINITY;
        Vertices verticeEncontrado = null;
        
        for (Vertices v : naovisitados) {
            if(D.get(v) < menorValor) {
                menorValor = D.get(v);
                verticeEncontrado = v;
            }
        }
        return verticeEncontrado;
    }
   
    public void resolverLabirinto() {
        Vertices tempInicio = null;
        ArrayList<Vertices> tempFim = new ArrayList<>();
        for (Vertices v : this.vertices) {
            if (v.getValor() == 2.0) {
                tempInicio = v;
//                out.println(tempInicio);
                
            }
            else if (v.getValor() == 3.0) {
                tempFim.add(v);
//                out.println(tempFim);
            }
        }
        
        out.println(algoritmoDijkstra(tempInicio, tempFim));
        
    }
    
    public void leitorArquivo(String nome) throws FileNotFoundException, IOException{
        FileReader arq = new FileReader("/home/hayhack/Documents/"+nome);
        BufferedReader lerArq = new BufferedReader(arq);


        String linha = lerArq.readLine(); 
        while (linha != null) {
            rows++;
//            System.out.printf("%s\n", linha);
            if(columns == 0){
                columns = linha.length();
            }

            linha = lerArq.readLine(); // lê da segunda até a última linha
        }
        
        FileReader arq2 = new FileReader("/home/hayhack/Documents/"+nome);
        BufferedReader lerArq2 = new BufferedReader(arq2);
        String linhaMatriz = lerArq2.readLine();
        
        int[][] a = new int[rows][columns];  
        
//        out.println(linhaMatriz);
        for(int i = 0; i < rows; ++i){  
            for(int j = 0; j < columns; ++j)
            {
                a[i][j] = Character.getNumericValue(linhaMatriz.charAt(j));
//                out.print(a[i][j]);
            }
//            out.println("");
            linhaMatriz = lerArq2.readLine(); 
            
        }
        matriz = a;
        arq2.close();
    }
    
    public void gerarMatrizVertices(){
        Vertices[][] temp;
        temp = new Vertices[rows][columns];
        Vertices novo;
        int cont = 0; 
        for(int i = 0; i < rows; ++i){  
            for(int j = 0; j < columns; ++j)
            {
                if(matriz[i][j] != 1){
                    novo = new Vertices(cont, matriz[i][j]);
                    temp[i][j] = novo;
                    cont++;
                    inserirVertice(novo);
                } else {
                    temp[i][j] = null;
                }
//                out.print(temp[i][j]+" ");
            }
//            out.println("");
        }
        qtdVertices = cont;
        matrizVertices = temp;
        
    }
    
    public void gerarGrafoLabirinto(){
//  Aqui se completa a matriz de vértices adjacentes com os valores das arestas, e informando onde tem as arestas D: fuck i'm stupid
        
        int i = 0;
        while(i < rows) {
//            out.println(i);
            for(int j = 0; j < columns; ++j)
            {
//                out.print(matrizVertices[i][j]);

                if(matrizVertices[i][j] != null){
                    
                    
                    if ( j == 0 ) {
//                        return;
                    } else {
                        if(matrizVertices[i][j-1] != null && j-1 >= 0){
                            insereAresta(matrizVertices[i][j], matrizVertices[i][j-1], 1, false);
//                            out.println("imprimindo os vértices que representam sentido oeste");
//                            out.println("origem: " + oeste.getVerticeOrigem() + "destino:" + oeste.getVerticeDestino());

                        }     
                    }
                    
                    
                    if(j == columns-1) {
//                        return;
                    } else {
                        if(matrizVertices[i][j+1] != null){
                            insereAresta(matrizVertices[i][j], matrizVertices[i][j+1], 1, false);
//                            out.println("imprimindo os vértices que representam sentido leste");
//                            out.println("origem: " + leste.getVerticeOrigem() + "destino:" + leste.getVerticeDestino());
                        }
                    }
                    
                    if(matrizVertices[i-1][j] != null && i-1 >= 0){
                        insereAresta(matrizVertices[i][j], matrizVertices[i-1][j], 1, false);
//                            out.println("imprimindo os vértices que representam sentido norte");
//                            out.println("origem: " + norte.getVerticeOrigem() + "destino:" + norte.getVerticeDestino());
                    }
                    
                    if(matrizVertices[i+1][j] != null && i+1 <= rows){
                        insereAresta(matrizVertices[i][j], matrizVertices[i+1][j], 1, false);
//                        out.println("imprimindo os vértices que representam sentido sul");
//                        out.println("origem: " + sul.getVerticeOrigem() + "destino:" + sul.getVerticeDestino());
                    }
                    
                }
            }
//        out.println("");
        //out.println("Construída a linha " + i + " da matriz!" );
        i++;
        }
    }

    
    public static void main(String[] args) throws IOException {
        
        
//  Do Labirinto

        TADGrafoCompleto grafo = new TADGrafoCompleto();
        
      
        
        grafo.leitorArquivo("labirinto.dat");
//        grafo.leitorArquivo("labirinto2.dat");
        grafo.gerarMatrizVertices();
        grafo.gerarGrafoLabirinto();       
        grafo.mostraMatriz();
        grafo.resolverLabirinto();

        

// Com um grafo qualquer sem ser o labirinto
        
//        Vertices a = new Vertices(55, 0);
//        Vertices b = new Vertices(44, 0);
//        Vertices c = new Vertices(33, 0);
//        Vertices d = new Vertices(22, 0);
        
//        TADGrafoCompleto grafo = new TADGrafoCompleto();
////        
//        grafo.inserirVertice(a);
//        grafo.inserirVertice(b);
//        grafo.inserirVertice(c);
//        grafo.inserirVertice(d);
//        
        
//        System.out.println(grafo.vertices());   
//        grafo.mostraVertices();
//        System.out.println(grafo.ordem());
//        System.out.println(grafo.achaIndice(a.getChave()));
//        System.out.println(grafo.achaIndice(b.getChave()));
//        System.out.println(grafo.achaIndice(c.getChave()));
//        System.out.println(grafo.achaIndice(d.getChave()));
//        System.out.println(grafo.achaIndice(e.getChave()));
//        
//        
//        grafo.insereArcoDirecionado(a, b, 500);
//        grafo.insereArcoNaoDirecionado(b, c, 200);
//        grafo.insereArcoDirecionado(c, a, 7);
//        grafo.insereArcoDirecionado(b, d, 27);
//        
//        grafo.insereArcoDirecionado(c, e, 200, true);
//        grafo.insereArcoNaoDirecionado(e, a, 12);
//        grafo.insereArcoNaoDirecionado(e, a, 14);
//        grafo.insereArcoNaoDirecionado(b, e, 14);
//        grafo.insereArcoNaoDirecionado(a, c, 14);
        
//        grafo.insereAresta(a, b, 500, true);
//        grafo.insereAresta(a, b, 200, true);
//        grafo.insereAresta(a, b, 76, true);
//        grafo.removeArco(a,b,500);
//        grafo.insereArcoNaoDirecionado(b, c, 200);
//        grafo.insereAresta(c, a, 7, true);
//        grafo.insereAresta(b, d, 27, true);
//        grafo.insereArco(c, e, 200, true);
//        grafo.insereArco(e, a, 12, false);
//        grafo.insereArco(e, a, 14, false);
//        grafo.insereArco(b, e, 14, false);
//        grafo.insereAresta(a, c, 14, false);
//        grafo.insereArcoDirecionado(a, b, 500);
//        grafo.insereArcoNaoDirecionado(b, c, 200);
//        grafo.insereArcoDirecionado(c, a, 7);
//        grafo.insereArcoDirecionado(b, d, 27);
//        grafo.insereArcoDirecionado(c, e, 200, true);
//        grafo.insereArcoNaoDirecionado(e, a, 12);
//        grafo.insereArcoNaoDirecionado(e, a, 14);
//        grafo.insereArcoNaoDirecionado(b, e, 14);
//        grafo.insereArcoNaoDirecionado(a, c, 14);
//        grafo.removerVertice(e);
//        grafo.removeArco(a,b,200);
//        out.println(grafo.grau(e));
//        grafo.arestasIncidentes(e);
//        grafo.arestas();
//        out.println(grafo.finalVertices(grafo.getAresta(a, b, 500)));
//        out.println(grafo.ehAdjacente(a, c));

        

//        System.out.println(grafo.arestas());
//        grafo.verificarCaminhoEuleriano();

//        grafo.mostraMatriz();
//        
//        ArrayList<Vertices> fim = new ArrayList<>();
//        fim.add(d);
//        
//        out.println(grafo.algoritmoDijkstra(a, fim));

       
    }
  
    
}
