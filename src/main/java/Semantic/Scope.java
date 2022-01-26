package Semantic;
import Node.ID;

import java.util.HashMap;

/*
 * Ogni scope è un hashmap e al suo interno c'è un identificatore e la tipologia di elemento (variabile, funzione) al cui interno c'è il tipo.
 */
public class Scope extends HashMap<String, IKind> {

    private final Scope parent;
    private String name=null;
    private static final long serialVersionUID = 1L;


    public Scope(Scope parent) {
        super();
        this.parent = parent;
    }

    public Scope(Scope parent, String name) {
        super();
        this.parent = parent;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public Scope getParent() {
        return parent;
    }

}
