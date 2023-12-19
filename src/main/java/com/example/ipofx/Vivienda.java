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
        try {
            Image image = new Image("image.png");
            this.foto = new ImageView(image);
        }catch (Exception e){
            System.out.println("error creando foto de vivienda");
        }

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
        this.botonDetails = new Button();
        this.botonDelete = new Button();

        try{
            Image imagen = new Image("edit.png");
            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            botonEdit.setGraphic(imageView);

            Image imagen1 = new Image("search.png");
            ImageView imageView1 = new ImageView(imagen1);
            imageView1.setFitWidth(30);
            imageView1.setFitHeight(30);
            botonDetails.setGraphic(imageView1);

            Image imagen2 = new Image("delete.png");
            ImageView imageView2 = new ImageView(imagen2);
            imageView2.setFitWidth(30);
            imageView2.setFitHeight(30);
            botonDelete.setGraphic(imageView2);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error creando botones viviendas");
        }
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
