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
 * @author Profesor Lic. Gilberth Chaves A
 * Grafo Lista de Adyacencia
 */
public class AdjacencyListGraph implements Graph {
    private int n;
    private Vertex vertexList[];
    private int counter; //contador de vertices
    
    //para los recorridos dfs, bfs
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public AdjacencyListGraph(int n){
        if(n<=0) System.exit(1);
        this.n = n;
        this.counter = 0;
        this.vertexList = new Vertex[n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
    }
    
    public Vertex[] getVertexList(){
        return this.vertexList;
    }

    @Override
    public int getNumNodes(){
        return counter;
    }
    public int getVertexesCounter(){
        return counter;
    }
    
    public Object getWeight(Object a, Object b) throws ListException {
        EdgeWeight ew = (EdgeWeight) vertexList[indexOf(a)].edgesList
                .getNode(new EdgeWeight(b, null)).getData();
        return ew.getWeight();
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
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
        this.counter = 0;
    }

    @Override
    public boolean isEmpty() {
        return counter==0;
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException {
        if(isEmpty()){
            throw new GraphException("Adjacency List Graph is Empty");
        }
        for (int i = 0; i < counter; i++) {
            if(util.Utility.compare(vertexList[i].data, element)==0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Adjacency List Graph is Empty");
        }
        return !vertexList[indexOf(a)].edgesList.isEmpty()
                ?vertexList[indexOf(a)].edgesList.contains(new EdgeWeight(b, null))
                :false;
    }
    
    public int indexOf(Object element){
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
            throw new GraphException("Adjacency List Graph is Full");
        }
        vertexList[counter++] = new Vertex(element);
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        vertexList[indexOf(a)].edgesList.add(new EdgeWeight(b, null));
        //grafo no dirigido
        vertexList[indexOf(b)].edgesList.add(new EdgeWeight(a, null));
    }

    @Override
    public void addEdgeAndWeight(Object a, Object b, Object c) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        if(!containsEdge(a, b)) {
            vertexList[indexOf(a)].edgesList.add(new EdgeWeight(b, c));
            //grafo no dirigido
            vertexList[indexOf(b)].edgesList.add(new EdgeWeight(a, c));
        }
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsEdge(a, b)){
            throw new GraphException("There is no edge between the vertexes "
                    + "["+a+"] and ["+b+"]");
        }
        EdgeWeight ew = (EdgeWeight) vertexList[indexOf(a)].edgesList
                .getNode(new EdgeWeight(b, null)).getData();
        ew.setWeight(weight);
        vertexList[indexOf(a)].edgesList.getNode(new EdgeWeight(b, null))
                .setData(ew);
        //grafo no dirigido
        ew = (EdgeWeight) vertexList[indexOf(b)].edgesList
                .getNode(new EdgeWeight(a, null)).getData();
        ew.setWeight(weight);
        vertexList[indexOf(b)].edgesList
                .getNode(new EdgeWeight(a, null)).setData(ew);
    }
    
    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        if(containsVertex(element)){
            for (int i = 0; i < counter; i++) {
                if(util.Utility.compare(vertexList[i].data, element)==0){
                    //se deben suprimir todas las aristas asociadas
                    for (int j = 0; j < counter; j++) {
                        if(containsEdge(vertexList[j].data, element)){
                            removeEdge(vertexList[j].data, element);
                        }
                    }
                 //se debe suprimir el vertice
                    for (int j = i; j < counter-1; j++) {
                        vertexList[j] = vertexList[j+1];
                        
                    }
                    counter--; //por el vertice suprimido
                }
            }
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot remove edge between "
                    + " vertexes ["+a+"] y ["+b+"]");
        }
        if(!vertexList[indexOf(a)].edgesList.isEmpty()){
            vertexList[indexOf(a)].edgesList.remove(new EdgeWeight(b, null));
        }
        //grafo no dirigido
        if(!vertexList[indexOf(b)].edgesList.isEmpty()){
            vertexList[indexOf(b)].edgesList.remove(new EdgeWeight(a, null));
        }
    }
    
    @Override
    public String toString(){
         String result = "ADJACENCY LIST GRAPH CONTENT...";
            for (int i = 0; i < counter; i++) {
                result+="\n\nVextex in the position "+i+": "+vertexList[i].data;
                if(!vertexList[i].edgesList.isEmpty()){
                    result+="\n......EDGES AND WEIGHTS: "+vertexList[i].edgesList.toString();
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
     * @throws domain.list.ListException
     */
    @Override
    public String dfs() throws GraphException, StackException, ListException {
        setVisited(false);//marca todos los vertices como no vistados
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
                 info+=vertexList[index].data+", ";
                 stack.push(index); //inserta la posicion
             }
         }
         return info;
	}//dfs

    /***
     * RECORRIDO POR AMPLITUD
     * @return 
     * @throws domain.GraphException
     * @throws domain.queue.QueueException
     * @throws domain.list.ListException
     */
    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);//marca todos los vertices como no visitados
         // inicia en el vertice 0
         String info =vertexList[0].data+", ";
         vertexList[0].setVisited(true); // lo marca
         queue.clear();
         queue.enQueue(0); // encola el elemento
         int index2;
         while(!queue.isEmpty()){
             int index1 = (int) queue.deQueue(); // remueve el vertice de la cola
             // hasta que no tenga vecinos sin visitar
             while((index2=adjacentVertexNotVisited(index1)) != -1 ){
                 // obtiene uno
                 vertexList[index2].setVisited(true); // lo marca
                 info+=vertexList[index2].data+", ";
                 queue.enQueue(index2); // lo encola
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
    
    private int adjacentVertexNotVisited(int index) throws ListException {
        Object vertexData = vertexList[index].data;
        for(int i=0; i<counter; i++)
	    if(!vertexList[i].edgesList.isEmpty()
              &&vertexList[i].edgesList
                .contains(new EdgeWeight(vertexData, null)) 
                && !vertexList[i].isVisited())
	             return i;
	     return -1;
    }
    
    public Vertex getVertex(int index) throws GraphException, ListException{
        for (int i = 0; i < counter; i++) {
            if(i==index){
                return this.vertexList[i];
            }//if
        }//for
        return null; //no existe el vertice
    } 

}
