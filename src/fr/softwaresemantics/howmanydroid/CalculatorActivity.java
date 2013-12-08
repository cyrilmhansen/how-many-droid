package fr.softwaresemantics.howmanydroid;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorActivity extends Activity implements View.OnClickListener {

	String formula;

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
	}
	public void onClick(View v) {
		WebView w = (WebView) findViewById(R.id.mathjaxview);
		
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
                
		
		if (v == findViewById(R.id.buttonEQ))
                {
                    Calculable calc;
                    try {
                        calc = new ExpressionBuilder(formula).build();
                        w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
		          +formula
				  +"\\\\]<br/> \\\\["+"="+Double.toString(calc.calculate())+"\\\\]';");
                        w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");	
                             
                    } catch (UnknownFunctionException ex) {
                        Logger.getLogger(CalculatorActivity.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnparsableExpressionException ex) {
                        Logger.getLogger(CalculatorActivity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		
		}
                else
                {
		w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
		          +formula
				  +"\\\\]';");
		w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		
		WebView w = (WebView) findViewById(R.id.mathjaxview);
		w.getSettings().setJavaScriptEnabled(true);
		
		w.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
		                      +"MathJax.Hub.Config({ " 
							  	+"showMathMenu: false, "
							  	+"jax: ['input/TeX','output/HTML-CSS'], "
							  	+"extensions: ['tex2jax.js'], " 
							  	+"TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
							  	  +"'noErrors.js','noUndefined.js'] } "
							  +"});</script>"
		                      +"<script type='text/javascript' "
							  +"src='file:///android_asset/MathJax/MathJax.js'"
							  +"></script><span id='math'></span>","text/html","utf-8","");
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
               
                
                
		return true;
	}

}
