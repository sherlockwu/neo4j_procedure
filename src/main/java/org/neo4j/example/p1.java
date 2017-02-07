package myproc;

import java.util.List;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.ResourceIterator;

import org.neo4j.procedure.*;

/**
 * This is an example how you can create a simple user-defined function for Neo4j.
 */
public class p1
{
	@Context
    public GraphDatabaseService graphDB;    

	public enum RelationshipTypes implements RelationshipType {
    	connected;
    }

    public enum Labels implements Label {
    	node;
    }


	//@UserFunction
	@Procedure(value = "myproc.p1", mode = Mode.WRITE)
	@Description("myproc.p1(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public void p1(
            @Name("strings") List<String> strings,
            @Name(value = "delimiter", defaultValue = ",") String delimiter) {
		
		System.out.println("T01, here in the new procedure!");
        

        // Begin some search
            // Find all users
            ResourceIterator<Node> nodes = graphDB.findNodes( Labels.node );
            System.out.println( "Nodes:" );
            while( nodes.hasNext() )
            {
                Node node = nodes.next();
                System.out.println( "\t" + node.getProperty( "id" ) );
            }




		// return String.join(delimiter, strings);
    	return;
	}
}
