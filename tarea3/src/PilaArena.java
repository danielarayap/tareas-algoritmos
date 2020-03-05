import java.util.Scanner;
public class PilaArena {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n; long Ti,Tf;
        System.out.println("Introduzca un número entero: ");
        n = sc.nextInt();
        int a = (int) Math.sqrt(n) + 1;//Se define un tamaño minimo que debe tener la matriz
        int m[][] = new int[a][a];
        int pm = (a - 1) / 2;
        m[pm][pm] = n;//se inicializa la matriz con el dato n en la celda central
        Ti=System.currentTimeMillis();
        for(int i=0;i<n;i++) {//ciclo que repite el recorrido n veces
            int j=0;
            while (j < a) {//ciclos de recorrido de la matriz
                int k=0;
                while (k < a) {
                    if (m[k][j] >= 4) {//condición de estabilidad
                        m[k][j] -= 4;
                        m[k][j - 1] += 1;
                        m[k - 1][j] += 1;
                        m[k + 1][j] += 1;
                        m[k][j + 1] += 1;
                    }
                 k++;
                }
             j++;
            }
        }
        Tf=System.currentTimeMillis();
        System.out.println(Tf-Ti);
        Ventana v = new Ventana(700, "Pilas de Arena");
        v.mostrarMatriz(m);
    }
}
