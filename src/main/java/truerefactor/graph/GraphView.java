/**
 * 
 */
package truerefactor.graph;

import java.util.HashMap;
import java.util.List;

/**
 * @author Isaac
 */
public class GraphView {

    /**
     * 
     */
    public static void print(CodeGraph graph)
    {
        HashMap<String, ClassNode> classes = graph.getClasses();
        List<MethodNode> methods = graph.getMethods();
        List<FieldNode> fields = graph.getFields();

        System.out.println("Classes: ");
        for (String className : classes.keySet())
        {
            ClassNode cNode = classes.get(className);
            printClassNode(cNode);
        }

        System.out.println("Field List");
        for (FieldNode fNode : fields)
        {
            System.out.println("\t" + printFieldNode(fNode));
        }

        System.out.println("Method List");
        for (MethodNode mNode : methods)
        {
            System.out.println("\t" + printMethodNode(mNode));
        }

    }

    /**
     * @param clas
     */
    private static void printClassNode(ClassNode clas)
    {
        System.out.println("\t" + clas.getName());
        System.out.println("\t\tFields:");
        for (FieldNode field : clas.getFields())
            System.out.println("\t\t\t" + printFieldNode(field));
        System.out.println("\t\tMethods:");
        for (MethodNode method : clas.getMethods())
            System.out.println("\t\t\t" + printMethodNode(method));
    }

    /**
     * @param method
     * @return
     */
    private static String printMethodNode(MethodNode method)
    {
        return (method.getName() + " : Parent " + method.getParentClass().getName());
    }

    /**
     * @param field
     * @return
     */
    private static String printFieldNode(FieldNode field)
    {
        return (field.getName() + " : Parent " + field.getParentClass().getName());
    }
}
