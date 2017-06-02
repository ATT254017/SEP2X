package GUI.Menubar;/**
 * Created by filip on 30/05/2017.
 */

import java.util.HashMap;
import java.util.Map;

import Client.ClientControl;
import GUI.MainPage;
import Model.Category;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class MenubarMain extends HBox
{
   private MainPage parent;
   private ChoiceBox<Category> categoryList;
   private Category defaultCategory;

   public MenubarMain(MainPage parent)
   {
      setMaxWidth(Double.MAX_VALUE);
      this.parent = parent;

      categoryList = new ChoiceBox<>();
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

      categoryList.getSelectionModel().selectedItemProperty()
            .addListener((v, oldValue, newValue) ->
            {
               if (newValue != defaultCategory)
               {
                  //Category search
                  this.parent.search(null, newValue);
               }
            });

    
      Button booksButton = new Button("Books");
      booksButton.setOnAction(event -> parent.search(null, findCategory("books")));

      Button clothesButton = new Button("Clothes");
      clothesButton.setOnAction(event -> parent.search(null, findCategory("cloth")));

      Button videoGamesButton = new Button("Video Games");
      videoGamesButton.setOnAction(event -> parent.search(null, findCategory("video")));

      Button computersButton = new Button("Computers");
      computersButton.setOnAction(event -> parent.search(null, findCategory("consumer electronic")));

      this.getChildren()
            .addAll(booksButton, clothesButton, videoGamesButton, computersButton, categoryList);

      booksButton.setPrefWidth(230);
      clothesButton.setPrefWidth(230);
      videoGamesButton.setPrefWidth(230);
      computersButton.setPrefWidth(230);
      categoryList.setPrefWidth(260);

   }
   private Map<String, Category> findCategoryCache = new HashMap<>();
   private Category findCategory(String catName)
   {
	   if(findCategoryCache.containsKey(catName))
		   return findCategoryCache.get(catName);
	      for(Category category : categoryList.getItems())
	      {
	    	  if(category.getCategoryName().toLowerCase().contains(catName))
	    	  {
	    		  findCategoryCache.put(catName, category);
	    		  return category;
	    	  }
	      }
	      return defaultCategory;	      
   }
}
