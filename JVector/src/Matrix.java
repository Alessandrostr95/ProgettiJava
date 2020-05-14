
public class Matrix {
	public Vector[] rows;
	private int r,c;
	
	public Matrix(Vector...vectors) {
		this.rows = vectors;
		
		this.r = vectors.length;
		this.c = vectors[0].dim();
		
	}
	
	/* ritorna il coefficiente in posizione (i,j) */
	public float get(int i, int j) {
		if (i >= r || j >= c) {
			throw new IllegalArgumentException("Indexs out of range.");
		} else {
			return rows[i].get(j);
		}
	}
	
	public String toString() {
		String ret = "";
		for(Vector r: this.rows) {
			ret = ret + r.toString() + "\n";
		}
		return ret;
	}
	
	public int numRows() { return this.r; }
	public int numCols() { return this.c; }
	
	/* inverte la riga R1 con la riga R2 */
	public void invertiRiga(int r1, int r2) {
		if (r1 >= r || r2 >= c) {
			throw new IllegalArgumentException("Indexs out of range.");
		} else {
			Vector v =  this.rows[r1];
			this.rows[r1] = this.rows[r2];
			this.rows[r2] = v;
		}
	}
	
	/* ritorna un array bidimensionale relativo alla matrice M chiamante */
	public float[][] toArray() {
		float[][] M = new float[this.r][this.c];
		for (int i = 0; i < this.r; i++) {
			M[i] = this.rows[i].toArray();
		}
		return M;
	}
	
	/* restituisce una matrice costruita dall'array bidimensionale M in input */
	public static Matrix toMatrix(float[][] M) {
		var righe = new Vector[M.length];
		
		for(int i = 0; i < M.length; i++) {
			righe[i] = new Vector(M[i]);
		}
		
		return new Matrix(righe);
	}
	
	/* traspone la matrice chiamante */
	public void transpose() {
		var M = this.toArray();
		
		/* il numero di righe di MT è uguale al numero di colonne di M */
		var rowsT = new Vector[this.c];
		
		for (int i = 0; i < this.c; i++) {
			/* le righe di MT saranno lunghe quanto il numero di righe di M */
			float[] appoggio = new float[this.r];
			
			for (int j = 0; j < this.r; j++) {
				appoggio[j] = M[j][i];
			}
			rowsT[i] = new Vector(appoggio);
		}

		this.rows = rowsT;
		int t = this.r;
		this.r = this.c;
		this.c = t;
	}
	
	/* ritorna la matrice trasposta di A, senza però modificarla */
	public static Matrix transposed(Matrix A) {
		Matrix AT = Matrix.copy(A);
		AT.transpose();
		return AT;
	}
	
	/* ritorna una matrice minore Ai,j */
	public Matrix minore(int I, int J) {
		if ( I >= this.r || J >= this.c ) throw new IllegalArgumentException("Indexs out of range.");
		else {
			int j = 0;
			int i = 0;
			
			float[][] appoggio = new float[this.r-1][this.c-1];
			
			while ( i < I ) {
				j = 0;
				while( j < J ) {
					appoggio[i][j] = this.get(i, j);
					j++;
				}
				j = J + 1;
				while( j < this.c ) {
					appoggio[i][j-1] = this.get(i, j);
					j++;
				}
				i++;
			}
			
			i = I + 1;
			
			while ( i < this.r ) {
				j = 0;
				while( j < J ) {
					appoggio[i-1][j] = this.get(i, j);
					j++;
				}
				j = J + 1;
				while( j < this.c ) {
					appoggio[i-1][j-1] = this.get(i, j);
					j++;
				}
				i++;
			}
			
			var righe_nuova_matrice = new Vector[this.r-1];
			for(int k = 0; k < appoggio.length; k++) {
				righe_nuova_matrice[k] = new Vector(appoggio[k]);
			}
			
			return new Matrix(righe_nuova_matrice);
		}
	}
	
	

	/* ====== MATRICE INVERSA ====== */
	
	/* ritorna il complemento algebrico dell'elemento a(i,j) della matrice chiamante */
	public float Cof(int i, int j) {	
		return (float) (Math.pow(-1, i+j+2)*this.minore(i, j).det());
	}
	
	/* ritorna la matrice dei complementi algebrici della matrice chiamante */
	public Matrix CofMatrix() {
		var CofM = new float[this.r][this.c];
		for(int i = 0; i < this.r; i++) {
			for(int j = 0; j < this.c; j++) {
				CofM[i][j] = this.Cof(i, j);
			}
		}
		return Matrix.toMatrix(CofM);
	}
	
	/* ritorna la matrice inversa della matrice chiamante */
	public Matrix inversa() {
		if ( ! this.isSquare() ) throw new IllegalArgumentException("Matrix must be squared");
		else {
			
			var det = this.det();
			
			/* caso in cui la matrice è 1x1 */
			if( this.r == 1 ) return new Matrix( new Vector( (float)1/det ) );
			
			
			if ( det == 0 ) throw new SingularMatrixException("Matrix is singular");
			Matrix inversa = this.CofMatrix();
			inversa.transpose();
			inversa.mult((float) 1/det);
			return inversa;
		}
	}
	
	
	
	/* ==== TRIANGOLARIZZAZIONE ==== */
	
	/* dato un pivot P, in posizione (riga,colonna), pone tutti i coefficienti nella stessa colonna
	 * e al di sotto di P pari a 0 tramite una serie di combinazioni lineari
	 *  */
	public void sistemaColonna(int riga, int colonna) {
		
		float p = this.get(riga, colonna);
		float first;
		for(int i = riga + 1; i < r; i++) {
			//first = this.get(i, colonna);
			if ( (first = this.get(i, colonna)) != 0 )
				this.rows[i].add( Vector.mult(this.rows[riga],-(float)(first/p)) );
		}
	}
	
	
	/* porta in testa la riga con il pivot più a sinistra possibile, nella sottomatrice a partire dalla riga i-esima */
	public void portaInAlto(int i) {
		int valore = rows[i].findNotZero();
		int t;
		for (int k = i + 1; k < r; k++) {
			t = rows[k].findNotZero();
			if ( t < valore && t != -1 ) {
				valore = t;
				this.invertiRiga(i, k);
			}			
		}
	}
	
	/* triangolarizza la matrice chiamante */
	public void triangolarizza() {
		int i = 0;
		int t;
		while( i < r ) {
			this.portaInAlto(i);
			if ( (t = rows[i].findNotZero()) != -1 )
				this.sistemaColonna(i, t);
			i++;
		}
	}
	
	
	/* ====== DETERMINANTE ====== */
	
	/* ritorna il determinante della matrice chiamante */
	public float det() {
		if( this.r != this.c ) {
			throw new IllegalArgumentException("Matrix must be squared");
		} else {
			
			if( this.r == 2 ) return (this.get(0, 0)*this.get(1, 1)) - (this.get(0, 1)*this.get(1, 0));
			
			Matrix C = Matrix.copy(this);
			C.triangolarizza();
			float det = 1;
			for(int i = 0; i < this.c; i++) {
				det = det * C.get(i, i);
			}
			return det;
		}
	}
	
	/* diagonalizza la matrice chiamante */
	public void diagonalizza() throws IllegalArgumentException {
		if( this.r == this.c ) {
			this.triangolarizza();
			this.transpose();
			this.triangolarizza();
		} else
			throw new IllegalArgumentException("Matrix must be squared");
	}

	
	/* ======= OPERAZIONI ======= */
	
	/* ritorna una copia della matrice M in input */
	public static Matrix copy(Matrix M) {
		Vector[] a = new Vector[M.r];
		int i = 0;
		for (Vector r : M.rows) {
			a[i] = Vector.copy(r);
			i++;
		}
		return new Matrix(a);
	}
	
	/* ritorna la matrice identita' di dimensione "dim" */
	public static Matrix identity(int dim) {
		Vector[] v = new Vector[dim];
		
		for(int i = 0; i < dim; i++) {
			v[i] = Vector.vector1D(dim, i);
		}
		
		return new Matrix(v);
		
	}
	
	/* Somma la matrice M2 alla matrice M1 */
	public void add(Matrix M2) {

		if( r == M2.r && c == M2.c ) {
			for( int i = 0; i < r; i++ )
				this.rows[i].add(M2.rows[i]);
		}
	}
	
	/* Ritorna il valore della somma tra la matrice M1 e M2, senza modificarle */
	public static Matrix add(Matrix M1, Matrix M2) {
		Matrix C = Matrix.copy(M1);
		C.add(M2);
		return C;
	}
	
	/* controllare */
	@Deprecated
	public boolean isSingular() {
		Matrix m = Matrix.copy(this);
		m.diagonalizza();
		for(Vector r: m.rows) {
			if (r.isNull()) return true;
		}
		return false;
	}
	
	
	/* moltiplica t alla matrice chiamante */
	public void mult(float t) {
		for (Vector riga : this.rows) {
			riga.mult(t);
		}
	}
	
	/* Ritorna il valore del prodotto della matrice M per lo scalare t */
	public Matrix mult(Matrix M, float t) {
		Matrix C = Matrix.copy(M);
		C.mult(t);
		return C;
	}
	
	/* prodotto tra matrici */
	public static Matrix mult(Matrix A, Matrix B) {
		
		if ( A.numCols() != B.numRows() )
			throw new IncompatibleDimensionException("The number of A's columns must be equal to numbers of B's rows");
		
		var C = new float[A.numRows()][B.numCols()];
		
		for (int i = 0; i < A.numRows(); i++) {
			for (int j = 0; j < B.numCols(); j++) {
				C[i][j] = 0;
				for(int k = 0; k < A.numCols(); k++) {
					C[i][j] += A.get(i, k)*B.get(k, j);
				}
			}
			
		}
		return Matrix.toMatrix(C);
	}
	
	/* se la matrice chiamante è quadrata ritorna True, False altrimenti */
	public boolean isSquare() {
		if ( this.r == this.c ) return true;
		else return false;
	}
	
	
	/* !!!! DA TESTARE !!!! */
	/* potenza di una matrice */
	public static Matrix pow(Matrix a,int n) {
		switch(n) {
			case -1:
				return a.inversa();
			
			case 0:
				return Matrix.identity(n);
			
			default:
				Matrix a1 = Matrix.copy(a);
				
				for(int i = 0; i < n; i++) {
					a1 = Matrix.mult(a1, a);
				}
				
				return a1;
		}
	}
	

}
