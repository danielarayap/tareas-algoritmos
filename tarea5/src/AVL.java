import java.io.*;
import java.util.*;
public class AVL {
    private NodoAVL raiz;
    public class NodoAVL {
        String palabra;
        NodoAVL izq, der;
        int altura;
        NodoAVL(String palabra) {
            izq = null;
            der = null;
            altura = 0;
            this.palabra=palabra;
        }
        String getPalabra(){
            return palabra;
        }
        NodoAVL getIzq(){
            return izq;
        }
        void setIzq(NodoAVL p){
            izq=p;
        }
        NodoAVL getDer(){
            return der;
        }
        void setDer(NodoAVL p){
            der=p;
        }
        void setAltura(int altura) {
            this.altura = altura;
        }
    }
    private AVL() {raiz = null;}
    private int altura(NodoAVL p ) {
        return p == null ? -1 : p.altura;
    }
    private void insertar(String palabra){
        raiz=insertar(raiz,palabra);
    }
    private NodoAVL insertar(NodoAVL t, String x) {
        if (t == null){
            t = new NodoAVL(x);
            return t;
        }
        else{
            if(x.compareTo(t.getPalabra())<0){
                t.setIzq(insertar(t.getIzq(),x));
                if(altura(t.getIzq())-altura(t.getDer())==2){
                    if(x.compareTo(t.getIzq().getPalabra())<0) t=rotacionIzq(t);
                    else t=rotacionDobleIzq(t);
                }
            }
            else{
                if(x.compareTo(t.getPalabra())>0){
                    t.setDer(insertar(t.getDer(),x));
                    if(altura(t.getDer())-altura(t.getIzq())==2){
                        if(x.compareTo(t.getDer().getPalabra())>0) t=rotacionDer(t);
                        else t=rotacionDobleDer(t);
                    }
                }
            }
        }
        t.setAltura(Math.max(altura(t.getIzq()),altura(t.getDer()))+1);
        return t;
    }
    //Rotacion simple izquierda
    private NodoAVL rotacionIzq(NodoAVL p){
        NodoAVL aux = p.izq;
        p.izq = aux.der;
        aux.der = p;
        p.altura = Math.max( altura( p.izq), altura( p.der) ) + 1;
        aux.altura = Math.max( altura( aux.izq), altura(aux.der)) + 1;
        return aux;
    }
    //Rotacion simple derecha
    private NodoAVL rotacionDer(NodoAVL p) {
        NodoAVL aux = p.der;
        p.der = aux.izq;
        aux.izq = p;
        p.altura = Math.max( altura( p.izq), altura( p.der) ) + 1;
        aux.altura = Math.max( altura( aux.izq), altura(aux.der)) + 1;
        return aux;
    }
    //Rotacion doble a la izquierda
    private NodoAVL rotacionDobleIzq(NodoAVL p) {
        p.izq = rotacionDer( p.izq);
        return rotacionIzq( p );
    }
    //Rotacion doble a la derecha
    private NodoAVL rotacionDobleDer(NodoAVL p) {
        p.der = rotacionIzq( p.der);
        return rotacionDer( p );
    }
    private boolean contiene(String val){
        return contiene(raiz,val);
    }
    private boolean contiene(NodoAVL r, String val) {
        if (r == null) return false;
        if (val.compareTo(r.getPalabra()) < 0) return contiene(r.getIzq(), val);
        return val.compareTo(r.getPalabra()) <= 0 || contiene(r.getDer(), val);
    }
    private static String limpiarString(String palabra){
        palabra=palabra.toLowerCase();
        for (int i = 0; i < palabra.length() ; i++) {
            char c=palabra.charAt(i);
            if(Character.isDigit(c)){
                palabra=palabra.replace(c+"","");
                i=i-1;
            }
        }
        palabra=palabra.replaceAll("\\p{Punct}","");
        return palabra;
    }
    public static void main(String[] args) throws IOException {
        String s; long Ti,Tf;
        AVL a=new AVL();
        Scanner sc=new Scanner(System.in);
        BufferedReader bf=new BufferedReader(new FileReader("src/espanol2.txt"));
        while ((s = bf.readLine())!=null) {
            s = limpiarString(s);
            a.insertar(s);
        }
        System.out.print("Frase: ");
        s=sc.nextLine();
        String[]palabras=s.split(" ");
        System.out.println("Palabras incorrectas: ");
        for (String i : palabras) {
            i=limpiarString(i);
            if(!a.contiene(i)) System.out.println(i);
        }
        sc.close();
    }
}