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
        /* GET FILE NAME */
        int indexSeparator = args[0].toString().lastIndexOf("\\");
        if(indexSeparator==-1)indexSeparator = args[0].toString().lastIndexOf("/");

        String fileName = args[0].toString().substring(indexSeparator + 1);
        int indexDot = fileName.toString().lastIndexOf(".");
        if(indexDot != -1) {
            fileName = fileName.toString().substring(0, indexDot);
        }

                                                                                /* Lexical and Syntaxt area */
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


                                                                        /* Generate intermediate code in directory test_files\c_out */

        CodeVisitor codeVisitor = new CodeVisitor(symbolTable);
        File directory = new File(System.getProperty("user.dir")+File.separator+"test_files"+File.separator+"c_out");
        if(!directory.exists()) directory.mkdir();

        File generatedFile = new File(System.getProperty("user.dir")+File.separator+"test_files"+File.separator+"c_out"+ File.separator + fileName + ".c");

        FileWriter pw = new FileWriter(generatedFile);

        pw.write((String) codeVisitor.visit(prog));
        pw.close();
        
    }

}