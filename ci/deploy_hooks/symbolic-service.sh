#!/bin/bash

echo "$DEPLOYMENT_GROUP_NAME"
export APPENV="$DEPLOYMENT_GROUP_NAME"

#create symbolic with force to overwrite existing links
sudo ln -s -f /var/allie-data/*.jar /etc/init.d/allie-data
#next start the service
sudo service allie-data start
#then register it as a long running service
sudo chkconfig allie-data on