package Node;

import Node.Expression.Expression;
import Visitor.Visitor;

public class ID implements Expression {

    private final String name;

    public ID(String id) {
        this.name = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
