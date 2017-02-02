
import matplotlib
matplotlib.use('Agg')
from pylab import *

class Grapher():
    def __init__(self, filename):
        self.filename = filename
        self.points = []
        values1 = set()
        values2 = set()
        with open('../data/' + filename, 'r') as f:
            points = []
            for line in f:
                if '#'in line:
                    continue
                parts = line.split()
                points.append(parts)
                values1.add(parts[0])
                values2.add(parts[1])
        for value1 in values1:
            for value2 in values2:
                temp = []
                for point in points:
                    epoch, rate, train, test = point
                    if value1 == epoch and value2 == rate:
                        temp.append([train, test])
                final = [value1, value2, sum([float(tr) for tr, te in temp])/len(temp), sum([float(te) for tr, te in temp])/len(temp)]
                self.points.append(final)
        self.values = values2
                        
                
    def makeAllRateScatterPlots(self):
        """ Create a box plot. """
        for rate in self.values:
            self.makeRateScatterPlot(float(rate))
            
    def makeRateScatterPlot(self, currentRate):
        clf()
        for point in self.points:
            epochs, rate, train, test = point
            if float(currentRate) == float(rate):
                scatter(epochs, float(train) * 100, color='blue')
                scatter(epochs, float(test) * 100, color='green')
        title("Learning Rate %.1f" % (float(currentRate)))
        axis([5, 1005, 0, 100])
        xlabel('X Axis: Epochs')
        ylabel('Y Axis: Accuracy')
        thisName = '../data/%s-%0.1f.png' % (self.filename.split('.')[0], float(currentRate))
        print thisName
        savefig(thisName)     
                
    def makeNodeScatterPlot(self, currentNode):
        clf()
        for point in self.points:
            epochs, node, train, test = point
            if int(currentNode) == int(node):
                scatter(epochs, float(train) * 100, color='blue')
                scatter(epochs, float(test) * 100, color='green')
        title("Hidden Layer Node Count %d" % (int(currentNode)))
        axis([5, 1005, 0, 100])
        xlabel('X Axis: Epochs')
        ylabel('Y Axis: Accuracy')
        thisName = '../data/%s-%0.1f.png' % (self.filename.split('.')[0], float(currentNode))
        print thisName
        savefig(thisName)
    
    def makeAllNodeScatterPlots(self):
        """ Create a box plot. """
        for node in self.values:
            self.makeNodeScatterPlot(int(node))
            
    def makeAllMomentumScatterPlots(self):
        self.makeMomentumScatterPlots(True)
        self.makeMomentumScatterPlots(False)
            
    def makeMomentumScatterPlots(self, tBool):
        clf()
        colors = ['blue', 'green', 'red', 'cyan', 'magenta', 'yellow', 'black', '.25', '.75']
        for point in self.points:
            epochs, mom, train, test = point
            if tBool == True:
                scatter(epochs, float(train) * 100, color=colors[int(float(mom)*10)-1])
            else:
                scatter(epochs, float(test) * 100, color=colors[int(float(mom)*10)-1])
        if tBool == True:
            type = 'Train'
        else: 
            type = 'Test'
        title("Momentum %s" % (type))
        axis([5, 1005, 0, 100])
#         axis([5, 1005, 0, 100])
        xlabel('X Axis: Epochs')
        ylabel('Y Axis: Accuracy')
        thisName = '../data/%s-%s.png' % (self.filename.split('.')[0], type.lower())
        print thisName
        savefig(thisName)
        
        
if __name__ == '__main__':
#     Grapher('iris_output_nodes.txt').makeAllNodeScatterPlots()
#     Grapher('iris_output_rate.txt').makeAllRateScatterPlots()
#     Grapher('vowel_output_nodes.txt').makeAllNodeScatterPlots()
#     Grapher('vowel_output_rate.txt').makeAllRateScatterPlots()
#     Grapher('vowel-output_rate_6-4.txt').makeAllRateScatterPlots()
    Grapher('vowel_output_mom.txt').makeAllMomentumScatterPlots()
#     Grapher('iris_output_mom.txt').makeAllMomentumScatterPlots()
        
 