package com.example.ipofx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class AddViviendaController {
    private boolean editar;
    public String leng;
    public Button volverBoton;
    public Button guardarBoton;
    public Button addBoton;
    public ImageView foto1;
    public ImageView foto3;
    public ImageView foto2;
    public Label masFotos;
    public ImageView mapa;
    public TextField precioText;
    public TextField ubicacionText;
    public TextField descripcionText;
    public Button eliminarImg;
    HelloController h;
    ToggleGroup toggleGroup;
    public Menu idiomasMenu;
    Vivienda nuevaVivienda;

    public AddViviendaController() {
    }

    @FXML
    private void initialize() {
        //Icono boton guardar
        Image imagen = new Image("save.png");
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        guardarBoton.setGraphic(imageView);
        //Imagenes
        Image mapaImg = new Image("Mapa.png");
        mapa.setImage(mapaImg);
        mapaImg = new Image("foto1.png");
        foto1.setImage(mapaImg);
        mapaImg = new Image("foto2.png");
        foto2.setImage(mapaImg);
        mapaImg = new Image("foto3.png");
        foto3.setImage(mapaImg);

        nuevaVivienda = new Vivienda();
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
        editar=false;
        if(vivienda != null){
            editar=true;
            ubicacionText.setText(vivienda.nombre);
            precioText.setText(vivienda.precio);
            descripcionText.setText(vivienda.descripcion);
            nuevaVivienda=vivienda;
        }
    }

    @FXML
    private void setVolverBoton(){
        Stage stage = (Stage) volverBoton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void setPrecioText(){
        this.nuevaVivienda.precio = this.precioText.getText();
    }
    @FXML
    private void setUbicacionText(){
        this.nuevaVivienda.nombre = this.ubicacionText.getText();
    }
    @FXML
    private void setDescripcionText(){
        this.nuevaVivienda.descripcion = this.descripcionText.getText();
    }
    @FXML
    private void setGuardarBoton(){
        if(descripcionText.getText().isBlank() || precioText.getText().isBlank() || ubicacionText.getText().isBlank()){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle(h.f.idiomas.get(leng).getTexto("errorTitulo"));
            alerta.setHeaderText(h.f.idiomas.get(leng).getTexto("errorRepetidoHeader"));
            alerta.setContentText(h.f.idiomas.get(leng).getTexto("errorVacioContenido"));

            alerta.showAndWait();
        } else if(editar){
            if(h.viviendas.get(nuevaVivienda.nombre)!=null && !h.viviendas.get(nuevaVivienda.nombre).equals(nuevaVivienda))
                mensajeErrorUbicacionRepetida();
            else {
                h.mostrarViviendasEnPagina(h.paginaActual);
                setVolverBoton();
            }

        } else if(h.viviendas.containsKey(ubicacionText.getText())){
            mensajeErrorUbicacionRepetida();
        } else{
            nuevaVivienda.botonDelete.setOnAction(actionEvent -> h.onBotonBorrar(nuevaVivienda));
            h.viviendas.put(nuevaVivienda.nombre,nuevaVivienda);
            h.datosViviendas.add(nuevaVivienda);
            h.mostrarViviendasEnPagina(h.paginaActual);
            setVolverBoton();
        }
    }

    private void mensajeErrorUbicacionRepetida(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(h.f.idiomas.get(leng).getTexto("errorTitulo"));
        alerta.setHeaderText(h.f.idiomas.get(leng).getTexto("errorRepetidoHeader"));
        alerta.setContentText(h.f.idiomas.get(leng).getTexto("errorRepetidoContenido"));

        alerta.showAndWait();
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
        guardarBoton.setText(h.f.idiomas.get(idioma).getTexto("botonGuardar"));
        eliminarImg.setText(h.f.idiomas.get(idioma).getTexto("botonEliminarImg"));
        addBoton.setText(h.f.idiomas.get(idioma).getTexto("botonAddImg"));
        precioText.setPromptText(h.f.idiomas.get(idioma).getTexto("textoPrecio"));
        ubicacionText.setPromptText(h.f.idiomas.get(idioma).getTexto("textoUbicaion"));
        descripcionText.setPromptText(h.f.idiomas.get(idioma).getTexto("textoDescripcion"));
        masFotos.setText(h.f.idiomas.get(idioma).getTexto("textoMasFotos"));
    }
}
