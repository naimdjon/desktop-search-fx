package org.desktopsearch;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.lucene.search.TopDocs;
import org.desktopsearch.index.Indexer;

import java.io.File;

import static org.desktopsearch.search.LocalSearcher.search;

public class Main extends Application {

    private void init(final Stage primaryStage) {
        final Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 768));
        final TextField queryTextField = new TextField();
        final Button searchButton = new Button("Search");
        searchButton.setOnAction(actionEvent -> processResult(search(queryTextField.getText())));
        final HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(queryTextField, searchButton);
        hbox.setAlignment(Pos.CENTER);
        root.getChildren().add(hbox);
    }

    private void processResult(final TopDocs result) {
        System.out.printf("result.totalHits:"+result.totalHits);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Indexer.createStarted(new File("testindex")).awaitTermination(5L);
        init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
