import Node.ProgramOp;
import Semantic.Enum.ReturnType;
import Semantic.TreeSymbolTable;
import Visitor.TypeCheckingVisitor;
import Visitor.SymbolTableVisitor;
import Visitor.XMLGenerator;

import org.w3c.dom.Document;


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

        TypeCheckingVisitor semanticVisitor = new TypeCheckingVisitor(symbolTable);
        ReturnType returnType = (ReturnType) prog.accept(semanticVisitor);

    }

}