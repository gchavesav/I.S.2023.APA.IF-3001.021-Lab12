package controller;

import domain.GraphException;
import domain.SinglyLinkedListGraph;
import domain.Vertex;
import domain.list.ListException;
import domain.stack.StackException;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
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

    private TextInputDialog input1;

    private SinglyLinkedListGraph listGraph;
    @javafx.fxml.FXML
    public void initialize() throws GraphException, ListException {
        listGraph= new SinglyLinkedListGraph();
        for (int i = 1; i < 11; i++) { //Se inicializa asi para evitar problemas de is null
            String country = util.Utility.getCountry();
            if (i==1 ){
                listGraph.addVertex(country);
            }else{
                if (!listGraph.containsVertex(country)){
                    listGraph.addVertex(country);
                }else {
                    i--;
                }

            }
        }
        for (int i = 1; i < 11; i++) {
            int vertexIndex = util.Utility.random(i);
            int vertexIndex2 = util.Utility.random(i);
            int weight = util.Utility.random(200,1000);
            if (vertexIndex!=vertexIndex2) {
                listGraph.addEdgeAndWeight(listGraph.getVertexByIndex(vertexIndex2).data, listGraph.getVertexByIndex(vertexIndex).data, weight);
            }
            if (!listGraph.containsEdge(listGraph.getVertexByIndex(i).data,listGraph.getVertexByIndex(vertexIndex).data)) {
                listGraph.addEdgeAndWeight(listGraph.getVertexByIndex(i).data, listGraph.getVertexByIndex(vertexIndex).data, weight);
            }
        }
        drawListGraph();
    }

    private void drawListGraph() throws ListException, GraphException {
        double centerX = lienzo.getWidth() / 25;
        double centerY = lienzo.getHeight() /25;
        double radius = Math.min(centerX, centerY) - 200;

        int numVertices = listGraph.size();
        double angleStep = 2 * Math.PI / numVertices;

        graf.getChildren().clear();

        for (int i = 1; i < numVertices+1; i++) {
            double angle = i * angleStep;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle vertex = new Circle(x, y, 25);
            vertex.setFill(Color.GOLD);

            Text vertexLabel = new Text(String.valueOf(listGraph.getVertexByIndex(i).data));
            vertexLabel.setFill(Color.BLACK);
            vertexLabel.setX(x - vertexLabel.getLayoutBounds().getWidth() / 2);
            vertexLabel.setY(y + vertexLabel.getLayoutBounds().getHeight() / 4);

            graf.getChildren().addAll(vertex, vertexLabel);

        }

        for (int k = 1; k < numVertices+1; k++) {
            for (int j = 1; j < numVertices+1; j++) {
                if (listGraph.containsEdge(listGraph.getVertexByIndex(k).data, listGraph.getVertexByIndex(j).data)) {
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
    void bfsTourOnAction(ActionEvent event) {

    }

    @javafx.fxml.FXML
    void containsEdgeOnAction(ActionEvent event) {

    }

    @javafx.fxml.FXML
    void containsVertexOnAction(ActionEvent event) {
        input1 = util.FXUtility.dialog("Vertex","Contains: ");
        input1.showAndWait();
        String vertex1 = String.valueOf(input1.getResult());
        this.alert=util.FXUtility.alert("Vertex ","Contains: ");
        alert.setAlertType(Alert.AlertType.INFORMATION);
        try {
            alert.setContentText(String.valueOf(listGraph.containsVertex(vertex1)));
            alert.showAndWait();
        } catch (GraphException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }


    @javafx.fxml.FXML
    void dfsTourOnAction(ActionEvent event) {
        try {
            textareaInfo.setText("DFS Tour on Linked Graph: \n"+listGraph.dfs());
        } catch (GraphException e) {
            throw new RuntimeException(e);
        } catch (StackException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }

    }

    @javafx.fxml.FXML
    void randomizeOnAction(ActionEvent event) throws GraphException, ListException {
        listGraph.clear();
        for (int i = 1; i < 11; i++) {
            String country = util.Utility.getCountry();
            if (i==1 ){
                listGraph.addVertex(country);
            }else{
                if (!listGraph.containsVertex(country)){
                    listGraph.addVertex(country);

                }else {
                    i--;
                }
            }
        }
        for (int i = 1; i < 11; i++) {
            int vertexIndex = util.Utility.random(i);
            int vertexIndex2 = util.Utility.random(i);
            int weight = util.Utility.random(200,1000);
            if (vertexIndex!=vertexIndex2) {
                listGraph.addEdgeAndWeight(listGraph.getVertexByIndex(vertexIndex2).data, listGraph.getVertexByIndex(vertexIndex).data, weight);
            }
            if (!listGraph.containsEdge(listGraph.getVertexByIndex(i).data,listGraph.getVertexByIndex(vertexIndex).data)) {
                listGraph.addEdgeAndWeight(listGraph.getVertexByIndex(i).data, listGraph.getVertexByIndex(vertexIndex).data, weight);
            }
        }
        drawListGraph();
    }

    @javafx.fxml.FXML
    void toStringOnAction(ActionEvent event) {

    }





}
