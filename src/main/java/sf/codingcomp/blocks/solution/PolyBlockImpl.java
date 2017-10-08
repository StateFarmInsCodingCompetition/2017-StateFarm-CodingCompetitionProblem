package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl
  implements PolyBlock {

  private List<PolyBlock> connectedPolyBlocks;

  public PolyBlockImpl() {
    this.connectedPolyBlocks = new LinkedList<PolyBlock>();
  }

  @Override
  public Iterator<PolyBlock> iterator() {

    return new PolyBlockIterator( this );
  }

  @Override
  public void connect( PolyBlock aPolyBlock ) {

    if ( aPolyBlock == null ) {
      return;
    }

    if ( aPolyBlock == this ) {
      return;
    }

    if ( this.contains( aPolyBlock ) ) {
      return;
    }

    this.connectedPolyBlocks.add( aPolyBlock );

    if ( !aPolyBlock.contains( this ) ) {
      aPolyBlock.connect( this );
    }
  }

  @Override
  public void disconnect( PolyBlock aPolyBlock ) {

    if ( aPolyBlock == null ) {
      return;
    }

    if ( aPolyBlock == this ) {
      return;
    }

    this.connectedPolyBlocks.remove( aPolyBlock );

    if ( aPolyBlock.contains( this ) ) {
      aPolyBlock.disconnect( this );
    }
  }

  @Override
  public boolean contains( PolyBlock aPolyBlock ) {

    return this.connectedPolyBlocks.contains( aPolyBlock );
  }

  @Override
  public int connections() {

    return this.connectedPolyBlocks.size();
  }

  @Override
  public int size() {

    List<PolyBlock> traversed = new LinkedList<PolyBlock>();
    List<PolyBlock> discovered = new LinkedList<PolyBlock>();

    discovered.add( this );

    this.traverseGraph(
      traversed,
      discovered
    );

    return traversed.size();
  }

  @Override
  public PolyBlock copy() {

    List<PolyBlock> traversed = new ArrayList<PolyBlock>();
    List<PolyBlock> discovered = new LinkedList<PolyBlock>();

    discovered.add( this );

    this.traverseGraph(
      traversed,
      discovered
    );

    List<PolyBlock> cloned = new ArrayList<PolyBlock>();

    for (
      int i = 0;
      i < traversed.size();
      i++
    ) {
      PolyBlock clone = new PolyBlockImpl();
      cloned.add( clone );
    }

    for (
      int currentIndex = 0;
      currentIndex < traversed.size();
      currentIndex++
    ) {
      PolyBlock currentOriginal = traversed.get( currentIndex );
      PolyBlock currentClone = cloned.get( currentIndex );

      List<PolyBlock> originalConnections = currentOriginal.getConnectedPolyBlocks();

      for (
        PolyBlock connectedOriginal : originalConnections
      ) {
        int connectedIndex = traversed.indexOf( connectedOriginal );

        if ( connectedIndex > currentIndex ) {
          PolyBlock connectedClone = cloned.get( connectedIndex );

          currentClone.connect( connectedClone );
        }
      }
    }

    return cloned.get( 0 );
  }

  @Override
  public boolean equals( Object obj ) {
    if (
      obj
        .getClass()
        .isInstance( PolyBlock.class )
    ) {

      Iterator<PolyBlock> iteratorA = this.iterator();
      Iterator<PolyBlock> iteratorB = ((PolyBlock)obj).iterator();

      while (
        iteratorA.hasNext() && iteratorB.hasNext()
      ) {
        PolyBlock aNext = iteratorA.next();
        PolyBlock bNext = iteratorB.next();

        if ( aNext.connections() != bNext.connections() ) {
          return false;
        }
      }

      // one still has nodes left
      if (
        iteratorA.hasNext()
        || iteratorB.hasNext()
      ) {
        return false;
      }

      return true;
    }

    return super.equals( obj );
  }

  public List<PolyBlock> getConnectedPolyBlocks() {

    return this.connectedPolyBlocks;
  }

  private void traverseGraph(
    List<PolyBlock> traversed,
    List<PolyBlock> discovered
  ) {
    while ( discovered.size() > 0 ) {
      PolyBlock current = discovered.remove( 0 );

      traversed.add( current );

      List<PolyBlock> connections = current.getConnectedPolyBlocks();

      for (
        PolyBlock connected : connections
        ) {
        // check if undiscovered node
        if (
          !traversed.contains( connected )
          && !discovered.contains( connected )
          ) {
          discovered.add( connected );
        }
      }
    }
  }

  private class PolyBlockIterator
    implements Iterator<PolyBlock> {

    private List<PolyBlock> traversed;
    private List<PolyBlock> discovered;

    private PolyBlock current;
    private PolyBlock next;

    public PolyBlockIterator( PolyBlock start ) {

      this.traversed = new LinkedList<PolyBlock>();
      this.discovered = new LinkedList<PolyBlock>();
      this.discovered.add( start );

      // run through the graph once
      while ( discovered.size() > 0 ) {
        PolyBlock current = discovered.remove( 0 );

        traversed.add( current );

        List<PolyBlock> connections = current.getConnectedPolyBlocks();

        for (
          PolyBlock connected : connections
          ) {
          // check if undiscovered node
          if (
            !traversed.contains( connected )
            && !discovered.contains( connected )
            ) {
            discovered.add( connected );
          }
        }
      }
      // shift nodes back to discovered
      this.discovered = this.traversed;
      this.traversed = new LinkedList<PolyBlock>();

      this.discovered.remove( start );

      this.current = null;
      this.next = start;
    }

    @Override
    public boolean hasNext() {

      return this.next != null;
    }

    @Override
    public PolyBlock next() {

      this.current = this.next;

      if( this.discovered.size() > 0 ) {
        this.next = this.discovered.remove( 0 );
      }
      else {
        this.next = null;
      }

      return current;
    }

    @Override
    public void remove() {

      if ( this.current == null ) {
        throw new IllegalStateException();
      }

      this.current = null;
    }
  }
}
