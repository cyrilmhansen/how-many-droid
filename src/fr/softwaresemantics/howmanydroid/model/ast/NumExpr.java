package fr.softwaresemantics.howmanydroid.model.ast;

public class NumExpr extends Expr
{
	public final double value;
	
	public NumExpr(String value)
	{
		super();
		this.value = Double.parseDouble(value);
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
