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

public class MainPage extends Application
{
   private boolean isSignedIn;

   private ClientControl controller;
   private Stage window;
   private Scene scene;

   //STATE main page = true, search results = false;
   private boolean isMain;
   private boolean logedIn;

   private ScrollPane scrollwindow;
   private ItemListPane featuredList;
   private ItemListPane searchList;

   private Button titleButton1;
   private Button titleButton2;

   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage)
   {
      window = primaryStage;
      window.setTitle("JavaFx");
      isMain = true;
      logedIn = false;
      isSignedIn = false;
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
      title.setOnMouseClicked(event -> returnToMain());

      //Buttons
      titleButton1 = new Button("Sign in");
      titleButton2 = new Button("Register");

      HBox buttonBox = new HBox(10);
      buttonBox.getChildren().addAll(titleButton1, titleButton2);
      buttonBox.setPadding(new Insets(35, 0, 0, 0));

      //Title & buttons row
      HBox titleBar = new HBox();
      titleBar.getChildren().addAll(title, buttonBox);
      titleBar.setPadding(new Insets(0, 0, 20, 0));

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
         for (int i = 0; i < categories.size(); i++)
         {
            categoryList.getItems().add(categories.get(i).getCategoryName());
         }
      });

      categoryList.getSelectionModel().selectedItemProperty()
            .addListener((v, oldValue, newValue) ->
            {
               if (!(newValue.equals("Categories")))
               {
                  System.out.println("Category set to: " + newValue);
               }
            });

      HBox searchBox = new HBox(5);
      searchBox.setPrefWidth(Double.MAX_VALUE);
      searchBox.setAlignment(Pos.CENTER_LEFT);
      searchBox.getChildren().addAll(searchBar, categoryList);

      VBox top = new VBox();
      top.setAlignment(Pos.CENTER);
      top.getChildren().addAll(titleBar, searchBox);
      top.setPadding(new Insets(0, 30, 20, 30));

      ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      //Center

      //Menu bar
      MenubarMain menuBar = new MenubarMain(this);

      //Scroll window
      featuredList = new ItemListPane();
      featuredList.addBlankItem();
      searchList = new ItemListPane();

      scrollwindow = new ScrollPane();
      scrollwindow.setContent(featuredList);

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
         if (logedIn)
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
               search(searchBar, categoryList);
            }
         }
      });

      //Toggle center scene
      /*titleButton1.setOnAction(event ->
      {
         if(isMain)
         {
            scrollwindow.setContent(searchList);
            toggleState();
         }
         else
         {
            scrollwindow.setContent(featuredList);
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

   private void returnToMain()
   {
      if(!(isMain))
      {
         changeList();
         System.out.println("2");
      }

   }

   private void search(TextField searchbar, ChoiceBox categoryBox)

   {
      if (!(searchbar.getText().equals("")) || !(searchbar.getText() != null))
      {

         changeList();
      }
      System.out.println(searchbar.getText());

   }

   public void searchCategory(String category)

   {
      //
      changeList();
      System.out.println("Search category: " + category);

   }

   private void changeList()
   {
      if (isMain)
      {
         scrollwindow.setContent(searchList);
         toggleState();
      }
      else
      {
         scrollwindow.setContent(featuredList);
         toggleState();
      }
   }

   private void toggleState()
   {
      if (isMain)
      {
         isMain = false;
      }
      else
      {
         isMain = true;
      }
   }

   public void signIn()
   {
      titleButton1.setText("Sell Item");
      titleButton2.setText("Sign Out");
      isSignedIn = true;
   }

   public void signOut()
   {
      titleButton1.setText("Sign In");
      titleButton2.setText("Register");
      isSignedIn = false;
   }

   public void print(String text)
   {
      System.out.println(text);
   }
}
