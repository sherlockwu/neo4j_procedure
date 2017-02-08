MATCH ()-[a]-() DELETE a;

MATCH (n) DELETE n;

USING PERIODIC COMMIT
LOAD CSV FROM "file:///mnt/myssd/data/node_test_graph.csv" AS line
CREATE (:node {id: toInt(line[0])});

CREATE INDEX ON :node(id);

USING PERIODIC COMMIT
LOAD CSV FROM "file:///mnt/myssd/data/test_graph.csv" AS line FIELDTERMINATOR ' '
MATCH (a:node {id: toInt(line[0])}), (b:node {id: toInt(line[1])})
CREATE (a)-[:connected]->(b);
