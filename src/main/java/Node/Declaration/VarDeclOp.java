package Node.Declaration;

import Node.SyntaxtNode;
import Visitor.Visitor;

public class VarDeclOp extends SyntaxtNode
{
    private final TypeOp type;
    private final IListInit listInit;

    public VarDeclOp(TypeOp t, IListInit listInit)
    {
        this.type=t;
        this.listInit=listInit;
    }

    public VarDeclOp(IListInit listInit)
    {
        this.type=null;
        this.listInit=listInit;
    }

    public TypeOp getType() {
        return type;
    }

    public IListInit getListInit() {
        return listInit;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
