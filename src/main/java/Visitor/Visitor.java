package Visitor;

import Node.Constant.*;
import Node.Declaration.*;
import Node.Expression.BinaryOperation;
import Node.Expression.CallFunOp;
import Node.Expression.Expression;
import Node.Expression.UnaryOperation;
import Node.FunOp;
import Node.ID;
import Node.MainOp;
import Node.ProgramOp;
import Node.Statement.*;

import java.util.ArrayList;

public interface Visitor {

    Object visit(ProgramOp Program);

    Object visit(MainOp Main);

    Object visit(VarDeclOp VarDecl);

    Object visit(TypeOp Type);

    Object visit(IdListInitOp IdListInit);

    Object visit(IdListInitObblOp IdListInitObbl);

    Object visit(Boolean_Const bconst);

    Object visit(Integer_Const Const);

    Object visit(Real_Const Const);

    Object visit(String_Const Const);

    Object visit(FunOp Fun);

    Object visit(ParDeclListOp ParamDeclList);

    Object visit(ParDeclOp ParDecl);

    Object visit(IfStatOp IfStat);

    Object visit(ElseStatOp Else);

    Object visit(WhileStatOp WhileStat);

    Object visit(ReadStatOp ReadStat);

    Object visit(WriteStatOp WriteStat);

    Object visit(AssignStatOp AssignStat);

    Object visit(CallFunOp CallFun);

    Object visit(ID id);

    Object visit(BinaryOperation binaryOperation);

    Object visit(UnaryOperation unaryOperation);

    Object visit(ReturnExpOp returnExpOp);
}
