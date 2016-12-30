package jgrapht.tests;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

public class JGraphTTests {

	@Test
	public void test() {
		
		DirectedGraph<String, DefaultEdge> g =
	            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		
		g.addVertex("root");
		g.addVertex("0");
		g.addEdge("root", "0");
		g.addVertex("1");
		g.addEdge("root", "1");

	}
	
	//private split();

}
