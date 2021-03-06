package GUI;/**
 * Created by filip on 26/05/2017.
 */

import Client.ClientControl;
import GUI.Menubar.MenubarMain;
import Model.Category;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

   private ScrollPane scrollwindow;
   private ItemListPane featuredList;
   private ItemListPane searchList;

   private Button titleButton1;
   private Button titleButton2;

   private SellItemPage sellItemPage;
   private LogInPage signInPage;
   private RegisterPage registerPage;
   private EventHandler<ActionEvent> titleButton1Action = event ->
   {
       if (isSignedIn)
       {
          sellItemPage.display();
       }
       else
       {
          signInPage.display();
       }
    };
    private EventHandler<ActionEvent> titleButton2Action = event ->
    {
       if(isSignedIn)
       {
          ClientControl.getInstance().signOut(() -> Platform.runLater(() -> signOut()));
       }
       else
       {
          registerPage.display();
       }
    };
    
    private Category defaultCategory;
    

   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage)
   {
      window = primaryStage;
      window.setTitle("Marketplace");
      isMain = true;
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
      registerPage = new RegisterPage();
      sellItemPage = new SellItemPage();
      signInPage = new LogInPage(this, registerPage);

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
      ChoiceBox<Category> categoryList = new ChoiceBox<>();
      defaultCategory = new Category(-1, "Categories");
      categoryList.getItems().add(defaultCategory);
      categoryList.setValue(defaultCategory);

      ClientControl.getInstance().getCategories(null, (status, categories) ->
      {
         for (int i = 0; i < categories.size(); i++)
         {
            categoryList.getItems().add(categories.get(i));
         }
      });

      /*categoryList.getSelectionModel().selectedItemProperty()
            .addListener((v, oldValue, newValue) ->
            {
               if (!(newValue.equals("Categories")))
               {
                  System.out.println("Category set to: " + newValue);
               }
            });*/

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
      titleButton1.setOnAction(titleButton1Action);

      //Open register page
      titleButton2.setOnAction(titleButton2Action);


      searchBar.setOnKeyPressed(new EventHandler<KeyEvent>()
      {
         @Override
         public void handle(KeyEvent ke)
         {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
            	Category searchCategory = categoryList.getValue() != defaultCategory ? categoryList.getValue() : null;
               search(searchBar.getText(), searchCategory);
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
      
      window.setOnCloseRequest(W -> ClientControl.getInstance().disconnect());

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

   public void search(String searchString, Category searchCategory)

   {

            //call server for list
            ClientControl.getInstance().getItems(searchCategory,
                  searchString, null, ((status, items) ->
            {
            	System.out.println(searchString);
            	System.out.println(searchCategory);
            	System.out.println(status);
            	System.out.println(items.size());
               Platform.runLater(() ->
               {
                  //clear searchList
                  searchList.getChildren().clear();

                  //add new items to searchList
                  for (int i = 0; i < items.size(); i++)
                  {
                	  System.out.println(items.get(i).getItemName());
                     searchList.addItem(items.get(i));
                  }

                  //change to search list
                  changeToSearch();
               });
            }));

            System.out.println("search method: " + searchString);


      
   }

   /*
   public void searchCategory(String category)

   {
      //call server for list
      ClientControl.getInstance().getItems(new Category(0, category), null, null, (status, items) ->
      {
         Platform.runLater(() ->
         {
            //clear searchList
            searchList.getChildren().clear();

            //add new items to searchList
            for(int i = 0; i < items.size(); i++)
            {
               searchList.addItem(items.get(i));
            }

            //change to search list
            changeToSearch();
         });

      });



      changeList();
      System.out.println("Search category: " + category);

   }
*/
   private void changeToSearch()
   {
      if(isMain)
      {
         changeList();
      }
   }

   private void changeList()
   {
      if (isMain)
      {
         scrollwindow.setContent(searchList);
         isMain = false;
      }
      else
      {
         scrollwindow.setContent(featuredList);
         isMain = true;
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
	   ClientControl.getInstance().signOut(() -> {});
      titleButton1.setText("Sign In");
      titleButton2.setText("Register");
      isSignedIn = false;
   }

   public void print(String text)
   {
      System.out.println(text);
   }
}
