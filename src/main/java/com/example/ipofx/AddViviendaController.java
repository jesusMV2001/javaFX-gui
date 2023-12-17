package com.example.ipofx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Objects;

public class AddViviendaController {
    public String leng;
    public Label prueba;
    HelloController h;
    ToggleGroup toggleGroup;
    public Menu idiomasMenu;

    public AddViviendaController() {
    }

    @FXML
    private void initialize() {
        toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarTexto(((RadioMenuItem) newValue).getText(),false);
                h.actualizarTexto(((RadioMenuItem) newValue).getText());
            }
        });
    }

    public void inicializarConParametros(HelloController h) {
        this.h=h;
        leng=h.leng;
        crearIdiomas(h.f.idiomas.keySet().stream().toList());
    }

    private void crearIdiomas(List<String> idiomas){
        for (String idioma: idiomas){
            RadioMenuItem nuevoItem = new RadioMenuItem(idioma);
            nuevoItem.setToggleGroup(toggleGroup);

            idiomasMenu.getItems().add(nuevoItem);
        }
        actualizarTexto(idiomas.getFirst(), true);
    }

    public void actualizarTexto(String idioma, boolean primeraVez){
        if(primeraVez){
            //se cambian todos los textos
            idiomasMenu.setText(h.f.idiomas.get(leng).getTexto("idiomasMenu"));
            //se selecciona el idioma elegido
            for(MenuItem t: idiomasMenu.getItems()){
                if( t.getText().equals(leng))  ((RadioMenuItem) t).setSelected(true);
            }
        }else if(!Objects.equals(idioma, leng)){
            //se cambian todos los textos
            idiomasMenu.setText(h.f.idiomas.get(idioma).getTexto("idiomasMenu"));
            //se selecciona el idioma elegido
            for(MenuItem t: idiomasMenu.getItems()){
                if( t.getText().equals(idioma))  ((RadioMenuItem) t).setSelected(true);
            }

            leng=idioma;
        }
    }
}
