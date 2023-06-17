package controller;

import domain.DirectedSinglyLinkedListGraph;
import domain.GraphException;
import domain.Vertex;
import domain.list.ListException;
import domain.list.SinglyLinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraphOperationsController {

    @FXML
    private Pane base;

    @FXML
    private TextArea dataTextArea;

    private Alert alert;
    DirectedSinglyLinkedListGraph directedSingly;
    SinglyLinkedList listOfEdges;

    @FXML
    public void initialize() throws ListException {
        directedSingly = new DirectedSinglyLinkedListGraph();
        listOfEdges = new SinglyLinkedList();
        randomizeGraph();
        drawGraph();
    }

    @FXML
    void addENWOnAction(ActionEvent event) {    //hacer
        try {
            addEdge();
            drawGraph();
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
        dataTextArea.setText(directedSingly.toString());
    }

    @FXML
    void addVertexOnAction(ActionEvent event) {    //hacer
        try {
            directedSingly.addVertex(util.Utility.getCapital());
            drawGraph();
        } catch (GraphException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
        dataTextArea.setText(directedSingly.toString());
    }

    @FXML
    void clearOnAction(ActionEvent event) {

    }

    @FXML
    void randomizeOnAction(ActionEvent event) {   //hacer
        try {
            randomizeGraph();
            drawGraph();
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void removeENWOnAction(ActionEvent event) {

    }

    @FXML
    void removeVertexOnAction(ActionEvent event) {

    }

    private void randomizeGraph(){
        listOfEdges.clear();
        directedSingly.clear();
        try {
            directedSingly.addVertex(util.Utility.getCapital());
            for (int i = 1; i < 10; i++) {
                String capital = util.Utility.getCapital(); //para eviar error isEmpty
                if (!directedSingly.containsVertex(capital)) directedSingly.addVertex(capital); //para que no se repitan
                else i--;
            }
            for (int i = 0; i < 5; i++) {
                addEdge(); //agrega aristas
            }

        } catch (GraphException | ListException e) {
            e.printStackTrace();
        }

        dataTextArea.setText(directedSingly.toString());
    }

    public int calcularMaxEdges(int nodes){
        int sum = 0;
        int sum1 = nodes-1;
        while (sum1 >= 0){
            sum += sum1--;
        }
        return sum;
    }
    private void addEdge(){
        int a = 0;
        int b = 0;
        String vertexA;
        String vertexB;
        String dataList;
        try {
            int n = directedSingly.size();
            if (!listOfEdges.isEmpty()){
                if (listOfEdges.size() >= calcularMaxEdges(directedSingly.size())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("No se pueden agregar más Aristas");
                    alert.showAndWait();
                    return;
                }
                do {
                    a = util.Utility.random(1, n);
                    b = util.Utility.random(1, n);
                    vertexA = (String) directedSingly.getVertexByIndex(a).data;
                    vertexB = (String) directedSingly.getVertexByIndex(b).data;
                    dataList = vertexA +"-"+ vertexB;
                }while (directedSingly.containsEdge(vertexA, vertexB) || listOfEdges.contains(dataList));
                directedSingly.addEdgeAndWeight(vertexA, vertexB, util.Utility.random(200, 1000));
                listOfEdges.add(dataList);
            }else{ //si esta vacia solo se le agrega a la lista
                do {
                    a = util.Utility.random(1, n);
                    b = util.Utility.random(1, n);
                    vertexA = (String) directedSingly.getVertexByIndex(a).data;
                    vertexB = (String) directedSingly.getVertexByIndex(b).data;
                    dataList = vertexA +"-"+ vertexB;
                }while (directedSingly.containsEdge(vertexA, vertexB));
                directedSingly.addEdgeAndWeight(vertexA, vertexB, util.Utility.random(200, 1000));
                listOfEdges.add(dataList);
            }

        } catch (ListException | GraphException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
        }
    }

    private void drawGraph() throws ListException {
        base.getChildren().clear();
        int numNodes = 0;
        double centerY = base.getPrefHeight()/2;
        double centerX = base.getPrefWidth()/2;
        double radius = base.getPrefHeight()/2 - 20;
        Circle[] circles;
        Text[] texts;

        try {
            numNodes = directedSingly.size();
            double angleStep = 2 * Math.PI / numNodes;
            circles = new Circle[numNodes];
            texts = new Text[numNodes];
            for (int i = 1; i <= numNodes; i++) {
                double angle = i * angleStep;
                double x = centerX + radius * Math.cos(angle);
                double y = centerY + radius * Math.sin(angle);

                Circle node = new Circle(x, y, 25, Color.valueOf("#027a8e"));
                circles[i-1] = node;

                Text data = new Text(String.valueOf(directedSingly.getVertexByIndex(i).data));

                data.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                data.setFill(Color.WHITE);
                double textWidth = data.getLayoutBounds().getWidth();
                double textHeight = data.getLayoutBounds().getHeight();
                data.setX(x - textWidth / 2);
                data.setY(y + textHeight / 4);
                texts[i-1] = data;

                base.getChildren().addAll(node, data);
            }
        } catch (ListException e) {
            throw new RuntimeException(e);
        }

        try {
            drawEdges(circles);
        } catch (GraphException e) {
            throw new RuntimeException(e);
        }
        //base.getChildren().addAll(texts);
    }
    private void drawEdges(Circle[] circles) throws ListException, GraphException {
        int n = listOfEdges.size();
        double centerY = base.getPrefHeight()/2;
        double centerX = base.getPrefWidth()/2;

        for (int i = 1; i < n; i++) {
            String partir = (String) listOfEdges.getNode(i).data;
            String[] parts = partir.split("-"); //separa el strin por el "-"
            String vA = parts[0];
            String vB = parts[1];
            int a = directedSingly.indexOf(vA);
            int b = directedSingly.indexOf(vB);

            Circle nodoA = circles[a-1];
            Circle nodoB = circles[b-1];

            double endX = centerX + (nodoB.getCenterX() - centerX) * 0.9;
            double endY = centerY + (nodoB.getCenterY() - centerY) * 0.9;
            double startX = centerX + (nodoA.getCenterX() - centerX) * 0.9;
            double startY = centerY + (nodoA.getCenterY() - centerY) * 0.9;

            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(3);

            drawFlechas(startX, startY, endX, endY);

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
            //base.getChildren().addAll(line, flecha);
            this.base.getChildren().add(line);
        }
    }

    private Canvas canvas;
    private void drawFlechas(double startX, double startY, double endX, double endY) {
        // Calcula las coordenadas para dibujar la flecha
        double arrowLength = 10;
        double arrowWidth = 5;
        double arrowHeadSize = 5;

        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2.0 * sin) * arrowLength + endX;
        double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2.0 * cos) * arrowLength + endY;
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2.0 * sin) * arrowLength + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2.0 * cos) * arrowLength + endY;

        // Dibuja la flecha
        Polygon flecha = new Polygon();
        flecha.getPoints().addAll(
                endX,endY,
                x1,y1
                ,x2, y2
        );

        this.base.getChildren().add(flecha);
    }


}
