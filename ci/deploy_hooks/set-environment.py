#!/usr/bin/python
import os

print 'hello'

fo = open('/var/' +  os.environ['APPLICATION_NAME'] + '/config/application.properties','rw+')

print "file name ", fo.name

fo.seek(0,2)
line = fo.write("spring.profiles.active=" + os.environ['DEPLOYMENT_GROUP_NAME'])

fo.close()
