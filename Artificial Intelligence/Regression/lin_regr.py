import os  
import numpy as np  
import pandas as pd
import sys

def calcError(X, y, theta):  
    return np.sum(np.power(((X * theta.T) - y), 2)) / (2 * len(X))

def gradientDescent(X, y, theta, alpha, iters):  
    temp = np.matrix(np.zeros(theta.shape))
    parameters = int(theta.ravel().shape[1])
    cost = np.zeros(iters)
    global iterations
    iterations = 1
    global totalError
    totalError = 0
    preve = 100
    done = False
    while not done:
        for i in range(iters):
            error = (X * theta.T) - y
            for j in range(parameters):
                term = np.multiply(error, X[:,j])
                temp[0,j] = theta[0,j] - ((alpha / len(X)) * np.sum(term))
            iterations += 1
            theta = temp
            e[i] = calcError(X, y, theta)
            if iterations % 10 == 0:
                print ("\n{0} Iterations: {1} error = {2}".format(iterations, theta, e[i]))
                if abs(preve - e[i]) <= 0.0000001:
                    done = True
                    print("Converged") 
                    break
                if iterations == iters:
                  done = True
                  break
            preve = e[i]
            totalError += e[i]
            iterations += 1
            
    return theta, e


def main():
    global totalError
    if len(sys.argv) == 3:
        train = sys.argv[1]
        trainData = pd.read_csv(train, header=None)  
        trainData.head()
        test = sys.argv[2]
        testData = pd.read_csv(test, header=None)  
        testData.head()
        trainData.insert(0, 'Ones', 1)
        alpha = 0.01  
        iters = 2000
        # set X (training data) and y (target variable)
        cols = trainData.shape[1]  
        X = trainData.iloc[:,0:cols-1]  
        y = trainData.iloc[:,cols-1:cols]
        X = np.matrix(X.values)  
        y = np.matrix(y.values)  
        theta = np.matrix(np.array([0,0])) 
        g, e = gradientDescent(X, y, theta, alpha, iters)
        print("\nAverage Error: {0}".format(totalError/ iterations))
        cols = testData.shape[1] 
        X2 = testData.iloc[:,0:cols-1]  
        y2 = testData.iloc[:,cols-1:cols] 
    	X2 = np.matrix(X2.values)  
        y2 = np.matrix(y2.values) 
        #Make a prediction using our line for each test case
        for i in range(0, len(testData)):
            x = X2[i, 0]
            y = y2[i]
            prediction = np.array([x]).dot(g).flatten()
            prediction = prediction[0,1]
            print("Prediction: {0} ".format(prediction))
                    
        
    if len(sys.argv) == 2:
        path = sys.argv[1]
        data = pd.read_csv(path, header=None)  
        data.head()
        alpha = 0.01  
        iters = 2000
        data = (data - data.mean()) / data.std()  
        data.head() 
        data.insert(0, 'Ones', 1)
        df = pd.DataFrame(data)
        msk = np.random.rand(len(df)) < .8
        train = df[msk]
        test = df[~msk]
        cols = data.shape[1]  
        X2 = train.iloc[:,0:cols-1]  
        y2 = train.iloc[:,cols-1:cols]
        X2 = np.matrix(X2.values)  
        y2 = np.matrix(y2.values)  
        theta2 = np.matrix(np.array([0,0,0,0,0,0,0,0,0,0,0,0,0,0])) 
        g2, e2 = gradientDescent(X2, y2, theta2, alpha, iters)
    	print("\nAverage Error: {0}".format(totalError/ iterations))
        cols = test.shape[1] 
        X2 = test.iloc[:,0:cols-1]  
        y2 = test.iloc[:,cols-1:cols] 
     	X2 = np.matrix(X2.values)  
     	y2 = np.matrix(y2.values) 
        #Make a prediction using our line for each test case
        for i in range(0, len(test)):
            x = X2[i]
            y = y2[i]
            prediction = g2 * 2 - x
            prediction = prediction[0,1]
            print("Prediction: {0} ".format(prediction))      
main()