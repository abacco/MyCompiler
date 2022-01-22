package Node.Expression;

import Visitor.Visitor;

public interface Expression
{
    Object accept(Visitor visitor);

}
