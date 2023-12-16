package com.example.ipofx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    public CheckMenuItem idioma2;
    public CheckMenuItem idioma1;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onIdioma1() {
        //TODO hacer que cuando se seleccione cambie al idioma 1
        if(idioma1.isSelected()) idioma2.setSelected(false);
        if(!idioma1.isSelected() && !idioma2.isSelected()) idioma1.setSelected(true);
    }

    @FXML
    protected void onIdioma2() {
        //TODO hacer que cuando se seleccione cambie al idioma 2
        if(idioma2.isSelected()) idioma1.setSelected(false);
        if(!idioma1.isSelected() && !idioma2.isSelected()) idioma2.setSelected(true);
    }
}