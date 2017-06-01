package GUI;/**
 * Created by filip on 29/05/2017.
 */

import Model.*;
import Model.ItemState;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;

public class ItemListPane extends VBox
{
   ItemListPane()
   {
      setPadding(new Insets(10, 10, 0, 10));

   }

   public ListItem[] getItemArray()
   {
      ObservableList<Node> items = this.getChildren();
      ListItem[] out = new ListItem[items.size()];
      for (int i = 0; i < out.length; i++)
      {
         out[i] = (ListItem) items.get(i);
      }

      return out;
   }

   public void setItemArray(ListItem[] itemArray)
   {
      getChildren().clear();
      getChildren().addAll(itemArray);
   }

   public void addItem(ListItem item)
   {
      getChildren().add(item);
   }

   public void addBlankItem()
   {
      getChildren().add(new ListItem(new Item(0, "Blank Item", 0.00, 0)));
   }

   public void getFeaturedItems()
   {
      /*
      Items[] items = get featured item method;
      getChildren().clear();
      for(int i = 0; i < items.length; i++)
      {
         getChildren().add(new ListItem(items[i]) );
      }
      */
   }
}
