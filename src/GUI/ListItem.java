package GUI;/**
 * Created by filip on 29/05/2017.
 */

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.text.html.ListView;

import Model.Item;
import javafx.scene.text.Font;

public class ListItem extends VBox
{
   private Item item;

   public ListItem(Item item)
   {
      Font font1 = new Font("Arial", 37);
      Font font2 = new Font("Arial", 28);
      //this.item = item;
      setPadding(new Insets(10, 0, 10, 0));

      //Left
      Rectangle r1 = new Rectangle(50, 200);
      r1.setFill(Color.valueOf("#DDDDDD"));
      Rectangle r2 = new Rectangle(50, 200);
      r2.setFill(Color.valueOf("#DDDDDD"));

      //Right

      Label name = new Label(item.getItemName());
      name.setFont(font1);
      name.setPadding(new Insets(0, 0, 10, 0));

      Rectangle underline = new Rectangle(985, 2);
      underline.setFill(Color.valueOf("#BBBBBB"));

      Label price = new Label("Price: " + item.getItemPrice() + "dkk");
      price.setPadding(new Insets(5, 0, 5, 0));
      price.setFont(font2);
      Label quantity = new Label("Quantity: " + item.getCurrentRemainingQuantity());
      quantity.setPadding(new Insets(5, 0, 5, 0));
      quantity.setFont(font2);

      Label category = new Label("category" + item.getItemCategory().getCategoryName());
      category.setPadding(new Insets(5, 0, 5, 0));
      category.setFont(font2);

      VBox box2 = new VBox(5);
      box2.getChildren().addAll(name, underline, price, quantity, category);
      box2.setPadding(new Insets(0, 10, 20, 20));
      box2.setMaxWidth(Double.MAX_VALUE);

      Rectangle underline2 = new Rectangle(1000, 3);
      underline2.setFill(Color.valueOf("#DDDDDD"));

      HBox box = new HBox(20);
      box.getChildren().addAll(r1, box2, r2);
      box.setPadding(new Insets(5, 0, 5, 0));

      getChildren().addAll(box, underline2);

   }
}
