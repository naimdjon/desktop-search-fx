package org.desktopsearch;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.desktopsearch.utils.Constants;

import java.util.Collection;
import java.util.LinkedHashSet;

import static org.desktopsearch.search.LocalSearcher.search;

public class SearchController {
    @FXML
    private TextField queryTextField;

    @FXML
    private Label totalHitsLabel;

    @FXML
    private ListView searchResults;

    private Stage stage;

    @SuppressWarnings("unused")
    public void performSearch(final ActionEvent event) {
        try {
            final Collection<String> children= new LinkedHashSet<>();
            search(queryTextField.getText(), System.out::println);
            final int totalHits = search(queryTextField.getText(), doc -> children.add(doc.get(Constants.Fields.path.name())));
            totalHitsLabel.setText("Total hits:" + totalHits);
            ObservableList<String> items = FXCollections.observableArrayList(children);
            searchResults.setItems(items);
            System.out.println("result.totalHitsLabel:" + totalHits);
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog(String.format("Error searching (%s)!","Error searching!")).show(stage);
        }
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }
}
