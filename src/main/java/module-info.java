module com.example.ipofx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.ipofx to javafx.fxml;
    exports com.example.ipofx;
    exports LectorDatos;
    opens LectorDatos to javafx.fxml;
}