package fr.softwaresemantics.howmanydroid.db;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by christophe goessen on 02/02/14.
 */
public class JSONHelper {
    public static void main(String[] args) {

        Locale en = new Locale();
        en.setName("en_GB");
        en.setDescription("English (UK)");

        I18n i18n;
        Calculus calculus;
        Expression expression;
        Parameter parameter;
        Category category;
        Unit unit;
        Translation translation;
        Collection<Translation> transList;
        ObjectMapper mapper = new ObjectMapper();

        unit = new Unit();
        unit.setSymbol("cm");
        unit.setUnit_name("length"); //should be i18n?!
        unit.setValue_type("Double");

        expression = new Expression();
        expression.setExpression("L^2");
        expression.setType("Double");//?! can't remember the use of this
        expression.setUnit(unit);
        //should have a i18n desc

        parameter = new Parameter();
        parameter.setName("L");
        parameter.setUnit(unit);
        parameter.setType("Double");//?
        parameter.setExpression(expression);
        // we should have a i18n desc
        Collection<Parameter> paramList = new ArrayList<Parameter>();
        paramList.add(parameter);
        expression.setParameterList(paramList);

        calculus = new Calculus();
        expression.setCalculus(calculus);
        Collection<Expression> exprList =  new ArrayList<Expression>();
        exprList.add(expression);
        calculus.setExpressionList(exprList);
        i18n = new I18n();
        transList = new ArrayList<Translation>();
        translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue("Area of a Square");
        i18n.setMsgID("AREA_SQUARE");
        transList.add(translation);
        i18n.setTranslations(transList);
        calculus.setName(i18n);
        i18n = new I18n();
        transList = new ArrayList<Translation>();
        translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue("Calculate area of a square surface given its size (L)");
        i18n.setMsgID("AREA_SQUARE_DESC");
        transList.add(translation);
        i18n.setTranslations(transList);
        calculus.setDescription(i18n);

        category = new Category();
        i18n = new I18n();
        transList = new ArrayList<Translation>();
        translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue("Basic Geometric");
        i18n.setMsgID("GEOM_BASIC");
        transList.add(translation);
        i18n.setTranslations(transList);
        category.setName(i18n);
        i18n = new I18n();
        transList = new ArrayList<Translation>();
        translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue("Common formulae for usual geometrical calculus (e.g. area, volume of basic shapes)");
        i18n.setMsgID("GEOM_BASIC_DESC");
        transList.add(translation);
        i18n.setTranslations(transList);
        category.setDescription(i18n);

        Collection<Calculus> calList = new ArrayList<Calculus>();
        calList.add(calculus);
        calculus.setCategory(category);
        category.setCalculi(calList);


        Category rootCategory = new Category();
        i18n = new I18n();
        transList = new ArrayList<Translation>();
        translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue("Geometric");
        i18n.setMsgID("GEOM");
        transList.add(translation);
        i18n.setTranslations(transList);
        rootCategory.setName(i18n);
        i18n = new I18n();
        transList = new ArrayList<Translation>();
        translation = new Translation();
        translation.setLocale(en);
        translation.setI18n(i18n);
        translation.setValue("Common formulae for geometrical calculus (e.g. area, volume of basic shapes)");
        i18n.setMsgID("GEOM_DESC");
        transList.add(translation);
        i18n.setTranslations(transList);
        rootCategory.setDescription(i18n);
        Collection<Category> subcat = new ArrayList<Category>();
        subcat.add(category);
        category.setParent(rootCategory);
        rootCategory.setChildren(subcat);
        Collection<Category>  rootDoc =  new ArrayList<Category>();
        rootDoc.add(rootCategory);


        try {
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
}
