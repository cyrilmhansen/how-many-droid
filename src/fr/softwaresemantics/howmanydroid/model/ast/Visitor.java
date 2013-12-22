package fr.softwaresemantics.howmanydroid.model.ast;
import java.lang.Math;
public abstract class Visitor
{
	public abstract Object visit(ErrExpr expr);
        public abstract Object visit(NumExpr expr);
	public abstract Object visit(NegExpr expr);
	public abstract Object visit(MultExpr expr);
	public abstract Object visit(DivExpr expr);
	public abstract Object visit(PlusExpr expr);
	public abstract Object visit(MinusExpr expr);
	public abstract Object visit(ExponExpr expr);
	public abstract Object visit(VarExpr expr);
	public abstract Object visit(FuncExpr expr);
}
