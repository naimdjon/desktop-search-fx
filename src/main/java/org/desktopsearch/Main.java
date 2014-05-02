package org.desktopsearch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.desktopsearch.index.Indexer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private final Group root = new Group();

    private void init(final Stage primaryStage) throws IOException {
        final Scene scene = new Scene(root, 600, 768);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        final URL location = Main.class.getClassLoader().getResource("./main.fxml");
        final FXMLLoader fxmlLoader = new FXMLLoader(location);
        final Pane contentPane = fxmlLoader.load();
        final SearchController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
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
