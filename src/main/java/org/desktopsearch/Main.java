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
