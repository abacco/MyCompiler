package Node.Constant;


import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

public class Boolean_Const extends SyntaxtNode implements Expression, IConstant {

    private final boolean boo;

    public Boolean_Const(boolean boo ){this.boo = boo; }

    public Object accept(Visitor visitor) {
        return visitor.visit( this);
    }

    public String getValue() {
        return boo + "";
    }
}
