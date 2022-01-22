package Node.Expression;

import Visitor.Visitor;

public class BinaryOperation implements Expression
{
    public enum BinaryOperationType
    {
        AddOp,
        MulOp,
        DiffOp,
        DivOp,
        DivIntOp,
        PowOp,
        StrCatOp,
        AndOp,
        OrOp,
        GTOp,
        GEOp,
        LTOp,
        LEOp,
        EQOp,
        NEOp
    }

    private BinaryOperationType type;
    private final Expression e1, e2;

    public BinaryOperationType getType() {
        return type;
    }

    public Expression getE1() {
        return e1;
    }

    public Expression getE2() {
        return e2;
    }

    public BinaryOperation(BinaryOperationType type, Expression e1, Expression e2){ this.type=type; this.e1=e1; this.e2=e2;}

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
