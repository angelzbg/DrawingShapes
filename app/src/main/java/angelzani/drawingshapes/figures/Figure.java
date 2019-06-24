package angelzani.drawingshapes.figures;

import android.content.Context;
import android.view.View;

public abstract class Figure extends View implements FigureInterface{
    public Figure(Context context) {
        super(context);
    }
    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = TrianglePrav; 10 = Изпитна фигура
    // Не ми се занимава с гетъри, сетъри или конструктори
    public int type=0;
    public String title=null;
    public String fillColor="#000000", strokeColor="#00000000"; // fill - черен, stroke - прозрачен (при създаване на фигура)
    public int strokeWidth = 0;
}
