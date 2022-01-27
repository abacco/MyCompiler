import Node.ProgramOp;
import Semantic.Enum.ReturnType;
import Semantic.TreeSymbolTable;
import Visitor.SemanticVisitor;
import Visitor.SymbolTableVisitor;
import Visitor.XMLGenerator;

import java_cup.runtime.Symbol;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Tester
{
    public static void main(String[] args) throws Exception {

        FileReader inFile = new FileReader(args[0]);
        Lexer lexer = new Lexer(inFile);
        parser p = new parser(lexer);
        /*
        Symbol token;
        while ((token = lexer.next_token())!= null) {
            if (token.sym == sym.EOF) {
                break;
            }

            System.out.println(sym.terminalNames[token.sym] + " " + token.sym);
        }
        */



        ProgramOp prog =  (ProgramOp) p.debug_parse().value;

        XMLGenerator xml = new XMLGenerator();
        Document doc = (Document) prog.accept(xml);
        /*
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource((Node) doc);
        StreamResult streamResult = new StreamResult(new File(System.getProperty("user.dir")+"\\albero_sintattico.xml"));
        transformer.transform(domSource, streamResult);
        */
        SymbolTableVisitor visitor = new SymbolTableVisitor();
        TreeSymbolTable symbolTable = (TreeSymbolTable) prog.accept(visitor);

        symbolTable.stampTree();

        SemanticVisitor semanticVisitor = new SemanticVisitor(symbolTable);
        ReturnType returnType = (ReturnType) prog.accept(semanticVisitor);

    }

}