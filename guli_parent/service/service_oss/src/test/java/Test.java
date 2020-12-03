import org.joda.time.DateTime;

import java.io.*;

public class Test {



    public static void main(String args[]){
        System.out.println("sadasdasd");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:\\a.txt")), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }
            System.out.println(str);
        } catch (IOException e) {
        }

    }

}
