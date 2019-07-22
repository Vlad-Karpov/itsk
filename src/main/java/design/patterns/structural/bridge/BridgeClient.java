package design.patterns.structural.bridge;

public class BridgeClient {

    public static void main(String[] args) {

        ProgramTool[] tool = {new ProgramToolJava(), new ProgramToolDelphi()};
        Program[] progs = {new ProgramChess(tool[0]), new ProgramCalc(tool[1])};

        for (Program p : progs) {
            p.createProgram();
        }
    }

}
