package fr.softwaresemantics.howmanydroid.ui.mockup;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import fr.softwaresemantics.howmanydroid.R;
import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;
import fr.softwaresemantics.howmanydroid.util.MJView;

/**
 * Created by cmh on 05/01/14.
 */
public class MockupInputParamActivity extends Activity {

    public MockupInputParamActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mockup_input_params);

        WebView w1 = (WebView) findViewById(R.id.webViewFormulaPres);
        WebView w2 = (WebView) findViewById(R.id.webViewComputationDetail);

        final MJView mjView1 = new MJView(this, w1);
        final MJView mjView2 = new MJView(this, w2);

        String formula1 = mjView1.loadFormulaFromDemo("demos/ascii", "demo2ConeArea.am");
        mjView1.displayMath(FormulaSyntax.TEX, formula1);

        String formula2 = mjView2.loadFormulaFromDemo("demos/ascii", "demo3ConeAreaLine1.am");
        String formula3 = mjView2.loadFormulaFromDemo("demos/ascii", "demo3ConeAreaLine2.am");
        mjView2.displayMath(FormulaSyntax.ASCII_MATHML, formula2, 0);
        mjView2.displayMath(FormulaSyntax.ASCII_MATHML, formula3, 1);


    }
}
