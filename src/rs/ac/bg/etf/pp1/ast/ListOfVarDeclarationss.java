// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class ListOfVarDeclarationss extends ListOfVarDeclarations {

    private ListOfVarDeclarations ListOfVarDeclarations;
    private VarDecl VarDecl;

    public ListOfVarDeclarationss (ListOfVarDeclarations ListOfVarDeclarations, VarDecl VarDecl) {
        this.ListOfVarDeclarations=ListOfVarDeclarations;
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public ListOfVarDeclarations getListOfVarDeclarations() {
        return ListOfVarDeclarations;
    }

    public void setListOfVarDeclarations(ListOfVarDeclarations ListOfVarDeclarations) {
        this.ListOfVarDeclarations=ListOfVarDeclarations;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListOfVarDeclarationss(\n");

        if(ListOfVarDeclarations!=null)
            buffer.append(ListOfVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListOfVarDeclarationss]");
        return buffer.toString();
    }
}
