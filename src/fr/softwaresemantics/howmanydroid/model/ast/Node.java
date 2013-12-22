package fr.softwaresemantics.howmanydroid.model.ast;

import beaver.Symbol;

public abstract class Node extends Symbol
{
	public abstract void accept(TreeWalker walker); 
	public abstract Object accept(Visitor eval);
}
