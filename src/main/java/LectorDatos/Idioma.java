package LectorDatos;

import java.util.ArrayList;
import java.util.HashMap;

public class Idioma {
    public String id;
    public int numTexto;
    public int numImagenes;
    public HashMap<String,String> texto;
    public ArrayList<String> img;

    public Idioma(String id) {
        this.id=id;
        texto=new HashMap<>();
        img=new ArrayList<>();
    }

    public void addTexto(String k, String v){
        texto.put(k,v);
    }

    public String getTexto(String k){
        return texto.get(k);
    }

    public void addImg(String v){
        img.add(v);
    }

    public String getImg(int k){
        return img.get(k);
    }
}
