/**
 * 
 */
package truerefactor.old.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CodeGraph - Graph which encapsulates the dependencies between
 * IUpdatables in the Model by generating DependencyEdges between two
 * IUpdatables encapsulated in DependentNodes. Each Node is stored as a key in a
 * Map relating the Node to a List of nodes which are dependent upon the key
 * node.
 * 
 * @author Isaac
 */
public class CodeGraph implements DiGraph {

    /**
     * The number of Nodes in the Graph
     */
    private int nodeCount;
    /**
     * The number of Edges in the Graph
     */
    private int edgeCount;
    /**
     * Map of a List of Nodes containing all of the nodes dependent upon the
     * Node indexing the List in the Map
     */
    private ConcurrentHashMap<Node, CopyOnWriteArrayList<Pair>> adjacencies;
    /**
     * List of all the nodes in the graph
     */
    private CopyOnWriteArrayList<Node> nodes = null;
    /**
     * List of source nodes of the graph. Where a source node is a node which is
     * not the destination end of any edge in the graph.
     */
    private List<Node> sources;
    /**
     * List of sink nodes of the graph. Where a sink node is a node which is not
     * a source end of any edge in the graph.
     */
    private List<Node> sinks;
    
    /**
     * Constructs a new Empty CodeGraph
     */
    public CodeGraph()
    {
        nodeCount = 0;
        edgeCount = 0;
        adjacencies = new ConcurrentHashMap<Node, CopyOnWriteArrayList<Pair>>();
        sources = new LinkedList<Node>();
        sinks = new LinkedList<Node>();
        nodes = new CopyOnWriteArrayList<Node>();
    }
    
//    public CodeGraph(GraphType type) {
//        super();
//        this.type = type;
//    }

    /*
     * (non-Javadoc)
     * @see loopmanager.DiGraph#NumberOfNodes()
     */
    @Override
    public synchronized int numberOfNodes()
    {
        return nodeCount;
    }

    /*
     * (non-Javadoc)
     * @see loopmanager.DiGraph#getEdge(loopmanager.Node, loopmanager.Node)
     */
    @Override
    public synchronized CodeEdge getEdge(Node source, Node destination)
    {
        CodeEdge retVal = null;

        if (adjacencies.containsKey(source))
        {
            CopyOnWriteArrayList<Pair> temp = adjacencies.get(source);
            Pair destPair = new Pair(1.0d, nodes.get(this.indexOf(destination)));

            if (temp.contains(destPair))
            {
                int index = temp.indexOf(destPair);
                if (index > -1)
                {
                    destPair = temp.get(index);
                    retVal = new CodeEdge(source, destination, destPair.getWeight());
                }
            }
        }

        return retVal;
    }

    /*
     * (non-Javadoc)
     * @see loopmanager.DiGraph#getIterator()
     */
    @Override
    public synchronized GraphIterator getIterator()
    {
        return new GraphIterator(this);
    }

    /*
     * (non-Javadoc)
     * @see loopmanager.DiGraph#insert(loopmanager.DependencyEdge)
     */
    @Override
    public synchronized void insert(CodeEdge edge)
    {
        Node source = edge.getSource();
        Node destination = edge.getDesination();
        double weight = edge.getWeight();
        Pair destPair = new Pair(weight, destination);

        if (adjacencies.containsKey(source))
        {
            CopyOnWriteArrayList<Pair> temp = adjacencies.get(source);
            if (!temp.contains(destPair))
            {
                temp.add(destPair);
                edgeCount++;
            }

            if (!adjacencies.containsKey(destination))
            {
                adjacencies.put(destination, new CopyOnWriteArrayList<Pair>());
                nodeCount++;
            }
        }
        else
        {
            CopyOnWriteArrayList<Pair> temp = new CopyOnWriteArrayList<Pair>();
            temp.add(destPair);
            adjacencies.put(source, temp);
            nodeCount++;

            if (!adjacencies.containsKey(destination))
            {
                adjacencies.put(destination, new CopyOnWriteArrayList<Pair>());
                edgeCount++;
            }
        }

        if (sources.contains(destination))
        {
            sources.remove(destination);
        }

        if (sinks.contains(destination) && !adjacencies.get(destination).isEmpty())
        {
            sinks.remove(destination);
        }

        if (sinks.contains(source))
        {
            sinks.remove(source);
        }

        destination.addPrerequisite(source);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.DiGraph#insert(org.neosimulation
     * .neo.framework.graph.Node)
     */
    @Override
    public synchronized Node insert(Node node)
    {
        if (node != null)
        {
            if (!adjacencies.contains(node))
            {
                adjacencies.put(node, new CopyOnWriteArrayList<Pair>());
                nodes.add(node);
                sources.add(node);
                sinks.add(node);

                nodeCount++;
            }
        }
        
        return node;
    }

    /*
     * (non-Javadoc)
     * @see loopmanager.DiGraph#numberOfEdges()
     */
    @Override
    public synchronized int numberOfEdges()
    {
        return edgeCount;
    }

    /*
     * (non-Javadoc)
     * @see loopmanager.DiGraph#remove(loopmanager.Node)
     */
    @Override
    public synchronized void remove(Node node)
    {
        if (adjacencies.containsKey(node))
        {
            adjacencies.remove(node);
            nodeCount--;
        }

        if (sources.contains(node))
        {
            sources.remove(node);
        }

        if (sinks.contains(node))
        {
            sinks.remove(node);
        }

        if (nodes.contains(node))
        {
            nodes.remove(node);
        }
    }

    /*
     * (non-Javadoc)
     * @see loopmanager.DiGraph#remove(loopmanager.DependencyEdge)
     */
    @Override
    public synchronized void remove(CodeEdge edge)
    {
        Node source = edge.getSource();
        Node destination = edge.getDesination();

        if (adjacencies.containsKey(source))
        {
            CopyOnWriteArrayList<Pair> temp = adjacencies.get(source);

            Pair destPair = new Pair(0, destination);
            if (temp.contains(destPair))
            {
                temp.remove(destPair);
                adjacencies.put(source, temp);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.graph.DiGraph#get(int)
     */
    @Override
    public synchronized Node get(int index)
    {
        if (nodes == null)
        {
            nodes = new CopyOnWriteArrayList<Node>();

            for (Node n : adjacencies.keySet())
            {
                nodes.add(n);
            }
        }

        if (index >= 0 && (nodes.size() - 1) >= index)
        {
            return nodes.get(index);
        }
        else
        {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.graph.DiGraph#getAdjacencies(org.
     * neosimulation.neo.framework.graph.Node)
     */
    @Override
    public synchronized CopyOnWriteArrayList<Node> getAdjacencies(Node node)
    {
        CopyOnWriteArrayList<Node> adjList = new CopyOnWriteArrayList<Node>();

        if (node != null && adjacencies.containsKey(node))
        {
            for (Pair p : adjacencies.get(node))
            {
                adjList.add(p.getNode());
            }
        }
        return adjList;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.DiGraph#getIndex(org.neosimulation
     * .neo.framework.graph.Node)
     */
    @Override
    public synchronized int indexOf(Node node)
    {
        if (nodes == null)
        {
            nodes = new CopyOnWriteArrayList<Node>();

            for (Node n : adjacencies.keySet())
            {
                nodes.add(n);
            }
        }

        if (nodes.contains(node))
        {
            return nodes.indexOf(node);
        }
        else
        {
            return -1;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.DiGraph#isEdge(org.neosimulation
     * .neo.framework.graph.Node, org.neosimulation.neo.framework.graph.Node)
     */
    @Override
    public synchronized boolean isEdge(Node source, Node destination)
    {
        if (source == null)
            System.out.println("Source is null");
        if (adjacencies.containsKey(source))
        {
            CopyOnWriteArrayList<Pair> pairs = adjacencies.get(source);
            for (Pair pair : pairs)
            {
                if (pair.getNode().equals(destination))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.graph.DiGraph#getSinks()
     */
    @Override
    public synchronized List<Node> getSinks()
    {
        return sinks;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.graph.DiGraph#getSources()
     */
    @Override
    public synchronized List<Node> getSources()
    {
        return sources;
    }

    public synchronized Set<Node> diff(DiGraph other) {
        Set<Node> diffNodes = new HashSet<Node>();
        
        List<Node> nodes = new LinkedList<Node>();
        for (int i = 0; i < other.numberOfNodes(); i++) {
            nodes.add(other.get(i));
        }
        
        for (int i = 0; i < numberOfNodes(); i++) {
            if (nodes.contains(get(i))) {
                Node node = nodes.get(nodes.indexOf(get(i)));
                if (other.getAdjacencies(node).size() != getAdjacencies(get(i)).size()) {
                    diffNodes.add(get(i));
                } else {
                    nodes.remove(node);
                }
            }
        }
        
        if (!nodes.isEmpty()) {
            diffNodes.addAll(nodes);
        }
        
        return diffNodes;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CodeGraph) {
            CodeGraph depGraph = (CodeGraph) obj;
            
            if (depGraph.diff(this).isEmpty()) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Provides a means of retaining the information in a CodeEdge without
     * requiring the need to maintain edges and the overhead of those objects.
     * 
     * @author Isaac
     */
    private class Pair {
        /**
         * weight of the original edge
         */
        private double weight;
        /**
         * node of the destination end of the edge
         */
        private Node node;

        /**
         * Constructs a new Pair with the provided weight and destination node
         * 
         * @param weight
         *            weight of the edge
         * @param node
         *            destination node
         */
        public Pair(double weight, Node node)
        {
            this.weight = weight;
            this.node = node;
        }

        /**
         * Returns the Destination node contained in this pair
         * 
         * @return Destination Node
         */
        public synchronized Node getNode()
        {
            return node;
        }

        /**
         * Returns the weight
         * 
         * @return Weight associated beteen the source node and the contained
         *         destination node of this pair
         */
        public synchronized double getWeight()
        {
            return weight;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public synchronized boolean equals(Object obj)
        {
            boolean returnVal = false;

            if (obj instanceof Pair)
            {
                if (((Pair) obj).node.equals(this.node))
                {
                    returnVal = true;
                }
            }

            return returnVal;
        }
    }

    public ClassNode getClass(String className)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public MethodNode getMethod(ClassNode cNode, String methodName, List<String> params)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
