package minimud_client;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.IOException;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.concurrent.Semaphore;
import minimud_shared.*;

public class MiniMUDClient
{
    private static SSLSocket m_sslSocket = null;
    private static PrintWriter m_serverOut = null;
    private static BufferedReader m_serverIn = null;
    private static BufferedReader m_stdIn = null;
    private static Semaphore m_stopSem = new Semaphore(1);
    private static Object m_rwLock = new Object();
    
    private static int SOCKET_WAIT_MS = 100;

    public enum ErrorCode
    {

        Success,
        Exception,
        ObjectCreationFailed
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            while (true)
            {
                ErrorCode retVal = ErrorCode.Success;
                
                if(args.length != 2)
                {
                    System.out.println("Invalid number of parameters.");  
                    System.out.println("Proper usage is: java -jar minimud_client.jar <server> <port>");
                    break;
                }
                
                RegularExpressions regEx = new RegularExpressions();
                
                String strServer = args[0];
                
                if(!regEx.stringMatchesRegEx(strServer, RegularExpressions.RegExID.IP)
                        && !regEx.stringMatchesRegEx(strServer, RegularExpressions.RegExID.DOMAIN))
                {
                    System.out.println("Invalid server specified.");
                    break;
                }

                String strPort = args[1];
                
                if(!regEx.stringMatchesRegEx(strPort, RegularExpressions.RegExID.PORT))
                {
                    System.out.println("Invalid port number specified");
                    break;
                }
                
                int nPort = Integer.parseInt(strPort);
                
                retVal = initializeClient(strServer, nPort);

                if (ErrorCode.Success != retVal)
                {
                    System.out.println("Failed to connect to game server.");
                    break;
                }

                String serverInput = null;
                String userInput = null;
                boolean bMsgDisplayedText = false;

                while (!m_sslSocket.isClosed() && 1 == m_stopSem.availablePermits())
                {
                    try
                    {                        
                        // Check for ready input from either the client or server
                        if(null != (serverInput = m_serverIn.readLine()))
                        {     
                            synchronized(m_rwLock)
                            {
                                System.out.println("");
                                
                                do
                                {
                                    try
                                    {
                                        //System.out.println("Raw server string: " + serverInput);
                                        Message msg = parseServerCommand(serverInput);

                                        if (null != msg)
                                        {
                                            bMsgDisplayedText = executeServerCommand(msg);
                                        }

                                        serverInput = m_serverIn.readLine();
                                    }
                                    catch (IOException e)
                                    {
                                        serverInput = null;
                                    }
                                    catch (Exception e)
                                    {
                                        System.out.println("Invalid server message received.");
                                    }                                 
                                }
                                while (null != serverInput
                                        && (1 == m_stopSem.availablePermits()));

                                System.out.println("");
                                
                                if(bMsgDisplayedText)
                                    System.out.print(">>");
                            }
                        }                            
                    }
                    catch (SocketTimeoutException e)
                    {
                        // Do nothing
                    }
                    
                    bMsgDisplayedText = false;
                }

                retVal = teardownClient();

                if (ErrorCode.Success != retVal)
                {
                    System.out.println("Error disconnecting from game server.");
                    break;
                }

                break;
            }
        }
        catch (SocketException e)
        {
            System.out.println("Disconnected from server");
            teardownClient();
        }
        catch (SSLException e)
        {
            System.out.println("Disconnected from server");
            teardownClient();
        }
        catch (Exception e)
        {
            System.out.println("Exception caught: " + e);
        }
    }

    private static ErrorCode initializeClient(String strServer, int nPort)
    {
        ErrorCode retVal = ErrorCode.Success;

        try
        {
            // Method for overriding certificate chain validation found at:  
            // 		https://code.google.com/p/misc-utils/wiki/JavaHttpsUrl

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]
            {
                new X509TrustManager()
                {

                    @Override
                    public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                    {
                    }

                    @Override
                    public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                    {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers()
                    {
                        return null;
                    }
                }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory socketFactory = sslContext.getSocketFactory();

            // Creating Client Sockets
            m_sslSocket = (SSLSocket) socketFactory.createSocket(strServer, nPort);
            m_sslSocket.setSoTimeout(SOCKET_WAIT_MS);

            // Initializing the streams for Communication with the Server
            m_serverIn = new BufferedReader(new InputStreamReader(m_sslSocket.getInputStream()));

            // Initializing the streams for Communication with the Server
            m_serverOut = new PrintWriter(m_sslSocket.getOutputStream(), true);

            m_stdIn = new BufferedReader(new InputStreamReader(System.in));
            if (null == m_stdIn)
            {
                retVal = ErrorCode.ObjectCreationFailed;
            }
        }
        catch (Exception e)
        {
            retVal = ErrorCode.Exception;
        }

        return retVal;
    }

    private static ErrorCode teardownClient()
    {
        ErrorCode retVal = ErrorCode.Success;
        try
        {
            // Closing the Streams and the Socket
            m_serverIn.close();

            if (!m_sslSocket.isClosed())
            {
                m_sslSocket.close();
            }
        }
        catch (Exception e)
        {
            retVal = ErrorCode.Exception;
        }

        return retVal;
    }

    private static Message parseServerCommand(String strMsg)
    {
        Message msg = null;

        try
        {
            MessageParser parser = new MessageParser(new StringReader(strMsg));

            parser.parse();

            msg = parser.getLastMessage();
        }
        catch(java.lang.Error e)
        {
            System.out.println("Invalid Server Message.");
            msg = null;
        }
        catch (Exception e)
        {
            System.out.println("Invalid Server Message.");
            msg = null;
        }

        return msg;
    }

    private static boolean executeServerCommand(Message msg)
    {
        boolean bMsgDisplayedText = false;

        try
        {
            if (MessageID.CLIENT_SHOW_TEXT == msg.getMessageId())
            {
                System.out.println(msg.getClientDisplaytext());
                bMsgDisplayedText = true;
            }
            else if (MessageID.CLIENT_REQUEST_INPUT == msg.getMessageId())
            {
                ClientRequestInputMessage clMsg = (ClientRequestInputMessage) msg;

                System.out.print(clMsg.getMessage());

                if (ClientRequestInputMessage.Type.Normal == clMsg.getInputRequestType())
                {
                    String userInput = m_stdIn.readLine();

                    if (null != userInput)
                    {
                        m_serverOut.println(userInput);
                    }
                }
                else if (ClientRequestInputMessage.Type.Password == clMsg.getInputRequestType())
                {
                    char szPwd[] = System.console().readPassword();

                    if (null != szPwd)
                    {
                        String strPwd = new String(szPwd);

                        m_serverOut.println(strPwd);
                    }
                }
            }
            else if (MessageID.SERVER_STATUS == msg.getMessageId())
            {
                ServerStatusMessage srvMsg = (ServerStatusMessage) msg;

                // If interactive logon is complete, then begin bi-directional communication
                if (ServerStatusMessage.Status.LOGON_SUCCESS == srvMsg.getStatus())
                {
                    System.out.println("Login successful.");

                    // Create and start thread to read user input and send it to the server
                    UserInputThread userInThread = new UserInputThread(m_sslSocket, m_stopSem, m_rwLock);
                    userInThread.start();
                    
                    bMsgDisplayedText = true;
                }
                else if (ServerStatusMessage.Status.LOGOUT == srvMsg.getStatus())
                {
                    // acquire the semaphore to stop the client
                    m_stopSem.acquire();
                }
            }
            else if (MessageID.USER_LOGOUT == msg.getMessageId())
            {
                // acquire the semaphore to stop the client
                m_stopSem.acquire();
            }
        }
        catch (Exception e)
        {
            bMsgDisplayedText = false;
        }

        return bMsgDisplayedText;
    }
}
