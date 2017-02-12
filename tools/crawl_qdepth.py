import re
from zplot import *
import sys

def ncq_plots(plot_title):
	ctype = 'pdf'
	c = canvas(ctype, title=plot_title, dimensions=['800','340'])

	t = table(file='ncq.data')

	d = drawable(canvas=c, xrange=[-1,t.getmax(column='rownumber') + 1],
             yrange=[0,t.getmax(column='ncq_depth')+1], coord=[40,40], dimensions=[750,270])

	grid(drawable=d, ystep=1, xstep=1, linecolor='lightgrey')

	axis(drawable=d, style='y', yauto=['','',1])
	axis(drawable=d, style='x', xmanual=t.getaxislabels(column='pid'),
     	xlabelrotate=90, xlabelanchor='r,c', xlabelfontsize=7,
    	title='ncq_depth', titlesize=8,
     	titlefont='Courier-Bold', xtitle='pid-timeslots',
     	xtitleshift=[0,-15])

	p = plotter()
	p.line(drawable=d, table=t, xfield='rownumber', yfield='ncq_depth', stairstep=True,
       	linecolor='purple', labelfield='ncq_depth', labelsize=7, labelcolor='purple',
      	labelshift=[6,0], labelrotate=90, labelanchor='l,c')

	c.render()

if __name__ == '__main__':
	print('crawling ncq from the output')
	# read in
	ncq_file = open('./ncq_depth_timeline.txt', 'r')
	line_no  = 0
	ncq_depth_list = []
	pid_list = []
	# prepare to print out for zplots
	ncq_data = open('ncq.data', 'w')
	ncq_data.write('# no ncq_depth pid\n')
	for line in ncq_file.readlines():
		line_no+=1
		if line_no<=1:
			continue
		line = re.sub(r'\n', '', line)
		io_list = map(str, line.split(';'))
		# get ncq into list
		ncq_depth_list.append(int(io_list[1]))
		ncq_data.write(str(line_no) + ' ' + io_list[1] + ' ' + io_list[6] + '\n')
		# get pid into list
		pid_list.append(int(io_list[6]))

	ncq_data.close()
	ncq_plots('ncq_shortestpath')
	print('ncq: ')
	print(str(ncq_depth_list))
	print('pid: ')
	print(str(pid_list))


