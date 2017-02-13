package myproc;

import java.util.*;
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
public class prim
{
	@Context
    public GraphDatabaseService graphDB;    

	public enum RelationshipTypes implements RelationshipType {
    	frontier,
		connected;
    }

    public enum Labels implements Label {
    	node,
		reached;
    }


	//@UserFunction
	@Procedure(value = "myproc.prim", mode = Mode.WRITE)
	@Description("myproc.prim(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public void prim(
            @Name("strings") List<String> strings,
            @Name(value = "delimiter", defaultValue = ",") String delimiter) {
		
		System.out.println("Here in procedure prim to find the min span tree!");

		/*
        // Begin some search
            // Find all users
            ResourceIterator<Node> nodes = graphDB.findNodes( Labels.Node );
            System.out.println( "Nodes:" );
            while( nodes.hasNext() )
            {
                Node node = nodes.next();
                System.out.println( "\t" + node.getProperty( "id" ) );
            }
		*/

		// Initialize
		// node 0 -> reached
			Node node_next = graphDB.findNode(Labels.node, "id", 13);
			System.out.println("get the node:"+ node_next);
			//node_next.setProperty( "reached", 1 );
			node_next.addLabel(Labels.reached);
		
		// EdgeFrontier 0->outedge
			//TODO load into mem/ add a property
			// just give one more property to nodes?
			// List edge_frontier = new ArrayList();
			int frontier_count = 0;
			for (Relationship line_from_node_next: node_next.getRelationships( RelationshipTypes.connected )) {
				// edge_frontier.add(line_from_node_next);
			 	// get end node
				Node EndNode = line_from_node_next.getEndNode();
			
				// create another relationship
				Relationship on_frontier = node_next.createRelationshipTo( EndNode, RelationshipTypes.frontier);	
				frontier_count++;
			}
		
		// System.out.println("the edge_frontiers: ");
		// System.out.println(edge_frontier);
		// While loop  edgelist not empty
		//while (!empty(edge_frontier)) {
		while (frontier_count>0) {
			// find a smallest relationship
			int smallest_weight = 999;
			for (Node n: graphDB.getAllNodesWithLabel(Labels.reached)){
				for (Relationship relationship: n.getRelationships(RelationshipTypes.frontier)){	
					// do stuff
					int weight_tmp = relationship.getProperty("weight");
					if (weight_tmp < smallest_weight) {
						smallest_weight = weight_tmp;
						node_next = relationship.getEndNode();
					}
				}
			}

			
			// add node and update edges(delete, add)
				// delete edges to this node, add edges out from this node to not reached nodes  
				for (Node n: graphDB.getAllNodesWithLabel(Labels.reached)){
					for (Relationship relationship: n.getRelationships(RelationshipTypes.frontier)){	
						// delete edge
						if (relationship.getEndNode().getId() == node_next.getId()) {
							relationship.delete();
							frontier_count--;
						}
					}
				}
				// set node to reached
				node_next.addLabel(Labels.reached);
				
				// add edges (to not reached ones)
				for (Relationship relationship: node_next.getRelationships(RelationshipTypes.connected)) {
					Node EndNode = relationship.getEndNode();
					// not reached, add
					if (!EndNode.hasLabel(Labels.reached)) {
						Relationship on_frontier = node_next.createRelationshipTo( EndNode, RelationshipTypes.frontier);
						frontier_count++;
					}
				}
		}
		// Printout according to extended property of the relationship

		// return String.join(delimiter, strings);
    	return;
	}
}
