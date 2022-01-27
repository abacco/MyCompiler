package Semantic;

import Semantic.Enum.ReturnType;

public class CompatibilityType {

    //Ordine intestazione tabelle(vale sia per le righe che per le colonne): int, real, string, bool

    public static ReturnType EQRELOP[][] = {
             //             int,                real,                    string,                bool
   /*int*/         { ReturnType.BOOLEAN, ReturnType.UNDEFINED, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
   /*real*/        { ReturnType.UNDEFINED, ReturnType.BOOLEAN, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
   /*string*/      { ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.BOOLEAN, ReturnType.UNDEFINED},
   /*bool*/        { ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.BOOLEAN}

    };

    public static ReturnType RELOP[][] = {
            { ReturnType.BOOLEAN, ReturnType.BOOLEAN, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
            { ReturnType.BOOLEAN, ReturnType.BOOLEAN, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
            { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED},
            { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED }
    };

    public static ReturnType STR_CONCAT[][] =
    {
            { ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.STRING,ReturnType.UNDEFINED},
            { ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.STRING,ReturnType.UNDEFINED},
            { ReturnType.STRING     ,ReturnType.STRING   ,ReturnType.STRING,ReturnType.UNDEFINED},
            { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED }
    };

    public static ReturnType AND_OR[][] =
            {
                    { ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.BOOLEAN }
            };

    public static ReturnType MINUS[] =
            {
                     ReturnType.INT, ReturnType.REAL, ReturnType.UNDEFINED,ReturnType.UNDEFINED

            };

    public static ReturnType NOT[] =
            {
                     ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.UNDEFINED,ReturnType.BOOLEAN

            };

    public static ReturnType Arithmetic_operation[][] =
            {
                    { ReturnType.INT, ReturnType.REAL, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.REAL, ReturnType.REAL, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED }

            };
    public static ReturnType DIVINT_POW[][] =
            {
                    { ReturnType.INT, ReturnType.UNDEFINED, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED, ReturnType.UNDEFINED, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.UNDEFINED }

            };

    public static ReturnType ASSIGNOP[][] =
            {
                    { ReturnType.INT, ReturnType.INT, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.REAL, ReturnType.REAL, ReturnType.UNDEFINED,ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.STRING, ReturnType.UNDEFINED},
                    { ReturnType.UNDEFINED,ReturnType.UNDEFINED,ReturnType.UNDEFINED, ReturnType.BOOLEAN }

            };

    public static int getIndexFor(ReturnType r) {
        int toReturn = 0;
        if(r == ReturnType.INT)
            toReturn = 0;
        else if(r == ReturnType.REAL)
            toReturn = 1;
        else if(r == ReturnType.STRING)
            toReturn = 2;
        else if (r == ReturnType.BOOLEAN)
            toReturn = 3;
        return toReturn;
    }

}
