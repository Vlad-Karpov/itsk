package design.patterns.structural.bridge;

public class ProgramChess implements Program {

    private ProgramTool tool;

    public ProgramChess(ProgramTool tool) {
        this.tool = tool;
    }

    @Override
    public void createProgram() {
        System.out.println("Develop Chess program!");
        tool.writeCode();
    }

}
