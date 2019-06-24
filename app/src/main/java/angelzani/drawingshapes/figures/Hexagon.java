package angelzani.drawingshapes.figures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;

public class Hexagon extends Figure {
    public Hexagon(Context context) {
        super(context);
        this.type = 8;
    }
    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen

    public void drawHexagon(int coordX, int coordY, int diameter, String fillColor, int strokeWidth, String strokeColor) {

        int radius = diameter/2;

        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;

        int numberOfSides = 6;
        final float section = (float) (2.0 * Math.PI / numberOfSides);

        this.getLayoutParams().width = radius*2;
        this.getLayoutParams().height = radius*2;

        this.setX(coordX);
        this.setY(coordY);

        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(radius*2, radius*2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        /*paint.setColor(Color.parseColor("#ff0000")); //testing
        canvas.drawPaint(paint);*/

        paint.setStrokeWidth(0);
        paint.setColor(Color.parseColor(fillColor));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);


        Path path = new Path();
        path.moveTo((radius + radius * (float)Math.cos(0)), (radius + radius * (float)Math.sin(0)));
        for (int i = 1; i < numberOfSides; i++) {
            path.lineTo((radius + radius * (float)Math.cos(section * i)), (radius + radius * (float)Math.sin(section * i)));
        }
        path.close();
        canvas.drawPath(path, paint);

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(strokeWidth);
        paint2.setColor(Color.parseColor(strokeColor));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);

        int radius2 = (int) (radius-(strokeWidth/2)*1.2);

        Path path2 = new Path();
        path2.moveTo((int)((radius + radius2 * (float)Math.cos(0)) ), (int)((radius + radius2 * (float)Math.sin(0)) ));
        for (int i = 1; i < numberOfSides; i++) {
            path2.lineTo((int)((radius + radius2 * (float)Math.cos(section * i)) ), (int)((radius + radius2 * (float)Math.sin(section * i)) ));
        }
        path2.close();
        canvas.drawPath(path2, paint2);

        BitmapDrawable bmpDrawable = new BitmapDrawable(getResources(), bitmap);
        this.setBackground(bmpDrawable);
    }

    @Override
    public void drawShape(int coordX, int coordY, int diameter, String fillColor, int strokeWidth, String strokeColor) {
        this.drawHexagon(coordX, coordY, diameter, fillColor, strokeWidth, strokeColor);
    }

    @Override
    public void drawShape(int int1, int int2, int int3, int int4, String str1, int int5, String str2) { }
}