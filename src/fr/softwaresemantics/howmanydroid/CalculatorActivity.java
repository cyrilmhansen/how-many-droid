package fr.softwaresemantics.howmanydroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import org.jscience.physics.amount.Amount;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.Mass;
import javax.measure.unit.NonSI;

import bsh.Interpreter;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;

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
                String formula= "";
                //Formulae F = new Formulae();
                //F.addInteger(1);
                /*F.addOperator("+");
                F.addInteger(1);
                Log.d(formula, F.printStack());
                 F.addOperator("*");
                F.addInteger(2);
                Log.d(formula, F.printStack());*/


        WebView w = (WebView) findViewById(R.id.mathjaxview);
        w.getSettings().setJavaScriptEnabled(true);

        initMathjax(w);

        modeDemo = true;


        demoBeanshell();

	}

    private void demoBeanshell() {
        Interpreter i = new Interpreter();  // Construct an interpreter
        String result = "aaa";
        try  {
        i.set("foo", 5);                    // Set variables
        i.set("date", new Date() );
        Date date = (Date)i.get("date");    // retrieve a variable
            i.eval("bar = foo*10");
            System.out.println( i.get("bar") );
            result= String.valueOf((Integer) i.get("bar"));
        } catch (Throwable err) {
            err.printStackTrace();
        }


        //result = demoJscience(i, result);


        displayTestInDialog(result);


// Eval a statement and get the result
    }

    private void displayTestInDialog(String result) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Demo results");
        alertDialog.setMessage(result);
//        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Add your code for the button here.
//            }
        //   });
// Set the Icon for the Dialog
        // alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }

    private void demoJscience() {

        Interpreter i = new Interpreter();  // Construct an interpreter

        //  import javax.measure.quantity.*;
        //  import javax.measure.unit.*;
        // import static org.jscience.economics.money.Currency.*;
        // import static javax.measure.unit.NonSI.*;
        Amount<Mass> m0 = Amount.valueOf(100, NonSI.POUND );
        Amount<Mass> m1 = m0.times(33).divide(2);
        Amount<ElectricCurrent> m2 = Amount.valueOf("234 mA").to(
                javax.measure.unit.SI.MICRO(javax.measure.unit.SI.AMPERE));
        String result = (" m0 = " + m0);
        result +=  ("\n m1 = " + m1);
        result += ("\n m2 = " + m2);

        try  {
            i.eval("org.jscience.physics.amount.Amount m0 = org.jscience.physics.amount.Amount.valueOf(100, javax.measure.unit.NonSI.POUND );");
            i.eval("res=m0.toString();");
            result= String.valueOf((String) i.get("res"));
        } catch (Throwable err) {
            err.printStackTrace();
        }

        displayTestInDialog(result);
    }


    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
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
        formula = "d/dxf(x)=lim_(h->0)(f(x+h)-f(x))/h";
        updateFormulaAndTypeset(w, FormulaSyntax.ASCII_MATHML, formula);
    }

    public void demoMathjaxTEX() {
        WebView w = (WebView) findViewById(R.id.mathjaxview);
        modeDemo = true;
        formula = "\\begin{aligned}" +
                "\\nabla \\times \\vec{\\mathbf{B}} -\\, \\frac1c\\, \\frac{\\partial\\vec{\\mathbf{E}}}{\\partial t} & = \\frac{4\\pi}{c}\\vec{\\mathbf{j}} \\\\   \\nabla \\cdot \\vec{\\mathbf{E}} & = 4 \\pi \\rho \\\\" +
                "\\nabla \\times \\vec{\\mathbf{E}}\\, +\\, \\frac1c\\, \\frac{\\partial\\vec{\\mathbf{B}}}{\\partial t} & = \\vec{\\mathbf{0}} \\\\" +
                "\\nabla \\cdot \\vec{\\mathbf{B}} & = 0 \\end{aligned}";
        updateFormulaAndTypeset(w, FormulaSyntax.TEX, formula);
    }

    public void demoMathjaxMM() {
        WebView w = (WebView) findViewById(R.id.mathjaxview);
        modeDemo = true;
        formula =      "<math  xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">"+
                 "  <msup>" +
                "    <mrow>" +
                "      <mo>(</mo>" +
                "      <munderover>" +
                "        <mo>&#x2211;<!-- ∑ --></mo>" +
                "        <mrow class=\"MJX-TeXAtom-ORD\">" +
                "          <mi>k</mi>" +
                "          <mo>=</mo>" +
                "          <mn>1</mn>" +
                "        </mrow>" +
                "        <mi>n</mi>" +
                "      </munderover>" +
                "      <msub>" +
                "        <mi>a</mi>" +
                "        <mi>k</mi>" +
                "      </msub>" +
                "      <msub>" +
                "        <mi>b</mi>" +
                "        <mi>k</mi>" +
                "      </msub>" +
                "      <mo>)</mo>" +
                "    </mrow>" +
                "    <mn>2</mn>" +
                "  </msup>" +
                "  <mo>&#x2264;<!-- ≤ --></mo>" +
                "  <mrow>" +
                "    <mo>(</mo>" +
                "    <munderover>" +
                "      <mo>&#x2211;<!-- ∑ --></mo>" +
                "      <mrow class=\"MJX-TeXAtom-ORD\">" +
                "        <mi>k</mi>" +
                "        <mo>=</mo>" +
                "        <mn>1</mn>" +
                "      </mrow>" +
                "      <mi>n</mi>" +
                "    </munderover>" +
                "    <msubsup>" +
                "      <mi>a</mi>" +
                "      <mi>k</mi>" +
                "      <mn>2</mn>" +
                "    </msubsup>" +
                "    <mo>)</mo>" +
                "  </mrow>" +
                "  <mrow>" +
                "    <mo>(</mo>" +
                "    <munderover>" +
                "      <mo>&#x2211;<!-- ∑ --></mo>" +
                "      <mrow class=\"MJX-TeXAtom-ORD\">" +
                "        <mi>k</mi>" +
                "        <mo>=</mo>" +
                "        <mn>1</mn>" +
                "      </mrow>" +
                "      <mi>n</mi>" +
                "    </munderover>" +
                "    <msubsup>" +
                "      <mi>b</mi>" +
                "      <mi>k</mi>" +
                "      <mn>2</mn>" +
                "    </msubsup>" +
                "    <mo>)</mo>" +
                "  </mrow></math>"; // </math>
        updateFormulaAndTypeset(w, FormulaSyntax.DISPLAY_MATHML, formula);
    }


    public void onClick(View v) {
		WebView w = (WebView) findViewById(R.id.mathjaxview);
    if (modeDemo) {
        modeDemo = false;
        formula = "";
    }


		if (v == findViewById(R.id.button0))
		{
              formula+="0";
		}
                else if (v == findViewById(R.id.button1))
                {
			formula+="1";
		}
                else if (v == findViewById(R.id.button2))
                {
			formula+="2";
		}
                else if (v == findViewById(R.id.button3))
                {
			formula+="3";
		}
                else if (v == findViewById(R.id.button4))
                {
			formula+="4";
		}
                else if (v == findViewById(R.id.button5))
                {
			formula+="5";
		}else if (v == findViewById(R.id.button6))
                {
			formula+="6";
		}
                else if (v == findViewById(R.id.button7))
                {
			formula+="7";
		}
                else if (v == findViewById(R.id.button8))
                {
			formula+="8";
		}
                else if (v == findViewById(R.id.button9))
                {
			formula+="9";
		}
                else if (v == findViewById(R.id.buttonEQ))
                {
			
		}
                else if (v == findViewById(R.id.buttonMUL))
                {
			formula+="*";
		}
                else if (v == findViewById(R.id.buttonDIV))
                {
			formula+="/";
		}
                else if (v == findViewById(R.id.buttonSUB))
                {
			formula+="-";
		}
                else if (v == findViewById(R.id.buttonADD))
                {
			formula+="+";
		}
                else if (v == findViewById(R.id.buttonLPar))
                {
			formula+="(";
		}
                else if (v == findViewById(R.id.buttonRPar))
                {
			formula+=")";
		}
                else if (v == findViewById(R.id.buttonSIN))
                {
			formula+="sin(";
		}
                else if (v == findViewById(R.id.buttonTAN))
                {
			formula+="tan(";
		}
                else if (v == findViewById(R.id.buttonCOS))
                {
			formula+="cos(";
		}
                else if (v == findViewById(R.id.buttonLOG))
                {
			formula+="log(";
		}
                else if (v == findViewById(R.id.buttonDEL))
                {
                    if (formula.length()>0)
			formula = formula.substring(0,formula.length()-1);
		}

        if (v == findViewById(R.id.buttonEQ)) {
            calculateFormula(w);
        } else {
            updateFormulaAndTypeset(w, FormulaSyntax.ASCII_MATHML, formula);
        }
    }

    private void calculateFormula(WebView w ) {
        Calculable calc;

        try {
            calc = new ExpressionBuilder(formula).build();
            displayMath(w,FormulaSyntax.ASCII_MATHML, "="+Double.toString(calc.calculate()));

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
        clearAllMath(w);
        updateFormula(w, syntax, formula);
       // typeset(w, syntax);
    }

    private void updateFormula(WebView w, FormulaSyntax syntax, String formula) {
        switch(syntax) {
            case ASCII_MATHML:
                w.loadUrl("javascript:UpdateMathAscii('"+formula+"');");
                break;

            case TEX:
                // Attention les formules Tex doivent quoter les _\
                String quoted = formula.replace("\\","\\\\");
                w.loadUrl("javascript:UpdateMathTex('" + quoted + "');");
                break;

            case DISPLAY_MATHML:

                w.loadUrl("javascript:UpdateMathML('" + formula + "');");
                break;
        }
    }

    private void clearFormula(WebView w, FormulaSyntax syntax) {
        switch(syntax) {
            case ASCII_MATHML:
                w.loadUrl("javascript:UpdateMathAscii('');");
            case DISPLAY_MATHML:
                w.loadUrl("javascript:UpdateMathML('');");
       // w.loadUrl("javascript:document.getElementById('" +syntax.getId()+"').innerHTML='';");
                break;
            case TEX:
        w.loadUrl("javascript:UpdateMathTex('');");
                break;
        }
    }

//    private void typeset(WebView w, FormulaSyntax syntax) {
//
//        switch(syntax) {
//            case ASCII_MATHML:
//            case DISPLAY_MATHML:
//                 //  w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
//
//        //loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub,\"" +syntax.getId()+"\"]);");
//
//            default:
//                // nothing
//        }
//        //MathJax.Hub.Queue(["Typeset",MathJax.Hub,"MathExample"]);
//    }

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
