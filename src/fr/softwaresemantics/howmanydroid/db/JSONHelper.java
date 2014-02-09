package fr.softwaresemantics.howmanydroid.db;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by christophe goessen on 02/02/14.
 */
public class JSONHelper {
    public static void main(String[] args) {

        I18n hello = new I18n();
        I18n JsonI18n;
        Translation trans = new Translation();

        Locale english = new Locale();
        Locale fromJSON = new Locale();
        english.setDescription("english UK");
        english.setName("en_GB");


        Locale french = new Locale();
        french.setDescription("Français France");
        french.setName("fr_FR");

        trans.setLocale(english);
        trans.setValue("Hello World");
        trans.setI18n(hello);
        hello.setTranslations(new ArrayList<Translation>());
        hello.getTranslations().add(trans);
        hello.setMsgID("HELLO_WORLD");
        trans = new Translation();
        trans.setLocale(french);
        trans.setValue("Bonjour Monde");
        trans.setI18n(hello);
        hello.getTranslations().add(trans);


        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(english));
            fromJSON = mapper.readValue(mapper.writeValueAsString(english), Locale.class);
            System.out.println(fromJSON.getDescription());

            System.out.println(mapper.writeValueAsString(hello));
            JsonI18n = mapper.readValue(mapper.writeValueAsString(hello),I18n.class);
            System.out.println("obj->json->obj");
            for (Translation tr: JsonI18n.getTranslations())
                System.out.println(tr.getI18n().getMsgID()+"@"+tr.getLocale().getDescription()+":="+tr.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
