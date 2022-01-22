package Node.Declaration;

import Visitor.Visitor;

import java.util.ArrayList;

public class ParDeclListOp
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

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
