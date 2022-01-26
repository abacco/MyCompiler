package Node.Declaration;

import Node.SyntaxtNode;
import Visitor.Visitor;

public class ParDeclOp extends SyntaxtNode
{
    public enum ParType
    {
        IN,
        OUT
    }

    private final ParType type;
    private final String id;
    private final TypeOp typeOp;

    public TypeOp getTypeOp() {
        return typeOp;
    }

    public ParType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public ParDeclOp(ParType type,TypeOp typeOp, String id) {
        this.typeOp=typeOp;
        this.type = type;
        this.id = id;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
