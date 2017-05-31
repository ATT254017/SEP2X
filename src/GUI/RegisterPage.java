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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterPage
{

   Stage window;
   Scene scene;
   Button button;

   public void display()
   {
      window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Register new account");

      //Title
      Label title = new Label("Register Account");
      title.setMaxWidth(Double.MAX_VALUE);
      title.setFont(new Font("Verdana", 74));
      title.setAlignment(Pos.CENTER);
      title.setPadding(new Insets(50, 0, 50, 0));

      //Username field
      HBox usernameBox = new HBox();
      usernameBox.setAlignment(Pos.CENTER);

      TextField usernameInput = new TextField();
      usernameInput.setPrefWidth(400);
      usernameInput.setPrefHeight(30);

      usernameBox.setPadding(new Insets(0, 0, 25, 0));
      usernameInput.setPromptText("Username");
      usernameBox.getChildren().addAll(usernameInput);

      //Password field
      HBox passwordBox = new HBox();
      passwordBox.setAlignment(Pos.CENTER);

      TextField passwordInput = new TextField();
      passwordInput.setPrefWidth(400);
      passwordInput.setPrefHeight(30);

      passwordBox.setPadding(new Insets(0, 0, 25, 0));
      passwordInput.setPromptText("Password");
      passwordBox.getChildren().addAll(passwordInput);

      //Email field
      HBox emailBox = new HBox();
      emailBox.setAlignment(Pos.CENTER);

      TextField emailInput = new TextField();
      emailInput.setPrefWidth(400);
      emailInput.setPrefHeight(30);

      emailBox.setPadding(new Insets(0, 0, 25, 0));
      emailInput.setPromptText("Email");
      emailBox.getChildren().addAll(emailInput);

      //First & Last name field
      HBox nameBox = new HBox(10);
      nameBox.setAlignment(Pos.CENTER);

      TextField firstNameInput = new TextField();
      firstNameInput.setPrefWidth(195);
      firstNameInput.setPrefHeight(30);

      TextField lastNameInput = new TextField();
      lastNameInput.setPrefWidth(195);
      lastNameInput.setPrefHeight(30);

      nameBox.setPadding(new Insets(0, 0, 0, 0));
      firstNameInput.setPromptText("First name");
      lastNameInput.setPromptText("List name");
      nameBox.getChildren().addAll(firstNameInput, lastNameInput);

      //Address field
      HBox addressBox = new HBox();
      addressBox.setAlignment(Pos.CENTER);

      TextField addressInput = new TextField();
      addressInput.setPrefWidth(400);
      addressInput.setPrefHeight(30);

      addressBox.setPadding(new Insets(0, 0, 25, 0));
      addressInput.setPromptText("Address");
      addressBox.getChildren().addAll(addressInput);

      //Phone number field
      HBox phoneNumberBox = new HBox();
      phoneNumberBox.setAlignment(Pos.CENTER);

      TextField phoneNumberInput = new TextField();
      phoneNumberInput.setPrefWidth(400);
      phoneNumberInput.setPrefHeight(30);

      phoneNumberBox.setPadding(new Insets(0, 0, 25, 0));
      phoneNumberInput.setPromptText("Phone Number");
      phoneNumberBox.getChildren().addAll(addressInput);

      //Sex and birthday field
      HBox snhBox = new HBox(3);
      snhBox.setAlignment(Pos.CENTER);

      Label gender = new Label("Gender");

      ChoiceBox<String> genderSelector = new ChoiceBox<>();
      genderSelector.getItems().add("Male");
      genderSelector.getItems().add("Female");
      genderSelector.setValue("Male");
      genderSelector.get

      TextField dayInput = new TextField();
      dayInput.setPrefWidth(60);
      dayInput.setPrefHeight(30);
      dayInput.setPromptText("DD");

      TextField monthInput = new TextField();
      monthInput.setPrefWidth(60);
      monthInput.setPrefHeight(30);
      monthInput.setPromptText("MM");

      TextField yearInput = new TextField();
      yearInput.setPrefWidth(60);
      yearInput.setPrefHeight(30);
      yearInput.setPromptText("YYYY");

      snhBox.setPadding(new Insets(0, 0, 25, 0));
      snhBox.getChildren().addAll(gender, genderSelector, dayInput, monthInput, yearInput);










      //Log in Button
      Button logInButton = new Button("Log in");
      logInButton.setFont(new Font("Verdana", 30));
      logInButton.setMaxWidth(200);

      //Register
      Label registerLabel = new Label("Don't have an account");
      registerLabel.setFont(new Font("Verdana", 30));
      registerLabel.setPadding(new Insets(80, 0, 20, 0));

      Button registerButton = new Button("Register");
      registerButton.setFont(new Font("Verdana", 30));
      registerButton.setMaxWidth(200);






      //Center
      VBox center = new VBox();
      center.getChildren().addAll(title, usernameBox, passwordBox, emailBox, nameBox, addressBox, phoneNumberBox, snhBox, logInButton, registerLabel, registerButton);
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
      scene = new Scene(layout, 750, 750);
      window.setScene(scene);

      //window.showAndWait();
      window.showAndWait();
   }
}
