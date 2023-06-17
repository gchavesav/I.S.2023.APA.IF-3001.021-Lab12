package controller;

import domain.GraphException;
import domain.SinglyLinkedListGraph;
import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class LinkedGraphController
{
    @javafx.fxml.FXML
    private TextArea textareaInfo;
    @javafx.fxml.FXML
    private Pane lienzo;

    @javafx.fxml.FXML
    private Group graf;

    private Alert alert;

    private TextInputDialog input1, input2;

    private SinglyLinkedListGraph listGraph;
    @javafx.fxml.FXML
    public void initialize() throws GraphException, ListException {
//        listGraph= new SinglyLinkedListGraph();
//        for (int i = 0; i < 10; i++) {
//            String country = util.Utility.getCountry(i);
//            if (i==0){
//                listGraph.addVertex(country);
//            }else{
//                if (!listGraph.containsVertex(country)){
//                    listGraph.addVertex(country);
//                }else{
//                    i--;
//                }
//            }
//        }
//        for (int i = 0; i < 10; i++) {
//            String vertexIndex = util.Utility.getCountry(i);
//            String vertexIndex2 = util.Utility.getCountry(i);
//            int weight = util.Utility.random(200,1000);
//            if (!listGraph.containsEdge(listGraph.getVertex(vertexIndex2).data,listGraph.getVertex(vertexIndex).data)) {
//                listGraph.addEdgeAndWeight(listGraph.getVertex(vertexIndex2).data, listGraph.getVertex(vertexIndex).data, weight);
//            }
//            if (!listGraph.containsEdge(listGraph.getVertex(i).data,listGraph.getVertex(vertexIndex).data)) {
//                listGraph.addEdgeAndWeight(listGraph.getVertex(i).data, listGraph.getVertex(vertexIndex).data, weight);
//
//            }
//        }
//        drawListGraph();
    }

    private void drawListGraph() throws ListException, GraphException {
        double centerX = lienzo.getWidth() / 25;
        double centerY = lienzo.getHeight() /25;
        double radius = Math.min(centerX, centerY) - 200;

        int numVertices = listGraph.size();
        double angleStep = 2 * Math.PI / numVertices;

        graf.getChildren().clear();

        for (int i = 0; i < numVertices; i++) {
            double angle = i * angleStep;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle vertex = new Circle(x, y, 25);
            vertex.setFill(Color.CYAN);

            Text vertexLabel = new Text(String.valueOf(listGraph.getVertex(i).data));
            vertexLabel.setFill(Color.BLACK);
            vertexLabel.setX(x - vertexLabel.getLayoutBounds().getWidth() / 2);
            vertexLabel.setY(y + vertexLabel.getLayoutBounds().getHeight() / 4);

            graf.getChildren().addAll(vertex, vertexLabel);
        }

        // Dibujar las conexiones entre los vértices
        for (int k = 0; k < numVertices; k++) {
            for (int j = 0; j < numVertices; j++) {
                if (listGraph.containsEdge(listGraph.getVertex(k).data, listGraph.getVertex(j).data)) {
                    double angleTo = j * angleStep;
                    double xTo = centerX + radius * Math.cos(angleTo);
                    double yTo = centerY + radius * Math.sin(angleTo);

                    Line connection = new Line(
                            centerX + radius * Math.cos(k * angleStep),
                            centerY + radius * Math.sin(k * angleStep),
                            xTo,
                            yTo
                    );

                    connection.setStroke(Color.BLACK);
                    connection.setStrokeWidth(1.5);

                    connection.setOnMouseEntered(event -> {
                        connection.setStroke(Color.RED);
                        connection.setStrokeWidth(6.0);
                    });

                    connection.setOnMouseExited(event -> {
                        connection.setStroke(Color.BLACK);
                        connection.setStrokeWidth(1.5);
                    });

                    graf.getChildren().add(connection);
                }
            }
        }

    }

    @javafx.fxml.FXML
    void bfsTourOnAction(ActionEvent event) throws GraphException, QueueException, ListException {
        textareaInfo.setText(listGraph.bfs());
    }

    @javafx.fxml.FXML
    void containsEdgeOnAction(ActionEvent event) {
        input1 = util.FXUtility.dialog("Edge Contains","Contains: ");
        input1.showAndWait();
        int value1 = Integer.parseInt(input1.getResult());
        this.alert=util.FXUtility.alert("Edge Contains","Contains: ");
        alert.setAlertType(Alert.AlertType.INFORMATION);

        input2 = util.FXUtility.dialog("Edge Contains","Contains: ");
        input2.showAndWait();
        int value2 = Integer.parseInt(input2.getResult());
        this.alert=util.FXUtility.alert("Edge Contains","Contains: ");
        alert.setAlertType(Alert.AlertType.INFORMATION);

        try {
            alert.setContentText(String.valueOf(listGraph.containsEdge(value1, value2)));
            alert.showAndWait();
        } catch (GraphException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }

    }

    @javafx.fxml.FXML
    void containsVertexOnAction(ActionEvent event) {


    }

    @javafx.fxml.FXML
    void dfsTourOnAction(ActionEvent event) throws GraphException, ListException, StackException {

            textareaInfo.setText("DFS Tour on Linked Graph: \n"+listGraph.dfs());


    }

    @javafx.fxml.FXML
    void randomizeOnAction(ActionEvent event) throws GraphException, ListException {
//        listGraph= new SinglyLinkedListGraph();
//        for (int i = 0; i < 10; i++) {
//            String country = util.Utility.getCountry(i);
//            if (i==0){
//                listGraph.addVertex(country);
//            }else{
//                if (!listGraph.containsVertex(country)){
//                    listGraph.addVertex(country);
//                }else{
//                    i--;
//                }
//            }
//        }
//        for (int i = 0; i < 10; i++) {
//            String vertexIndex = util.Utility.getCountry(i);
//            String vertexIndex2 = util.Utility.getCountry(i);
//            int weight = util.Utility.random(200,1000);
//            if (!listGraph.containsEdge(listGraph.getVertex(vertexIndex2).data,listGraph.getVertex(vertexIndex).data)) {
//                listGraph.addEdgeAndWeight(listGraph.getVertex(vertexIndex2).data, listGraph.getVertex(vertexIndex).data, weight);
//            }
//            if (!listGraph.containsEdge(listGraph.getVertex(i).data,listGraph.getVertex(vertexIndex).data)) {
//                listGraph.addEdgeAndWeight(listGraph.getVertex(i).data, listGraph.getVertex(vertexIndex).data, weight);
//
//            }
//        }
//        drawListGraph();
    }

    @javafx.fxml.FXML
    void toStringOnAction(ActionEvent event) {
        textareaInfo.setText(listGraph.toString());
    }





}