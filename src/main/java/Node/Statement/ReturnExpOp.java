package Node.Statement;

import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

public class ReturnExpOp extends SyntaxtNode implements Statement
{
    private final Expression expression;

    public ReturnExpOp(Expression expression) {
        this.expression = expression;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }
}
