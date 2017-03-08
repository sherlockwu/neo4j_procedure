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
public class sp
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
	@Procedure(value = "myproc.sp", mode = Mode.WRITE)
	@Description("myproc.sp(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public void sp(@Name("startnode") Long start_id, @Name("endnode") Long end_id){
            //@Name("strings") List<String> strings,
            //@Name(value = "delimiter", defaultValue = ",") String delimiter) {
		
		System.out.println("T01, here in the new procedure!");
        

        // Begin some search
        /*    // Find all users
            ResourceIterator<Node> nodes = graphDB.findNodes( Labels.node );
            System.out.println( "Nodes:" );
            while( nodes.hasNext() )
            {
                Node node = nodes.next();
                System.out.println( "\tsp:" + node.getProperty( "id" ) );
            }
			System.out.println("Hello, World!");	
		*/
		// find nodes according to ID
			System.out.println(String.format("Start finding shortestpath from %d to %d", start_id, end_id));
			Node node_start = graphDB.findNode(Labels.node, "id", start_id);
			Node node_end = graphDB.findNode(Labels.node, "id", end_id);
		// Try to get the shortestpath
			PathExpander<Object> pathExpander = PathExpanders.allTypesAndDirections();
			PathFinder<Path> shortestPath = GraphAlgoFactory.shortestPath(pathExpander,10);
        // find all paths between given two nodes
			Path path_result = shortestPath.findSinglePath(node_start, node_end);
			Iterator<Node> nodes = path_result.nodes().iterator();
			while (nodes.hasNext()){
				Node next = nodes.next();
				System.out.println(next.getProperty( "id" ));
			}
				
			System.out.println("find a short path:");
			System.out.println(path_result);
		// return String.join(delimiter, strings);
    	return;
	}
}
