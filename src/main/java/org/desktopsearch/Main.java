package org.desktopsearch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.lucene.search.TopDocs;
import org.desktopsearch.index.Indexer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.desktopsearch.search.LocalSearcher.search;

public class Main extends Application {

    private final Group root = new Group();

    private void init(final Stage primaryStage) throws IOException {
       /* primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 768));
        final TextField queryTextField = new TextField();
        final Button searchButton = new Button("Search");
        searchButton.setOnAction(actionEvent -> processResult(search(queryTextField.getText())));
        final BorderPane contentPane=new BorderPane();
        final HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(queryTextField, searchButton);
        hbox.setAlignment(Pos.TOP_LEFT);

        contentPane.setTop(hbox);
        final Label test = new Label("test");
        contentPane.setCenter(test);

        root.getChildren().add(contentPane);*/
        URL location = test.class.getClassLoader().getResource("./main.fxml");
        System.out.println(location);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Pane contentPane = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        System.out.println(controller);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 768));
        root.getChildren().add(contentPane);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Indexer.createStarted(new File("testindex")).awaitTermination(5L);
        init(primaryStage);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
