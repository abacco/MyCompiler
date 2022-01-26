package Node.Expression;

import Node.ID;
import Node.Statement.Statement;
import Node.SyntaxtNode;
import Visitor.Visitor;

import java.util.ArrayList;

public class CallFunOp extends SyntaxtNode implements Expression, Statement
{
    private final ID id;
    private final ArrayList<Expression> listExpression;

    public ID getId() {
        return id;
    }

    public ArrayList<Expression> getListExpression() {
        return listExpression;
    }

    public CallFunOp(ID id, ArrayList<Expression> listExpression)
    {
        this.id=id;
        this.listExpression=listExpression;
    }
    public CallFunOp(ID id)
    {
        this.id=id;
        this.listExpression= new ArrayList<Expression>();
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

}
