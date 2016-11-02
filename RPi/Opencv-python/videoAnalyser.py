from __future__ import division
import numpy
import cv2
import time

##############################################
# with background extraction from video feed #
##############################################

cap= cv2.VideoCapture('crossroads.mp4')
# cap= cv2.VideoCapture('clipcanvas_preview_316059.mp4')
# relevant area mask
mask = cv2.imread('relmask.png',0)
#count number of non black pixels in mask
masksize=60000
highestcover=0

#start background substractor
fgbg = cv2.bgsegm.createBackgroundSubtractorMOG(200,3,0.7,0)
fgbg2 = cv2.createBackgroundSubtractorMOG2(200,16,True)

count =  0
while(True):
    ret,frame=cap.read()
    # if(count%1 == 0):

    #apply mask
    masked = cv2.bitwise_and(frame,frame, mask=mask)

    #feed to subtractor and get resulting mask
    fgmask = fgbg.apply(masked)
    fgmask2 = fgbg2.apply(masked)

    #smooth resulting mask
    blur = cv2.GaussianBlur(fgmask2,(7,7),2)

    #threshold the blurred frame with otsu
    ret,otsu = cv2.threshold(blur,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)

    # count whites in thresholded frame
    covered=cv2.countNonZero(otsu)
    ratio = covered/masksize*100

    #print on-screen stats
    font = cv2.FONT_HERSHEY_SIMPLEX
    cv2.rectangle(otsu,(0,0),(200,120), (0,0,0),-1 )
    cv2.putText(otsu,'%d' % masksize,(20,40), font, 1, (255,255,255), 1, cv2.LINE_AA)
    cv2.putText(otsu,'%d' % covered,(20,70), font, 1, (255,255,255), 1, cv2.LINE_AA)
    cv2.putText(otsu,'%d percent' % ratio,(20,100), font, 1, (255,255,255), 1, cv2.LINE_AA)

    #dump highest whitecount frame
    if covered > highestcover:
        highestcover = covered
        cv2.imwrite('highest.png', otsu)
    cv2.imshow('frame', frame)
    cv2.imshow('otsu', otsu)
    cv2.imshow('mask', fgmask)
    cv2.imshow('mask2', fgmask2)

    k=cv2.waitKey(30) & 0xff

    if k==27:
        break
    # time.sleep(0.25)
    count += 1

print(highestcover)

###################################
# with background image reference #
###################################

# # background reference image
# bgref = cv2.imread('crossref.png')
# # relevant area mask
# mask = cv2.imread('relmask.png',0)
#
# # apply mask to background reference image
# maskedref = cv2.bitwise_and(bgref,bgref, mask=mask)
#
# # alternative color space
# hsvref = cv2.cvtColor(maskedref,cv2.COLOR_BGR2HSV)
# while(True):
#
#     ret,frame=cap.read()
#     if(count%10 == 0):
#         # apply mask to frame
#         maskedframe = cv2.bitwise_and(frame,frame, mask=mask)
#
#         # rgb color space
#         # diff image and reference bg
#         subtracted = cv2.absdiff(maskedframe,maskedref)
#         # convert diff to grayscale
#         gray = cv2.cvtColor(subtracted,cv2.COLOR_BGR2GRAY)
#         # improve histogram
#         histEq = cv2.equalizeHist(gray)
#
#         # Otsu threshold
#         ret,oThresh = cv2.threshold(histEq,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)
#
#         # Gaussian blur and Otsu threshold
#         blur = cv2.GaussianBlur(gray,(9,9),0)
#         ret,ogThresh = cv2.threshold(blur,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)
#
#         # hsv color space
#         hsvframe = cv2.cvtColor(maskedframe,cv2.COLOR_BGR2HSV)
#         hsvsub = cv2.absdiff(hsvframe,hsvref)
#         hsvgray = cv2.cvtColor(hsvsub,cv2.COLOR_BGR2GRAY)
#         hsvhistEq = cv2.equalizeHist(hsvgray)
#         ret,hsvoThresh = cv2.threshold(hsvhistEq,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)
#         hsvblur = cv2.GaussianBlur(hsvgray,(9,9),0)
#         ret,hsvogThresh = cv2.threshold(hsvblur,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)
#
#         images = [frame, gray, oThresh, blur,  ogThresh, hsvgray, hsvoThresh, hsvblur, hsvogThresh ]
#         labels = ['frame', 'gray', 'otsu','gauss','gauss+otsu', 'hsv gray','hsv Otsu', 'hsv gauss', 'hsv gauss + otsu']
#
#         for i in range(len(images)):
#             cv2.imshow(labels[i], images[i]);
#
#         k=cv2.waitKey(30) & 0xff
#
#         if k==27:
#             break
#         time.sleep(0.5)
#     count +=1
cap.release()

cv2.destroyAllWindows
