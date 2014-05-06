package org.desktopsearch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.lucene.search.TopDocs;

import static org.desktopsearch.search.LocalSearcher.search;

public class SearchController {
    @FXML
    private TextField queryTextField;

    @FXML
    private Label totalHits;

    private Stage stage;

    @SuppressWarnings("unused")
    public void performSearch(final ActionEvent event) {
        try {
            final TopDocs result = search(queryTextField.getText());
            System.out.println("result.totalHits:" + result.totalHits);
            totalHits.setText("Total hits:" + result.totalHits);
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog(String.format("Error searching (%s)!",e.getMessage())).show(stage);
        }
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }
}
