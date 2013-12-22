package fr.softwaresemantics.howmanydroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import org.jscience.mathematics.number.Real;

import java.io.StringReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import bsh.Interpreter;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import fr.softwaresemantics.howmanydroid.model.ast.parser.ASTParser;
import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;
import fr.softwaresemantics.howmanydroid.util.AssetsUtil;
import fr.softwaresemantics.howmanydroid.util.MsgDialog;

public class CalculatorActivity extends Activity implements View.OnClickListener {

    String formula;
    boolean modeDemo;

    public CalculatorActivity() {
        this.formula = new String();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        WebView w = (WebView) findViewById(R.id.mathjaxview);
        w.getSettings().setJavaScriptEnabled(true);

        initMathjax(w);
        initButtonListener(w);

        modeDemo = true;
        demoParseEval();
    }

    private void demoParseEval() {
        String input= " 2*3.14159*9^2+6/(3+4)";
        String inputWithParam = "a * x * x + b * x + c";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("a", 8.0d);
        param.put("b", 5.0d);
        param.put("c", 3.0d);
        param.put("x", 4.0d);
        String resultWithParam = "";
        String astDemoRes = "";
        String astDemoTex = "";
        try {
            astDemoRes = ASTParser.demoParseAndEval(input).toString();
            astDemoTex = ASTParser.parseAndGenTex(input);
            resultWithParam = ASTParser.demoParseAndEvalWithParam(inputWithParam, param).toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        displayTestInDialog("Parse Eval demo 1", input + "\n" + astDemoRes + "\n" + inputWithParam + "\n" + resultWithParam + "\n");
        formula = astDemoTex;
        WebView w = (WebView) findViewById(R.id.mathjaxview);
        modeDemo = true;
        updateFormulaAndTypeset(w, FormulaSyntax.TEX, formula);
    }

    private void demoBeanshell() {
        Interpreter i = new Interpreter();  // Construct an interpreter

        String bsScript = "";
        double result = Double.NaN;
        try {
            bsScript = AssetsUtil.loadUTF8AssetAsString(this, "demos/beanshell", "arithmetic.bsh");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //  result = a * x * x + b * x + c
        try {
            // Set variables
            i.set("a", 8.0d);
            i.set("b", 5.0d);
            i.set("c", 3.0d);
            i.set("x", 4.0d);

            i.eval(bsScript);

            result = (Double) i.get("result");
        } catch (Throwable err) {
            err.printStackTrace();
        }

        String display = "result = a * x * x + b * x + c\n"
                + "a=8.0, b=5.0, c=3.0, x=4.0\n"
                + "result=" + result;

        displayTestInDialog("Beanshell Demo 1", display);
    }

    private void displayTestInDialog(String title, String msg) {
        MsgDialog.showDialog(this, title, msg);
    }

    private void demoJscience() {
        try {
            Interpreter i = new Interpreter();
            String bsScript = "";
            String result = "";

            bsScript = AssetsUtil.loadUTF8AssetAsString(this, "demos/jscience", "demo1.bsh");
            i.eval(bsScript);
            result = String.valueOf((String) i.get("result"));
            displayTestInDialog("JScience script demo 1", bsScript + "\n->\n" + result);

            bsScript = AssetsUtil.loadUTF8AssetAsString(this, "demos/jscience", "demojs2.bsh");
            i.eval(bsScript);
            Real squareRootOfLVPO = (Real) i.get("squareRootOfLVPO");
            displayTestInDialog("JScience script demo 2", bsScript + "\n->\n" + squareRootOfLVPO.toText());

            bsScript = AssetsUtil.loadUTF8AssetAsString(this, "demos/jscience", "demojs3.bsh");
            StringReader reader = new StringReader(bsScript);
            i.eval(reader);
            result = (String) i.get("result");
            displayTestInDialog("JScience script demo 3", bsScript + "\n->\n" + result);

            bsScript = AssetsUtil.loadUTF8AssetAsString(this, "demos/jscience", "demojs4.bsh");
            reader = new StringReader(bsScript);
            i.eval(reader);
            result = (String) i.get("result");
            displayTestInDialog("JScience script demo 4", bsScript + "\n->\n" + result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.demo1:
                demoBeanshell();
                return true;
            case R.id.demo2:
                demoJscience();
                return true;
            case R.id.demo3:
                demoMathjaxAM();
                return true;
            case R.id.demo4:
                demoMathjaxTEX();
                return true;
            case R.id.demo5:
                demoMathjaxMM();
                return true;
            case R.id.demo6:
                demoParseEval();
                return true;
            case R.id.help:
                //  showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void demoMathjaxAM() {
        WebView w = (WebView) findViewById(R.id.mathjaxview);
        modeDemo = true;

        // formula = "d/dxf(x)=lim_(h->0)(f(x+h)-f(x))/h";
        loadFormulaFromDemo("demos/ascii", "demo1.am");

        updateFormulaAndTypeset(w, FormulaSyntax.ASCII_MATHML, formula);
    }

    private void loadFormulaFromDemo(String path, String file) {
        try {
            formula = AssetsUtil.loadUTF8AssetAsString(this, path, file);
            formula = formula.replaceAll("\r|\n", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void demoMathjaxTEX() {
        WebView w = (WebView) findViewById(R.id.mathjaxview);
        modeDemo = true;

        loadFormulaFromDemo("demos/tex", "demo1.tex");
        updateFormulaAndTypeset(w, FormulaSyntax.TEX, formula);
    }

    public void demoMathjaxMM() {
        WebView w = (WebView) findViewById(R.id.mathjaxview);
        modeDemo = true;
        loadFormulaFromDemo("demos/mathml", "demo1.xml");

        updateFormulaAndTypeset(w, FormulaSyntax.DISPLAY_MATHML, formula);
    }


    public void onClick(View v) {
        WebView w = (WebView) findViewById(R.id.mathjaxview);
        if (modeDemo) {
            modeDemo = false;
            formula = "";
        }

        if (v == findViewById(R.id.button0)) {
            formula += "0";
        } else if (v == findViewById(R.id.button1)) {
            formula += "1";
        } else if (v == findViewById(R.id.button2)) {
            formula += "2";
        } else if (v == findViewById(R.id.button3)) {
            formula += "3";
        } else if (v == findViewById(R.id.button4)) {
            formula += "4";
        } else if (v == findViewById(R.id.button5)) {
            formula += "5";
        } else if (v == findViewById(R.id.button6)) {
            formula += "6";
        } else if (v == findViewById(R.id.button7)) {
            formula += "7";
        } else if (v == findViewById(R.id.button8)) {
            formula += "8";
        } else if (v == findViewById(R.id.button9)) {
            formula += "9";
        } else if (v == findViewById(R.id.buttonEQ)) {

        } else if (v == findViewById(R.id.buttonMUL)) {
            formula += "*";
        } else if (v == findViewById(R.id.buttonDIV)) {
            formula += "/";
        } else if (v == findViewById(R.id.buttonSUB)) {
            formula += "-";
        } else if (v == findViewById(R.id.buttonADD)) {
            formula += "+";
        } else if (v == findViewById(R.id.buttonLPar)) {
            formula += "(";
        } else if (v == findViewById(R.id.buttonRPar)) {
            formula += ")";
        } else if (v == findViewById(R.id.buttonSIN)) {
            formula += "sin(";
        } else if (v == findViewById(R.id.buttonTAN)) {
            formula += "tan(";
        } else if (v == findViewById(R.id.buttonCOS)) {
            formula += "cos(";
        } else if (v == findViewById(R.id.buttonLOG)) {
            formula += "log(";
        } else if (v == findViewById(R.id.buttonDEL)) {
            if (formula.length() > 0)
                formula = formula.substring(0, formula.length() - 1);
        }

        if (v == findViewById(R.id.buttonEQ)) {
            calculateFormula(w);
        } else {
            updateFormulaAndTypeset(w, FormulaSyntax.ASCII_MATHML, formula);
        }
    }

    private void calculateFormula(WebView w) {
        Calculable calc;
        try {
            calc = new ExpressionBuilder(formula).build();
            displayMath(w, FormulaSyntax.ASCII_MATHML, "=" + Double.toString(calc.calculate()));

        } catch (UnknownFunctionException ex) {
            Logger.getLogger(CalculatorActivity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnparsableExpressionException ex) {
            Logger.getLogger(CalculatorActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayMath(WebView w, FormulaSyntax syntax, String formula) {
        updateFormulaAndTypeset(w, syntax, formula);
    }

    private void updateFormulaAndTypeset(WebView w, FormulaSyntax syntax, String formula) {
        // clearAllMath(w);
        updateFormula(w, syntax, formula);
        // typeset(w, syntax);
    }

    private void updateFormula(WebView w, FormulaSyntax syntax, String formula) {
        switch (syntax) {
            case ASCII_MATHML:
                w.loadUrl("javascript:UpdateMathAscii('" + formula + "');");
                break;
            case TEX:
                // Attention les formules Tex doivent quoter les _\
                String quoted = formula.replace("\\", "\\\\");
                w.loadUrl("javascript:UpdateMathTex('" + quoted + "');");
                break;
            case DISPLAY_MATHML:
                String quoted2 = formula.replace("\\", "\\\\");
                w.loadUrl("javascript:UpdateMathML('" + quoted2 + "');");
                break;
        }
    }

    private void clearFormula(WebView w, FormulaSyntax syntax) {
        switch (syntax) {
            case ASCII_MATHML:
                w.loadUrl("javascript:UpdateMathAscii('');");
            case DISPLAY_MATHML:
                w.loadUrl("javascript:UpdateMathML('');");
                break;
            case TEX:
                w.loadUrl("javascript:UpdateMathTex('');");
                break;
        }
    }

    private void clearAllMath(WebView w) {
        for (FormulaSyntax syntax : FormulaSyntax.values()) {
            clearFormula(w, syntax);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator, menu);
        return true;
    }

    private void initMathjax(WebView w) {
        w.loadUrl("file:///android_asset/baseDocMj3.html");
    }

    private void initButtonListener(WebView w) {
        Button b = (Button) findViewById(R.id.button0);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button3);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button4);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button5);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button6);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button7);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button8);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.button9);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonADD);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonDIV);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonMUL);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonSUB);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonEQ);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonLPar);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonRPar);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonTAN);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonCOS);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonSIN);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonLOG);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.buttonDEL);
        b.setOnClickListener(this);
    }
}
