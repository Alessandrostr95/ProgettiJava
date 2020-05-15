package Grafi;

/**
 * Sottoclasse di Grafo, il DiGrafo e' l'implementazione di un grafo diretto.
 * Tutti gli archi avranno quindi una direzione.
 * 
 * @author Alessandro Straziota
 * @version 1.0
 * */

public class DiGrafo extends Grafo {
	
	/**
	 * Costruttore di classe
	 * */
	public DiGrafo() { super(); }
	
	/**
	 * Crea un arco diretto tra due dati nodi del grafo, rendendoli adiacenti.
	 * @param a
	 * @param b
	 * */
	public void arco(Nodo<?> a, Nodo<?> b) {
		a.append(b);
		pesi.put(a.getNome() + "," + b.getNome() , (double)0);
	}
	
	/**
	 * Crea un arco diretto e pesato tra due dati nodi del grafo, rendendoli adiacenti.
	 * @param a
	 * @param b
	 * @param w peso dell'arco
	 * */
	public void arco(Nodo<?> a, Nodo<?> b, double w) {
		a.append(b);
		pesi.put(a.getNome() + "," + b.getNome() , w);
	}
	
	/**
	 * Dati due oggetti crea un arco diretto tra i due nodi ad essi associati,
	 * rendendoli adiacenti.
	 * @param a
	 * @param b
	 * */
	public void arco(Object a, Object b) {
		Nodo<?> na = this.grafo.get(a.toString());
		Nodo<?> nb = this.grafo.get(b.toString());
		if( na != null && nb != null ) {
			na.append(nb);
			pesi.put(na.getNome() + "," + nb.getNome() , (double)0);
		} else {
			System.err.print("Uno o entrambi gli oggetti referenziati non è presente nel grafo.");
		}
	}
	
	/**
	 * Dati due oggetti crea un arco diretto e pesato tra i due nodi ad essi associati,
	 * rendendoli adiacenti.
	 * @param a
	 * @param b
	 * @param w peso dell'arco
	 * */
	public void arco(Object a, Object b, double w) {
		Nodo<?> na = this.grafo.get(a.toString());
		Nodo<?> nb = this.grafo.get(b.toString());
		if( na != null && nb != null ) {
			na.append(nb);
			pesi.put(na.getNome() + "," + nb.getNome() , w);
			pesato = true;
		} else {
			System.err.print("Uno o entrambi gli oggetti referenziati non è presente nel grafo.");
		}
	}
	
	
	/**
	 * Metodo che stacca i due nodi indicati nel grafo.
	 * */
	public void stacca(Nodo<?> a, Nodo<?> b) {
		a.stacca(b);
		pesi.remove(a.getNome() + "," + b.getNome());
	}
	
	/**
	 * Metodo che stacca i due nodi indicati nel grafo.
	 * */
	public void stacca(Object a, Object b) {
		Nodo<?> na = this.grafo.get(a.toString());
		Nodo<?> nb = this.grafo.get(b.toString());
		
		if( na != null && nb != null ) {
			na.stacca(nb);
			pesi.remove(na.getNome() + "," + nb.getNome());
		} else {
			System.err.print("Uno o entrambi gli oggetti referenziati non è presente nel grafo.");
		}
	}
	
	
	/**
	 * Metodo che rimuove un nodo da un grafo.
	 * */
	public void rimuovi(Nodo<?> v) {
		/* stacco v dagli altri nodi ad esso adiacenti */
		for( Nodo<?> u : v.getAdiacenti() ) {
			u.getAdiacenti().remove(v);
			pesi.remove(v.getNome() + "," + u.getNome());
			pesi.remove(u.getNome() + "," + v.getNome());
		}
		grafo.remove(v.getNome());
	}

}
