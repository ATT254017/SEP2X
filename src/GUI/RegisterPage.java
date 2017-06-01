package GUI;/**
 * Created by filip on 24/05/2017.
 */

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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterPage
{

   private Stage window;
   private Scene scene;

   private TextField usernameInput;
   private TextField passwordInput;
   private TextField emailInput;
   private TextField firstNameInput;
   private TextField lastNameInput;
   private TextField addressInput;
   private TextField phoneNumberInput;
   private ChoiceBox<String> genderSelector;
   private TextField dayInput;
   private TextField monthInput;
   private TextField yearInput;

   private Label errorMessage;
   private Label errorMessage2;

   public void display()
   {
      window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Register new account");

      errorMessage = new Label("Username is already taken");
      errorMessage.setVisible(false);
      errorMessage.setTextFill(Color.web("#FF0000"));
      errorMessage.setPadding(new Insets(0, 0, 10, 0));

      errorMessage2 = new Label("Incorrect input in highlighted fields");
      errorMessage2.setVisible(false);
      errorMessage2.setTextFill(Color.web("#FF0000"));
      errorMessage2.setPadding(new Insets(0, 0, 10, 0));

      //Title
      Label title = new Label("Register Account");
      title.setMaxWidth(Double.MAX_VALUE);
      title.setFont(new Font("Verdana", 60));
      title.setAlignment(Pos.CENTER);
      title.setPadding(new Insets(50, 0, 20, 0));

      //Username field
      HBox usernameBox = new HBox();
      usernameBox.setAlignment(Pos.CENTER);

      usernameInput = new TextField();
      usernameInput.setPrefWidth(400);
      usernameInput.setPrefHeight(30);
      usernameInput.setPromptText("Username");
      usernameInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      usernameBox.setPadding(new Insets(0, 0, 25, 0));
      usernameBox.getChildren().addAll(usernameInput);

      //Password field
      HBox passwordBox = new HBox();
      passwordBox.setAlignment(Pos.CENTER);

      passwordInput = new TextField();
      passwordInput.setPrefWidth(400);
      passwordInput.setPrefHeight(30);
      passwordInput.setPromptText("Password");
      passwordInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      passwordBox.setPadding(new Insets(0, 0, 25, 0));
      passwordBox.getChildren().addAll(passwordInput);

      //Email field
      HBox emailBox = new HBox();
      emailBox.setAlignment(Pos.CENTER);

      emailInput = new TextField();
      emailInput.setPrefWidth(400);
      emailInput.setPrefHeight(30);
      emailInput.setPromptText("Email");
      emailInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      emailBox.setPadding(new Insets(0, 0, 25, 0));
      emailBox.getChildren().addAll(emailInput);

      //First & Last name field
      HBox nameBox = new HBox(10);
      nameBox.setAlignment(Pos.CENTER);

      firstNameInput = new TextField();
      firstNameInput.setPrefWidth(195);
      firstNameInput.setPrefHeight(30);
      firstNameInput
            .setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      lastNameInput = new TextField();
      lastNameInput.setPrefWidth(195);
      lastNameInput.setPrefHeight(30);
      lastNameInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      nameBox.setPadding(new Insets(0, 0, 25, 0));
      firstNameInput.setPromptText("First name");
      lastNameInput.setPromptText("List name");
      nameBox.getChildren().addAll(firstNameInput, lastNameInput);

      //Address field
      HBox addressBox = new HBox();
      addressBox.setAlignment(Pos.CENTER);

      addressInput = new TextField();
      addressInput.setPrefWidth(400);
      addressInput.setPrefHeight(30);
      addressInput.setPromptText("Address");
      addressInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      addressBox.setPadding(new Insets(0, 0, 25, 0));
      addressBox.getChildren().addAll(addressInput);

      //Phone number field
      HBox phoneNumberBox = new HBox();
      phoneNumberBox.setAlignment(Pos.CENTER);

      phoneNumberInput = new TextField();
      phoneNumberInput.setPrefWidth(400);
      phoneNumberInput.setPrefHeight(30);
      phoneNumberInput.setPromptText("Phone Number");
      phoneNumberInput
            .setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      phoneNumberBox.setPadding(new Insets(0, 0, 25, 0));
      phoneNumberBox.getChildren().addAll(phoneNumberInput);

      //Gender field
      HBox snhBox = new HBox(3);
      snhBox.setAlignment(Pos.CENTER);

      Label gender = new Label("Gender");

      genderSelector = new ChoiceBox<>();
      genderSelector.getItems().add("Male");
      genderSelector.getItems().add("Female");
      genderSelector.setValue("Male");

      HBox genderbox = new HBox(3);
      genderbox.setAlignment(Pos.CENTER);
      genderbox.setPadding(new Insets(0, 15, 0, 0));
      genderbox.getChildren().addAll(gender, genderSelector);

      //Birthday
      Label birthday = new Label("Birthday");

      dayInput = new TextField();
      dayInput.setPrefWidth(60);
      dayInput.setPrefHeight(30);
      dayInput.setPromptText("DD");
      dayInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      monthInput = new TextField();
      monthInput.setPrefWidth(60);
      monthInput.setPrefHeight(30);
      monthInput.setPromptText("MM");
      monthInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      yearInput = new TextField();
      yearInput.setPrefWidth(60);
      yearInput.setPrefHeight(30);
      yearInput.setPromptText("YYYY");
      yearInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      snhBox.setPadding(new Insets(0, 0, 25, 0));
      snhBox.getChildren()
            .addAll(genderbox, birthday, dayInput, monthInput, yearInput);

      //Register Button
      Button registerButton = new Button("Register");
      registerButton.setFont(new Font("Verdana", 24));
      registerButton.setMaxWidth(190);
      registerButton.setOnAction(event -> checkRegistration());

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
      buttonbox.getChildren().addAll(registerButton, cancelButton);

      //Center
      VBox center = new VBox();
      center.getChildren()
            .addAll(title, errorMessage, errorMessage2, usernameBox,
                  passwordBox, emailBox, nameBox, addressBox, phoneNumberBox,
                  snhBox, buttonbox);
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
      usernameInput.clear();
      passwordInput.clear();
      emailInput.clear();
      firstNameInput.clear();
      lastNameInput.clear();
      addressInput.clear();
      phoneNumberInput.clear();
      dayInput.clear();
      monthInput.clear();
      yearInput.clear();
      genderSelector.setValue("Male");
   }

   private void checkRegistration()
   {
      boolean correct = false;

      if (usernameInput.getText().equals("") || usernameInput.getText()
            .equals(null))
      {
         usernameInput.setStyle("-fx-border-color: red;");
         correct = true;
      }

      if (passwordInput.getText().equals("") || passwordInput.getText()
            .equals(null))
      {
         passwordInput.setStyle("-fx-border-color: red;");
         correct = true;
      }

      if (emailInput.getText().equals("") || emailInput.getText().equals(null))
      {
         emailInput.setStyle("-fx-border-color: red;");
         correct = true;
      }

      if (firstNameInput.getText().equals("") || firstNameInput.getText()
            .equals(null))
      {
         firstNameInput.setStyle("-fx-border-color: red;");
         correct = true;
      }

      if (lastNameInput.getText().equals("") || lastNameInput.getText()
            .equals(null))
      {
         lastNameInput.setStyle("-fx-border-color: red;");
         correct = true;
      }

      if (addressInput.getText().equals("") || addressInput.getText()
            .equals(null))
      {
         addressInput.setStyle("-fx-border-color: red;");
         correct = true;
      }

      if (phoneNumberInput.getText().equals("") || phoneNumberInput.getText()
            .equals(null))
      {
         phoneNumberInput.setStyle("-fx-border-color: red;");
         correct = true;
      }
      else
      {
         try
         {
            int phoneNumber = Integer.parseInt(phoneNumberInput.getText());
         }
         catch (NumberFormatException e)
         {
            phoneNumberInput.setStyle("-fx-border-color: red;");
            correct = true;
         }
      }

      if (dayInput.getText().equals("") || dayInput.getText().equals(null))
      {
         dayInput.setStyle("-fx-border-color: red;");
         correct = true;
      }
      else
      {
         try
         {
            int day = Integer.parseInt(dayInput.getText());
         }
         catch (NumberFormatException e)
         {
            dayInput.setStyle("-fx-border-color: red;");
            correct = true;
         }
      }

      if (monthInput.getText().equals("") || monthInput.getText().equals(null))
      {
         monthInput.setStyle("-fx-border-color: red;");
         correct = true;
      }
      else
      {
         try
         {
            int month = Integer.parseInt(monthInput.getText());
         }
         catch (NumberFormatException e)
         {
            monthInput.setStyle("-fx-border-color: red;");
            correct = true;
         }
      }

      if (yearInput.getText().equals("") || yearInput.getText().equals(null))
      {
         yearInput.setStyle("-fx-border-color: red;");
         correct = true;
      }
      else
      {
         try
         {
            int year = Integer.parseInt(yearInput.getText());
         }
         catch (NumberFormatException e)
         {
            yearInput.setStyle("-fx-border-color: red;");
            correct = true;
         }
      }

   }
}
