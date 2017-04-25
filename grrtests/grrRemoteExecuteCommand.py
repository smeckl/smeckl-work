#!/usr/bin/python    
import sys, json, urllib2, base64, requests   
from requests.auth import HTTPBasicAuth   
 
# Get command-line parameters  
uriExtension = sys.argv[1] # The API GET string we want to use for this command
data = sys.argv[2] # The JSON structured data we are passing to the API
   
# TODO: Need to find a more secure way to manage credentials here
grrserver = 'http://<server_IP>:8000'   
username = ''   
password = ''   
   
# Create the basic authentication string for the HTTP connection
base64string = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')   
authheader = "Basic %s" % base64string   
   
# Open connection to server and authenticate
index_response = requests.get(grrserver, auth=HTTPBasicAuth(username, password))   

# The auth token.  We can use this in future connections to show we are authenticated
csrf_token = index_response.cookies.get("csrftoken")   
   
# HTTP headers
headers = {   
  "Authorization": authheader,   
  "x-csrftoken": csrf_token,   
  "x-requested-with": "XMLHttpRequest"   
}   
 
# Append our API's URI extension  
fullURI = grrserver + uriExtension

# Make the API request
response = requests.post(fullURI,   
        headers=headers, data=json.dumps(data),   
        cookies=index_response.cookies, auth=HTTPBasicAuth(username, password)) 

# TODO: Need to pass this back to caller so they can get any IDs or tokens to 
# be used in follow-up calls
print response
