package Node.Expression;

import Node.Statement.Statement;
import Visitor.Visitor;

import java.util.ArrayList;

public class CallFunOp implements Expression, Statement
{
    private final String id;
    private final ArrayList<Expression> listExpression;

    public String getId() {
        return id;
    }

    public ArrayList<Expression> getListExpression() {
        return listExpression;
    }

    public CallFunOp(String id, ArrayList<Expression> listExpression)
    {
        this.id=id;
        this.listExpression=listExpression;
    }
    public CallFunOp(String id)
    {
        this.id=id;
        this.listExpression= new ArrayList<Expression>();
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
