package fr.softwaresemantics.howmanydroid.model.ast;
import java.util.ArrayList;
public class FuncExpr extends Expr
{
	public ArrayList<Expr> parameters;
	public String name;
	
	public FuncExpr()
	{
		super();
		parameters=new ArrayList<Expr>();
	}
	@Override
	public void accept(TreeWalker walker)
	{
		walker.visit(this);
	}
        @Override
	public Object accept(Visitor eval)
	{
		return eval.visit(this);
	}

   
}
