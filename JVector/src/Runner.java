
public class Runner {

	public static void main(String[] args) {
		
		Vector a = new Vector(-1,2,3);
		Vector b = new Vector(0,4,-2);
		Vector c = new Vector(0,1,2);
		Vector d = new Vector(1,-2,-3);
		
		
		Matrix m = new Matrix(a,b,c,d);
		System.out.println(m);
		var m2 = Matrix.copy(m);
		m2.transpose();
		System.out.println(m2);
		
		System.out.println(Matrix.mult(m, m2));
		
		
		Vector i = new Vector(1,-1,2);
		Vector j = new Vector(1,2,3);
		System.out.println(Vector.vectMult(i, j));
		
	}
}
