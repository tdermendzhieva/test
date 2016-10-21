#!/bin/bash

#create symbolic with force to overwrite existing links
sudo ln -s -f /var/allie-data/*.jar /etc/init.d/allie-data
#next start the service
sudo service allie-data start
#then register it as a long running service
sudo chkconfig allie-data on
sudo service allie-data restart
