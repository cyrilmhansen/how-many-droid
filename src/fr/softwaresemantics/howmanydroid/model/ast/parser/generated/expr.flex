package fr.softwaresemantics.howmanydroid.model.ast.parser.generated;
import beaver.Symbol;
import beaver.Scanner;

import fr.softwaresemantics.howmanydroid.model.ast.parser.generated.ExpressionParser.Terminals;

%%
%public
%class ExpressionScanner
%extends Scanner
%function nextToken
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
	return newToken(Terminals.EOF, "end-of-file");
%eofval}
%unicode
%line
%column
%{
	private Symbol newToken(short id)
	{
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength());
	}

	private Symbol newToken(short id, Object value)
	{
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
	}
%}
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

Number = [:digit:] [:digit:]* (\. [:digit:] [:digit:]* )?

%%

<YYINITIAL> {
	{Number}    { return newToken(Terminals.NUMBER, yytext()); }

	"("         { return newToken(Terminals.LPAREN, yytext()); }
	")"         { return newToken(Terminals.RPAREN, yytext()); }
	"*"         { return newToken(Terminals.MULT,   yytext()); }
	"/"         { return newToken(Terminals.DIV,    yytext()); }
	"+"         { return newToken(Terminals.PLUS,   yytext()); }
	"-"         { return newToken(Terminals.MINUS,  yytext()); }
	"^"         { return newToken(Terminals.EXPON,  yytext()); }
	","			{ return newToken(Terminals.EXPON,  yytext()); }
	[a-zA-Z][a-zA-Z0-9]* { return newToken(Terminals.ALPHA09NAME,  yytext());}
}

.|\n            { throw new Scanner.Exception("unexpected character '" + yytext() + "'"); }
