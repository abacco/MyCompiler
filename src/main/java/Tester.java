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

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(System.getProperty("user.dir")+"\\albero_sintattico.xml"));
        transformer.transform(domSource, streamResult);

        SymbolTableVisitor visitor = new SymbolTableVisitor();
        TreeSymbolTable symbolTable = (TreeSymbolTable) prog.accept(visitor);

        symbolTable.stampTree();

        TypeCheckingVisitor semanticVisitor = new TypeCheckingVisitor(symbolTable);
        ReturnType returnType = (ReturnType) prog.accept(semanticVisitor);

        CodeVisitor codeVisitor = new CodeVisitor(symbolTable);
        
        File generatedFile = new File(System.getProperty("user.dir")+"\\file.c");
        FileWriter pw = new FileWriter(generatedFile);

        pw.write((String) codeVisitor.visit(prog));
        pw.close();
        
    }

}