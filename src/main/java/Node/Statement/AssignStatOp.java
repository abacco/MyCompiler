package Node.Statement;

import Node.Expression.Expression;
import Node.ID;
import Node.SyntaxtNode;
import Visitor.Visitor;

public class AssignStatOp extends SyntaxtNode implements Statement
{
    private final ID id;
    private final Expression expression;


    public AssignStatOp(ID id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    public ID getId() {
        return id;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
