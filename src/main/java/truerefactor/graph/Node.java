/**
 * 
 */
package truerefactor.graph;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Node - The most basic, or atomic, unit of a Graph. This incarnation of the
 * node maintains a list of next nodes as well as previous nodes (or
 * prerequisites).
 * 
 * @author Isaac
 */
public class Node {

    /**
     * List of Nodes which are connected to this node and provide edges into
     * this node.
     */
    protected List<Node> prerequisites;

    /**
     * The Value of the complexity of the operations to be performed by the
     * IUpdatables associated with this node
     */
    protected double complexity;

    /**
     * Constructs a new disconnected and empty Node
     */
    public Node()
    {
        prerequisites = new CopyOnWriteArrayList<Node>();
    }

    /**
     * Inserts the specified node into this Node's list of prerequisites, the
     * list of nodes which provide incoming edges that connect to this node.
     * 
     * @param node
     *            A new prerequisite node of this node.
     */
    public void addPrerequisite(Node node)
    {
        prerequisites.add(node);
    }

    /**
     * Returns the list of prerequisite nodes which must be visited prior to
     * visiting this node.
     * 
     * @return List of prerequisite nodes of this node
     */
    public List<Node> getPrerequisites()
    {
        return prerequisites;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Node)
        {
            Node node = (Node) obj;

            if (node.getPrerequisites().size() == this.prerequisites.size())
            {
                List<Node> prereqs = node.getPrerequisites();
                for (int i = 0; i < prereqs.size(); i++)
                {
                    if (!prereqs.get(i).equals(prerequisites.get(i)))
                    {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }
}
