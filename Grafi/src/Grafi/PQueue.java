package Grafi;

import java.util.HashMap;

/**
 * Implementazione di coda con priorita' con mappa hash.
 * La chiave e' un numero reale key rappresentato con un tipo double.
 * Il valore associato e' un oggetto di tipo E.
 * 
 * Gli oggetti nella coda sono UNICI (uno stesso oggetto non puo' comparire piu' 
 * di una volta).
 * I valori associati agli oggetti non sono univocamente assegnati, in quanto si vuole 
 * che piu' oggetti abbiano la possibilita' di avere la stessa priorita'.
 * 
 * @author Alessandro Straziota
 * @version 1.0
 * */

public class PQueue<E> {
		private HashMap<E,Double> mappa;
		
		/**
		 * Costruttore di classe
		 * */
		public PQueue(){
			this.mappa = new HashMap<E,Double>();
		}
		
		
		public boolean insert(double key, E value) {
			try {
				this.mappa.put(value, key);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		public boolean insert(int key, E value) {
			try {
				this.mappa.put(value, (double)key);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		public boolean insert(float key, E value) {
			try {
				this.mappa.put(value, (double)key);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		/**
		 * Modifica la chiave dell'olemento elem col nuovo valore newKey
		 * 
		 * @param newKey nuova chiave
		 * @param elem elemento al quale si desidera modificare la priorita'
		 * 
		 * @return true se la modifica va a buon fine, false altrimenti.
		 * */
		public boolean changeKey(double newKey, E elem) {
			try {
				this.mappa.remove(elem);
				this.insert(newKey, elem);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		/* linear cost */
		/**
		 * Metodo che restituisce il valore con chiave minima, senza pero'
		 * rimuoverlo dalla coda.
		 * 
		 * Il costo del metodo e' lineare nella lunghezza della coda.
		 * 
		 * */
		public E getMin() {
			E min = null;
			double v = Double.POSITIVE_INFINITY;
			
			for (E e : this.mappa.keySet()) {
				if (this.mappa.get(e) < v) {
					v = this.mappa.get(e);
					min = e;
				}
			}
			return min;
		}

		
		/* linear cost */
		/**
		 * Metodo che rimuove e restituisce il valore con chiave minima.
		 * 
		 * Il costo del metodo e' lineare nella lunghezza della coda.
		 * 
		 * */
		public E deleteMin() {
			E min = this.getMin();
			this.mappa.remove(min);
			return min;
		}
		
		/**
		 * Svuota completamente la coda.
		 * 
		 * Costo del metodo costante.
		 * */
		public void clear() { this.mappa = new HashMap<E,Double>(); }
		
		/**/
		/**
		 * Rimuove l'elemento v nella coda.
		 * 
		 * @param elem elemento che si vuole rimuovere
		 * */
		public void remove(E elem) { this.remove(elem); }
		
		/**
		 * Ritorna l'elemento con chiave key, senza rimuoverlo dalla coda.
		 * 
		 * @param key chiave
		 * @return valore al quale e' associata la chiave key
		 * */
		@SuppressWarnings("unchecked")
		public E get(double key) { return (E) mappa.get(key); }
		@SuppressWarnings("unchecked")
		public E get(float key) { return (E) mappa.get(key); }
		@SuppressWarnings("unchecked")
		public E get(int key) { return (E) mappa.get(key); }
		@SuppressWarnings("unchecked")
		public E get(long key) { return (E) mappa.get(key); }
		
		/**
		 * @return true se la coda e' vuota, false altrimenti
		 * */
		public boolean isEmpty() { return this.mappa.isEmpty(); }
		
		
		public String toString() {
			return this.mappa.toString();
		}
		
		
	}