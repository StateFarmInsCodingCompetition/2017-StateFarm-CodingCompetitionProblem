package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl
  implements BuildingBlock {

  /**
   * the block above this one
   */
  private BuildingBlock blockAbove;

  /**
   * the block below this one
   */
  private BuildingBlock blockBelow;

  public BuildingBlockImpl() {
  }

  @Override
  public Iterator<BuildingBlock> iterator() {

    Iterator<BuildingBlock> blockIterator = new BuildingBlockIterator( this );

    return blockIterator;
  }

  @Override
  public void stackOver( BuildingBlock block ) {

    // check for null (unstacking)
    if ( block == null ) {
      // check if this has existing connection
      if ( this.findBlockUnder() != null ) {
        BuildingBlock prevBlock = this.findBlockUnder();
        this.blockBelow = null;
        // check if block is still connected to this one
        if ( prevBlock.findBlockOver() == this ) {
          // unstack the previous connection
          prevBlock.stackUnder( null );
        }
      }
      return;
    }

    // check for self reference
    if ( block == this ) {
      return;
    }

    // check for circle
    if ( this.findBlockOver() != null ) {
      BuildingBlock currentBlock = this;
      while ( currentBlock.findBlockOver() != null ) {
        if ( currentBlock == block ) {
          throw new CircularReferenceException();
        }
        currentBlock = currentBlock.findBlockOver();
      }
    }

    if ( block.findBlockUnder() != null ) {
      BuildingBlock currentBlock = block.findBlockUnder();
      while ( currentBlock.findBlockUnder() != null ) {
        if ( currentBlock.findBlockUnder() == this ) {
          throw new CircularReferenceException();
        }
        currentBlock = currentBlock.findBlockUnder();
      }
    }

    // replace existing connection
    if ( this.blockBelow != null ) {
      this.blockBelow.stackUnder( null );
    }

    this.blockBelow = block;

    if ( block.findBlockOver() != this ) {
      block.stackUnder( this );
    }
  }

  @Override
  public void stackUnder( BuildingBlock block ) {

    // check for null (unstacking)
    if ( block == null ) {
      // check if this has existing connection
      if ( this.findBlockOver() != null ) {
        BuildingBlock prevBlock = this.findBlockOver();
        this.blockAbove = null;
        // check if block is still connected to this one
        if ( prevBlock.findBlockUnder() == this ) {
          // unstack the previous connection
          prevBlock.stackOver( null );
        }
      }
      return;
    }

    // check for self reference
    if ( block == this ) {
      return;
    }

    // check for circle
    if ( this.findBlockUnder() != null ) {
      BuildingBlock currentBlock = this;
      while ( currentBlock.findBlockUnder() != null ) {
        if ( currentBlock == block ) {
          throw new CircularReferenceException();
        }
        currentBlock = currentBlock.findBlockUnder();
      }
    }

    if ( block.findBlockOver() != null ) {
      BuildingBlock currentBlock = block.findBlockOver();
      while ( currentBlock.findBlockOver() != null ) {
        if ( currentBlock.findBlockOver() == this ) {
          throw new CircularReferenceException();
        }
        currentBlock = currentBlock.findBlockOver();
      }
    }

    // replace existing connection
    if ( this.blockAbove != null ) {
      this.blockAbove.stackOver( null );
    }

    this.blockAbove = block;

    if ( block.findBlockUnder() != this ) {
      block.stackOver( this );
    }
  }

  @Override
  public BuildingBlock findBlockUnder() {

    return this.blockBelow;
  }

  @Override
  public BuildingBlock findBlockOver() {

    return this.blockAbove;
  }

  /**
   * starts iterating from bottom of the stack
   */
  private class BuildingBlockIterator
    implements Iterator<BuildingBlock> {

    private BuildingBlock currentBlock;
    private BuildingBlock nextBlock;

    public BuildingBlockIterator(
      BuildingBlock startBlock
    ) {

      // move to the bottom of the stack
      while ( startBlock.findBlockUnder() != null ) {
        startBlock = startBlock.findBlockUnder();
      }
      this.currentBlock = null;
      this.nextBlock = startBlock;
    }

    @Override
    public boolean hasNext() {

      return this.nextBlock != null;
    }

    @Override
    public BuildingBlock next() {

      this.currentBlock = this.nextBlock;
      this.nextBlock = this.currentBlock.findBlockOver();

      return this.currentBlock;
    }

    @Override
    public void remove() {
      if ( this.currentBlock == null ) {
        throw new IllegalStateException();
      }
      BuildingBlock blockAbove = this.currentBlock.findBlockOver();
      BuildingBlock blockBelow = this.currentBlock.findBlockUnder();

      this.currentBlock.stackOver( null );
      this.currentBlock.stackUnder( null );

      blockAbove.stackOver( blockBelow );

      this.currentBlock = null;
    }
  }
}
