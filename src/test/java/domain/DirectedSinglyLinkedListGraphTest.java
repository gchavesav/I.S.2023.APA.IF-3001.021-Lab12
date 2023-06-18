package domain;

import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import org.junit.jupiter.api.Test;
import util.Utility;

import static org.junit.jupiter.api.Assertions.*;

class DirectedSinglyLinkedListGraphTest {

    @Test
    void Test() throws GraphException, ListException, StackException, QueueException {
        DirectedSinglyLinkedListGraph graph = new DirectedSinglyLinkedListGraph();
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");
        graph.addVertex("5");

        graph.addEdgeAndWeight("2","2","A");
        graph.addEdgeAndWeight("2","1","D");
        graph.addEdgeAndWeight("1","3","F");
        graph.addEdgeAndWeight("1","4","C");
        graph.addEdgeAndWeight("3","3","K");
        graph.addEdgeAndWeight("3","4","V");
        graph.addEdgeAndWeight("4","3","R");
        graph.addEdgeAndWeight("4","2","B");
        graph.addEdgeAndWeight("5","1","P");
        graph.addEdgeAndWeight("5","4","O");
        System.out.println(graph);

        System.out.println("\n"+graph.dfs());
        System.out.println(graph.bfs());

        graph.removeVertex("1");
        graph.removeVertex("3");
        graph.removeVertex("5");

        System.out.println("\n"+graph);

    }
}