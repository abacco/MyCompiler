package Visitor;

import Node.*;
import Node.Constant.Boolean_Const;
import Node.Constant.Integer_Const;
import Node.Constant.Real_Const;
import Node.Constant.String_Const;
import Node.Declaration.*;
import Node.Expression.*;
import Node.Statement.*;
import Semantic.*;
import Semantic.Enum.Kind;
import Semantic.IKind;
import Semantic.Enum.ParType;
import Semantic.Enum.ReturnType;
import org.w3c.dom.Element;

import java.util.ArrayList;

public class SymbolTableVisitor implements Visitor{
    private final ISymbolTable symbolTable = new TreeSymbolTable("Global");

    @Override
    public Object visit(ProgramOp Program) throws Exception {
        /*Global symbol table*/

        /*Inseriamo le variabili nella tabella globale*/
        for (VarDeclOp el: Program.getListVarDecl()){
            el.accept(this);
        }
        /* inseriamo le dichiarazioni delle funzioni nella tabella globale */
        for (FunOp funOp: Program.getListFun()){

            if(symbolTable.prob(funOp.getId().getName())) return 0; //Error
            ReturnType funReturnType = ReturnType.NO_TYPE;
            if(funOp.getType()!=null) funReturnType = (ReturnType) funOp.getType().accept(this);
            ParType parType=null;
            ArrayList<ParamType> listParamType= new ArrayList<ParamType>();

            for (ParDeclOp par : funOp.getListParam().getListParDecl()) {
                ReturnType returnType = (ReturnType) par.getTypeOp().accept(this);
                if(par.getType()==ParDeclOp.ParType.IN) parType=ParType.IN;
                if(par.getType()==ParDeclOp.ParType.OUT) parType=ParType.OUT;

                listParamType.add(0, new ParamType(returnType, parType));
            }

            symbolTable.add(funOp.getId().getName(), new FunctionKind(funReturnType, listParamType) );
        }

        for (FunOp funOp: Program.getListFun()){
            funOp.accept(this);
        }

        Program.getMainOp().accept(this);
        Program.attachScope(symbolTable.getCurrentScope());
        return symbolTable;
    }

    @Override
    public Object visit(MainOp Main) throws Exception {

        symbolTable.enterScope("Main");
        for (VarDeclOp el: Main.getListVarDecl()){
            el.accept(this);

        }
        for (Statement el: Main.getListStatement()){
            el.accept(this);

        }
        Main.attachScope(symbolTable.getCurrentScope());
        symbolTable.exitScope();

        return null;
    }

    @Override
    public Object visit(VarDeclOp VarDecl) throws Exception {

        if(VarDecl.getType()!=null)
        {
            IdListInitOp idListInit = (IdListInitOp) VarDecl.getListInit();
            ReturnType typeDeclaration = (ReturnType) VarDecl.getType().accept(this);

            for (String key: idListInit.getList().keySet())
            {
                if(symbolTable.prob(key)) throw new Exception("La variabile " + key + " è già stata dichiarata"); //ERROR;
                Expression exp = idListInit.getList().get(key);
                if(exp!=null)exp.accept(this);
                symbolTable.add(key, new VarKind(typeDeclaration));
            }

        }
        else
        {
            IdListInitObblOp idListInit = (IdListInitObblOp) VarDecl.getListInit();
            for (String key: idListInit.getList().keySet())
            {
                if(symbolTable.prob(key)) throw new Exception("La variabile " + key + " è già stata dichiarata"); //ERROR;
                ReturnType constantType = (ReturnType) idListInit.getList().get(key).accept(this);
                symbolTable.add(key, new VarKind(constantType));
            }

        }
        VarDecl.getListInit().accept(this );
        VarDecl.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(TypeOp Type) {
        Type.attachScope(symbolTable.getCurrentScope());
        if(Type.getType() == TypeOp.Type.BOOL)
        {
            return ReturnType.BOOLEAN;
        }
        else if(Type.getType() == TypeOp.Type.REAL)
        {
            return ReturnType.REAL;
        }
        else if(Type.getType() == TypeOp.Type.STRING)
        {
            return ReturnType.STRING;
        }
        else {
            return ReturnType.INT;
        }

    }

    @Override
    public Object visit(IdListInitOp IdListInit)
    {
        IdListInit.attachScope(symbolTable.getCurrentScope());

        return null;
    }

    @Override
    public Object visit(IdListInitObblOp IdListInitObbl) {
        IdListInitObbl.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(Boolean_Const bconst) {

        return ReturnType.BOOLEAN;
    }

    @Override
    public Object visit(Integer_Const Const) {


        return ReturnType.INT;
    }

    @Override
    public Object visit(Real_Const Const) {


        return ReturnType.REAL;
    }

    @Override
    public Object visit(String_Const Const) {

        return ReturnType.STRING;
    }

    @Override
    public Object visit(FunOp Fun) throws Exception {
        symbolTable.enterScope("FunOp - " + Fun.getId().getName());

        Fun.getListParam().accept(this);

        for (VarDeclOp el: Fun.getListVarDecl()){
            el.accept(this);
        }

        for (Statement el: Fun.getListStatement()){
            ReturnType e = (ReturnType) el.accept(this);
        }

        Fun.attachScope(symbolTable.getCurrentScope());
        symbolTable.exitScope();

        return null;
    }

    @Override
    public Object visit(ParDeclListOp ParamDeclList) throws Exception {

        for (ParDeclOp el: ParamDeclList.getListParDecl()){
            el.accept(this);
        }
        ParamDeclList.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(ParDeclOp ParDecl) throws Exception {
        VarKind var;
        String id = ParDecl.getId();
        if(symbolTable.prob(id)) throw new Exception("Parametro con il nome" + ParDecl.getId() + " già dichiarato"); //ERROR

        if(ParDecl.getType() == ParDeclOp.ParType.IN) {
            var = new VarKind((ReturnType) ParDecl.getTypeOp().accept(this), ParType.IN);
        }
        else {
            var = new VarKind((ReturnType) ParDecl.getTypeOp().accept(this),ParType.OUT);
        }
        symbolTable.add(id, var);
        ParDecl.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(IfStatOp IfStat) throws Exception {
        symbolTable.enterScope("IfStatOp");

        IfStat.getExpression().accept(this);

        for (VarDeclOp el: IfStat.getListVarDeclOp()){
            el.accept(this);
        }

        for (Statement el: IfStat.getListStatement()){
            el.accept(this);
        }
        IfStat.attachScope(symbolTable.getCurrentScope());
        symbolTable.exitScope();

        IfStat.getElseStatOp().accept(this);
        return null;
    }

    @Override
    public Object visit(ElseStatOp Else) throws Exception {

            symbolTable.enterScope("ElseStatOp");

            for (VarDeclOp el : Else.getListVar()) {
                el.accept(this);

            }
            for (Statement el : Else.getListStat()) {
                el.accept(this);
            }
            Else.attachScope(symbolTable.getCurrentScope());
            symbolTable.exitScope();

        return null;
    }

    @Override
    public Object visit(WhileStatOp WhileStat) throws Exception {
        symbolTable.enterScope("WhileStatOp");
        WhileStat.getExpression().accept(this);

        for (VarDeclOp el: WhileStat.getVarDeclListOp()){
            el.accept(this);

        }
        for (Statement el: WhileStat.getListStatement()){
            el.accept(this);
        }
        WhileStat.attachScope(symbolTable.getCurrentScope());
        symbolTable.exitScope();
        return null;
    }

    @Override
    public Object visit(ReadStatOp ReadStat) throws Exception {
        if(ReadStat.getExpression()!=null) ReadStat.getExpression().accept(this);

        for (ID el: ReadStat.getListId()){
            el.accept(this);
            if(symbolTable.lookup(el.getName())==null)  throw new Exception("La variabile " + el.getName() + "non è dichiarata"); //ERROR;
        }
        ReadStat.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(WriteStatOp WriteStat) throws Exception {

        WriteStat.getExpression().accept(this);
        WriteStat.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(AssignStatOp AssignStat) throws Exception {
        AssignStat.getId().accept(this);
        AssignStat.getExpression().accept(this);
        AssignStat.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(CallFunOp CallFun) throws Exception {

        IKind kind = symbolTable.lookup(CallFun.getId().getName());
        if(kind== null) throw new Exception("Non è dichiarata alcuna funzione " + CallFun.getId().getName()); //ERROR;
        if(kind.getKind() != Kind.FUN) throw new Exception("Non è dichiarata alcuna funzione " + CallFun.getId().getName()); //ERROR;
        CallFun.getId().accept(this);
        for (Expression el: CallFun.getListExpression()){
            el.accept(this);
        }
        CallFun.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(ID id) throws Exception {

        IKind kind = symbolTable.lookup(id.getName());
        if(kind == null) throw new Exception("La variabile " + id.getName() + "non è dichiarata"); //ERROR;
        id.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(BinaryOperation binaryOperation) throws Exception {
        binaryOperation.getE1().accept(this);
        binaryOperation.getE2().accept(this);
        binaryOperation.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(UnaryOperation unaryOperation) throws Exception {
        unaryOperation.getE1().accept(this);
        unaryOperation.attachScope(symbolTable.getCurrentScope());
        return null;
    }

    @Override
    public Object visit(ReturnExpOp returnExpOp) throws Exception {
        returnExpOp.getExpression().accept(this);
        returnExpOp.attachScope(symbolTable.getCurrentScope());

        return null;

    }

    @Override
    public Object visit(ExpressionPar expressionPar) throws Exception {
        expressionPar.getExpression().accept(this);
        expressionPar.attachScope(symbolTable.getCurrentScope());
        return null;
    }
}
