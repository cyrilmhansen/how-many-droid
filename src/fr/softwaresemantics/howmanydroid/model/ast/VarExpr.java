package fr.softwaresemantics.howmanydroid.model.ast;

public class VarExpr extends Expr
{
	public final String name;
	
	public VarExpr(String name)
	{
		super();
		this.name = name;
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
