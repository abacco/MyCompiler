package Node.Constant;

import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

public class Integer_Const extends SyntaxtNode implements Expression,IConstant {

    private final int value;

    public Integer_Const(int value) {this.value=value;}

    public Object accept(Visitor visitor) {
        return visitor.visit( this);
    }

    public int getValue() {
        return value;
    }
}
