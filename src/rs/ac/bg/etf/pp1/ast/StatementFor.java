// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class StatementFor extends Statement {

    private ForNonTerm ForNonTerm;
    private ForInit ForInit;
    private ForFirstSemicolon ForFirstSemicolon;
    private ForCond ForCond;
    private ForSecondSemicolon ForSecondSemicolon;
    private ForUpdate ForUpdate;
    private ForRparen ForRparen;
    private Statement Statement;

    public StatementFor (ForNonTerm ForNonTerm, ForInit ForInit, ForFirstSemicolon ForFirstSemicolon, ForCond ForCond, ForSecondSemicolon ForSecondSemicolon, ForUpdate ForUpdate, ForRparen ForRparen, Statement Statement) {
        this.ForNonTerm=ForNonTerm;
        if(ForNonTerm!=null) ForNonTerm.setParent(this);
        this.ForInit=ForInit;
        if(ForInit!=null) ForInit.setParent(this);
        this.ForFirstSemicolon=ForFirstSemicolon;
        if(ForFirstSemicolon!=null) ForFirstSemicolon.setParent(this);
        this.ForCond=ForCond;
        if(ForCond!=null) ForCond.setParent(this);
        this.ForSecondSemicolon=ForSecondSemicolon;
        if(ForSecondSemicolon!=null) ForSecondSemicolon.setParent(this);
        this.ForUpdate=ForUpdate;
        if(ForUpdate!=null) ForUpdate.setParent(this);
        this.ForRparen=ForRparen;
        if(ForRparen!=null) ForRparen.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForNonTerm getForNonTerm() {
        return ForNonTerm;
    }

    public void setForNonTerm(ForNonTerm ForNonTerm) {
        this.ForNonTerm=ForNonTerm;
    }

    public ForInit getForInit() {
        return ForInit;
    }

    public void setForInit(ForInit ForInit) {
        this.ForInit=ForInit;
    }

    public ForFirstSemicolon getForFirstSemicolon() {
        return ForFirstSemicolon;
    }

    public void setForFirstSemicolon(ForFirstSemicolon ForFirstSemicolon) {
        this.ForFirstSemicolon=ForFirstSemicolon;
    }

    public ForCond getForCond() {
        return ForCond;
    }

    public void setForCond(ForCond ForCond) {
        this.ForCond=ForCond;
    }

    public ForSecondSemicolon getForSecondSemicolon() {
        return ForSecondSemicolon;
    }

    public void setForSecondSemicolon(ForSecondSemicolon ForSecondSemicolon) {
        this.ForSecondSemicolon=ForSecondSemicolon;
    }

    public ForUpdate getForUpdate() {
        return ForUpdate;
    }

    public void setForUpdate(ForUpdate ForUpdate) {
        this.ForUpdate=ForUpdate;
    }

    public ForRparen getForRparen() {
        return ForRparen;
    }

    public void setForRparen(ForRparen ForRparen) {
        this.ForRparen=ForRparen;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForNonTerm!=null) ForNonTerm.accept(visitor);
        if(ForInit!=null) ForInit.accept(visitor);
        if(ForFirstSemicolon!=null) ForFirstSemicolon.accept(visitor);
        if(ForCond!=null) ForCond.accept(visitor);
        if(ForSecondSemicolon!=null) ForSecondSemicolon.accept(visitor);
        if(ForUpdate!=null) ForUpdate.accept(visitor);
        if(ForRparen!=null) ForRparen.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForNonTerm!=null) ForNonTerm.traverseTopDown(visitor);
        if(ForInit!=null) ForInit.traverseTopDown(visitor);
        if(ForFirstSemicolon!=null) ForFirstSemicolon.traverseTopDown(visitor);
        if(ForCond!=null) ForCond.traverseTopDown(visitor);
        if(ForSecondSemicolon!=null) ForSecondSemicolon.traverseTopDown(visitor);
        if(ForUpdate!=null) ForUpdate.traverseTopDown(visitor);
        if(ForRparen!=null) ForRparen.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForNonTerm!=null) ForNonTerm.traverseBottomUp(visitor);
        if(ForInit!=null) ForInit.traverseBottomUp(visitor);
        if(ForFirstSemicolon!=null) ForFirstSemicolon.traverseBottomUp(visitor);
        if(ForCond!=null) ForCond.traverseBottomUp(visitor);
        if(ForSecondSemicolon!=null) ForSecondSemicolon.traverseBottomUp(visitor);
        if(ForUpdate!=null) ForUpdate.traverseBottomUp(visitor);
        if(ForRparen!=null) ForRparen.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementFor(\n");

        if(ForNonTerm!=null)
            buffer.append(ForNonTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForInit!=null)
            buffer.append(ForInit.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForFirstSemicolon!=null)
            buffer.append(ForFirstSemicolon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCond!=null)
            buffer.append(ForCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForSecondSemicolon!=null)
            buffer.append(ForSecondSemicolon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForUpdate!=null)
            buffer.append(ForUpdate.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForRparen!=null)
            buffer.append(ForRparen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementFor]");
        return buffer.toString();
    }
}
