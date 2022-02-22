// Lexer.class
//
// Description of lexer for circuit description language.
//
// Ian Stark

import java_cup.runtime.Symbol;
import java.util.HashMap;
	//This is how we pass tokens to the parser

%%


						// Declarations for JFlex
%unicode 								// We wish to read text files
%class Lexer
%line
%column
%cup 	// Declare that we expect to use Java CUP

%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type) {   return new Symbol(type, yyline, yycolumn);  }
    private Symbol symbol(int type, Object value) {     return new Symbol(type, yyline, yycolumn, value);   }

%}



										// REGULAR EXPRESSION

whitespace = {LineTerminator} | [ \t]
LineTerminator = \r|\n|\r\n
EscapeCharacter = \\u|\\n|\\r|\\t|\\b|\\f|\\\"|\\'
InputCharacter = [^\r\n]

ID = [$_A-Za-z][$_A-Za-z0-9]*



/* Numbers */
digit = [0-9]
Integer = {digit}+
Real = ({Integer}(\.{digit}+))|((\+|-)?\.{digit}+)

/* Comments */
Comment = {Comment_SingleLine} | {Comment_MultiLine}
Comment_SingleLine = "#" {InputCharacter}* {LineTerminator}?
Comment_MultiLine = "#*" ([^#]|{LineTerminator})* "#"

/* STRING */
// String = \' [^\']* \' | \" [^\"]* \"


%state STRING1, STRING2

%%
										// Now for the actual tokens and assocated actions
<YYINITIAL> {
    /* PAROLE CHIAVE */
    "if" 		    {return symbol(sym.IF); }
    "then" 		    {return symbol(sym.THEN); }
    "else" 	    	{return symbol(sym.ELSE); }
    "real"      	{return symbol(sym.REAL); }
    "integer" 		{return symbol(sym.INTEGER); }
    "while" 	    {return symbol(sym.WHILE); }
    "main"          {return symbol(sym.MAIN); }
    "string"        {return symbol(sym.STRING); }
    "bool"          {return symbol(sym.BOOL); }
    "fun"           {return symbol(sym.FUN); }
    "end"           {return symbol(sym.END); }
    "do"            {return symbol(sym.LOOP); }
    "and"           {return symbol(sym.AND); }
    "or"            {return symbol(sym.OR); }
    "not"           {return symbol(sym.NOT); }
    "loop"          {return symbol(sym.LOOP); }
    "null"          {return symbol(sym.NULL); }
    "true"          {return symbol(sym.TRUE); }
    "false"         {return symbol(sym.FALSE); }
    "return"        {return symbol(sym.RETURN); }
    "out"           {return symbol(sym.OUT); }
    "var"           {return symbol(sym.VAR); }
    "@"             {return symbol(sym.OUTPAR); }

    /* OPERATORI RELAZIONI */
    "="             { return symbol(sym.EQ);}
    ">"             { return symbol(sym.GT);}
    "<"             { return symbol(sym.LT);}
    ":="            { return symbol(sym.ASSIGN); }
    "<>"            { return symbol(sym.NE); }
    "!="            { return symbol(sym.NE); }
    "<="            { return symbol(sym.LE); }
    ">="            { return symbol(sym.GE); }
    /* SEPARATORI */
    "("             { return symbol(sym.LPAR); }
    ")"             { return symbol(sym.RPAR); }
    ":"             { return symbol(sym.COLON); }
    ";"             { return symbol(sym.SEMI); }
    ","             { return symbol(sym.COMMA); }
    /* OPERATORS*/
    "+"             { return symbol(sym.PLUS); }
    "-"             { return symbol(sym.MINUS); }
    "/"             { return symbol(sym.DIV); }
    "*"             { return symbol(sym.TIMES); }
    "&"             { return symbol(sym.STR_CONCAT); }
    "div"           { return symbol(sym.DIVINT); }
    "^"             { return symbol(sym.POW); }
    /* WRITE AND READ*/
    "%"             { return symbol(sym.READ); }
    "?"             { return symbol(sym.WRITE); }
    "?."            { return symbol(sym.WRITELN); }
    "?,"            { return symbol(sym.WRITEB); }
    "?:"            { return symbol(sym.WRITET); }



    /* WHITESPACE */
    {whitespace} {/* ignora */}

    /* COMMENT */
    {Comment}    {/* ignora */}

    /* IDENTIFICATORI */
    {ID}     {return symbol(sym.ID,yytext());}

    /* NUMER */
    {Integer}        { return symbol(sym.INTEGER_CONST,Integer.valueOf(yytext())); }
    {Real}           { return symbol(sym.REAL_CONST,Double.valueOf(yytext())); }

    /* STRING */
    // {String}           { return symbol(sym.STRING_CONST,yytext()); }
    \"               { string.setLength(0); yybegin(STRING1); }
    \'               { string.setLength(0); yybegin(STRING2); }

}

//Case String start with " character
<STRING1> {
\"               { yybegin(YYINITIAL); return symbol(sym.STRING_CONST,  string.toString()  ); }
[^\n\r\"\'\\]+   {string.append(yytext()); }
\'               {string.append("\\'"); }
\\               {string.append("\\\\"); }
{EscapeCharacter}  { string.append(yytext()); }
{LineTerminator} { throw new Error("Unterminated string at end of line"); }
}

//Case String start with ' character
<STRING2> {
\'               { yybegin(YYINITIAL); return symbol(sym.STRING_CONST, string.toString()); }
[^\n\r\'\"\\]+   {string.append(yytext()); }
\"               {string.append("\\\""); }
\\               {string.append("\\\\"); }
{EscapeCharacter}  { string.append(yytext()); }
{LineTerminator} { throw new Error("Unterminated string at end of line"); }
}


[^]      { throw new Error("Illegal character <"+yytext()+">"); }
<<EOF>>  {return symbol(sym.EOF);}
