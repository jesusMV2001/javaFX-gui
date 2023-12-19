package com.example.ipofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public HelloApplication() {}

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        // Obtener el controlador y establecer los parámetros
        HelloController controller = fxmlLoader.getController();
        controller.inicializarConParametros("idiomas.tsv","viviendas.csv");

        Scene scene = new Scene(root, 950, 650);
        stage.setTitle("IPO APP");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}