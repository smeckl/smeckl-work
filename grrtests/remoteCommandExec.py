import sys, json, urllib3, base64, requests   
from requests.auth import HTTPBasicAuth   

class remoteCommandExec(object):
    """Connects to and executes a command on a GRR server"""

    def __init__(self):
        # TODO: Need to find a more secure way to manage credentials here
        self.grrserver = 'http://<server_IP>:8000'   
        self.username = ''   
        self.password = ''   

        # Create the basic authentication string for the HTTP connection
        base64string = base64.encodestring('%s:%s' % (self.username, self.password)).replace('\n', '')   
        self.authheader = "Basic %s" % base64string   

        # Init data needed for POST requests
        self.initCookies()

    def initCookies(self):
        # Open connection to server and authenticate
        index_response = requests.get(self.grrserver, auth=HTTPBasicAuth(self.username, self.password))   

        self.cookies = index_response.cookies

    def runPostCommand(self, uriExtension, data):
        # Append our API's URI extension  
        fullURI = self.grrserver + uriExtension

        # HTTP headers
        headers = {   
          "Authorization": self.authheader,   
          "x-csrftoken": self.cookies.get("csrftoken"),   
          "x-requested-with": "XMLHttpRequest"   
        }   
 
        # Append our API's URI extension  
        fullURI = self.grrserver + uriExtension

        # Make the API request
        response = requests.post(fullURI,   
                headers=headers, data=json.dumps(data),   
                cookies=self.cookies, auth=HTTPBasicAuth(self.username, self.password))

        return response

    def runGetCommand(self, uriExtension, stream=False):
         # Append our API's URI extension  
        fullURI = self.grrserver + uriExtension

        # Make the API request
        response = requests.get(fullURI, auth=HTTPBasicAuth(self.username, self.password), stream=stream)

        # Send response back to caller
        return response