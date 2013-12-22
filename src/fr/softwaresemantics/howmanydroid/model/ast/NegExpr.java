package fr.softwaresemantics.howmanydroid.model.ast;

public class NegExpr extends Expr
{
	public final Expr e;
	
	public NegExpr(Expr expr)
	{
		super();
		this.e = expr;
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
