package GUI.Menubar;/**
 * Created by filip on 30/05/2017.
 */

import Client.ClientControl;
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

   public MenubarMain(MainPage parent)
   {
      setMaxWidth(Double.MAX_VALUE);

      categoryList = new ChoiceBox<>();
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

      this.getChildren()
            .addAll(books, clothes, videoGames, computers, categoryList);

      books.setPrefWidth(230);
      clothes.setPrefWidth(230);
      videoGames.setPrefWidth(230);
      computers.setPrefWidth(230);
      categoryList.setPrefWidth(260);

   }
}
