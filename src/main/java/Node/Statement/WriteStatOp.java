package Node.Statement;

import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

public class WriteStatOp extends SyntaxtNode implements Statement
{
    public enum TypeWrite
    {
       WRITE,
	   WRITELN,
	   WRITET,
	   WRITEB,
    }
    private final Expression expression;
    private final TypeWrite type;

    public WriteStatOp(TypeWrite t, Expression exp){ expression=exp; this.type=t;}

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public TypeWrite getType() {
        return type;
    }
}
