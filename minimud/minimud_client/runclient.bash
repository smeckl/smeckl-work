#!/bin/bash

export CLASSPATH=/usr/local/lib/mysql-connector-java-5.1.26-bin.jar:.:/home/steve/codemunki-gitrepo/minimud/minimud_shared/dist/minimud_shared.jar

java -jar ./dist/minimud_client.jar
