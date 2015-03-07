package grep;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PrikazV - vypise riadky v ktorych sa nenachadza dany regularny vyraz
 * @author Martin Čuka 5ZI021
 */
public class PrikazV implements IPrikazy {

    @Override
    public void vykonaj(ArrayList<String> vysledok, String[] argumenty, int i) {
        System.out.println("V subore \"" + argumenty[i] + " sa nenachadza vyraz " + argumenty[0] + " v nasledujucich riadkoch:");
        Pattern p = Pattern.compile(argumenty[0]);
        for (String string : vysledok) {
            Matcher mat = p.matcher(string);
            if (!mat.find()) {
                System.out.println(string);
            }
        }
        System.out.println();
    }

    @Override
    public void dajPopis() {
        System.out.println("Prepinac -v vypise tie riadky kde sa dany regularny vyraz nenachadza");
    }

}
