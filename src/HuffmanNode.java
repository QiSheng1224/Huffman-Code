/**
 * this class is used to create a node for the Huffman tree
 * @author Mohamed Bashir
 */

public class HuffmanNode {
    private HuffmanNode one;
    private HuffmanNode zero;
    private Character data;
    public HuffmanNode(Character data) {
        this.data = data;
    }
    public HuffmanNode(HuffmanNode _zero, HuffmanNode _one) {
        this.zero = _zero;
        this.one = _one;
        this.data = null;
    }
  
    // getters and setters
    public HuffmanNode getOne() {
        return one;
    }
    public HuffmanNode getZero() {
        return zero;
    }
    public void setOne(HuffmanNode one) {
        this.one = one;
    }
    public void setZero(HuffmanNode zero) {
        this.zero = zero;
    }
    public Character getData() {
        return data;
    }
    public void setData(Character data) {
        this.data = data;
    }
    /**
     * check if the node is a leaf
     * a node is leaf if it has no children nodes
     * @return true if leaf else false
     */
    public boolean isLeaf() {
        return one == null && zero == null;
    }
    /**
     * check if the node is valid
     * a node is valid if it has a data or if it has children nodes and all children nodes are valid
     * @return true if valid else false
     */
    public boolean isValid() {

        //base case: it is leaf node
        if(one == null && zero == null && data != null) {
            return true;
        }
        if(one == null || zero == null) {
            return false;
        }
        //recursive case: checking if all children nodes are valid
        return one.isValid() && zero.isValid();

    }

}

