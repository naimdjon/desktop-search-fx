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

public class Main extends Application {

    private void init(Stage primaryStage) {
        final Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400, 100));
        final TextField queryTextField = new TextField();
        final Button searchButton = new Button("Search");
        final HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(queryTextField, searchButton);
        hbox.setAlignment(Pos.CENTER);
        root.getChildren().add(hbox);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
