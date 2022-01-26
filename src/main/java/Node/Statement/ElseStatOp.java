package Node.Statement;

import Node.Declaration.VarDeclOp;
import Node.SyntaxtNode;
import Visitor.Visitor;

import java.util.ArrayList;

public class ElseStatOp extends SyntaxtNode
{
    private final ArrayList<VarDeclOp> listVar;
    private final ArrayList<Statement> listStat;

    public ArrayList<VarDeclOp> getListVar() {
        return listVar;
    }

    public ArrayList<Statement> getListStat() {
        return listStat;
    }

    public ElseStatOp(ArrayList<VarDeclOp> listVar, ArrayList<Statement> listStat) {
        this.listVar = listVar;
        this.listStat = listStat;
    }

    public ElseStatOp() {
        this.listVar = new ArrayList<VarDeclOp>();
        this.listStat = new ArrayList<Statement>();
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }


}
