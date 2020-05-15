package Grafi;

public class QuickFind {

	private Grafo QF = new Grafo();
	
	/**
	 * Costruttore di classe.
	 * */
	public QuickFind() { super(); }
	
	/**
	 * Metodo makeSet
	 * */
	public void makeSet( Object x ) {
		Nodo<Integer> padre = new Nodo<Integer>("\""+x.toString()+"\"", 1);
		Nodo<Object> nodox = new Nodo<Object>(x.toString(), x);
		
		QF.add(nodox,padre);
		QF.arco(nodox,padre);
		
		padre.mark();
	}
	
	/**
	 * sia x un elemento appartenente al dominio del problema,
	 * l’operazione di Find restituisce l’etichetta associata all’insieme
	 * contenente l’elemento x.
	 * */
	public String find( Object x ) {
		Nodo<?> nx = QF.grafo.get(x.toString());
		return nx.getAdiacenti().get(0).getNome().replace("\"", "");
	}
	
	/**
	 * siano A e B due etichette rappresentanti due insiemi di QF,
	 * l’operazione di Union unisce gli elementi dei due insiemi in un unico
	 * insieme che sarà denominato A, infine, cancella i vecchi insiemi A,B;
	 * */
	public void union(String A, String B) {
		@SuppressWarnings("unchecked")
		Nodo<Integer> setA = (Nodo<Integer>) this.QF.grafo.get("\"" + A + "\"");
		@SuppressWarnings("unchecked")
		Nodo<Integer> setB = (Nodo<Integer>) this.QF.grafo.get("\"" + B + "\"");
		
		int sizeA = setA.getContenuto();
		int sizeB = setB.getContenuto();
		
		//TODO da sistemare
		
		for ( Nodo<?> v : setB.getAdiacenti() ) {
			QF.arco(v,setA);
		}
		QF.rimuovi(setB);
		setA.setContenuto(sizeA+sizeB);
		
	}
	
	/**
	 * Metodo che mostra a schermo per ogni oggetto a quale padre e' associato.
	 * */
	public void mostra() {
		System.out.println("\n");
		for(String key : QF.grafo.keySet()) {
			
			Nodo<?> n = QF.grafo.get(key);
			
			if( n.isMarked() )
				System.out.println(n + " ---> " + n.getAdiacenti());
		}
		System.out.println("\n");
	}
}
