package Semantic;

import Node.ID;

public interface ISymbolTable {

    /* start new scope */
    void enterScope();

    void enterScope(String name);

    /* exit current scope*/
    void exitScope();

    /*true if address defined in current scope*/
    boolean prob(String id);
    /* add address element to current scope*/
    void add(String id, IKind kind);

    /* finds current scope*/
    IKind lookup(String id);

    IKind lookup(Scope scope, String id);

    /* get current scope */
    Scope getCurrentScope();

    /* set current scope */
    void setCurrentScope(Scope sc);

}
