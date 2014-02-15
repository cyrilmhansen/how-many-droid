package fr.softwaresemantics.howmanydroid.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import fr.softwaresemantics.howmanydroid.R;
import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;

/**
 * Created by cmh on 05/01/14.
 */
public class MJView {

    private Context context;
    private WebView w;
    private boolean isInitializing;
    private boolean somethingWaitingToDisplay;

    private long timeStampStartTypeSet;
    private int lastTypeSetDelayMs;

    private FormulaSyntax syntax;
    private String[] formula;


    public MJView(Context context, WebView w, int nbLines) {
        formula = new String[2];
        this.w = w;
        this.context = context;
        initMathjax(w, nbLines);
    }

    public WebView getWebView() {
        return w;
    }

    private void initMathjax(WebView w, int nbLines) {
        isInitializing = true;
        w.setVisibility(View.GONE);
        w.getSettings().setJavaScriptEnabled(true);
        w.addJavascriptInterface(this, "MJView");
        w.loadUrl("file:///android_asset/baseDocMj3.html?nbLines="+nbLines);

    }
    public void notifyMJTypesetComplete(final int blockHeight) {

        lastTypeSetDelayMs = (int) (System.currentTimeMillis() - timeStampStartTypeSet);
        timeStampStartTypeSet = 0;
        Log.i("notifyMJTypesetComplete", "MJTypesetComplete in ms for " + lastTypeSetDelayMs + " " + formula);
        Log.i("notifyMJTypesetComplete", "block height =" + blockHeight);
        // w.setMinimumHeight(blockHeight+50); // TODO Temp debug ?


        // Cette notification est exécuté dans un thread spécifique "Java Bridge"
        // impropre à l'appel de la webview
        Runnable myRunnable = new Runnable() {
            public void run() {
                ViewGroup.LayoutParams layoutParams = w.getLayoutParams();
                if (layoutParams != null) {
                layoutParams.height = blockHeight * 5 +  10;
                } else {
                    Log.e("notifyMJTypesetComplete", "called too early / layoutParams null");
                }
                w.setVisibility(View.VISIBLE);
                }
            };
            runInUIThread( myRunnable);
    }

    public int getLastTypeSetDelayMs(){
        return lastTypeSetDelayMs;
    }

    public void notifyMJInitComplete() {
        Log.i("notifyMJInitComplete", "MJInitComplete");
        isInitializing = false;
        if (somethingWaitingToDisplay) {
            // Cette notification est exécuté dans un thread spécifique "Java Bridge"
            // impropre à l'appel de la webview
            Runnable myRunnable = new Runnable() {
                public void run() {
                    for (int i = 0; i < formula.length; i++) {
                        if (formula[i] == null)
                            break;
                         updateFormula(syntax, formula[i],i);
                    }
                }};
            runInUIThread( myRunnable);
        }
    }

    private void runInUIThread(Runnable myRunnable) {
        // Cette notification est exécuté dans un thread spécifique "Java Bridge"
        // impropre à l'appel de la webview
        Handler mainHandler = new Handler(context.getMainLooper());
//        Runnable myRunnable = new Runnable() {
//            public void run() {
//                for (int i = 0; i < formula.length; i++) {
//                    if (formula[i] == null)
//                        break;
//                    updateFormula(syntax, formula[i],i);
//                }
//            }};
        mainHandler.post(myRunnable);
    }

    public void displayMath(FormulaSyntax syntax, String formula) {
        Log.i("displayMath", "MJTypesetComplete");
        timeStampStartTypeSet = System.currentTimeMillis();
        displayMath(syntax, formula, 0);
        // FIXME? : effacer les autres lignes ?
    }

    public void displayMath(FormulaSyntax syntax, String formula, int line) {
        this.formula[line] = formula;
        this.syntax = syntax;

        // Se synchroniser et attendre la fin de l'init MJ
        if (!isInitializing) {
            updateFormula(syntax, formula, line);
        } else {
            // marquer à faire plus tard
            somethingWaitingToDisplay = true;
        }
    }


    public void clearAllAndHide() {
        displayMath(FormulaSyntax.ASCII_MATHML, "", 0);
        w.setVisibility(View.GONE);
    }

    public void updateFormula(FormulaSyntax syntax, String formula, int line) {
        switch (syntax) {
            case ASCII_MATHML:
                w.loadUrl("javascript:UpdateMathAsciiML('" + formula + "', "+line+");");
                break;
            case TEX:
                // Attention les formules Tex doivent quoter les _\
                String quoted = formula.replace("\\", "\\\\");
                w.loadUrl("javascript:UpdateMathTexML('" + quoted + "', "+line+");");
                break;
            case DISPLAY_MATHML:
                String quoted2 = formula.replace("\\", "\\\\");
                w.loadUrl("javascript:UpdateMathML('" + quoted2 + "', "+line+");");
                break;
        }
    }

    public String loadFormulaFromDemo(String path, String file) {
        try {
            String formula = AssetsUtil.loadUTF8AssetAsString(context, path, file);
            formula = formula.replaceAll("\r|\n", "");

            return formula;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
