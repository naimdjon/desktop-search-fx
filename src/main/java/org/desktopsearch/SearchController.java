package org.desktopsearch;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.desktopsearch.utils.Strings;

import java.util.Collection;
import java.util.LinkedHashSet;

import static org.desktopsearch.search.LocalSearcher.search;
import static org.desktopsearch.utils.Constants.Fields.path;

public class SearchController {
    @FXML
    private TextField queryTextField;

    @FXML
    private Label totalHitsLabel;

    @FXML
    private ListView<String> searchResults;

    @FXML
    private ProgressBar progressBar;

    private Stage stage;

    @SuppressWarnings("unused")
    public void performSearch(final ActionEvent event) {
        try {
            if (Strings.isEmpty(queryTextField.getText())) {
                MessageDialog.error("Empty query", stage);
            }
            final Collection<String> children = new LinkedHashSet<>();
            search(queryTextField.getText(), System.out::println);
            final int totalHits = search(queryTextField.getText(), doc -> children.add(doc.get(path.name())));
            totalHitsLabel.setText("Total hits:" + totalHits);
            ObservableList<String> items = FXCollections.observableArrayList(children);
            searchResults.setItems(items);
            System.out.println("result.totalHitsLabel:" + totalHits);
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialog.error(String.format("Error searching (%s)!", "Error searching!"), stage);
        }
    }

    public void stopIndexingProgress() {
        progressBar.setProgress(60.0);
    }
    public void setStage(final Stage stage) {
        this.stage = stage;
    }
}
