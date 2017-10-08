package sf.codingcomp.blocks.solution;
import sf.codingcomp.blocks.*;
import java.util.*;

public class ExtraDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PolyBlockImpl nodes[] = new PolyBlockImpl[5];
		for (int i=0; i<5; ++i) {
			nodes[i] = new PolyBlockImpl();
		}
		nodes[0].connect(nodes[1]);
		nodes[0].connect(nodes[4]);
		nodes[1].connect(nodes[2]);
		nodes[2].connect(nodes[3]);
		nodes[3].connect(nodes[1]);
		nodes[0].printGraphStats();
		
		ArrayList<BuildingBlock> arr = new ArrayList<BuildingBlock>();
		for (int i=0; i<10; ++i) {
			arr.add(new BuildingBlockImpl());
		}
		arr.get(2).stackOver(arr.get(4));
		arr.get(4).stackOver(arr.get(5));
		
		arr.get(6).stackUnder(arr.get(7));
		arr.get(1).stackUnder(arr.get(9));
		BuildingBlockImpl.printConnections(arr);
	}

}
