#!/bin/bash

echo test2
if ($(ps -ef | grep -v grep | grep 'allie-data' | wc -l) > 0)
then
    echo test
    sudo service allie-data stop
fi