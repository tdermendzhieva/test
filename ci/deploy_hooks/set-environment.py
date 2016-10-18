#!/usr/bin/python

from __future__ import print_function
print(os.environ['DEPLOYMENT_GROUP_NAME'], file="/var/allie-data/config/application.properties")


