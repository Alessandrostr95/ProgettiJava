import java.io.*;
import java.util.*;
import java.text.*;  

class MailBox {
    private String name;
    private static TreeSet<String> allNames = new TreeSet<>();
    private LinkedList<Mail> mails;

    public MailBox(String name) {
        if ( ! MailBox.exist(name) ) {
            this.name = name;
            this.mails = new LinkedList<Mail>();
            MailBox.allNames.add(name);
        } else {
            throw new IllegalArgumentException("Nick name gia' in uso");
        }
    }

    /* ritorna true se il nome e' gia' in uso, false altrimenti */
    public static boolean exist(String name) { return MailBox.allNames.contains(name); }

    /*  FROM - SEND - TEXT */
    public void receiveMail(String msg) throws Exception {
        /*  formato del messaggio
            FROM:mit\n
            SEND:data\n
            TEXT:...
             */
        
        try{
            /*
            BufferedReader buff = new BufferedReader( new StringReader(msg) );
            System.out.println(buff.readLine());
            System.out.println(buff.readLine());
            
            char[] txt = new char[1024];
            buff.read(txt);
            System.out.println(txt);*/

            LinkedList<String> lines = new LinkedList<>();
            Scanner scan = new Scanner(msg);
            while( scan.hasNextLine() ){
                lines.add(scan.nextLine());
            }
            scan.close();
            //System.out.println(lines);

            /* setto variabili per costruttore mail */
            String mittente = lines.get(0).split(":")[1];
            
            String destinatario = this.name;

            SimpleDateFormat format = new SimpleDateFormat("E dd MM yyyy HH:mm:ss");
            Date send = format.parse( lines.get(1).replaceAll("SEND:", "") );

            String txt = lines.get(2).replace("TEXT:", "");
            for (int i = 3; i < lines.size(); i++) {
                txt = txt+"\n"+lines.get(i);
            }

           this.mails.add( new Mail(mittente, destinatario, send, txt) );

        } catch (Exception e) {
            throw new IllegalArgumentException("Formato messagio non valido. Riprovare");
        }

    }

    public void showMails() {
        for( Mail m : this.mails ) {
            System.out.println(m);
            System.out.println("\n\t \\=============\\ \n\n");
        }
    }

    public Mail getMail(int index) throws IndexOutOfBoundsException {
        return this.mails.get(index);
    }

    public void removeMail(int index) throws IndexOutOfBoundsException {
        this.mails.remove(index);
    }

    public String getName() { return this.name; }

    public int numberOfMails() { return this.mails.size(); }

    public static void main(String[] args) throws Exception {
        
        /* creare una data formattata */
        /* da data a stringa */
        SimpleDateFormat form = new SimpleDateFormat("E dd MM yyyy HH:mm:ss");
        Date ora = new Date();
        String mes1 = "FROM:mittente1\nSEND:" + form.format(ora) +"\nTEXT:Ciao\nSpero tu stia bene.\nSaluti.";
        String mes2 = "FROM:mittente2\nSEND:" + form.format(ora) +"\nTEXT:Ciao\nSono il mittente 2\n;)\n\n\ne";
        MailBox M = new MailBox("alessandro");
        M.receiveMail(mes1);
        M.receiveMail(mes2);
        M.showMails();
        System.out.println(M.getMail(0).length());
        System.out.println(M.getMail(1).length());

        /*BufferedReader tastiera = new BufferedReader( new InputStreamReader( System.in ) );
        
        String t = "";
        while ( ! t.endsWith("[end]") ){
            t = t + tastiera.readLine(); 
        }
        System.out.println(t);*/
    }


}


/* FORMATO MAIL 
        FROM:mittente\n
        SEND:data_spedizione\n
        TEXT:testo.
*/

class Mail{
    private String FROM;
    private String TO;
    private Date send;
    private Date receive;
    private String TEXT;

    public Mail(String from, String to, Date send, String text){
        this.FROM = from;
        this.TO = to;
        this.send = send;
        this.TEXT = text;
        this.receive = new Date();
    }

    public String toString() {
        return  "FROM: " + this.FROM +
                "\nTO: " + this.TO + 
                "\nSEND: " + this.send.toString() +
                "\nRECEIVED: " + this.receive.toString() +
                "\n\nTEXT:\n" + this.TEXT + "\n";
    }

    public int length() {
        /* ritorna il numero di righe totali più il numero di intestazioni di una mail */
        return this.TEXT.split("\n").length + 6;
    }
}
