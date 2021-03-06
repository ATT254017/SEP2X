package GUI;/**
 * Created by filip on 01/06/2017.
 */

import Client.ClientControl;
import Model.BuyItemStatus;
import Model.Item;
import Model.MethodStatus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ItemPane
{
   private Item item;
   private Stage window;
   private Scene scene;
   private VBox box2;

   private Label msg;


   public ItemPane(Item item)
   {
      this.item = item;
   }

   public void display()
   {
      window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle(item.getItemName());

      Font font1 = new Font("Arial", 45);
      Font font2 = new Font("Arial", 35);

      //Left
      Rectangle r1 = new Rectangle(50, 350);
      r1.setFill(Color.valueOf("#DDDDDD"));
      Rectangle r2 = new Rectangle(50, 350);
      r2.setFill(Color.valueOf("#DDDDDD"));

      //Right

      Label name = new Label(item.getItemName());
      name.setFont(font1);
      name.setPadding(new Insets(0, 0, 10, 0));

      Rectangle underline = new Rectangle(800, 2);
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

      //Quantity box
      Label quantityLabel = new Label("Select the amount you want to buy");
      ChoiceBox<String> quantityChoiceBox = new ChoiceBox<>();
      for(int i = 1; i < item.getCurrentRemainingQuantity()+1; i++)
      {
         quantityChoiceBox.getItems().add("" + i);
      }

      try
      {
         quantityChoiceBox.setValue("1");
      }
      catch (Exception e)
      {
         System.out.println("Item doesn't have stock");
      }

      Text message = new Text("");
      message.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");
      msg = new Label();
      msg.setVisible(false);

      HBox quantityBox = new HBox(10);
      quantityBox.getChildren().addAll(quantityLabel, quantityChoiceBox);

      box2 = new VBox(5);
      box2.getChildren().addAll(name, underline, price, quantity, category, description, quantityChoiceBox, msg);
      box2.setPadding(new Insets(0, 10, 20, 20));
      box2.setMaxWidth(Double.MAX_VALUE);

      Rectangle underline2 = new Rectangle(890, 3);
      underline2.setFill(Color.valueOf("#DDDDDD"));


      HBox box = new HBox(20);
      box.getChildren().addAll(r1, box2, r2);
      box.setPadding(new Insets(5, 0, 5, 0));

      Button buyButton = new Button("Buy");
      buyButton.setFont(new Font("Arial", 28));
      buyButton.setOnAction(event ->
      {
         int q = Integer.parseInt(quantityChoiceBox.getSelectionModel().getSelectedItem().toString());
         ClientControl.getInstance().buyItem(item, q, (status, state) ->
         {
            buyResponse(status, state);
         });
      });
      Button cancelButton = new Button("Cancel");
      cancelButton.setFont(new Font("Arial", 28));
      cancelButton.setOnAction(event -> window.close());
      HBox buttonBox = new HBox(15);
      buttonBox.setAlignment(Pos.CENTER);
      buttonBox.getChildren().addAll(buyButton, cancelButton);

      VBox layout = new VBox(10);
      layout.getChildren().addAll(box, underline2, buttonBox);
      layout.setPadding(new Insets(10, 0, 10, 0));

      //Scene and window
      scene = new Scene(layout, 900, 500);
      window.setScene(scene);

      window.showAndWait();

   }

   private void buyResponse(MethodStatus status, BuyItemStatus state)
   {
      Platform.runLater( () ->
      {
         switch (status)
         {
            //1
            case SuccessfulInvocation:
            {
               switch (state)
               {
                  //1.1
                  case SuccessfullyBought:
                  {
                     msg.setText("Successfully bought item");
                     msg.setVisible(true);
                     msg.setTextFill(Color.web("#77ff42"));
                  };break;

                  //1.2
                  case AllowedQuantityExceeded:
                  {
                     msg.setText("Selected quantity you wish to buy is over the actual stock");
                     msg.setVisible(true);
                     msg.setTextFill(Color.web("#ff9900"));
                  };break;

                  //1.3
                  case ItemNotFoundOrCancelled:
                  {
                     msg.setText("Item could not be found!");
                     msg.setTextFill(Color.web("#00ff00"));
                  };break;
               }
            }; break;

            //2
            case Unauthorized:
            {
               msg.setText("To buy an item you need to sign in!");
               msg.setVisible(true);
               msg.setTextFill(Color.web("#ff9900"));
            };break;

            //3
            case TimedOut:
            {
               msg.setText("Connection with server timed out!");
               msg.setVisible(true);
               msg.setTextFill(Color.web("#ff0000"));
            };break;

            //4
            case UnknownError:
            {
               msg.setText("Unknown error!");
               msg.setVisible(true);
               msg.setTextFill(Color.web("#ff0000"));
            };break;
         }
      });


   }

}
