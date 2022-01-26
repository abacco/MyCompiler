package Node;

import Node.Declaration.VarDeclOp;
import Visitor.Visitor;

import java.util.ArrayList;

public class ProgramOp extends SyntaxtNode
{
    private final ArrayList<VarDeclOp> listVarDecl;
    private final ArrayList<FunOp> listFun;
    public final MainOp mainOp;


    public ProgramOp(ArrayList<VarDeclOp> listVarDecl, ArrayList<FunOp> listFun, MainOp main) {
        this.listVarDecl = listVarDecl;
        this.listFun = listFun;
        this.mainOp=main;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public ArrayList<VarDeclOp> getListVarDecl() {
        return listVarDecl;
    }

    public ArrayList<FunOp> getListFun() {
        return listFun;
    }

    public MainOp getMainOp() {
        return mainOp;
    }
}
