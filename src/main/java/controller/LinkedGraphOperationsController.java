package controller;

import domain.EdgeWeight;
import domain.GraphException;
import domain.SinglyLinkedListGraph;
import domain.Vertex;
import domain.list.ListException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import util.Utility;

import java.util.ArrayList;
import java.util.List;

public class LinkedGraphOperationsController {
    @FXML
    private TextArea data;
    @FXML
    private Pane grafo;
    private SinglyLinkedListGraph linkedGraph = new SinglyLinkedListGraph();
    private List<String> listOfEdges = new ArrayList<>();

    @FXML
    public void initialize() {
        data.setEditable(false);
        randomizeGraph();
        drawGraph();
    }

    //Pablo Madrigal Coto 5c (Randomize)
    @FXML
    void randomizeOnAction(ActionEvent event) {
        randomizeGraph();
        drawGraph();
    }

    //Pablo Madrigal Coto 5c (addVertex)
    @FXML
    void addVertexOnAction(ActionEvent event) {
        addVertex();
        drawGraph();
    }

    //Pablo Madrigal Coto 5c (addEdgesWeights)
    @FXML
    void addEdgesWeightsOnAction(ActionEvent event) {
        addEdgeWeight();
        drawGraph();
        data.setText(linkedGraph.toString());
    }

    @FXML
    void clearOnAction(ActionEvent event) {
    }

    @FXML
    void removeEdgesWeightsOnAction(ActionEvent event) {
    }

    @FXML
    void removeVertexOnAction(ActionEvent event) {
    }

    // Método para dibujar el gráfo
    private void drawGraph() {
        grafo.getChildren().clear();
        int numNodes = 0;
        try {
            numNodes = linkedGraph.size();
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
        double centerY = grafo.getPrefHeight() / 2;
        double centerX = grafo.getPrefWidth() / 2;
        double radius = grafo.getPrefHeight() / 2 - 20;
        Circle[] circles = new Circle[numNodes];
        Text[] texts = new Text[numNodes];

        double angleStep = 2 * Math.PI / numNodes;
        for (int i = 0; i < numNodes; i++) {
            double angle = i * angleStep;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle node = new Circle(x, y, 25, Color.valueOf("#027a8e"));
            circles[i] = node;

            String data = (String) linkedGraph.getVertexByIndex(i + 1).data;
            Text text = new Text(data);
            text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            text.setFill(Color.BLACK);
            double textWidth = text.getLayoutBounds().getWidth();
            double textHeight = text.getLayoutBounds().getHeight();
            text.setX(x - textWidth / 2);
            text.setY(y + textHeight / 4);
            texts[i] = text;

            grafo.getChildren().add(node);
        }

        drawEdges(circles);
        grafo.getChildren().addAll(texts);
    }

    //Método para dibujar las Áristas
    private void drawEdges(Circle[] circles) {
        double centerY = grafo.getPrefHeight() / 2;
        double centerX = grafo.getPrefWidth() / 2;
        try {
            int n = linkedGraph.size();
            for (int i = 0; i < n; i++) {
                Vertex vA = linkedGraph.getVertexByIndex(i + 1);
                Circle nodoA = circles[i];
                if (!vA.edgesList.isEmpty()) {
                    int nEdges = vA.edgesList.size();

                    for (int j = 0; j < nEdges; j++) {
                        EdgeWeight edge = (EdgeWeight) vA.edgesList.getNode(j + 1).data;
                        String vB = (String) edge.getEdge();
                        int b = linkedGraph.indexOf(vB);
                        Circle nodoB = circles[b - 1];
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

                        this.grafo.getChildren().add(line);
                    }
                }

            }
        } catch (ListException e) {
            new RuntimeException(e);
        }
    }

    //Randomiza el Gráfo
    private void randomizeGraph() {
        linkedGraph.clear();
        listOfEdges.clear();
        try {
            linkedGraph.addVertex(Utility.getCountry());
            for (int i = 0; i < 10 - 1; i++) {
                String country = Utility.getCountry();
                if (!linkedGraph.containsVertex(country)) {
                    linkedGraph.addVertex(country);
                } else {
                    i--;
                }
            }
            for (int i = 0; i < 5; i++) {
                addEdgeWeight();
            }
        } catch (ListException | GraphException e) {
            new RuntimeException(e);
        }

        data.setText(linkedGraph.toString());
    }

    //Método para agregar las áristas y los pesos entre 200 y 1000
    private void addEdgeWeight() {
        int a = 0;
        int b = 0;
        String vertexA;
        String vertexB;
        String dataList;
        String dataListInverted;
        try {
            if (listOfEdges.size() >= calcularMaxEdges(linkedGraph.size())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No se permite agregar Aristas");
                alert.showAndWait();
                return;
            }
            do {
                a = Utility.random(1, linkedGraph.size());
                b = Utility.random(1, linkedGraph.size());
                vertexA = (String) (linkedGraph.getVertexByIndex(a).data);
                vertexB = (String) (linkedGraph.getVertexByIndex(b).data);
                dataList = vertexA + vertexB;
                dataListInverted = dataList.charAt(1) + Character.toString(dataList.charAt(0));
            } while (linkedGraph.containsEdge(vertexA, vertexB) || a == b || listOfEdges.contains(dataList) || listOfEdges.contains(dataListInverted));
            linkedGraph.addEdgeAndWeight(vertexA, vertexB, Utility.random(200, 1000));
            listOfEdges.add(dataList);
        } catch (ListException | GraphException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
        }
    }

    //Método para agregar vértices nuevos y que se dibujen en el gráfo
    private void addVertex() {
        String country = Utility.getCountry();
        try {
            if (linkedGraph.isEmpty()) {
                linkedGraph.addVertex(country);
            } else if (linkedGraph.size() >= 30) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No se permite agregar más vértices");
                alert.showAndWait();
                return;
            } else if (!linkedGraph.isEmpty()) {
                while (linkedGraph.containsVertex(country)) {
                    country = Utility.getCountry();
                }
                linkedGraph.addVertex(country);
            }
        } catch (GraphException | ListException e) {
            new RuntimeException(e);
        }
        data.setText(linkedGraph.toString());
    }

    //Este método calcula la cantidad máxima de áristas que se permiten en el gráfo
    private int calcularMaxEdges(int nodes) {
        int sum = 0;
        int num = nodes - 1;
        for (int i = 1; i <= num; i++) {
            sum += i;
        }
        return sum;
    }
}
