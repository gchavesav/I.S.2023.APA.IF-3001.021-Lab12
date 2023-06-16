package controller;

import domain.DirectedSinglyLinkedListGraph;
import domain.GraphException;
import domain.list.ListException;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
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
        String randomCapital = getCapital();
        matrixGraph.addVertex(randomCapital);

        while (matrixGraph.size()<10){
            String randomCapital2 = getCapital();
            if (!matrixGraph.containsVertex(randomCapital2)){
            matrixGraph.addVertex(randomCapital2);
            }


        }



        int numVertices = matrixGraph.size();



        for (int i = 1; i < numVertices+1; i++) {
            int vertexIndex = util.Utility.random(1,10);
            int vertexIndex2 = util.Utility.random(1,10);
            if (vertexIndex != vertexIndex2) {
                matrixGraph.addEdge(matrixGraph.getVertexByIndex(i).data, matrixGraph.getVertexByIndex(vertexIndex).data);
            }
            if (!matrixGraph.containsEdge(matrixGraph.getVertexByIndex(i).data,matrixGraph.getVertexByIndex(vertexIndex).data)) {
                matrixGraph.addEdge(matrixGraph.getVertexByIndex(i).data, matrixGraph.getVertexByIndex(vertexIndex).data);
            }


        }



        drawMatrixGraph();
    }



    private void drawMatrixGraph() throws ListException, GraphException {
        double centerX = lienzo.getWidth() / 25;
        double centerY = lienzo.getHeight() / 25;
        double radius = Math.min(centerX, centerY) - 200;

        int numVertices = matrixGraph.size();
        double angleStep = 2 * Math.PI / numVertices;

        graf.getChildren().clear();

        for (int i = 1; i < numVertices + 1; i++) {
            double angle = i * angleStep;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle vertex = new Circle(x, y, 27);
            vertex.setFill(Color.CYAN);

            Text vertexLabel = new Text(String.valueOf(matrixGraph.getVertexByIndex(i).data));
            vertexLabel.setFill(Color.BLACK);
            vertexLabel.setFont(Font.font("Arial", 16)); // Ajusta el tamaño de la fuente aquí
            vertexLabel.setX(x - vertexLabel.getLayoutBounds().getWidth() / 2);
            vertexLabel.setY(y + vertexLabel.getLayoutBounds().getHeight() / 4);

            // Verificar si el vértice tiene conexión consigo mismo
            if (matrixGraph.containsEdge(matrixGraph.getVertexByIndex(i).data, matrixGraph.getVertexByIndex(i).data)) {
                Circle selfConnectionCircle = new Circle(x, y, 35);
                selfConnectionCircle.setFill(Color.TRANSPARENT);
                selfConnectionCircle.setStroke(Color.BLACK);
                selfConnectionCircle.setStrokeWidth(1.5);
                graf.getChildren().add(selfConnectionCircle);
            }

            graf.getChildren().addAll(vertex, vertexLabel);
        }

        // Dibujar las conexiones entre los vértices
        for (int k = 1; k < numVertices + 1; k++) {
            for (int j = 1; j < numVertices + 1; j++) {
                if (matrixGraph.containsEdge(matrixGraph.getVertexByIndex(k).data, matrixGraph.getVertexByIndex(j).data)) {
                    double angleFrom = k * angleStep;
                    double angleTo = j * angleStep;
                    double xFrom = centerX + radius * Math.cos(angleFrom);
                    double yFrom = centerY + radius * Math.sin(angleFrom);
                    double xTo = centerX + radius * Math.cos(angleTo);
                    double yTo = centerY + radius * Math.sin(angleTo);

                    double dx = xTo - xFrom;
                    double dy = yTo - yFrom;
                    double angle = Math.atan2(dy, dx);

                    double arrowLength = 5;
                    double arrowAngle = Math.PI / 10;

                    double vertexRadius = 15;

                    // Calcular las coordenadas ajustadas para los puntos de flecha
                    double arrowPoint1X = xTo - (vertexRadius + arrowLength) * Math.cos(angle - arrowAngle);
                    double arrowPoint1Y = yTo - (vertexRadius + arrowLength) * Math.sin(angle - arrowAngle);
                    double arrowPoint2X = xTo - (vertexRadius + arrowLength) * Math.cos(angle + arrowAngle);
                    double arrowPoint2Y = yTo - (vertexRadius + arrowLength) * Math.sin(angle + arrowAngle);

                    // Calcular el punto central de las líneas
                    double lineCenterX = (xTo + arrowPoint1X) / 2;
                    double lineCenterY = (yTo + arrowPoint1Y) / 2;

                    Line connectionLine = new Line(xFrom, yFrom, lineCenterX, lineCenterY);
                    connectionLine.setStroke(Color.BLACK);
                    connectionLine.setStrokeWidth(1.5);

                    Polygon arrowhead = new Polygon(xTo, yTo, arrowPoint1X, arrowPoint1Y, arrowPoint2X, arrowPoint2Y);
                    arrowhead.setFill(Color.BLACK);

                    Group connectionGroup = new Group(connectionLine, arrowhead);
                    connectionGroup.setOnMouseEntered(event -> {
                        connectionLine.setStroke(Color.RED);
                        connectionLine.setStrokeWidth(6.0);
                    });
                    connectionGroup.setOnMouseExited(event -> {
                        connectionLine.setStroke(Color.BLACK);
                        connectionLine.setStrokeWidth(1.5);
                    });

                    graf.getChildren().add(connectionGroup);
                }
            }
        }
    }

    public static String getCapital(){
        String list[] = {"Buenos Aires", "Canberra", "Viena", "Berlín",
                "Bruselas", "La Paz", "Brasilia", "Belmopán",
                "San José", "Bogotá", "Ottawa", "Santiago",
                "Copenhague", "Quito", "Tallin", "San Salvador",
                "París", "Helsinki", "Atenas", "Ciudad de Guatemala",
                "Tegucigalpa", "Budapest", "Nueva Delhi", "Roma",
                "Kingston", "Tokio", "Ciudad de México", "Rabat",
                "Washington D.C.", "Abuya", "Panamá", "Lisboa"
        };

        return list[util.Utility.random(31)];
    }



    public void ramdomizeOnAction(ActionEvent actionEvent) throws GraphException, ListException {
        matrixGraph = new DirectedSinglyLinkedListGraph();
        String randomCapital = getCapital();
        matrixGraph.addVertex(randomCapital);

        while (matrixGraph.size()<10){
            String randomCapital2 = getCapital();
            if (!matrixGraph.containsVertex(randomCapital2)){
                matrixGraph.addVertex(randomCapital2);
            }


        }

        int numVertices = matrixGraph.size();

        for (int i = 1; i < numVertices+1; i++) {
            int vertexIndex = util.Utility.random(1,10);
            int vertexIndex2 = util.Utility.random(1,10);
            if (vertexIndex != vertexIndex2) {
                matrixGraph.addEdge(matrixGraph.getVertexByIndex(i).data, matrixGraph.getVertexByIndex(vertexIndex).data);
            }
            if (!matrixGraph.containsEdge(matrixGraph.getVertexByIndex(i).data,matrixGraph.getVertexByIndex(vertexIndex).data)) {
                matrixGraph.addEdge(matrixGraph.getVertexByIndex(i).data, matrixGraph.getVertexByIndex(vertexIndex).data);
            }


        }

        drawMatrixGraph();
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