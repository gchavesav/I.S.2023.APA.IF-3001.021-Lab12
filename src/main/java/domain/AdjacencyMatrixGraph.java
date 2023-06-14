/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
import domain.list.ListException;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import domain.stack.LinkedStack;
import domain.stack.StackException;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * Grafo Matriz de Adyacencia
 */
public class AdjacencyMatrixGraph implements Graph {
    private int n;
    private Vertex vertexList[];
    private Object adjacencyMatrix[][];
    private int counter; //contador de vertices
    
    //para los recorridos dfs, bfs
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public AdjacencyMatrixGraph(int n){
        if(n<=0) System.exit(1);
        this.n = n;
        this.counter = 0;
        this.vertexList = new Vertex[n];
        this.adjacencyMatrix = new Object[n][n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
        initMatrix();
    }
    
    private void initMatrix(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
    }

    public Object[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    @Override
    public int getNumNodes(){
        return counter;
    }

    public void setAdjacencyMatrix(Object[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public Vertex[] getVertexList(){
        return this.vertexList;
    }
    
    public int getVertexesCount(){
        return counter;
    }
    
    public Object getWeight(Object a, Object b) {
        return adjacencyMatrix[indexOf(a)][indexOf(b)];
    }

    @Override
    public Vertex getVertexByIndex(int index){
        return vertexList[index];
    }
    
    @Override
    public int size() {
        return counter;
    }

    @Override
    public void clear() {
        this.vertexList = new Vertex[n];
        this.adjacencyMatrix = new Object[n][n];
        this.initMatrix();
        this.counter = 0;
    }

    @Override
    public boolean isEmpty() {
        return counter==0;
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException {
        if(isEmpty()){
            throw new GraphException("Adjacency Matrix Graph is Empty");
        }
        for (int i = 0; i < counter; i++) {
            if(util.Utility.compare(vertexList[i].data, element)==0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException {
        if(isEmpty()){
            throw new GraphException("Adjacency Matrix Graph is Empty");
        }
        return !(util.Utility.compare(adjacencyMatrix[indexOf(a)][indexOf(b)], 0)==0);
    }
    
    private int indexOf(Object element){
        for (int i = 0; i < counter; i++) {
            if(util.Utility.compare(vertexList[i].data, element)==0){
                return i;
            }
        }
        return -1; //significa q el data del vertice no existe
    }

    @Override
    public void addVertex(Object element) throws GraphException {
        if(counter>=vertexList.length){
            throw new GraphException("Adjacency Matrix Graph is Full");
        }
        vertexList[counter++] = new Vertex(element);
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = 1;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = 1; //grafo no dirigido
    }

    @Override
    public void addEdgeAndWeight(Object a, Object b, Object c) throws GraphException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = c;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = c; //grafo no dirigido
    }
    
    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException {
        if(!containsEdge(a, b)){
            throw new GraphException("There is no edge between the vertexes "
                    + "["+a+"] y ["+b+"]");
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = weight; //grafo no dirigido
    }
    
    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        int index = indexOf(element);
        if(index!=-1){ //si el vertice existe
            for (int i = index; i < counter-1; i++) {
                vertexList[i] = vertexList[i+1];
                //System.arraycopy(adjacencyMatrix[i+1], 0, adjacencyMatrix[i], 0, counter);
                //movemos todas las filas, una pos hacia arriba
                for (int j = 0; j < counter; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i+1][j];
                }
            }
            //movemos todas las columnas una posicion a la izq
            for (int i = 0; i < counter; i++) {
                for (int j = index; j < counter-1; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i][j+1];
                }//for j
            }//for i
            counter--; //se decrementa por el vertice suprimido
        }
        //si ya no quedan vertices verificamos que se remuevan todas sus aristas
        if(counter==0){
            initMatrix();
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("There's no some of the vertexes");
            //throw new GraphException("Alguno o ninguno de los vértices existe");
        }
        int i = indexOf(a);
        int j = indexOf(b);
        if(i!=-1&&j!=-1){
            adjacencyMatrix[i][j] = 0;
            adjacencyMatrix[j][i] = 0;
        }
    }
    
    @Override
    public String toString(){
        String result = "ADJACENCY MATRIX GRAPH CONTENT...";
        for (int i = 0; i < counter; i++) {
            result+="\nThe vertex in the position: "+i+" is: "+vertexList[i].data;
        }
        result+="\n";
        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < counter; j++) {
                if(!adjacencyMatrix[i][j].equals(0)){ //si existe una arista
                    result+="\nThere is edge between the vertexes:  "+vertexList[i].data+"......"
                            +vertexList[j].data;
                    if(!adjacencyMatrix[i][j].equals(1)){
                        result+="_____WEIGHT: "+adjacencyMatrix[i][j];
                    }
                }
            }   
        }
        return result;
    }
    
    /**
    *____________RECORRIDOS POR GRAFOS
    */
    
    /***
     * RECORRIDO EN PROFUNDIDAD
     * @return 
     * @throws domain.GraphException
     * @throws domain.stack.StackException
     */
    @Override
    public String dfs() throws GraphException, StackException {
        setVisited(false);//marca todos los vertices como no vistados
        // inicia en el vertice 0
        String info =vertexList[0].data+", ";
         vertexList[0].setVisited(true); // lo marca
         stack.clear();
         stack.push(0); //lo apila
         while( !stack.isEmpty() ){
             // obtiene un vertice adyacente no visitado, 
             //el que esta en el tope de la pila
             int index = adjacentVertexNotVisited((int) stack.top());
             if(index==-1) // no lo encontro
                 stack.pop();
             else{
                 vertexList[index].setVisited(true); // lo marca
                 info+=vertexList[index].data+", "; //lo muestra
                 stack.push(index); //inserta la posicion
             }
         }
         return info;
	}

    /***
     * RECORRIDO POR AMPLITUD
     * @return 
     * @throws domain.GraphException
     */
    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);//marca todos los vertices como no visitados
         // inicia en el vertice 0
         String info =vertexList[0].data+", ";
         vertexList[0].setVisited(true); // lo marca
         queue.clear();
         queue.enQueue(0); // encola el elemento
         int v2;
         while(!queue.isEmpty()){
             int v1 = (int) queue.deQueue(); // remueve el vertice de la cola
             // hasta que no tenga vecinos sin visitar
             while((v2=adjacentVertexNotVisited(v1)) != -1 ){
                 // obtiene uno
                 vertexList[v2].setVisited(true); // lo marca
                 info+=vertexList[v2].data+", "; //lo muestra
                 queue.enQueue(v2); // lo encola
             } 
         }
    return info;
    }
    
    //setteamos el atributo visitado del vertice respectivo
    private void setVisited(boolean value) {
        for (int i = 0; i < counter; i++) {
            vertexList[i].setVisited(value); //value==true o false
        }//for
    }
    
    private int adjacentVertexNotVisited(int index) {
        for (int i = 0; i < counter; i++) {
            if(!adjacencyMatrix[index][i].equals(0)
               && !vertexList[i].isVisited())
                return i;//retorna la posicion del vertice adyacente no visitado
        }//for i
        return -1;
    }
    
}
