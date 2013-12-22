/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.softwaresemantics.howmanydroid.model.ast.parser;

/**
 *
 * @author chris
 */
import fr.softwaresemantics.howmanydroid.model.ast.*;

import java.lang.Math;

import beaver.Parser;
import fr.softwaresemantics.howmanydroid.model.ast.parser.generated.ExpressionParser;
import fr.softwaresemantics.howmanydroid.model.ast.parser.generated.ExpressionScanner;

import java.io.IOException;
import java.io.StringReader;
        
public class ASTParser {

    static class EvalVisitor extends Visitor
{
	public Object visit(ErrExpr expr)
	{
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
		return new Double(0);//val from hashmap here
	}
	public Object visit(FuncExpr expr)
	{
		for (Expr e : expr.parameters)
			e.accept(this);
		//for (int i=0;i<expr.parameters.size();i++)
		//	expr.parameters[i].eval(this);
		return new Double(0);//val from hashmap here
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
		return new String("\frac{"+(String)expr.l.accept(this)+"}{"+(String)expr.r.accept(this)+"}");
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

    public static String parseAndGenTex(String str) throws IOException, Parser.Exception {
        StringBuffer outputBuf = new StringBuffer();
        ExpressionParser parser = new ExpressionParser();
        ExpressionScanner input = new ExpressionScanner(new StringReader(str));
        Expr expr = (Expr) parser.parse(input);

        LaTEXVisitor latex = new LaTEXVisitor();
        return (String) expr.accept(latex);
    }

}
