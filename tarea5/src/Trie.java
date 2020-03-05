import java.io.*;
import java.util.*;
public class Trie {
    public class NodoTrie {
        NodoTrie [] hijos;
        boolean fin;
        NodoTrie(){
            hijos=new NodoTrie[32];
        }
    }
    private NodoTrie raiz;
    private Trie(){
        raiz = new NodoTrie();
    }
    private void insertar(String palabra){
        NodoTrie p = raiz;
        int indice;
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            if(letra=='á') indice = 26;
            else if(letra=='é') indice = 27;
            else if(letra=='í') indice = 28;
            else if(letra=='ó') indice = 29;
            else if(letra=='ú') indice = 30;
            else if(letra=='ñ') indice = 31;
            else indice = letra-'a';
            if(p.hijos[indice]==null){
                NodoTrie nodo = new NodoTrie();
                p.hijos[indice] = nodo;
                p = nodo;
            }
            else p = p.hijos[indice];
        }
        p.fin = true;
    }
    private boolean contiene(String palabra){
        NodoTrie p = contieneNodo(palabra);
        if(p==null) return false;
        else{
            if (p.fin) return true;
        }
        return false;
    }
    private NodoTrie contieneNodo(String s){
        NodoTrie p = raiz;
        int indice;
        for (int i = 0; i < s.length(); i++) {
            char letra = s.charAt(i);
            if(letra=='á') indice = 26;
            else if(letra=='é') indice = 27;
            else if(letra=='í') indice = 28;
            else if(letra=='ó') indice = 29;
            else if(letra=='ú') indice = 30;
            else if(letra=='ñ') indice = 31;
            else indice = letra-'a';
            if(p.hijos[indice]!=null) p = p.hijos[indice];
            else return null;
        }
        if(p==raiz) return null;
        return p;
    }
    private static String limpiarString(String palabra){
        palabra=palabra.replaceAll("[^A-Za-záéíóúñ]","");
        palabra=palabra.toLowerCase();
        return palabra;
    }
    public static void main(String[] args) throws IOException {
        String s;
        Trie t = new Trie();
        Scanner scanner=new Scanner(System.in);
        BufferedReader bf=new BufferedReader(new FileReader("src/espanol2.txt"));
        while((s=bf.readLine())!=null){
            s=limpiarString(s);
            t.insertar(s);
        }
        System.out.print("Frase: ");
        s=scanner.nextLine();
        scanner.close();
        String[]palabras=s.split(" ");
        System.out.println("Palabras incorrectas: ");
        for (String i : palabras) {
            i=limpiarString(i);
            if(!t.contiene(i)) System.out.println(i);
        }
    }
}