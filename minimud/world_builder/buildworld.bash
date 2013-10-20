#!/bin/bash

cd ./bin

export CLASSPATH=/usr/local/lib/mysql-connector-java-5.1.26-bin.jar:.:/home/steve/codemunki-gitrepo/minimud/world_builder/bin/:/home/steve/codemunki-gitrepo/minimud/minimud_shared/bin/

java world_builder.BuildWorld localhost 3306 minimud ntnmfuicd 

