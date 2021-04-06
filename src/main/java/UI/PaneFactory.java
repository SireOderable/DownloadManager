package UI;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.geometry.VPos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.layout.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.util.converter.NumberStringConverter;
import java.util.*;
import downloadmanager.*;

public class PaneFactory {

  /** Create the pane of the top of the UI
  *
  * @param Pane
  *
  * @return Pane
  */
  public static Pane createTopPane(Pane top){

    ////////////////////
    try{
      FileInputStream backgroundf = new FileInputStream("src/main/resources/background.jpg");
      Image background_i = new Image(backgroundf, top.getPrefWidth(), top.getPrefHeight(), true, true);
      top.setBackground(new Background(new BackgroundImage(background_i, BackgroundRepeat.NO_REPEAT,
      BackgroundRepeat.NO_REPEAT,
      BackgroundPosition.CENTER,
      new BackgroundSize(top.getPrefWidth(), top.getPrefHeight(), false, false, false, false))));
    } catch(FileNotFoundException e){};
    ////////////////////

    ////////////////////
    Text ask = new Text();
    ask.setTextAlignment(TextAlignment.CENTER);
    ask.setText("Enter your URL below :");
    ask.setLayoutX((top.getPrefWidth()*30)/100);
    ask.setLayoutY((top.getPrefHeight()*50)/100);
    ask.setFont(Font.loadFont("file:src/main/resources/UbuntuMono-Regular.ttf", 24));
    ask.setStyle("-fx-text-inner-color: red;");
    ////////////////////

    ////////////////////
    TextField txt = new TextField();
    txt.setPrefWidth((top.getPrefWidth()*60)/100);
    txt.setPrefHeight((top.getPrefHeight()*20)/100);
    txt.setLayoutX((top.getPrefWidth()*5)/100);
    txt.setLayoutY((top.getPrefHeight()*70)/100);
    txt.setFont(Font.loadFont("file:src/main/resources/UbuntuMono-Regular.ttf", 18));
    ////////////////////

    ////////////////////
    Button b = new Button();
    b.setText("Download");
    b.setFont(Font.loadFont("file:src/main/resources/UbuntuMono-Regular.ttf", 18));
    b.setPrefWidth((top.getPrefWidth()*20)/100);
    b.setPrefHeight((top.getPrefHeight()*20)/100);
    b.setLayoutX((top.getPrefWidth()*68)/100);
    b.setLayoutY((top.getPrefHeight()*70)/100);
    ////////////////////

    top.getChildren().addAll(ask, txt, b);
    return top;
  }
  ////////////////////

  /** Create the Pane that shows downloads
  *
  * @param ScrollPane
  *
  * @return ScrollPane
  */
  public static ScrollPane createBottomPane(ScrollPane bottom){
    VBox dls = new VBox();
    String style = "-fx-background-color: rgba(0, 0, 0, 0.5);";
    dls.setStyle(style);
    bottom.setContent(dls);
    return bottom;
  }
  ////////////////////

  /** Add a download to the downloadManager and show it on the UI
  *
  * @param VBox
  * @param stage
  * @param stage
  */
  public static void addDl(VBox dls, DownloadManager manager, int index, double Width, double Height){
    DownloadPane p = new DownloadPane();

    try{
      p = new DownloadPane(Width-10, ((Height*15)/100)-10, manager, index);
    } catch(FileNotFoundException e){};
    manager.getDownloadList().get(index).addObserver(p);

    dls.getChildren().add(p);
  }
  ////////////////////

}
