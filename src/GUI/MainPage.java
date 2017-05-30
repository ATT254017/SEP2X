package GUI;/**
 * Created by filip on 26/05/2017.
 */

import GUI.Menubar.MenubarMain;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainPage extends Application
{
   private Stage window;
   private Scene scene;
   //main page = true, search results = false;
   private boolean state;

   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage)
   {
      window = primaryStage;
      window.setTitle("JavaFx");
      state = true;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      //Top

         //Title
         Label title = new Label("Marketplace");
         title.setMaxWidth(Double.MAX_VALUE);
         title.setFont(new Font("Verdana", 65));
         title.setAlignment(Pos.CENTER);
         title.setPadding(new Insets(0, 150, 0, 300));

         //Buttons
         Button titleButton1 = new Button("Sign in");
         Button titleButton2 = new Button("Register");

         HBox buttonBox = new HBox(10);
         buttonBox.getChildren().addAll(titleButton1, titleButton2);
         buttonBox.setPadding(new Insets(35,0,0,0));

         //Title & buttons row
         HBox titleBar = new HBox();
         titleBar.setAlignment(Pos.CENTER);
         titleBar.getChildren().addAll(title, buttonBox);
         titleBar.setPadding(new Insets(0,0,20,0));

         //Search bar
         TextField searchBar = new TextField();
         searchBar.setPromptText("Search");

      VBox top = new VBox();
      top.setAlignment(Pos.CENTER);
      top.getChildren().addAll(titleBar, searchBar);
      top.setPadding(new Insets(0,30,20,30));


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      //Center

         //Menu bar
         MenubarMain menuBar = new MenubarMain(this);



         //Scroll window
         ItemListPane r1 = new ItemListPane();
         ItemListPane r2 = new ItemListPane();


         ScrollPane scrollwindow = new ScrollPane();
         scrollwindow.setContent(r1);

      VBox center = new VBox();
      center.getChildren().addAll(menuBar, scrollwindow);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


      //Left & Right
         VBox left = new VBox();
         VBox right = new VBox();


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      //Toggle center scene
     titleButton1.setOnAction(event ->
      {
         if(state)
         {
            scrollwindow.setContent(r2);
            toggleState();
         }
         else
         {
            scrollwindow.setContent(r1);
            toggleState();
         }
      });
      //Change window scenes
     titleButton2.setOnAction(event ->
     {
        if(state)
        {
           r1.addBlankItem();
        }
        else
        {
           r2.addBlankItem();
        }
     });






///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


      //Layout
         BorderPane layout = new BorderPane();
         layout.setTop(top);
         layout.setCenter(center);
         layout.setLeft(left);
         layout.setRight(right);
         layout.setPadding(new Insets(25, 50, 50, 50));

      scene = new Scene(layout, 1280, 960);
      window.setScene(scene);
      window.show();
   }

   private void toggleState()
   {
      if (state)
      {
         state = false;
      }
      else
      {
         state = true;
      }
   }

   public void print(String text)
   {
      System.out.println(text);
   }

   public void changeScene()
   {

   }
}
