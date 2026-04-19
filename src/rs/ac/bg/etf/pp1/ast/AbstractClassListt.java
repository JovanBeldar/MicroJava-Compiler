// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassListt extends AbstractClassList {

    private AbstractClassList AbstractClassList;
    private AbstractClassListPart AbstractClassListPart;

    public AbstractClassListt (AbstractClassList AbstractClassList, AbstractClassListPart AbstractClassListPart) {
        this.AbstractClassList=AbstractClassList;
        if(AbstractClassList!=null) AbstractClassList.setParent(this);
        this.AbstractClassListPart=AbstractClassListPart;
        if(AbstractClassListPart!=null) AbstractClassListPart.setParent(this);
    }

    public AbstractClassList getAbstractClassList() {
        return AbstractClassList;
    }

    public void setAbstractClassList(AbstractClassList AbstractClassList) {
        this.AbstractClassList=AbstractClassList;
    }

    public AbstractClassListPart getAbstractClassListPart() {
        return AbstractClassListPart;
    }

    public void setAbstractClassListPart(AbstractClassListPart AbstractClassListPart) {
        this.AbstractClassListPart=AbstractClassListPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassList!=null) AbstractClassList.accept(visitor);
        if(AbstractClassListPart!=null) AbstractClassListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassList!=null) AbstractClassList.traverseTopDown(visitor);
        if(AbstractClassListPart!=null) AbstractClassListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassList!=null) AbstractClassList.traverseBottomUp(visitor);
        if(AbstractClassListPart!=null) AbstractClassListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassListt(\n");

        if(AbstractClassList!=null)
            buffer.append(AbstractClassList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractClassListPart!=null)
            buffer.append(AbstractClassListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassListt]");
        return buffer.toString();
    }
}
