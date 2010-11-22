/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package truerefactor.old;

import java.util.List;

import truerefactor.old.graph.ClassNode;
import truerefactor.old.graph.CodeGraph;
import truerefactor.old.graph.FieldNode;
import truerefactor.old.graph.MethodNode;

/**
 * RefactorController -
 * 
 * @author Isaac Griffith
 */
public class RefactorController {

    /**
     * 
     */
    private CodeGraph codeGraph;

    /**
     * @param origGraph
     */
    public RefactorController(CodeGraph origGraph)
    {
        try
        {
            codeGraph = (CodeGraph) origGraph.clone();
        }
        catch (CloneNotSupportedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param superClass
     */
    public void collapseHierarchy(ClassNode superClass)
    {

    }

    /**
     * @param fNode
     * @param cNodeFromLst
     * @param cNodeTo
     */
    public void pullUpField(FieldNode fNode, List<ClassNode> cNodeFromLst, ClassNode cNodeTo)
    {

        for (ClassNode from : cNodeFromLst)
        {
            if (codeGraph.getField(from, fNode.getName()) != null)
            {
                from.removeAttribute(from.getAttribute(fNode.getName()));
            }
        }

        cNodeTo.addAttribute(fNode.getName(), fNode.getCode());
    }

    /**
     * @param mNode
     * @param cNode
     */
    public void pullUpMethod(MethodNode mNode, ClassNode cNode)
    {
        for (ClassNode scNode : cNode.getSuperClass())
        {
            List<ClassNode> children = scNode.getChildClasses();

            for (ClassNode child : children)
            {
                if (child.getMethod(mNode.getName(), mNode.getParams()) != null)
                {
                    child.removeMethod(child.getMethod(mNode.getName(), mNode.getParams()));
                }
            }

            scNode.addMethod(mNode.getName(), mNode.getCode(), mNode.getParams());
        }
    }

    /**
     * @param fNode
     * @param fromClass
     * @param toClass
     */
    public void moveField(FieldNode fNode, ClassNode toClass)
    {
        toClass.addAttribute(fNode);
    }

    /**
     * @param mNode
     * @param fromClass
     * @param toClass
     */
    public void moveMethod(MethodNode mNode, ClassNode toClass)
    {
        toClass.addMethod(mNode);
    }

    /**
     * @return
     */
    public CodeGraph getGraph()
    {
        return codeGraph;
    }
}
