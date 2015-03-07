package grep;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Trieda MyParser slúži na spracovanie argumentov príkazového riadku.
 * Pre blizsie informacie ako s triedou pracovat pouzite prikaz -man
 * @author Martin Čuka 5ZI021
 */
public class MyParser {

    private final String[] zadanyVyraz;
    private final ArrayList<String> ostatneArgumenty;
    private final ArrayList<String> prepinace;

    /**
     * Konstruktor 
     * @param args 
     */
    public MyParser(String[] args) {

        this.zadanyVyraz = args;
        this.ostatneArgumenty = new ArrayList<>();
        this.prepinace = new ArrayList<>();

        parse();

    }

    /**
     * Rozdeli argumenty prikazovej riadky na prepinace, regularny vyraz a cesty k textovym suborom
     */
    private void parse() {

        if (zadanyVyraz.length == 0) {
            System.err.println("Neboli zadane dostatocne parametre.");
            System.err.println("Treba zadat minimalne regularny vyraz a subor v ktorom hladat.");
            System.err.println("Pre viac informacii zadajte prepinac \"-man\"");
            System.exit(0);
        }

        Pattern p = Pattern.compile("^\\-[a-zA-Z]+"); // prvy znak je pomlcka dalsi pismeno a potom lubovolne znaky
        for (String arg : this.zadanyVyraz) {
            Matcher mat = p.matcher(arg);
            if (mat.matches()) {
                String tmp = mat.group(); // vrati do Stringu prepinac co nasiel
                String s = tmp.substring(1); // vymaze pomlcku
                prepinace.add(s); // ulozi do arrayListu
            } else {
                this.ostatneArgumenty.add(arg);
            }
        }
        
        if (prepinace.isEmpty())
            prepinace.add("null");
    }

    /**
     * Getter pre argumenty (argument[0] = regExp) zvysne = cesty k textovym suborom
     * @return ArrayList - vrati pole argumentov zadanych z prikazovej riadky
     */
    public ArrayList<String> getOstatneArgumenty() {
        return ostatneArgumenty;
    }

    /**
     * Getter pre prepinace
     * @return ArrayList - vrati pole prepinacov zadanych z prikazovej riadky
     */
    public ArrayList<String> getPrepinace() {
        return prepinace;
    }
}
