import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DataManagementSystem {
    public static void RegisterUser(String name, String password) throws Exception {
            File file = new File("./Data/passw.txt");
            FileWriter passw = new FileWriter(file,true);
            BufferedWriter writer = new BufferedWriter( passw );
            writer.append(name+":"+password+"\n");
            writer.close();
            new File("./Data/"+name).mkdir();
            passw.close();
    }


    public static void Log(String s) throws Exception {
        File file = new File("./Data/log.txt");
        FileWriter passw = new FileWriter(file,true);
        BufferedWriter writer = new BufferedWriter( passw );
        SimpleDateFormat format = new SimpleDateFormat("E dd MM yyyy HH:mm:ss");
        Date date = new Date();
        writer.append(format.format(date)+ "\t"+s+"\n");
        writer.close();
        passw.close();
    }

    public static void addMail(Mail m) throws Exception {
        File file = new File("./Data/" + m.to() + "/" + m.from() + "_" + "'" + m.when() + "'.txt");
        FileWriter fWriter = new FileWriter(file,true);
        BufferedWriter writer = new BufferedWriter( fWriter );
        writer.append(m.toString());
        writer.close();
        fWriter.close();
    }

    public static void deleteMail(Mail m) throws Exception {

    }
    
}