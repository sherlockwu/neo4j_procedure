MATCH ()-[a]-() DELETE a;

MATCH (n) DELETE n;

USING PERIODIC COMMIT
LOAD CSV FROM "file:///mnt/myssd/data/node_test_graph.csv" AS line
CREATE (:node {id: line[0]});

USING PERIODIC COMMIT
LOAD CSV FROM "file:///mnt/myssd/data/test_graph.csv" AS line FIELDTERMINATOR ' '
MATCH (a:node {id: line[0]}), (b:node {id: line[1]})
CREATE (a)-[:connected]->(b);
