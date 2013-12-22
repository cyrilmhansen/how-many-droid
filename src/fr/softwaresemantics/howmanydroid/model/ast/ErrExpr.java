package fr.softwaresemantics.howmanydroid.model.ast;

public class ErrExpr extends Expr
{
        public String errMsg; 
	public ErrExpr()
	{
		super();
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
