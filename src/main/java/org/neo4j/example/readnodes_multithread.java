package myproc;

import java.util.List;
import java.util.Iterator;
import java.lang.Thread;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Path;

import org.neo4j.procedure.*;

/**
 * This is an example how you can create a simple user-defined function for Neo4j.
 */
public class readnodes_multithread
{
	@Context
    public GraphDatabaseService graphDB;    

	public enum RelationshipTypes implements RelationshipType {
    	connected;
    }

    public enum Labels implements Label {
    	Node;
    }


	class Print_Thread implements Runnable {
		// constructor
			// set number
		private int start, end;
		private Thread t;

		Print_Thread(int st, int ed) {
			start = st;
			end = ed;
		}

		// start
			// new thread, start thread
		public void start() {
			System.out.println("=== starting " + start);
			t = new Thread(this);
			t.start();			
		}
		// run
			// print out things		
		public void run() {
	 		Transaction tx = graphDB.beginTx();
			try
			{
				for(int node_id=start; node_id<end; node_id++ ) {
					Node node_curr = graphDB.findNode(Labels.Node, "id", node_id);
					node_id+=1000;
			if (node_curr!=null) {
            			System.out.println( "\n=== in thread " + start + " get: " + node_curr.getProperty( "id" ) );
        		}
				}
         		System.out.println("!!!=== Thread " +  start + " exiting ===!!!");
			} finally {
				tx.success();
			}
		}	

	}





	//@UserFunction
	@Procedure(value = "myproc.readnodes_multithread", mode = Mode.WRITE)
	@Description("myproc.readnodes_multithread(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public void readnodes_multithread(){
      	//@Name("strings") List<String> strings,
    	//@Name(value = "delimiter", defaultValue = ",") String delimiter) {
		
		System.out.println("here in readnodes with multithread!");
        

        // Begin some search
        // read in some users
        System.out.println( "Nodes:" );
        int node_id = 14;
		int number_nodes = 10000;
			
		// threads
		Print_Thread t1 = new Print_Thread(14, 5000000);
		t1.start();
		
		Print_Thread t2 = new Print_Thread(5000001, 10000000);
		t2.start();
		
		return;
	}
}
