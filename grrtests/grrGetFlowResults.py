#!/usr/bin/python    
import sys, json, urllib3, base64, requests   
from requests.auth import HTTPBasicAuth  
from remoteCommandExec import remoteCommandExec

data = ''

hostname = sys.argv[1]   
flowname = sys.argv[2]

# GET /api/clients/<client_id>/flows/<flow_id>/results
uriExtension = "/api/clients/" + hostname + "/flows/" + flowname + "/results"

grrConn = remoteCommandExec()

response = grrConn.runGetCommand(uriExtension)

# Now parse out the JSON response.  For some reason, in the GRR API response,
# the JSON document is the second line.
lines = response.content.splitlines()
responsetext = lines[1]

# Build a dictionary out of the JSON document
response_dict = json.loads(responsetext)

# Get the path from the dictionary created from the JSON document
filePath = response_dict['items'][0]['value']['payload']['value']['pathspec']['value']['path']['value']

# GET /api/clients/<client_id>/flows/<flow_id>/results/files-archive
uriExtension = "/api/clients/" + hostname + "/vfs-blob/fs/os" + filePath

# Download this as a stream to reduce memory required to download the results
response = grrConn.runGetCommand(uriExtension, True)

with open('security.evt', 'wb') as fd:
    for chunk in response.iter_content(chunk_size=128):
        fd.write(chunk)

