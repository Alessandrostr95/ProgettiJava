package Grafi;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe nodo, utile per l'implementazione della classe Grafo.
 * 
 * @author Alessandro Straziota
 * @version 1.0
 * */
public class Nodo<E> {
	private String nome;
	private E contenuto;
	private List<Nodo<E>> adiacenza = new LinkedList<>();
	private boolean mark = false;
	public double ds;
	
	/**
	 * Costruttore di classe che crea un nodo di tipo E, identificato con la stringa nome.
	 * @param nome nome del nodo
	 * @param contenuto oggetto contenuto all'interno del nodo
	 * */
	public Nodo(String nome, E contenuto) {
		this.nome = nome;
		this.contenuto = contenuto;
	}

	public String getNome() { return nome; }

	public E getContenuto() { return contenuto; }

	public List<Nodo<E>> getAdiacenti() { return adiacenza; }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void append(Nodo e) { adiacenza.add(e); }
	
	public String toString() { return nome; }
	
	public void mark() { mark = true; }
	
	public void unmark() { mark = false; }
	
	public boolean isMarked() { return mark; }

}
