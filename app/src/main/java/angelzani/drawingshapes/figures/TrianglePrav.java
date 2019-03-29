package angelzani.drawingshapes.figures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;

public class TrianglePrav extends Figure {
    public TrianglePrav(Context context) {
        super(context);
        this.type = 9;
    }
    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen

    public void drawTrianglePrav(int coordX, int coordY, int width, int height, String fillColor, int strokeWidth, String strokeColor) {

        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;

        this.getLayoutParams().width = width;
        this.getLayoutParams().height = height;

        this.setX(coordX);
        this.setY(coordY);

        //Първи тръигълник, който ще се запълни (реална големина)

        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        /*paint.setColor(Color.parseColor("#ff0000")); //testing
        canvas.drawPaint(paint);*/

        paint.setStrokeWidth(0);
        paint.setColor(Color.parseColor(fillColor));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        android.graphics.Point a = new android.graphics.Point(0, height);
        android.graphics.Point b = new android.graphics.Point(width, height);
        android.graphics.Point c = new android.graphics.Point(0, 0);

        Path path = new Path();
        path.setLastPoint(a.x, a.y); // A
        path.lineTo(b.x, b.y); // B
        path.lineTo(c.x, c.y); // C
        path.lineTo(a.x, a.y); // A
        path.close();

        canvas.drawPath(path, paint);

        //Втори тръигълник, който ще няма да се запълва, а само ще аутлайнва
        // (линиите, който го очертават трябва да влизат вътре в големия до половината аутлайн, понеже самият аутлайн минава от двете страни на линиите...)

        Paint paint2 = new Paint();

        paint2.setStrokeWidth(strokeWidth);
        paint2.setColor(Color.parseColor(strokeColor));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);

        android.graphics.Point a2 = new android.graphics.Point(a.x+strokeWidth/2, a.y-strokeWidth/2);
        android.graphics.Point b2 = new android.graphics.Point((int) (b.x-strokeWidth*1.2), b.y-strokeWidth/2);
        android.graphics.Point c2 = new android.graphics.Point(c.x+strokeWidth/2, (int) (c.y+strokeWidth*1.2));

        Path path2 = new Path();
        path2.setLastPoint(a2.x, a2.y); // A
        path2.lineTo(b2.x, b2.y); // B
        path2.lineTo(c2.x, c2.y); // C
        path2.lineTo(a2.x, a2.y); // A
        path2.close();

        canvas.drawPath(path2, paint2);

        BitmapDrawable bmpDrawable = new BitmapDrawable(getResources(), bitmap);
        this.setBackground(bmpDrawable);
    }

}