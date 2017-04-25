#!/usr/bin/python    
import sys, json, urllib3, base64, requests   
from requests.auth import HTTPBasicAuth  
from remoteCommandExec import remoteCommandExec

data = ''

hostname = sys.argv[1]   
flowname = sys.argv[2]

uriExtension = "/api/clients/" + hostname + "/flows/" + flowname
print uriExtension
grrConn = remoteCommandExec()

response = grrConn.runGetCommand(uriExtension)

print response.text