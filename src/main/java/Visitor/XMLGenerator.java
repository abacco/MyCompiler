package Visitor;

import Node.Constant.*;
import Node.Declaration.*;
import Node.Expression.*;
import Node.FunOp;
import Node.ID;
import Node.MainOp;
import Node.ProgramOp;
import Node.Statement.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLGenerator  implements Visitor{

    private Document document;

    public XMLGenerator() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
    }

    @Override
    public Object visit(ProgramOp program) throws Exception {
        Element programElement = document.createElement("ProgramOp");

        for (VarDeclOp el: program.getListVarDecl()){
            Element e = (Element) el.accept(this);
            programElement.appendChild(e);
        }
        for (FunOp funOp: program.getListFun()){
            Element e = (Element) funOp.accept(this);
            programElement.appendChild(e);
        }

        Element main = (Element) program.getMainOp().accept(this);
        programElement.appendChild(main);
        document.appendChild(programElement);

        return document;
    }

    @Override
    public Object visit(MainOp main) throws Exception {
        Element mainElement = document.createElement("MainOp");

        for (VarDeclOp el: main.getListVarDecl()){
            Element e = (Element) el.accept(this);
            mainElement.appendChild(e);
        }
        for (Statement el: main.getListStatement()){
            Element e = (Element) el.accept(this);
            mainElement.appendChild(e);
        }

        return mainElement;
    }

    @Override
    public Object visit(VarDeclOp varDecl) throws Exception {

        Element varElement = document.createElement("VarDeclOp");
        Element listIn = (Element) varDecl.getListInit().accept(this);
        if(varDecl.getType()!= null){
        Element type = (Element) varDecl.getType().accept(this);
        varElement.appendChild(type);
        }
        varElement.appendChild(listIn);


        return varElement;
    }

    @Override
    public Object visit(TypeOp type) {
        Element typeElement = document.createElement("TypeOp");
        typeElement.setAttribute("type", type.getType().toString());
        return typeElement;

    }

    @Override
    public Object visit(IdListInitOp idListInit) throws Exception {
        Element idListElement = document.createElement("IdListInitOp");

        for (String key: idListInit.getList().keySet()){
            if(idListInit.getList().get(key)!=null) {
                Element expression = (Element) idListInit.getList().get(key).accept(this);
                idListElement.appendChild(expression);
            }
            Element id = document.createElement("ID");
            id.setAttribute("name", key);
            idListElement.appendChild(id);
        }

        return idListElement;
    }

    @Override
    public Object visit(IdListInitObblOp idListInitObbl) {
        Element idListElement = document.createElement("IdListInitObbl");


        for (String key: idListInitObbl.getList().keySet()){
            if(idListInitObbl.getList().get(key)!=null) {
                Element constant = (Element) idListInitObbl.getList().get(key).accept(this);
                idListElement.appendChild(constant);
            }

            Element id = document.createElement("ID");
            id.setAttribute("name", key);
            idListElement.appendChild(id);

        }

        return idListElement;
    }

    @Override
    public Object visit(Boolean_Const bconst) {
        Element constElement = document.createElement("Boolean_Const");
        constElement.setAttribute("value", bconst.getValue());

        return constElement;
    }

    public Object visit(Integer_Const integer_const) {
        Element constElement = document.createElement("Integer_Const");
        constElement.setAttribute("value", integer_const.getValue() + "");
        return constElement;
    }

    public Object visit(Real_Const real_const) {
        Element constElement = document.createElement("Real_Const");
        constElement.setAttribute("value", real_const.getValue() + "");

        return constElement;
    }

    public Object visit(String_Const string_const) {
        Element constElement = document.createElement("String_Const");
        constElement.setAttribute("value", string_const.getStringConst() );

        return constElement;
    }

    @Override
    public Object visit(FunOp fun) throws Exception {
        Element funElement = document.createElement("FunOp");
        if(fun.getType()!=null)
        {
            Element e = (Element) fun.getType().accept(this);
            funElement.appendChild(e);
        }

        for (VarDeclOp el: fun.getListVarDecl()){
            Element e = (Element) el.accept(this);
            funElement.appendChild(e);
        }
        for (Statement el: fun.getListStatement()){
            Element e = (Element) el.accept(this);
            funElement.appendChild(e);
        }
        Element listParam = (Element) fun.getListParam().accept(this);
        funElement.appendChild(listParam);

        return funElement;

    }

    @Override
    public Object visit(ParDeclListOp paramDeclList) throws Exception {
        Element parListElement = document.createElement("ParDeclListOp");

        for (ParDeclOp el: paramDeclList.getListParDecl()){
            Element e = (Element) el.accept(this);
            parListElement.appendChild(e);
        }

        return parListElement;
    }

    @Override
    public Object visit(ParDeclOp parDecl) {
        Element parElement = document.createElement("ParDeclOp_" + parDecl.getType().toString());
        Element id = document.createElement("ID");
        id.setAttribute("name", parDecl.getId());

        parElement.appendChild(id);

        return parElement;
    }

    /*                              Statement                   */
    @Override
    public Object visit(AssignStatOp stat) throws Exception {
        Element assignElement = document.createElement("AssignStatOp");
        Element id = document.createElement("ID");
        id.setAttribute("name", stat.getId().getName());

        Element expression = (Element) stat.getExpression().accept(this);

        assignElement.appendChild(id);
        assignElement.appendChild(expression);

        return assignElement;
    }

    @Override
    public Object visit(IfStatOp ifStat) throws Exception {
        Element ifElement = document.createElement("IfStatOp");

        Element expression = (Element) ifStat.getExpression().accept(this);
        ifElement.appendChild(expression);

        for (VarDeclOp el: ifStat.getListVarDeclOp()){
            Element e = (Element) el.accept(this);
            ifElement.appendChild(e);
        }
        for (Statement el: ifStat.getListStatement()){
            Element e = (Element) el.accept(this);
            ifElement.appendChild(e);
        }
        if(!ifStat.getElseStatOp().isEmpty())
        {
            Element elseElem = (Element) ifStat.getElseStatOp().accept(this);
            ifElement.appendChild(elseElem);
        }
        return ifElement;
    }

    @Override
    public Object visit(ElseStatOp elseStat) throws Exception {
        Element elseElement = document.createElement("ElseStatOp");

        for (VarDeclOp el: elseStat.getListVar()){
            Element e = (Element) el.accept(this);
            elseElement.appendChild(e);
        }
        for (Statement el: elseStat.getListStat()){
            Element e = (Element) el.accept(this);
            elseElement.appendChild(e);
        }
        return elseElement;
    }

    @Override
    public Object visit(WhileStatOp whileStat) throws Exception {
        Element whileElement = document.createElement("WhileStatOp");

        Element expression = (Element) whileStat.getExpression().accept(this);
        whileElement.appendChild(expression);

        for (VarDeclOp el: whileStat.getVarDeclListOp()){
            Element e = (Element) el.accept(this);
            whileElement.appendChild(e);
        }
        for (Statement el: whileStat.getListStatement()){
            Element e = (Element) el.accept(this);
            whileElement.appendChild(e);
        }
        return whileElement;
    }

    @Override
    public Object visit(ReadStatOp readStat) throws Exception {
        Element read = document.createElement("ReadStatOp");
        for (ID el: readStat.getListId()){
            Element e = (Element) el.accept(this);
            read.appendChild(e);
        }
        if(readStat.getExpression()!= null){
        Element expression = (Element) readStat.getExpression().accept(this);
        read.appendChild(expression);
        }

        return read;
    }

    @Override
    public Object visit(WriteStatOp writeStat) throws Exception {
        Element writeElement = document.createElement(writeStat.getType().toString());
        Element expression = (Element) writeStat.getExpression().accept(this);
        writeElement.appendChild(expression);

        return writeElement;
    }

    @Override
    public Object visit(CallFunOp callFun) throws Exception {
        Element writeElement = document.createElement("CallFunOp");
        Element id = document.createElement("ID");
        id.setAttribute("name", callFun.getId().getName());

        for (Expression el: callFun.getListExpression()){
            Element e = (Element) el.accept(this);
            writeElement.appendChild(e);
        }

        writeElement.appendChild(id);
        return writeElement;
    }


    @Override
    public Object visit(ID id) {
        Element idElement = document.createElement("ID");
        idElement.setAttribute("name", id.getName());

        return idElement;
    }

    @Override
    public Object visit(BinaryOperation binaryOperation) throws Exception {
        Element binaryOp = document.createElement(binaryOperation.getType().toString());
        Element exp1 = (Element) binaryOperation.getE1().accept(this);
        Element exp2 = (Element) binaryOperation.getE2().accept(this);
        binaryOp.appendChild(exp1);
        binaryOp.appendChild(exp2);
        return binaryOp;
    }

    @Override
    public Object visit(UnaryOperation unaryOperation) throws Exception {
        Element  unaryOp = document.createElement(unaryOperation.getType().toString());
        Element exp1 = (Element) unaryOperation.getE1().accept(this);

        unaryOp.appendChild(exp1);

        return unaryOp;
    }

    @Override
    public Object visit(ReturnExpOp returnExpOp) throws Exception {
        Element returnElement = document.createElement("ReturnExpOp");
        Element expression = (Element) returnExpOp.getExpression().accept(this);
        returnElement.appendChild(expression);
        return returnElement;
    }

    @Override
    public Object visit(ExpressionPar expressionPar) throws Exception {
        Element returnElement = document.createElement("ExpParOp");
        Element expression = (Element) expressionPar.getExpression().accept(this);
        returnElement.appendChild(expression);
        return returnElement;
    }

    /**/

}
