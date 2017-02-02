#! /usr/bin/env python

import math
import os
import string
import random

""" This program verifies the Law of Conservation of Generalization or """
""" Part ii of NFL Theorem 2 (i.e., E1(D|C)=E2(C|D)) for the space of  """
""" binary functions on NUM_INPUTS Boolean variables  """

""" Supporting functions  """

def int2bin(int_to_code, code_length):
    """ converts an integer 0 < n < 2^l-1 to a binary string of length l """
    bin_eq = ""
    while int_to_code <> 0:
        n_div_2 = divmod(int_to_code, 2)
        bin_eq = ',' + repr(n_div_2[1]) + bin_eq
        int_to_code = n_div_2[0]
    for i in range(len(bin_eq)/2, code_length):
        bin_eq = ',' + repr(0) + bin_eq
    return bin_eq[1:]

def makeBaseARFFHeaders():
    """ creates the headers of the base ARFF files """
    train_set = open(BASE_TRAIN_FILE_NAME, 'w')
    test_set = open(BASE_TEST_FILE_NAME, 'w')
    train_set.write('@relation BoolTrain')
    test_set.write('@relation BoolTest')
    for i in range(0, NUM_INPUTS):
        train_set.write('\n@attribute A' + repr(i) + ' {0,1}')
        test_set.write('\n@attribute A' + repr(i) + ' {0,1}')
    train_set.write('\n@attribute class {0,1}\n')
    test_set.write('\n@attribute class {0,1}\n')
    train_set.write('@data\n')
    test_set.write('@data\n')
    train_set.close()
    test_set.close()

def makeBaseARFFContents(task_index):
    """ creates the contents of the base ARFF files for task of index """
    """ task_index, with NUM_EXAMPLES total, NUM_TEST examples for    """
    """ testing (OTS) [NUM_EXAMPLES - NUM_TEST for training] and      """
    """ NUM_INPUTS                                                    """
    """ NOTE: Since we are in the Boolean case, we assume that        """
    """       the total number of tasks is pow(2, NUM_EXAMPLES)       """
    """       and hence task_index is coded on NUM_EXAMPLES bits      """
    train_set = open(BASE_TRAIN_FILE_NAME, 'a')
    test_set = open(BASE_TEST_FILE_NAME, 'a')
    vector_C = int2bin(task_index, NUM_EXAMPLES)
    for i in range(0, NUM_EXAMPLES - NUM_TEST):
        train_set.write(int2bin(i, NUM_INPUTS) + ',' + vector_C[2*i] + '\n')
    for i in range(NUM_EXAMPLES - NUM_TEST, NUM_EXAMPLES):
        test_set.write(int2bin(i, NUM_INPUTS) + ',' + vector_C[2*i] + '\n')
    train_set.close()
    test_set.close()
    
    
def minorityLearner(task_index):
    # train
    classes = []
    with open(BASE_TRAIN_FILE_NAME) as f:
        for line in f:
            if line[0] == '@':
                continue
            classes.append(float(line.split(',')[-1].strip()))
        f.close()
    
    minorityClass = 1
    if sum(classes) > len(classes)/float(2):
        minorityClass = 0
        
    # test
    correct = 0
    incorrect = 0
    with open(BASE_TEST_FILE_NAME) as f:
        for line in f:
            if line[0] == '@':
                continue
            classValue = float(line.split(',')[-1].strip())
            if minorityClass == classValue:
                correct += 1
            else:
                incorrect += 1
        f.close()
    return (correct)/float(correct + incorrect) * 100
    
""" Main program                                """

""" Get parameter values  """
NUM_INPUTS = input("Number of binary inputs: ")
NUM_TEST = input("Number of test instances: ")
classifier_names = ['trees.J48', 'rules.ZeroR', 'bayes.NaiveBayes', 'functions.MultilayerPerceptron', "MinorityLearner"]
message = 'Please pick a Classifier (by number)\n'
i = 0
for name in classifier_names:
    i += 1
    message += ' %d --> %s\n' % (i, name)
classifier_name = classifier_names[int(input(message))-1]

""" Initialize global variables  """
NUM_EXAMPLES = pow(2, NUM_INPUTS)
NUM_TASKS = pow(2, NUM_EXAMPLES)

BASE_TRAIN_FILE_NAME = "Train.arff"
BASE_TEST_FILE_NAME = "Test.arff"
OUTPUT_FILE_NAME = "TaskGP.out"
LAST_VALUE = "Hello"

""" Sets things up for the external call to Weka """
""" Result is stored in OUTPUT_FILE_NAME """
calling_stem = 'java -cp /home/jr2of6/Documents/weka.jar weka.classifiers.'
base_options = ' -t ' + BASE_TRAIN_FILE_NAME + ' -T ' + BASE_TEST_FILE_NAME + ' -o > ' + OUTPUT_FILE_NAME

""" Run classifier on all tasks and print result """

accuracies = []
print "Testing %s with %d tasks" % (classifier_name, NUM_TASKS)
for task_num in xrange(NUM_TASKS):
    makeBaseARFFHeaders()
    makeBaseARFFContents(task_num)
    if classifier_name == "MinorityLearner":
        accuracy = minorityLearner(task_num)
    else:
        os.system(calling_stem + classifier_name + base_options)
        infile = open(OUTPUT_FILE_NAME, 'r')
        file_content = infile.read()
        ind1 = string.rfind(file_content, 'Correctly Classified Instances')
        list1 = string.split(file_content[ind1+31:])
        accuracy = float(list1[1])
    accuracies.append(accuracy)
#     print 'Accuracy=%2.2f' % (accuracy)
print 'Generalization Performance: %f' % (sum([acc - 50 for acc in accuracies]))
print 'Generalization Performance: %f' % (sum(accuracies)/len(accuracies))
    



