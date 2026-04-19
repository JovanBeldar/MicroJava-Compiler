// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class AbstrClassDeclNoExtList extends AbstractClassDecl {

    private String I1;
    private ListOfVarDeclarations ListOfVarDeclarations;
    private AbstractClassList AbstractClassList;

    public AbstrClassDeclNoExtList (String I1, ListOfVarDeclarations ListOfVarDeclarations, AbstractClassList AbstractClassList) {
        this.I1=I1;
        this.ListOfVarDeclarations=ListOfVarDeclarations;
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.setParent(this);
        this.AbstractClassList=AbstractClassList;
        if(AbstractClassList!=null) AbstractClassList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ListOfVarDeclarations getListOfVarDeclarations() {
        return ListOfVarDeclarations;
    }

    public void setListOfVarDeclarations(ListOfVarDeclarations ListOfVarDeclarations) {
        this.ListOfVarDeclarations=ListOfVarDeclarations;
    }

    public AbstractClassList getAbstractClassList() {
        return AbstractClassList;
    }

    public void setAbstractClassList(AbstractClassList AbstractClassList) {
        this.AbstractClassList=AbstractClassList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.accept(visitor);
        if(AbstractClassList!=null) AbstractClassList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.traverseTopDown(visitor);
        if(AbstractClassList!=null) AbstractClassList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListOfVarDeclarations!=null) ListOfVarDeclarations.traverseBottomUp(visitor);
        if(AbstractClassList!=null) AbstractClassList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstrClassDeclNoExtList(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ListOfVarDeclarations!=null)
            buffer.append(ListOfVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractClassList!=null)
            buffer.append(AbstractClassList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstrClassDeclNoExtList]");
        return buffer.toString();
    }
}
