package GUI;/**
 * Created by filip on 01/06/2017.
 */

import Model.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ItemPane
{
   private Item item;
   private Stage window;
   private Scene scene;


   public ItemPane(Item item)
   {
      this.item = item;
   }

   public void display()
   {
      window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Log In");

      Font font1 = new Font("Arial", 45);
      Font font2 = new Font("Arial", 35);

      //Left
      Rectangle r1 = new Rectangle(50, 650);
      r1.setFill(Color.valueOf("#DDDDDD"));
      Rectangle r2 = new Rectangle(50, 650);
      r2.setFill(Color.valueOf("#DDDDDD"));

      //Right

      Label name = new Label(item.getItemName());
      name.setFont(font1);
      name.setPadding(new Insets(0, 0, 10, 0));

      Rectangle underline = new Rectangle(900, 2);
      underline.setFill(Color.valueOf("#BBBBBB"));

      Label price = new Label("Price: " + String.format( "%.2f", item.getItemPrice() ) + "dkk");
      price.setPadding(new Insets(5, 0, 5, 0));
      price.setFont(font2);
      Label quantity = new Label("Quantity: " + item.getCurrentRemainingQuantity());
      quantity.setPadding(new Insets(5, 0, 5, 0));
      quantity.setFont(font2);

      Label category = new Label("Category: " + item.getItemCategory().getCategoryName());
      category.setPadding(new Insets(5, 0, 5, 0));
      category.setFont(font2);

      Label description = new Label("Description: " + item.getDescription());
      description.setPadding(new Insets(5, 0, 5, 0));
      description.setFont(font2);

      VBox box2 = new VBox(5);
      box2.getChildren().addAll(name, underline, price, quantity, category, description);
      box2.setPadding(new Insets(0, 10, 20, 20));
      box2.setMaxWidth(Double.MAX_VALUE);

      Rectangle underline2 = new Rectangle(1000, 3);
      underline2.setFill(Color.valueOf("#DDDDDD"));

      HBox box = new HBox(20);
      box.getChildren().addAll(r1, box2, r2);
      box.setPadding(new Insets(5, 0, 5, 0));

      VBox layout = new VBox(10);
      layout.getChildren().addAll(box, underline2);
      layout.setPadding(new Insets(10, 0, 10, 0));

      //Scene and window
      scene = new Scene(layout, 1000, 700);
      window.setScene(scene);

      window.showAndWait();

   }

}
