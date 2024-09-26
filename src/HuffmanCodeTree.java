/**
 * this class is used to build a Huffman tree for faster decoding
 * @author Mohamed Bashir
 */
public class HuffmanCodeTree {
    private HuffmanNode root;

    public HuffmanCodeTree(HuffmanNode root) 
    {
        this.root = root;
    }
    public HuffmanCodeTree(HuffmanCodeBook code_book)
    {
        root = new HuffmanNode(null, null);
        for(Character c : code_book)
        {
            if(c!= null)
            {
                put(code_book.getSequence(c), c);
            }
        }
    }
    /**
     * check if root is valid
     * @return true if valid else false
     */
    public boolean isValid()
    {
        return root.isValid();
    }
    /**
     * put a new node into the tree
     * @param sequence the sequence of the new node
     * @param data the data of the new node
     */
    public void put(BinarySequence seq, char letter)
    {
        HuffmanNode node = root;
        for(boolean b : seq)
        {
            //if child is null, create a new node
            //move to the root node following the sequence
            if(!b)
            {
                if(node.getZero() == null)
                {
                    node.setZero(new HuffmanNode(null, null));
                }
                node = node.getZero();
            }
            if(b)
            {
                if(node.getOne() == null)
                {
                    node.setOne(new HuffmanNode(null, null));
                }
                node = node.getOne();
            }
        }
        // set the data
        if(node.isLeaf())
        {
            node.setData(letter);
            node = root;
        }
    }
    /**
     * decode the given sequence to string representation
     * @param sequence the binarySequence to decode
     */
    public String decode(BinarySequence sequence)
    {

          HuffmanNode curr_node = root;
          StringBuilder sb = new StringBuilder();
          for(boolean bit : sequence)
          {
              //move to the next node
              if(curr_node != null)
              {
                  if(bit && curr_node.getOne() != null)
                  {
                    curr_node = curr_node.getOne();
                  }
                  else if(curr_node.getZero() != null)
                  {
                    curr_node = curr_node.getZero();
                  }
                  //if node has data, append it to the string
                  if(curr_node.isLeaf())
                  {
                      sb.append(curr_node.getData());
                      curr_node = root;
                  }
            }
          }
          //append the last node
          if(curr_node!=null && curr_node.isLeaf())
          {
              sb.append(curr_node.getData());
          }
          return sb.toString();
    }
}
