#!/usr/bin/python
import os

print 'hello'

fo = open('/var/allie-data/config/application.properties','rw+')

print "file name ", fo.name

fo.seek(0,2)
line = fo.write(os.environ['DEPLOYMENT_GROUP_NAME'])

fo.close()
