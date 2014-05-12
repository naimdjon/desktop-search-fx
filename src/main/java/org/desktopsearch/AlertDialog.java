package org.desktopsearch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertDialog {
    private final String message;

    public AlertDialog(final String message) {
        this.message = message;
    }

    public void show(final Stage stage) {
        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(stage);
        dialog.setScene(buildScene(stage, dialog));
        dialog.getScene().getStylesheets().add(getClass().getResource("/alert.css").toExternalForm());
        final Node root = dialog.getScene().getRoot();
        final Coordinates dragCoordinates = new Coordinates();
        root.setOnMousePressed(mouseEvent -> {
            dragCoordinates.x = dialog.getX() - mouseEvent.getScreenX();
            dragCoordinates.y = dialog.getY() - mouseEvent.getScreenY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            dialog.setX(mouseEvent.getScreenX() + dragCoordinates.x);
            dialog.setY(mouseEvent.getScreenY() + dragCoordinates.y);
        });
        stage.getScene().getRoot().setEffect(new BoxBlur());
        dialog.show();
    }

    private Scene buildScene(Stage stage, Stage dialog) {
        final Button okButton = new Button("OK");
        okButton.cancelButtonProperty().set(true);
        okButton.setOnAction(closeDialog(stage, dialog));
        final HBox hbox = new HBox(new Label(message), okButton);
        hbox.getStyleClass().add("alert-dialog");
        return new Scene(hbox, Color.TRANSPARENT);
    }

    private EventHandler<ActionEvent> closeDialog(Stage stage, Stage dialog) {
        return actionEvent -> {
            stage.getScene().getRoot().setEffect(null);
            dialog.close();
        };
    }

    class Coordinates {
        double x, y;
    }
}
