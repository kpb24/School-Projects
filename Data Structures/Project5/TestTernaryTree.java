import java.io.*;
import java.util.Iterator;
public class TestTernaryTree{
	//Testing the tree
	//Kerilee Bookleiner
		public static void main(String[] args){
		
		//TEST setTree
		TernaryTreeInterface<String> dTree = new TernaryTree<>();
		dTree.setTree("D");
		TernaryTreeInterface<String> fTree = new TernaryTree<>();
		fTree.setTree("F");
		TernaryTreeInterface<String> gTree = new TernaryTree<>();
		gTree.setTree("G");
		TernaryTreeInterface<String> hTree = new TernaryTree<>();
		hTree.setTree("H");
		TernaryTreeInterface<String> jTree = new TernaryTree<>();
		jTree.setTree("J");
		TernaryTreeInterface<String> kTree = new TernaryTree<>();
		kTree.setTree("K");
		TernaryTreeInterface<String> lTree = new TernaryTree<>();
		lTree.setTree("L");
		TernaryTreeInterface<String> nTree = new TernaryTree<>();
		nTree.setTree("N");
		TernaryTreeInterface<String> oTree = new TernaryTree<>();
		oTree.setTree("O");
		TernaryTreeInterface<String> emptyTree = new TernaryTree<>();
		//TEST setTree
		TernaryTreeInterface<String> eTree = new TernaryTree<>();
		eTree.setTree("E", fTree, gTree, hTree);
		TernaryTreeInterface<String> iTree = new TernaryTree<>();
		iTree.setTree("I", jTree, emptyTree, lTree);
		TernaryTreeInterface<String> mTree = new TernaryTree<>();
		mTree.setTree("M", nTree, iTree, oTree);
		
		//TEST getRootData
		System.out.println("The root of ETREE contains: " + eTree.getRootData());
		System.out.println("The root of MTREE contains: " + mTree.getRootData() + "\n");
		
		
		//TEST isEmpty
		if(eTree.isEmpty() == false){
			System.out.println("ETREE is not empty");
		}
		if(mTree.isEmpty() == false){
			System.out.println("MTREE is not empty \n");
		}
		
		//TEST getHeight
		System.out.println("Height of ETREE: " + eTree.getHeight());
		System.out.println("Height of MTREE: " + mTree.getHeight() + "\n");
		
		//TEST numberOfNodes
		System.out.println("Number of nodes ETREE: " + eTree.getNumberOfNodes());
		System.out.println("Number of nodes MTREE: " + mTree.getNumberOfNodes() + "\n");
		
		
		//TEST preorderTraversal
		System.out.println("Preorder Traveral of ETREE: ");
		Iterator<String> preorderE = eTree.getPreorderIterator();
		while(preorderE.hasNext()){
			System.out.println(preorderE.next() + "");
			System.out.println();
		}
		
		System.out.println("Preorder Traveral of MTREE: ");
		Iterator<String> preorderM = mTree.getPreorderIterator();
		while(preorderM.hasNext()){
			System.out.println(preorderM.next() + "");
			System.out.println();
		}
		
		
		//TEST postorderTraversal
		System.out.println("Postorder Traveral of ETREE: ");
		Iterator<String> postorderE = eTree.getPostorderIterator();
		while(postorderE.hasNext()){
			System.out.println(postorderE.next() + "");
			System.out.println();
		}
		
		System.out.println("Postorder Traveral of MTREE: ");
		Iterator<String> postorderM = mTree.getPostorderIterator();
		while(postorderM.hasNext()){
			System.out.println(postorderM.next() + "");
			System.out.println();
		}
		
		
		//TEST levelorderTraversal
		System.out.println("LevelOrder Traveral of ETREE: ");
		Iterator<String> levelOrderE = eTree.getLevelOrderIterator();
		while(levelOrderE.hasNext()){
			System.out.println(levelOrderE.next() + "");
			System.out.println();
		}
		
		System.out.println("LevelOrder Traveral of MTREE: ");
		Iterator<String> levelOrderM = mTree.getLevelOrderIterator();
		while(levelOrderM.hasNext()){
			System.out.println(levelOrderM.next() + "");
			System.out.println();
		}
	}
}