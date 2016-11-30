import numpy as np
import matplotlib.pyplot as plt
from math import sqrt
import random
mu, sigma = 0, 0.7 # mean and standard deviation


s = np.random.normal(mu, sigma, 100000)
bins=270
bin_count = np.histogram(s,bins)[0]/(max(np.histogram(s,bins)[0])*(1+random.random()/2.5))


np.savetxt("newSeed2.txt", bin_count, delimiter=',', fmt='%.2f')

count, bins, ignored = plt.hist(s, bins)
plt.plot(bins, 1/(sigma * np.sqrt(2 * np.pi)) *
               np.exp( - (bins - mu)**2 / (2 * sigma**2) ),
         linewidth=2, color='r')
