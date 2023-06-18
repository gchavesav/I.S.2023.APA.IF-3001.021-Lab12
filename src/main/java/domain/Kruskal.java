package domain;

import domain.list.ListException;

import java.util.*;

public class Kruskal {

    private ArrayList<Edge> edges = new ArrayList<>();

    private ArrayList<VertexK> vertices = new ArrayList<>();
    private ArrayList<HashSet<VertexK>> subsets = new ArrayList<>();
    Vertex[] vertexList;

    public void Kruskal(AdjacencyMatrixGraph adjacencyMatrixGraph) throws GraphException {
        kruskalAlgorithm(adjacencyMatrixGraph);


         //adjacencyMatrixGraph
        // adjacencyListGraph;
        // singlyLinkedListGraph;

    }

    public static void main(String args[]) throws GraphException {
        Kruskal kruskal = new Kruskal();
        AdjacencyMatrixGraph adjacencyMatrixGraph;
//        AdjacencyListGraph adjacencyListGraph;
//        SinglyLinkedListGraph singlyLinkedListGraph;

        adjacencyMatrixGraph = new AdjacencyMatrixGraph(10);


//        try {
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

            adjacencyMatrixGraph.addEdgeAndWeight(0, 1, 2);
            adjacencyMatrixGraph.addEdgeAndWeight(0, 3, 3);
            adjacencyMatrixGraph.addEdgeAndWeight(0, 6, 6);
            adjacencyMatrixGraph.addEdgeAndWeight(1, 2, 1);
            adjacencyMatrixGraph.addEdgeAndWeight(1, 6, 4);
            adjacencyMatrixGraph.addEdgeAndWeight(3, 5, 9);
            adjacencyMatrixGraph.addEdgeAndWeight(3, 9, 16);
            adjacencyMatrixGraph.addEdgeAndWeight(2, 4, 11);
            adjacencyMatrixGraph.addEdgeAndWeight(2, 8, 17);
            adjacencyMatrixGraph.addEdgeAndWeight(4, 7, 7);
            adjacencyMatrixGraph.addEdgeAndWeight(4, 8, 8);
            adjacencyMatrixGraph.addEdgeAndWeight(4, 6, 12);
            adjacencyMatrixGraph.addEdgeAndWeight(5, 7, 5);
            adjacencyMatrixGraph.addEdgeAndWeight(5, 9, 10);
            adjacencyMatrixGraph.addEdgeAndWeight(5, 6, 13);
            adjacencyMatrixGraph.addEdgeAndWeight(6, 8, 18);
            adjacencyMatrixGraph.addEdgeAndWeight(6, 9, 19);
            adjacencyMatrixGraph.addEdgeAndWeight(7, 9, 14);
            adjacencyMatrixGraph.addEdgeAndWeight(7, 8, 15);
            adjacencyMatrixGraph.addEdgeAndWeight(5, 4, 20);
//        } catch (ListException e) {
//            throw new RuntimeException(e);
//        }

        kruskal.kruskalAlgorithm(adjacencyMatrixGraph);

    }

    public Graph kruskalAlgorithm(Graph graph) {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
        subsets = new ArrayList<>();
        Graph resultGraph = null;
        try {
            int size = graph.size();
            if (graph instanceof SinglyLinkedListGraph) resultGraph = new SinglyLinkedListGraph();
            else if (graph instanceof AdjacencyListGraph) resultGraph = new AdjacencyListGraph(size);
            else resultGraph = new AdjacencyMatrixGraph(size);
            vertexList = new Vertex[size];
            //PARTE DE DEBE ADAPTARSE A LOS DEMAS GRAPH
            for (int i = 0; i < size; i++) {
                if (graph instanceof SinglyLinkedListGraph)
                    vertexList[i] = graph.getVertexByIndex(i + 1);
                else
                    vertexList[i] = graph.getVertexByIndex(i);
                vertices.add(new VertexK((int) vertexList[i].data));
            }

            if (graph instanceof AdjacencyMatrixGraph) {
                Object adjacencyMatrix[][] = ((AdjacencyMatrixGraph) graph).getAdjacencyMatrix();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {

                        if (!adjacencyMatrix[i][j].equals(0)) { //si existe una arista
                            edges.add(new Edge(((int) vertexList[i].data), (int) vertexList[j].data, (int) adjacencyMatrix[i][j]));
                        }
                    }
                }//  //PARTE DE DEBE ADAPTARSE A LOS DEMAS GRAPH
            }else {
                for (int i = 0; i < size; i++) {
                    Vertex vA = null;
                    if (graph instanceof SinglyLinkedListGraph) vA = graph.getVertexByIndex(i + 1);
                    else vA = graph.getVertexByIndex(i);
                    if (!vA.edgesList.isEmpty()) {
                        int nEdges = vA.edgesList.size();

                        for (int j = 0; j < nEdges; j++) {
                            EdgeWeight edge = (EdgeWeight) vA.edgesList.getNode(j + 1).data;
                            int vB = (int) edge.getEdge();
                            int weightB = (int) edge.getWeight();
                            edges.add(new Edge((Integer) vA.data, vB, weightB));
                        }
                    }
                }
            }
            for (int i = 0; i < size; i++) {
                if (graph instanceof SinglyLinkedListGraph) resultGraph.addVertex(graph.getVertexByIndex(i+1).data);
                else resultGraph.addVertex(graph.getVertexByIndex(i).data);
            }
        }catch (ListException | GraphException e){
            e.printStackTrace();
        }
        // make subset of each vertex
        for (int i = 0; i < vertices.size(); i++) {
            HashSet<VertexK> set = new HashSet<>();
            set.add(vertices.get(i));
            subsets.add(set);
        }

        // sorting the edges based on the weight
//        System.out.println("The input Graph with "+ vertices.size() +" vertex and following edges");
//        System.out.println(edges);
        Collections.sort(edges, new weightComparator());
//        System.out.println("The MST after running Kruskals Algorithm");
//        System.out.println("CANTIDAD DE EDGES "+ edges.size());
//        System.out.println("Src --> Dst == Wt");
        // Union and Find algorithm to detect a cycle
        for (int i = 0; i < edges.size(); i++) {
            Edge edg = edges.get(i);
            try {
                int edgSrc = graph.indexOf(edg.src);
                int edgDest = graph.indexOf(edg.dest);
                if (graph instanceof SinglyLinkedListGraph){
                    edgSrc--;
                    edgDest--;
                }
                VertexK srcNode = vertices.get(edgSrc);
                VertexK destNode = vertices.get(edgDest);

                if (find(srcNode) != find(destNode)) {
//                    System.out.println(edg.src + " --> " + edg.dest + " == " + edg.weight);
                    if (resultGraph != null) resultGraph.addEdgeAndWeight(edg.src, edg.dest, edg.weight);
                    union(find(srcNode), find(destNode));
                }
            } catch (GraphException | ListException e) {
                throw new RuntimeException(e);
            }
        }

        return resultGraph;
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
