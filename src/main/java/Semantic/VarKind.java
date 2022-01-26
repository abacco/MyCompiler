package Semantic;

import Semantic.Enum.Kind;
import Semantic.Enum.ParType;
import Semantic.Enum.ReturnType;


public class VarKind implements IKind {

    private ReturnType type;
    private ParType partype;

    public VarKind(ReturnType type) {

        this.type = type;
        partype = null;
    }

    public VarKind(ReturnType type, ParType partype) {
        this.partype=partype;
        this.type = type;
    }

    public ReturnType getType() {
        return type;
    }

    public ParType getPartype() {
        return partype;
    }

    public void setPartype(ParType partype) {
        this.partype = partype;
    }

    public void setType(ReturnType type) {
        this.type = type;
    }

    @Override
    public Kind getKind() {
        return Kind.VAR;
    }

    @Override
    public ReturnType getReturnType() {
        return type;
    }

}
