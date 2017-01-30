import sys

file_name = sys.argv[1]

# read in each nodes
print(file_name)
node_file = 'node_' + file_name
print(node_file)
f_in = open(file_name, 'r')
f_out = open(node_file, 'w')

number_seen = set()
for line in f_in:
	a,b = map(int,line.split())
	if a not in number_seen:
		f_out.write(str(a)+'\n')
		number_seen.add(a)
	if b not in number_seen:
		f_out.write(str(b)+'\n')		
		number_seen.add(b)
# print to a file with _node.csv



f_in.close()
f_out.close()
