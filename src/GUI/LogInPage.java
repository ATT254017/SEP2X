package GUI;/**
 * Created by filip on 24/05/2017.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class LogInPage
{

   Stage window;
   Scene scene;
   Button button;

   public void display()
   {
      window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Log In");

      //Title
      Label title = new Label("Marketplace");
      title.setMaxWidth(Double.MAX_VALUE);
      title.setFont(new Font("Verdana", 96));
      title.setAlignment(Pos.CENTER);
      title.setPadding(new Insets(100, 0, 50, 0));

      //Username field
      HBox usernameBox = new HBox();
      usernameBox.setAlignment(Pos.CENTER);
      TextField usernameInput = new TextField();
      usernameInput.setPrefWidth(400);
      usernameInput.setPrefHeight(40);
      usernameBox.setPadding(new Insets(0, 0, 45, 0));
      usernameInput.setPromptText("Username");
      usernameBox.getChildren().addAll(usernameInput);

      //Password field
      HBox passwordBox = new HBox();
      passwordBox.setAlignment(Pos.CENTER);
      TextField passwordInput = new TextField();
      passwordInput.setPrefWidth(400);
      passwordInput.setPrefHeight(40);
      passwordBox.setPadding(new Insets(0, 0, 55, 0));
      passwordInput.setPromptText("Password");
      passwordBox.getChildren().addAll(passwordInput);

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
      center.getChildren().addAll(title, usernameBox, passwordBox, logInButton, registerLabel, registerButton);
      center.setAlignment(Pos.TOP_CENTER);
      center.setMinWidth(480);

      //Left & right
      VBox left = new VBox();
      VBox right = new VBox();
      left.setMinWidth(50);
      right.setMinWidth(50);

      BorderPane layout = new BorderPane();
      layout.setCenter(center);
      layout.setLeft(left);
      layout.setRight(right);

      //Scene and window
      scene = new Scene(layout, 700, 750);
      window.setScene(scene);

      //window.showAndWait();
      window.showAndWait();
   }
}
