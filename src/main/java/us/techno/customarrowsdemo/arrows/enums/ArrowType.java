package us.techno.customarrowsdemo.arrows.enums;

public enum ArrowType {
    BUNDLE(100, "Bundle"),
    DIAMOND(200, "Diamond"),
    END_CRYSTAL(400, "End Crystal"),
    FISH(500, "Fish"),
    INFINITY(600, "Infinity");

    private final int modelData;
    private final String name;


    public int getModelData(){
        return modelData;
    }

    public String getName() {
        return name;
    }
    ArrowType(int i, String s) {
        this.modelData = i;
        this.name = s;
    }
}
