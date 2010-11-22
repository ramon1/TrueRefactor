package truerefactor.old;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LiteralExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.SuperMemberAccessExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import truerefactor.old.graph.ClassNode;
import truerefactor.old.graph.CodeGraph;
import truerefactor.old.graph.Edge;
import truerefactor.old.graph.FieldNode;
import truerefactor.old.graph.MethodNode;
import truerefactor.old.graph.Node;
import truerefactor.old.graph.StatementNode;

/**
 * ParseGraph - Class designed to input source code files, and using a parser
 * add items to a provided graph to ensure completeness of the graph.
 * 
 * @author Isaac Griffith
 */
public class JavaParseGraph {

    /** The current file */
    protected File file;
    /** The graph of the code */
    protected CodeGraph graph;

    /**
     * @param graph
     * @param basePath
     */
    public void process(CodeGraph graph, String basePath)
    {
        this.graph = graph;

        List<File> files = this.generateSourceFileList(basePath);

        for (File file : files)
        {
            parseAndBuildFromFile(file);
        }
    }

    /**
     * @param source
     */
    private void parseAndBuildFromFile(File source)
    {
        file = source;

        try
        {
            CompilationUnit cu = JavaParser.parse(source);

            List<TypeDeclaration> types = cu.getTypes();
            for (TypeDeclaration type : types)
            {
                if (type instanceof ClassOrInterfaceDeclaration)
                {
                    ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) type;

                    String className = cid.getName();
                    ClassNode cNode = getClassNode(className);

                    processTypeDeclaration(cid, cNode);
                }
            }

            buildConnections(cu);
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * @param cu
     */
    private void buildConnections(CompilationUnit cu)
    {
        List<TypeDeclaration> types = cu.getTypes();
        for (TypeDeclaration type : types)
        {
            if (type instanceof ClassOrInterfaceDeclaration)
            {
                ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) type;
                ClassNode cNode = getClassNode(cid.getName());

                List<ClassOrInterfaceType> lstClasses = cid.getImplements();
                if (lstClasses != null)
                {
                    for (ClassOrInterfaceType cit : cid.getImplements())
                    {
                        String impl = cit.getName();
                        if (cNode != null)
                        {
                            graph.insert(new Edge(cNode, getClassNode(impl), Edge.Type.REALIZATION));
                        }
                    }
                }

                cid.getJavaDoc();

                lstClasses = cid.getExtends();
                if (lstClasses != null)
                {
                    for (ClassOrInterfaceType cit : cid.getExtends())
                    {
                        String ext = cit.getName();
                        if (cNode != null)
                        {
                            graph.insert(new Edge(cNode, getClassNode(ext), Edge.Type.INHERITANCE));
                        }
                    }
                }

                processTypeDeclaration(cid, cNode);
            }
        }
    }

    /**
     * @param className
     * @return
     */
    private ClassNode getClassNode(String className)
    {
        return graph.getClass(className);
    }

    /**
     * @param ClassName
     * @param methodName
     * @param params
     * @return
     */
    private MethodNode getMethodNode(String ClassName, String methodName, List<String> params)
    {
        ClassNode cNode = graph.getClass(ClassName);
        return graph.getMethod(cNode, methodName, params);
    }

    /**
     * @param basePath
     * @return
     */
    private List<File> generateSourceFileList(String basePath)
    {
        List<File> files = new ArrayList<File>();
        File base = new File(basePath);

        if (base.isDirectory())
            files.addAll(recursiveDirListing(base));

        return files;
    }

    /**
     * @param file
     * @return
     */
    private List<File> recursiveDirListing(File file)
    {
        List<File> files = new ArrayList<File>();
        if (file.isDirectory())
        {
            for (File f : file.listFiles())
            {
                if (f.isDirectory())
                {
                    files.addAll(recursiveDirListing(f));
                }
                else
                {
                    files.add(f);
                }
            }
        }

        return files;
    }

    /**
     * @param methodBody
     * @param node
     */
    private void handleMethodContents(BlockStmt methodBody, MethodNode node)
    {
        List<Statement> statements = methodBody.getStmts();
        if (statements != null)
        {
            for (Statement stmt : statements)
            {
                processStatement(stmt, node);
            }
        }
    }

    /**
     * @param stmt
     * @param node
     */
    @SuppressWarnings("unused")
    private void handleBodyStatements(Statement stmt, StatementNode node)
    {
        processStatement(stmt, node);
    }

    /**
     * @param expr
     * @param parent
     */
    @SuppressWarnings("unused")
    private void handleExpressionContents(Expression expr, Node parent)
    {
        if (expr instanceof AnnotationExpr)
        {
            AnnotationExpr annotate = (AnnotationExpr) expr;

            handleAnnotation(annotate, parent);

        }
        else if (expr instanceof ArrayAccessExpr)
        {
            ArrayAccessExpr array = (ArrayAccessExpr) expr;

        }
        else if (expr instanceof ArrayCreationExpr)
        {
            ArrayCreationExpr array = (ArrayCreationExpr) expr;

        }
        else if (expr instanceof ArrayInitializerExpr)
        {
            ArrayInitializerExpr array = (ArrayInitializerExpr) expr;

        }
        else if (expr instanceof AssignExpr)
        {
            AssignExpr assign = (AssignExpr) expr;

        }
        else if (expr instanceof BinaryExpr)
        {
            BinaryExpr binary = (BinaryExpr) expr;

        }
        else if (expr instanceof CastExpr)
        {
            CastExpr cast = (CastExpr) expr;

        }
        else if (expr instanceof ClassExpr)
        {
            ClassExpr classEx = (ClassExpr) expr;

        }
        else if (expr instanceof ConditionalExpr)
        {
            ConditionalExpr cond = (ConditionalExpr) expr;

        }
        else if (expr instanceof EnclosedExpr)
        {
            EnclosedExpr enclose = (EnclosedExpr) expr;

        }
        else if (expr instanceof FieldAccessExpr)
        {
            FieldAccessExpr field = (FieldAccessExpr) expr;
            // field.get
            //
            // MethodNode mNode = (MethodNode) parent;
            // FieldNode fNode =
            // mNode.getParentClass().getAttribute(field.getField());
            //
            // StatementNode sNode = mNode.addStatement(code);

            // graph.connect(new Use(graph), sNode, fNode);
        }
        else if (expr instanceof InstanceOfExpr)
        {
            InstanceOfExpr ioEx = (InstanceOfExpr) expr;

        }
        else if (expr instanceof LiteralExpr)
        {
            LiteralExpr litEx = (LiteralExpr) expr;
            handleLiteralExpression(litEx, parent);

        }
        else if (expr instanceof MethodCallExpr)
        {
            String className = "";

            MethodCallExpr methodCall = (MethodCallExpr) expr;
            if (methodCall.getScope() instanceof ClassExpr)
                className = ((ClassOrInterfaceType) ((ClassExpr) methodCall.getScope()).getType()).getName();

            MethodNode callNode = getMethodNode(className, methodCall.getName(),
                    getParamTypes(methodCall.getTypeArgs()));
            MethodNode mNode = (MethodNode) parent;
            // StatementNode sNode = mNode.addStatement(code);
            //
            // graph.connect(new Use(graph), sNode, callNode);
        }
        else if (expr instanceof NameExpr)
        {
            NameExpr nameEx = (NameExpr) expr;

        }
        else if (expr instanceof ObjectCreationExpr)
        {
            ObjectCreationExpr objCreate = (ObjectCreationExpr) expr;

            ClassOrInterfaceType cit = objCreate.getType();
            MethodNode mNode = (MethodNode) parent;
            // StatementNode sNode = mNode.addStatement(code);

            ClassNode cNode = getClassNode(cit.getName());

            // graph.connect(new Use(graph), sNode, cNode);
        }
        else if (expr instanceof QualifiedNameExpr)
        {
            QualifiedNameExpr qual = (QualifiedNameExpr) expr;

        }
        else if (expr instanceof SuperExpr)
        {
            SuperExpr superEx = (SuperExpr) expr;

        }
        else if (expr instanceof SuperMemberAccessExpr)
        {
            SuperMemberAccessExpr superMemeber = (SuperMemberAccessExpr) expr;

        }
        else if (expr instanceof ThisExpr)
        {
            ThisExpr thisEx = (ThisExpr) expr;

        }
        else if (expr instanceof UnaryExpr)
        {
            UnaryExpr unary = (UnaryExpr) expr;

        }
        else if (expr instanceof VariableDeclarationExpr)
        {
            VariableDeclarationExpr varDec = (VariableDeclarationExpr) expr;

            List<VariableDeclarator> lstVars = varDec.getVars();
            for (VariableDeclarator declarator : lstVars)
            {
                declarator.getId().getName();

            }
        }
    }

    /**
     * @param expr
     * @param parent
     */
    @SuppressWarnings("unused")
    private void handleLiteralExpression(LiteralExpr expr, Node parent)
    {
        if (expr instanceof BooleanLiteralExpr)
        {
            BooleanLiteralExpr bool = (BooleanLiteralExpr) expr;

        }
        else if (expr instanceof StringLiteralExpr)
        {
            StringLiteralExpr stringLit = (StringLiteralExpr) expr;

        }
        else if (expr instanceof NullLiteralExpr)
        {
            NullLiteralExpr nullLit = (NullLiteralExpr) expr;

        }
    }

    /**
     * @param expr
     * @param parent
     */
    @SuppressWarnings("unused")
    private void handleStirngLiteralExpression(StringLiteralExpr expr, Node parent)
    {
        if (expr instanceof LongLiteralExpr)
        {
            LongLiteralExpr longLit = (LongLiteralExpr) expr;

        }
        else if (expr instanceof LongLiteralMinValueExpr)
        {
            LongLiteralMinValueExpr minLong = (LongLiteralMinValueExpr) expr;

        }
        else if (expr instanceof CharLiteralExpr)
        {

        }
        else if (expr instanceof IntegerLiteralExpr)
        {
            IntegerLiteralExpr intLit = (IntegerLiteralExpr) expr;

        }
        else if (expr instanceof IntegerLiteralMinValueExpr)
        {
            IntegerLiteralMinValueExpr minInt = (IntegerLiteralMinValueExpr) expr;

        }
        else if (expr instanceof DoubleLiteralExpr)
        {
            DoubleLiteralExpr doulbeLit = (DoubleLiteralExpr) expr;

        }
    }

    /**
     * @param expr
     * @param parent
     */
    @SuppressWarnings("unused")
    private void handleAnnotation(AnnotationExpr expr, Node parent)
    {
        if (expr instanceof MarkerAnnotationExpr)
        {
            MarkerAnnotationExpr annotate = (MarkerAnnotationExpr) expr;

        }
        else if (expr instanceof NormalAnnotationExpr)
        {
            NormalAnnotationExpr annotate = (NormalAnnotationExpr) expr;

        }
        else if (expr instanceof SingleMemberAnnotationExpr)
        {
            SingleMemberAnnotationExpr single = (SingleMemberAnnotationExpr) expr;

        }
    }

    /**
     * @param typeDec
     * @param node
     */
    @SuppressWarnings("unused")
    private void handleTypeDeclaration(TypeDeclaration typeDec, StatementNode node)
    {
        List<BodyDeclaration> decs = typeDec.getMembers();

        for (BodyDeclaration type : decs)
        {
            if (type instanceof ClassOrInterfaceDeclaration)
            {
                ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) type;

                String className = cid.getName();
                ClassNode cNode = getClassNode(className);
                if (cNode != null)
                {
                    List<BodyDeclaration> body = cid.getMembers();
                    for (BodyDeclaration bd : body)
                    {
                        if (bd instanceof FieldDeclaration)
                        { // Handle Fields

                            FieldDeclaration field = (FieldDeclaration) bd;

                            List<VariableDeclarator> variables = field.getVariables();
                            for (VariableDeclarator var : variables)
                            {
                                addAttribute(
                                        cNode,
                                        var.getId().getName(),
                                        siphonCode(file, var.getBeginLine(), var.getBeginColumn(), var.getEndLine(),
                                                var.getEndColumn()));
                            }
                        }
                        else if (bd instanceof MethodDeclaration)
                        { // Handle
                          // Methods
                            MethodDeclaration method = (MethodDeclaration) bd;

                            MethodNode mNode = addMethod(
                                    cNode,
                                    method.getName(),
                                    getParams(method.getParameters()),
                                    siphonCode(file, method.getBeginLine(), method.getBeginColumn(),
                                            method.getEndLine(), method.getEndColumn()));

                            // Handle the method body
                            BlockStmt methodBody = method.getBody();
                            handleMethodContents(methodBody, mNode);
                        }
                        else if (bd instanceof ConstructorDeclaration)
                        { // Handle
                          // Constructors
                            ConstructorDeclaration construct = (ConstructorDeclaration) bd;
                            BlockStmt methodBody = construct.getBlock();

                            MethodNode mNode = addMethod(
                                    cNode,
                                    construct.getName(),
                                    getParams(construct.getParameters()),
                                    siphonCode(file, construct.getBeginLine(), construct.getBeginColumn(),
                                            construct.getEndLine(), construct.getEndColumn()));

                            handleMethodContents(methodBody, mNode);
                        }
                    }
                }
            }
        }

    }

    /**
     * @param file
     * @param beginLine
     * @param beginCol
     * @param endLine
     * @param endCol
     * @return
     */
    private String siphonCode(File file, int beginLine, int beginCol, int endLine, int endCol)
    {
        StringBuilder sbCode = new StringBuilder();
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = null;
            for (int i = beginLine; i <= endLine && (line = br.readLine()) != null; i++)
            {
                // Verify that these substring calls are inclusive to the column
                // number
                if (i == beginLine && beginLine == endLine)
                {
                    line = line.substring(beginCol, endCol + 1);
                    sbCode.append(line);
                    break;
                }

                if (i == beginLine && i < endLine)
                {
                    line = line.substring(beginCol - 1);
                    sbCode.append(line);
                }
                else if (i == endLine && i > beginLine)
                {
                    line = line.substring(0, endCol);
                    sbCode.append(line);
                }
                else if (i > beginLine && i < endLine)
                {
                    sbCode.append(line);
                }
            }

            br.close();
            fr.close();
        }
        catch (FileNotFoundException fnfex)
        {

        }
        catch (IOException iox)
        {

        }

        return sbCode.toString();
    }

    // private static Node buildForCFG(ForeachStmt forStmt, MethodNode mNode) {
    // VariableDeclarationExpr vardec = forStmt.getVariable();
    // Expression iter = forStmt.getIterable();
    //
    // Statement body = forStmt.getBody();
    // if (body instanceof BlockStmt) {
    // BlockStmt block = (BlockStmt)body;
    // block.getStmts();
    // }
    // }

    public MethodNode addMethod(ClassNode cNode, String methodName, List<String> params, String code)
    {
        MethodNode mNode = new MethodNode(methodName, params);
        graph.insert(new Edge(cNode, mNode, Edge.Type.BODY));

        return mNode;
    }

    /**
     * @param cNode
     * @param fieldName
     * @param code
     * @return
     */
    public FieldNode addAttribute(ClassNode cNode, String fieldName, String code)
    {
        FieldNode fNode = new FieldNode(fieldName);
        graph.insert(new Edge(cNode, fNode, Edge.Type.BODY));

        return fNode;
    }

    public StatementNode addStatement(Node node, String type, String code)
    {
        if (node instanceof MethodNode)
        {
            MethodNode mNode = (MethodNode) node;
            return addStatement(mNode, type, code);
        }
        else if (node instanceof StatementNode)
        {
            StatementNode sNode = (StatementNode) node;
            return addStatement(sNode, type, code);
        }
        else
            return null;
    }

    /**
     * @param mNode
     * @param type
     * @param code
     * @return
     */
    private StatementNode addStatement(MethodNode mNode, String type, String code)
    {
        StatementNode sNode = new StatementNode(code);
        graph.insert(new Edge(mNode, sNode, Edge.Type.BODY));

        return sNode;
    }

    /**
     * @param sNode
     * @param type
     * @param code
     * @return
     */
    private StatementNode addStatement(StatementNode sNode, String type, String code)
    {
        StatementNode sNodeDest = new StatementNode(code);
        graph.insert(new Edge(sNode, sNodeDest, Edge.Type.BODY));

        return sNodeDest;
    }

    /**
     * @param params
     * @return
     */
    public List<String> getParams(List<Parameter> params)
    {
        List<String> paramTypes = new ArrayList<String>();
        for (int i = 0; i < params.size(); i++)
        {
            paramTypes.add(params.get(i).getType().toString());
        }

        return paramTypes;
    }

    /**
     * @param params
     * @return
     */
    public List<String> getParamTypes(List<Type> params)
    {
        List<String> paramTypes = new ArrayList<String>();
        for (int i = 0; i < params.size(); i++)
        {
            paramTypes.add(params.get(i).toString());
        }

        return paramTypes;
    }

    @SuppressWarnings("unused")
    public void processStatement(Statement stmt, Node node)
    {
        if (stmt instanceof BlockStmt)
        {
            BlockStmt block = (BlockStmt) stmt;

            // List<Statement> stmts = block.getStmts();
            // StatementNode sNode = node.addStatement("block",
            // siphonCode(file, block.getBeginLine(),
            // block.getBeginColumn(), block.getEndLine(),
            // block.getEndColumn()));
            // for(Statement s : stmts) {
            // handleBodyStatements(s, sNode);
            // }

        }
        else if (stmt instanceof DoStmt)
        {
            DoStmt doStmt = (DoStmt) stmt;

            StatementNode sNode = addStatement(
                    node,
                    "do",
                    siphonCode(file, doStmt.getBeginLine(), doStmt.getBeginColumn(), doStmt.getEndLine(),
                            doStmt.getEndColumn()));
            // handleBodyStatements(doStmt.getBody(), sNode);
            // handleExpressionContents(doStmt.getCondition(), sNode);

        }
        else if (stmt instanceof ExpressionStmt)
        {
            ExpressionStmt exprStmt = (ExpressionStmt) stmt;
            // StatementNode sNode = node.addStatement("expression",
            // siphonCode(file, exprStmt.getBeginLine(),
            // exprStmt.getBeginColumn(), exprStmt.getEndLine(),
            // exprStmt.getEndColumn()));

            // Expression expr = exprStmt.getExpression();

            // handleExpressionContents(expr, node);
        }
        else if (stmt instanceof ForeachStmt)
        {
            ForeachStmt foreach = (ForeachStmt) stmt;

            StatementNode sNode = addStatement(
                    node,
                    "for",
                    siphonCode(file, foreach.getBeginLine(), foreach.getBeginColumn(), foreach.getEndLine(),
                            foreach.getEndColumn()));

            // handleExpressionContents(foreach.getIterable(), sNode);
            // handleExpressionContents(foreach.getVariable(), sNode);
            // handleBodyStatements(foreach.getBody(), sNode);

        }
        else if (stmt instanceof IfStmt)
        {
            IfStmt ifStmt = (IfStmt) stmt;

            // StatementNode sNode = node.addStatement("if",
            // siphonCode(file, ifStmt.getBeginLine(),
            // ifStmt.getBeginColumn(), ifStmt.getEndLine(),
            // ifStmt.getEndColumn()));

            // handleExpressionContents(ifStmt.getCondition(), sNode);
            // handleBodyStatements(ifStmt.getElseStmt(), sNode);
            // handleBodyStatements(ifStmt.getThenStmt(), sNode);

        }
        else if (stmt instanceof ReturnStmt)
        {
            ReturnStmt retStmt = (ReturnStmt) stmt;
            // StatementNode sNode = node.addStatement("return",
            // siphonCode(file, retStmt.getBeginLine(),
            // retStmt.getBeginColumn(), retStmt.getEndLine(),
            // retStmt.getEndColumn()));

            // handleExpressionContents(retStmt.getExpr(), sNode);

        }
        else if (stmt instanceof SwitchEntryStmt)
        {
            SwitchEntryStmt switchEntry = (SwitchEntryStmt) stmt;
            StatementNode sNode = addStatement(
                    node,
                    "switch",
                    siphonCode(file, switchEntry.getBeginLine(), switchEntry.getBeginColumn(),
                            switchEntry.getEndLine(), switchEntry.getEndColumn()));

            // handleExpressionContents(switchEntry.getLabel(), sNode);
            // List<Statement> switchBody = switchEntry.getStmts();
            // for (Statement bodyStmt : switchBody) {
            // handleBodyStatements(bodyStmt, sNode);
            // }

        }
        else if (stmt instanceof SynchronizedStmt)
        {
            SynchronizedStmt sync = (SynchronizedStmt) stmt;
            StatementNode sNode = addStatement(
                    node,
                    "synchronized",
                    siphonCode(file, sync.getBeginLine(), sync.getBeginColumn(), sync.getEndLine(), sync.getEndColumn()));

            // handleBodyStatements(sync.getBlock(), sNode);
            // handleExpressionContents(sync.getExpr(), sNode);

        }
        else if (stmt instanceof ThrowStmt)
        {
            ThrowStmt throwStmt = (ThrowStmt) stmt;
            StatementNode sNode = addStatement(
                    node,
                    "throw",
                    siphonCode(file, throwStmt.getBeginLine(), throwStmt.getBeginColumn(), throwStmt.getEndLine(),
                            throwStmt.getEndColumn()));

            // handleExpressionContents(throwStmt.getExpr(), sNode);

        }
        else if (stmt instanceof TryStmt)
        {
            TryStmt tryStmt = (TryStmt) stmt;
            StatementNode sNode = addStatement(
                    node,
                    "try",
                    siphonCode(file, tryStmt.getBeginLine(), tryStmt.getBeginColumn(), tryStmt.getEndLine(),
                            tryStmt.getEndColumn()));

            // handleBodyStatements(tryStmt.getTryBlock(), sNode);
            // handleBodyStatements(tryStmt.getFinallyBlock(), sNode);

            // List<CatchClause> catches = tryStmt.getCatchs();
            // for (CatchClause cc : catches) {
            // BlockStmt catchBlock = cc.getCatchBlock();
            // handleBodyStatements(catchBlock, sNode);
            // }

        }
        else if (stmt instanceof TypeDeclarationStmt)
        {
            TypeDeclarationStmt typeDec = (TypeDeclarationStmt) stmt;
            StatementNode sNode = addStatement(
                    node,
                    "typedec",
                    siphonCode(file, typeDec.getBeginLine(), typeDec.getBeginColumn(), typeDec.getEndLine(),
                            typeDec.getEndColumn()));

            // handleTypeDeclaration(typeDec.getTypeDeclaration(),
            // sNode);
        }
        else if (stmt instanceof WhileStmt)
        {
            WhileStmt whileStmt = (WhileStmt) stmt;
            StatementNode sNode = addStatement(
                    node,
                    "while",
                    siphonCode(file, whileStmt.getBeginLine(), whileStmt.getBeginColumn(), whileStmt.getEndLine(),
                            whileStmt.getEndColumn()));

            // handleExpressionContents(whileStmt.getCondition(),
            // sNode);
            // handleBodyStatements(whileStmt.getBody(), sNode);
        }
    }

    public void processTypeDeclaration(ClassOrInterfaceDeclaration cid, ClassNode cNode)
    {

        if (cNode != null)
        {
            List<BodyDeclaration> body = cid.getMembers();
            for (BodyDeclaration bodyDec : body)
            {
                if (bodyDec instanceof FieldDeclaration)
                { // Handle
                  // Fields

                    FieldDeclaration field = (FieldDeclaration) bodyDec;

                    List<VariableDeclarator> variables = field.getVariables();
                    for (VariableDeclarator var : variables)
                    {
                        addAttribute(
                                cNode,
                                var.getId().getName(),
                                siphonCode(file, var.getBeginLine(), var.getBeginColumn(), var.getEndLine(),
                                        var.getEndColumn()));
                    }
                }
                else if (bodyDec instanceof MethodDeclaration)
                { // Handle
                  // Methods
                    MethodDeclaration method = (MethodDeclaration) bodyDec;

                    MethodNode mNode = addMethod(
                            cNode,
                            method.getName(),
                            getParams(method.getParameters()),
                            siphonCode(file, method.getBeginLine(), method.getBeginColumn(), method.getEndLine(),
                                    method.getEndColumn()));

                    // Handle the method body
                    BlockStmt methodBody = method.getBody();

                    handleMethodContents(methodBody, mNode);
                }
                else if (bodyDec instanceof ConstructorDeclaration)
                { // Handle
                  // Constructors
                    ConstructorDeclaration construct = (ConstructorDeclaration) bodyDec;
                    BlockStmt methodBody = construct.getBlock();

                    MethodNode node = addMethod(
                            cNode,
                            construct.getName(),
                            getParams(construct.getParameters()),
                            siphonCode(file, construct.getBeginLine(), construct.getBeginColumn(),
                                    construct.getEndLine(), construct.getEndColumn()));

                    handleMethodContents(methodBody, node);
                }
            }
        }
        else
        {
            cNode = new ClassNode(cid.getName());
        }
    }
}
