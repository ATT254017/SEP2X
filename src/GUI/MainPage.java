package GUI;/**
 * Created by filip on 26/05/2017.
 */

import Client.ClientControl;
import GUI.Menubar.MenubarMain;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

public class MainPage extends Application
{
   private ClientControl controller;
   private Stage window;
   private Scene scene;
   //main page = true, search results = false;
   private boolean state;
   private boolean logedIn;

   private ScrollPane scrollwindow;
   private ItemListPane r1;
   private ItemListPane r2;

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
      logedIn = false;
      controller = ClientControl.getInstance();
      try
      {
         controller.setServerConnectionDetails("localhost", 9999);
      }
      catch (Exception e)
      {
         System.out.println("Error: no connection!");
      }
      RegisterPage registerPage = new RegisterPage();
      LogInPage logInPage = new LogInPage(this, registerPage);





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
         titleBar.getChildren().addAll(title, buttonBox);
         titleBar.setPadding(new Insets(0,0,20,0));

         //Search bar
         TextField searchBar = new TextField();
         searchBar.setPromptText("Search");
         searchBar.setPrefWidth(1000);
         searchBar.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

         //Search category box
         ChoiceBox<String> categoryList = new ChoiceBox<>();
         categoryList.getItems().add("Categories");
         categoryList.setValue("Categories");

         ClientControl.getInstance().getCategories(null, (status, categories) ->
         {
            for(int i = 0; i <  categories.size(); i++)
            {
               categoryList.getItems().add(categories.get(i).getCategoryName());
            }
         });


         categoryList.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) ->
         {
            if(!(newValue.equals("Categories")))
            {
               System.out.println(newValue);
            }
         });

         HBox searchBox = new HBox(5);
         searchBox.setPrefWidth(Double.MAX_VALUE);
         searchBox.setAlignment(Pos.CENTER_LEFT);
         searchBox.getChildren().addAll(searchBar, categoryList);


      VBox top = new VBox();
      top.setAlignment(Pos.CENTER);
      top.getChildren().addAll(titleBar, searchBox);
      top.setPadding(new Insets(0,30,20,30));


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      //Center

         //Menu bar
         MenubarMain menuBar = new MenubarMain(this);



         //Scroll window
         r1 = new ItemListPane();
         r2 = new ItemListPane();


         scrollwindow = new ScrollPane();
         scrollwindow.setContent(r1);

      VBox center = new VBox();
      center.getChildren().addAll(menuBar, scrollwindow);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


      //Left & Right
         VBox left = new VBox();
         VBox right = new VBox();


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      //Open log in page
      titleButton1.setOnAction(event ->
      {
         if(logedIn)
         {
            System.out.println("nope");
         }
         else
         {
            logInPage.display();
         }
      });

      //Open register page
      titleButton2.setOnAction(event ->
      {
         registerPage.display();
      });

      searchBar.setOnKeyPressed(new EventHandler<KeyEvent>()
      {
         @Override
         public void handle(KeyEvent ke)
         {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
               search();
            }
         }
      });

      //Toggle center scene
      /*titleButton1.setOnAction(event ->
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
      });*/








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

   private void search()
   {
      //controller.
      changeList();
   }

   private void changeList()
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
      }
