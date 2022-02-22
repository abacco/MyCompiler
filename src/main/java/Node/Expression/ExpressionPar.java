package Node.Expression;

import Node.SyntaxtNode;
import Visitor.Visitor;

public class ExpressionPar extends SyntaxtNode implements Expression
{
    private final Expression exp;

    public ExpressionPar(Expression exp) {
        this.exp = exp;
    }

    public Expression getExpression() {
        return exp;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}