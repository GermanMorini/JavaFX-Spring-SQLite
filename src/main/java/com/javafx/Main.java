package com.javafx;

import atlantafx.base.theme.NordDark;
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

	public static void main(String[] args) {
		launch(args);
	}

//	este método se usa para configurar la aplicación antes de mostrar la parte gráfica
	@Override
	public void init() throws Exception {
		Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());

//		Inicia la aplicación Spring Boot y obtiene el contexto de la aplicación,
//		que se puede utilizar para obtener beans administrados por Spring.
		appContext = SpringApplication.run(Main.class);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/index.fxml"));

//		Configura el loader para utilizar el contexto de la aplicación Spring Boot como fábrica de controladores,
//		lo que permite la inyección de dependencias en los controladores FXML.
		loader.setControllerFactory(appContext::getBean);
		rootNode = loader.load();
	}

//	se ejecuta para iniciar la parte gráfica y la lógica principal
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(rootNode, 1300, 800, false, SceneAntialiasing.BALANCED);
		stage.setScene(scene);
		stage.show();
	}

//	se ejecuta al terminar la aplicación, ya sea por el usuario o debido a un error
	@Override
	public void stop() throws Exception {

	}
}
