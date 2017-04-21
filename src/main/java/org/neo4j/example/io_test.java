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
public class io_test
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
	@Procedure(value = "myproc.io_test", mode = Mode.WRITE)
	@Description("myproc.io_test() - traverse all relationships of a node.")
    public void io_test(@Name("nodeid") Long NodeId){
        System.out.println("here in io_test");

        // read in some users
        //System.out.println( "Node:" );
	Node node_curr = graphDB.getNodeById(NodeId);
	//long id = (long) node_curr.getProperty("id");
	//System.out.println("get Id: " + id);
            		
			/*int node_id = 14;
	   		Node node_curr = graphDB.findNode(Labels.Node, "id", node_id);
            		*/

	Iterable<Relationship> relations_attached = node_curr.getRelationships();
	System.out.println("begin traversing: ");
        for (Relationship relation:relations_attached){
		Node node_1 = relation.getStartNode();
		Node node_2 = relation.getEndNode();
		System.out.println("relationship " + relation.getId() + "(" + node_1 + ", " + node_2 + ")");
	}
			
   			//Relationship relation = graphDB.getRelationshipById(181870);
			//Node node_3 = graphDB.getNodeById(node_1.getId());
			
			//node_id = 1000;
	   		//node_curr = graphDB.findNode(Labels.Node, "id", node_id);
	return;
    }
}
