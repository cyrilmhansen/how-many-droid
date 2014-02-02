package fr.softwaresemantics.howmanydroid.ui.mockup;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import fr.softwaresemantics.howmanydroid.R;
import fr.softwaresemantics.howmanydroid.model.ast.Expr;
import fr.softwaresemantics.howmanydroid.model.ast.parser.ASTParser;
import fr.softwaresemantics.howmanydroid.model.ast.parser.generated.ExpressionParser;
import fr.softwaresemantics.howmanydroid.model.ast.parser.generated.ExpressionScanner;
import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;
import fr.softwaresemantics.howmanydroid.util.MJView;
import fr.softwaresemantics.howmanydroid.util.MsgDialog;

/**
 * Created by cmh on 05/01/14.
 */
public class MockupInputParamActivity extends Activity {

    private int currentTargetVar = 1;
    private MJView mjView1;
    private MJView mjView2;

    private EditText et1;
    private EditText et2;

    public MockupInputParamActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mockup_input_params);

        WebView w1 = (WebView) findViewById(R.id.webViewFormulaPres);
        WebView w2 = (WebView) findViewById(R.id.webViewComputationDetail);

        mjView1 = new MJView(this, w1, 1);
        mjView2 = new MJView(this, w2, 2);

        String formula1 = mjView1.loadFormulaFromDemo("demos/ascii", "demo2ConeArea.am");
        mjView1.displayMath(FormulaSyntax.ASCII_MATHML, formula1);

        String formula2 = mjView2.loadFormulaFromDemo("demos/ascii", "demo3ConeAreaLine1.am");
        String formula3 = mjView2.loadFormulaFromDemo("demos/ascii", "demo3ConeAreaLine2.am");
        mjView2.displayMath(FormulaSyntax.ASCII_MATHML, formula2, 0);
        mjView2.displayMath(FormulaSyntax.ASCII_MATHML, formula3, 1);


        et1 = (EditText) findViewById(R.id.tVInput1);
        et2 = (EditText) findViewById(R.id.tVInput2);

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // demoParseEval();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                //Log.i("bla", "bla");
            }

            @Override
            public void afterTextChanged(Editable s) {
                demoParseEval();
            }
        };

        et1.addTextChangedListener(textWatcher);
        et2.addTextChangedListener(textWatcher);


    }

    private void demoParseEval() {

        // Pour l'instant la formule est en dur, à paramétrer à partir de la base
        //  a_1 = pi*R*S
        String input = "pi*R*S";
       //  String inputWithParam = "a * x * x + b * x + c";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("pi", Math.PI);

        // Paramètre à récupérer dans les champs d'édition (à dynamiser)
        // COMA decimal separator i18N ?
        double value1 = 1;
        double value2 = 1;

        NumberFormat nf = NumberFormat.getInstance();
        try  {
//             value1 = Double.parseDouble(et1.getText().toString());
//             value2 = Double.parseDouble(et1.getText().toString());

        Number number = nf.parse(et1.getText().toString());
        value1 = number.doubleValue();

         number = nf.parse(et2.getText().toString());
         value2 = number.doubleValue();

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        param.put("R", value1);
        param.put("S", value2);

        String resultWithParam = "";
        String astDemoRes = "";
        String astDemoAM = "";
        try {

            astDemoAM = "a_1=" + ASTParser.parseAndReplaceWithParam(input, param);
            Double resultDouble = ASTParser.parseAndEvalWithParam(input, param);

            // Limiter le nombre de chiffres significatifs par défaut
            DecimalFormat df = new DecimalFormat("#.####");
            // Gérer l'unité
            resultWithParam = "a_1=" +  df.format(resultDouble) + "\\  m^2";

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        displayTestInDialog("Parse Eval demo 1", input + "\n" + astDemoRes + "\n" + inputWithParam + "\n" + resultWithParam + "\n");
//        formula = astDemoTex;
//        modeDemo = true;
        // MsgDialog.showDialog(this, "title", astDemoTex);
        mjView2.displayMath(FormulaSyntax.ASCII_MATHML, astDemoAM, 0);
        mjView2.displayMath(FormulaSyntax.ASCII_MATHML, resultWithParam, 1);



    }

    public void onRadioButtonClicked(View view) {
        // Changement de la formule en cas de changement de variable
        if (((RadioButton) view).isChecked()) {
            switch(view.getId()) {
                case R.id.rBtnVar1:
                    currentTargetVar = 1;
                    String formula1 = mjView1.loadFormulaFromDemo("demos/ascii", "demo2ConeArea.am");
                    mjView1.displayMath(FormulaSyntax.ASCII_MATHML, formula1);
                    break;
                case R.id.rBtnVar2:
                    currentTargetVar = 2;
                    String formula2 = mjView1.loadFormulaFromDemo("demos/ascii", "demo2ConeAreaAltR.am");
                    mjView1.displayMath(FormulaSyntax.ASCII_MATHML, formula2);
                    break;
                case R.id.rBtnVar3:
                    currentTargetVar = 3;
                    String formula3 = mjView1.loadFormulaFromDemo("demos/ascii", "demo2ConeAreaAltS.am");
                    mjView1.displayMath(FormulaSyntax.ASCII_MATHML, formula3);
                    break;
            }
        }

    }
}
