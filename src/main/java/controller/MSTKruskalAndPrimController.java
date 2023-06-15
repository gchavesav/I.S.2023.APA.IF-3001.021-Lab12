package controller;

import domain.AdjacencyListGraph;
import domain.AdjacencyMatrixGraph;
import domain.SinglyLinkedListGraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.util.Collection;

public class MSTKruskalAndPrimController {

    @FXML
    private Pane pnGraph;
    @FXML
    private Pane pnMST;
    @FXML
    private RadioButton rbtAdjList;
    @FXML
    private RadioButton rbtAdjMatrix;
    @FXML
    private RadioButton rbtKruskal;
    @FXML
    private RadioButton rbtLinkedList;
    @FXML
    private RadioButton rbtPrim;
    private AdjacencyListGraph adjacencyListGraph;
    private AdjacencyMatrixGraph adjacencyMatrixGraph;
    private SinglyLinkedListGraph singlyLinkedListGraph;

    @FXML
    void initialize(){

        ToggleGroup group = new ToggleGroup();
        rbtAdjMatrix.setToggleGroup(group);
        rbtAdjList.setToggleGroup(group);
        rbtLinkedList.setToggleGroup(group);
        ToggleGroup group1 = new ToggleGroup();
        rbtKruskal.setToggleGroup(group1);
        rbtPrim.setToggleGroup(group1);

    }

    @FXML
    void onActionRandomize(ActionEvent event) {

    }

}
