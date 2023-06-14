/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.list.SinglyLinkedList;

/**
 *
 * @author Lic. Gilberth Chaves A
 * Vertice
 */
public class Vertex {
    public Object data;
    public SinglyLinkedList edgesList; //lista de aristas
    private boolean visited; //para los recorridos DFS, BFS
    
    
    //Constructor
    public Vertex(Object data){
        this.data = data;
        this.visited = false;
        this.edgesList = new SinglyLinkedList();
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    
    
    @Override
    public String toString() {
        return data+"";
    }
    
}
