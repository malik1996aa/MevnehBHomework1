import java.util.Scanner;

public class Tester {

	public static BinSearchTree<Integer> t1 = new  BinSearchTree<Integer>();
	public static BinSearchTree<Integer> t2 = new  BinSearchTree<Integer>();

	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		buildSearchTree();
		t1.inOrder();
		try {
			System.out.println ("\n Minimum is: "+ t1.smallest()) ;
			System.out.println ("\n Maximum is: "+ t1.biggest()) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.print();	
		deleteDemo();
		System.out.println ("Done....") ;
	}
	//End of main()
/*********************************************************************************/
	// delete nodes
	public static void deleteDemo() {
		int num;
		System.out.println("-- Delete nodes --");
		do {
			System.out.print ("type number (-1 to finish) --> ");
			num = input.nextInt();
			if (num == -1)
				break;
			t1.delete(num);
			System.out.print ("\nRemoving "+num+":\n ");
			t1.print();
		}while (true);
	}
		

	// build tree
	public static void buildSearchTree() {
		int num;
		System.out.println("-- Build Search Tree --");
		do {
			System.out.print ("type number (-1 to finish) --> ");
			num = input.nextInt();
			if (num != -1)
				t1.add(num);
		}while (num != -1);
	}//end of buildSearchTree()
}
