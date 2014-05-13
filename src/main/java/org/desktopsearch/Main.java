package org.desktopsearch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.desktopsearch.index.Indexer;
import org.desktopsearch.utils.Resources;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private final Group root = new Group();

    private void init(final Stage primaryStage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(Resources.loadResource("./main.fxml"));
        final Pane contentPane = fxmlLoader.load();
        final SearchController controller = fxmlLoader.getController();
        controller.stopIndexingProgress();
        controller.setStage(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 768));
        root.getChildren().add(contentPane);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Indexer.createStarted(new File("src/main/resources/testindex")).awaitTermination(5L);
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
