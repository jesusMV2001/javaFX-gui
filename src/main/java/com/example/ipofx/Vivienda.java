package com.example.ipofx;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vivienda {
    public String descripcion;
    public String precio;
    public String nombre;
    public ImageView foto;
    public Button botonEdit;
    public Button botonDetails;
    public Button botonDelete;
    // Propiedades observables para los botones
    private final SimpleObjectProperty<Button> boton1Property = new SimpleObjectProperty<>(this, "boton1Property");
    private final SimpleObjectProperty<Button> boton2Property = new SimpleObjectProperty<>(this, "boton2Property");
    private final SimpleObjectProperty<Button> boton3Property = new SimpleObjectProperty<>(this, "boton3Property");
    private final SimpleObjectProperty<ImageView> imagenProperty = new SimpleObjectProperty<>(this, "imagenProperty");

    public Vivienda(){
        creaBotones();
        // Asignar los botones a las propiedades observables
        boton1Property.set(this.botonEdit);
        boton2Property.set(this.botonDetails);
        boton3Property.set(this.botonDelete);
        Image image = new Image("image.png");
        this.foto = new ImageView(image);
        // Crear un ImageView para mostrar la imagen
        imagenProperty.set(this.foto);
    }

    public Vivienda(String nombre, String rutaFoto) {
        this.nombre = nombre;
        creaBotones();
        // Asignar los botones a las propiedades observables
        boton1Property.set(this.botonEdit);
        boton2Property.set(this.botonDetails);
        boton3Property.set(this.botonDelete);

        Image image = new Image(rutaFoto);
        this.foto = new ImageView(image);
        // Crear un ImageView para mostrar la imagen
        imagenProperty.set(this.foto);
    }

    private void creaBotones(){
        this.botonEdit = new Button();
        Image imagen = new Image("edit-icon.png");
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        botonEdit.setGraphic(imageView);

        this.botonDetails = new Button();
        imagen = new Image("search-icon.png");
        imageView = new ImageView(imagen);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        botonDetails.setGraphic(imageView);

        this.botonDelete = new Button();
        imagen = new Image("delete-icon.png");
        imageView = new ImageView(imagen);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        botonDelete.setGraphic(imageView);
    }

    // Getters para las propiedades observables de los botones
    public SimpleObjectProperty<Button> botonEditProperty() {
        return boton1Property;
    }

    public SimpleObjectProperty<Button> botonDetailsProperty() {
        return boton2Property;
    }

    public SimpleObjectProperty<Button> botonDeleteProperty() {
        return boton3Property;
    }
    public SimpleObjectProperty<ImageView> imagenProperty() {
        return imagenProperty;
    }
}
