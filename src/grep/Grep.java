/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Martin Čuka 5ZI021
 */
public class Grep {

    /**
     * Vnorena Trieda Obsahuje vsetky mozne prikazy s ktorymi vieme operovat
     */
    private class ZoznamPrikazov {

        private final HashMap<String, IPrikazy> zoznamPrikazov;

        public ZoznamPrikazov() {
            zoznamPrikazov = new HashMap<>();

            zoznamPrikazov.put("c", new PrikazC());
            zoznamPrikazov.put("v", new PrikazV());
            zoznamPrikazov.put("null", new PrikazNULL());
        }
    }

    private final ZoznamPrikazov zp;
    private final MyParser parser;
    private final ArrayList<String> prepinace;
    private String[] argumenty;
    private final ArrayList<String> vysledok;

    public Grep(String[] args) {
        parser = new MyParser(args);
        vysledok = new ArrayList<>();
        prepinace = parser.getPrepinace();
        argumenty = new String[parser.getOstatneArgumenty().size()];
        argumenty = parser.getOstatneArgumenty().toArray(argumenty);
        zp = new ZoznamPrikazov();
        
        
        if (args[0].equals("-man")) {
            System.out.println("Program pracuje s argumentami z prikazovej riadky nasledujucim sposobom!");
            System.out.println("[prepinace] [regularny vyraz] [cesty k suborom]");
            System.out.println("[-c -v -xyz...] [banana|orange|apple] [c:\\ProgramFiles\\ovocie.txt d:\\zelenina.txt");
            System.out.println("-c -v ^Jano.*^ osoby.txt");
            System.out.println("--------------------------------");
            System.out.println("Zoznam prepinacov a ich vyznamy:");
            System.out.println("--------------------------------\n");
            for (String string : zp.zoznamPrikazov.keySet()) {
                zp.zoznamPrikazov.get(string).dajPopis();
            }
            System.exit(0);
        }
    }

    /**
     * Nacita postupne vsetky subory a postupne nad nimi robi dane operacie.
     *
     * @throws IOException ak sa nepodarilo nacitat subor
     */
    private void LoadFiles() throws IOException {
        for (int i = 1; i < argumenty.length; i++) {
            if (ReadFile(i)) {
                LoadIPrikaz(i);
            }
            vysledok.clear();
        }
    }

    /**
     * Precita postupne vsetky subory....
     *
     * @param i poradove cislo suboru
     * @throws IOException ak sa nepodarilo nacitat subor
     * @return - true ak sa podari nacitat false ak sa nepodari
     */
    public boolean ReadFile(int i) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(argumenty[i]))) {
            String line;
            while ((line = br.readLine()) != null) {
                vysledok.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Zadali ste neplatnu cestu k suboru: " + argumenty[i]);
            return false;
        }
        return true;
    }

    /**
     * Vykona prikazy
     *
     * @param i poradove cislo suboru
     */
    private void LoadIPrikaz(int i) {
        for (String string : prepinace) {
            if (zp.zoznamPrikazov.containsKey(string)) {
                zp.zoznamPrikazov.get(string).vykonaj(vysledok, argumenty, i);
            }
        }
    }
    
    /**
     * Vstupny bod programu
     * @param args nacitane parametre z prikazovej riadky
     * @throws IOException - vynimka v pripade nespravnej cesty k suboru
     */
    public static void main(String[] args) throws IOException {
        Grep g = new Grep(args);
        g.LoadFiles();
    }
}
