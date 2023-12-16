package com.example.ipofx;

import LectorDatos.LectorDatos;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Objects;

public class HelloController {
    private String leng="";
    private LectorDatos f;
    public Menu idiomasMenu;
    ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarTexto(((RadioMenuItem) newValue).getText());
            }
        });
    }

    /**
     * Inicia la clase necesaria con los datos recogidos del fichero de idiomas
     * @param ruta ruta del fichero de idiomas
     */
    public void inicializarConParametros(String ruta) {
        f = new LectorDatos(ruta);
        crearIdiomas(f.idiomas.keySet().stream().toList());

    }

    /**
     * Crea todos los seleccionables dentro del menu en el apartado idioma
     * @param idiomas lista con los idiomas sacado del archivo idioma.tsv
     */
    public void crearIdiomas(List<String> idiomas){
        for (String idioma: idiomas){
            RadioMenuItem nuevoItem = new RadioMenuItem(idioma);
            nuevoItem.setToggleGroup(toggleGroup);

            idiomasMenu.getItems().add(nuevoItem);
        }
        actualizarTexto(idiomas.getFirst());
    }

    public void actualizarTexto(String idioma){
        if(!Objects.equals(idioma, leng)){
            idiomasMenu.setText(f.idiomas.get(idioma).getTexto("menuIdioma"));
            ObservableList<MenuItem> listaIdiomas = idiomasMenu.getItems();
            for(MenuItem t: listaIdiomas){
                if( t.getText().equals(idioma))  ((RadioMenuItem) t).setSelected(true);
            }

            leng=idioma;
        }
    }
}