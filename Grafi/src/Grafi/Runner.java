package Grafi;

public class Runner {

	public static void main(String[] args) {
		
		Nodo<Integer> 	a = new Nodo<Integer>("a",1),
						b = new Nodo<Integer>("b",1),
						c = new Nodo<Integer>("c",1),
						d = new Nodo<Integer>("d",1),
						e = new Nodo<Integer>("e",1);
		
		Grafo G = new Grafo();
		
		G.add(a);
		G.add(b,c,d,e);
		
		G.arco(a, b, 5);
		G.arco(b, c, 0.3);
		G.arco(a, d, 12.5);
		G.arco(c, d, 2.22);
		G.arco(c, e, 10);
		G.arco(b, e, 3.1415);
		
		G.mostra();
		G.BFS(c);
		G.DFS(a);

		G.Dijkstra(e);
		
		
		Nodo<Integer> 	a1 = new Nodo<Integer>("a",1),
						b1 = new Nodo<Integer>("b",1),
						c1 = new Nodo<Integer>("c",1),
						d1 = new Nodo<Integer>("d",1),
						e1 = new Nodo<Integer>("e",1);

		DiGrafo G1 = new DiGrafo();

		G1.add(a1,b1,c1,d1,e1);

		G1.arco(a1, b1, 5);
		G1.arco(b1, c1, 0.3);
		G1.arco(a1, d1, 12.5);
		G1.arco(c1, d1, 2.22);
		G1.arco(c1, e1, 10);
		G1.arco(b1, e1, 3.1415);
		
		G1.mostra();
		G1.BFS(c1);
		G1.DFS(a1);
		
		G1.Dijkstra(e1);
		
		String asd = "adasdasd";
		G.add("adasdasd");
		G.add(G1);
		G.arco(asd, G1,0);
		G.arco(a, G1, 0.3);
		G.mostra();
		System.out.println(G1);
		G.Dijkstra(a);
		
	}

}
