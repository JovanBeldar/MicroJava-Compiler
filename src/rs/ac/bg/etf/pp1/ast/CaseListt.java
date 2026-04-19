// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:59


package rs.ac.bg.etf.pp1.ast;

public class CaseListt extends CaseList {

    private CaseList CaseList;
    private CaseListPart CaseListPart;

    public CaseListt (CaseList CaseList, CaseListPart CaseListPart) {
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
        this.CaseListPart=CaseListPart;
        if(CaseListPart!=null) CaseListPart.setParent(this);
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public CaseListPart getCaseListPart() {
        return CaseListPart;
    }

    public void setCaseListPart(CaseListPart CaseListPart) {
        this.CaseListPart=CaseListPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CaseList!=null) CaseList.accept(visitor);
        if(CaseListPart!=null) CaseListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
        if(CaseListPart!=null) CaseListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        if(CaseListPart!=null) CaseListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseListt(\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseListPart!=null)
            buffer.append(CaseListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseListt]");
        return buffer.toString();
    }
}
