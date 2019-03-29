package angelzani.drawingshapes.figures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;

public class Izpit extends Figure {
    public Izpit(Context context) {
        super(context);
        this.type = 10;
        this.fillColor = "#ffffff"; // бяло
        this.strokeColor = "#000000"; // черно
    }
    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen; 10 = Izpit

    public void drawIzput(int coordX, int coordY, int width, int height, String fillColor, int strokeWidth, String strokeColor) {

        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;

        this.getLayoutParams().width = width;
        this.getLayoutParams().height = height;

        this.setX(coordX);
        this.setY(coordY);

        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        /*paint.setColor(Color.parseColor("#ff0000")); //testing
        canvas.drawPaint(paint);*/

        paint.setStrokeWidth(0);
        paint.setColor(Color.parseColor(fillColor));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        // Изчертаваме обикновен четириъгълник
        Path path = new Path();
        path.moveTo(0, 0); // горна дясна страна (малко излишно)
        path.lineTo(width, 0); // горна дясна страна
        path.lineTo(width, height); // долна дясна страна
        path.lineTo(0, height); // долна лява страна
        path.lineTo(0, 0); // горна дясна страна
        path.close();

        canvas.drawPath(path, paint); // изчертаваме (ще се запълни с цвета)

        Paint paint2 = new Paint();

        paint2.setStrokeWidth(strokeWidth);
        paint2.setColor(Color.parseColor(strokeColor));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);

        // Ред е на по-сложния инлайн
        int step = strokeWidth/2; // понеже отива от двете страни трябва да го преместим половин стъпка навътре
        Path path2 = new Path();
        path2.moveTo(0+step*2, 0+step); // горна лява страна (малко излишно) -> step*2 се компенсира от завършека path2.lineTo(0+step, 0); горна лява страна -> 0
        path2.lineTo(width-step, 0+step); // горна дясна страна
        path2.lineTo(width-step, height-step); // долна дясна страна
        path2.lineTo(0+step, height-step); // долна лява страна
        path2.lineTo(0+step, 0); // горна лява страна
        // Лините от центъра към краищата
        path2.moveTo(width/2, height/2); // център на фигурата
        path2.lineTo(0+step, height-step); // долна лява страна
        path2.moveTo(width/2, height/2); // център на фигурата
        path2.lineTo(width-step, height-step); // долна дясна страна
        path2.moveTo(width/2, height/2); // център на фигурата
        path2.lineTo(width/2, 0); // горен център
        path2.close();

        canvas.drawPath(path2, paint2);

        BitmapDrawable bmpDrawable = new BitmapDrawable(getResources(), bitmap);
        this.setBackground(bmpDrawable);
    }
}
