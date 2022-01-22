package Node;

import Node.Declaration.ParDeclListOp;
import Node.Declaration.TypeOp;
import Node.Declaration.VarDeclOp;
import Node.Statement.Statement;
import Visitor.Visitor;
import Visitor.XMLGenerator;

import java.util.ArrayList;

public class FunOp
{
    private final TypeOp type;
    private final ArrayList<VarDeclOp> listVarDecl;
    private final ParDeclListOp listParam;
    private final ArrayList<Statement> listStatement;

    public FunOp(TypeOp type, ArrayList<VarDeclOp> listVarDecl, ParDeclListOp listParam, ArrayList<Statement> listStatement) {
        this.type = type;
        this.listVarDecl = listVarDecl;
        this.listParam = listParam;
        this.listStatement = listStatement;
    }

    public FunOp(ArrayList<VarDeclOp> listVarDecl, ParDeclListOp listParam, ArrayList<Statement> listStatement) {
        this.type = null;
        this.listVarDecl = listVarDecl;
        this.listParam = listParam;
        this.listStatement = listStatement;
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

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
