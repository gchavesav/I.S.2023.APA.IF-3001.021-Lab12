package domain;

import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListGraphTest {

    @Test
    void test() throws GraphException, ListException, StackException, QueueException {
        SinglyLinkedListGraph graph = new SinglyLinkedListGraph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("H");
        graph.addVertex("I");
        graph.addVertex("J");

        graph.addEdgeAndWeight("A","B","Cozumel");
        graph.addEdgeAndWeight("A","E","Ambergris Caye");
        graph.addEdgeAndWeight("B","C","Útila");
        graph.addEdgeAndWeight("A","H","Bocas del Toro");
        graph.addEdgeAndWeight("C","D","Roatán");
        graph.addEdgeAndWeight("D","H","Isla Colón");
        graph.addEdgeAndWeight("E","F","Isla de San Martín");
        graph.addEdgeAndWeight("E","J","Anguila");
        graph.addEdgeAndWeight("F","J","Isla de Mona");
        graph.addEdgeAndWeight("F","G","Isla Trinidad");
        graph.addEdgeAndWeight("G","H","Isla La Tortuga");
        graph.addEdgeAndWeight("B","I","Isla Tierra Bomba");
        graph.addEdgeAndWeight("I","C","Isla de San Andrés");
        graph.addEdgeAndWeight("I","D","Isla Popa");

        System.out.println(graph.toString());
        System.out.println(graph.dfs());
        System.out.println(graph.bfs());

        graph.removeVertex("B");
        graph.removeVertex("H");
        graph.removeVertex("J");
        graph.removeVertex("I");

        System.out.println("\n"+graph.dfs());
        System.out.println(graph.bfs());

        System.out.println("\n"+graph.toString());

    }
}