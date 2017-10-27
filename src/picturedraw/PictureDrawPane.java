/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturedraw;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

/**
 *
 * @author Tavi
 */
public class PictureDrawPane extends AnchorPane {

    @FXML
    private ResizableCanvas canvas;
    @FXML
    private Button clearButton;
    @FXML
    private ColorPicker colorpicker;
    @FXML
    private AnchorPane bgpane;
    @FXML
    private Slider slider;
    @FXML
    private Label timeLabel;
    @FXML
    private Button timeButton;

    public PictureDrawPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PictureDrawPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void aikaMuutos() throws InterruptedException {
        for (int i = 60; i > 0; i--) {
            timeLabel.setText(String.valueOf(i));
            Thread.sleep(1000);
        }
    }

    @FXML
    private void initialize() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.setFill(Color.WHITE);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        colorpicker.setValue(Color.BLACK);

        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMax(20.0);
        slider.setMin(1.0);
        slider.setValue(3.0);
        slider.setMajorTickUnit(5.0f);
        slider.setBlockIncrement(5.0f);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);

        slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            gc.setLineWidth(newValue.doubleValue());
        });

        double newThicknessValue = 1.0;

        canvas.setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() < 0) {
                gc.setLineWidth(slider.getValue() + newThicknessValue);
                slider.setValue(slider.getValue() + newThicknessValue);

            } else if (event.getDeltaY() > 0) {
                gc.setLineWidth(slider.getValue() - newThicknessValue);
                slider.setValue(slider.getValue() - newThicknessValue);

            }
        });


        bgpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        timeButton.setOnAction((event) -> {
//            for (int i = 60; i > 0; i--) {
//                timeLabel.setText(String.valueOf(i));
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//
//            }


            int i = 60;
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0),
                    new KeyValue(timeLabel.textProperty(), String.valueOf(60))));
            timeline.play();
        });

        clearButton.setOnAction((event) -> {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        slider.setOnScroll((event) -> {

        });

        canvas.setOnMouseDragged((MouseEvent event) -> {
            gc.lineTo(event.getX(), event.getY());


            if (event.isPrimaryButtonDown()) {
                gc.setStroke(colorpicker.getValue());
            } else if (event.isSecondaryButtonDown()) {
                gc.setStroke(Color.WHITE);
            }
            gc.stroke();
            System.out.println(event.getX() + " " + event.getY());
        });

        canvas.setOnMousePressed((MouseEvent event) -> {
            gc.beginPath();
            gc.lineTo(event.getX(), event.getY());

            if (event.isPrimaryButtonDown()) {
                gc.setStroke(colorpicker.getValue());
            } else if (event.isSecondaryButtonDown()) {
                gc.setStroke(Color.WHITE);
            }

            gc.stroke();
        });
    }
}
