import java.util.NoSuchElementException;

class Pila{
    class Nodo {
        int[]info=new int[2];
        Nodo sgte;
        }
    private Nodo tope;
    Pila(){
        tope=null;
    }
    boolean estaVacia() {
        return tope==null;
    }
    int[] tope() {
        if (!estaVacia()) return tope.info;        // si esta vacia es un error
        else return null;
    }
    void apilar(int i, int j){
        int[]a=new int[2];
        a[0]=i;
        a[1]=j;
        Nodo nuevo=new Nodo();
        nuevo.info=a;
        if(tope==null){
            nuevo.sgte=null;
            tope=nuevo;
        }else{
            Nodo aux=new Nodo();
            aux.info=a;
            aux.sgte=tope;
            tope=aux;
        }
    }
    int[] desapilar(){
        if (estaVacia()) throw new NoSuchElementException("Pila vacía");
        int[] aux=tope.info;
        tope=tope.sgte;
        return aux;
    }

}
