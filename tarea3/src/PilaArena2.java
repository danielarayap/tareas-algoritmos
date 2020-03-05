import java.util.Scanner;
public class PilaArena2 {
    static int m[][];
    static int pm;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Introduzca un número entero: ");
        n = sc.nextInt();
        int a = (int) Math.sqrt(n) + 1;
        if(a % 2 == 0){
            a++;
        }
        m = new int[a][a];
        pm = (a - 1) / 2;
        m[pm][pm] = 0;

        for (int i = 0; i < n; i++) {
            ponerPunto(pm, pm);
        }

        Ventana v = new Ventana(700);
        v.mostrarMatriz(m);
    }

    private static void ponerPunto(int x, int y) {

        m[x][y]++;

        if(m[x][y] >= 4){
            m[x][y] = 0;
            ponerPunto(x + 1, y);
            ponerPunto(x - 1, y);
            ponerPunto(x, y + 1);
            ponerPunto(x, y - 1);
        }

    }
}


