// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodTypeName MethodTypeName;
    private FormalParameters FormalParameters;
    private ListOfVarDeclarations ListOfVarDeclarations;
    private ListOfStatements ListOfStatements;

    public MethodDecl (MethodTypeName MethodTypeName, FormalParameters FormalParameters, ListOfVarDeclarations ListOfVarDeclarations, ListOfStatements ListOfStatements) {
        this.MethodTypeName=MethodTypeName;
        if(MethodTypeName!=null) MethodTypeName.setParent(this);
        this.FormalParameters=FormalParameters;
        if(FormalParameters!=null) FormalParameters.setParent(this);
        this.ListOfVarDeclarations=ListOfVarDeclarations;
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.setParent(this);
        this.ListOfStatements=ListOfStatements;
        if(ListOfStatements!=null) ListOfStatements.setParent(this);
    }

    public MethodTypeName getMethodTypeName() {
        return MethodTypeName;
    }

    public void setMethodTypeName(MethodTypeName MethodTypeName) {
        this.MethodTypeName=MethodTypeName;
    }

    public FormalParameters getFormalParameters() {
        return FormalParameters;
    }

    public void setFormalParameters(FormalParameters FormalParameters) {
        this.FormalParameters=FormalParameters;
    }

    public ListOfVarDeclarations getListOfVarDeclarations() {
        return ListOfVarDeclarations;
    }

    public void setListOfVarDeclarations(ListOfVarDeclarations ListOfVarDeclarations) {
        this.ListOfVarDeclarations=ListOfVarDeclarations;
    }

    public ListOfStatements getListOfStatements() {
        return ListOfStatements;
    }

    public void setListOfStatements(ListOfStatements ListOfStatements) {
        this.ListOfStatements=ListOfStatements;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.accept(visitor);
        if(FormalParameters!=null) FormalParameters.accept(visitor);
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.accept(visitor);
        if(ListOfStatements!=null) ListOfStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodTypeName!=null) MethodTypeName.traverseTopDown(visitor);
        if(FormalParameters!=null) FormalParameters.traverseTopDown(visitor);
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.traverseTopDown(visitor);
        if(ListOfStatements!=null) ListOfStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.traverseBottomUp(visitor);
        if(FormalParameters!=null) FormalParameters.traverseBottomUp(visitor);
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.traverseBottomUp(visitor);
        if(ListOfStatements!=null) ListOfStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodTypeName!=null)
            buffer.append(MethodTypeName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParameters!=null)
            buffer.append(FormalParameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListOfVarDeclarations!=null)
            buffer.append(ListOfVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListOfStatements!=null)
            buffer.append(ListOfStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
