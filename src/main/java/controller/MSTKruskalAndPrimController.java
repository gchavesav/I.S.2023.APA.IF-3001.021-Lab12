package controller;

import domain.*;
import domain.list.ListException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import domain.Kruskal;
import util.Utility;

public class MSTKruskalAndPrimController {

    @FXML
    private Pane pnGraph;
    @FXML
    private Pane pnMST;
    @FXML
    private RadioButton rbtAdjList;
    @FXML
    private RadioButton rbtAdjMatrix;
    @FXML
    private RadioButton rbtKruskal;
    @FXML
    private RadioButton rbtLinkedList;
    @FXML
    private RadioButton rbtPrim;
    private AdjacencyListGraph adjacencyListGraph;
    private AdjacencyMatrixGraph adjacencyMatrixGraph;
    private SinglyLinkedListGraph singlyLinkedListGraph;
    private AdjacencyListGraph kruskalAdjacencyListGraph;
    private AdjacencyMatrixGraph kruskalAdjacencyMatrixGraph;
    private SinglyLinkedListGraph kruskalSinglyLinkedListGraph;
    private ToggleGroup group;
    Kruskal kruskal ;

    @FXML
    void initialize(){
        kruskal= new Kruskal();
        singlyLinkedListGraph = new SinglyLinkedListGraph();
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(10);
        adjacencyListGraph = new AdjacencyListGraph(10);
        group = new ToggleGroup();
        rbtAdjMatrix.setToggleGroup(group);
        rbtAdjList.setToggleGroup(group);
        rbtLinkedList.setToggleGroup(group);
        ToggleGroup group1 = new ToggleGroup();
        rbtKruskal.setToggleGroup(group1);
        rbtPrim.setToggleGroup(group1);
    }

    @FXML
    void onActionRandomize(ActionEvent event) {
        if (rbtLinkedList.isSelected() && rbtKruskal.isSelected()){
            randomizeGraph(singlyLinkedListGraph);
            drawGraph(singlyLinkedListGraph, this.pnGraph);
            kruskalSinglyLinkedListGraph = (SinglyLinkedListGraph) kruskal.kruskalAlgorithm(singlyLinkedListGraph);
            drawGraph(kruskalSinglyLinkedListGraph, this.pnMST);
        } else if (rbtAdjList.isSelected() && rbtKruskal.isSelected()) {
            randomizeGraph(adjacencyListGraph);
            drawGraph(adjacencyListGraph, this.pnGraph);
            kruskalAdjacencyListGraph = (AdjacencyListGraph) kruskal.kruskalAlgorithm(adjacencyListGraph);
            drawGraph(kruskalAdjacencyListGraph, this.pnMST);
        } else if (rbtAdjMatrix.isSelected() && rbtKruskal.isSelected()) {
            randomizeGraph(adjacencyMatrixGraph);
            drawGraph(adjacencyMatrixGraph, this.pnGraph);
            kruskalAdjacencyMatrixGraph = (AdjacencyMatrixGraph) kruskal.kruskalAlgorithm(adjacencyMatrixGraph);
            drawGraph(kruskalAdjacencyMatrixGraph, this.pnMST);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un tipo de grafo y un algoritmo MST para randomizar");
            alert.showAndWait();
        }
    }

    @FXML
    void setOnActionAdjMatrix(ActionEvent event) {
        pnMST.getChildren().clear();
        rbtKruskal.setSelected(false);
        randomizeGraph(adjacencyMatrixGraph);
        drawGraph(adjacencyMatrixGraph, this.pnGraph);
    }

    @FXML
    void onActionAdjList(ActionEvent event) {
        pnMST.getChildren().clear();
        rbtKruskal.setSelected(false);
        randomizeGraph(adjacencyListGraph);
        drawGraph(adjacencyListGraph, this.pnGraph);
    }

    @FXML
    void onActionLinkedList(ActionEvent event) {
        pnMST.getChildren().clear();
        rbtKruskal.setSelected(false);
        randomizeGraph(singlyLinkedListGraph);
        drawGraph(singlyLinkedListGraph, this.pnGraph);
    }

    @FXML
    void onActionKruskal(ActionEvent event) {
        if (rbtAdjMatrix.isSelected() || rbtAdjList.isSelected() || rbtLinkedList.isSelected()){
            if (rbtAdjMatrix.isSelected()){
                kruskalAdjacencyMatrixGraph = (AdjacencyMatrixGraph) kruskal.kruskalAlgorithm(adjacencyMatrixGraph);
                drawGraph(kruskalAdjacencyMatrixGraph, this.pnMST);
            } else if (rbtAdjList.isSelected()) {
                kruskalAdjacencyListGraph = (AdjacencyListGraph) kruskal.kruskalAlgorithm(adjacencyListGraph);
                drawGraph(kruskalAdjacencyListGraph, this.pnMST);
            }else {
                kruskalSinglyLinkedListGraph = (SinglyLinkedListGraph) kruskal.kruskalAlgorithm(singlyLinkedListGraph);
                drawGraph(kruskalSinglyLinkedListGraph, this.pnMST);
            }

            //poner aquí el código para ejecutar el algoritmo y mostrar el grafo
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un tipo de grafo para empezar");
            rbtKruskal.setSelected(false);
            alert.showAndWait();
        }
    }

    @FXML
    void onActionPrim(ActionEvent event) {

        if (rbtAdjMatrix.isSelected() || rbtAdjList.isSelected() || rbtLinkedList.isSelected()){
            //poner aquí el código para ejecutar el algoritmo y mostrar el grafo
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un tipo de grafo para empezar");
            rbtPrim.setSelected(false);
            alert.showAndWait();
        }
    }

    private void randomizeGraph(Graph graph){
        graph.clear();
        try {
            graph.addVertex(Utility.random(1, 99));
            for (int i = 0; i < 10-1; i++) {
                int data = Utility.random(1, 99);
                if (!graph.containsVertex(data)) graph.addVertex(data);
                else i--;
            }
            for (int i = 0; i < 15; i++) {
                addEdge(graph);
            }

        } catch (GraphException | ListException e) {
            e.printStackTrace();
        }

    }

    public int calcularMaxEdges(int nodes){
        int sum = 0;
        int sum1 = nodes-1;
        while (sum1 >= 0){
            sum += sum1--;
        }
        return sum;
    }

    private void addEdge(Graph graph){
        int a = 0;
        int b = 0;
        int vertexA;
        int vertexB;
        try {
            do {
                if (graph instanceof SinglyLinkedListGraph) {
                    a = Utility.random(1, graph.size());
                    b = Utility.random(1, graph.size());
                }else {
                    a = Utility.random(0, graph.size()-1);
                    b = Utility.random(0, graph.size()-1);
                }
                vertexA = (int) graph.getVertexByIndex(a).data;
                vertexB = (int) graph.getVertexByIndex(b).data;
            }while (graph.containsEdge(vertexA, vertexB) || a == b);
            graph.addEdgeAndWeight(vertexA, vertexB, Utility.random(1, 50));
        } catch (ListException | GraphException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
        }
    }

    private void drawGraph(Graph graph, Pane pane){
        pane.getChildren().clear();
        int numNodes = 0;
        double centerY = pane.getPrefHeight()/2;
        double centerX = pane.getPrefWidth()/2;
        double radius = pane.getPrefHeight()/2 - 20;
        Circle[] circles;
        Text[] texts;

        try {
            numNodes = graph.size();
            double angleStep = 2 * Math.PI / numNodes;
            circles = new Circle[numNodes];
            texts = new Text[numNodes];
            for (int i = 0; i < numNodes; i++) {
                double angle = i * angleStep;
                double x = centerX + radius * Math.cos(angle);
                double y = centerY + radius * Math.sin(angle);

                Circle node = new Circle(x, y, 25, Color.valueOf("#027a8e"));
                circles[i] = node;

                Text data = null;
                if (graph instanceof SinglyLinkedListGraph) data = new Text(String.valueOf(graph.getVertexByIndex(i+1).data));
                else data = new Text(String.valueOf(graph.getVertexByIndex(i).data));

                data.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                data.setFill(Color.WHITE);
                double textWidth = data.getLayoutBounds().getWidth();
                double textHeight = data.getLayoutBounds().getHeight();
                data.setX(x - textWidth / 2);
                data.setY(y + textHeight / 4);
                texts[i] = data;

                pane.getChildren().add(node);
            }
        } catch (ListException e) {
            throw new RuntimeException(e);
        }

        drawEdges(circles, graph, pane);
        pane.getChildren().addAll(texts);
    }

    private void drawEdges(Circle[] circles, Graph graph, Pane pane) {

        try {
            int n = graph.size();
            if (!(graph instanceof AdjacencyMatrixGraph)) {
                for (int i = 0; i < n; i++) {
                    Vertex vA = null;
                    if (graph instanceof SinglyLinkedListGraph) vA = graph.getVertexByIndex(i + 1);
                    else vA = graph.getVertexByIndex(i);
                    Circle nodoA = circles[i];
                    if (!vA.edgesList.isEmpty()) {
                        int nEdges = vA.edgesList.size();

                        for (int j = 0; j < nEdges; j++) {
                            EdgeWeight edge = (EdgeWeight) vA.edgesList.getNode(j + 1).data;
                            int vB = (int) edge.getEdge();
                            int b = graph.indexOf(vB);
                            Circle nodoB = null;
                            if (graph instanceof SinglyLinkedListGraph) nodoB = circles[b - 1];
                            else nodoB = circles[b];
                            drawLine(nodoA, nodoB, vA.data, vB, pane);
                        }
                    }

                }
            }else {
                Object[][] matrix = ((AdjacencyMatrixGraph) graph).getAdjacencyMatrix();
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if(!matrix[i][j].equals(0)){
                            Circle nodoA = circles[i];
                            Circle nodoB = circles[j];
                            Vertex vA = graph.getVertexByIndex(i);
                            Vertex vB = graph.getVertexByIndex(j);
                            drawLine(nodoA, nodoB, vA.data, vB.data, pane);
                        }
                    }
                }
            }
        }catch (ListException e){
            e.printStackTrace();
        }
    }

    private void drawLine(Circle nodoA, Circle nodoB, Object vA, Object vB, Pane pane){
        double centerY = pane.getPrefHeight()/2;
        double centerX = pane.getPrefWidth()/2;
        double endX = centerX + (nodoB.getCenterX() - centerX) * 0.9;
        double endY = centerY + (nodoB.getCenterY() - centerY) * 0.9;
        double startX = centerX + (nodoA.getCenterX() - centerX) * 0.9;
        double startY = centerY + (nodoA.getCenterY() - centerY) * 0.9;

        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);

        Tooltip tooltip = new Tooltip("Vertice entre los nodos " + vA + " y " + vB);
        Tooltip.install(line, tooltip);
        tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        line.setOnMouseEntered(event -> {
            tooltip.show(pane, event.getScreenX(), event.getScreenY() + 10);
            line.setStroke(Color.RED);
            line.setStrokeWidth(6);
        });

        line.setOnMouseExited(event -> {
            tooltip.hide();
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(3);
        });
        pane.getChildren().add(line);
    }

}