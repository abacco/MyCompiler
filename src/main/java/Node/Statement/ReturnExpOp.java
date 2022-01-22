package Node.Statement;

import Node.Expression.Expression;
import Visitor.Visitor;

public class ReturnExpOp implements Statement
{
    private final Expression expression;

    public ReturnExpOp(Expression expression) {
        this.expression = expression;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }
}
