#!/bin/bash
#first we need to make sure the jar has the proper permission
sudo chown allie "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar"

#update permission to read or execute by owner only
sudo chmod 500 "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar"

#make jar immutable
sudo chattr +i "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar"

#now we are ready to startup our app as a service
#create symbolic with force to overwrite existing links
sudo ln -s -f "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar" "/etc/init.d/$APPLICATION_NAME"

#next start the service
sudo service "$APPLICATION_NAME" start

#then register it as a long running service
sudo chkconfig "$APPLICATION_NAME" on

#finally, restart the service to pickup property file changes
sudo service "$APPLICATION_NAME" restart

