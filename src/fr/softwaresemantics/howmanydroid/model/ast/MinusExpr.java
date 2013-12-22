package fr.softwaresemantics.howmanydroid.model.ast;

public class MinusExpr extends BinExpr
{
	public MinusExpr(Expr left, Expr right)
	{
		super(left, right);
	}
	
	public void accept(TreeWalker walker)
	{
		walker.visit(this);
	}
	public Object accept(Visitor eval)
	{
		return eval.visit(this);
	}
}
