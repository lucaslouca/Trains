package com.lucaslouca.util;

import java.util.*;

/**
 * Class representing a generic directed graph.
 *
 * @param <T> Type of graphs's the nodes. For example a {@code LLTown}.
 */
@SuppressWarnings("unchecked")
public class LLDirectedGraph<T> {
    /**
     * {@code Exception} that is thrown when a node in the graph is not reachable.
     */
    public static class NodeNotReachableException extends Exception {
        public NodeNotReachableException(String message) {
            super(message);
        }
    }

    /**
     * Condition to be evaluated for a path during DFS traversal
     */
    private interface DFSCondition {
        /**
         * Evaluate condition for given path.
         *
         * @param path Path for which to evaluate condition
         * @return true if condition is met. False otherwise
         */
        boolean evaluate(LLDirectedGraph.GraphPath path);
    }

    /**
     * Internal Graph Node model never exposed to the public.
     * Wrapper class containing properties for shortest path computation, etc.
     */
    private class GraphNode implements Comparable<GraphNode> {
        private T data;
        private Integer distance;
        private GraphNode previous;
        protected Map<T, Integer> neighbours;

        public GraphNode(T data) {
            this.data = data;
            this.neighbours = new HashMap<T, Integer>();
        }

        public T getData() {
            return data;
        }

        public Map<T, Integer> getNeighbours() {
            return neighbours;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public GraphNode getPrevious() {
            return previous;
        }

        public void setPrevious(GraphNode previous) {
            this.previous = previous;
        }

        public GraphPath path(GraphNode startNode) {
            List<T> nodes = new LinkedList<T>();
            int distance = 0;
            GraphNode currentNode = this;

            if (currentNode.getPrevious() == null) {
                return new GraphPath(nodes, distance);
            } else {
                nodes.add(currentNode.getData());

                while (!currentNode.equals(currentNode.getPrevious())) {
                    distance += weightForEdge(currentNode.getPrevious().getData(), currentNode.getData());

                    currentNode = currentNode.getPrevious();
                    nodes.add(0, currentNode.getData());

                    if (currentNode.equals(startNode)) {
                        break;
                    }
                }

                return new GraphPath(nodes, distance);
            }
        }

        public int compareTo(GraphNode other) {
            return distance.compareTo(other.getDistance());
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    /**
     * An instance of {@code GraphPath} represents a path containing the nodes in the order
     * in which they have been traversed.
     */
    public class GraphPath {
        private final String PATH_SEPARATOR = "->";
        private List<T> nodes;
        private int distance;

        /**
         * Creates a new empty {@code GraphPath}.
         */
        public GraphPath() {
            this.nodes = new ArrayList<T>();
            this.distance = 0;
        }

        /**
         * Creates a new {@code GraphPath} with the provided nodes and length.
         *
         * @param nodes  {@code List} of nodes that make up the path.
         * @param length length of path.
         */
        public GraphPath(List<T> nodes, int length) {
            this.nodes = nodes;
            this.distance = length;
        }

        /**
         * Remove last node of the path.
         *
         * @param weight the weight of the edge that leads to the last node.
         * @return true if last node was available. {@code false} otherwise.
         */
        public boolean removeLastNode(int weight) {
            if (!this.nodes.isEmpty()) {
                distance -= weight;
                this.nodes.remove(this.nodes.size() - 1);
                return true;
            } else {
                return false;
            }
        }

        /**
         * Append node to the end of the path.
         *
         * @param node   node to append.
         * @param weight weight of edge that leads to the node.
         * @return true if this {@code GraphPath} changed as a result of the call. {@code false} otherwise.
         */
        public boolean appendNode(T node, int weight) {
            distance += weight;
            return this.nodes.add(node);
        }

        /**
         * {@code List} of nodes.
         *
         * @return {@code List} of nodes.
         */
        public List<T> getNodes() {
            return nodes;
        }

        /**
         * Last node in path.
         *
         * @return last node of the path. {@code null} if path is empty.
         */
        public T last() {
            if (nodes.isEmpty()) {
                return null;
            } else {
                return nodes.get(nodes.size() - 1);
            }
        }

        /**
         * Total length of path. This is the sum of edge weights.
         *
         * @return the length of the path.
         */
        public int distance() {
            return distance;
        }

        /**
         * Total length of path in terms of number of hops.
         * <p>
         * Example: A path {@code 'A->b->C'} will have a hop count of 2.
         *
         * @return the length of path in terms of number of hops.
         */
        public int hopCount() {
            return Math.max(0, this.nodes.size() - 1);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodes.size() - 1; i++) {
                sb.append(nodes.get(i) + PATH_SEPARATOR);
            }
            sb.append(nodes.get(nodes.size() - 1));
            return sb.toString();
        }
    }


    private Map<T, GraphNode> graph;

    public LLDirectedGraph() {
        graph = new HashMap<T, GraphNode>();
    }

    /**
     * Add a new node to the graph.
     *
     * @param node the new node to add to this graph.
     */
    public void addNode(T node) {
        GraphNode graphNode = new GraphNode(node);
        if (!graph.containsKey(node)) {
            graph.put(node, graphNode);
        }
    }

    /**
     * Check whether graphs contains given node.
     *
     * @param node node to check whether it is contained in this graph.
     * @return {@code true} if node is in this graph. {@code false} otherwise.
     */
    public boolean contains(T node) {
        return graph.containsKey(node);
    }

    /**
     * Add an unidirectional edge from a given start node to a destination node.
     *
     * @param start  the node with outgoing edge.
     * @param dest   the node with the incoming edge.
     * @param weight the weight of the edges between the two nodes.
     * @throws NoSuchElementException   if one or both nodes don't exist.
     * @throws IllegalArgumentException if {@code weight} is less than 0.
     */
    public void addEdge(T start, T dest, int weight) {
        validateInputNodes(start, dest);

        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be >= 0");
        }

        graph.get(start).getNeighbours().put(dest, weight);
    }

    /**
     * Remove an edge from the graph.
     *
     * @param start the node with outgoing edge.
     * @param dest  the node with the incoming edge.
     * @throws NoSuchElementException if one or both graph don't exist.
     */
    public void removeEdge(T start, T dest) {
        validateInputNodes(start, dest);

        graph.get(start).getNeighbours().remove(dest);
    }

    /**
     * Get all the adjacent graph of the given node.
     *
     * @param node the node to retrieve the neighbors from.
     * @return {@code Set} of neighbors.
     * @throws NoSuchElementException if node doesn't exist.
     */
    public Set<T> getNeighbours(T node) {
        validateInputNodes(node);

        return graph.get(node).getNeighbours().keySet();
    }

    /**
     * Given two graph in the graph, returns whether there is an edge from the
     * first node to the second node. If either node does not exist in the
     * graph, throws a NoSuchElementException.
     *
     * @param start the start node.
     * @param dest  the destination node.
     * @return whether there is an edge from start to end.
     * @throws NoSuchElementException if either endpoint does not exist.
     */
    public boolean edgeExists(T start, T dest) {
        validateInputNodes(start, dest);

        return graph.get(start).getNeighbours().containsKey(dest);
    }

    /**
     * Get weight of the given edge.
     *
     * @param start the start node.
     * @param dest  the destination node.
     * @return the weight of the given edge.
     */
    private int weightForEdge(T start, T dest) {
        return graph.get(start).getNeighbours().get(dest);
    }

    /**
     * Nodes of graph.
     *
     * @return {@code Set} containing all graph in the graph.
     */
    public Set<T> getNodes() {
        return graph.keySet();
    }


    /**
     * Run a Dijkstra on the graph starting from start node and ending as soon as we reach dest.
     *
     * @param start source node.
     * @param dest  destination node.
     */
    private void dijkstra(T start, T dest) {
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();

        // Init graph:
        // Assign to every node a tentative distance value. Set it to zero for our initial node
        // and to infinity for all other graph.
        Set<T> nodes = getNodes();
        for (T node : nodes) {
            GraphNode graphNode = graph.get(node);
            graphNode.setDistance(Integer.MAX_VALUE);
            if (start.equals(node)) {
                graphNode.setDistance(0);
                graphNode.setPrevious(graphNode);
            }

            // Add node to priority queue
            queue.add(graphNode);
        }

        // Compute distances
        while (!queue.isEmpty()) {
            GraphNode currentGraphNode = queue.poll(); // Node with shortest distance

            if (currentGraphNode.getData().equals(dest) && !currentGraphNode.equals(currentGraphNode.getPrevious())) {
                break;
            }

            for (T neighbour : getNeighbours(currentGraphNode.getData())) {
                GraphNode neighbourGraphNode = graph.get(neighbour);

                final int alternateDist = currentGraphNode.getDistance() + weightForEdge(currentGraphNode.getData(), neighbour);
                if (alternateDist < neighbourGraphNode.getDistance() || neighbourGraphNode.equals(neighbourGraphNode.getPrevious())) { // shorter path to neighbour found
                    queue.remove(neighbourGraphNode);
                    neighbourGraphNode.setDistance(alternateDist);
                    neighbourGraphNode.setPrevious(currentGraphNode);
                    queue.add(neighbourGraphNode);
                }
            }
        }
    }


    /**
     * Count number of paths stating from currentNode and ending at destinationNode based on a filter condition.
     *
     * @param start  starting node.
     * @param stop   {@code DFSCondition} that evaluates to {@code true} if current search should be aborted for current path.
     * @param filter {@code DFSCondition} that evaluates to {@code true} if current path should be counted.
     * @return number of paths the met the condition.
     */
    private int countPaths(T start, DFSCondition stop, DFSCondition filter) {
        int total = 0;

        // Start a DFS from each neighbour
        for (T neighbour : getNeighbours(start)) {
            GraphPath path = new GraphPath();
            path.appendNode(start, 0);
            path.appendNode(neighbour, weightForEdge(start, neighbour));
            total += dfs(neighbour, stop, filter, path);
        }

        return total;
    }

    /**
     * Count number of paths stating from currentNode and ending at destinationNode using Depth First Search (DFS).
     * <p>
     * The algorithms traverses from currentNode to adjacent nodes (neighbours) until we get to the destination node
     * or until we reach maximum depth of maxHops.
     *
     * @param currentNode search start node.
     * @param stop        {@code DFSCondition} that evaluates to true if current search should be aborted for current path.
     * @param filter      {@code DFSCondition} that evaluates to true if current path should be counted.
     * @param path        current path.
     * @return
     */
    private int dfs(T currentNode, DFSCondition stop, DFSCondition filter, GraphPath path) {
        int total = 0;

        if (filter.evaluate(path)) {
            // Count path as valid
            total++;
        }

        for (T neighbour : getNeighbours(currentNode)) {

            path.appendNode(neighbour, weightForEdge(currentNode, neighbour));

            if (stop.evaluate(path)) {
                path.removeLastNode(weightForEdge(currentNode, neighbour));
                continue; // go to next neighbour
            } else {
                total += dfs(neighbour, stop, filter, path);
            }

            path.removeLastNode(weightForEdge(currentNode, neighbour));
        }
        return total;
    }


    /**
     * Confirm nodes exist in the graph.
     *
     * @throws NoSuchElementException if either node does not exist in the graph.
     */
    private void validateInputNodes(T... nodes) {
        for (T node : nodes) {
            if (!contains(node)) {
                throw new NoSuchElementException(LLPropertyFactory.getProperties().get("exception.directed_graph.no_such_element"));
            }
        }
    }

    /***************************************************************************************************/
    /*                                      PUBLIC METHODS                                             *
    /***************************************************************************************************/

    /**
     * Get GraphPath representing the shortest path between start and dest.
     *
     * @param start start node.
     * @param dest  node which we want to reach.
     * @return {@code GraphPath} representing the shortest path between start and dest.
     * @throws NoSuchElementException    if either node does not exist in the graph.
     * @throws NodeNotReachableException if {@code dest} is unreachable.
     */
    public GraphPath shortestPathBetween(T start, T dest) throws NodeNotReachableException {
        validateInputNodes(start, dest);

        dijkstra(start, dest);

        GraphNode destNode = graph.get(dest);
        GraphPath path = destNode.path(graph.get(start));

        if (path.getNodes().isEmpty()) {
            throw new NodeNotReachableException(LLPropertyFactory.getProperties().get("exception.directed_graph.node_not_reachable"));
        } else {
            return path;
        }
    }

    /**
     * Get total distance of route.
     *
     * @param nodes array of nodes as they should be traversed.
     * @return total distance of route.
     * @throws NoSuchElementException    if a node in the route does not exist in the graph.
     * @throws NodeNotReachableException if node is unreachable.
     */
    public int distance(List<T> nodes) throws NodeNotReachableException {
        validateInputNodes((T[]) nodes.toArray());

        int distance = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            T start = nodes.get(i);
            T dest = nodes.get(i + 1);

            if (edgeExists(start, dest)) {
                distance += weightForEdge(start, dest);
            } else {
                throw new NodeNotReachableException(LLPropertyFactory.getProperties().get("exception.directed_graph.invalid_edge"));
            }

        }

        return distance;
    }

    /**
     * Count number of possible routes starting from start and ending at dest with a maximum number of maxHops.
     *
     * @param start   starting node of route.
     * @param dest    destination node of route.
     * @param maxHops maximum number of stops in route.
     * @return number of possible routes.
     */
    public int countRoutesWithMaxHops(T start, T dest, int maxHops) {
        validateInputNodes(start, dest);

        return countPaths(start, (p) -> {
            return p.hopCount() > maxHops;
        }, (p) -> {
            return dest.equals(p.last());
        });
    }

    /**
     * Count number of possible routes starting from start and ending at dest with a exactly number of hops.
     *
     * @param start starting node of route.
     * @param dest  destination node of route.
     * @param hops  number of stops in route.
     * @return number of possible routes.
     */
    public int countRoutesWithHops(T start, T dest, int hops) {
        validateInputNodes(start, dest);

        return countPaths(start, (p) -> {
            return p.hopCount() > hops;
        }, (p) -> {
            return dest.equals(p.last()) && p.hopCount() == hops;
        });
    }

    /**
     * Count number of possible routes starting from start and ending at dest with a distance less equal to distance.
     *
     * @param start    starting node of route.
     * @param dest     destination node of route.
     * @param distance maximum  allowed distance.
     * @return number of possible routes.
     */
    public int countRoutesWithMaxDistance(T start, T dest, int distance) {
        validateInputNodes(start, dest);

        return countPaths(start, (p) -> {
            return p.distance() > distance;
        }, (p) -> {
            return dest.equals(p.last()) && p.distance() <= distance;
        });
    }
}
