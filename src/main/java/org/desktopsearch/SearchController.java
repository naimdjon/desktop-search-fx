package org.desktopsearch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.lucene.search.TopDocs;

import java.net.URL;
import java.util.ResourceBundle;

import static org.desktopsearch.search.LocalSearcher.search;

public class SearchController implements Initializable {
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
        try {
            final TopDocs result = search(queryTextField.getText());
            System.out.printf("result.totalHits:" + result.totalHits);
            totalHits.setText("Total hits:" + result.totalHits);
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog(String.format("Error searching (%s)!",e.getMessage())).show(stage);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
