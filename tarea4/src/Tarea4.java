import java.io.*;
import java.util.*;
import static java.lang.Character.*;
public class Tarea4 {
	static public void main(String[] args) throws IOException{
        Scanner sc=new Scanner(System.in);
        System.out.print("Entrada: ");
        String fx=sc.nextLine();
        System.out.print("Derivando en: ");
        String var=sc.nextLine();
        sc.close(); /*hasta aqui obtuvimos la expresion en notacion polaca inversa por parte del usuario y la variable
        respecto a la cual el usuario quiere derivar la expresion*/
        String expresion=getString(simplificar(construir(fx)));
        System.out.print("Corresponde a: " + expresion.substring(1,expresion.length()-1));/*hasta aqui el programa
        entrega la expresion del arbol en notacion normal y si los paréntesis externos*/
        expresion=getString(simplificar(derivar(construir(fx),var)));
        System.out.print("\nSalida: " + expresion.substring(1,expresion.length()-1) );/*deriva la expresión e imprime el
        resultado sin los paréntesis externos*/
	}
	private static Nodo construir(String fx){ /*construye el arbol de la expresion*/
        Stack<Nodo> pila=new Stack<>();//aqui se guardaran los Nodos;
        for(int i=0;i<fx.length();i++) {
            char caracter = fx.charAt(i);
            if (caracter == '+' || caracter == '*' || caracter == '/' || caracter == '-') { /*cuando es una operacion,
            se crea un Nodo cuya raiz es la operacion, su Nodo derecho es el elemento que le sigue en la pila y su Nodo
            izq es el siguiente*/
                Nodo aux = new Nodo();
                aux.info = "" + caracter;
                aux.der = pila.pop();
                aux.izq = pila.pop();
                pila.push(aux);
            } else if (isDigit(caracter) || isLetter(caracter)) { /*cuando es un número o variable, se crea un nodo con
            su valor y se apila*/
                Nodo aux = new Nodo("" + caracter, null, null);
                pila.push(aux);
            }
        }
        return pila.pop();
    }
	private static String getString(Nodo fx){
        String raiz=fx.info;
        String expresion;
        if (!raiz.equals("+") && !raiz.equals("*") && !raiz.equals("-") && !raiz.equals("/")){/*si la raiz no
		corresponde a una operacion, el String toma el valor del nodo*/
            expresion=raiz;
        }
        else{ /*en caso de que sea una operacion, se concatenan paréntesis "(", se hace una llamada recursiva a
        getString sobre el Nodo de la izq,se concatena la raiz, getString al Nodo derecho recursivamente y se
        concatena ")"  */
            expresion="("+getString(fx.izq)+raiz+getString(fx.der)+")";
        }
        return expresion;
    }
	private static Nodo simplificar(Nodo fx){
		String s=fx.info;
		if (!s.equals("+") && !s.equals("*") && !s.equals("-") && !s.equals("/")) return fx; /*las variables o numeros
		solos no se simplifican*/
		else{
			fx.izq=simplificar(fx.izq);
			fx.der=simplificar(fx.der);
            switch (s) {
                case "+":  /*si la operacion es una suma, se debe simplifcar*/
                    if (fx.izq.info.equals("0")) {
                        return fx.der; /*si el operando izquierdo es 0, se retorna sólo la expresión de la derecha*/
                    } else if (fx.der.info.equals("0")) {
                        return fx.izq; /*si el operando derecho es 0 se retorna sólo la expresion de la izquierda*/
                    } else return fx; /*si la expresion no tiene un 0 a la izquierda o la derecha, no se simplifica*/
                case "*":  /*si la operacion es una multiplicacion, se tienen ciertos casos de simplficacion*/
                    if (fx.izq.info.equals("0") || fx.der.info.equals("0")) {
                        return new Nodo("0", null, null); /*si alguno de los operando es 0, resultado es 0*/
                    } else if (fx.izq.info.equals("1")) {
                        return fx.der; /*si el operando de la izquierda es 1, retorna sólo la expresion de la derecha*/
                    } else if (fx.der.info.equals("1")) {
                        return fx.izq; /*si el operando de la derecha es 1, retorna solo la expresion de la izquierda*/
                    } else return fx; /*si no se tienen ni 0 ni 1 en ninguno de los hijos, se retorna la expresion*/
                case "/":   /*si la operacion es una division, se tienen ciertos casos de simplficacion*/
                    if (fx.izq.info.equals("0")) {
                        return new Nodo("0", null, null); /*si el valor del numerador es 0, se retorna un Nodo
					con valor 0*/
                    } else if (fx.der.info.equals("1")) {
                        return fx.izq;/*si el denominador es 1, se retorna la expresión del numerador*/
                    }
                    break;
                default:  /*caso de que la operacion sea una resta*/
                    if (fx.der.info.equals("0")) {
                        return fx.izq; /*si el operando de la derecha es un 0, entrega la expresion de la izquierda*/
                    }
                    break;
            }
			return fx; //retorna el arbol simplificado
		}
	}
	private static Nodo derivar(Nodo fx, String var){
		String caracter=fx.info;
		Nodo derivada=new Nodo();
		if(caracter.equals(var)) derivada.info ="1"; //si el caracter corresponde a la variable a derivar, devuelve 1
		else if(caracter.equals("+")||caracter.equals("-")){ /*si es + o -, en la raiz va la operacion y en los hijos
		izquierdo y derecho van las derivadas de los subárboles izquierdo y derecho respectivamente
		*/
			derivada.info =caracter;
			derivada.izq=derivar(fx.izq,var);
			derivada.der=derivar(fx.der,var);
		}
		else if(caracter.equals("*")){/*(fg)'=f'g+fg'; en la raiz va el operador de suma, en el hijo izquierdo va la
		multiplicacion de la derivada del hijo izquierdo por el hijo derecho y en el hijo derecho va la multiplic. del
		hijo izquierdo por la derivada del hijo derecho
		*/
			derivada.info ="+";
			derivada.izq=new Nodo("*",derivar(fx.izq,var),fx.der);
			derivada.der=new Nodo("*",fx.izq,derivar(fx.der,var));
		}
		else if(caracter.equals("/")){ /* (f/g)'=(f'g-fg')/(g*g); la raiz es '/', el hijo izquierdo es la operacion del
		 numerador, por lo que se crea un nuevo Nodo a la izquierda del Nodo izquierdo cuya raiz es '-', su hijo
		 izquierdo es un arbol con la raiz: *, Nodo izquierdo igual a la derivada del hijo izquierdo por el hijo
		 derecho, y Nodo derecho igual a la derivada del hijo derecho por el hijo izquierdo; el hijo derecho es la
		 operacion del denominador, es decir, un nuevo Nodo cuya raiz es '*' y sus Nodos derecho e izquierdo son g.
		*/
			derivada.info =caracter;
			derivada.izq=new Nodo();
			derivada.izq.info ="-";
			derivada.izq.izq=new Nodo("*",derivar(fx.izq,var),fx.der);
			derivada.izq.der=new Nodo("*",fx.izq,derivar(fx.der,var));
			derivada.der=new Nodo("*",fx.der,fx.der);
		}
		else derivada.info ="0"; //si es un numero o variable que no es segun la cual se deriva, el valor en raiz es 0
		return derivada;
	}
}