package com.example.ipofx;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

public class Vivienda {
    public String nombre;
    public String rutaFoto;
    public Button boton1;
    public Button boton2;
    public Button boton3;
    // Propiedades observables para los botones
    private final SimpleObjectProperty<Button> boton1Property = new SimpleObjectProperty<>(this, "boton1Property");
    private final SimpleObjectProperty<Button> boton2Property = new SimpleObjectProperty<>(this, "boton2Property");
    private final SimpleObjectProperty<Button> boton3Property = new SimpleObjectProperty<>(this, "boton3Property");

    public Vivienda(String nombre, String rutaFoto) {
        this.nombre = nombre;
        this.rutaFoto = rutaFoto;
        this.boton1 = new Button("Botón 1");
        this.boton2 = new Button("Botón 2");
        this.boton3 = new Button("Botón 3");
        // Asignar los botones a las propiedades observables
        boton1Property.set(this.boton1);
        boton2Property.set(this.boton2);
        boton3Property.set(this.boton3);
    }
    // Getters para las propiedades observables de los botones
    public SimpleObjectProperty<Button> boton1Property() {
        return boton1Property;
    }

    public SimpleObjectProperty<Button> boton2Property() {
        return boton2Property;
    }

    public SimpleObjectProperty<Button> boton3Property() {
        return boton3Property;
    }
}
