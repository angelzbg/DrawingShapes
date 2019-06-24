package angelzani.drawingshapes.figures;

        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.drawable.GradientDrawable;
        import android.support.constraint.ConstraintLayout;
        import android.support.constraint.ConstraintSet;

public class Circle extends Figure {
    public Circle(Context context) {
        super(context);
        this.type = 3;
    }

    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen

    public void drawCircle(int coordX, int coordY, int diametur, String fillColor, int strokeWidth, String strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;

        this.getLayoutParams().width = diametur;
        this.getLayoutParams().height = diametur;

        this.setX(coordX);
        this.setY(coordY);

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

    @Override
    public void drawShape(int coordX, int coordY, int diametur, String fillColor, int strokeWidth, String strokeColor) {
        this.drawCircle(coordX, coordY, diametur, fillColor, strokeWidth, strokeColor);
    }

    @Override
    public void drawShape(int int1, int int2, int int3, int int4, String str1, int int5, String str2) { }
}