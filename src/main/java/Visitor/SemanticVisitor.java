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

public class SemanticVisitor implements Visitor {
    private final ISymbolTable symbolTable;

    public SemanticVisitor(ISymbolTable symbolTable){
        this.symbolTable=symbolTable;
    }

    @Override
    public Object visit(ProgramOp Program) throws Exception {

        for (VarDeclOp el: Program.getListVarDecl()){
            ReturnType e = (ReturnType) el.accept(this);
        }
        for (FunOp funOp: Program.getListFun()){
            ReturnType e = (ReturnType) funOp.accept(this);
        }
        ReturnType main = (ReturnType) Program.getMainOp().accept(this);

        Program.setNodeType(ReturnType.NO_TYPE);
        return Program.getNodeType();
    }

    @Override
    public Object visit(MainOp Main) throws Exception {

        for (VarDeclOp el: Main.getListVarDecl()){
            ReturnType e = (ReturnType) el.accept(this);

        }
        for (Statement el: Main.getListStatement()){
            ReturnType e = (ReturnType) el.accept(this);
            if(e != ReturnType.NO_TYPE) return 0; //ERROR
        }

        Main.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(VarDeclOp VarDecl) throws Exception {

        ReturnType type = (ReturnType) VarDecl.getListInit().accept(this);
        if(type!= ReturnType.NO_TYPE) return 0; //ERROR

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
                if(varType!=constant) return 0; //ERROR
            }
        }

        IdListInit.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(IdListInitObblOp IdListInitObbl) {
        //verifichiamo che non ci siano id già definiti nello stesso scope
        for (String key: IdListInitObbl.getList().keySet())
        {
            ReturnType varType = symbolTable.lookup(IdListInitObbl.getAttachScope(),key).getReturnType();
            if(IdListInitObbl.getList().get(key)!=null)
            {
                ReturnType constant = (ReturnType) IdListInitObbl.getList().get(key).accept(this);
                if(varType!=constant) return 0; //ERROR
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
        if(ret != ReturnType.NO_TYPE) return 0; //ERROR

        for (VarDeclOp el: Fun.getListVarDecl()){
            ret = (ReturnType) el.accept(this);
            if(ret != ReturnType.NO_TYPE) return 0; //ERROR
        }
        for (Statement el: Fun.getListStatement()){
            ret = (ReturnType) el.accept(this);
            if(el instanceof ReturnExpOp){
                ReturnExpOp returnOp = (ReturnExpOp) el;
                ReturnType expressionType = ((SyntaxtNode)returnOp.getExpression()).getNodeType();
                if(expressionType!= funkind.getReturnType()) return 0; //ERROR
            }
            if(ret != ReturnType.NO_TYPE) return 0; //ERROR
        }


        Fun.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(ParDeclListOp ParamDeclList) throws Exception {

        for (ParDeclOp el: ParamDeclList.getListParDecl()){
            ReturnType e = (ReturnType) el.accept(this);
            if(e != ReturnType.NO_TYPE) return 0; //Error
        }

        ParamDeclList.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(ParDeclOp ParDecl) {

        String id = ParDecl.getId();
        if(symbolTable.prob(id)) return 0; //ERROR

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
        if(typeE!=ReturnType.BOOLEAN) return 0; //ERROR

        for (Statement el: IfStat.getListStatement()){
            ReturnType typeS = (ReturnType) el.accept(this);
            if(typeS != ReturnType.NO_TYPE) return 0; //ERROR
        }

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
            if(typeS != ReturnType.NO_TYPE) return 0; //ERROR
        }

        Else.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(WhileStatOp WhileStat) throws Exception {

        ReturnType typeE = (ReturnType) WhileStat.getExpression().accept(this);
        if(typeE!=ReturnType.BOOLEAN) return 0; //ERROR

        for (VarDeclOp el: WhileStat.getVarDeclListOp()){
            ReturnType e = (ReturnType) el.accept(this);

        }
        for (Statement el: WhileStat.getListStatement()){
            ReturnType typeS = (ReturnType) el.accept(this);
            if(typeS != ReturnType.NO_TYPE) return 0; //ERROR
        }

        WhileStat.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(ReadStatOp ReadStat) throws Exception {
        ReturnType typeE=null;
        if(ReadStat.getExpression() != null) typeE= (ReturnType) ReadStat.getExpression().accept(this);

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
        if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE

        AssignStat.setNodeType(ReturnType.NO_TYPE);
        return ReturnType.NO_TYPE;
    }

    @Override
    public Object visit(CallFunOp CallFun) throws Exception {


        FunctionKind funKind = (FunctionKind) symbolTable.lookup(CallFun.getAttachScope(),CallFun.getId().getName());

        if(CallFun.getListExpression().size() != funKind.getListParm().size()) return 0; //ERROR

            for(int i=0; i<funKind.getListParm().size(); i++)
            {
                ParamType p = funKind.getListParm().get(0);
                Expression el = CallFun.getListExpression().get(0);
                ReturnType returnTypeExpression = (ReturnType) el.accept(this);
                if(el instanceof ID)
                {
                    ID id = (ID) el;
                    if(p.getParType()== ParType.OUT && !id.isOutPar()) return 0; //Error
                    if(p.getParType()== ParType.IN && id.isOutPar())    return 0; //Error
                    if(p.getVarType()!=returnTypeExpression) return 0; //Error
                }
                else
                {
                    if(p.getVarType()!=returnTypeExpression) return 0; //Error
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
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
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
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType == BinaryOperation.BinaryOperationType.EQOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.EQRELOP[row][col];
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType == BinaryOperation.BinaryOperationType.AndOp || operationType == BinaryOperation.BinaryOperationType.OrOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.AND_OR[row][col];
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType== BinaryOperation.BinaryOperationType.StrCatOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.STR_CONCAT[row][col];
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
            binaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(operationType== BinaryOperation.BinaryOperationType.DivIntOp || operationType== BinaryOperation.BinaryOperationType.PowOp)
        {
            int row = CompatibilityType.getIndexFor(typeOP1);
            int col = CompatibilityType.getIndexFor(typeOP2);
            ReturnType nodeType = CompatibilityType.DIVINT_POW[row][col];
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
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
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
            unaryOperation.setNodeType(nodeType);
            return nodeType;
        }
        else if(unaryOperation.getType() == UnaryOperation.UnaryOperationType.UminusOp) {
            int col = CompatibilityType.getIndexFor(type);
            ReturnType nodeType = CompatibilityType.MINUS[col];
            if(nodeType == ReturnType.UNDEFINED) return 0; //ERROR CORREGGERE
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
