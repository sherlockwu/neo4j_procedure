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
        IS_FRIEND_OF,
        HAS_SEEN;
    }

    public enum Labels implements Label {
            USER,
            MOVIE;
    }


	//@UserFunction
	@Procedure(value = "myproc.p1", mode = Mode.WRITE)
	@Description("myproc.p1(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public void p1(
            @Name("strings") List<String> strings,
            @Name(value = "delimiter", defaultValue = ",") String delimiter) {
		
		System.out.println("T01, here in the new procedure!");
        

		// Create Nodes
            Transaction tx = graphDB.beginTx();
            Node steve = graphDB.createNode(Labels.USER);
            steve.setProperty("name", "Steve");
            Node tom = graphDB.createNode(Labels.USER);
            tom.setProperty("name", "Tom");

            Node divergent = graphDB.createNode(Labels.MOVIE);
            divergent.setProperty("name", "Divergent");

            tx.success();

        // Create Relationship
            tx = graphDB.beginTx();
            steve.createRelationshipTo(tom, RelationshipTypes.IS_FRIEND_OF);
            Relationship relationship = steve.createRelationshipTo(divergent, RelationshipTypes.HAS_SEEN);
            tx.success();

        // Begin some search
            // Find all users
            ResourceIterator<Node> users = graphDB.findNodes( Labels.USER );
            System.out.println( "Users:" );
            while( users.hasNext() )
            {
                Node user = users.next();
                System.out.println( "\t" + user.getProperty( "name" ) );
            }




		// return String.join(delimiter, strings);
    	return;
	}
}
