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
public class insertgraph
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
	@Procedure(value = "myproc.insertgraph", mode = Mode.WRITE)
	@Description("myproc.insertgraph( n ) - create a fully connected graph with n nodes.")
    public void insertgraph(@Name("n") Long n)
    {
        System.out.println("Prepare to insert a fully-connected graph with " + n + " nodes");
        long i, j; 
        // create nodes
        for (i = 1; i <= n; i++) {
            graphDB.createNode(Labels.Node);
        }
        // create relationships
	for (i = 1; i <= n-5; i++) {
            Node source = graphDB.getNodeById(i);
            for (j = i+1; j<= n-5; j++) {
                Node dest = graphDB.getNodeById(j);
                source.createRelationshipTo(dest, RelationshipTypes.connected);
            }
        }
	// insert again
        for (i = 1; i <= n-5; i++) {
            Node source = graphDB.getNodeById(i);
            for (j = n-4; j<= n; j++) {
                Node dest = graphDB.getNodeById(j);
                source.createRelationshipTo(dest, RelationshipTypes.connected);
            }
        }
    	return;
    }
}
