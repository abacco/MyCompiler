package Semantic;

import Node.ID;

import java.util.ArrayList;
import java.util.function.Function;

public class TreeSymbolTable implements ISymbolTable {
    private Scope currentScope;
    private final Scope root;
    private ArrayList<Scope> listScope = new ArrayList<Scope>();

    public ArrayList<Scope> getListScope() {
        return listScope;
    }

    public TreeSymbolTable() {
        currentScope = new Scope(null);
        listScope.add(currentScope);
        root = currentScope;

    }
    public TreeSymbolTable(String name) {
        currentScope = new Scope(null, name);
        listScope.add(currentScope);
        root = currentScope;

    }

    @Override
    public void enterScope() {
        Scope parent = this.getCurrentScope();
        Scope sc = new Scope(parent);
        listScope.add(sc);
        this.currentScope = sc;
    }

    public void enterScope(String name) {
        Scope parent = this.getCurrentScope();
        Scope sc = new Scope(parent, name);
        listScope.add(sc);
        this.currentScope = sc;
    }

    @Override
    public void exitScope() {
        this.setCurrentScope(getCurrentScope().getParent());
    }

    @Override
    public boolean prob(String id) {
        return currentScope.containsKey(id);
    }

    @Override
    public void add(String id, IKind kind) {
        this.getCurrentScope().put(id, kind);
    }

    @Override
    public boolean isGlobal(Scope scope) {
        if(scope==root) return true;
        else return false;
    }



    @Override
    public IKind lookup(String id) {
        Scope lastScope = this.getCurrentScope();
        while (lastScope != null) {
            if (prob(lastScope, id)) {
                return lastScope.get(id);
            } else {
                lastScope = lastScope.getParent();
            }
        }
        return null;
    }

    public IKind lookup(Scope scope, String id) {
        Scope lastScope = scope;
        while (lastScope != null) {
            if (prob(lastScope, id)) {
                return lastScope.get(id);
            } else {
                lastScope = lastScope.getParent();
            }
        }
        return null;
    }

    private boolean prob(Scope sc, String id) {
        return sc.containsKey(id);
    }

    @Override
    public Scope getCurrentScope() {
        return currentScope;
    }

    @Override
    public void setCurrentScope(Scope sc) {
        currentScope = sc;
    }

    public void stampTree()
    {
        for(Scope e : listScope) {
            System.out.println("Scope : " + e.getName());
            for (String key : e.keySet()) {
                IKind kind = e.get(key);

                if (kind instanceof VarKind)
                    System.out.println(key + "   " + " var " + kind.getReturnType().toString());
                else {
                    FunctionKind kindfun = (FunctionKind) kind;
                    System.out.print(key + "   " + " fun ");
                    for (ParamType p : kindfun.getListParm()) {
                        System.out.print(p.getParType() + " " + p.getVarType() + "*");
                    }
                    System.out.print("-> " + kindfun.getReturnType());
                    System.out.print("\n");
                }
            }
            System.out.print("\n");
        }
        }
}
