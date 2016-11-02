import numpy as np
import cv2

cap = cv2.VideoCapture(0)
success, image = cap.read()
# if (success!= True):
#     print("video capture failed")
count=0

while count <50:
    if (count%10 == 0):
        print("saving frame %d" % count)
        cv2.imwrite("frame%d.png" % count, image)

    success,image = cap.read()
    ret, frame = cap.read()
    count +=1
