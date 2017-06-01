package GUI;/**
 * Created by filip on 24/05/2017.
 */

import Client.ClientControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.applet.Main;

import java.awt.*;

public class LogInPage
{

   private MainPage mainPage;
   private Stage window;
   private Scene scene;
   private RegisterPage register;
   private TextField passwordInput;
   private TextField usernameInput;
   private Label errorMessage;

   public LogInPage(MainPage mainPage, RegisterPage register)
   {
      this.mainPage = mainPage;
      this.register = register;
   }

   public void display()
   {
      window = new Stage();
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Log In");

      //Title
      Label title = new Label("Log in");
      title.setMaxWidth(Double.MAX_VALUE);
      title.setFont(new Font("Verdana", 60));
      title.setAlignment(Pos.CENTER);
      title.setPadding(new Insets(50, 0, 75, 0));

      //Error label
      errorMessage = new Label("Username and password are incorrect");
      errorMessage.setTextFill(Color.web("#FF0000"));
      errorMessage.setPadding(new Insets(0, 0, 10, 0));
      errorMessage.setVisible(false);

      //Username field
      HBox usernameBox = new HBox();
      usernameBox.setAlignment(Pos.CENTER);

      usernameInput = new TextField();
      usernameInput.setPrefWidth(400);
      usernameInput.setPrefHeight(50);
      usernameInput.setFont(new Font("Arial", 18));
      usernameInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      usernameBox.setPadding(new Insets(0, 0, 45, 0));
      usernameInput.setPromptText("Username");
      usernameBox.getChildren().addAll(usernameInput);

      //Password field
      HBox passwordBox = new HBox();
      passwordBox.setAlignment(Pos.CENTER);

      passwordInput = new TextField();
      passwordInput.setPrefWidth(400);
      passwordInput.setPrefHeight(50);
      passwordInput.setFont(new Font("Arial", 18));
      passwordInput.setStyle("-fx-border-color: grey; -fx-border-width: 1px ;");

      passwordBox.setPadding(new Insets(0, 0, 55, 0));
      passwordInput.setPromptText("Password");
      passwordBox.getChildren().addAll(passwordInput);

      //Log in Button
      Button logInButton = new Button("Log in");
      logInButton.setFont(new Font("Verdana", 30));
      logInButton.setMaxWidth(200);
      logInButton.setOnAction(event ->
      {
         login(usernameInput.getText(), passwordInput.getText());
      });

      //Register
      Label registerLabel = new Label("Don't have an account");
      registerLabel.setFont(new Font("Verdana", 30));
      registerLabel.setPadding(new Insets(50, 0, 30, 0));

      Button registerButton = new Button("Register");
      registerButton.setFont(new Font("Verdana", 30));
      registerButton.setMaxWidth(200);

      registerButton.setOnAction(event ->
      {
         register.display();
         window.close();

      });

      //Center
      VBox center = new VBox();
      center.getChildren()
            .addAll(title, errorMessage, usernameBox, passwordBox, logInButton,
                  registerLabel, registerButton);
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
      scene = new Scene(layout, 600, 700);
      window.setScene(scene);

      //window.showAndWait();
      window.showAndWait();
   }

   private void login(String username, String password)
   {
      ClientControl.getInstance()
            .signIn(username, password, (status, isSignedIn, sessionID) ->
            {
               Platform.runLater(() ->
                     {
                           mainPage.signIn();
                           /*if (isSignedIn)
                           {
                              mainPage.signIn();
                           }
                           else
                           {
                              usernameInput.setStyle("-fx-border-color: red;");
                              passwordInput.setStyle("-fx-border-color: red;");
                              errorMessage.setVisible(true);

                           }*/
                     }
               );
            });
   }
}
