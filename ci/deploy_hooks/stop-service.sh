#!/bin/bash

if sudo service --status-all | grep -Fq 'allie-data' ; then
    echo test
    sudo service allie-data stop
fi