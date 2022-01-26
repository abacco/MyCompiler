package Node.Constant;

import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

public class Real_Const extends SyntaxtNode implements Expression,IConstant
{
    private final double value;

    public Real_Const(double value ){ this.value=value; }


    public Object accept(Visitor visitor) {
        return visitor.visit( this);
    }

    public double getValue() {
        return value;
    }
}
