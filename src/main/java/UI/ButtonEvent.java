package UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import downloadmanager.*;

public class ButtonEvent{
  /**set the action event
  *
  * @param Button
  * @param TextField
  * @param VBox
  * @param Stage
  * @param DownloadManager
  */
  public static void setButtonEvent(Button btn, TextField t, VBox dls, DownloadManager manager, double Width, double Height){
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(!t.getText().trim().isEmpty()){
          manager.addDownload(t.getText());
          if(manager.getDownloadList().get(manager.getDownloadList().size()-1) != null)
            PaneFactory.addDl(dls, manager, manager.getDownloadList().size()-1, Width, Height);
        }
      }
    });
  }
  ////////////////////


  /**set the Image of the button play to pause
  *
  * @param Button
  * @param Download
  */
  public static void setPauseEvent(Button play, DownloadManager manager, int index){
    play.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(manager.getDownloadList().get(index).getState() == 1){
          try{
            manager.pauseDownload(index);
            FileInputStream playf = new FileInputStream("src/main/resources/play.png");
            Image play_i = new Image(playf, play.getPrefWidth(), play.getPrefHeight(), true, true);
            play.setGraphic(new ImageView(play_i));
          } catch(FileNotFoundException e){};
          ButtonEvent.setPlayEvent(play, manager, index);
        }
      }
    });
  }
  ////////////////////

  /**set the Image of the button play to pause
  *
  * @param Button
  * @param Download
  */
  public static void setPlayEvent(Button pause, DownloadManager manager, int index){
    pause.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(manager.getDownloadList().get(index).getState() == 2){
          try{
            manager.startDownload(index);
            FileInputStream pausef = new FileInputStream("src/main/resources/pause.png");
            Image pause_i = new Image(pausef, pause.getPrefWidth(), pause.getPrefHeight(), true, true);
            pause.setGraphic(new ImageView(pause_i));
          } catch(FileNotFoundException e){};
          ButtonEvent.setPauseEvent(pause, manager, index);
        }
      }
    });
  }

  ////////////////////

  /**Stop the download
  *
  * @param Button
  * @param Download
  */
  public static void setCancelEvent(Button stop, DownloadManager manager, int index){
      stop.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          if(manager.getDownloadList().get(index).getState() != 3){
            System.out.println(manager.getDownloadList().get(index).getState());
            manager.cancelDownload(index);
            ButtonEvent.setDeleteEvent(stop, manager, index);
          }
          else
            ((VBox)((Pane)stop.getParent()).getParent()).getChildren().remove((Pane)stop.getParent());
        }
      });
    }

  ////////////////////

  /**set the Image of the button play to pause
  *
  * @param Button
  */
  public static void setDeleteEvent(Button delete, DownloadManager manager, int index){
    delete.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ((VBox)((Pane)delete.getParent()).getParent()).getChildren().remove((Pane)delete.getParent());
        manager.clearDownload(index);
      }
    });
  }
}
