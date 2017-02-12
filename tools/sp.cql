MATCH (n1:node {id: 13}), (n2:node {id: 1000}), p = shortestPath((n1)-[*]-(n2))
RETURN p;
