package Node.Expression;

import Node.SyntaxtNode;
import Visitor.Visitor;

public class UnaryOperation extends SyntaxtNode implements Expression
{
    public enum UnaryOperationType
    {
        UminusOp,
        NotOp
    }

    public UnaryOperationType getType() {
        return type;
    }

    public Expression getE1() {
        return e1;
    }

    private UnaryOperationType type;
    private final Expression e1;

    public UnaryOperation(UnaryOperationType type, Expression e1){ this.type=type; this.e1=e1;}

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

}
