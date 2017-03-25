package myproc;

import java.util.List;
import java.util.Iterator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
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
public class readnodes
{
	@Context
    public GraphDatabaseService graphDB;    

	public enum RelationshipTypes implements RelationshipType {
    	connected;
    }

    public enum Labels implements Label {
    	Node;
    }


	//@UserFunction
	@Procedure(value = "myproc.readnodes", mode = Mode.WRITE)
	@Description("myproc.readnodes() - join the given strings with the given delimiter.")
    public void readnodes(){
            //@Name("strings") List<String> strings,
            //@Name(value = "delimiter", defaultValue = ",") String delimiter) {
		
		System.out.println("here in readnodes!");
        

        // Begin some search
        // read in some users
            System.out.println( "Nodes:" );
            int node_id = 14;
	    int number_nodes = 10000;
	    while( number_nodes > 0 )
      	{
			Node node_curr = graphDB.findNode(Labels.Node, "id", node_id);
			node_id+=1000;
			if (node_curr!=null) {
            			System.out.println( "\t" + node_curr.getProperty( "id" ) );
        		}
		number_nodes--;
		}
		
		return;
	}
}
