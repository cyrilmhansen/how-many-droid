package fr.softwaresemantics.howmanydroid.model.ast;

public class PlusExpr extends BinExpr
{
	public PlusExpr(Expr left, Expr right)
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
