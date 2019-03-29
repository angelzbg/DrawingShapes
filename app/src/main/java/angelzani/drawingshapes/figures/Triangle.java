package angelzani.drawingshapes.figures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;

public class Triangle extends Figure {
    public Triangle(Context context) {
        super(context);
        this.type = 5;
    }
    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen

    public void drawTriangle(int coordX, int coordY, int sideLen, String fillColor, int strokeWidth, String strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;

        //Съотношение:
        double suotnoshenie = 0.857;

        this.getLayoutParams().width = sideLen;
        this.getLayoutParams().height = (int) Math.floor(sideLen*suotnoshenie);

        this.setX(coordX);
        this.setY(coordY);

        //Първи тръигълник, който ще се запълни (реална големина)

        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(sideLen, (int) Math.floor(sideLen*suotnoshenie), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        /*paint.setColor(Color.parseColor("#ff0000")); //testing
        canvas.drawPaint(paint);*/

        paint.setStrokeWidth(0);
        paint.setColor(Color.parseColor(fillColor));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        /*android.graphics.Point a = new android.graphics.Point(0, (int) (sideLen*suotnoshenie));
        android.graphics.Point b = new android.graphics.Point(sideLen, (int) (sideLen*suotnoshenie));
        android.graphics.Point c = new android.graphics.Point(sideLen/2, 0);*/

        Path path = new Path();
        path.setLastPoint(0, (float) (sideLen*suotnoshenie)); // A
        path.lineTo(sideLen, (float) (sideLen*suotnoshenie)); // B
        path.lineTo((float)(sideLen/2), 0); // C
        path.lineTo(0, (float) (sideLen*suotnoshenie)); // A
        path.close();

        canvas.drawPath(path, paint);

        //Втори тръигълник, който ще няма да се запълва, а само ще аутлайнва
        // (линиите, който го очертават трябва да влизат вътре в големия до половината аутлайн, понеже самият аутлайн минава от двете страни на линиите...)

        Paint paint2 = new Paint();

        paint2.setStrokeWidth(strokeWidth);
        paint2.setColor(Color.parseColor(strokeColor));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);

        /*android.graphics.Point a1 = new android.graphics.Point((int) (a.x+(strokeWidth+strokeWidth*suotnoshenie)/2), a.y-strokeWidth/2);
        android.graphics.Point b1 = new android.graphics.Point((int) (b.x-(strokeWidth+strokeWidth*suotnoshenie)/2), b.y-strokeWidth/2);
        android.graphics.Point c1 = new android.graphics.Point(c.x, (int) (c.y+strokeWidth));*/

        Path path2 = new Path();
        path2.setLastPoint((float) (0+(strokeWidth*1.75)/2), (float) ((sideLen*suotnoshenie)-strokeWidth/2)); // A
        path2.lineTo((float) (sideLen-(strokeWidth*1.75)/2), (float) ((sideLen*suotnoshenie)-strokeWidth/2)); // B
        path2.lineTo((float) (sideLen/2), (float) (0+strokeWidth)); // C
        path2.lineTo((float) (0+(strokeWidth*1.75)/2), (float) ((sideLen*suotnoshenie)-strokeWidth/2)); // A
        path2.close();

        canvas.drawPath(path2, paint2);

        BitmapDrawable bmpDrawable = new BitmapDrawable(getResources(), bitmap);
        this.setBackground(bmpDrawable);
    }

}