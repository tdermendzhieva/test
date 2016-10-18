#!/bin/bash

if service --status-all | grep -Fq 'allie-data'; then
    sudo service allie-data stop
fi