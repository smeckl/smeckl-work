#!/usr/bin/python    
from remoteCommandExec import remoteCommandExec
import sys, json

data = {  
  "flow": {
    "args": {
      "fetch_binaries": False,
      "filename_regex": "."
    },
    "name": "ListProcesses",
    "runner_args": {
      "notify_to_user": False,
      "output_plugins": [],
      "priority": "HIGH_PRIORITY"
    }
  }
}

hostname = sys.argv[1]   

uriExtension = "/api/clients/" + hostname + "/flows"  

grrConn = remoteCommandExec()

#response = grrConn.runPostCommand(uriExtension, data)

responsetext = '{"age": 0, "type": "ApiFlow", "value": {"last_active_at": {"age": 1486325062751568, "type": "RDFDatetime", "value": 1486325062751315}, "name": {"age": 0, "type": "unicode", "value": "ListProcesses"}, "creator": {"age": 0, "type": "unicode", "value": "admin"}, "urn": {"age": 0, "type": "SessionID", "value": "aff4:/C.4b3298928c5de3e1/flows/F:D1E6D808"}, "args": {"age": 0, "type": "ListProcessesArgs", "value": {"filename_regex": {"age": 0, "type": "RegularExpression", "value": "."}, "fetch_binaries": {"age": 0, "type": "RDFBool", "value": false}}}, "state": {"age": 0, "type": "EnumNamedValue", "value": "RUNNING"}, "started_at": {"age": 0, "type": "RDFDatetime", "value": 1486325062748952}}}'

response_dict = json.loads(responsetext)

print response_dict['value']['urn']['value']