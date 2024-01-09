package com.javafx;

import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {

	public static ConfigurableApplicationContext appContext;
	public static Parent rootNode;
	public static Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
		appContext = SpringApplication.run(Main.class);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/index.fxml"));
		loader.setControllerFactory(appContext::getBean);
		Scene scene = new Scene(loader.load(), 1300, 800, false, SceneAntialiasing.BALANCED);

		stage.setScene(scene);
		stage.show();
	}
}
