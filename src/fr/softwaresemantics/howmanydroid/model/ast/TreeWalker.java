package fr.softwaresemantics.howmanydroid.model.ast;

public class TreeWalker
{
	public void visit(ErrExpr expr)
	{
		// leaf
	}

    public void visit(NumExpr expr)
	{
		// leaf
	}

	public void visit(NegExpr expr)
	{
		expr.e.accept(this);
	}
	
	public void visit(MultExpr expr)
	{
		expr.l.accept(this);
		expr.r.accept(this);
	}
	
	public void visit(DivExpr expr)
	{
		expr.l.accept(this);
		expr.r.accept(this);
	}
	
	public void visit(PlusExpr expr)
	{
		expr.l.accept(this);
		expr.r.accept(this);
	}
	
	public void visit(MinusExpr expr)
	{
		expr.l.accept(this);
		expr.r.accept(this);
	}
	public void visit(ExponExpr expr)
	{
		expr.l.accept(this);
		expr.r.accept(this);
	}
	public void visit(VarExpr expr)
	{
		//expr.l.accept(this);
		//expr.r.accept(this);
	}
	public void visit(FuncExpr expr)
	{
		//expr.l.accept(this);
		//expr.r.accept(this);
	}
}
