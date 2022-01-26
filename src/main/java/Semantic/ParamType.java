package Semantic;

import Semantic.Enum.Kind;
import Semantic.Enum.ParType;
import Semantic.Enum.ReturnType;


public class ParamType {

    private ReturnType varType;
    private ParType parType;

    public ParamType(ReturnType varType, ParType parType) {
        this.varType = varType;
        this.parType = parType;
    }

    public ReturnType getVarType() {
        return varType;
    }

    public void setVarType(ReturnType varType) {
        this.varType = varType;
    }

    public ParType getParType() {
        return parType;
    }

    public void setParType(ParType parType) {
        this.parType = parType;
    }


}
