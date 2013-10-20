#!/bin/bash

cd ./bin

export CLASSPATH=/usr/local/lib/mysql-connector-java-5.1.26-bin.jar:.:/home/steve/codemunki-work/minimud/minimud_shared/bin/:/home/steve/antlr-3.4/lib/antlr-3.4-complete.jar:.

java MiniMUDClient.MiniMUDClient
