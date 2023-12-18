package com.example.ipofx;

import LectorDatos.LectorDatos;
import LectorDatos.LectorViviendas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class HelloController {
    private static final int VIVIENDAS_POR_PAGINA = 5;
    public Button pagAnterior;
    public Button pagSiguiente;
    public Label numPagina;
    public AnchorPane contenedorTabla;
    public HashMap<String,Vivienda> viviendas;
    public ObservableList<Vivienda> datosViviendas;
    public int paginaActual = 0;
    public TextField buscador;
    public Button botonBuscador;
    public Label textoBuscador;
    public Button botonAddVivienda;
    public TableView tablaViviendas;
    public String leng="";
    public LectorDatos f;
    public Menu idiomasMenu;
    ToggleGroup toggleGroup;

    @FXML
    private void initialize() {
        Image imagen = new Image("add-icon.png");
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        botonAddVivienda.setGraphic(imageView);

        numPagina.setText(String.valueOf(paginaActual+1));
        toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarTexto(((RadioMenuItem) newValue).getText());
            }
        });
        // Ajusta la altura preferida de la tabla
        tablaViviendas.setFixedCellSize(82); // Ajusta el tamaño de la celda según tus necesidades
        tablaViviendas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaViviendas.setPrefSize(TableView.USE_COMPUTED_SIZE, TableView.USE_COMPUTED_SIZE);


        // Crear las columnas y asignar las propiedades
        TableColumn<Vivienda, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre));
        columnaNombre.setResizable(true);
        columnaNombre.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.5));

        TableColumn<Vivienda, ImageView> columnaRutaFoto = new TableColumn<>("Ruta Foto");
        columnaRutaFoto.setCellValueFactory(cellData -> cellData.getValue().imagenProperty());
        columnaRutaFoto.setResizable(true);
        columnaRutaFoto.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.2));

        TableColumn<Vivienda, Button> columnaBoton1 = new TableColumn<>("Botón 1");
        columnaBoton1.setCellValueFactory(cellData -> cellData.getValue().botonEditProperty());
        columnaBoton1.setResizable(true);
        columnaBoton1.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.1));

        TableColumn<Vivienda, Button> columnaBoton2 = new TableColumn<>("Botón 2");
        columnaBoton2.setCellValueFactory(cellData -> cellData.getValue().botonDetailsProperty());
        columnaBoton2.setResizable(true);
        columnaBoton2.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.1));

        TableColumn<Vivienda, Button> columnaBoton3 = new TableColumn<>("Botón 3");
        columnaBoton3.setCellValueFactory(cellData -> cellData.getValue().botonDeleteProperty());
        columnaBoton3.setResizable(true);
        columnaBoton3.prefWidthProperty().bind(tablaViviendas.widthProperty().multiply(0.1));

        // Agregar las columnas a la tabla
        tablaViviendas.getColumns().addAll(columnaRutaFoto,columnaNombre, columnaBoton1, columnaBoton2, columnaBoton3);





    }

    private void addViviendasPredeterminadas(List<Vivienda> casas) {
        viviendas = new HashMap<>();

        for (Vivienda v : casas){
            v.botonDelete.setOnAction(actionEvent -> onBotonBorrar(v));
            viviendas.put(v.nombre,v);
        }

        Collection<Vivienda> viviendasCollection = viviendas.values();
        // Crea un ObservableList a partir de la Collection
        datosViviendas = FXCollections.observableArrayList(viviendasCollection);
    }

    public void mostrarViviendasEnPagina(int pagina) {
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
        datosViviendas.clear();
        buscarEnHashMap(viviendas,this.buscador.getText());
        paginaActual=0;
        mostrarViviendasEnPagina(paginaActual);
    }
    private void buscarEnHashMap(HashMap<String, Vivienda> mapa, String cadenaBusqueda) {
        for (Map.Entry<String, Vivienda> entry : mapa.entrySet()) {
            String clave = entry.getKey();

            if (clave.toLowerCase().contains(cadenaBusqueda.toLowerCase()))
                datosViviendas.add(mapa.get(clave));
        }
    }

    @FXML
    public void onBotonBorrar(Vivienda vivienda){
        viviendas.remove(vivienda.nombre);
        datosViviendas.remove(vivienda);
        mostrarViviendasEnPagina(paginaActual);
    }

    @FXML
    private void onBotonAddVivienda(){
        try {
            // Cargar la nueva ventana desde un archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-vivienda.fxml"));
            Parent root = loader.load();
            AddViviendaController a = loader.getController();
            a.inicializarConParametros(this);
            // Crear una nueva instancia de Stage
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("IPO APP");

            // Configurar la escena en la nueva ventana
            nuevaVentana.setScene(new Scene(root,950, 800));

            // Configurar la ventana modal (bloquea la ventana principal hasta que se cierre)
            nuevaVentana.initModality(Modality.WINDOW_MODAL);
            nuevaVentana.initOwner(tablaViviendas.getScene().getWindow());
            nuevaVentana.setResizable(false);


            // Mostrar la nueva ventana
            nuevaVentana.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia la clase necesaria con los datos recogidos del fichero de idiomas
     * @param ruta ruta del fichero de idiomas
     */
    public void inicializarConParametros(String ruta, String rutaViviendas) {
        f = new LectorDatos(ruta);
        LectorViviendas l = new LectorViviendas(rutaViviendas);
        addViviendasPredeterminadas(l.viviendas);
        crearIdiomas(f.idiomas.keySet().stream().toList());
        // Mostrar las primeras viviendas
        mostrarViviendasEnPagina(paginaActual);
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