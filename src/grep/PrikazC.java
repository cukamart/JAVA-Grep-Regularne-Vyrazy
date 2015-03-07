/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grep;

//
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Trieda PrikazC spocita riadky v kolkych sa nachadza dany regularny vyraz
 * @author Martin Čuka 5ZI021
 */
public class PrikazC implements IPrikazy {

    @Override
    public void vykonaj(ArrayList<String> vysledok, String[] argumenty, int i) {
        Pattern p = Pattern.compile(argumenty[0]);
        int count = 0;
        for (String string : vysledok) {
            Matcher mat = p.matcher(string);
            if (mat.find()) {
                count++;
            }
        }
        System.out.println("V subore \"" + argumenty[i] + " sa nachadza vyraz " + argumenty[0]);
        System.out.println(count + " krat");
        System.out.println();
    }

    @Override
    public void dajPopis() {
        System.out.println("Prepinac -c vypise pocet riadkov v ktorych sa nachadza hladany regularny vyraz");
    }

}
