package Node.Declaration;

import Visitor.Visitor;

public class TypeOp
{
    public enum Type
    {
        INTEGER , BOOL , REAL , STRING
    }
    private final Type type;

    public TypeOp(Type type){ this.type=type; }

    public Type getType() {
        return type;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
