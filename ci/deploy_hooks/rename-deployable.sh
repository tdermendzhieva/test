#!/bin/bash

#first we need to make sure we have the allie group setup
#create group if one does not exist
getent group allie || sudo groupadd allie

#next, create user and add to group, if it does not exist/belong
getent passwd allie || sudo useradd allie -g allie

#change log permissions
sudo chown -R allie /allie/logs
#remove current backup
sudo rm "/var/$APPLICATION_NAME/$APPLICATION_NAME.bak"

#find the allie war that was dropped by code deploy and rename it to a consistent naming convention
echo "renaming jar to $APPLICATION_NAME.jar"
sudo find . \( -name "$APPLICATION_NAME-*" -a -name "*.jar" \) -execdir mv {} "$APPLICATION_NAME.jar" \;
echo "jar renamed"