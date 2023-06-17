package controller;

import domain.*;
import domain.list.ListException;
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
import java.util.ArrayList;
import java.util.List;

public class DijkstraController {
    @FXML
    private TableColumn<String, String> DistanceColum;

    @FXML
    private RadioButton adjacencyMatrixRadiobutton;
    @FXML
    private RadioButton adjencyListRadiobutton;
    @FXML
    private RadioButton linkedListRadiobutton;
    @FXML
    private TableColumn<String, String> positionColum;
    @FXML
    private TableColumn<String, String> vertexColum;
    @FXML
    private Pane base;
    AdjacencyListGraph adjacencyListGraph;
    AdjacencyMatrixGraph adjacencyMatrixGraph;
    private SinglyLinkedListGraph linkedGraph = new SinglyLinkedListGraph();
    private List<String> listOfEdges = new ArrayList<>();


    @FXML
    public void initialize() throws ListException {
        ToggleGroup toggleGroup = new ToggleGroup();

        linkedListRadiobutton.setToggleGroup(toggleGroup);
        adjencyListRadiobutton.setToggleGroup(toggleGroup);
        adjacencyMatrixRadiobutton.setToggleGroup(toggleGroup);

//        adjencyListRadiobutton.setSelected(true);
//        adjacencyListGraph = new AdjacencyListGraph(26);
//        randomizeGraph();
//        drawGraph();

        linkedListRadiobutton.setOnAction(event -> {
            adjencyListRadiobutton.setSelected(false);
            adjacencyMatrixRadiobutton.setSelected(false);
            linkedGraph = new SinglyLinkedListGraph();
            try {
                randomizeGraph();
                drawGraph();
            } catch (ListException e) {
                throw new RuntimeException(e);
            }


        });

        adjencyListRadiobutton.setOnAction(event -> {
            linkedListRadiobutton.setSelected(false);
            adjacencyMatrixRadiobutton.setSelected(false);
            adjacencyListGraph = new AdjacencyListGraph(26);
            try {
            randomizeGraph();
                drawGraph();
            } catch (ListException e) {
                throw new RuntimeException(e);
            }

        });

        adjacencyMatrixRadiobutton.setOnAction(event -> {
            linkedListRadiobutton.setSelected(false);
            adjencyListRadiobutton.setSelected(false);
            adjacencyMatrixGraph = new AdjacencyMatrixGraph(26);
            randomizeGraph();
            try {
                drawGraph();
            } catch (ListException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void randomizeGraph() {
        if (adjencyListRadiobutton.isSelected()) {
            adjacencyListGraph.clear();
            listOfEdges.clear();
            try {
                adjacencyListGraph.addVertex(util.Utility.random(10));
                for (int i = 0; i < 10 - 1; i++) {
                    int number = util.Utility.random(10);
                    if (!adjacencyListGraph.containsVertex(number)) {
                        adjacencyListGraph.addVertex(number);
                    } else {
                        i--;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    addEdge();
                }
            } catch (GraphException e) {
                e.printStackTrace();
                System.out.println("6");
            }
        } else if (adjacencyMatrixRadiobutton.isSelected()) {
            adjacencyMatrixGraph.clear();
            listOfEdges.clear();
            try {
                adjacencyMatrixGraph.addVertex(util.Utility.random(10));
                for (int i = 0; i < 10 - 1; i++) {
                    int number = util.Utility.random(10);
                    if (!adjacencyMatrixGraph.containsVertex(number)) {
                        adjacencyMatrixGraph.addVertex(number);
                    } else {
                        i--;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    addEdge();
                }
            } catch (GraphException e) {
                e.printStackTrace();
                System.out.println("6");
            }

        } else if (linkedListRadiobutton.isSelected()) {
            linkedGraph.clear();
            listOfEdges.clear();
            try {
                linkedGraph.addVertex(util.Utility.random(10));
                for (int i = 0; i < 10 - 1; i++) {
                    int number = util.Utility.random(10);
                    if (!linkedGraph.containsVertex(number)) {
                        linkedGraph.addVertex(number);
                    } else {
                        i--;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    addEdge();
                }
            } catch (ListException | GraphException e) {
                e.printStackTrace();
                System.out.println("5");
            }
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

    private void addEdge() {
        int a = 0;
        int b = 0;
        int vertexA;
        int vertexB;
        int dataList;
        int dataListInverted;
        if (adjencyListRadiobutton.isSelected()) {
            try {
                if (adjacencyListGraph.size() >= calcularMaxEdges(adjacencyListGraph.size())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("No se pueden agregar más Aristas");
                    alert.showAndWait();
                    return;
                }
                do {
                    a = util.Utility.random(0, adjacencyListGraph.size());
                    b = util.Utility.random(0, adjacencyListGraph.size());
                    vertexA = (int) (adjacencyListGraph.getVertexByIndex(a).data);
                    vertexB = (int) (adjacencyListGraph.getVertexByIndex(b).data);
                    dataList = vertexA + vertexB;
                    dataListInverted = dataList + dataList;
                } while (adjacencyListGraph.containsEdge(vertexA, vertexB) || a == b || listOfEdges.contains(dataList) || listOfEdges.contains(dataListInverted));
                adjacencyListGraph.addEdgeAndWeight(vertexA, vertexB, util.Utility.random(200, 1000));
                listOfEdges.add(String.valueOf(dataList));
            } catch (ListException | GraphException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(String.valueOf(e));
                alert.showAndWait();
                System.out.println("4");
            }
        } else if (adjacencyMatrixRadiobutton.isSelected()) {
            try {
                if (adjacencyMatrixGraph.size() >= calcularMaxEdges(adjacencyMatrixGraph.size())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("No se pueden agregar más Aristas");
                    alert.showAndWait();
                    return;
                }
                do {
                    a = util.Utility.random(0, adjacencyMatrixGraph.size());
                    b = util.Utility.random(0, adjacencyMatrixGraph.size());
                    vertexA = (int) (adjacencyMatrixGraph.getVertexByIndex(a).data);
                    vertexB = (int) (adjacencyMatrixGraph.getVertexByIndex(b).data);
                    dataList = vertexA + vertexB;
                    dataListInverted = dataList + dataList;
                } while (adjacencyMatrixGraph.containsEdge(vertexA, vertexB) || a == b || listOfEdges.contains(dataList) || listOfEdges.contains(dataListInverted));
                adjacencyMatrixGraph.addEdgeAndWeight(vertexA, vertexB, util.Utility.random(200, 1000));
                listOfEdges.add(String.valueOf(dataList));
            } catch (GraphException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(String.valueOf(e));
                alert.showAndWait();
                System.out.println("4");
            }
        } else if (linkedListRadiobutton.isSelected()) {
            try {
                if (listOfEdges.size() >= calcularMaxEdges(linkedGraph.size())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("No se pueden agregar más Aristas");
                    alert.showAndWait();
                    return;
                }
                do {
                    a = util.Utility.random(1, linkedGraph.size());
                    b = util.Utility.random(1, linkedGraph.size());
                    vertexA = (int) (linkedGraph.getVertexByIndex(a).data);
                    vertexB = (int) (linkedGraph.getVertexByIndex(b).data);
                    dataList = vertexA + vertexB;
                    dataListInverted = dataList + dataList;
                } while (linkedGraph.containsEdge(vertexA, vertexB) || a == b || listOfEdges.contains(dataList) || listOfEdges.contains(dataListInverted));
                linkedGraph.addEdgeAndWeight(vertexA, vertexB, util.Utility.random(200, 1000));
                listOfEdges.add(String.valueOf(dataList));
            } catch (ListException | GraphException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(String.valueOf(e));
                alert.showAndWait();
                System.out.println("3");
            }
        }
    }


        @FXML
        void randomizeOnAction (ActionEvent event) throws ListException {
            randomizeGraph();
            drawGraph();
        }

        private void drawGraph () throws ListException {
            if (adjencyListRadiobutton.isSelected()) {
                base.getChildren().clear();
                int numNodes = 0;
                numNodes = adjacencyListGraph.size();

                double centerY = base.getPrefHeight() / 2;
                double centerX = base.getPrefWidth() / 2;
                double radius = base.getPrefHeight() / 2 - 20;
                Circle[] circles = new Circle[numNodes];
                Text[] texts = new Text[numNodes];

                double angleStep = 2 * Math.PI / numNodes;
                for (int i = 0; i < numNodes; i++) {
                    double angle = i * angleStep;
                    double x = centerX + radius * Math.cos(angle);
                    double y = centerY + radius * Math.sin(angle);

                    Circle node = new Circle(x, y, 25, Color.valueOf("#027a8e"));
                    circles[i] = node;

                    Text text = new Text(String.valueOf(adjacencyListGraph.getVertexByIndex(i).data));
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    text.setFill(Color.BLACK);
                    double textWidth = text.getLayoutBounds().getWidth();
                    double textHeight = text.getLayoutBounds().getHeight();
                    text.setX(x - textWidth / 2);
                    text.setY(y + textHeight / 4);
                    texts[i] = text;

                    base.getChildren().add(node);
                }
                drawEdges(circles);
                base.getChildren().addAll(texts);

            } else if (adjacencyMatrixRadiobutton.isSelected()) {
                base.getChildren().clear();
                int numNodes = 0;
                numNodes = adjacencyMatrixGraph.size();

                double centerY = base.getPrefHeight() / 2;
                double centerX = base.getPrefWidth() / 2;
                double radius = base.getPrefHeight() / 2 - 20;
                Circle[] circles = new Circle[numNodes];
                Text[] texts = new Text[numNodes];

                double angleStep = 2 * Math.PI / numNodes;
                for (int i = 0; i < numNodes; i++) {
                    double angle = i * angleStep;
                    double x = centerX + radius * Math.cos(angle);
                    double y = centerY + radius * Math.sin(angle);

                    Circle node = new Circle(x, y, 25, Color.valueOf("#027a8e"));
                    circles[i] = node;

                    Text text = new Text(String.valueOf(adjacencyMatrixGraph.getVertexByIndex(i).data));
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    text.setFill(Color.BLACK);
                    double textWidth = text.getLayoutBounds().getWidth();
                    double textHeight = text.getLayoutBounds().getHeight();
                    text.setX(x - textWidth / 2);
                    text.setY(y + textHeight / 4);
                    texts[i] = text;

                    base.getChildren().add(node);
                }
                drawEdges(circles);
                base.getChildren().addAll(texts);

            } else if (linkedListRadiobutton.isSelected()) {
                base.getChildren().clear();
                int numNodes = 0;
                try {
                    numNodes = linkedGraph.size();
                } catch (ListException e) {
                    throw new RuntimeException(e);
                }
                double centerY = base.getPrefHeight() / 2;
                double centerX = base.getPrefWidth() / 2;
                double radius = base.getPrefHeight() / 2 - 20;
                Circle[] circles = new Circle[numNodes];
                Text[] texts = new Text[numNodes];

                double angleStep = 2 * Math.PI / numNodes;
                for (int i = 0; i < numNodes; i++) {
                    double angle = i * angleStep;
                    double x = centerX + radius * Math.cos(angle);
                    double y = centerY + radius * Math.sin(angle);

                    Circle node = new Circle(x, y, 25, Color.valueOf("#027a8e"));
                    circles[i] = node;

                    Text text = new Text(String.valueOf(linkedGraph.getVertexByIndex(i + 1).data));
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    text.setFill(Color.BLACK);
                    double textWidth = text.getLayoutBounds().getWidth();
                    double textHeight = text.getLayoutBounds().getHeight();
                    text.setX(x - textWidth / 2);
                    text.setY(y + textHeight / 4);
                    texts[i] = text;

                    base.getChildren().add(node);
                }
                drawEdges(circles);
                base.getChildren().addAll(texts);
            }
        }

        private void drawEdges(Circle[] circles) {
            if (adjencyListRadiobutton.isSelected()) {
                double centerY = base.getPrefHeight() / 2;
                double centerX = base.getPrefWidth() / 2;
                try {
                    int n = adjacencyListGraph.size();
                    for (int i = 0; i < n; i++) {
                        Vertex vA = adjacencyListGraph.getVertexByIndex(i);
                        Circle nodoA = circles[i];
                        if (!vA.edgesList.isEmpty()) {
                            int nEdges = vA.edgesList.size();
                            for (int j = 0; j < nEdges; j++) {
                                EdgeWeight edge = (EdgeWeight) vA.edgesList.getNode(j+1).data;
                                int vB = (int) edge.getEdge();
                                int b = adjacencyListGraph.indexOf(vB);
                                Circle nodoB = circles[b];
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
                                    tooltip.show(base, event.getScreenX(), event.getScreenY() + 10);
                                    line.setStroke(Color.RED);
                                    line.setStrokeWidth(6);
                                });

                                line.setOnMouseExited(event -> {
                                    tooltip.hide();
                                    line.setStroke(Color.BLACK);
                                    line.setStrokeWidth(3);
                                });
                                this.base.getChildren().add(line);
                            }
                        }

                    }
                } catch (ListException e) {
                    e.printStackTrace();
                    System.out.println("1");
                }
            } else if (adjacencyMatrixRadiobutton.isSelected()) {
                double centerY = base.getPrefHeight() / 2;
                double centerX = base.getPrefWidth() / 2;
                try {
                    int n = adjacencyMatrixGraph.size();
                    for (int i = 0; i < n; i++) {
                        Vertex vA = adjacencyMatrixGraph.getVertexByIndex(i);
                        Circle nodoA = circles[i];
                        if (!vA.edgesList.isEmpty()) {
                            int nEdges = vA.edgesList.size();
                            for (int j = 0; j < nEdges; j++) {
                                EdgeWeight edge = (EdgeWeight) vA.edgesList.getNode(j).data;
                                int vB = (int) edge.getEdge();
                                int b = adjacencyMatrixGraph.indexOf(vB);
                                Circle nodoB = circles[b];
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
                                    tooltip.show(base, event.getScreenX(), event.getScreenY() + 10);
                                    line.setStroke(Color.RED);
                                    line.setStrokeWidth(6);
                                });

                                line.setOnMouseExited(event -> {
                                    tooltip.hide();
                                    line.setStroke(Color.BLACK);
                                    line.setStrokeWidth(3);
                                });
                                this.base.getChildren().add(line);
                            }
                        }

                    }
                } catch (ListException e) {
                    e.printStackTrace();
                    System.out.println("1");
                }

            } else if (linkedListRadiobutton.isSelected()) {

                double centerY = base.getPrefHeight() / 2;
                double centerX = base.getPrefWidth() / 2;
                try {
            int n = linkedGraph.size();
            for (int i = 0; i < n; i++) {
                Vertex vA = linkedGraph.getVertexByIndex(i + 1);
                Circle nodoA = circles[i];
                if (!vA.edgesList.isEmpty()) {
                    int nEdges = vA.edgesList.size();
                    for (int j = 0; j < nEdges; j++) {
                        EdgeWeight edge = (EdgeWeight) vA.edgesList.getNode(j + 1).data;
                        int vB = (int) edge.getEdge();
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

                        line.setOnMouseEntered(event -> {
                            tooltip.show(base, event.getScreenX(), event.getScreenY() + 10);
                            line.setStroke(Color.RED);
                            line.setStrokeWidth(6);
                        });

                        line.setOnMouseExited(event -> {
                            tooltip.hide();
                            line.setStroke(Color.BLACK);
                            line.setStrokeWidth(3);
                        });
                        this.base.getChildren().add(line);
                    }
                }

            }
        } catch (ListException e) {
            e.printStackTrace();
                    System.out.println("2");
                }
            }
    }

}
