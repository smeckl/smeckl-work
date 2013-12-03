#!/bin/bash

export CLASSPATH=/home/steve/codemunki-gitrepo/minimud/lib/mysql-connector-java-5.1.26-bin.jar:.:/home/steve/codemunki-gitrepo/minimud/minimud_shared/dist/minimud_shared.jar

java -jar ./dist/world_builder.jar 127.0.0.1 3306 reset root ../world_data.xml 
