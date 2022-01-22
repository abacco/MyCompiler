package Node.Statement;

import Node.Declaration.VarDeclOp;
import Visitor.Visitor;

import java.util.ArrayList;

public class ElseStatOp
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
        this.listVar = null;
        this.listStat = null;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }


}
