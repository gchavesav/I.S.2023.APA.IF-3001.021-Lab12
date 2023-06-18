package controller;

import domain.*;
import domain.list.ListException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class DijkstraController {
    @FXML
    private TableView tableView;
    @FXML
    private RadioButton adjacencyMatrixRadiobutton;
    @FXML
    private RadioButton adjencyListRadiobutton;
    @FXML
    private RadioButton linkedListRadiobutton;

    @FXML
    private Pane base;
    private AdjacencyListGraph adjacencyListGraph;
    private AdjacencyMatrixGraph adjacencyMatrixGraph;
    private SinglyLinkedListGraph singlyLinkedGraph = new SinglyLinkedListGraph();
    DijkstraAlgorithm dijkstraAlgorithm;
    private ToggleGroup group;


    @FXML
    public void initialize() {
        dijkstraAlgorithm = new DijkstraAlgorithm();
        adjacencyListGraph = new AdjacencyListGraph(26);
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(26);

        group = new ToggleGroup();
        linkedListRadiobutton.setToggleGroup(group);
        adjencyListRadiobutton.setToggleGroup(group);
        adjacencyMatrixRadiobutton.setToggleGroup(group);

        //llenar la tabla

        TableColumn<List<String>, String> distanceColum = new TableColumn<>("Distancia");
        TableColumn<List<String>, String> vertexColum = new TableColumn<>("Vertice");
        TableColumn<List<String>, String> positionColum = new TableColumn<>("Posicion");

        positionColum.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(0)));
        vertexColum.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(1)));
        distanceColum.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(2)));

        this.tableView.getColumns().add(positionColum);
        this.tableView.getColumns().add(vertexColum);
        this.tableView.getColumns().add(distanceColum);

    }

    private ObservableList<List<String>> getData() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        String position;
        Vertex vertex = null;
        String distance = null;

        for (int i = 0; i < 10; i++) {
            List<String> rowData = new ArrayList<>();

            position = String.valueOf(i);
            //distance = String.valueOf(i+1);


            rowData.add(position);


            if (adjencyListRadiobutton.isSelected()) {
                vertex = adjacencyListGraph.getVertexByIndex(i);
                rowData.add(String.valueOf(vertex.data));
                distance = String.valueOf(Utility.random(200,1000));
            } else if (adjacencyMatrixRadiobutton.isSelected()) {
                vertex = adjacencyMatrixGraph.getVertexByIndex(i);
                rowData.add(String.valueOf(vertex.data));
                distance =String.valueOf(Utility.random(200,1000));
            } else {
                vertex = singlyLinkedGraph.getVertexByIndex(i+1);
                rowData.add(String.valueOf(vertex.data));
                distance = String.valueOf(Utility.random(200,1000));
            }
            rowData.add(distance);

            data.add(rowData);
        }

        return data;
    }


    private void randomizeGraph(Graph graph) {
        graph.clear();

        try {
            graph.addVertex(Utility.random(1, 99));
            for (int i = 0; i < 10 - 1; i++) {
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

    public int calcularMaxEdges(int nodes) {
        int sum = 0;
        int sum1 = nodes - 1;
        while (sum1 >= 0) {
            sum += sum1--;
        }
        return sum;
    }

    private void addEdge(Graph graph) {
        int a = 0;
        int b = 0;
        int vertexA;
        int vertexB;
        try {
            do {
                if (graph instanceof SinglyLinkedListGraph) {
                    a = util.Utility.random(1, graph.size());
                    b = util.Utility.random(1, graph.size());

                } else {
                    a = util.Utility.random(0, graph.size() - 1);
                    b = util.Utility.random(0, graph.size() - 1);
                }
                vertexA = (int) graph.getVertexByIndex(a).data;
                vertexB = (int) graph.getVertexByIndex(b).data;

            } while (graph.containsEdge(vertexA, vertexB) || a == b);
            graph.addEdgeAndWeight(vertexA, vertexB, util.Utility.random(1, 50));
        } catch (GraphException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void randomizeOnAction(ActionEvent event) {
        if (adjencyListRadiobutton.isSelected()) {
            randomizeGraph(adjacencyListGraph);
            drawGraph(adjacencyListGraph, this.base);
        } else if (adjacencyMatrixRadiobutton.isSelected()) {
            randomizeGraph(adjacencyMatrixGraph);
            drawGraph(adjacencyMatrixGraph, this.base);
        } else if (linkedListRadiobutton.isSelected()) {
            randomizeGraph(singlyLinkedGraph);
            drawGraph(singlyLinkedGraph, this.base);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un tipo de grafo y un algoritmo MST para randomizar");
            alert.showAndWait();
        }
        tableView.setItems(getData());

    }

    private void drawGraph(Graph graph, Pane pane) {
        base.getChildren().clear();
        int numNodes = 0;
        double centerY = base.getPrefHeight() / 2;
        double centerX = base.getPrefWidth() / 2;
        double radius = base.getPrefHeight() / 2 - 20;
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
                if (graph instanceof SinglyLinkedListGraph)
                    data = new Text(String.valueOf(graph.getVertexByIndex(i + 1).data));
                else data = new Text(String.valueOf(graph.getVertexByIndex(i).data));

                data.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                data.setFill(Color.WHITE);
                double textWidth = data.getLayoutBounds().getWidth();
                double textHeight = data.getLayoutBounds().getHeight();
                data.setX(x - textWidth / 2);
                data.setY(y + textHeight / 4);
                texts[i] = data;

                base.getChildren().add(node);
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
            } else {
                Object[][] matrix = ((AdjacencyMatrixGraph) graph).getAdjacencyMatrix();
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (!matrix[i][j].equals(0)) {
                            Circle nodoA = circles[i];
                            Circle nodoB = circles[j];
                            Vertex vA = graph.getVertexByIndex(i);
                            Vertex vB = graph.getVertexByIndex(j);
                            drawLine(nodoA, nodoB, vA.data, vB.data, pane);
                        }
                    }
                }
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
    }

    private void drawLine(Circle nodoA, Circle nodoB, Object vA, Object vB, Pane pane) {
        double centerY = pane.getPrefHeight() / 2;
        double centerX = pane.getPrefWidth() / 2;
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
