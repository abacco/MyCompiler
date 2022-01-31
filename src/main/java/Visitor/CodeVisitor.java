package Visitor;

import Node.Constant.Boolean_Const;
import Node.Constant.Integer_Const;
import Node.Constant.Real_Const;
import Node.Constant.String_Const;
import Node.Declaration.*;
import Node.Expression.BinaryOperation;
import Node.Expression.CallFunOp;
import Node.Expression.UnaryOperation;
import Node.FunOp;
import Node.ID;
import Node.MainOp;
import Node.ProgramOp;
import Node.Statement.*;
import Semantic.TreeSymbolTable;

public class CodeVisitor implements Visitor{
    private static final String C_HEADER =
            "#include <stdio.h>\n" + "#include <stdbool.h>\n#include <string.h>\n#include<stdlib.h>\n";

    private static final String DECL_HEADER =  "#define LENGTH  2048\n" + "char str1[LENGTH], str2[LENGTH];\n";

    private TreeSymbolTable symbolTable;
    private StringBuilder mallocString = new StringBuilder();

    // use the mallocString for create into main the malloc for global variable (string)
    public CodeVisitor(TreeSymbolTable table) {
        super();
        this.symbolTable = table;
    }

    @Override
    public Object visit(ProgramOp Program) throws Exception {


        String declarations = "";
        String functions = "" ;
        String main = "";
        StringBuilder programBuilder = new StringBuilder();

        for (VarDeclOp var: Program.getListVarDecl()) {
            declarations += (String) var.accept(this);
        }
        for (FunOp fun: Program.getListFun()) {
            functions += (String) fun.accept(this);
        }

        main = (String) Program.getMainOp().accept(this);




        programBuilder.append(C_HEADER).append('\n').append(DECL_HEADER).append('\n')
                .append(declarations).append('\n').append(functions).append('\n').
                append(main).append('\n');

        return programBuilder.toString();
    }

    @Override
    public Object visit(MainOp Main) throws Exception {

        StringBuilder mainBuilder = new StringBuilder();
        String declarations = "";
        String statements = "";

        for (VarDeclOp var: Main.getListVarDecl()) {
            declarations += (String) var.accept(this);
        }
        for (Statement stm : Main.getListStatement()) {
            statements += (String) stm.accept(this);
        }

        mainBuilder.append("int main(void){\n").append(declarations).append("\n").append(statements).append("\n").append(" return 0;\n}\n");

        return mainBuilder.toString();
    }

    @Override
    public Object visit(VarDeclOp VarDecl) throws Exception {
        return null;
    }

    @Override
    public Object visit(TypeOp Type) {
        return null;
    }

    @Override
    public Object visit(IdListInitOp IdListInit) throws Exception {
        return null;
    }

    @Override
    public Object visit(IdListInitObblOp IdListInitObbl) {
        return null;
    }

    @Override
    public Object visit(Boolean_Const bconst) {
        return String.valueOf(bconst.getValue());
    }

    @Override
    public Object visit(Integer_Const Const) {
        return String.valueOf(Const.getValue());
    }

    @Override
    public Object visit(Real_Const Const) {
        return String.valueOf(Const.getValue());
    }

    @Override
    public Object visit(String_Const Const) {
        return "\"" + String.valueOf(Const.getStringConst()) + "\"";
    }

    @Override
    public Object visit(FunOp Fun) throws Exception {
        return null;
    }

    @Override
    public Object visit(ParDeclListOp ParamDeclList) throws Exception {
        return null;
    }

    @Override
    public Object visit(ParDeclOp ParDecl) throws Exception {
        return null;
    }

    @Override
    public Object visit(IfStatOp IfStat) throws Exception {
        return null;
    }

    @Override
    public Object visit(ElseStatOp Else) throws Exception {
        return null;
    }

    @Override
    public Object visit(WhileStatOp WhileStat) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReadStatOp ReadStat) throws Exception {
        return null;
    }

    @Override
    public Object visit(WriteStatOp WriteStat) throws Exception {
        return null;
    }

    @Override
    public Object visit(AssignStatOp AssignStat) throws Exception {
        return null;
    }

    @Override
    public Object visit(CallFunOp CallFun) throws Exception {
        return null;
    }

    @Override
    public Object visit(ID id) throws Exception {
        return null;
    }

    @Override
    public Object visit(BinaryOperation binaryOperation) throws Exception {
        return null;
    }

    @Override
    public Object visit(UnaryOperation unaryOperation) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReturnExpOp returnExpOp) throws Exception {
        return null;
    }

    /* private String compactCode(List<? extends SyntaxtNode> list) {
        return list.stream().map(l -> l.accept(this)).reduce("", String::concat);
    } */
}
