package Visitor;

import Node.*;
import Node.Constant.Boolean_Const;
import Node.Constant.Integer_Const;
import Node.Constant.Real_Const;
import Node.Constant.String_Const;
import Node.Declaration.*;
import Node.Expression.BinaryOperation;
import Node.Expression.CallFunOp;
import Node.Expression.UnaryOperation;
import Node.Statement.*;
import Semantic.Enum.ReturnType;
import Semantic.FunctionKind;
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

        mainBuilder.append("int main(void){\n").append("\n").append(declarations).append("\n").append(statements).append("\n").append(" return 0;\n}\n");

        return mainBuilder.toString();
    }

    @Override
    public Object visit(VarDeclOp VarDecl) throws Exception {

        StringBuilder varDeclBuilder = new StringBuilder();
        String type = "";
        String listInit = "";

        if(VarDecl.getType()!=null) {
            type = (String) VarDecl.getType().accept(this);
            listInit = (String) VarDecl.getListInit().accept(this);
            return varDeclBuilder.append(type).append(listInit).append("\n").toString();
        }
        else // case typeOp is var
        {
            listInit = (String) VarDecl.getListInit().accept(this);
            return varDeclBuilder.append(listInit).append("\n").toString();
        }

    }

    @Override
    public Object visit(TypeOp Type) {

        String type = "";

        if(Type.getType() == TypeOp.Type.BOOL)
        {
            type="bool";
        }
        else if(Type.getType() == TypeOp.Type.INTEGER)
        {
            type="int";
        }
        else if(Type.getType() == TypeOp.Type.REAL)
        {
            type="double";
        }
        else
        {
            type="char"; //case string
        }

        return type;
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
        return String.valueOf(Const.getStringConst()).replace("\'", "\"") ;
    }

    @Override
    public Object visit(IdListInitOp IdListInit) throws Exception
    {
        StringBuilder listInit = new StringBuilder();
        int i=1;
        String expression = "";

        for (String key: IdListInit.getList().keySet())
        {
            String id = key;
            ReturnType varType = symbolTable.lookup(IdListInit.getAttachScope(), key).getReturnType();

            if(varType==ReturnType.STRING) id = "*" + key;

            if (IdListInit.getList().get(key) != null) {
                expression = (String) IdListInit.getList().get(key).accept(this);
                listInit.append(" ").append(id).append("=").append(expression);
            } else {
                listInit.append(" ").append(id);
            }

            if (i != IdListInit.getList().size()) listInit.append(",").append(" ");

            i++;

        }
        listInit.append(";");
        return listInit.toString();
    }

    @Override
    public Object visit(IdListInitObblOp IdListInitObbl)
    {
        StringBuilder listInit = new StringBuilder();
        int i=1;
        String expression = "";

        for (String key: IdListInitObbl.getList().keySet())
        {

            String id = key;
            String type =convertReturnType_ToTypeC(symbolTable.lookup(IdListInitObbl.getAttachScope(), key).getReturnType());
            if(type.equals("char")) id = "*" + key;

            expression = (String) IdListInitObbl.getList().get(key).accept(this);
            listInit.append(type).append(" ").append(id).append("=").append(expression);

            if (i != IdListInitObbl.getList().size()) listInit.append(";").append("\n");

            i++;

        }

        listInit.append(";");
        return listInit.toString();

    }

    @Override
    public Object visit(FunOp Fun) throws Exception
    {
        StringBuilder funBuilder = new StringBuilder();
        String return_type = "";
        String paramDecl = "";
        String declarations = "";
        String statements = "";

        if(Fun.getType()==null) return_type = "void";
        else return_type = (String) Fun.getType().accept(this);


        paramDecl = (String) Fun.getListParam().accept(this);


        for (VarDeclOp var: Fun.getListVarDecl()) {
            declarations += (String) var.accept(this);
        }
        for (Statement stm : Fun.getListStatement()) {
            statements += (String) stm.accept(this);
        }

       return funBuilder.append(return_type).append(" ").append(Fun.getId()).append("(").append(paramDecl).append(")\n{\n").append(declarations).append("\n").append(statements).append("\n}\n").toString();
    }

    @Override
    public Object visit(ParDeclListOp ParamDeclList) throws Exception
    {
        StringBuilder parBuilder = new StringBuilder();
        int i=1;

        for (ParDeclOp el: ParamDeclList.getListParDecl())
        {

            parBuilder.append(el.accept(this));
            if(i!=ParamDeclList.getListParDecl().size()) parBuilder.append(", ");
            i++;
        }

        return parBuilder.toString();
    }

    @Override
    public Object visit(ParDeclOp ParDecl) throws Exception
    {

        StringBuilder parBuilder = new StringBuilder();

        String type = (String) ParDecl.getTypeOp().accept(this);
        String id = (String) ParDecl.getId();

        if(ParDecl.getType() == ParDeclOp.ParType.OUT || type.equals("char")){
            parBuilder.append(type).append(" ").append("*" + id);
        }
        else
        {
            parBuilder.append(type).append(" ").append(id);
        }

        return parBuilder.toString();
    }

    @Override
    public Object visit(IfStatOp IfStat) throws Exception
    {
        StringBuilder ifBuilder = new StringBuilder();
        String declarations = "";
        String expression = "";
        String statements = "";
        String elseOp = "";

        for (VarDeclOp var: IfStat.getListVarDeclOp())
        {
            declarations += (String) var.accept(this);
        }

        expression = (String) IfStat.getExpression().accept(this);

        for (Statement stm:IfStat.getListStatement() )
        {
            statements += (String) stm.accept(this);
        }

        if(!elseOp.isEmpty()) elseOp= (String) IfStat.getElseStatOp().accept(this);
        else elseOp="";

        return ifBuilder.append("if(").append(expression).append(")\n{\n").append(declarations).append("\n").append(statements).append("\n}\n").append(elseOp).toString();

    }

    @Override
    public Object visit(ElseStatOp Else) throws Exception {
        StringBuilder elseBuilder = new StringBuilder();
        String declarations = "";
        String statements = "";


        for (VarDeclOp var: Else.getListVar())
        {
            declarations += (String) var.accept(this);
        }

        for (Statement stm:Else.getListStat() )
        {
            statements += (String) stm.accept(this);
        }
        return elseBuilder.append("else\n{\n").append(declarations).append("\n").append(statements).append("\n}\n").toString();
    }

    @Override
    public Object visit(WhileStatOp WhileStat) throws Exception
    {
        StringBuilder whileBuilder = new StringBuilder();
        String declarations = "";
        String expression = "";
        String statements = "";


        for (VarDeclOp var: WhileStat.getVarDeclListOp())
        {
            declarations += (String) var.accept(this);
        }

        expression = (String) WhileStat.getExpression().accept(this);

        for (Statement stm:WhileStat.getListStatement() )
        {
            statements += (String) stm.accept(this);
        }

        return whileBuilder.append("while(").append(expression).append(")\n{\n").append(declarations).append("\n").append(statements).append("\n}\n").toString();
    }

    @Override
    public Object visit(ReadStatOp ReadStat) throws Exception {

        StringBuilder builder = new StringBuilder();
        String expr = "", idList = "";
        String listFormat = "";
        if (ReadStat.getExpression() != null){
            expr = (String) ReadStat.getExpression().accept(this);
        }


        int i = 1;
        for (ID id  : ReadStat.getListId()){

            listFormat += getFormatReturType(id.getNodeType());
            idList += "&" + (String) id.accept(this) ;
            if(ReadStat.getListId().size() != i) idList += ",";
            i++;
        }
        builder.append("printf(").append(expr).append(");").append("\n");
        return builder.append("scanf(").append( "\"" + listFormat + "\"").append(",").append(idList).append(");").toString();
    }

    @Override
    public Object visit(WriteStatOp WriteStat) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        String expr = (String) WriteStat.getExpression().accept(this);
        String write = "";

        if(WriteStat.getType()==WriteStatOp.TypeWrite.WRITE)
        {
            builder.append("printf(").append(expr).append(");");
        }
        else if(WriteStat.getType()==WriteStatOp.TypeWrite.WRITELN)
        {
            builder.append("printf(").append(expr).append("\n").append(");");
        }
        else if(WriteStat.getType()==WriteStatOp.TypeWrite.WRITET)
        {
            builder.append("printf(").append(expr).append("\t").append(");");
        }
        else
        { //case WriteB
            builder.append("printf(").append(expr).append("\b").append(");");
        }
        return builder.toString();
    }

    @Override
    public Object visit(AssignStatOp AssignStat) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        String id = (String) AssignStat.getId().accept(this);
        String expression = (String) AssignStat.getExpression().accept(this);

        return builder.append(id).append(" = ").append(expression).append(";").toString();

    }

    @Override
    public Object visit(CallFunOp CallFun) throws Exception
    {
        return null;
    }

    @Override
    public Object visit(ID id) throws Exception
    {
        if(id.isOutPar()) return "(*" + id.getName() + ")";
        else return id.getName();
    }

    @Override
    public Object visit(BinaryOperation binaryOperation) throws Exception {
        return null;
    }

    @Override
    public Object visit(UnaryOperation unaryOperation) throws Exception
    {
        return null;
    }

    @Override
    public Object visit(ReturnExpOp returnExpOp) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        String expression = (String) returnExpOp.getExpression().accept(this);

        return builder.append("return ").append(expression).append(";").toString();
    }

    public static String convertReturnType_ToTypeC(ReturnType returnType)
    {
        String type = "";

        if(returnType == ReturnType.BOOLEAN)
        {
            type="bool";
        }
        else if(returnType == ReturnType.INT)
        {
            type="int";
        }
        else if(returnType == ReturnType.REAL)
        {
            type="double";
        }
        else
        {
            type="char"; //case string
        }

        return type;
    }

    public static String getFormatReturType(ReturnType rt){

        String type = "";
        if(rt == ReturnType.STRING)
        {
            type="%s";
        }
        else if(rt == ReturnType.INT)
        {
            type="%d";
        }
        else if(rt == ReturnType.REAL)
        {
            type="%f";
        }

        return type;

    }

}
