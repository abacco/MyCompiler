package Node.Statement;

import Node.Declaration.VarDeclOp;
import Node.Expression.Expression;
import Visitor.Visitor;

import java.util.ArrayList;

public class WhileStatOp implements Statement
{
    private final Expression expression;
    private final ArrayList<VarDeclOp> varDeclListOp;
    private final ArrayList<Statement> listStatement;

    public Expression getExpression() {
        return expression;
    }

    public ArrayList<VarDeclOp> getVarDeclListOp() {
        return varDeclListOp;
    }

    public ArrayList<Statement> getListStatement() {
        return listStatement;
    }

    public WhileStatOp(Expression expression, ArrayList<VarDeclOp> varDeclListOp, ArrayList<Statement> listStatement) {
        this.expression = expression;
        this.varDeclListOp = varDeclListOp;
        this.listStatement = listStatement;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
