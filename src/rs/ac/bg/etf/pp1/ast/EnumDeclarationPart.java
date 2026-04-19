// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclarationPart extends EnumDeclList {

    private EnumDeclPart EnumDeclPart;

    public EnumDeclarationPart (EnumDeclPart EnumDeclPart) {
        this.EnumDeclPart=EnumDeclPart;
        if(EnumDeclPart!=null) EnumDeclPart.setParent(this);
    }

    public EnumDeclPart getEnumDeclPart() {
        return EnumDeclPart;
    }

    public void setEnumDeclPart(EnumDeclPart EnumDeclPart) {
        this.EnumDeclPart=EnumDeclPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumDeclPart!=null) EnumDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumDeclPart!=null) EnumDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumDeclPart!=null) EnumDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclarationPart(\n");

        if(EnumDeclPart!=null)
            buffer.append(EnumDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDeclarationPart]");
        return buffer.toString();
    }
}
