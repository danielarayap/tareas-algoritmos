import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class Expand {
    private Map<String, Boolean> refs = new HashMap<>();//aquí se declara el Objeto donde se guardarán los archivos
    static public void main(String[]args) throws IOException {
        Expand expand = new Expand();
        for (String arg : args)expand.leerArchivo(arg); //aplica método leerArchivo a cada uno de los argumentos
    }
    private void leerArchivo(String archivo) throws IOException {
        if (refs.containsKey(archivo)) {
            System.out.println("SE ENCONTRARON REFERENCIAS CIRCULARES EN "+archivo);
            return;
        }else refs.put(archivo, Boolean.TRUE );
        FileReader fr = new FileReader(archivo);
        BufferedReader bf = new BufferedReader(fr);
        String sCadena;
        while((sCadena=bf.readLine())!=null){
            sCadena = sCadena.replace(">>>","<<<");//en la linea del archivo reemplaza
            // cualquier secuencia de caracteres ">>>" por "<<<"
            if(sCadena.contains("<<<")){
                String[] p = sCadena.split("<<<");
                for (String s : p) {
                    if (s.contains(".txt")) leerArchivo(s.substring(0, s.length()));
                    else System.out.println(s);
                }
            } else System.out.println(sCadena);
        }
    }
}
