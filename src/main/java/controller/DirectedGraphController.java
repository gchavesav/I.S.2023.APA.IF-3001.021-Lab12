package controller;

import domain.AdjacencyMatrixGraph;
import domain.DirectedSinglyLinkedListGraph;
import domain.EdgeWeight;
import domain.GraphException;
import domain.list.ListException;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class DirectedGraphController
{
    @javafx.fxml.FXML
    private TextArea textareaInfo;
    @javafx.fxml.FXML
    private Pane lienzo;
    @javafx.fxml.FXML
    private Group graf;

    private DirectedSinglyLinkedListGraph matrixGraph;

    @javafx.fxml.FXML
    public void initialize() throws GraphException, ListException {
        matrixGraph = new DirectedSinglyLinkedListGraph();

        matrixGraph.addVertex("P.City");
        matrixGraph.addVertex("madrid");
        matrixGraph.addVertex("Tallin");
        matrixGraph.addVertex("Bogota");
        matrixGraph.addVertex("Seúl");
        matrixGraph.addVertex("San Jose");
        matrixGraph.addVertex("Olso");
        matrixGraph.addVertex("Estocolmo");
        matrixGraph.addVertex("Copenhague");
        matrixGraph.addVertex("Balmopan");



        int numVertices = matrixGraph.size();
        for (int i = 1; i < numVertices; i++) {
            int vertexIndex = util.Utility.random(1,10);
            int vertexIndex2 = util.Utility.random(1,10);
            if (vertexIndex != vertexIndex2) {
                matrixGraph.addEdge(matrixGraph.getVertexByIndex(vertexIndex), matrixGraph.getVertexByIndex(vertexIndex2));
            }
            if (!matrixGraph.containsEdge(matrixGraph.getVertexByIndex(i),matrixGraph.getVertexByIndex(vertexIndex))) {
                matrixGraph.addEdge(matrixGraph.getVertexByIndex(i), matrixGraph.getVertexByIndex(vertexIndex));
            }

        }



        drawMatrixGraph();
    }



    private void drawMatrixGraph() throws ListException, GraphException {
        double centerX = lienzo.getWidth() / 25;
        double centerY = lienzo.getHeight() /25;
        double radius = Math.min(centerX, centerY) - 200;

        int numVertices = matrixGraph.size();
        double angleStep = 2 * Math.PI / numVertices;

        graf.getChildren().clear();

        for (int i = 1; i < numVertices+1; i++) {
            double angle = i * angleStep;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle vertex = new Circle(x, y, 25);
            vertex.setFill(Color.CYAN);

            Text vertexLabel = new Text(String.valueOf(matrixGraph.getVertexByIndex(i).data));
            vertexLabel.setFill(Color.BLACK);
            vertexLabel.setX(x - vertexLabel.getLayoutBounds().getWidth() / 2);
            vertexLabel.setY(y + vertexLabel.getLayoutBounds().getHeight() / 4);

            graf.getChildren().addAll(vertex, vertexLabel);
        }

        // Dibujar las conexiones entre los vértices
        for (int k = 1; k < numVertices+1; k++) {
            for (int j = 1; j < numVertices+1; j++) {
                if (matrixGraph.containsEdge(matrixGraph.getVertexByIndex(k), matrixGraph.getVertexByIndex(j))) {
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

    public void ramdomizeOnAction(ActionEvent actionEvent) {
    }

    public void containsVertexOnAction(ActionEvent actionEvent) {
    }

    public void containsEdgeOnAction(ActionEvent actionEvent) {
    }

    public void toStringOnAction(ActionEvent actionEvent) {
    }

    public void bfsTourOnAction(ActionEvent actionEvent) {
    }

    public void dfsTourOnAction(ActionEvent actionEvent) {

    }
}