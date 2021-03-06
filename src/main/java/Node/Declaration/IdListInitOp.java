package Node.Declaration;

import Node.Expression.Expression;
import Node.SyntaxtNode;
import Visitor.Visitor;

import java.util.HashMap;

public class IdListInitOp extends SyntaxtNode implements IListInit{
    private HashMap<String, Expression> list;

    public IdListInitOp(HashMap<String, Expression> list) {
        this.list = list;

    }



    public IdListInitOp put(String id,Expression e){
        this.list.put(id,e);
        return this;
    }

    public HashMap<String, Expression> getList() {
        return list;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }


}
