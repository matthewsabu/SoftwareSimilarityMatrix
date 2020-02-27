package metrics;
/**
 * @author Ahmed Metwally
 *
 */

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SuppressWarnings("WeakerAccess")
public class MetricMachine {
	//CHANGE: Renamed to MetricMachine
	// construct AST of the .java files	
	private static ASTVisitorMod parse(char[] str) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(str);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		// Check for compilationUnits problems in the provided file
		IProblem[] problems = cu.getProblems();
		for (IProblem problem : problems) {
			// Ignore some error because of the different versions.			
//            if (problem.getID() == 1610613332) 		 // 1610613332 = Syntax error, annotations are only available if source level is 5.0
//                continue;
//            else if (problem.getID() == 1610613329) // 1610613329 = Syntax error, parameterized types are only available if source level is 5.0
//                continue;
//            else if (problem.getID() == 1610613328) // 1610613328 = Syntax error, 'for each' statements are only available if source level is 5.0
//                continue;
//            else if (problem.getID() == 16778099) // 16778099 = <>
//            	continue;
//            else if (problem.getID() == 536871353)
//            	continue;
//            else
//            {
			//print out compilation error if encountered
			System.out.println("CompilationUnit problem Message " + problem.getMessage() + " \t At line= " + problem.getSourceLineNumber() + "\t Problem ID=" + problem.getID());
//            }
		}

		// visit nodes of the constructed AST
		ASTVisitorMod visitor = new ASTVisitorMod();
		cu.accept(visitor);

		return visitor;
	}


	// parse file in char array
	private static char[] ReadFileToCharArray(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();

		return fileData.toString().toCharArray();
	}


	// parse files in a directory to list of char array
	private static List<char[]> ParseFilesInDir(List<String> JavaFiles) throws IOException {
		if (JavaFiles.isEmpty()) {
			System.out.println("There is no java source code in the provided directory");
			System.exit(0);
		}

		List<char[]> FilesRead = new ArrayList<char[]>();

		for (int i = 0; i < JavaFiles.size(); i++) {
			System.out.println("Now, reading: " + JavaFiles.get(i));
			FilesRead.add(ReadFileToCharArray(JavaFiles.get(i)));
		}

		return FilesRead;
	}


	// retrieve all .java files in the directory and subdirectories.
	private static List<String> retrieveFiles(String directory) {
		List<String> Files = new ArrayList<String>();
		File dir = new File(directory);

		if (!dir.isDirectory()) {
			System.out.println("The provided path is not a valid directory");
			System.exit(1);
		}

		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				Files.addAll(retrieveFiles(file.getAbsolutePath()));
			}
			if (file.getName().endsWith((".java"))) {
				Files.add(file.getAbsolutePath());
			}
		}

		return Files;
	}


	public static HalsteadMetrics getMetrics(String pathDirectory) throws IOException {
		// get the Directory name from the user
		System.out.println("Directory Name is: " + pathDirectory);

		// retrieve all .java files in the directory and subdirectories. 
		List<String> JavaFiles = retrieveFiles(pathDirectory);

		// parse files in a directory to list of char array
		List<char[]> FilesRead = ParseFilesInDir(JavaFiles);

		ASTVisitorMod ASTVisitorFile;
		int DistinctOperators = 0;
		int DistinctOperands = 0;
		int TotalOperators = 0;
		int TotalOperands = 0;
		int OperatorCount = 0;
		int OperandCount = 0;

		// Construct the AST of each java file. visit different nodes to get the number of operors and operands
		// Retrieve the number of distinct operators, distinct operands, 
		// total operators, and total operands for each .java file in the directory. 
		// Sum each parameter from different files together to be used in Halstead Complexity metrics. 
		for (int i = 0; i < FilesRead.size(); i++) {
			System.out.println("Now, AST parsing for : " + JavaFiles.get(i));
			ASTVisitorFile = parse(FilesRead.get(i));
			DistinctOperators += ASTVisitorFile.oprt.size();
			DistinctOperands += ASTVisitorFile.names.size();

			OperatorCount = 0;
			for (int f : ASTVisitorFile.oprt.values()) {
				OperatorCount += f;
			}
			TotalOperators += OperatorCount;

			OperandCount = 0;
			for (int f : ASTVisitorFile.names.values()) {
				OperandCount += f;
			}
			TotalOperands += OperandCount;

			System.out.println("Distinct Operators in this .java file = " + ASTVisitorFile.oprt.size());
			System.out.println("Distinct Operands in this .java file = " + ASTVisitorFile.names.size());
			System.out.println("Total Operators in this .java file = " + OperatorCount);
			System.out.println("Total Operands in this .java file = " + OperandCount);
			System.out.println("\n");
		}

		System.out.println("\n");


		// calculate Halstead Complexity Metrics
		HalsteadMetrics hal = new HalsteadMetrics();

		hal.setParameters(DistinctOperators, DistinctOperands, TotalOperators, TotalOperands);
		return hal;
	}
}