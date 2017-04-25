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
public class insertstar
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
	@Procedure(value = "myproc.insertstar", mode = Mode.WRITE)
	@Description("myproc.insertstar( n ) - create a star graph with n nodes.")
    public void insertstar(@Name("n") Long n)
    {
        System.out.println("Prepare to insert a star graph with " + n + " nodes");
        int i, j; 
        // create nodes
        for (i = 1; i <= n; i++) {
            graphDB.createNode(Labels.Node);
        }
        // create relationships
            Node source = graphDB.getNodeById(1);
            for (j = 2; j<= n; j++) {
                Node dest = graphDB.getNodeById(j);
                source.createRelationshipTo(dest, RelationshipTypes.connected);
            }
    	return;
    }
}
