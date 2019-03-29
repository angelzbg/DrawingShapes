package angelzani.drawingshapes.figures;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;

public class Point extends Figure {
    public Point(Context context) {
        super(context);
        this.type = 1;
    }
    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen

    public void drawPoint(int coordX, int coordY, int thickness, String fillColor, int strokeWidth, String strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;

        this.getLayoutParams().width = thickness;
        this.getLayoutParams().height = thickness;

        this.setX(coordX-thickness/2);
        this.setY(coordY-thickness/2);

        /*ConstraintSet cs = new ConstraintSet();
        cs.clone( ((ConstraintLayout) this.getParent()) );
        cs.connect(this.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, this.marginTOP);
        cs.connect(this.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, this.marginSTART);
        cs.applyTo( ((ConstraintLayout) this.getParent()) );*/

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(this.getLayoutParams().width, this.getLayoutParams().height);
        gradientDrawable.setColor(Color.parseColor(fillColor));
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setStroke(strokeWidth, Color.parseColor(strokeColor));
        this.setBackground(gradientDrawable);
    }

}
