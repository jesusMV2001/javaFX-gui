package com.example.ipofx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class VistaViviendaController {
    HelloController h;
    public String leng;
    public Menu idiomasMenu;
    public Button volverBoton;
    public ImageView foto1;
    public ImageView foto2;
    public ImageView foto3;
    public Label masFotos;
    public ImageView mapa;
    public Label precioText;
    public Label ubicacionText;
    public Label descripcionText;
    ToggleGroup toggleGroup;

    @FXML
    private void initialize() {
        //Icono boton guardar
        Image imagen = new Image("save.png");
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        //Imagenes
        Image mapaImg = new Image("Mapa.png");
        mapa.setImage(mapaImg);
        mapaImg = new Image("foto1.png");
        foto1.setImage(mapaImg);
        mapaImg = new Image("foto2.png");
        foto2.setImage(mapaImg);
        mapaImg = new Image("foto3.png");
        foto3.setImage(mapaImg);

        toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarTexto(((RadioMenuItem) newValue).getText(),false);
                h.actualizarTexto(((RadioMenuItem) newValue).getText());
            }
        });
    }

    public void inicializarConParametros(HelloController h, Vivienda vivienda) {
        this.h=h;
        leng=h.leng;
        crearIdiomas(h.f.idiomas.keySet().stream().toList());
        ubicacionText.setText(vivienda.nombre);
        precioText.setText(vivienda.precio);
        descripcionText.setText(vivienda.descripcion);
    }

    private void crearIdiomas(List<String> idiomas){
        for (String idioma: idiomas){
            RadioMenuItem nuevoItem = new RadioMenuItem(idioma);
            nuevoItem.setToggleGroup(toggleGroup);

            idiomasMenu.getItems().add(nuevoItem);
        }
        actualizarTexto(idiomas.getFirst(), true);
    }

    @FXML
    private void setVolverBoton(){
        Stage stage = (Stage) volverBoton.getScene().getWindow();
        stage.close();
    }

    public void actualizarTexto(String idioma, boolean primeraVez){
        if(primeraVez){
            //se cambian todos los textos
            cambiarNombres(leng);
            //se selecciona el idioma elegido
            for(MenuItem t: idiomasMenu.getItems()){
                if( t.getText().equals(leng))  ((RadioMenuItem) t).setSelected(true);
            }
        }else if(!Objects.equals(idioma, leng)){
            //se cambian todos los textos
            cambiarNombres(idioma);
            //se selecciona el idioma elegido
            for(MenuItem t: idiomasMenu.getItems()){
                if( t.getText().equals(idioma))  ((RadioMenuItem) t).setSelected(true);
            }

            leng=idioma;
        }
    }

    private void cambiarNombres(String idioma){
        idiomasMenu.setText(h.f.idiomas.get(idioma).getTexto("idiomasMenu"));
        volverBoton.setText(h.f.idiomas.get(idioma).getTexto("botonVolver"));
        masFotos.setText(h.f.idiomas.get(idioma).getTexto("textoMasFotos"));
    }

}
