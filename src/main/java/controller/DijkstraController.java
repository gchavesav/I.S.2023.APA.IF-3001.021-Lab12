package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;

public class DijkstraController {

    @FXML
    private TableColumn<?, ?> DistanceColum;

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
    void randomizeOnAction(ActionEvent event) {

    }

}