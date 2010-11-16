package truerefactor.genetic;

import truerefactor.graph.ClassNode;
import truerefactor.graph.FieldNode;
import truerefactor.graph.MethodNode;

public class Allele {

    private String type;
    private MethodNode method;
    private FieldNode field;
    private ClassNode newParent;

    public Allele(MethodNode method, ClassNode newParent) {
	type = "MoveMethod";

	this.method = method;
	this.newParent = newParent;
    }

    public Allele(FieldNode field, ClassNode newParent) {
	type = "MoveField";

	this.field = field;
	this.newParent = newParent;
    }

    public Allele(String type) {
	this.type = type;
    }

    public Allele(Allele clone) {
	this.type = clone.getType();
	this.method = clone.getMethod();
	this.field = clone.getField();
	this.newParent = clone.getNewParent();
    }

    public void setMethod(MethodNode method) {
	this.method = method;
    }

    public void setField(FieldNode field) {
	this.field = field;
    }

    public void setNewParent(ClassNode newParent) {
	this.newParent = newParent;
    }

    public String getType() {
	return type;
    }

    public MethodNode getMethod() {
	return method;
    }

    public FieldNode getField() {
	return field;
    }

    public ClassNode getNewParent() {
	return newParent;
    }

    public void print() {
	System.out.print(type + ": (" + (type.equals("MoveMethod") ? method.getName() : field.getName()) + ", " + newParent + ") ");
    }
}