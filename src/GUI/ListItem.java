package GUI;/**
 * Created by filip on 29/05/2017.
 */

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import javax.swing.text.html.ListView;


public class ListItem extends VBox
{
   private Item item;

   public ListItem()
   {

      //this.item = item;
      setPadding(new Insets(10,0,10,0));
      setStyle("-fx-background-color: #FF0000;");

      //Left
      Rectangle r1 = new Rectangle(50, 150);
      Rectangle r2 = new Rectangle(50, 150);

      //Right

         Label name = new Label("Name");
         name.setPadding(new Insets(0, 0, 10, 0));

         Rectangle underline = new Rectangle(400, 2);

         Label price = new Label("Price");
         Label location = new Label("Location");
         Label condition = new Label("Condition");

      VBox box2 = new VBox(5);
      box2.getChildren().addAll(name, underline, price, location, condition);
      box2.setPadding(new Insets(10, 10, 20, 20));
      box2.setMaxWidth(Double.MAX_VALUE);

      Rectangle underline2 = new Rectangle(1000, 3);

      HBox box = new HBox(5);
      box.getChildren().addAll(r1, box2, r2);

      getChildren().addAll(box, underline2);
   }
}
