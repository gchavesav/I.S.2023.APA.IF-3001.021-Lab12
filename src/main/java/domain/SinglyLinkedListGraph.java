/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.list.SinglyLinkedList;
import domain.list.ListException;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import domain.stack.LinkedStack;
import domain.stack.StackException;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A
 * Grafo Lista Enlazada
 */
public class SinglyLinkedListGraph implements Graph {
    private SinglyLinkedList vertexList; //la lista de vertices
    
    //para los recorridos dfs, bfs
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public SinglyLinkedListGraph(){
        this.vertexList = new SinglyLinkedList();
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
    }

    public int indexOf(Object element) throws ListException {
        for(int i=1;i<=vertexList.size();i++){
            Vertex vertex = (Vertex)vertexList.getNode(i).data;
            if(util.Utility.compare(vertex.data, element)==0){
                return i; //encontro el vertice
            }
        }//for
        return -1; //no existe
    }

    public SinglyLinkedList getVertexList() {
        return vertexList;
    }

    @Override
    public int getNumNodes() throws ListException{
        return vertexList.size();
    }

    @Override
    public Vertex getVertexByIndex(int index){
        try {
            return (Vertex) vertexList.getNode(index).data;
        } catch (ListException ex) {
            throw new RuntimeException(ex);
        }
    } 
    
    @Override
    public int size() throws ListException {
        return vertexList.size();
    }

    @Override
    public void clear() {
        vertexList.clear();
    }

    @Override
    public boolean isEmpty() {
        return vertexList.isEmpty();
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Singly Linked List Graph is Empty");
        }
        for(int i=1;i<=vertexList.size();i++){
            Vertex vertex = (Vertex)vertexList.getNode(i).data;
            if(util.Utility.compare(vertex.data, element)==0){
                return true; //encontro el vertice
            }
        }//for
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Singly Linked List Graph is Empty");
        }
        if(!containsVertex(a)||!containsVertex(b)){
            return false;
        }
        for(int i=1;i<=vertexList.size();i++){
            Vertex vertex = (Vertex)vertexList.getNode(i).data;
            if((util.Utility.compare(vertex.data, a)==0)
             &&!vertex.edgesList.isEmpty()&&vertex.edgesList.contains(new EdgeWeight(b, null))){
                return true;
            }
        }
        return false; //no existe la arista
    }
    
    @Override
    public void addVertex(Object element) throws GraphException, ListException {
        if(vertexList.isEmpty()){
            vertexList.add(new Vertex(element)); //agrego un nuevo objeto vertice
        }else
            if(!vertexList.contains(element)){
                vertexList.add(new Vertex(element));
            }
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + "vertexes ["+a+"] y ["+b+"]");
        }
        addVertexEdgeWeight(a, b, null, "addEdge"); //agrego la arista
        //grafo no dirigido
        addVertexEdgeWeight(b, a, null, "addEdge"); //agrego la arista
    }

    @Override
    public void addEdgeAndWeight(Object a, Object b, Object c) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Cannot add edge between "
                    + "vertexes ["+a+"] y ["+b+"]");
        }
        if(!containsEdge(a, b)) {
            addVertexEdgeWeight(a, b, c, "addEdge"); //agrego la arista
            //grafo no dirigido
            addVertexEdgeWeight(b, a, c, "addEdge"); //agrego la arista
        }
    }
    
    private void addVertexEdgeWeight(Object a, Object b, Object weight, String action) throws ListException{
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if(util.Utility.compare(vertex.data, a)==0){
                switch(action){
                    case "addEdge":
                        vertex.edgesList.add(new EdgeWeight(b, weight));
                        break;
                    case "addWeight":
                        vertex.edgesList.getNode(new EdgeWeight(b, weight))
                                .setData(new EdgeWeight(b, weight));
                        break;
                    case "remove":
                        if(vertex.edgesList!=null&&!vertex.edgesList.isEmpty()) {
                            vertex.edgesList.remove(new EdgeWeight(b, weight));
                        }
                } 
            }
        }
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsEdge(a, b)){
            throw new GraphException("There is no edge between the vertexes "
                    + "["+a+"] and ["+b+"]");
        }
        addVertexEdgeWeight(a, b, weight, "addWeight"); //agrego la arista
        //grafo no dirigido
        addVertexEdgeWeight(b, a, weight, "addWeight"); //agrego la arista
    }
    
    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        boolean removed = false;
        if(!vertexList.isEmpty() && containsVertex(element)){
             for (int i = 1; !removed&&i <= vertexList.size(); i++) {
                 Vertex vertex = (Vertex) vertexList.getNode(i).data;
                 if(util.Utility.compare(vertex.data, element)==0){ //ya lo encontro
                     vertexList.remove(new Vertex(element));
                     removed = true;
                     //ahora se debe eliminar la entrada de ese vertice de todas
                     //las listas de aristas de los otros vertices
                     int n = vertexList.size();
                     for (int j=1; vertexList!=null&&!vertexList.isEmpty()&&j<=n; j++) {
                         vertex = (Vertex) vertexList.getNode(j).data;
                         if(!vertex.edgesList.isEmpty())
                            addVertexEdgeWeight(vertex.data, element, null, "remove");
                     }
                 }//if
             }//for i
         }//if
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("No se puede suprimir la arista entre "
                    + "los vertices ["+a+"] y ["+b+"]");
        }
        addVertexEdgeWeight(a, b, null, "remove"); //suprimo la arista
        //grafo no dirigido
        addVertexEdgeWeight(b, a, null, "remove"); //suprimo la arista
        
    }
    
    @Override
    public String toString() {
        String result = "SINGLY LINKED LIST GRAPH CONTENT...\n";
        try {
            for(int i=1; i<=vertexList.size(); i++){
                Vertex vertex = (Vertex)vertexList.getNode(i).data;
                result+="\nThe vertex in the position "+i+" is: "+vertex+"\n";
                if(!vertex.edgesList.isEmpty()){
                    result+="........EDGES AND WEIGHTS: "+vertex.edgesList+"\n";
                }//if

            }//for
        } catch (ListException ex) {
            System.out.println(ex.getMessage());
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
    public String dfs() throws GraphException, StackException, ListException {
        setVisited(false);//marca todos los vertices como no vistados
        // inicia en el vertice 1
        Vertex vertex = (Vertex)vertexList.getNode(1).data;
        String info =vertex+", ";
        vertex.setVisited(true); //lo marca
         stack.clear();
         stack.push(1); //lo apila
         while( !stack.isEmpty() ){
             // obtiene un vertice adyacente no visitado, 
             //el que esta en el tope de la pila
             int index = adjacentVertexNotVisited((int) stack.top());
             if(index==-1) // no lo encontro
                 stack.pop();
             else{
                 vertex = (Vertex)vertexList.getNode(index).data;
                 vertex.setVisited(true); // lo marca
                 info+=vertex+", ";
                 stack.push(index); //inserta la posicion
             }
         }
         return info;
	}//dfs

    /***
     * RECORRIDO POR AMPLITUD
     * @return 
     * @throws domain.GraphException
     */
    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);//marca todos los vertices como no visitados
        // inicia en el vertice 1
        Vertex vertex = (Vertex)vertexList.getNode(1).data;
        String info =vertex+", ";
        vertex.setVisited(true); //lo marca
         queue.clear();
         queue.enQueue(1); // encola el elemento
         int index2;
         while(!queue.isEmpty()){
             int index1 = (int) queue.deQueue(); // remueve el vertice de la cola
             // hasta que no tenga vecinos sin visitar
             while((index2=adjacentVertexNotVisited(index1)) != -1 ){
                 // obtiene uno
                vertex = (Vertex)vertexList.getNode(index2).data;
                vertex.setVisited(true); //lo marco
                 info+=vertex+", ";
                 queue.enQueue(index2); // lo encola
             } 
         }
    return info;
    }
    
    //setteamos el atributo visitado del vertice respectivo
    private void setVisited(boolean value) throws ListException {
        for (int i=1; i<=vertexList.size(); i++) {
            Vertex vertex = (Vertex)vertexList.getNode(i).data; 
            vertex.setVisited(value); //value==true or false
        }//for
    }
    
    private int adjacentVertexNotVisited(int index) throws ListException {
        Vertex vertex1 = (Vertex)vertexList.getNode(index).data;        
        for(int i=1; i<=vertexList.size(); i++){
            Vertex vertex2 = (Vertex)vertexList.getNode(i).data; 
	    if(!vertex2.edgesList.isEmpty()&&vertex2.edgesList
                .contains(new EdgeWeight(vertex1.data, null)) 
                && !vertex2.isVisited())
	             return i;   
        }
        return -1;
    }
    
    public Vertex getVertex(Object element) throws GraphException, ListException{
	if(containsVertex(element)){
            for (int i = 1; i <= vertexList.size(); i++) {
                Vertex vertex = (Vertex) vertexList.getNode(i).data;
                if(util.Utility.compare(vertex.data, element)==0)
                    return vertex;
            }
        }
        return null; //no existe el vertice
    }
}
