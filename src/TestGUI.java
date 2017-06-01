import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Client.ClientControl;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TestGUI extends Application
{
	private ClientControl controller;
	private Stage window;

	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		window = primaryStage;
		

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		

		Image image = SwingFXUtils.toFXImage(ImageIO.read(fileChooser.showOpenDialog(window)), null);

		// simple displays ImageView the image as is
		ImageView iv1 = new ImageView();
		iv1.setImage(image);

		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.WHITE);

		HBox box = new HBox();
		box.getChildren().add(iv1);

		root.getChildren().add(box);

		window.setTitle("ImageView");
		window.setWidth(415);
		window.setHeight(200);
		window.setScene(scene);
		window.sizeToScene();
		window.show();
	}
}
