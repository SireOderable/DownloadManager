package UI;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import downloadmanager.*;

public class UI extends Application {

  /** downloadmanager to launch downloads from the UI */
  private final DownloadManager manager = new DownloadManager();

  public static void main(final String[] args){
    launch(args);
  }

  /** Initialise and launch the UI
  *
  * @param stage
  */
  @Override
  public void start(final Stage primaryStage) {
    ////////////////////
    primaryStage.setWidth(1200);
    primaryStage.setHeight(800);
    primaryStage.setTitle("Download Manager");
    primaryStage.setResizable(false);
    ////////////////////

    ////////////////////
    final Group root = new Group();
    final Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight(), Color.WHITE);
    ////////////////////

    ////////////////////
    Pane top = new Pane();
    top.setLayoutX(0);
    top.setLayoutY(0);
    top.setPrefWidth(primaryStage.getWidth());
    top.setPrefHeight((primaryStage.getHeight()*25)/100);

    top = PaneFactory.createTopPane(top);
    ////////////////////

    ////////////////////
    ScrollPane bottom = new ScrollPane();
    bottom.setLayoutX(0);
    bottom.setLayoutY((primaryStage.getHeight()*25)/100);
    bottom.setPrefWidth(primaryStage.getWidth());
    bottom.setPrefHeight((primaryStage.getHeight()*75)/100);
    bottom.setStyle("-fx-background-color: black;");
    bottom.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    bottom = PaneFactory.createBottomPane(bottom);
    ////////////////////

    ////////////////////
    ButtonEvent.setButtonEvent((Button)top.getChildren().get(2) ,(TextField)top.getChildren().get(1), (VBox)bottom.getContent(), manager, primaryStage.getWidth(), primaryStage.getHeight());
    ////////////////////

    ////////////////////
    root.getChildren().addAll(top, bottom);

    primaryStage.setScene(scene);
    primaryStage.show();
    ////////////////////
  }
  ////////////////////
}
