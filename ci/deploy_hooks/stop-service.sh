#!/bin/bash

if sudo service --status-all | grep -Fq 'allie-data' ; then
    echo test
    sudo service allie-data stop
fi

#delete jar and prop file
sudo rm /var/allie-data/*.jar
sudo rm /var/allie-data/config/application.properties
#delete symbolic link
sudo rm /etc/init.d/allie-data