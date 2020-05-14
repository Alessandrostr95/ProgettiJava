package Grafi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Classe Grafo che implementa tutte le operazioni piu comuni sui grafi,
 * dall'aggiunta di archi e nodi, alle visite, al calcolo delle distanze minime.
 * 
 * @author Alessandro Straziota
 * @version 1.0
 * */

public class Grafo {
	protected Map<String,Nodo<?>> grafo = new HashMap<>();
	protected Map<String,Double> pesi = new HashMap<>();
	protected boolean pesato = false;
	
	/**
	 * Costruttore di classe.
	 * @return Grafo.
	 * */
	public Grafo() { super(); }
	
	
	/* ========= AGGIUNTA DI NODI ========= */
	/**
	 * Aggiunge una serie di nodi al grafo, ognuno identificato dal prorpio nome.
	 * @param Nodi collezione di nodi che si vuole aggiungere al grafo.
	 * */
	public void add(Nodo<?> ... Nodi) {
		for( Nodo<?> v : Nodi ) {
			grafo.put(v.getNome(), v);
		}
	}
	
	/**
	 * Data una serie di stringhe, aggiunge al grafo dei nodi, ognuno 
	 * identificato dalla stringa ad essa associata.
	 * @param Nodi insieme di stringhe alle quali poi verranno creati ed associati dei
	 * nodi del grafo.
	 * */
	public void add(String ... Nodi) {
		for( String v : Nodi ) {
			grafo.put(v, new Nodo<Object>(v, null));
		}
	}
	
	
	/**
	 * Data una serie di oggetti, per ognuno di essi il metodo aggiunge al grafo dei nodi,
	 * ognuno contenente al suo interno l'ogetto stesso ed etichettato col puntatore dell'oggetto
	 * che contiene.
	 * @param Nodi insieme di oggetti ai quali poi verranno creati ed associati dei
	 * nodi del grafo.
	 * */
	public void add(Object ... Nodi) {
		for( Object v : Nodi ) {
			grafo.put(v.toString(), new Nodo<Object>(v.toString(), v));
		}
	}
	
	
	
	
	/* ========= AGGIUNTA DI ARCHI ========= */
	/**
	 * Crea un arco tra due dati nodi del grafo, rendendoli adiacenti.
	 * @param a
	 * @param b
	 * */
	public void arco(Nodo<?> a, Nodo<?> b) {
		a.append(b);
		b.append(a);
		pesi.put(a.getNome() + "," + b.getNome() , (double)0);
		pesi.put(b.getNome() + "," + a.getNome() , (double)0);
	}
	
	
	/**
	 * Crea un arco pesato tra due dati nodi del grafo, rendendoli adiacenti.
	 * @param a
	 * @param b
	 * @param w peso dell'arco
	 * */
	public void arco(Nodo<?> a, Nodo<?> b, double w) {
		a.append(b);
		b.append(a);
		pesi.put(a.getNome() + "," + b.getNome() , w);
		pesi.put(b.getNome() + "," + a.getNome() , w);
		pesato = true;
	}
	
	/**
	 * Dati due oggetti crea un arco tra i due nodi ad essi associati,
	 * rendendoli adiacenti.
	 * @param a
	 * @param b
	 * */
	public void arco(Object a, Object b) {
		Nodo<?> na = this.grafo.get(a.toString());
		Nodo<?> nb = this.grafo.get(b.toString());
		if( na != null && nb != null ) {
			na.append(nb);
			nb.append(na);
			pesi.put(na.getNome() + "," + nb.getNome() , (double)0);
			pesi.put(nb.getNome() + "," + na.getNome() , (double)0);
		} else {
			System.err.print("Uno o entrambi gli oggetti referenziati non è presente nel grafo.");
		}
	}
	
	/**
	 * Dati due oggetti crea un arco pesato tra i due nodi ad essi associati,
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
			nb.append(na);
			pesi.put(na.getNome() + "," + nb.getNome() , w);
			pesi.put(nb.getNome() + "," + na.getNome() , w);
			pesato = true;
		} else {
			System.err.print("Uno o entrambi gli oggetti referenziati non è presente nel grafo.");
		}
	}
	
	
	
	
	public void mostra() {
		for(String key : grafo.keySet()) {
			
			System.out.println(grafo.get(key) + " ---> " +
					grafo.get(key).getAdiacenti());
		}
	}
	
	
	/**
	 * Metodo che togli il marchio da tutti i nodi del grafo.
	 * */
	public void unmarkAll() {
		for(Nodo<?> v : grafo.values())
			v.unmark();
	}
	
	/**
	 * Metodo che marchia tutti i nodi del grafo.
	 * */
	public void markAll() {
		for(Nodo<?> v : grafo.values())
			v.mark();
	}
	
	/**
	 * Breadth-first search, BFS
	 * @param s sorgente
	 * */
	public void BFS(Nodo<?> s) {
		
		this.unmarkAll();
		
		System.out.println("Visita BFS:");
		
		@SuppressWarnings("rawtypes")
		Queue<Nodo> coda = new LinkedList<>();
		s.mark();
		coda.add(s);
		
		Nodo<?> u = null;
		
		while( !(coda.isEmpty()) ) {
			u = coda.poll();
			
			System.out.print(" - " + u.getNome());
				
			for(Nodo<?> v: u.getAdiacenti()) {
				if( !v.isMarked() ) {
					coda.add(v);
					v.mark();
					}
			}
		}
		System.out.println();
	}
	
	
	/**
	 * Deep-first search, DFS
	 * @param s sorgente
	 * */
	public void DFS(Nodo<?> s) {
		
		this.unmarkAll();
		
		System.out.println("Visita DFS:");
		
		@SuppressWarnings("rawtypes")
		Stack<Nodo> pila = new Stack<>();
		s.mark();
		pila.push(s);
		
		Nodo<?> u = null;
		
		while( !(pila.isEmpty()) ) {
			u = pila.pop();
			
			System.out.print(" - " + u.getNome());
				
			for(Nodo<?> v: u.getAdiacenti()) {
				if( !v.isMarked() ) {
					pila.push(v);
					v.mark();
					}
			}
		}
		System.out.println();
	}

	/**
	 * Dijkstra's algorithm
	 * @param s sorgente
	 * */
	public void Dijkstra(Nodo<?> s) {
		
		for(Nodo<?> v : this.grafo.values()) {
			v.ds = Double.POSITIVE_INFINITY;
		}
		s.ds = 0;
		
		PQueue<Nodo<?>> Q = new PQueue<Nodo<?>>();
		Q.insert(s.ds, s);
		PQueue<Nodo<?>> Visita = new PQueue<Nodo<?>>();
		
		while( ! Q.isEmpty() ) {
			
			var u = Q.deleteMin();
			Visita.insert(u.ds, u);
			
			for(Nodo<?> v : u.getAdiacenti()) {
				
				if( v.ds == Double.POSITIVE_INFINITY) {
					v.ds = u.ds + pesi.get(u.getNome()+","+v.getNome());
					Q.insert(v.ds, v);
				} else if ( u.ds + pesi.get(u.getNome()+","+v.getNome()) < v.ds ) {
					v.ds = u.ds + pesi.get(u.getNome()+","+v.getNome());
					Q.changeKey(v.ds, v);
				}
				
			}
			
		}
		
		System.out.println("Dijkstra:\n"+Visita);

	}
}
