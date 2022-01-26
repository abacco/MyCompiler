package Semantic;

import Semantic.Enum.Kind;
import Semantic.Enum.ReturnType;

import java.util.ArrayList;

public class FunctionKind implements IKind {
  private ArrayList<ParamType> listParm;
  private ReturnType returnType;


  public FunctionKind(ReturnType returnType, ArrayList<ParamType> listParm) {
    this.returnType = returnType;
    this.listParm= listParm;
  }

  public ArrayList<ParamType> getListParm() {
    return listParm;
  }

  public void addParam(ParamType e)
  {
    listParm.add(e);
  }


  @Override
  public Kind getKind() {
    return Kind.FUN;
  }

  @Override
  public ReturnType getReturnType() {
    return returnType;
  }


}
