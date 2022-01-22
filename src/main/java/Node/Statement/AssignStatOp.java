package Node.Statement;

import Node.Expression.Expression;
import Visitor.Visitor;

public class AssignStatOp implements Statement
{
    private final String id;
    private final Expression expression;

    public String getId() {
        return id;
    }

    public Expression getExpression() {
        return expression;
    }

    public AssignStatOp(String id, Expression exp) {this.id=id; this.expression=exp;}

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }


}
