package Node;

import Semantic.Enum.ReturnType;
import Semantic.Scope;

public abstract  class SyntaxtNode {
    private ReturnType type;
    private Scope scope = null;

    public ReturnType getNodeType() {
        return type;
    }

    public void setNodeType(ReturnType type) {
        this.type = type;
    }

    public void attachScope(Scope sc) {
        this.scope=sc;
    }

    public Scope getAttachScope() {
        return scope;
    }
}
