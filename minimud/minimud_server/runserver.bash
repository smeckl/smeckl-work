#!/bin/bash

export CLASSPATH=/usr/local/lib/mysql-connector-java-5.1.26-bin.jar:.:/home/steve/codemunki-gitrepo/minimud/minimud_shared/dist/minimud_shared.jar:/home/steve/codemunki-gitrepo/minimud/lib/commons-lang3-3.1.jar:.

java -jar ./dist/minimud_server.jar localhost 3306 minimud ~/.keystore ./minimud_log.txt password.txt
