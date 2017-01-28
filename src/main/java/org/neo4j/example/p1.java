package myproc;

import java.util.List;

import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

/**
 * This is an example how you can create a simple user-defined function for Neo4j.
 */
public class p1
{
    @UserFunction
    @Description("example.p1(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public String p1(
            @Name("strings") List<String> strings,
            @Name(value = "delimiter", defaultValue = ",") String delimiter) {
        if (strings == null || delimiter == null) {
            return null;
        }
	System.out.println("T01, here in the new procedure!");
        return String.join(delimiter, strings);
    }
}
