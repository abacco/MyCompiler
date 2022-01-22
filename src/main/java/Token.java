public class Token {

    public static final int ERROR = 0;

    public static final int EOF = 1;

    public static final int IF = 2;
    public static final int THEN = 3;
    public static final int ELSE = 4;
    public static final int REAL = 5;
    public static final int INTEGER = 6;
    public static final int WHILE = 7;

    public static final int MAIN = 8;
    public static final int ID = 9;
    public static final int STRING = 10;
    public static final int BOOL = 11;
    public static final int LPAR = 12;
    public static final int RPAR = 13;
    public static final int COLON = 14;
    public static final int FUN = 15;
    public static final int END  = 16;
    public static final int LOOP = 17;
    public static final int READ = 18;
    public static final int WRITE = 19;
    public static final int WRITELN= 20;
    public static final int WRITEB = 21;
    public static final int WRITET = 22;
    public static final int ASSIGN = 23;
    public static final int PLUS = 24;
    public static final int MINUS = 25;
    public static final int TIMES = 26;
    public static final int DIVINT = 27;
    public static final int DIV = 28;
    public static final int POW = 29;
    public static final int STR_CONCAT = 30;
    public static final int EQ = 31;
    public static final int NE = 32;
    public static final int LT = 33;
    public static final int LE = 34;
    public static final int GT = 35;
    public static final int GE = 36;
    public static final int AND = 37;
    public static final int OR = 38;
    public static final int NOT = 39;
    public static final int NULL = 40;
    public static final int TRUE = 41;
    public static final int FALSE = 42;
    public static final int INTEGER_CONST = 43;
    public static final int REAL_CONST = 44;
    public static final int STRING_CONST = 45;
    public static final int SEMI = 46;
    public static final int COMMA = 47;
    public static final int RETURN = 48;
    public static final int OUT = 49;
    public static final int VAR = 50;

    public static final String[] TOKENS = {
            "ERROR",
            "EOF",
            "IF",
            "THEN",
            "ELSE",
            "REAL",
            "INTEGER",
            "MAIN",
            "ID",
            "STRING",
            "BOOL",
            "LPAR",
            "RPAR",
            "COLON",
            "FUN",
            "LOOP",
            "READ",
            "WRITE",
            "WRITELN",
            "WRITEB",
            "WRITET",
            "ASSIGN",
            "PLUS",
            "MINUS",
            "TIMES",
            "DIVINT",
            "DIV",
            "POW",
            "STR_CONCAT",
            "EQ",
            "NE",
            "LT",
            "LE",
            "GT",
            "GE",
            "AND",
            "OR",
            "NOT",
            "NULL",
            "TRUE",
            "FALSE",
            "INTEGER_CONST",
            "REAL_CONST",
            "STRING_CONST",
            "SEMI",
            "COMMA",
            "RETURN",
            "OUT",
            "VAR"
    };
}
