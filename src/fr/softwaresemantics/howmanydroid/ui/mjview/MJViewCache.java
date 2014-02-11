package fr.softwaresemantics.howmanydroid.ui.mjview;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import java.util.HashSet;
import java.util.Set;

import fr.softwaresemantics.howmanydroid.HMDApplication;
import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;
import fr.softwaresemantics.howmanydroid.util.MJView;

/**
 * Created by cmh on 09/02/14.
 */
public class MJViewCache {

    public static final int DEFAULT_CACHE_SIZE = 5;

    private static int cacheSize;
    private static MJViewCache instance;
    private static Context context;

    private Set<MJView> mjViews;
    private Set<MJView> mjViewsInUse;

    private MJViewCache(int cacheSize) {

        this.cacheSize = cacheSize;
        this.context = HMDApplication.getContext();

        mjViews = new HashSet<MJView>(cacheSize);
        mjViewsInUse = new HashSet<MJView>(cacheSize);

        // Initialisation
        for (int index = 0; index< cacheSize; index++) {
            WebView wv = new WebView(context);
            wv.setMinimumHeight(0);
            wv.setVisibility(View.GONE);

            MJView mjv = new MJView(context, wv, 1);
            //mjv.displayMath(FormulaSyntax.ASCII_MATHML, "pi.2.R^2+" + index);


            mjViews.add(mjv);

//            WebView wv = new WebView(HMDApplication.getContext());
//            //ll.addView(wv);
//            wv.setVisibility(View.GONE);
//
//            MJView mjView = new MJView(this, wv, 1);
//            mjView.displayMath(FormulaSyntax.ASCII_MATHML, "pi.2.R^2");
        }
    }

    public MJView getView() {
        if (!mjViews.isEmpty()) {
            MJView mjv = mjViews.iterator().next();
            mjViewsInUse.add(mjv);
            mjViews.remove(mjv);
            return mjv;
        }
        return null;
    }

    public void releaseView(MJView mjv) {
        mjViewsInUse.remove(mjv);
        mjViews.add(mjv);
        mjv.clearAllAndHide();
        // Attention le set "" va générer une notification ?
        // TODO Call MJ To clear Formulae in advance
    }

    public static void init(int cacheSize) {
        MJViewCache.cacheSize = cacheSize;
        getInstance();
    }

    public static void init() {
        init(DEFAULT_CACHE_SIZE);
    }

    public static MJViewCache getInstance() {
        if (instance == null) {
            instance = new MJViewCache(cacheSize);
        }

        return instance;
    }

}
