package org.desktopsearch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button searchButton;

    @FXML
    private TextField queryTextField;

    @FXML
    private Label totalHits;
    private Stage stage;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        searchButton.setOnAction(this::performSearch);
    }

    private void performSearch(final ActionEvent event) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
