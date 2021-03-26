package Ukonen;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private int from=0 ;
    private int to=0 ;

    private Node suffixLink;
    private Map<Integer, Node> leaves;

    public boolean findEdge(int edge){
        return leaves.containsKey(edge);
    }

    public Node getSuffixLink() {
        return suffixLink;
    }

    public void setSuffixLink(Node suffixLink) {
        this.suffixLink = suffixLink;
    }
    public Node(int from, int to, Map<Integer, Node> leaves, Node suffix) {
        this.from = from;
        this.to = to;
        this.leaves = leaves;
        this.suffixLink=suffix;
    }

    public int length(){
        return to-from+1;
    }

    public Node getLeaf(int i){
        if(leaves!=null && leaves.get(i)!=null ){
            return leaves.get(i);
        }
        else{
            return null;
        }

    }

    public void setLeaf(int i,Node node) {
        if (leaves == null) {
            leaves = new HashMap<>();
        }
        leaves.put(i, node);
    }

    public Map<Integer, Node> getLeaves() {
        return leaves;
    }

    public void setLeaves(Map<Integer, Node> leaves) {
        this.leaves = leaves;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
