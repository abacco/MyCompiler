package Node.Statement;

import Node.Expression.Expression;
import Node.ID;
import Node.SyntaxtNode;
import Visitor.Visitor;

import java.util.ArrayList;

public class ReadStatOp extends SyntaxtNode implements Statement
{
    private final ArrayList<ID> listId;
    private final Expression exp;

    public ArrayList<ID> getListId() {
        return listId;
    }

    public Expression getExpression() {
        return exp;
    }

    public ReadStatOp(ArrayList<ID> listId){
        this.listId=listId;
        this.exp=null;
    }

    public ReadStatOp(ArrayList<ID> listId, Expression exp)
    {
        this.listId=listId;
        this.exp=exp;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }




}
