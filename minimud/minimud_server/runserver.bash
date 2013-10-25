#!/bin/bash

cd ./bin

export CLASSPATH=/usr/local/lib/mysql-connector-java-5.1.26-bin.jar:.:/home/steve/codemunki-gitrepo/minimud/minimud_shared/bin/:/home/steve/antlr-3.4/lib/antlr-3.4-complete.jar:/home/steve/codemunki-gitrepo/minimud/lib/commons-lang3-3.1.jar:.

java MiniMUDServer.MiniMUDServer localhost 3306 minimud ntnmfuicd ./minimud_server_log.txt

