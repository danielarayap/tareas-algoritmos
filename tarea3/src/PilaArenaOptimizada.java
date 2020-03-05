import java.util.Scanner;
public class PilaArenaOptimizada {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca un número entero: ");
        int n = sc.nextInt();long Ti,Tf;
        int lado=(int)Math.sqrt(n);
        int m[][] = new int[lado][lado];//matriz de granos de arena
        m[lado/2][lado/2]=n;//centro de la matriz con n granos de arena
        Pila indices=new Pila();//pila donde se guardarán los índices
        indices.apilar(lado/2, lado/2);//n granos de arena en la celda central
        Ti=System.currentTimeMillis();
        while (!indices.estaVacia()){
            int[] a=indices.tope();//para obtener los índices
            int x=a[0],y=a[1];
            if (m[x][y]>=4){
                m[x][y] -= 4;
                m[x][y - 1] += 1;
                m[x - 1][y] += 1;
                m[x + 1][y] += 1;
                m[x][y + 1] += 1;
            } else indices.desapilar();
            if(m[x+1][y]==4)indices.apilar(x+1,y);
            if(m[x-1][y]==4)indices.apilar(x-1,y);
            if(m[x][y-1]==4)indices.apilar(x,y-1);
            if(m[x][y+1]==4)indices.apilar(x,y+1);
        }
        Tf=System.currentTimeMillis();
        System.out.println("tiempo que se demora= " + (Tf-Ti));
        Ventana v = new Ventana(700, "Pila de Arena 2");
        v.mostrarMatriz(m);
    }
}