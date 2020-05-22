import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

class ServerMail {
    
    private static final int port = 1805;
    public static HashMap<String,MailBox> users = new HashMap<>();
    public static HashMap<MailBox,String> passwords = new HashMap<>();

    public static void main(String[] args) throws Exception {
        MailBox a = new MailBox("super");
        users.put("super", a);
        passwords.put(a, ".");
        ServerSocket ascoltoSocket = new ServerSocket(ServerMail.port);
        System.out.println("Server multithread in ascolto sulla porta "+ ServerMail.port);

        while( true ) {
            
            Socket connSocket = ascoltoSocket.accept();
            TCPServerThread st = new TCPServerThread(connSocket);
            Thread th = new Thread(st);
            th.start();

        }
        


    }
    
}



class TCPServerThread implements Runnable {
    private Socket connSocket; // socket di connessione
    private String richiesta; // messaggio di richiesta proveniente dal client
    private String risposta; // messaggio di risposta elaborato in risposta al client
    private BufferedReader in_stream; // stream di ingresso al processo
    private DataOutputStream out_stream; // stream di uscita
    public MailBox mBox;
    
    
    public TCPServerThread(Socket conn) {
        connSocket = conn;
        try {
            in_stream = new BufferedReader( new InputStreamReader( connSocket.getInputStream() ) );
            out_stream = new DataOutputStream(connSocket.getOutputStream());  //stream di uscita del socket 
        } catch (IOException e) {  
            e.printStackTrace();
        }
    }
    
    public void run()  {
        richiesta = "";
        
        try { 

            while ( ! richiesta.equals("EXIT") ) {

                richiesta = in_stream.readLine();


                /* ===================== SIGN IN ===================== */
                if( richiesta.startsWith("SIGN_") ) {
                    /* messaggio ricevuto dal client:
                        SIGN_USR:username;PSW:password*/
                    
                    if( TCPServerThread.SIGN( richiesta.replace("SIGN_", "") ) ) {
                        /* iscrizione avvenuta con successo e rispondo OK */
                        out_stream.writeBytes("OK\n");
                    } else {
                        out_stream.writeBytes("ERR\n");
                    }
                }


                /* ===================== LOG IN ===================== */
                else if ( richiesta.startsWith("LOG_") ) {
                    /* messaggio ricevuto dal client:
                        LOG_USR:username;PSW:password*/
                    
                    if( TCPServerThread.LOG( richiesta.replace("LOG_", ""), this) ){
                        /* accesso autorizzato e rispondo OK */
                        out_stream.writeBytes("OK\n");
                        System.out.println("-"+mBox.getName() + " log.");
                        TCPServerThread.SERVE(mBox, in_stream, out_stream);
                        System.out.println("-"+mBox.getName() + " log out.");

                    } else {
                        out_stream.writeBytes("ERR\n");
                    }

                }

            }

        connSocket.close(); // chiusura del socket

        } catch (Exception e) {  
            e.printStackTrace();
        }
    }

    public static void SERVE(MailBox MB, BufferedReader in_stream, DataOutputStream out_stream) throws Exception {
        /* In questa modalita' il server attende istruzioni perservire poi il client */
        String richiesta = "";
        String risposta = "";

        while ( ! richiesta.equals("OUT") ) {

            richiesta = in_stream.readLine();
            System.out.println("Messaggio: " + richiesta + "\nDa: " + MB.getName());

            /* ================== LIST PROCEDURE ==================== */
            if( richiesta.equals("LIST") ){
                out_stream.writeBytes( String.valueOf(MB.numberOfMails()) + "\n" );
                System.out.println("Risposta: " + String.valueOf(MB.numberOfMails()) + "\n=========");
            }


            /* ================== GET PROCEDURE ==================== */
            else if( richiesta.startsWith("GET_") ) {
                try{
                    int index = Integer.valueOf(richiesta.replace("GET_", ""));
                    Mail m = MB.getMail( index );
                    int righe = m.length();
                    risposta = "OK\n"+String.valueOf(righe)+"\n"+m.toString();
                    out_stream.writeBytes(risposta+"\n");
                    System.out.println("Risposta: " + risposta + "\n=========");
                } catch(Exception e) {
                    out_stream.writeBytes("ERR\n");
                    System.out.println("Risposta: ERR\n=========");
                    
                }
            }

            /* ================== SEND PROCEDURE ==================== */
            else if ( richiesta.startsWith("SEND_") ) {
                
                try{
                    String[] lines = richiesta.split(";");
                    String dest = lines[0].replace("SEND_TO:", "");

                    if ( MailBox.exist(dest) ){
                        MailBox mBoxDest = ServerMail.users.get(dest);
                        int index = Integer.valueOf(lines[1].replace("TXT:", ""));
                        String txt = "";
                        for(int i = 0; i < index; i++){
                            txt = txt + in_stream.readLine() + "\n";
                        }
                        //System.out.println(txt);
                        SimpleDateFormat form = new SimpleDateFormat("E dd MM yyyy HH:mm:ss");
                        Date ora = new Date();
                        String mes = "FROM:" + MB.getName() + "\nSEND:" + form.format(ora) + "\nTEXT:" + txt + "\n";
                        mBoxDest.receiveMail( mes );

                        out_stream.writeBytes("OK\n");
                        System.out.println("MES: "+mes);
                        System.out.println("Risposta: OK\n=========");
                        
                    } else {
                        out_stream.writeBytes("ERR\n");
                        System.out.println("Risposta: ERR\n=========");
                    }

                    
                } catch (Exception e) {
                    out_stream.writeBytes("ERR\n");
                    System.out.println("Risposta: OK\n=========");
                }

            }



            /* ================== DEL PROCEDURE ==================== */
            else if( richiesta.startsWith("DEL_") ) {

                try {
                    int index = Integer.valueOf( richiesta.replace("DEL_", "") );
                    MB.removeMail(index);
                    out_stream.writeBytes("OK\n");
                } catch (Exception e) {
                    out_stream.writeBytes("ERR\n");
                }

            }
            
        }
    }

    private static boolean SIGN(String msg){
        /* messaggio in input
            "USR:username;PSW:password*/
        
        try {
            String[] lines = msg.split(";");
            System.out.println("Richiesta di iscrizione :\n" + msg);

            String user = lines[0].replace("USR:", "");
            String passw = lines[1].replace("PSW:", "");

            if ( MailBox.exist(user) ) return false;
            else {
                MailBox M = new MailBox(user);
                ServerMail.users.put(user, M);
                ServerMail.passwords.put(M, passw);
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean LOG(String msg, TCPServerThread th){
        /* messaggio in input
            "USR:username;PSW:password*/
        
        try {
            String[] lines = msg.split(";");
            System.out.println("Richiesta di accesso :\n" + msg);

            String user = lines[0].replace("USR:", "");
            String passw = lines[1].replace("PSW:", "");
            MailBox M = ServerMail.users.get(user);

            if ( M == null ) return false;
            else {
                if ( ServerMail.passwords.get(M).equals(passw) ) {
                    th.mBox = M;
                    return true;
                }
                else return false;
            }

        } catch (Exception e) {
            return false;
        }
        
    }
  
  }