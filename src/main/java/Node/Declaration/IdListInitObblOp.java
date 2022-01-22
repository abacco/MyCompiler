package Node.Declaration;

import Node.Constant.IConstant;
import Node.Expression.Expression;
import Visitor.Visitor;

import java.util.HashMap;

public class IdListInitObblOp implements IListInit
{
    private HashMap<String, IConstant> list;

    public IdListInitObblOp(HashMap<String, IConstant> list) {
        this.list = list;

    }


    public IdListInitObblOp put(String id,IConstant c){
        this.list.put(id,c);
        return this;
    }

    public HashMap<String, IConstant> getList() {
        return list;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
