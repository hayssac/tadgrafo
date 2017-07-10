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

//        int ind1=achaIndice(verticeUm.getChave());
//        int ind2=achaIndice(verticeDois.getChave());
        
//        No caso do labirinto que estamos desenhados, os índices são exatamente representados pelas chaves dos vértices

        int ind1=verticeUm.getChave();
        int ind2=verticeDois.getChave();
        
//        out.println(ind1);
//        out.println(ind2);
        
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
        
//        System.out.printf(rows + " " + columns);
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
                    vertices.add(novo);
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
//                            Arestas oeste = new Arestas(matrizVertices[i][j], matrizVertices[i][j-1], 1, false);
                            insereAresta(matrizVertices[i][j], matrizVertices[i][j-1], 1, false);
//                            out.println("imprimindo os vértices que representam sentido oeste");
//                            out.println("origem: " + oeste.getVerticeOrigem() + "destino:" + oeste.getVerticeDestino());

                        }     
                    }
                    
                    
                    if(j == columns-1) {
//                        return;
                    } else {
                        if(matrizVertices[i][j+1] != null){
//                            Arestas leste = new Arestas(matrizVertices[i][j], matrizVertices[i][j+1], 1, false);
                            insereAresta(matrizVertices[i][j], matrizVertices[i][j+1], 1, false);
//                            out.println("imprimindo os vértices que representam sentido leste");
//                            out.println("origem: " + leste.getVerticeOrigem() + "destino:" + leste.getVerticeDestino());
                        }
                    }
                    
                    if(matrizVertices[i-1][j] != null && i-1 >= 0){
//                        Arestas norte = new Arestas(matrizVertices[i][j], matrizVertices[i-1][j], 1, false);
                        insereAresta(matrizVertices[i][j], matrizVertices[i-1][j], 1, false);
//                            out.println("imprimindo os vértices que representam sentido norte");
//                            out.println("origem: " + norte.getVerticeOrigem() + "destino:" + norte.getVerticeDestino());
                    }
                    
                    if(matrizVertices[i+1][j] != null && i+1 <= rows){
//                        Arestas sul = new Arestas(matrizVertices[i][j], matrizVertices[i+1][j], 1, false);
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
        
        
        TADGrafoCompleto grafo = new TADGrafoCompleto();
        grafo.leitorArquivo("labirinto.dat");
        grafo.gerarMatrizVertices();
        grafo.gerarGrafoLabirinto();
        grafo.mostraMatriz();

//        out.println(grafo.vertices());
//        out.println(grafo.ordem());
        
        
        
    }
  
    
}
