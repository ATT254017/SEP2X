package GUI.Menubar;/**
 * Created by filip on 30/05/2017.
 */

import GUI.MainPage;
import com.sun.glass.ui.*;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;

public class MenubarMain extends HBox
{
   private MainPage main;
   private ChoiceBox<String> categoryList;
   private String[] categoryStringList;

   public MenubarMain(MainPage parent)
   {
      setMaxWidth(Double.MAX_VALUE);

      categoryStringList = new String[]
            {
                  "Art", "Baby", "Books", "Business & Industrial", "Cameras & Photo", "Cell Phones & Accessories", "Clothing, Shoes & Accessories", "Coins & Paper Money", "Collectibles", "Computers/Tablets & Networking",
                  "Consumer Electronics", "Crafts", "Dolls & Bears", "DVDs & Movies", "Motors", "Gift Cards & Coupons", "Health & Beauty", "Home & Garden", "Jewelry & Watches", "Music",  "Musical Instruments & Gear", "Pet Supplies", "Specialty Services",
                  "Sporting Goods", "Tickets & Experiences", "Toys & Hobbies", "Video Games & Consoles", "Everything Else"
            };
      categoryList = new ChoiceBox<>();
      categoryList.getItems().add("Categories");
      categoryList.setValue("Categories");

      for (int i = 0; i < categoryStringList.length; i++)
      {
         categoryList.getItems().add(categoryStringList[i]);
      }

      categoryList.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) ->
      {
         if(!(newValue.equals("Categories")))
         {
            System.out.println(newValue);
         }
      });



      Button books = new Button("Books");
      books.setOnAction(event -> System.out.println("Books"));


      Button clothes = new Button("Clothes");
      clothes.setOnAction(event -> System.out.println("Clothes"));

      Button videoGames = new Button("Video Games");
      videoGames.setOnAction(event -> System.out.println("Video Games"));


      Button computers = new Button("Computers");
      computers.setOnAction(event -> System.out.println("Computers"));

      this.getChildren().addAll(books, clothes, videoGames, computers, categoryList);

      books.setPrefWidth(230);
      clothes.setPrefWidth(230);
      videoGames.setPrefWidth(230);
      computers.setPrefWidth(230);
      categoryList.setPrefWidth(260);




   }
}
