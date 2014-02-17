package fr.softwaresemantics.howmanydroid.db;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by cyril Hansen
 */
public class TestDbHelper {
    private static Locale en;
    private static Locale fr;

/*    Géométrie
    - (Conversions d'unités)
    - Périmètres et Surface dans le plan
    - - Cercle
    - - Parallélogramme
    - - Rectangle
    - - Trapézoide
    - - Triangle

    - Surface et Volumes dans l'espace
            - - Sphere
    - - Cone de révolution
    - - Cylindre
    - - Prisme rectangle
    - - Pyramide à base carrée
    - - Isosceles Triangular Prism
    - - Circle Sector & Arc
    - - Elipse

    - Triangles et Pythagore
    - Repères et Coordonnées

    Finance
    - (Conversions de devises)
    - Prêts et intérets

    Sciences et techniques

    -- Forces et Mécanique
    ---- Gravitation

    -- Fluides
    --- Gaz parfaits
    --- Flux
    --- Pression

    -- Transferts de chaleur

    -- Electricité
    --- Fondamentaux de l'électronique
    --- Circuits usuels
    --- Magnétisme

    -- Optique
    --- Reflection
    --- Refraction
    --- Lentilles*/

    public static void main(String[] args) {

        TestDbHelper helper = new TestDbHelper();

        helper.initLocales();

        Collection<Category>  rootDoc = helper.genCategoryTree();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
            mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS,true);
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,true);
            /*
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true); //accept null collection*/
            String Json = mapper.writeValueAsString(rootDoc);
            System.out.println(Json);
            System.out.println("Testing Deserialization");
            List<Category> DBFromJson = mapper.readValue(Json, mapper.getTypeFactory().constructCollectionType(List.class, Category.class));
            Json = mapper.writeValueAsString(DBFromJson);
            System.out.println(Json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*I18n hello = new I18n();
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
        }*/

    }

    private  Collection<Category> genCategoryTree() {

        Collection<Category>  rootDoc =  new ArrayList<Category>();

        Category unitConvert = defineCategory(null, "UNIT_CONVERT", "Unit convertion", "Conversion d'unité");
        rootDoc.add(unitConvert);


       // -----
        Category geometry = defineCategory(null, "GEOM", "Geometry", "Géométrie");
        rootDoc.add(geometry);

        Category lengthAndArea = defineCategory(geometry, "GEOM_AREA", "Length & planar Area", "Périmètre et Surface plane");
        Category circle = defineCategory(lengthAndArea, "GEOM_AREA_CIRC", "Circle", "Cercle");
        Category paral = defineCategory(lengthAndArea, "GEOM_AREA_PARAL", "Circle", "Parallélogramme");
        Category rect = defineCategory(lengthAndArea, "GEOM_AREA_RECT", "Rectangle", "Rectangle");
        Category trapezoid = defineCategory(lengthAndArea, "GEOM_AREA_TRAPZ", "Trapezoid", "Trapézoîde");
        Category triangle = defineCategory(lengthAndArea, "GEOM_AREA_TRIANG", "Triangle", "Triangle");
    //--
        Category surfaceAndVolume = defineCategory(geometry, "GEOM_VOLUME", "Area & Volume (3D)", "Surface et Volumes (3D)");
        Category sphere = defineCategory(surfaceAndVolume, "GEOM_VOL_SPHERE", "Sphere", "Sphère");
        Category cone = defineCategory(surfaceAndVolume, "GEOM_VOL_CONE", "Cone", "Cône");
        Category cylinder = defineCategory(surfaceAndVolume, "GEOM_VOL_CYL", "Cylinder", "Cylindre");
        Category rectPrism = defineCategory(surfaceAndVolume, "GEOM_VOL_RECTPRISM", "Rectangle Prism", "Prisme rectangle");
        Category rectPyram = defineCategory(surfaceAndVolume, "GEOM_VOL_RECTPYRAM", "Pyramide", "Pyramide");
        // add 3 more...

        Category triangleAndPythagoras = defineCategory(geometry, "GEOM_TRIANGLE", "Triangle,Pythagoras &Co", "Triangles, Pythagore &Cie");
        Category coordinatesAndFrame = defineCategory(geometry, "GEOM_COORD", "Coordinate system", "Repère & Coordonnées");

        // -----
        Category finance = defineCategory(null, "FINANCE", "Finance", "Finance");
        rootDoc.add(finance);
        Category currencyConvert = defineCategory(finance, "FIN_CURRENCY", "Currency conversion", "Conversion de devises");
        Category loans = defineCategory(finance, "FIN_LOAN", "Loan & Interests", "Prêts et Intérets");


        Category science = defineCategory(null, "TECH", "Science & Engineering", "Sciences & Techniques");
        rootDoc.add(science);
        Category mecanics = defineCategory(science, "TECH_FORCE", "Forces & Mecanics", "Forces & Mécanique");
        Category gravitation = defineCategory(mecanics, "TECH_FORCE_GRAVI", "Gravitation", "Gravitation");

        Category fluids = defineCategory(science, "TECH_FLUIDS", "Fluids", "Fluides");
        Category idealGas = defineCategory(fluids, "TECH_FLUIDS_IDEALGAS", "Ideal Gas", "Gaz parfaits");
        Category flows = defineCategory(fluids, "TECH_FLUIDS_FLOW", "Flow", "Flux");
        Category pressure = defineCategory(fluids, "TECH_FLUIDS_PRESSURE", "Pressure", "Pression");

        Category heatTransfer = defineCategory(science, "TECH_HEAT", "Heat Transfer", "Transfert de chaleur");

        Category electricity = defineCategory(science, "TECH_ELECTRICITY", "Electricity", "Electricité");

        Category electronics101 = defineCategory(electricity, "TECH_ELEC_FUNDAM", "Electronics Fundamentals", "Electronique fondamentale");
        Category usualCircuits = defineCategory(electricity, "TECH_ELEC_CIRCUITS", "Usual Circuits", "Circuits usuels");
        Category magnetism = defineCategory(electricity, "TECH_ELEC_MAGNET", "Magnetism", "Magnétisme");


        return rootDoc;

    }

    private Category defineCategory(Category parent, String id, String labelEn, String labelFr) {
        Category newCategory = new Category();

        ArrayList<Translation> transList = new ArrayList<Translation>();

        I18n i18n = new I18n();
        Translation translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue(labelEn);
        i18n.setMsgID(id);
        transList.add(translation);


        translation = new Translation();
        translation.setLocale(fr);
        translation.setI18n(i18n);
        translation.setValue(labelFr);
        i18n.setMsgID(id);
        transList.add(translation);
        i18n.setTranslations(transList);

        newCategory.setName(i18n);

        i18n = new I18n();
        transList = new ArrayList<Translation>();
        translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue(labelEn + " Description to be defined");
        i18n.setMsgID(id + "_DESC");
        transList.add(translation);

        translation = new Translation();
        translation.setLocale(fr);
        translation.setI18n(i18n);
        translation.setValue(labelFr + " Description to be defined");
        i18n.setMsgID(id + "_DESC");
        transList.add(translation);

        i18n.setTranslations(transList);
        newCategory.setDescription(i18n);

        if (parent != null) {
            Collections.addAll(parent.getChildren(), newCategory);
            newCategory.setParent(parent);
        }

        return newCategory;
    }

    private void initLocales() {
        en = new Locale();
        en.setName("en_GB");
        en.setDescription("English (UK)");

        fr = new Locale();
        en.setName("fr_FR");
        en.setDescription("Français (FR)");
    }
}
