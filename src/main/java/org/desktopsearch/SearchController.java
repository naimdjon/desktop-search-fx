package org.desktopsearch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.LinkedHashSet;

import static org.desktopsearch.search.LocalSearcher.search;
import static org.desktopsearch.utils.Constants.Fields.contents;

public class SearchController {
    @FXML
    private TextField queryTextField;

    @FXML
    private Label totalHitsLabel;

    @FXML
    private VBox searchResults;

    private Stage stage;

    @SuppressWarnings("unused")
    public void performSearch(final ActionEvent event) {
        try {
            final Collection<Node> children= new LinkedHashSet<>();
            final int totalHits = search(queryTextField.getText(), doc -> {
                children.add(new Label(doc.get(contents.name())));
            });
            totalHitsLabel.setText("Total hits:" + totalHits);
            searchResults.getChildren().addAll(children);
            System.out.println("result.totalHitsLabel:" + totalHits);
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog(String.format("Error searching (%s)!",e.getMessage())).show(stage);
        }
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }
}
