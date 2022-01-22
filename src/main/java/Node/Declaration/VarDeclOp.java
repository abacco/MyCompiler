package Node.Declaration;

import Visitor.Visitor;

public class VarDeclOp
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

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
