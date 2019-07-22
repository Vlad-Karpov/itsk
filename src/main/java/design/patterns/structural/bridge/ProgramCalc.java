package design.patterns.structural.bridge;

public class ProgramCalc implements Program {

    private ProgramTool tool;

    public ProgramCalc(ProgramTool tool) {
        this.tool = tool;
    }

    @Override
    public void createProgram() {
        System.out.println("Develop Calc program!");
        tool.writeCode();
    }

}
