import java.io.File;
import java.io.IOException;


public class Test {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
	// TODO Auto-generated method stub
	
	File file = new File("file/BeanBuilder.java");
	Index index = new Index();
	index.trimming(file);
	System.out.print("outputCode:");
	for(int i = 0; i < index.code.size(); i++){
	    System.out.print(index.code.get(i)+" ");
	}
	System.out.println();
	System.out.print("outputComment:");
	for(int i = 0; i < index.comment.size(); i++){
	    System.out.print(index.comment.get(i)+" ");
	}
    }

}
