package Visitor;

import Node.*;
import Node.Constant.Boolean_Const;
import Node.Constant.Integer_Const;
import Node.Constant.Real_Const;
import Node.Constant.String_Const;
import Node.Declaration.*;
import Node.Expression.BinaryOperation;
import Node.Expression.CallFunOp;
import Node.Expression.Expression;
import Node.Expression.UnaryOperation;
import Node.Statement.*;
import Semantic.*;
import Semantic.Enum.ParType;
import Semantic.Enum.ReturnType;

import java.util.Collections;

public class CodeVisitor implements Visitor{
    private static final String C_HEADER =
            "#include <stdio.h>\n" + "#include <stdbool.h>\n#include <string.h>\n#include<stdlib.h>\n#include <math.h>\n";


    private static final String DECL_HEADER =  "#define LENGTH  2048\n" ;
    private static final String DEFAULT_FUNCTION =
            "char* concatCD(char *s1, double i) {\n" +
                    "char *s=malloc(sizeof(char) * LENGTH);\n" +
                    "sprintf(s, \"%s%.2f\", s1, i);\n" +
                    "return s;\n" +
                    "}" +
                    "\n" +
                    "char* concatDC(double i, char *s1) {\n" +
                    "char *s=malloc(sizeof(char) * LENGTH);\n" +
                    "sprintf(s, \"%.2f%s\", i, s1);\n" +
                    "return s;\n" +
                    "}" +
                    "\n" +
                    "char* concatCI(char *s1, int i) {\n" +
                    "char *s=malloc(sizeof(char) * LENGTH);\n" +
                    "sprintf(s, \"%s%d\", s1, i);\n" +
                    "return s;\n" +
                    "}" +
                    "\n" +
                    "char* concatIC(int i, char *s1) {\n" +
                    "char *s=malloc(sizeof(char) * LENGTH);\n" +
                    "sprintf(s, \"%d%s\", i, s1);\n" +
                    "return s;\n" +
                    "}" +
                    "\n" +
                    "char* concat(char *str1, char *str2)\n" +
                    "{\n" +
                    "char *s=malloc(sizeof(char) * LENGTH);\n" +
                    "strcat(s, str1);\n" +
                    "strcat(s, str2);\n" +
                    "\n" +
                    "return s;\n" +
                    "}" +
                    "\n" +
                    "char* copy_string(char *str1)\n" +
                    "{\n" +
                    "char *s=malloc(sizeof(char) * LENGTH);\n" +
                    "strcat(s, str1);\n" +
                    "\n" +
                    "return s;\n" +
                    "}";

    private TreeSymbolTable symbolTable;
    private StringBuilder mallocString;

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
                .append(declarations).append('\n').append(DEFAULT_FUNCTION).append("\n").append(functions).append('\n').
                append(main).append('\n');

        return programBuilder.toString();
    }

    @Override
    public Object visit(MainOp Main) throws Exception {

        StringBuilder mainBuilder = new StringBuilder();
        String declarations = "";
        String statements = "";

        mainBuilder.append("int main(void){\n\t");
        for (VarDeclOp var: Main.getListVarDecl()) {
            declarations = (String) var.accept(this);
            mainBuilder.append(declarations).append("\n\t");
        }
        for (Statement stm : Main.getListStatement()) {
            statements = (String) stm.accept(this);
            mainBuilder.append(statements);
            if(stm instanceof CallFunOp ) mainBuilder.append(";");
            mainBuilder.append("\n\t");
        }

        mainBuilder.append(" return 0;\n}\n");

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
            return varDeclBuilder.append(type).append(listInit).toString();
        }
        else // case typeOp is var
        {
            listInit = (String) VarDecl.getListInit().accept(this);
            return varDeclBuilder.append(listInit).toString();
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
    public Object visit(String_Const Const)
    {

        return "\"" + String.valueOf(Const.getStringConst()).substring(1,Const.getStringConst().length()-1) + "\"" ;
    }

    @Override
    public Object visit(IdListInitOp IdListInit) throws Exception
    {
        StringBuilder listInit = new StringBuilder();
        mallocString = new StringBuilder();

        int i=1;
        String expression = "";

        for (String key: IdListInit.getList().keySet())
        {
            String id = key;
            ReturnType varType = symbolTable.lookup(IdListInit.getAttachScope(), key).getReturnType();


            if(varType==ReturnType.STRING)
            {
                if(symbolTable.isGlobal(IdListInit.getAttachScope()))
                {
                    id =  key + "[LENGTH]";
                }
                else
                {
                    id = "*" + key + "=malloc(sizeof(char)*LENGTH)";
                }
            }
            if(varType!=ReturnType.STRING) {
                if (IdListInit.getList().get(key) != null) {
                    expression = (String) IdListInit.getList().get(key).accept(this);
                    listInit.append(" ").append(id).append("=").append(expression);
                } else {
                    listInit.append(" ").append(id);
                }
            }
            else
            {
                listInit.append(" ").append(id);
                if (IdListInit.getList().get(key) != null) {
                    if(symbolTable.isGlobal(IdListInit.getAttachScope()))
                    {
                        listInit.append("=").append(expression);
                    }
                    else
                    {
                    mallocString.append("strcpy(").append(key).append(",").append(expression + ");\n");
                    }
                }
            }
            if (i != IdListInit.getList().size()) listInit.append(",").append(" ");

            i++;

        }
        listInit.append(";");
        return listInit.append(mallocString).toString();
    }

    @Override
    public Object visit(IdListInitObblOp IdListInitObbl)
    {
        StringBuilder listInit = new StringBuilder();
        int i=1;
        String expression = "";
        mallocString = new StringBuilder();

        for (String key: IdListInitObbl.getList().keySet())
        {

            String id = key;
            String type =convertReturnType_ToTypeC(symbolTable.lookup(IdListInitObbl.getAttachScope(), key).getReturnType());
            expression = (String) IdListInitObbl.getList().get(key).accept(this);
            if(type.equals("char")) {
                if(symbolTable.isGlobal(IdListInitObbl.getAttachScope()))
                {
                    id =  key + "[LENGTH]";
                    listInit.append(type).append(" ").append(id).append("=").append(expression);
                }
                else
                {
                    id = "*" + key + "=malloc(sizeof(char)*LENGTH)";
                    listInit.append(type).append(" ").append(id);
                    mallocString.append("strcpy(").append(key).append(",").append(expression + ");\n");
                }
            }
            else
            {
            listInit.append(type).append(" ").append(id).append("=").append(expression);
            }
            listInit.append(";").append("\n");

            i++;

        }


        return listInit.append(mallocString).toString();

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

        if(return_type.equals("char")){
            return_type =  "char*";
        }

        paramDecl = (String) Fun.getListParam().accept(this);

        funBuilder.append(return_type).append(" ").append(Fun.getId()).append("(").append(paramDecl).append(")\n{\n\t");


        for (VarDeclOp var: Fun.getListVarDecl()) {
            declarations = (String) var.accept(this);
            funBuilder.append(declarations).append("\n\t");
        }
        for (Statement stm : Fun.getListStatement()) {
            statements = (String) stm.accept(this);
            funBuilder.append(statements);
            if(stm instanceof CallFunOp ) funBuilder.append(";");
            funBuilder.append("\n\t");
        }

       return funBuilder.append("\n}\n").toString();
    }

    @Override
    public Object visit(ParDeclListOp ParamDeclList) throws Exception
    {
        StringBuilder parBuilder = new StringBuilder();
        int i=1;
        Collections.reverse(ParamDeclList.getListParDecl());
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

        expression = (String) IfStat.getExpression().accept(this);
        ifBuilder.append("if(").append(expression).append("){\n\t\t");

        // if scope is global ...
        for (VarDeclOp var: IfStat.getListVarDeclOp())
        {
            declarations = (String) var.accept(this);
            ifBuilder.append(declarations).append("\n").append("\t\t");
        }


        for (Statement stm:IfStat.getListStatement() )
        {
            statements = (String) stm.accept(this);
            ifBuilder.append(statements);
            if(stm instanceof CallFunOp ) ifBuilder.append(";");
            ifBuilder.append("\n\t\t");
        }

        if(!IfStat.getElseStatOp().isEmpty()) elseOp= (String) IfStat.getElseStatOp().accept(this);
        else elseOp="";

        return ifBuilder.append("\n\t}\n").append(elseOp).toString();

    }

    @Override
    public Object visit(ElseStatOp Else) throws Exception
    {
        StringBuilder elseBuilder = new StringBuilder();
        String declarations = "";
        String statements = "";

        elseBuilder.append("else{\n\t");

        for (VarDeclOp var: Else.getListVar())
        {
            declarations = (String) var.accept(this);
            elseBuilder.append(declarations).append("\n").append("\t\t");
        }

        for (Statement stm:Else.getListStat() )
        {
            statements = (String) stm.accept(this);
            elseBuilder.append(statements);
            if(stm instanceof CallFunOp ) elseBuilder.append(";");
            elseBuilder.append("\n\t\t");
        }
        elseBuilder.append("\n\t}\n");
        return elseBuilder.toString();
    }

    @Override
    public Object visit(WhileStatOp WhileStat) throws Exception
    {
        StringBuilder whileBuilder = new StringBuilder();
        String declarations = "";
        String expression = "";
        String statements = "";

        whileBuilder.append("while(");
        expression = (String) WhileStat.getExpression().accept(this);
        whileBuilder.append(expression).append("){\n\t\t");

        for (VarDeclOp var: WhileStat.getVarDeclListOp())
        {
            declarations = (String) var.accept(this);
            whileBuilder.append(declarations).append("\n").append("\t\t");
        }



        for (Statement stm:WhileStat.getListStatement() )
        {
            statements = (String) stm.accept(this);
            whileBuilder.append(statements);
            if(stm instanceof CallFunOp ) whileBuilder.append(";");
            whileBuilder.append("\n\t\t");
        }

        return whileBuilder.append("\n\t}\n").toString();
    }

    @Override
    public Object visit(ReadStatOp ReadStat) throws Exception {

        StringBuilder builder = new StringBuilder();
        String expr = "\"\"", idList = "";
        String listFormat = "";
        if (ReadStat.getExpression() != null){
            expr = (String) ReadStat.getExpression().accept(this);
        }


        int i = 1;
        for (ID id  : ReadStat.getListId()){

            listFormat += getFormatReturnType(id.getNodeType());

            if(id.getNodeType()==ReturnType.STRING)idList +=  (String) id.accept(this) ;
            else idList += "&" + ((String) id.accept(this)) ;

            if(ReadStat.getListId().size() != i) idList += ",";
            i++;
        }
        idList.replace("*","");
        builder.append("printf(").append(expr).append(");").append("\n\t");
        return builder.append("scanf(").append( "\"" + listFormat + "\"").append(",").append(idList).append(");").toString();
    }

    @Override
    public Object visit(WriteStatOp WriteStat) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        String expr = (String) WriteStat.getExpression().accept(this);
        ReturnType type = ((SyntaxtNode)WriteStat.getExpression()).getNodeType();
        String typeString = getFormatReturnType(type);
        String write = "";


        if(WriteStat.getType()==WriteStatOp.TypeWrite.WRITE)
        {
            builder.append("printf(").append("\""+typeString+"\",").append(expr).append(");");
        }
        else if(WriteStat.getType()==WriteStatOp.TypeWrite.WRITELN)
        {
            builder.append("printf(").append("\""+typeString+"\\n\",").append(expr).append(");");
        }
        else if(WriteStat.getType()==WriteStatOp.TypeWrite.WRITET)
        {
            builder.append("printf(").append("\""+typeString+"\\t\",").append(expr).append(");");
        }
        else
        { //case WriteB
            builder.append("printf(").append("\""+typeString+"\\b\",").append(expr).append(");");
        }
        return builder.toString();
    }

    @Override
    public Object visit(AssignStatOp AssignStat) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        String id = (String) AssignStat.getId().accept(this);

        String expression = (String) AssignStat.getExpression().accept(this);
        if(AssignStat.getId().getNodeType() == ReturnType.STRING) return builder.append("strcpy(").append(id).append(",").append(expression).append(");").toString();
        else return builder.append(id).append(" = ").append(expression).append(";").toString();

    }

    @Override
    public Object visit(CallFunOp CallFun) throws Exception
    {
        StringBuilder callBuilder = new StringBuilder();
        String id = (String) CallFun.getId().accept(this);
        String listExpression = "";
        int i=1;
        callBuilder.append(id).append("(");
        for (Expression e:CallFun.getListExpression())
        {
            listExpression = (String) e.accept(this);

            if(e instanceof ID)
            {
                if(((ID) e).getNodeType() == ReturnType.STRING && !((ID) e).isOutPar())  callBuilder.append("copy_string(" + listExpression + ")");
                else if(((ID) e).getNodeType() == ReturnType.STRING)  callBuilder.append(listExpression);
                else if(((ID) e).isOutPar()) callBuilder.append("&").append(listExpression);
                else callBuilder.append(listExpression);
            }
            else callBuilder.append(listExpression);

            if( i != CallFun.getListExpression().size()) callBuilder.append(",");
            i++;
        }

        return callBuilder.append(")").toString();
    }

    @Override
    public Object visit(ID id) throws Exception
    {
        IKind kind = symbolTable.lookup(id.getAttachScope(), id.getName());
        if(kind instanceof VarKind)
        {
            if(((VarKind) kind).getPartype()== ParType.OUT && id.getNodeType()!= ReturnType.STRING)
                return "*"+id.getName();
        }
        return id.getName();
    }

    @Override
    public Object visit(BinaryOperation binaryOperation) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        String exp1 = (String) binaryOperation.getE1().accept(this);
        String exp2 = (String) binaryOperation.getE2().accept(this);

        ReturnType typeExp1 = ((SyntaxtNode)binaryOperation.getE1()).getNodeType();
        ReturnType typeExp2 = ((SyntaxtNode)binaryOperation.getE2()).getNodeType();

        BinaryOperation.BinaryOperationType operationType = binaryOperation.getType();

        if( operationType == BinaryOperation.BinaryOperationType.AddOp )
        {
            builder.append(exp1).append("+").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.MulOp)
        {
            builder.append(exp1).append("*").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.DiffOp)
        {
            builder.append(exp1).append("-").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.DivOp)
        {
            builder.append(exp1).append("/").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.LTOp)
        {
            builder.append(exp1).append("<").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.LEOp)
        {
            builder.append(exp1).append("<=").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.GEOp)
        {
            builder.append(exp1).append(">=").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.GTOp)
        {
            builder.append(exp1).append(">").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.EQOp)
        {
            if(typeExp1==ReturnType.STRING && typeExp2==ReturnType.STRING )
            builder.append("(strcmp(").append(exp1).append(",").append(exp2).append(")==0)");
            else builder.append(exp1).append("==").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.AndOp)
        {
            builder.append(exp1).append("&&").append(exp2);
        }
        else if(operationType == BinaryOperation.BinaryOperationType.OrOp)
        {
            builder.append(exp1).append("||").append(exp2);
        }
        else if(operationType== BinaryOperation.BinaryOperationType.StrCatOp)
        {
            if(typeExp1== ReturnType.STRING && typeExp2== ReturnType.STRING)
            {
                builder.append("concat(" + exp1 +"," + exp2 + ")");
            }
            if(typeExp1== ReturnType.STRING && typeExp2== ReturnType.INT)
            {
                builder.append("concatCI(" + exp1 +"," + exp2 + ")");
            }
            if(typeExp1== ReturnType.INT && typeExp2== ReturnType.STRING)
            {
                builder.append("concatIC(" + exp1 +"," + exp2 + ")");
            }
            if(typeExp1== ReturnType.REAL && typeExp2== ReturnType.STRING)
            {
                builder.append("concatDC(" + exp1 +"," + exp2 + ")");
            }
            if(typeExp1== ReturnType.STRING && typeExp2== ReturnType.REAL)
            {
                builder.append("concatCD(" + exp1 +"," + exp2 + ")");
            }

        }
        else if(operationType== BinaryOperation.BinaryOperationType.DivIntOp )
        {
            builder.append(exp1).append("%").append(exp2);
        }
        else if(operationType== BinaryOperation.BinaryOperationType.PowOp)
        {
            builder.append("pow(").append(exp1).append(",").append(exp2).append(")");
        }
        return builder.toString();
    }

    @Override
    public Object visit(UnaryOperation unaryOperation) throws Exception
    {
        StringBuilder builder = new StringBuilder();
        String expression = (String) unaryOperation.getE1().accept(this);

        if(unaryOperation.getType() == UnaryOperation.UnaryOperationType.NotOp){
            builder.append("!(").append(expression).append(")");
        }
        else if(unaryOperation.getType() == UnaryOperation.UnaryOperationType.UminusOp) {
            builder.append("-(").append(expression).append(")");
        }
        return builder.toString();
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

    public static String getFormatReturnType(ReturnType rt){

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
            type="%lf";
        }

        return type;

    }

}
