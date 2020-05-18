import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

class ClientMail {

    private static final String nomaServer = "localhost";
    private static final int port = 1805;
    private static final String version = "0.1";


    public static void main(String[] args) throws Exception {
        
        
        Socket clientSocket = new Socket( ClientMail.nomaServer, ClientMail.port );
        BufferedReader in_stream = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
        DataOutputStream out_stream = new DataOutputStream( clientSocket.getOutputStream() );

        //DataInputStream in = new DataInputStream( clientSocket.getInputStream() );

        BufferedReader in_tastiera = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Benvenuto nel ServerMail di Alessandro Straziota - uniroma2 Tor Vergata scienze MM FF NN.\nDigita:");
        System.out.println("\t- \"log\" per effettuare l'accesso");
        System.out.println("\t- \"sign\" per registrare un account");
        System.out.println("\t- \"exit\" per terminare");
        System.out.println("\t- \"help\" per visualizzare i comandi");
        
        //String  stringa = in_tastiera.readLine().toUpperCase();
        
        String CMD = "";
        String MSG = "";
        
        while( ! CMD.equals("EXIT") ) {
            CMD = in_tastiera.readLine().toUpperCase();
            
            switch( CMD ){

                case "LOG":
                    
                    MSG = ClientMail.LOGIN();
                    out_stream.writeBytes("LOG_"+ MSG + "\n");
                    /* messaggio inviato al server:
                        LOG_USR:username;PSW:password\n */

                    /* il messaggio di risposta sarà OK solamente se l'accesso avverra' con successo.
                        In tal caso si entra nella modalita' di servizio mail. */
                    if( in_stream.readLine().equals("OK") ){
                        ClientMail.SERVICE(in_stream, out_stream);
                    }
                    else System.out.println("Username o password errata.");
                    break;
                
                case "SIGN":
                    
                    MSG = ClientMail.SIGNIN();
                    out_stream.writeBytes("SIGN_"+ MSG + "\n");
                    /* messaggio inviato al server:
                        SIGN_USR:username;PSW:password\n */
                    
                    /* il messaggio di risposta sarà OK solamente se la registrazione sarà avvenuta con successo */
                    if( in_stream.readLine().equals("OK") ) System.out.println("Account registrato correttamente.");
                    else System.out.println("Username gia' in uso.");
                    break;
                
                case "EXIT":
                    System.out.println("Arrivederci!");
                    out_stream.writeBytes("EXIT");
                    break;

                case "HELP":
                    System.out.println("\t- \"log\" per effettuare l'accesso");
                    System.out.println("\t- \"sign\" per registrare un account");
                    System.out.println("\t- \"exit\" per terminare");
                    System.out.println("\t- \"help\" per visualizzare i comandi");
                    System.out.println("\t- \"version\" per visualizzare la versione");
                    break;
                
                case "VERSION":
                    System.out.println("Applicazione di posta elettronica di Alessandro Straziota - versione " + ClientMail.version);
                    break;
                
                default:
                    System.out.println("Comando inesistente.");
                    break;
            }
        }

        clientSocket.close();

    }

    private static String LOGIN(){
        
        try{
            
            BufferedReader tastiera = new BufferedReader( new InputStreamReader( System.in ) );
            System.out.println("Username:");
            String usr = tastiera.readLine().toLowerCase();
            System.out.println("Password:");
            String psw = tastiera.readLine();

            return "USR:" + usr + ";PSW:" + psw;

        } catch (Exception e) {
            System.err.println("ERRORE");
            return "USR:" + ";PSW:";
        }

    }

    private static String SIGNIN(){
        
        try{
            BufferedReader tastiera = new BufferedReader( new InputStreamReader( System.in ) );
            System.out.println("Inserire un username:");
            String usr = tastiera.readLine().toLowerCase();
            System.out.println("Inserire una password lunga almeno 8 caratteri, con almeno un carattere minuscolo, uno maiuscolo e un numero:");
            String psw = tastiera.readLine();
            
            while( ! Password.isGood(psw) ) {
                System.out.println("Password non corretta.");
                System.out.println("La password lunga almeno 8 caratteri, con almeno un carattere minuscolo, uno maiuscolo e un numero:");
                psw = tastiera.readLine();
            }

            return "USR:" + usr + ";PSW:" + psw;

        } catch (Exception e) {
            System.err.println("ERRORE");
            return "USR:" + ";PSW:";
        }
    }

    private static void SERVICE(BufferedReader in_stream, DataOutputStream out_stream) throws Exception {

        System.out.println("Benvenuto!\nDigita:");
        System.out.println("\t- \"list\" per vedere quante mail hai");
        System.out.println("\t- \"get\" per leggere una mail");
        System.out.println("\t- \"del\" per cancellare una mail");
        System.out.println("\t- \"send\" per iniziare a inviare una mail");
        System.out.println("\t- \"out\" per visualizzare uscire");
        
        BufferedReader tastiera = new BufferedReader( new InputStreamReader( System.in ) );

        String CMD = "";
        String MSG = "";

        while ( ! CMD.equals("OUT") ) {
            
            CMD = tastiera.readLine().toUpperCase();

            switch (CMD) {
                
                case "LIST":
                    out_stream.writeBytes("LIST"+"\n");
                    MSG = in_stream.readLine();
                    System.out.println("Hai in totale " + MSG + " mail.");
                    break;

                case "SEND":
                    System.out.println("A chi vuoi inviare il messaggio?");
                    String destinatario = tastiera.readLine();
                    System.out.println("Inserire in testo. Scrivi [end] una volta terminato il messaggio");
                    String txt = "" ;
                    int righe = 1;
                    String t = tastiera.readLine();

                    while( ! t.endsWith("[end]") ) {
                        txt = txt + "\n" + t;
                        t = tastiera.readLine();
                        righe ++;
                    }

                    /* invio al server il messagio in formato:
                        SEND_TO:destinatario;TXT:#righe\ntesto*/
                    
                    out_stream.writeBytes("SEND_TO:" + destinatario + ";TXT:" + String.valueOf(righe) + "\n" + txt +"\n");

                    if( in_stream.readLine().equals("OK") ) System.out.println("Messaggio inviato.");
                    else System.out.println("Si è verificato un errore nell'invio del messaggio");

                    break;
                
                case "GET":
                    System.out.println("Quale mail vuoi leggere?");
                    MSG = tastiera.readLine();
                    out_stream.writeBytes("GET_" + MSG +"\n");
                    MSG = in_stream.readLine();
                    
                    if( ! MSG.equals("OK") ) System.out.println("Qualcosa e' andato storto. Controlla il formato del comando.");

                    else {
                        /* se si riceve come risposta OK verranno scritti in ordine:
                            1. il numero di righe del messaggio da dover leggere, comprese di intestazioni varie
                            2. la mail suddivisa in righe da dover stampare. */
                        int k = Integer.valueOf( in_stream.readLine() );
                        for ( int i = 0; i < k; i++ ) {
                            System.out.println( in_stream.readLine() );
                        }
                    }
                    break;
                
                case "DEL":
                    System.out.println("Indicare il numero dell'email che si desidera eliminare.");
                    MSG = tastiera.readLine();
                    out_stream.writeBytes("DEL_"+MSG+"\n");
                    if ( in_stream.readLine().equals("OK") ) System.out.println("Messaggio rimosso.");
                    else System.out.println("Qualcosa e' andato storto.");
                    break;
                
                case "OUT":
                    out_stream.writeBytes("OUT"+ "\n");
                    break;
            
                default:
                    System.out.println("Comando non conosciuto.");
                    break;
            }
            

        }


    }

}

class Password{
    private String pswrd;

    public Password(String word) { super(); }

    public static boolean isGood(String pw) {
        if ( Pattern.compile( "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,20})" ).matcher( pw ).find() ) return true;
        else return false;
    }
}