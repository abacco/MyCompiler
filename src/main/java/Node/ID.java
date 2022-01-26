package Node;

import Node.Expression.Expression;
import Visitor.Visitor;

public class ID extends SyntaxtNode implements Expression {

    private final String name;
    private boolean outPar=false;

    public ID(String id) {
        this.name = id;
    }

    public String getName() {
        return name;
    }

    public void setOutPar(boolean bool){
        outPar=bool;
    }

    public boolean isOutPar() {
        return outPar;
    }

    @Override
    public String toString() {
        return name;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
