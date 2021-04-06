package UI;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observer;
import java.util.Observable;
import downloadmanager.*;

public class DownloadPane extends Pane implements Observer {

  // Download
  private DownloadManager manager;
  private int index;
  private Download download;

  // State of the download
  private String dlState = "";

  // Percentage downloaded of the download
  private int dlPercentage = 0;

  // Int to know which color to choose
  private static int color = 0;

  // graphic elements
  private Button pause;
  private Button stop;
  private Text dl_name;
  private Text dl_url;
  private Text dl_state;
  private Pane Percentage;
  private Rectangle Downloaded;

  public DownloadPane(){}

  /** Create the pane of a download
  *
  * @param Pane
  * @param Download
  *
  * @return Pane
  */
  public DownloadPane(double prefWidth, double prefHeight, DownloadManager m, int i) throws FileNotFoundException{
    super();

    this.manager = m;
    this.index = i;
    this.download = manager.getDownloadList().get(index);

    this.setPrefWidth(prefWidth);
    this.setPrefHeight(prefHeight);

    ////////////////////
    if(DownloadPane.color == 0){
      this.setStyle("-fx-background-color: white;");
      DownloadPane.color = 1;
    }
    else{
      this.setStyle("-fx-background-color: rgb(200, 200, 200);");
      DownloadPane.color = 0;
    }
    ////////////////////
    ////////////////////
    pause = new Button();
    pause.setPrefWidth((prefWidth*5)/100);
    pause.setPrefHeight((prefHeight*50)/100);
    pause.setLayoutX((prefWidth*75)/100);
    pause.setLayoutY(((prefHeight*50)/100)-(pause.getPrefHeight()/2));

    FileInputStream pausef = new FileInputStream("src/main/resources/pause.png");
    Image pause_i = new Image(pausef, (prefWidth*5)/100, (prefHeight*50)/100, true, true);
    pause.setGraphic(new ImageView(pause_i));
    ButtonEvent.setPauseEvent(pause, manager, index);
    ////////////////////

    ////////////////////
    stop = new Button();
    stop.setPrefWidth((prefWidth*5)/100);
    stop.setPrefHeight((prefHeight*50)/100);
    stop.setLayoutX((prefWidth*85)/100);
    stop.setLayoutY(((prefHeight*50)/100)-(stop.getPrefHeight()/2));
    ButtonEvent.setCancelEvent(stop, manager, index);

    FileInputStream stopf = new FileInputStream("src/main/resources/stop.png");
    Image stop_i = new Image(stopf, (prefWidth*5)/100, (prefHeight*50)/100, true, true);
    stop.setGraphic(new ImageView(stop_i));
    ////////////////////

    ////////////////////
    dl_name = new Text();
    dl_name.setTextAlignment(TextAlignment.CENTER);
    dl_name.setText(download.getFileName());
    dl_name.setLayoutX((prefWidth*15)/100);
    dl_name.setLayoutY((prefHeight*45)/100);
    dl_name.setFont(Font.loadFont("file:src/main/resources/UbuntuMono-Regular.ttf", 20));
    ////////////////////

    ////////////////////
    dl_url = new Text();
    dl_url.setTextAlignment(TextAlignment.CENTER);
    dl_url.setText(download.getUrl().toString());
    dl_url.setLayoutX((prefWidth*15)/100);
    dl_url.setLayoutY((prefHeight*85)/100);
    dl_url.setFont(Font.loadFont("file:src/main/resources/UbuntuMono-Regular.ttf", 12));
    ////////////////////

    ////////////////////
    Circle logo = new Circle((prefWidth*7)/100, (prefHeight*50)/100, (prefWidth*4)/100);
    String format = new String();
    int l = download.getFileName().length();
    char c = download.getFileName().charAt(l-1);
    while(c != '.' && l > 0){
      format = c + format;
      l--;
      c = download.getFileName().charAt(l-1);
    }

    ////////////////////
    if(format.equals("png") || format.equals("jpg") || format.equals("jpeg") || format.equals("tif") || format.equals("gif")){
      FileInputStream imagef = new FileInputStream("src/main/resources/image.png");
      Image image_i = new Image(imagef);
      logo.setFill(new ImagePattern(image_i));
    }

    else if(format.equals("wav") || format.equals("au") || format.equals("mp3") || format.equals("oga") || format.equals("ram")){
      FileInputStream soundf = new FileInputStream("src/main/resources/sound.png");
      Image sound_i = new Image(soundf);
      logo.setFill(new ImagePattern(sound_i));
    }

    else if(format.equals("avi") || format.equals("mpg") || format.equals("mov") || format.equals("mkv") || format.equals("mp4")){
      FileInputStream videof = new FileInputStream("src/main/resources/video.png");
      Image video_i = new Image(videof);
      logo.setFill(new ImagePattern(video_i));
    }

    else if(format.equals("exe") || format.equals("msi") || format.equals("sh") || format.equals("com") || format.equals("bat")){
      FileInputStream execf = new FileInputStream("src/main/resources/exec.png");
      Image exec_i = new Image(execf);
      logo.setFill(new ImagePattern(exec_i));
    }

    else if(format.equals("jar") || format.equals("tar") || format.equals("zip") || format.equals("rar") || format.equals("7z")){
      FileInputStream archivef = new FileInputStream("src/main/resources/archive.png");
      Image archive_i = new Image(archivef);
      logo.setFill(new ImagePattern(archive_i));
    }

    else{
      FileInputStream docf = new FileInputStream("src/main/resources/doc.png");
      Image doc_i = new Image(docf);
      logo.setFill(new ImagePattern(doc_i));
    }
    ////////////////////
    dl_state = new Text();

    dl_state.setText(Download.STATES[download.getState()]);
    dl_state.setLayoutX(((prefWidth*55)/100));
    dl_state.setLayoutY((prefHeight*30)/100);
    dl_state.setFont(Font.loadFont("file:src/main/resources/UbuntuMono-Regular.ttf", 20));
    ////////////////////

    ////////////////////
    Percentage = new Pane();
    Percentage.setPrefWidth((prefWidth*15)/100);
    Percentage.setPrefHeight((prefHeight*10)/100);
    Percentage.setLayoutX((prefWidth*60)/100 - (Percentage.getPrefWidth()/2));
    Percentage.setLayoutY((prefHeight*60)/100);
    Percentage.setStyle("-fx-background-color: red;");

    Downloaded = new Rectangle();
    Downloaded.setHeight((prefHeight*10)/100);
    Downloaded.setFill(Color.GREEN);
    Percentage.getChildren().add(Downloaded);
    ////////////////////


    this.getChildren().addAll(pause, stop, dl_name, dl_url, logo, dl_state, Percentage);
  }
  ////////////////////

  public void update(Observable o, Object arg){
    this.dl_state.setText(Download.STATES[((Download)o).getState()]);
    this.Downloaded.setWidth((this.Percentage.getPrefWidth()*((Download)o).getPercentageDownloaded())/100);
  }
}
