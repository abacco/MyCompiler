package Node.Declaration;

import Node.SyntaxtNode;
import Visitor.Visitor;

import java.util.ArrayList;

public class ParDeclListOp extends SyntaxtNode
{
    private final ArrayList<ParDeclOp> listParDecl;

    public ArrayList<ParDeclOp> getListParDecl() {
        return listParDecl;
    }

    public ParDeclListOp(ArrayList<ParDeclOp> listParDecl) {
        this.listParDecl = listParDecl;
    }
    public ParDeclListOp() {
        this.listParDecl = new ArrayList<ParDeclOp>();
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
