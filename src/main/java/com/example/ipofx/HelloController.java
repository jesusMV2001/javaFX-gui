package com.example.ipofx;

import LectorDatos.LectorDatos;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.util.List;
import java.util.Objects;

public class HelloController {
    private static final int VIVIENDAS_POR_PAGINA = 6;
    public Button pagAnterior;
    public Button pagSiguiente;
    public Label numPagina;
    public AnchorPane contenedorTabla;
    public AnchorPane contenedorPaginador;
    private ObservableList<Vivienda> datosViviendas;
    private int paginaActual = 0;
    public TextField buscador;
    public Button botonBuscador;
    public Label textoBuscador;
    public Button botonAddVivienda;
    public TableView tablaViviendas;
    private String leng="";
    private LectorDatos f;
    public Menu idiomasMenu;
    ToggleGroup toggleGroup;

    @FXML
    private void initialize() {
        numPagina.setText(String.valueOf(paginaActual+1));
        toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarTexto(((RadioMenuItem) newValue).getText());
            }
        });
        // Ajusta la altura preferida de la tabla
        tablaViviendas.setFixedCellSize(50); // Ajusta el tamaño de la celda según tus necesidades
        tablaViviendas.prefHeightProperty().bind(Bindings.size(tablaViviendas.getItems()).multiply(tablaViviendas.getFixedCellSize()).add(30)); // 30 es para tener espacio extra
        //numPagina.layoutXProperty().bind(contenedorPaginador.widthProperty().subtract(numPagina.widthProperty()).divide(2));


        // Crear las columnas y asignar las propiedades
        TableColumn<Vivienda, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre));
        columnaNombre.setResizable(true);
        columnaNombre.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.5));

        TableColumn<Vivienda, String> columnaRutaFoto = new TableColumn<>("Ruta Foto");
        columnaRutaFoto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().rutaFoto));
        columnaRutaFoto.setResizable(true);
        columnaRutaFoto.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.2));

        TableColumn<Vivienda, Button> columnaBoton1 = new TableColumn<>("Botón 1");
        columnaBoton1.setCellValueFactory(cellData -> cellData.getValue().boton1Property());
        columnaBoton1.setResizable(true);
        columnaBoton1.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.1));

        TableColumn<Vivienda, Button> columnaBoton2 = new TableColumn<>("Botón 2");
        columnaBoton2.setCellValueFactory(cellData -> cellData.getValue().boton2Property());
        columnaBoton2.setResizable(true);
        columnaBoton2.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.1));

        TableColumn<Vivienda, Button> columnaBoton3 = new TableColumn<>("Botón 3");
        columnaBoton3.setCellValueFactory(cellData -> cellData.getValue().boton3Property());
        columnaBoton3.setResizable(true);
        columnaBoton3.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.1));

        // Agregar las columnas a la tabla
        tablaViviendas.getColumns().addAll(columnaRutaFoto,columnaNombre, columnaBoton1, columnaBoton2, columnaBoton3);

        // Crear datos de ejemplo
        datosViviendas = FXCollections.observableArrayList(
                new Vivienda("Casa 1", "ruta1.jpg"),
                new Vivienda("Casa 2", "ruta2.jpg"),
                new Vivienda("Casa 3", "ruta3.jpg"),
                new Vivienda("Casa 4", "ruta4.jpg"),
                new Vivienda("Casa 5", "ruta5.jpg"),
                new Vivienda("Casa 6", "ruta6.jpg")
        );


        // Mostrar las primeras viviendas
        mostrarViviendasEnPagina(paginaActual);

    }

    private void cambiarAlturaTabla() {
        // Escuchar cambios en la escena
        contenedorTabla.sceneProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, javafx.scene.Scene oldScene, javafx.scene.Scene newScene) {
                if (newScene != null) {
                    // Cuando la escena cambia, obtener la ventana y realizar la vinculación
                    Window ventana = newScene.getWindow();
                    if (ventana != null) {
                        double alturaVentana = ventana.getHeight();
                        double alturaDeseada = 0.6 * alturaVentana;

                        // Vincular la propiedad prefHeight del AnchorPane al 60% de la altura de la ventana
                        contenedorTabla.prefHeightProperty().bindBidirectional((Property<Number>) ventana.heightProperty());

                        // Establecer la altura máxima y mínima según sea necesario
                        contenedorTabla.setMaxHeight(alturaDeseada);
                        contenedorTabla.setMinHeight(alturaDeseada);
                        System.out.println(alturaDeseada);
                    }
                }
            }
        });
    }

    private void mostrarViviendasEnPagina(int pagina) {
        int inicio = pagina * VIVIENDAS_POR_PAGINA;
        int fin = Math.min(inicio + VIVIENDAS_POR_PAGINA, datosViviendas.size());

        tablaViviendas.setItems(FXCollections.observableArrayList(datosViviendas.subList(inicio, fin)));
    }

    @FXML
    private void mostrarPaginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--;
            mostrarViviendasEnPagina(paginaActual);
            numPagina.setText(Integer.toString(paginaActual+1));
        }
    }

    @FXML
    private void mostrarPaginaSiguiente() {
        int ultimaPagina = (int) Math.ceil((double) datosViviendas.size() / VIVIENDAS_POR_PAGINA) - 1;
        if (paginaActual < ultimaPagina) {
            paginaActual++;
            mostrarViviendasEnPagina(paginaActual);
            numPagina.setText(Integer.toString(paginaActual+1));
        }
    }

    @FXML
    private void onBotonBuscador(){

    }
    @FXML
    private void onBotonAddVivienda(){

    }

    /**
     * Inicia la clase necesaria con los datos recogidos del fichero de idiomas
     * @param ruta ruta del fichero de idiomas
     */
    public void inicializarConParametros(String ruta) {
        f = new LectorDatos(ruta);
        crearIdiomas(f.idiomas.keySet().stream().toList());
        crearTablaViviendas();
    }

    /**
     * Metodo para crear la vista con las viviendas
     */
    private void crearTablaViviendas(){

    }

    /**
     * Crea todos los seleccionables dentro del menu en el apartado idioma
     * @param idiomas lista con los idiomas sacado del archivo idioma.tsv
     */
    private void crearIdiomas(List<String> idiomas){
        for (String idioma: idiomas){
            RadioMenuItem nuevoItem = new RadioMenuItem(idioma);
            nuevoItem.setToggleGroup(toggleGroup);

            idiomasMenu.getItems().add(nuevoItem);
        }
        actualizarTexto(idiomas.getFirst());
    }

    /**
     * Actualiza el texto de toda la aplicacion segun el idioma seleccionado
     * @param idioma idioma seleccionado
     */
    public void actualizarTexto(String idioma){
        if(!Objects.equals(idioma, leng)){
            //se cambian todos los textos
            idiomasMenu.setText(f.idiomas.get(idioma).getTexto("idiomasMenu"));
            textoBuscador.setText(f.idiomas.get(idioma).getTexto("textoBuscador"));
            botonBuscador.setText(f.idiomas.get(idioma).getTexto("botonBuscador"));
            botonAddVivienda.setText(f.idiomas.get(idioma).getTexto("botonAddVivienda"));
            //se selecciona el idioma elegido
            for(MenuItem t: idiomasMenu.getItems()){
                if( t.getText().equals(idioma))  ((RadioMenuItem) t).setSelected(true);
            }

            leng=idioma;
        }
    }
}