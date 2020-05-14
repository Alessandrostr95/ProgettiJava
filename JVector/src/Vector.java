public class Vector {
	private float[] vector;
	private int dim = 0;
	
	/**
	 * Costruttore di classe che crea un vettore dati una sequenza di valori numerici.
	 * 
	 * @param float valori del vettore
	 * */
	public Vector(float ...valori) { 
		this.vector = valori;
		this.dim = valori.length;
		}
	
	/**
	 * Metodo statico di classe che ritorna un vettore nullo di dimenzione dim.
	 * 
	 * @param int dim: dimensione.
	 * @return Vector O = origin.
	 * */
	public static Vector origin(int dim) {
		float[] o = new float[dim];
		for (int i = 0; i < dim; i++) {
			o[i] = 0;
		}
		return new Vector(o);
	}
	
	/* somma il vettore v al vettore chiamante */
	/**
	 * Metodo che somma un vettore v al vettore chiamante.
	 * 
	 * @semantica u.add( v ) <==> u = u + v
	 * 
	 * @param Vector v
	 * 
	 * @throws IncompatibleDimensionException quando i due vettori sono di dimensione differente
	 * */
	public void add(Vector v) {
		
		if ( this.dim != v.dim ) {
			throw new IncompatibleDimensionException("Dimensioni incompatibili");
		} else {
			
			for (int i = 0; i < this.dim; i++) {
				this.vector[i] = this.vector[i] + v.vector[i];
			}
		}
		
	}
	
	/* restituisce la somma dei vettori x e y, senza però modificarli */
	/**
	 * Metodo statico che restituisce la somma dei vettori x e y, senza però modificarli.
	 * 
	 * @param Vector x
	 * @param Vector y
	 * 
	 * @throws IncompatibleDimensionException quando i due vettori sono di dimensione differente
	 * 
	 * @return Vector z = x + y
	 * */
	public static Vector add(Vector x, Vector y) {
		
		if ( x.dim != y.dim ) {
			throw new IncompatibleDimensionException("Dimensioni incompatibili");
		} else {
			float []z = new float[x.dim];
			for (int i = 0; i < x.dim; i++) {
				z[i] = x.vector[i] + y.vector[i];
			}
			return new Vector(z);
			
		}
		
	}
	
	/* prodotto per scalare */
	/**
	 * Scala il vettore chiamante della quantita' t indicata come parametro
	 * 
	 * @param float
	 * 
	 * @semantica v.mult(t) <==> v = t*v
	 * */
	public void mult(float t) {
		for (int i = 0; i < this.dim; i++) {
			this.vector[i] = this.vector[i]*t;
		}
	}
	
	/* restituisce il prodotto per scalare di un vettore v */
	/**
	 * Metodo statico che ritorna il prodotto per scalare di un vettore v, senza modificarlo.
	 * 
	 * @param Vector
	 * @param float
	 * 
	 * @return Vector u = t*v;
	 * */
	public static Vector mult(Vector v, float t) {
		float w[] = new float[v.dim];
		for (int i = 0; i < v.dim; i++) {
			w[i] = v.vector[i]*t;
		}
		return new Vector(w);
	}
	
	/* sottrae il vettore v al vettore chiamante */
	/**
	 * Metodo analoggo alla somma vettoriale.
	 * 
	 * @see Vector.add(Vector v)
	 * */
	public void sub(Vector v) { 
		this.add(Vector.mult(v, -1));
	}
	
	/* restituisce la differenza dei vettori x e y, senza però modificarli */
	/**
	 * Metodo statico analoggo alla somma vettoriale.
	 * 
	 * @see Vector.add(Vector x, Vector y)
	 * */
	public static Vector sub(Vector x, Vector y) {
			return Vector.add(x, Vector.mult(y, -1));
	}
	
	/* rapporto per scalare */
	/**
	 * Metodo analogo al prodotto per scalare
	 * 
	 * @see Vector.mult(float t)
	 * */
	public void div(float t) {
		this.mult((float)1/t);
	}
	
	/* restituisce il prodotto per scalare di un vettore v */
	/**
	 * Metodo statico analogo al prodotto per scalare
	 * 
	 * @see Vector.mult(Vector v, float t)
	 * */
	public static Vector div(Vector v, float t) {
		return Vector.mult(v,(float)1/t);
	}
	
	/* ======== prodotto scalare ======== */
	/**
	 * Metodo che moltiplica un vettore v al vettore chiamante.
	 * 
	 * @param Vector
	 * @throws IncompatibleDimensionException qualora le dimensioni
	 * fossero incompatibili
	 * 
	 * @semantica u.mult(v) <==> u = u*v
	 * */
	public void mult(Vector v) {
		
		if( this.dim != v.dim ) {
			
			throw new IncompatibleDimensionException("Dimensioni incompatibili");
		
		} else {

			for (int i = 0; i < this.dim; i++) {
				this.vector[i] = this.vector[i]*v.vector[i];
			}
		}
	}
	
	public static Vector mult(Vector x, Vector y) {
		if( x.dim != y.dim ) {
			
			throw new IncompatibleDimensionException("Dimensioni incompatibili");
		
		} else {
			
			float[] z =  new float[x.dim];

			for (int i = 0; i < x.dim; i++) {
				z[i] = x.vector[i]*y.vector[i];
			}
			
			return new Vector(z);
			
		}
	}
	
	/* ====== PRODOTTO VETTORIALE ====== */
	
	public static Vector vectMult(Vector v, Vector w) {
		if ( v.dim() != 3 || w.dim() != 3 ) throw new IncompatibleDimensionException("The vectors must be 3-dimentional");

		return new Vector(	v.vector[1]*w.vector[2] - v.vector[2]*w.vector[1],
							v.vector[2]*w.vector[0] - v.vector[0]*w.vector[2],
							v.vector[0]*w.vector[1] - v.vector[1]*w.vector[0]);
	}
	
	
	/* ================================= */
	
	/* lunghezza del vettore */
	public float magnitude() {
		double sum = 0;
		double p = 0;
		for (int i = 0; i < this.dim; i++) {
			p = Math.pow(this.vector[i], 2);
			sum = sum + p;
		}
		return (float) Math.sqrt(sum);
	}
	
	/* scala il vettore rendendolo unitario (lunghezza = 1) */
	public void normalize() {
		this.mult((float)1/this.magnitude());
	}
	
	/* ritorna il vettore normalizzato di v senza modificarlo */
	public static Vector normalized(Vector v) {
		Vector w = Vector.copy(v);
		w.normalize();
		return w;
	}
	
	/* ritorna il vettore normalizzato del chiamante senza modificarlo */
	public Vector normalized() {
		return Vector.normalized(this);
	}
	
	
	@Override
	public String toString() {
		String ret = "[";
		int i;
		for (i = 0; i < this.dim - 1; i++) {
			ret = ret + String.valueOf(this.vector[i]).concat(",\t");
		}
		
		return ret.concat(String.valueOf(this.vector[i])).concat("]");
	}
	
	/* ritorna la copia del vettore v */
	public static Vector copy(Vector v) {
		float[] a = new float[v.dim];
		int i = 0;
		for(float x : v.vector) {
			a[i] = x;
			i ++;
		}
		return new Vector(a);
	}
	
	/* ritorna un aeeay contenente gli elementi del vettore chiamante */
	public float[] toArray() {
		return this.vector;
	}
	
	/* ritorna la dimensione del vettore inteso come numero di elementi */
	public int dim() {
		return this.dim;
	}
	
	/* ritorna l'indice del primo elemento diverso da 0, altrimenti ritorna -1 */
	public int findNotZero() {
		for(int i = 0; i < this.dim; i++) {
			if( this.vector[i] != 0 ) return i;
		}
		return -1;
	}
	
	/* ritorna un vettore di dimensione dim con un 1 alla posizione i */
	public static Vector vector1D(int dim, int i) {
		Vector e = Vector.origin(dim);
		e.vector[i] = 1;
		return e;
	}
	
	/* ritorna true se il vettore è nullo; false altrimenti */
	public boolean isNull() {
		if ( this.magnitude() == 0 ) return true;
		else return false;
	}
	
	/* ritorna l'i-esimo elemento del vettore */
	public float get(int i) {
		if ( i >= this.dim ) throw new IndexOutOfBoundsException(i);
		else return this.vector[i];
	}
	
	
	
}
