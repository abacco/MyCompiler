package Node;

import Node.Declaration.ParDeclListOp;
import Node.Declaration.TypeOp;
import Node.Declaration.VarDeclOp;
import Node.Statement.Statement;
import Visitor.Visitor;

import java.util.ArrayList;

public class FunOp extends SyntaxtNode
{
    private final ID id;
    private final TypeOp type;
    private final ArrayList<VarDeclOp> listVarDecl;
    private final ParDeclListOp listParam;
    private final ArrayList<Statement> listStatement;

    public FunOp(ID id, TypeOp type, ArrayList<VarDeclOp> listVarDecl, ParDeclListOp listParam, ArrayList<Statement> listStatement) {
        this.id=id;
        this.type = type;
        this.listVarDecl = listVarDecl;
        this.listParam = listParam;
        this.listStatement = listStatement;
    }


    public FunOp(ID id, ArrayList<VarDeclOp> listVarDecl, ParDeclListOp listParam, ArrayList<Statement> listStatement) {
        this.id=id;
        this.type = null;
        this.listVarDecl = listVarDecl;
        this.listParam = listParam;
        this.listStatement = listStatement;
    }
    public ID getId() {
    return id;
}


    public TypeOp getType() {
        return type;
    }

    public ArrayList<VarDeclOp> getListVarDecl() {
        return listVarDecl;
    }

    public ParDeclListOp getListParam() {
        return listParam;
    }

    public ArrayList<Statement> getListStatement() {
        return listStatement;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
