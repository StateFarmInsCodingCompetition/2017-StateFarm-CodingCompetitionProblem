package sf.codingcomp.blocks.solution;

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
	}

}
