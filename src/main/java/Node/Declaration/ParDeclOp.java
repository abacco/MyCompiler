package Node.Declaration;

import Visitor.Visitor;

public class ParDeclOp
{
    public enum ParType
    {
        IN,
        OUT
    }

    private final ParType type;
    private final String id;

    public ParType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public ParDeclOp(ParType type, String id) {
        this.type = type;
        this.id = id;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
