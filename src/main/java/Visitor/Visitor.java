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

    Object visit(ProgramOp Program) throws Exception;

    Object visit(MainOp Main) throws Exception;

    Object visit(VarDeclOp VarDecl) throws Exception;

    Object visit(TypeOp Type);

    Object visit(IdListInitOp IdListInit) throws Exception;

    Object visit(IdListInitObblOp IdListInitObbl);

    Object visit(Boolean_Const bconst);

    Object visit(Integer_Const Const);

    Object visit(Real_Const Const);

    Object visit(String_Const Const);

    Object visit(FunOp Fun) throws Exception;

    Object visit(ParDeclListOp ParamDeclList) throws Exception;

    Object visit(ParDeclOp ParDecl) throws Exception;

    Object visit(IfStatOp IfStat) throws Exception;

    Object visit(ElseStatOp Else) throws Exception;

    Object visit(WhileStatOp WhileStat) throws Exception;

    Object visit(ReadStatOp ReadStat) throws Exception;

    Object visit(WriteStatOp WriteStat) throws Exception;

    Object visit(AssignStatOp AssignStat) throws Exception;

    Object visit(CallFunOp CallFun) throws Exception;

    Object visit(ID id) throws Exception;

    Object visit(BinaryOperation binaryOperation) throws Exception;

    Object visit(UnaryOperation unaryOperation) throws Exception;

    Object visit(ReturnExpOp returnExpOp) throws Exception;
}
