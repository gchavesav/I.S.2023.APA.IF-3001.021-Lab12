package domain;

import domain.list.ListException;
import domain.stack.LinkedStack;
import domain.stack.StackException;

import java.util.ArrayList;

public class Prim{

        private Graph grafo;
        int vertices = 10;
        int mat[][] = new int[10][10];
        int newMat[][] = new int[10][10];
        private LinkedStack edges;
   
        public Prim(Graph grafo, LinkedStack edges) throws GraphException, ListException, StackException {
            this.grafo = grafo;
            this.edges = edges;
            deleteEdges();
            mst();

        }

        public void deleteEdges() throws StackException, GraphException, ListException {
            LinkedStack auxS = new LinkedStack();
            while(!edges.isEmpty()){
                auxS.push((String) edges.pop());
            }
            while(!auxS.isEmpty()) {
                String edge = (String) auxS.pop();
                String[] edgeAndWeight = edge.split(",");
                this.edges.push(edge);
                this.grafo.removeEdge(Integer.parseInt(edgeAndWeight[0]), Integer.parseInt(edgeAndWeight[1]));
            }
            while(!this.edges.isEmpty()){
                String edge = (String) this.edges.pop();
                String[] edgeAndWeight = edge.split(",");
                auxS.push(edge);
                mat[grafo.indexOf(Integer.parseInt(edgeAndWeight[0]))][grafo.indexOf(Integer.parseInt(edgeAndWeight[1]))]=Integer.parseInt(edgeAndWeight[2]);
            }
        }

        class Resultado {
            int padre;
            int peso;
        }

        int minVert( boolean [] mst, int [] pesos){
            int minPeso = Integer.MAX_VALUE;
            int vertice = 0;

            for (int i = 0; i < vertices; i++) {
                if (mst[i]== false && minPeso > pesos [i]) {
                    minPeso =pesos[i];
                    vertice = i;
                }
            }
            return vertice;
        }
        public void mst() {
            boolean [] mst = new boolean[vertices];
            int [] pesos = new int [vertices];
            Resultado [] resultado = new Resultado [vertices];

            for (int i = 0; i < vertices; i++) {
                pesos[i] = Integer.MAX_VALUE;
                resultado[i] = new Resultado();
            }

            pesos[0]=0;
            resultado[0].padre=-1;
            for (int i = 0; i < vertices; i++) {
                int vertice = minVert(mst, pesos);
                mst[vertice]=true;
                for (int j = 0; j < vertices; j++) {
                    if (mat[vertice][j]>0) {
                        if (mst[j]==false && mat[vertice][j]< pesos[j]) {
                            pesos[j] = mat[vertice][j];
                            resultado[j].padre=vertice;
                            resultado[j].peso = pesos[j];
                        }                        
                    }                                        
                }
            }
            //printMst2(resultado);
            printMst(resultado);
        }
        public void printMst( Resultado [] resultado ) {
            int total_coste_min = 0;
            System.out.println("Arbol de recubrimiento minimo");
            for (int i = 1; i < vertices; i++) {
                newMat[i][resultado[i].padre] = resultado[i].peso;
                System.out.println("Arista " + i + " - " + resultado[i].padre + " peso: " + resultado[i].peso);
                total_coste_min +=resultado[i].peso;
            }
            System.out.println("Coste min total = " + total_coste_min);
        }

        public Graph completarGrafo() throws ListException, GraphException {
            for (int i = 0; i < newMat.length; i++) {
                for (int j = 0; j < newMat.length-1; j++) {
                    //if(i != j){
                        if(newMat[i][j]>0){
                            this.grafo.addEdgeAndWeight(grafo.getVertexByIndex(i).data, grafo.getVertexByIndex(j).data, newMat[i][j]);
                      //  }
                    }

                }
            }
            return this.grafo;
        }

/*        public static void main(String[] args) {
            int vertices = 6;
            Grafo grafo = new Grafo(vertices);
            grafo.addEdge(0, 1, 10);
	        grafo.addEdge(0, 2, 25);
	        grafo.addEdge(1, 4, 30);
	        grafo.addEdge(1, 2, 10);
	        grafo.addEdge(2, 5, 5);
	        grafo.addEdge(2, 3, 20);
	        grafo.addEdge(3, 5, 40);
	        grafo.addEdge(5, 4, 12);
	        grafo.mst();
    }   */
}
