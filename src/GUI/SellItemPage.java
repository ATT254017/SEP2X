package GUI;/**
 * Created by filip on 24/05/2017.
 */

import Client.ClientControl;
import Model.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SellItemPage
{

   private Stage window;
   private Scene scene;

   private TextField nameInput;
   private TextField descInput;
   private TextField quantityInput;
   private TextField priceInput;
   private ChoiceBox<String> categoryInput;
   private Category defaultCategory;

   private Label errorMessage;

   public void display()
   {
      window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Sell an item");

      errorMessage = new Label("");
      errorMessage.setVisible(false);

      //Title
      Label title = new Label("Sell an item");
      title.setMaxWidth(Double.MAX_VALUE);
      title.setFont(new Font("Verdana", 60));
      title.setAlignment(Pos.CENTER);
      title.setPadding(new Insets(50, 0, 20, 0));

      //Name field
      nameInput = new TextField();
      nameInput.setPrefWidth(400);
      nameInput.setPrefHeight(30);
      nameInput.setPromptText("Item name");
      nameInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");
      nameInput.setPadding(new Insets(0, 0, 25, 0));

      //Description field
      descInput = new TextField();
      descInput.setPrefWidth(400);
      descInput.setPrefHeight(40);
      descInput.setPromptText("Password");
      descInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");
      descInput.setPadding(new Insets(0, 0, 25, 0));

      //Quantity field
      quantityInput = new TextField();
      quantityInput.setPrefWidth(400);
      quantityInput.setPrefHeight(30);
      quantityInput.setPromptText("Email");
      quantityInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");
      quantityInput.setPadding(new Insets(0, 0, 25, 0));

      //First & Last name field
      HBox quantityAndPriceBox = new HBox(10);
      quantityAndPriceBox.setAlignment(Pos.CENTER);

      quantityInput = new TextField();
      quantityInput.setPrefWidth(195);
      quantityInput.setPrefHeight(30);
      quantityInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");
      quantityInput.setPromptText("First name");

      priceInput = new TextField();
      priceInput.setPrefWidth(195);
      priceInput.setPrefHeight(30);
      priceInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");
      priceInput.setPromptText("List name");

      quantityAndPriceBox.setPadding(new Insets(0, 0, 25, 0));
      quantityAndPriceBox.getChildren().addAll(quantityInput, priceInput);

      //Category choicebox
      HBox categoryBox = new HBox(3);
      categoryBox.setAlignment(Pos.CENTER);

      Label categoryLabel = new Label("Category");

      categoryInput = new ChoiceBox<>();
      defaultCategory = new Category(-1, "Categories");
      categoryInput.getItems().add(defaultCategory.getCategoryName());
      categoryInput.setValue(defaultCategory.getCategoryName());
      ClientControl.getInstance().getCategories(null, (status, categories) ->
      {
         for (int i = 0; i < categories.size(); i++)
         {
            categoryInput.getItems().add(categories.get(i).getCategoryName());
         }
      });
      

      HBox genderbox = new HBox(3);
      genderbox.setAlignment(Pos.CENTER);
      genderbox.setPadding(new Insets(0, 15, 0, 0));
      genderbox.getChildren().addAll(categoryLabel, categoryInput);



      //Sell Button
      Button sellButton = new Button("Sell");
      sellButton.setFont(new Font("Verdana", 24));
      sellButton.setMaxWidth(190);
      sellButton.setOnAction(event -> {
    	  if(checkRegistration())
    		  register();
      });

      //Cancel button
      Button cancelButton = new Button("Cancel");
      cancelButton.setFont(new Font("Verdana", 24));
      cancelButton.setMaxWidth(190);
      cancelButton.setOnAction(event ->
      {
         window.close();
      });

      HBox buttonbox = new HBox(50);
      buttonbox.setPadding(new Insets(20, 0, 0, 0));
      buttonbox.setAlignment(Pos.CENTER);
      buttonbox.getChildren().addAll(sellButton, cancelButton);

      //Center
      VBox center = new VBox();
      center.getChildren().addAll(title, errorMessage, nameInput, descInput, quantityAndPriceBox, categoryBox, buttonbox);
      center.setAlignment(Pos.TOP_CENTER);
      center.setMinWidth(480);

      //Left & right
      VBox left = new VBox();
      VBox right = new VBox();
      left.setMinWidth(25);
      right.setMinWidth(25);

      BorderPane layout = new BorderPane();
      layout.setCenter(center);
      layout.setLeft(left);
      layout.setRight(right);

      //Scene and window
      scene = new Scene(layout, 600, 700);
      window.setScene(scene);

      //window.showAndWait();
      window.showAndWait();
   }

   private void resetWindow()
   {
      nameInput.clear();
      descInput.clear();
      quantityInput.clear();
      priceInput.clear();
      categoryInput.setValue("Category");
   }
   
   private void register()
   {

	   ClientControl.getInstance().insertItem(nameInput.getText(), descInput.getText(),
            Integer.parseInt(quantityInput.getText()), Double.parseDouble(priceInput.getText()),
            new Category(0, categoryInput.getSelectionModel().getSelectedItem()),
            (status, state) ->
            {
               sellResponse(status, state, errorMessage);
            });

   }

   private boolean checkRegistration()
   {
	   
      boolean correct = true;

      if (nameInput.getText().equals("") || nameInput.getText().equals(null))
      {
         nameInput.setStyle("-fx-border-color: red;");
         correct = false;
      }

      if (descInput.getText().equals("") || descInput.getText()
            .equals(null))
      {
         descInput.setStyle("-fx-border-color: red;");
         correct = false;
      }

      if (quantityInput.getText().equals("") || quantityInput.getText()
            .equals(null))
      {
         quantityInput.setStyle("-fx-border-color: red;");
         correct = false;
      }
      else
      {
         try
         {
            int quantity = Integer.parseInt(quantityInput.getText());
         }
         catch (NumberFormatException e)
         {
            priceInput.setStyle("-fx-border-color: red;");
            correct = false;
         }
      }

      if (priceInput.getText().equals("") || priceInput.getText()
            .equals(null))
      {
         priceInput.setStyle("-fx-border-color: red;");
         correct = false;
      }
      else
      {
         try
         {
            int price = Integer.parseInt(priceInput.getText());
         }
         catch (NumberFormatException e)
         {
            priceInput.setStyle("-fx-border-color: red;");
            correct = false;
         }
      }

      return correct;
   }

   private void sellResponse(MethodStatus status, InsertItemStatus state, Label msg)
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
                  case Success:
                  {
                     msg.setText("Successfully bought item");
                     msg.setVisible(true);
                     msg.setTextFill(Color.web("#77ff42"));
                  };break;

                  //1.2
                  case InvalidInput:
                  {
                     msg.setText("Unknown error!");
                     msg.setVisible(true);
                     msg.setTextFill(Color.web("#ff0000"));
                  };break;
               }
            }; break;

            //2
            case Unauthorized:
            {
               msg.setText("To sell an item you need to sign in!");
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
