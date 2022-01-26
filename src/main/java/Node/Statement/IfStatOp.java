package Node.Statement;

import Node.Declaration.VarDeclOp;
import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

import java.util.ArrayList;

public class IfStatOp extends SyntaxtNode implements Statement
{
    private final Expression expression;
    private final ArrayList<VarDeclOp> listVarDeclOp;
    private final ArrayList<Statement> listStatement;
    private final ElseStatOp elseStatOp;

    public Expression getExpression() {
        return expression;
    }

    public ArrayList<VarDeclOp> getListVarDeclOp() {
        return listVarDeclOp;
    }

    public ArrayList<Statement> getListStatement() {
        return listStatement;
    }

    public IfStatOp(Expression expression, ArrayList<VarDeclOp> listVarDeclOp, ArrayList<Statement> listStatement, ElseStatOp elseStatOp) {
        this.expression = expression;
        this.listVarDeclOp = listVarDeclOp;
        this.listStatement = listStatement;
        this.elseStatOp=elseStatOp;
    }

    public ElseStatOp getElseStatOp() {
        return elseStatOp;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

}
