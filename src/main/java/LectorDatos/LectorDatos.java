package LectorDatos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LectorDatos {
    public int nuIdiomas;
    public HashMap<String,Idioma> idiomas;
    public LectorDatos(String ruta) {
        idiomas = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(ruta);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            nuIdiomas= Integer.parseInt(bufferedReader.readLine().trim());

            for (int i = 0; i < nuIdiomas; i++) {
                Idioma idioma = new Idioma(bufferedReader.readLine().trim()); //sigla del idioma
                idioma.numTexto = Integer.parseInt(bufferedReader.readLine().trim()); //num de cadenas de texto
                for (int j = 0; j < idioma.numTexto; j++) {
                    String[] split = bufferedReader.readLine().split("=");

                    idioma.addTexto(split[0],split[1]);
                }
                idioma.numImagenes = Integer.parseInt(bufferedReader.readLine().trim()); //num de imagenes
                for (int j = 0; j < idioma.numImagenes; j++) {
                    idioma.addImg(bufferedReader.readLine());
                }
                idiomas.put(idioma.id,idioma);
            }

            // Cerrar los recursos después de su uso
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
