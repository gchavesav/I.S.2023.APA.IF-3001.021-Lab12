package controller;

import domain.AdjacencyMatrixGraph;
import domain.DirectedSinglyLinkedListGraph;
import domain.GraphException;
import domain.list.ListException;
import domain.list.SinglyLinkedList;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import util.Utility;

public class directedGraphOperationsController
{
    @javafx.fxml.FXML
    private Pane lienzo;
    @javafx.fxml.FXML
    private Group graf;
    @javafx.fxml.FXML
    private TextArea textArea;

    private DirectedSinglyLinkedListGraph directGraph;
    private SinglyLinkedList list;

    //Cola para mantener un registro de los vertices y los pesos creados
    private LinkedQueue edgesAdded;
    private LinkedQueue vertexAdded;
    private boolean isRandomized = false;

    private Alert alert;

    @javafx.fxml.FXML
    public void initialize() throws GraphException, ListException, QueueException {

        //Para remover ordenadamente
        vertexAdded = new LinkedQueue();
        edgesAdded = new LinkedQueue();
        list = new SinglyLinkedList();
        directGraph = new DirectedSinglyLinkedListGraph();
    }


    public void drawGraph() throws ListException, GraphException {
        double centerX = lienzo.getWidth() / 3;
        double centerY = lienzo.getHeight() / 3;
        double radius = Math.min(centerX, centerY) - 100;

        int numVertices = directGraph.size();
        double angleStep = 2 * Math.PI / numVertices;

        graf.getChildren().clear();

        for (int i = 1; i <= numVertices; i++) {
            double angle = i * angleStep;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle vertex = new Circle(x, y, 20);
            vertex.setFill(Color.CYAN);

            Text vertexLabel = new Text(String.valueOf(directGraph.getVertexByIndex(i)));
            vertexLabel.setFill(Color.RED);

            vertexLabel.setX(x - vertexLabel.getLayoutBounds().getWidth() / 2);
            vertexLabel.setY(y + vertexLabel.getLayoutBounds().getHeight() / 4);

            graf.getChildren().addAll(vertex, vertexLabel);
        }

        // Dibujar las conexiones entre los vértices
        for (int k = 1; k <= numVertices; k++) {
            for (int j = 1; j <= numVertices; j++) {
                //METODO containsEdge presenta errores (no se pudo solucionar)
                //No esta detectando las aristas -- REVISAR
                //System.out.println("Contiene aristas?: " + directGraph.containsEdge(directGraph.vertexList.getNode(k), directGraph.vertexList.getNode(j)));
                if (directGraph.containsEdge(list.getNode(k).data, list.getNode(j).data)) {
                    //if (directGraph.containsEdge(directGraph.vertexList.getNode(k).data, directGraph.vertexList.getNode(j).data)) {
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

                    Polygon arrow = new Polygon();
                    arrow.getPoints().addAll(-5.0, -5.0, 0.0, 0.0, -5.0, 5.0);
                    arrow.setFill(Color.BLACK);
                    arrow.setLayoutX(xTo);
                    arrow.setLayoutY(yTo);

                    double angle = Math.atan2(yTo - (centerY + radius * Math.sin(k * angleStep)),
                            xTo - (centerX + radius * Math.cos(k * angleStep)));
                    arrow.setRotate(Math.toDegrees(angle));

                    connection.setOnMouseEntered(event -> {
                        connection.setStroke(Color.RED);
                        connection.setStrokeWidth(6.0);
                    });
                    connection.setOnMouseExited(event -> {
                        connection.setStroke(Color.BLACK);
                        connection.setStrokeWidth(1.5);
                    });

                    graf.getChildren().add(connection);
                    graf.getChildren().add(arrow);

                }
            }
        }
         //Dibujar las conexiones entre los vértices
        for (int k = 1; k <= numVertices; k++) {
            boolean selfLoop = directGraph.containsEdge(list.getNode(k).data, list.getNode(k).data);

            if (selfLoop) {
                double angleFrom = k * angleStep;
                double xFrom = centerX + radius * Math.cos(angleFrom);
                double yFrom = centerY + radius * Math.sin(angleFrom);

                double angleTo = (numVertices + 1) * angleStep; // Ángulo para el vértice extra
                double xTo = centerX + radius * Math.cos(angleTo);
                double yTo = centerY + radius * Math.sin(angleTo);

                Line connection = new Line(
                        xFrom,
                        yFrom,
                        xTo,
                        yTo
                );

                connection.setStroke(Color.BLACK);
                connection.setStrokeWidth(1.5);

                Polygon arrow = new Polygon();
                arrow.getPoints().addAll(-5.0, -5.0, 0.0, 0.0, -5.0, 5.0);
                arrow.setFill(Color.BLACK);
                arrow.setLayoutX(xTo);
                arrow.setLayoutY(yTo);

                double angle = Math.atan2(yTo - yFrom, xTo - xFrom);
                arrow.setRotate(Math.toDegrees(angle));

                connection.setOnMouseEntered(event -> {
                    connection.setStroke(Color.RED);
                    connection.setStrokeWidth(6.0);
                });
                connection.setOnMouseExited(event -> {
                    connection.setStroke(Color.BLACK);
                    connection.setStrokeWidth(1.5);
                });

                graf.getChildren().add(connection);
                graf.getChildren().add(arrow);
            }

            for (int j = 1; j <= numVertices; j++) {
                if (directGraph.containsEdge(list.getNode(k).data, list.getNode(j).data)) {
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

                    Polygon arrow = new Polygon();
                    arrow.getPoints().addAll(-10.0, -10.0, 0.0, 0.0, -10.0, 10.0);
                    arrow.setFill(Color.BLACK);
                    arrow.setLayoutX(xTo);
                    arrow.setLayoutY(yTo);

                    double angle = Math.atan2(yTo - (centerY + radius * Math.sin(k * angleStep)),
                            xTo - (centerX + radius * Math.cos(k * angleStep)));
                    arrow.setRotate(Math.toDegrees(angle));

                    connection.setOnMouseEntered(event -> {
                        connection.setStroke(Color.RED);
                        connection.setStrokeWidth(6.0);
                    });
                    connection.setOnMouseExited(event -> {
                        connection.setStroke(Color.BLACK);
                        connection.setStrokeWidth(1.5);
                    });

                    graf.getChildren().add(connection);
                    graf.getChildren().add(arrow);
                }
            }
        }
        textArea.setText(directGraph.toString());
    }

    public void btnOnActionClear(ActionEvent actionEvent) {
        graf.getChildren().clear();
        vertexAdded.clear();
        edgesAdded.clear();
        list.clear();
        textArea.clear();
        directGraph.clear();
    }

    public void btnOnActionAddVertex(ActionEvent actionEvent) {
    }

    public void btnOnActionRemoveEdgesWeights(ActionEvent actionEvent) throws GraphException, ListException, QueueException {

        System.out.println(directGraph.toString());
        if (!directGraph.isEmpty()){
            if (edgesAdded.isEmpty()){
                alert = util.FXUtility.alert("Error", "No more edges found");
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.showAndWait();
            }else{
                //System.out.println(edgesAdded.front());
                String []remove = edgesAdded.deQueue().toString().split("-");
                //System.out.println("\nBorro la arista entre Vertice 1: -"+  remove[0] + "- Vertice 2: "+  remove[1] + "\n");
                directGraph.removeEdge(remove[0], remove[1]);
            }
        }
        //System.out.println("\n\nAcabo de borrar una arista: \n" +directGraph.toString());
        drawGraph();
    }

    public void btnOnActionAddEdges_Weights(ActionEvent actionEvent) {
    }

    public void btnOnActionRemoveVertex(ActionEvent actionEvent) throws QueueException, GraphException, ListException {

        if (!directGraph.isEmpty()) {
            list.remove(vertexAdded.front());//removemos de la lista
            directGraph.removeVertex(vertexAdded.deQueue());
        }else{
            alert = util.FXUtility.alert("Error", "No more vertex found");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
        drawGraph();
    }

    public void btnOnActionRandomize(ActionEvent actionEvent) throws GraphException, ListException, QueueException {

        vertexAdded.clear();
        edgesAdded.clear();
        list.clear();
        directGraph = new DirectedSinglyLinkedListGraph();

        //agregamos verticces aleatorios no repetidos, pero
        //uno por defecto, para evitar el error IOEception de isEmpty()
        String initialVertex = Utility.getCapital();
        directGraph.addVertex(initialVertex);
        vertexAdded.enQueue(initialVertex);
        list.add(initialVertex);
//        directGraph.addEdgeAndWeight(initialVertex,initialVertex,1000);
//        edgesAdded.enQueue(initialVertex+"-"+initialVertex);

        int randomNumVertex = Utility.random(4,11);
        int count = 0;
        while(count <= randomNumVertex){
            String vertex = Utility.getCapital();
            if (!directGraph.containsVertex(vertex)){
                count++;
                directGraph.addVertex(vertex);
                vertexAdded.enQueue(vertex);
                list.add(vertex);
            }
        }
        //Agregar 3 o 4 pesos aleatorios
        count = list.size();
        int i = 0;
        String vertexRandom1 = "1";
        String vertexRandom2 = "1";
        while(i <= 6){

            vertexRandom1 = (String) list.getNode(Utility.random(1, count)).data;
            vertexRandom2 = (String) list.getNode(Utility.random(1, count)).data;
            directGraph.addEdgeAndWeight(vertexRandom1,vertexRandom2, Utility.random(200,1000));
            edgesAdded.enQueue(vertexRandom1+"-"+vertexRandom2);
            i++;
        }
        drawGraph();
    }

    public String randomCapital() throws GraphException, ListException, QueueException {
        //num aleatorio
        String capital = Utility.getCapital();
        //seguir generando numeros hasta que ninguna coincida con uno de los del grafo
        while(directGraph.containsVertex(capital)){
            capital = Utility.getCapital();
        }
        return capital;
    }
}