#!/bin/bash

echo "testiing"

if ps ax | grep -v grep | grep $APPLICATION_NAME > /dev/null
then
    sudo service $APPLICATION_NAME stop
else
    echo "SERVICE is not running"
fi


#delete jar and prop file
if sudo [ -f "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar" ];
then

    echo "backup current jar"
    sudo cp "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar" "/var/$APPLICATION_NAME/$APPLICATION_NAME.bak"
    echo "making jar mutable to be deleted"
    sudo chattr -i "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar"
    echo "deleting old jar"
    sudo rm "/var/$APPLICATION_NAME/$APPLICATION_NAME.jar"
fi

if sudo [ -d "/var/$APPLICATION_NAME/config" ]; then
    echo "deleting old configs"
    sudo rm -rf "/var/$APPLICATION_NAME/config"
fi

#delete symbolic link
if sudo [ -f "/etc/init.d/$APPLICATION_NAME" ];
then
    echo "deleting old symbolic link"
    sudo rm "/etc/init.d/$APPLICATION_NAME"
fi