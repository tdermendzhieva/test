#!/bin/bash

sudo service allie-data stop


#delete jar and prop file
sudo rm /var/allie-data/*.jar
sudo rm /var/allie-data/config/application.properties
#delete symbolic link
sudo rm /etc/init.d/allie-data