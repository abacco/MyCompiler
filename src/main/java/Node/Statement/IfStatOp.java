package Node.Statement;

import Node.Declaration.VarDeclOp;
import Node.Expression.Expression;
import Visitor.Visitor;

import java.util.ArrayList;

public class IfStatOp  implements Statement
{
    private final Expression expression;
    private final ArrayList<VarDeclOp> listVarDeclOp;
    private final ArrayList<Statement> listStatement;

    public Expression getExpression() {
        return expression;
    }

    public ArrayList<VarDeclOp> getListVarDeclOp() {
        return listVarDeclOp;
    }

    public ArrayList<Statement> getListStatement() {
        return listStatement;
    }

    public IfStatOp(Expression expression, ArrayList<VarDeclOp> listVarDeclOp, ArrayList<Statement> listStatement) {
        this.expression = expression;
        this.listVarDeclOp = listVarDeclOp;
        this.listStatement = listStatement;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
