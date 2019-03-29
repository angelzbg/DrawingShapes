package angelzani.drawingshapes.database;

public class FigureDB {
    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon
    public int type = 0, width = 0, height = 0, x = 0, y = 0, rotation = 0;
    public String title = null;
    public String fillColor = "#000000", strokeColor = "#00000000";
    public int strokeWidth = 0;

    /*public FigureDB(int type, String title, String fillColor, String strokeColor, int strokeWidth, int width, int height, int x, int y) {
        this.type = type;
        this.title = title;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }*/
}