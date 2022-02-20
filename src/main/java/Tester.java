import Node.ProgramOp;
import Semantic.Enum.ReturnType;
import Semantic.TreeSymbolTable;
import Visitor.CodeVisitor;
import Visitor.TypeCheckingVisitor;
import Visitor.SymbolTableVisitor;
import Visitor.XMLGenerator;

import org.w3c.dom.Document;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Tester
{
    public static void main(String[] args) throws Exception {

        FileReader inFile = new FileReader(args[0]);
        Lexer lexer = new Lexer(inFile);            //Lexer
        parser p = new parser(lexer);               //Parser

        ProgramOp prog =  (ProgramOp) p.parse().value;  //Create Syntaxt Tree

        /* Print xml syntaxt tree */
        XMLGenerator xml = new XMLGenerator();
        Document doc = (Document) prog.accept(xml);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(System.getProperty("user.dir")+"\\albero_sintattico.xml"));
        transformer.transform(domSource, streamResult);

        /* Semantic area */

        /* Generate Symbol table */

        SymbolTableVisitor visitor = new SymbolTableVisitor();
        TreeSymbolTable symbolTable = (TreeSymbolTable) prog.accept(visitor);

        symbolTable.stampTree(); //stamp symbol table

        /* Type Checking */
        TypeCheckingVisitor semanticVisitor = new TypeCheckingVisitor(symbolTable);
        ReturnType returnType = (ReturnType) prog.accept(semanticVisitor);

        /* Generate intermediate code */
        CodeVisitor codeVisitor = new CodeVisitor(symbolTable);
        
        File generatedFile = new File(System.getProperty("user.dir")+"\\file.c");
        FileWriter pw = new FileWriter(generatedFile);

        pw.write((String) codeVisitor.visit(prog));
        pw.close();
        
    }

}