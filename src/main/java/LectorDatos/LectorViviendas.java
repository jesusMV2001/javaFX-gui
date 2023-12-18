package LectorDatos;

import com.example.ipofx.Vivienda;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LectorViviendas {
    public List<Vivienda> viviendas;
    public LectorViviendas(String ruta) {
        viviendas = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(ruta);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split(";");
                Vivienda nueva = new Vivienda(datos[0],datos[3]);
                nueva.precio = datos[1];
                nueva.descripcion = datos[2];
                viviendas.add(nueva);
            }

            // Cerrar los recursos después de su uso
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
