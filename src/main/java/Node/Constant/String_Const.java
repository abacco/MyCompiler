package Node.Constant;

import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

public class String_Const extends SyntaxtNode implements Expression, IConstant
{
    private final String string;

    public String_Const(String string){ this.string = string ; }

    public Object accept(Visitor visitor) {
        return visitor.visit( this);
    }

    public String getStringConst() {
        return string;
    }
}
