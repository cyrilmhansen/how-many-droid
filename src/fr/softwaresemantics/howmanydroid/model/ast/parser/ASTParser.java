/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.softwaresemantics.howmanydroid.model.ast.parser;

/**
 *
 * @author christophe goessens
 */

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import beaver.Parser;
import fr.softwaresemantics.howmanydroid.model.ast.DivExpr;
import fr.softwaresemantics.howmanydroid.model.ast.ErrExpr;
import fr.softwaresemantics.howmanydroid.model.ast.ExponExpr;
import fr.softwaresemantics.howmanydroid.model.ast.Expr;
import fr.softwaresemantics.howmanydroid.model.ast.FuncExpr;
import fr.softwaresemantics.howmanydroid.model.ast.MinusExpr;
import fr.softwaresemantics.howmanydroid.model.ast.MultExpr;
import fr.softwaresemantics.howmanydroid.model.ast.NegExpr;
import fr.softwaresemantics.howmanydroid.model.ast.NumExpr;
import fr.softwaresemantics.howmanydroid.model.ast.PlusExpr;
import fr.softwaresemantics.howmanydroid.model.ast.VarExpr;
import fr.softwaresemantics.howmanydroid.model.ast.Visitor;
import fr.softwaresemantics.howmanydroid.model.ast.parser.generated.ExpressionParser;
import fr.softwaresemantics.howmanydroid.model.ast.parser.generated.ExpressionScanner;

public class ASTParser {

    public class ParserException extends Exception {
        public ParserException() {
        }

        public ParserException(String msg) {
            super(msg);
        }

        public ParserException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public ParserException(Throwable cause) {
            super(cause);
        }
    }

    public class ParserFunctionUndefinedException extends ParserException {
        public ParserFunctionUndefinedException() {
        }

        public ParserFunctionUndefinedException(String msg) {
            super(msg);
        }

        public ParserFunctionUndefinedException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public ParserFunctionUndefinedException(Throwable cause) {
            super(cause);
        }
    }

    public class ParserFunctionParameterException extends ParserException {
        public ParserFunctionParameterException() {
        }

        public ParserFunctionParameterException(String msg) {
            super(msg);
        }

        public ParserFunctionParameterException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public ParserFunctionParameterException(Throwable cause) {
            super(cause);
        }
    }

    static class EvalVisitor extends Visitor
{
    private Map<String, Object> parameters;

    public EvalVisitor() {
        parameters = new HashMap<String, Object>();
    }

    public EvalVisitor(HashMap<String, Object> params) {
        parameters = params;
    }

    public void setParameter(String key, Object val) {
        parameters.put(key, val);
    }

    public Object getParameter(String key, Object val) {
        return parameters.get(key);
    }

    public Object visit(ErrExpr expr) {
		// leaf
		return new Double(0);
	}

        public Object visit(NumExpr expr)
	{
		// leaf
		return expr.value;
	}

	public Object visit(NegExpr expr)
	{
		return new Double(-1*(Double)expr.e.accept(this));
	}
	
	public Object visit(MultExpr expr)
	{
		return new Double((Double)expr.l.accept(this)*(Double)expr.r.accept(this));
	}
	
	public Object visit(DivExpr expr)
	{
		return new Double((Double)expr.l.accept(this)/(Double)expr.r.accept(this));
	}
	
	public Object visit(PlusExpr expr)
	{
		return new Double((Double)expr.l.accept(this)+(Double)expr.r.accept(this));
	}
	
	public Object visit(MinusExpr expr)
	{
		return new Double((Double)expr.l.accept(this)-(Double)expr.r.accept(this));
	}
	public Object visit(ExponExpr expr)
	{
		return new Double(Math.pow((Double)expr.l.accept(this),(Double)expr.r.accept(this)));
	}
	public Object visit(VarExpr expr)
	{
        if (parameters.containsKey(expr.name))
            return parameters.get(expr.name);
        else
            return new Double(0);//Should throw exception or return invalid expression object
    }

    public Object visit(FuncExpr expr)
	{
        /*Quick and dirty simple math functions*/
        if (expr.name.toLowerCase().equals("tan")) {
            if (expr.parameters.size() != 1)
                //throw
                return new Double(0);
            else
                return Math.tan((Double) (expr.parameters.get(0)).accept(this));
        }
        if (expr.name.toLowerCase().equals("cos")) {
            if (expr.parameters.size() != 1)
                //throw
                return new Double(0);
            else
                return Math.cos((Double) (expr.parameters.get(0)).accept(this));
        }
        if (expr.name.toLowerCase().equals("sin")) {
            if (expr.parameters.size() != 1)
                //throw
                return new Double(0);
            else
                return Math.sin((Double) (expr.parameters.get(0)).accept(this));
        } else {
            //compute params
            //todo store params val
            for (Expr e : expr.parameters)
                e.accept(this);

            //todo execute function
            return new Double(0);
        }

    }
}
    static class LaTEXVisitor extends Visitor
{
	public Object visit(ErrExpr expr)
	{
		// leaf
		return new String("Err");
	}

        public Object visit(NumExpr expr)
	{
		// leaf
		return Double.toString(expr.value);
	}

	public Object visit(NegExpr expr)
	{
		return new String("-" + (String)expr.e.accept(this));
	}
	
	public Object visit(MultExpr expr)
	{
		return new String((String)expr.l.accept(this)+"*"+(String)expr.r.accept(this));
	}
	
	public Object visit(DivExpr expr)
	{
        return new String("\\frac{" + (String) expr.l.accept(this) + "}{" + (String) expr.r.accept(this) + "}");
    }

    public Object visit(PlusExpr expr)
	{
		return new String((String)expr.l.accept(this)+"+"+(String)expr.r.accept(this));
	}
	
	public Object visit(MinusExpr expr)
	{
		return new String((String)expr.l.accept(this)+"-"+(String)expr.r.accept(this));
	}
	public Object visit(ExponExpr expr)
	{
		return new String((String)expr.l.accept(this)+"^"+(String)expr.r.accept(this));
	}
	public Object visit(VarExpr expr)
	{
		return new String(expr.name);//val from hashmap here
	}
	public Object visit(FuncExpr expr)
	{       String res = new String(expr.name+"(");
		for (Expr e : expr.parameters)
			res += e.accept(this)+",";
                res= res.substring(0, res.length()-1);
		//for (int i=0;i<expr.parameters.size();i++)
		//	expr.parameters[i].eval(this);
		return res;//val from hashmap here
	}
}


    static class InjectVarsAMVisitor extends Visitor
    {

        private Map<String, Object> parameters;

        public InjectVarsAMVisitor() {
            parameters = new HashMap<String, Object>();
        }

        public InjectVarsAMVisitor(HashMap<String, Object> params) {
            parameters = params;
        }

        public Object visit(ErrExpr expr)
        {
            // leaf
            return new String("Err");
        }

        public Object visit(NumExpr expr)
        {
            // leaf
            return Double.toString(expr.value);
        }

        public Object visit(NegExpr expr)
        {
            return new String("-" + (String)expr.e.accept(this));
        }

        public Object visit(MultExpr expr)
        {
            return new String((String)expr.l.accept(this)+"*"+(String)expr.r.accept(this));
        }

        public Object visit(DivExpr expr)
        {
            return new String( (String) expr.l.accept(this) + "/ (" + (String) expr.r.accept(this) + ")");
        }

        public Object visit(PlusExpr expr)
        {
            return new String((String)expr.l.accept(this)+"+"+(String)expr.r.accept(this));
        }

        public Object visit(MinusExpr expr)
        {
            return new String((String)expr.l.accept(this)+"-"+(String)expr.r.accept(this));
        }
        public Object visit(ExponExpr expr)
        {
            return new String((String)expr.l.accept(this)+"^"+(String)expr.r.accept(this));
        }
        public Object visit(VarExpr expr)
        {
            if (parameters.containsKey(expr.name)) {
                DecimalFormat df = new DecimalFormat("#.####");
                return df.format(((Number) parameters.get(expr.name)).doubleValue());
            }
            else
                return "0";//Should throw exception or return invalid expression object
        }
        public Object visit(FuncExpr expr)
        {       String res = new String(expr.name+"(");
            for (Expr e : expr.parameters)
                res += e.accept(this)+",";
            res= res.substring(0, res.length()-1);
            //for (int i=0;i<expr.parameters.size();i++)
            //	expr.parameters[i].eval(this);
            return res;//val from hashmap here
        }
    }
    
    
    public static void main(String[] args) throws Parser.Exception, IOException {
        String input = "2*3.14159*7";
        if (args.length == 0) {
            System.err.println("Passer l'expression à parser en paramètre");
        } else{
            input = args[0];
        }

        StringBuffer outputBuf = demoParseAndEval(input);
        System.err.println(outputBuf.toString());
    }

    public static StringBuffer demoParseAndEval(String str) throws IOException, Parser.Exception {
        StringBuffer outputBuf = new StringBuffer();
        ExpressionParser parser = new ExpressionParser();
        ExpressionScanner input = new ExpressionScanner(new StringReader(str));
        Expr expr = (Expr) parser.parse(input);
        EvalVisitor eval = new EvalVisitor();
        LaTEXVisitor latex = new LaTEXVisitor();
        outputBuf.append("rec= " + (Double)expr.accept(eval)+"\n");
        outputBuf.append("rec= " + (String) expr.accept(latex)+"\n");
        return outputBuf;
    }

       public static StringBuffer demoParseAndEvalWithParam(String str, HashMap<String, Object> param) throws IOException, Parser.Exception {
        StringBuffer outputBuf = new StringBuffer();
        ExpressionParser parser = new ExpressionParser();
        ExpressionScanner input = new ExpressionScanner(new StringReader(str));
        Expr expr = (Expr) parser.parse(input);
        EvalVisitor eval = new EvalVisitor(param);
        LaTEXVisitor latex = new LaTEXVisitor();
        outputBuf.append("rec= " + (Double) expr.accept(eval) + "\n");
        outputBuf.append("rec= " + (String) expr.accept(latex) + "\n");
        return outputBuf;
    }

    public static String parseAndReplaceWithParam(String str, HashMap<String, Object> param) throws IOException, Parser.Exception {
        StringBuffer outputBuf = new StringBuffer();
        ExpressionParser parser = new ExpressionParser();
        ExpressionScanner input = new ExpressionScanner(new StringReader(str));
        Expr expr = (Expr) parser.parse(input);
        InjectVarsAMVisitor inject = new InjectVarsAMVisitor(param);

        return (String) expr.accept(inject);
    }

    public static Double parseAndEvalWithParam(String str, HashMap<String, Object> param) throws IOException, Parser.Exception {
        StringBuffer outputBuf = new StringBuffer();
        ExpressionParser parser = new ExpressionParser();
        ExpressionScanner input = new ExpressionScanner(new StringReader(str));
        Expr expr = (Expr) parser.parse(input);
        EvalVisitor eval = new EvalVisitor(param);

        return (Double) expr.accept(eval);
    }

    public static String parseAndGenTex(String str) throws IOException, Parser.Exception {
        StringBuffer outputBuf = new StringBuffer();
        ExpressionParser parser = new ExpressionParser();
        ExpressionScanner input = new ExpressionScanner(new StringReader(str));
        Expr expr = (Expr) parser.parse(input);

        LaTEXVisitor latex = new LaTEXVisitor();
        return (String) expr.accept(latex);
    }

}
