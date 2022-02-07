package Visitor;

import Node.Constant.Boolean_Const;
import Node.Constant.Integer_Const;
import Node.Constant.Real_Const;
import Node.Constant.String_Const;
import Node.Declaration.*;
import Node.Expression.BinaryOperation;
import Node.Expression.CallFunOp;
import Node.Expression.Expression;
import Node.Expression.UnaryOperation;
import Node.*;
import Node.Statement.*;
import Semantic.*;
import Semantic.Enum.ParType;
import Semantic.Enum.ReturnType;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.Collections;

public class TypeCheckingVisitor implements Visitor {
    private final ISymbolTable symbolTable;

    public TypeCheckingVisitor(ISymbolTable symbolTable){
        this.symbolTable=symbolTable;
    }

    @Override
    public Object visit(ProgramOp Program) throws Exception {
        Collections.reverse(Program.getListVarDecl());

        for (VarDeclOp el: Program.getListVarDecl()){
            ReturnType e = (ReturnType) el.accept(this);
            if(e != ReturnType.NO_TYPE) throw new Exception("Le dichiarazioni di variabili non devono ritornare alcun tipo");
        }
        for (FunOp funOp: Program.getListFun()){
            ReturnType e = (ReturnType) funOp.accept(this);
            if(e != ReturnType.NO_TYPE) throw new Exception("Le dichiarazioni di funzioni non devono ritornare alcun tipo");
        }
        ReturnType main = (ReturnType) Program.getMainOp().accept(this);

        Program.setNodeType(ReturnType.NO_TYPE);
        return Program.getNodeType();
    }

    @Override
    public Object visit(MainOp Main) throws Exception {

        for (VarDeclOp el: Main.getListVarDecl()){
            ReturnType e = (ReturnType) el.accept(this);
            if(e != ReturnType.NO_TYPE) throw new Exception("Le dichiarazioni di variabili non devono ritornare alcun tipo");
        }
        for (Statement el: Main.getListStatement()){
            ReturnType e = (ReturnType) el.accept(this);
            if(e != ReturnType.NO_TYPE) throw new Exception("Gli statement non devono ritornare alcun tipo");
        }

        Main.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(VarDeclOp VarDecl) throws Exception {

        if(VarDecl.getType()!=null) VarDecl.getType().accept(this);
        ReturnType type = (ReturnType) VarDecl.getListInit().accept(this);
        if(type!= ReturnType.NO_TYPE) throw new Exception("Le dichiarazioni di variabili non devono ritornare alcun tipo");

        VarDecl.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(TypeOp Type) {

        Type.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(IdListInitOp IdListInit) throws Exception {
        //verifichiamo che non ci siano id già definiti nello stesso scope
        for (String key: IdListInit.getList().keySet())
        {
            ReturnType varType = symbolTable.lookup(IdListInit.getAttachScope(), key).getReturnType();
            if(IdListInit.getList().get(key)!=null)
            {
                ReturnType constant = (ReturnType) IdListInit.getList().get(key).accept(this);
                if(varType!=constant) throw new Exception("Non è possibile assegnare alla variabile " + key + " di tipo " + varType + " il valore di un espressione di tipo " + constant);
            }
        }

        IdListInit.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(IdListInitObblOp IdListInitObbl) throws Exception {
        //verifichiamo che non ci siano id già definiti nello stesso scope
        for (String key: IdListInitObbl.getList().keySet())
        {
            ReturnType varType = symbolTable.lookup(IdListInitObbl.getAttachScope(),key).getReturnType();
            if(IdListInitObbl.getList().get(key)!=null)
            {
                ReturnType constant = (ReturnType) IdListInitObbl.getList().get(key).accept(this);
                if(varType!=constant) throw new Exception("Non è possibile assegnare alla variabile " + key + " di tipo " + varType + " un espressione di tipo " + constant);
            }
        }

        IdListInitObbl.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(Boolean_Const bconst) {
        bconst.setNodeType(ReturnType.BOOLEAN);

        return bconst.getNodeType();
    }

    @Override
    public Object visit(Integer_Const Const) {
        Const.setNodeType(ReturnType.INT);

        return Const.getNodeType();
    }

    @Override
    public Object visit(Real_Const Const) {
        Const.setNodeType(ReturnType.REAL);

        return Const.getNodeType();
    }

    @Override
    public Object visit(String_Const Const) {

        Const.setNodeType(ReturnType.STRING);

        return Const.getNodeType();
    }

    @Override
    public Object visit(FunOp Fun) throws Exception {
        FunctionKind funkind = (FunctionKind) symbolTable.lookup(Fun.getAttachScope(), Fun.getId().getName());

        ReturnType ret = (ReturnType) Fun.getListParam().accept(this);
        if(ret != ReturnType.NO_TYPE) throw new Exception("La lista di parametri devono ritornare alcun tipo");

        for (VarDeclOp el: Fun.getListVarDecl()){
            ret = (ReturnType) el.accept(this);
            if(ret != ReturnType.NO_TYPE) throw new Exception("La dichiarazione di una variabile non deve ritornare alcun tipo");
        }
        for (Statement el: Fun.getListStatement()){
            ret = (ReturnType) el.accept(this);
            if(el instanceof ReturnExpOp){
                ReturnExpOp returnOp = (ReturnExpOp) el;
                ReturnType expressionType = ((SyntaxtNode)returnOp.getExpression()).getNodeType();
                if(expressionType!= funkind.getReturnType()) throw new Exception("La funzione " + Fun.getId().getName() + " ritorno un valore di tipo " + funkind.getReturnType() +
                                                                                " ma nello statement return viene ritornato un valore di tipo " + expressionType);
            }
            if(ret != ReturnType.NO_TYPE) throw new Exception("Gli statement non devono ritornare alcun tipo");
        }


        Fun.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(ParDeclListOp ParamDeclList) throws Exception {

        for (ParDeclOp el: ParamDeclList.getListParDecl()){
            ReturnType e = (ReturnType) el.accept(this);
            if(e != ReturnType.NO_TYPE) throw new Exception("La dichiarazione di un parametro non deve tornare alcun tipo");
        }

        ParamDeclList.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(ParDeclOp ParDecl) throws Exception {

        String id = ParDecl.getId();
        if(symbolTable.prob(id)) throw new Exception("Il parametro " + ParDecl.getId() + " è stato già dichiarato all'interno della funzione");

        ParDecl.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(IfStatOp IfStat) throws Exception {
        /* Γ ` e : boolean Γ ` block : notype
        Γ ` if e then block end if : notype */

        for (VarDeclOp el: IfStat.getListVarDeclOp()){
            ReturnType typeV = (ReturnType) el.accept(this);
        }

        ReturnType typeE = (ReturnType) IfStat.getExpression().accept(this);
        if(typeE!=ReturnType.BOOLEAN) throw new Exception("L'If statement può valutare solo espressioni bool");

        for (Statement el: IfStat.getListStatement()){
            ReturnType typeS = (ReturnType) el.accept(this);
            if(typeS != ReturnType.NO_TYPE) throw new Exception("Gli statement non devono ritornare alcun tipo");
        }
        if(!IfStat.getElseStatOp().isEmpty()) IfStat.getElseStatOp().accept(this);



        IfStat.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(ElseStatOp Else) throws Exception {

        for (VarDeclOp el: Else.getListVar()){
            ReturnType typeV = (ReturnType) el.accept(this);

        }
        for (Statement el: Else.getListStat()){
            ReturnType typeS = (ReturnType) el.accept(this);
            if(typeS != ReturnType.NO_TYPE) throw new Exception("Gli statement non devono ritornare alcun tipo");
        }

        Else.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(WhileStatOp WhileStat) throws Exception {

        ReturnType typeE = (ReturnType) WhileStat.getExpression().accept(this);
        if(typeE!=ReturnType.BOOLEAN) throw new Exception("While statement può valutare solo espressioni bool");

        for (VarDeclOp el: WhileStat.getVarDeclListOp()){
            ReturnType e = (ReturnType) el.accept(this);

        }
        for (Statement el: WhileStat.getListStatement()){
            ReturnType typeS = (ReturnType) el.accept(this);
            if(typeS != ReturnType.NO_TYPE) throw new Exception("Gli statement non devono ritornare alcun tipo");
        }

        WhileStat.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(ReadStatOp ReadStat) throws Exception {
        ReturnType typeE=null;
        if(ReadStat.getExpression() != null){
            typeE= (ReturnType) ReadStat.getExpression().accept(this);
            if(typeE != ReturnType.STRING) throw new Exception("La funzione read accetta un'espressione di tipo STRING, mentre l'espressione è di tipo " + typeE);
        }

        for (ID el: ReadStat.getListId()){
            ReturnType typeID= (ReturnType) el.accept(this);
        }

        ReadStat.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(WriteStatOp WriteStat) throws Exception {

        ReturnType typeE= (ReturnType) WriteStat.getExpression().accept(this);

        WriteStat.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(AssignStatOp AssignStat) throws Exception {
        /* T(id) = τ  T ` e : τ
        Γ ` id := e : notype */

        ReturnType typeId = (ReturnType) AssignStat.getId().accept(this);
        ReturnType typeExpression = (ReturnType) AssignStat.getExpression().accept(this);


        int row = CompatibilityType.getIndexFor(typeId);
        int col = CompatibilityType.getIndexFor(typeExpression);
        ReturnType nodeType = CompatibilityType.ASSIGNOP[row][col];
        if(nodeType == ReturnType.UNDEFINED) throw new Exception("Non è possibile assegnare alla variabile " + AssignStat.getId().getName() + " di tipo " + typeId + " il valore di un espressione di tipo " + typeExpression);

        AssignStat.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(CallFunOp CallFun) throws Exception {


        FunctionKind funKind = (FunctionKind) symbolTable.lookup(CallFun.getAttachScope(),CallFun.getId().getName());

        if(CallFun.getListExpression().size() != funKind.getListParm().size()) throw new Exception("Stai provando a chiamare la funzione " + CallFun.getId().getName() + " passando più argomenti dei parametri definiti nella funzione.");

            for(int i=0; i<funKind.getListParm().size(); i++)
            {
                ParamType p = funKind.getListParm().get(i);
                Expression el = CallFun.getListExpression().get(i);
                ReturnType returnTypeExpression = (ReturnType) el.accept(this);
                if(el instanceof ID)
                {
                    ID id = (ID) el;
                    if(p.getParType()== ParType.OUT && !id.isOutPar()) throw new Exception("Il parametro " + (i+1) + " della funzione " + CallFun.getId().getName() + " è un puntatore (out), ma l'argomento " + id.getName() + " non viene passato per riferimento (@)" );
                    if(p.getParType()== ParType.IN && id.isOutPar())    throw new Exception("Il parametro " + (i+1) + " della funzione " + CallFun.getId().getName() + " non è un puntatore, ma l'argomento " + id.getName() + " viene passato per riferimento (@)" );
                    if(p.getVarType()!=returnTypeExpression) throw new Exception("Il parametro " + (i+1) + " della funzione " + CallFun.getId().getName() + " è di tipo " + p.getVarType() + ", ma l'argomento " + id.getName() + " è di tipo " + returnTypeExpression);
                }
                else
                {
                    if(p.getVarType()!=returnTypeExpression) throw new Exception("Il parametro " + (i+1) + " della funzione " + CallFun.getId().getName() + " è di tipo " + p.getVarType() + ", ma il valore che gli viene passato è di tipo " + returnTypeExpression);
                }
            }

            CallFun.setNodeType(funKind.getReturnType());
            return CallFun.getNodeType();

    }

    @Override
    public Object visit(ID id) {

        IKind kind = symbolTable.lookup(id.getAttachScope(), id.getName());
        id.setNodeType(kind.getReturnType());
        return kind.getReturnType();

    }

    @Override
    public Object visit(BinaryOperation binaryOperation) throws Exception {

        binaryOperation.getE1().accept(this);
        binaryOperation.getE2().accept(this);
        ReturnType typeOP1 = ((SyntaxtNode) binaryOperation.getE1()).getNodeType();
        ReturnType typeOP2 = ((SyntaxtNode) binaryOperation.getE2()).getNodeType();

        BinaryOperation.BinaryOperationType operationType = binaryOperation.getType();

        if(operationType == BinaryOperation.BinaryOperationType.AddOp || operationType == BinaryOperation.BinaryOperationType.MulOp
           || operationType == BinaryOperation.BinaryOperationType.DiffOp || operationType == BinaryOperation.BinaryOperationType.DivOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.Arithmetic_operation[row][col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("Gli operatori aritmetici non sono applicabili fra un tipo " + typeOP1 + " e " + typeOP2);
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType == BinaryOperation.BinaryOperationType.LTOp
                || operationType == BinaryOperation.BinaryOperationType.LEOp
                || operationType == BinaryOperation.BinaryOperationType.GEOp
                || operationType == BinaryOperation.BinaryOperationType.GTOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.RELOP[row][col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("Gli operatori relazionali non sono applicabili fra un tipo " + typeOP1 + " e " + typeOP2);
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType == BinaryOperation.BinaryOperationType.EQOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.EQRELOP[row][col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("L'operatore =  non è applicabile fra un tipo " + typeOP1 + " e " + typeOP2);
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType == BinaryOperation.BinaryOperationType.AndOp || operationType == BinaryOperation.BinaryOperationType.OrOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.AND_OR[row][col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("Gli operatori and e or non sono applicabili fra un tipo " + typeOP1 + " e " + typeOP2);
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType== BinaryOperation.BinaryOperationType.StrCatOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.STR_CONCAT[row][col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("L'operatore di concatenazione stringhe (&) non è applicabile fra un tipo " + typeOP1 + " e " + typeOP2);
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType== BinaryOperation.BinaryOperationType.DivIntOp || operationType== BinaryOperation.BinaryOperationType.PowOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.DIVINT_POW[row][col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("L'operatore div non è applicabile fra un tipo " + typeOP1 + " e " + typeOP2);
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else return ReturnType.UNDEFINED;
    }

    @Override
    public Object visit(UnaryOperation unaryOperation) throws Exception {
        unaryOperation.getE1().accept(this);
        ReturnType type = ((SyntaxtNode) unaryOperation.getE1()).getNodeType();

        if(unaryOperation.getType() == UnaryOperation.UnaryOperationType.NotOp){
            int col = CompatibilityType.getIndexFor(type);
            ReturnType nodeType = CompatibilityType.NOT[col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("L'operatore unario not non è applicabile ad un tipo " + type );
            unaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(unaryOperation.getType() == UnaryOperation.UnaryOperationType.UminusOp) {
            int col = CompatibilityType.getIndexFor(type);
            ReturnType nodeType = CompatibilityType.MINUS[col];
            if(nodeType == ReturnType.UNDEFINED) throw new Exception("L'operatore unario - non è applicabile ad un tipo " + type );
            unaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else return ReturnType.UNDEFINED;

    }

    @Override
    public Object visit(ReturnExpOp returnExpOp) throws Exception {
        ReturnType typeE= (ReturnType) returnExpOp.getExpression().accept(this);

        returnExpOp.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }
}
