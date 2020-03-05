class Nodo{
	String info;
	Nodo izq, der;
	Nodo(String x, Nodo y, Nodo z){
		info =x;
		izq=y;
		der=z;
	}
    Nodo(){
        info =null;
        izq=null;
        der=null;
    }
}