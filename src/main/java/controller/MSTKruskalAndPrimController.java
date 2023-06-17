package controller;

import domain.*;
import domain.list.ListException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
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
import ucr.lab.Kruskal;
import util.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private ToggleGroup group;
    Kruskal kruskal ;

    @FXML
    void initialize(){
        kruskal= new Kruskal();
        singlyLinkedListGraph = new SinglyLinkedListGraph();
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(26);
        adjacencyListGraph = new AdjacencyListGraph(26);
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
        if (rbtLinkedList.isSelected()){
            randomizeGraph(singlyLinkedListGraph);
            drawGraph(singlyLinkedListGraph);
        } else if (rbtAdjList.isSelected()) {
            randomizeGraph(adjacencyListGraph);
            drawGraph(adjacencyListGraph);
        } else if (rbtAdjMatrix.isSelected()) {
            randomizeGraph(adjacencyMatrixGraph);
            drawGraph(adjacencyMatrixGraph);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un tipo de grafo para empezar");
            alert.showAndWait();
        }
    }

    @FXML
    void setOnActionAdjMatrix(ActionEvent event) {
        randomizeGraph(adjacencyMatrixGraph);
        drawGraph(adjacencyMatrixGraph);
    }

    @FXML
    void onActionAdjList(ActionEvent event) {
        randomizeGraph(adjacencyListGraph);
        drawGraph(adjacencyListGraph);
    }

    @FXML
    void onActionLinkedList(ActionEvent event) {
        randomizeGraph(singlyLinkedListGraph);
        drawGraph(singlyLinkedListGraph);
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

    @FXML
    void onActionKruskal(ActionEvent event) throws GraphException {
        if (rbtAdjMatrix.isSelected() || rbtAdjList.isSelected() || rbtLinkedList.isSelected()){
            if (rbtAdjMatrix.isSelected()){
              // kruskal.init(adjacencyMatrixGraph);
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

    private void drawGraph(Graph graph){
        pnGraph.getChildren().clear();
        int numNodes = 0;
        double centerY = pnGraph.getPrefHeight()/2;
        double centerX = pnGraph.getPrefWidth()/2;
        double radius = pnGraph.getPrefHeight()/2 - 20;
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

                pnGraph.getChildren().add(node);
            }
        } catch (ListException e) {
            throw new RuntimeException(e);
        }

        drawEdges(circles, graph);
        pnGraph.getChildren().addAll(texts);
    }

    private void drawEdges(Circle[] circles, Graph graph) {

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
                            drawLine(nodoA, nodoB, vA.data, vB);
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
                            drawLine(nodoA, nodoB, vA.data, vB.data);
                        }
                    }
                }
            }
        }catch (ListException e){
            e.printStackTrace();
        }
    }

    private void drawLine(Circle nodoA, Circle nodoB, Object vA, Object vB){
        double centerY = pnGraph.getPrefHeight()/2;
        double centerX = pnGraph.getPrefWidth()/2;
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
            tooltip.show(pnGraph, event.getScreenX(), event.getScreenY() + 10);
            line.setStroke(Color.RED);
            line.setStrokeWidth(6);
        });

        line.setOnMouseExited(event -> {
            tooltip.hide();
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(3);
        });
        this.pnGraph.getChildren().add(line);
    }

}
