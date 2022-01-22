package Node.Statement;

import Visitor.Visitor;

public interface Statement
{
    Object accept(Visitor visitor);
}
