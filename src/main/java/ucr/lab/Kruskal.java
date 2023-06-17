package ucr.lab;

import domain.*;

import java.util.*;

public class Kruskal {

    private ArrayList<Edge> edges = new ArrayList<>();

    private ArrayList<VertexK> vertices = new ArrayList<>();
    private ArrayList<HashSet<VertexK>> subsets = new ArrayList<>();
    Vertex[] vertexList;

    public void Kruskal(AdjacencyMatrixGraph adjacencyMatrixGraph) throws GraphException {
        init(adjacencyMatrixGraph);


         //adjacencyMatrixGraph
        // adjacencyListGraph;
        // singlyLinkedListGraph;

    }

    public static void main(String args[]) throws GraphException {
        Kruskal kruskal = new Kruskal();
    AdjacencyMatrixGraph adjacencyMatrixGraph;
    AdjacencyListGraph adjacencyListGraph;
    SinglyLinkedListGraph singlyLinkedListGraph;

        adjacencyMatrixGraph = new AdjacencyMatrixGraph(10);
        adjacencyMatrixGraph.addVertex(0);
        adjacencyMatrixGraph.addVertex(1);
        adjacencyMatrixGraph.addVertex(2);
        adjacencyMatrixGraph.addVertex(3);
        adjacencyMatrixGraph.addVertex(4);
        adjacencyMatrixGraph.addVertex(5);
        adjacencyMatrixGraph.addVertex(6);
        adjacencyMatrixGraph.addVertex(7);
        adjacencyMatrixGraph.addVertex(8);
        adjacencyMatrixGraph.addVertex(9);


        adjacencyMatrixGraph.addEdgeAndWeight2(0, 1, 2);
        adjacencyMatrixGraph.addEdgeAndWeight2(0, 3, 3);
        adjacencyMatrixGraph.addEdgeAndWeight2(0, 6, 6);
        adjacencyMatrixGraph.addEdgeAndWeight2(1, 2, 1);
        adjacencyMatrixGraph.addEdgeAndWeight2(1, 6, 4);
        adjacencyMatrixGraph.addEdgeAndWeight2(3, 5, 9);
        adjacencyMatrixGraph.addEdgeAndWeight2(3, 9, 16);
        adjacencyMatrixGraph.addEdgeAndWeight2(2, 4, 11);
        adjacencyMatrixGraph.addEdgeAndWeight2(2, 8, 17);
        adjacencyMatrixGraph.addEdgeAndWeight2(4, 7, 7);
        adjacencyMatrixGraph.addEdgeAndWeight2(4, 8, 8);
        adjacencyMatrixGraph.addEdgeAndWeight2(4, 6, 12);
        adjacencyMatrixGraph.addEdgeAndWeight2(5, 7, 5);
        adjacencyMatrixGraph.addEdgeAndWeight2(5, 9, 10);
        adjacencyMatrixGraph.addEdgeAndWeight2(5, 6, 13);
        adjacencyMatrixGraph.addEdgeAndWeight2(6, 8, 18);
        adjacencyMatrixGraph.addEdgeAndWeight2(6, 9, 19);
        adjacencyMatrixGraph.addEdgeAndWeight2(7, 9, 14);
        adjacencyMatrixGraph.addEdgeAndWeight2(7, 8, 15);
       adjacencyMatrixGraph.addEdgeAndWeight2(5, 4, 20);

        kruskal.init(adjacencyMatrixGraph);

    }

    public void init(AdjacencyMatrixGraph adjacencyMatrixGraph) throws GraphException {

        //PARTE DE DEBE ADAPTARSE A LOS DEMAS GRAPH
        for (int i = 0; i < adjacencyMatrixGraph.size(); i++) {
          vertexList =adjacencyMatrixGraph.getVertexList();
            vertices.add(new VertexK((int)vertexList[i].data));
        }
      Object adjacencyMatrix[][]=adjacencyMatrixGraph.getAdjacencyMatrix();

        for (int i = 0; i < adjacencyMatrixGraph.size(); i++) {
            for (int j = 0; j < adjacencyMatrixGraph.size(); j++) {

                if(!adjacencyMatrix[i][j].equals(0)){ //si existe una arista
                    edges.add(new Edge(((int)vertexList[i].data), (int)vertexList[j].data, (int)adjacencyMatrix[i][j]));
                }
            }
        }//  //PARTE DE DEBE ADAPTARSE A LOS DEMAS GRAPH

        // make subset of each vertex
        for (int i = 0; i < vertices.size(); i++) {
            HashSet<VertexK> set = new HashSet<>();
            set.add(vertices.get(i));
            subsets.add(set);
        }

        // sorting the edges based on the weight
        System.out.println("The input Graph with "+ vertices.size() +" vertex and following edges");
        System.out.println(edges);
        Collections.sort(edges, new weightComparator());
        System.out.println("The MST after running Kruskals Algorithm");
        System.out.println("CANTIDAD DE EDGES "+ edges.size());
        System.out.println("Src --> Dst == Wt");
        // Union and Find algorithm to detect a cycle
        for (int i = 0; i < edges.size(); i++) {
            Edge edg = edges.get(i);
            VertexK srcNode = vertices.get(edg.src);
            VertexK destNode = vertices.get(edg.dest);

            if (find(srcNode) != find(destNode)) {
                System.out.println(edg.src + " --> " + edg.dest + " == " + edg.weight);
                union(find(srcNode), find(destNode));
            }
        }
    }

    private void union(int aSubset, int bSubset) {
        HashSet<VertexK> aSet = subsets.get(aSubset);
        HashSet<VertexK> bSet = subsets.get(bSubset);
        //adding all elements of subsetB in subsetA and deleting subsetB
        Iterator<VertexK> iter = bSet.iterator();
        while (iter.hasNext()) {
            VertexK b = iter.next();
            aSet.add(b);
        }
        subsets.remove(bSubset);

    }

    private int find(VertexK node) {
        int number = -1;

        for (int i = 0; i < subsets.size(); i++) {
            HashSet<VertexK> set = subsets.get(i);
            Iterator<VertexK> iterator = set.iterator();
            while (iterator.hasNext()) {
                VertexK setnode = iterator.next();
                if (setnode.number == node.number) {
                    number = i;
                    return number;
                }

            }
        }
        return number;
    }
}

class weightComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge e1, Edge e2) {
        return e1.weight - e2.weight;
    }
}

class Edge {
    int src;
    int dest;
    int weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "\n" + src + " --> " + dest + " == " + weight
                + "]";
    }
}

class VertexK {
    int number = 0;

    VertexK(int number) {
        this.number = number;
    }
}
