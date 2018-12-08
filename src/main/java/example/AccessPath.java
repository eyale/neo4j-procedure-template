package example;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.procedure.*;

import java.util.Map;

/**
 * This is an example how you can create a simple user-defined function for
 * Neo4j.
 */
public class AccessPath {
  @Context
  public GraphDatabaseService db;

  @UserFunction("example.access_path")
  @Description("example.access_path(node1, node2) - returns distance between 2 given nodes")
  public long access_path(@Name("nameNode1") String nameNode1, @Name("nameNode2") String nameNode2) {
    Result res = db.execute("CYPHER runtime=compiled MATCH (s:Actor {name: \"" + nameNode1 + "\"}),(t:Actor {name: \""
        + nameNode2 + "\"}), p=shortestPath((s)-[:ACTS_IN*]-(t)) RETURN length(p) AS distance;");

    Map response = res.next();

    return response.get("distance") != null ? ((Number) response.get("distance")).longValue() : -1;
  }
}
