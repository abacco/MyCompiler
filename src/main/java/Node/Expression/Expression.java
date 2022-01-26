package Node.Expression;

import Node.SyntaxtNode;
import Visitor.Visitor;

public interface Expression
{

    Object accept(Visitor visitor) throws Exception;

}
