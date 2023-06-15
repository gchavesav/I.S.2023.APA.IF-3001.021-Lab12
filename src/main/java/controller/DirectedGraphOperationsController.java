package controller;

import domain.DirectedSinglyLinkedListGraph;
import domain.GraphException;
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

import java.util.ArrayList;
import java.util.List;

public class DirectedGraphOperationsController {

    @FXML
    private Pane base;

    @FXML
    private TextArea dataTextArea;

    private Alert alert;
    DirectedSinglyLinkedListGraph directedSingly;
    List<String> listOfEdges = new ArrayList<>();

    @FXML
    public void initialize() throws ListException {
        directedSingly = new DirectedSinglyLinkedListGraph();
        randomizeGraph();
        //drawGraph();
    }

    @FXML
    void addENWOnAction(ActionEvent event) {

    }

    @FXML
    void addVertexOnAction(ActionEvent event) {

    }

    @FXML
    void clearOnAction(ActionEvent event) {

    }

    @FXML
    void randomizeOnAction(ActionEvent event) {

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
            directedSingly.addVertex(util.Utility.getAlphabet());
            for (int i = 0; i < 10-1; i++) {
                String capital = util.Utility.getCapital();
                if (!directedSingly.containsVertex(capital)) directedSingly.addVertex(capital);
                else i--;
            }
            for (int i = 0; i < 5; i++) {
                addEdge();
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
        String dataListInverted;
        try {
            if (listOfEdges.size() >= calcularMaxEdges(directedSingly.size())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("No se pueden agregar más Aristas");
                alert.showAndWait();
                return;
            }
            do {
                a = util.Utility.random(0, directedSingly.size());
                b = util.Utility.random(0, directedSingly.size());
                vertexA = (String) directedSingly.getVertex(a).data;
                vertexB = (String) directedSingly.getVertex(b).data;
                dataList = vertexA +"-"+ vertexB;
                dataListInverted = vertexB +"-"+ vertexA;
            }while (directedSingly.containsEdge(vertexA, vertexB) || a == b || listOfEdges.contains(dataList) || listOfEdges.contains(dataListInverted));
            directedSingly.addEdgeAndWeight(vertexA, vertexB, util.Utility.random(200, 1000));
            listOfEdges.add(dataList);
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
            for (int i = 0; i < numNodes; i++) {
                double angle = i * angleStep;
                double x = centerX + radius * Math.cos(angle);
                double y = centerY + radius * Math.sin(angle);

                Circle node = new Circle(x, y, 25, Color.valueOf("#027a8e"));
                circles[i] = node;

                Text data = null;
                data = new Text(String.valueOf(directedSingly.getVertex(i).data));

                data.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                data.setFill(Color.WHITE);
                double textWidth = data.getLayoutBounds().getWidth();
                double textHeight = data.getLayoutBounds().getHeight();
                data.setX(x - textWidth / 2);
                data.setY(y + textHeight / 4);
                texts[i] = data;

                base.getChildren().add(node);
            }
        } catch (ListException | GraphException e) {
            throw new RuntimeException(e);
        }

        drawEdges(circles);
        base.getChildren().addAll(texts);
    }
    private void drawEdges(Circle[] circles) throws ListException {
        int n = listOfEdges.size();
        double centerY = base.getPrefHeight()/2;
        double centerX = base.getPrefWidth()/2;

        for (int i = 0; i < n; i++) {
            String[] parts = listOfEdges.get(i).split("-"); //separa el strin por el "-"
            String vA = parts[0];
            String vB = parts[1];
            int a = directedSingly.indexOf(vA);
            int b = directedSingly.indexOf(vB);
            Circle nodoA = circles[a];
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