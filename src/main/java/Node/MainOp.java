package Node;

import Node.Declaration.VarDeclOp;
import Node.Statement.Statement;
import Visitor.Visitor;

import java.util.ArrayList;

public class MainOp extends SyntaxtNode
{
    private final ArrayList<VarDeclOp> listVarDecl;
    private final ArrayList<Statement> listStatement;

    public MainOp(ArrayList<VarDeclOp> listVarDecl, ArrayList<Statement> listStatement) {
        this.listVarDecl = listVarDecl;
        this.listStatement = listStatement;
    }

    public ArrayList<VarDeclOp> getListVarDecl() {
        return listVarDecl;
    }

    public ArrayList<Statement> getListStatement() {
        return listStatement;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
