package angelzani.drawingshapes;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import angelzani.drawingshapes.database.DatabaseHelper;
import angelzani.drawingshapes.database.FigureDB;
import angelzani.drawingshapes.figures.Circle;
import angelzani.drawingshapes.figures.Figure;
import angelzani.drawingshapes.figures.Hexagon;
import angelzani.drawingshapes.figures.Izpit;
import angelzani.drawingshapes.figures.Line;
import angelzani.drawingshapes.figures.Oval;
import angelzani.drawingshapes.figures.Pentagon;
import angelzani.drawingshapes.figures.Rectangle;
import angelzani.drawingshapes.figures.Triangle;
import angelzani.drawingshapes.figures.TrianglePrav;

public class MainActivity extends AppCompatActivity {

    //DB
    DatabaseHelper db;

    //Display Metrics
    private int width, height;

    /* ---------- Design ---------- */
    ConstraintLayout CL_Main;

    ConstraintLayout CL_Window1;
    ConstraintLayout CL_Window2;

    //Top Menu
    ConstraintLayout CL_TopMenu;
    ImageView IV_Settings;
    LinearLayout LL_RazdelitelTop;
    ImageView IV_Point, IV_Line, IV_Circle, IV_Square, IV_Triangle, IV_Oval, IV_Pentagon, IV_Hexagon, IV_Triangle_Prav, IV_Izpit;

    //List Figures
    ConstraintLayout CL_ListWrapper;
    TextView TV_Figures;
    LinearLayout LL_RazdelitelFigures;
    ScrollView SV_Figures;
    LinearLayout LL_Figures;
    ImageView IV_ArrowFigures;

    //Windows Previews
    ConstraintLayout CL_Windows;
    LinearLayout LL_RazdelitelPreviews;
    ConstraintLayout CL_Window1Preview, CL_Window2Preview;
    ImageView IV_ArrowWindows;

    //Settings Window
    ConstraintLayout CL_SettingsWindow;
    ConstraintLayout CL_ActualSettings;
    TextView TV_Settings_Text;
    TextView TV_Settings_Resolution;
    TextView TV_Settings_ActualResolution;
    TextView TV_Settings_Title;
    EditText ET_Settings_Title;
    TextView TV_Settings_PaneColor;
    View V_Settings_PaneColor;
    LinearLayout LL_Settings_WrapButtons;
    Button B_Settings_Open;
    Button B_Settings_Save;
    Button B_Settings_Clear;
    Button B_Settings_Close;
        //Color picker in settings
    ConstraintLayout CL_Settings_ColorChooser;
    LinearLayout LL_In_ColorChooser;
    ImageView IV_Settings_C_Red, IV_Settings_C_Green, IV_Settings_C_Blue, IV_Settings_C_Black, IV_Settings_C_White, IV_Settings_C_Gray, IV_Settings_C_Yellow, IV_Settings_C_Pink, IV_Settings_C_Violet, IV_Settings_C_Brown;
    EditText ET_ColorChooser;
    LinearLayout LL_Colors_B_Wrapper;
    ImageView IV_Color_YES, IV_Color_NO;
    HorizontalScrollView HSV_for_LLColors;

    //Figure Settings
    ConstraintLayout CL_FigureSettingsOut;
    ConstraintLayout CL_FigureSettingsIn;
    Space Space_FigSet_Mid;
    EditText ET_FigSet_Title;

    LinearLayout LL_FigSettings_Color1Wrap;
    TextView TV_FigSet_FillColor;
    ConstraintLayout CL_Color1Helper;
    View V_FillColor;

    ConstraintLayout CL_ColorFillWrap;
    ImageView IV_ChooseFillColor;
    EditText ET_FillColor_Hex;
    HorizontalScrollView HSV_FillColor;
    ImageView IV_Fill_00000000, IV_Fill_ffffff, IV_Fill_ffff66, IV_Fill_ff4dff, IV_Fill_9933ff, IV_Fill_0066ff, IV_Fill_00ff00, IV_Fill_ff0000, IV_Fill_993300, IV_Fill_000000, IV_Fill_8c8c8c;

    LinearLayout LL_FigSettings_Dimensions;
    TextView TV_FigSet_Width;
    EditText ET_FigSet_Width;
    TextView TV_FigSet_Height;
    EditText ET_FigSet_Height;

    LinearLayout LL_FigSettings_Coords;
    TextView TV_FigSet_X;
    EditText ET_FigSet_X;
    TextView TV_FigSet_Y;
    EditText ET_FigSet_Y;

    View V_FigSet_Object;
    EditText ET_FigSet_TOP;
    EditText ET_FigSet_BOTTOM;
    EditText ET_FigSet_START;
    EditText ET_FigSet_END;

    TextView TV_FigSet_Stroke;
    EditText ET_FigSet_StrokeSize;

    TextView TV_FigSet_StrokeColor;
    View V_FigSet_StrokeColor;

    ConstraintLayout CL_ColorStrokeWrap;
    ImageView IV_ChooseStrokeColor;
    EditText ET_StrokeColor_Hex;
    HorizontalScrollView HSV_StrokeColor;
    ImageView IV_Stroke_00000000, IV_Stroke_ffffff, IV_Stroke_ffff66, IV_Stroke_ff4dff, IV_Stroke_9933ff, IV_Stroke_0066ff, IV_Stroke_00ff00, IV_Stroke_ff0000, IV_Stroke_993300, IV_Stroke_000000, IV_Stroke_8c8c8c;

    EditText ET_Angle;

    LinearLayout LL_FigSet_ButtonsWrap;
    Button B_FigSet_SAVE, B_FigSet_COPY, B_FigSet_DELETE, B_FigSet_CLOSE;

    /* ---------- Design ---------- */

    // Променливи за работа с височината и ширината на лейаутите за чертане
    private int widthPane, heightPane;

    // Променливи за следене на видимостта на менютата
    private boolean figuresMenu=true, previewMenu=true;

    // Променлива за следене в кой прозорец се намираме ( 0 = прозорец 1; 1 = прозорец 2 )
    private int windowPointer = 0;

    //Класове за прозорците/платната
    private class WindowPane {

        public WindowPane(){
            figures = new ArrayList<Figure>();
        }

        //List с фигури
        ArrayList<Figure> figures;

        //List с групи на фигури
        // много занимавка за без пари -> не мерси
        private String title;
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
    }
    //Масивче
    private WindowPane[] windows;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        hideSystemUI();
        setContentView(R.layout.activity_main);

        //DB
        db = new DatabaseHelper(getApplicationContext());

        /* ---------- Design ---------- */
        CL_Main = findViewById(R.id.CL_Main);

        CL_Window1 = findViewById(R.id.CL_Window1);
        CL_Window2 = findViewById(R.id.CL_Window2);

        //Top Menu
        CL_TopMenu = findViewById(R.id.CL_TopMenu);
        IV_Settings = findViewById(R.id.IV_Settings);
        LL_RazdelitelTop = findViewById(R.id.LL_RazdelitelTop);
        IV_Point = findViewById(R.id.IV_Point);
        IV_Line = findViewById(R.id.IV_Line);
        IV_Circle = findViewById(R.id.IV_Circle);
        IV_Square = findViewById(R.id.IV_Square);
        IV_Triangle = findViewById(R.id.IV_Triangle);
        IV_Oval = findViewById(R.id.IV_Oval);
        IV_Pentagon = findViewById(R.id.IV_Pentagon);
        IV_Hexagon = findViewById(R.id.IV_Hexagon);
        IV_Triangle_Prav = findViewById(R.id.IV_Triangle_Prav);
        IV_Izpit = findViewById(R.id.IV_Izpit);

        //List Figures
        CL_ListWrapper = findViewById(R.id.CL_ListWrapper);
        TV_Figures = findViewById(R.id.TV_Figures);
        LL_RazdelitelFigures = findViewById(R.id.LL_RazdelitelFigures);
        SV_Figures = findViewById(R.id.SV_Figures);
        LL_Figures = findViewById(R.id.LL_Figures);
        IV_ArrowFigures = findViewById(R.id.IV_ArrowFigures);

        //Windows Previews
        CL_Windows = findViewById(R.id.CL_Windows);
        LL_RazdelitelPreviews = findViewById(R.id.LL_RazdelitelPreviews);
        CL_Window1Preview = findViewById(R.id.CL_Window1Preview);
        CL_Window2Preview = findViewById(R.id.CL_Window2Preview);
        IV_ArrowWindows = findViewById(R.id.IV_ArrowWindows);

        //Settings Window
        CL_SettingsWindow = findViewById(R.id.CL_SettingsWindow);
        CL_ActualSettings = findViewById(R.id.CL_ActualSettings);
        TV_Settings_Text = findViewById(R.id.TV_Settings_Text);
        TV_Settings_Resolution = findViewById(R.id.TV_Settings_Resolution);
        TV_Settings_ActualResolution = findViewById(R.id.TV_Settings_ActualResolution);
        TV_Settings_Title = findViewById(R.id.TV_Settings_Title);
        ET_Settings_Title = findViewById(R.id.ET_Settings_Title);
        TV_Settings_PaneColor = findViewById(R.id.TV_Settings_PaneColor);
        V_Settings_PaneColor = findViewById(R.id.V_Settings_PaneColor);
        LL_Settings_WrapButtons = findViewById(R.id.LL_Settings_WrapButtons);
        B_Settings_Open = findViewById(R.id.B_Settings_Open);
        B_Settings_Save = findViewById(R.id.B_Settings_Save);
        B_Settings_Clear = findViewById(R.id.B_Settings_Clear);
        B_Settings_Close = findViewById(R.id.B_Settings_Close);

            //Color picker in settings
        CL_Settings_ColorChooser = findViewById(R.id.CL_Settings_ColorChooser);
        LL_In_ColorChooser = findViewById(R.id.LL_In_ColorChooser);
        IV_Settings_C_Red = findViewById(R.id.IV_Settings_C_Red);
        IV_Settings_C_Green = findViewById(R.id.IV_Settings_C_Green);
        IV_Settings_C_Blue = findViewById(R.id.IV_Settings_C_Blue);
        IV_Settings_C_Black = findViewById(R.id.IV_Settings_C_Black);
        IV_Settings_C_White = findViewById(R.id.IV_Settings_C_White);
        IV_Settings_C_Gray = findViewById(R.id.IV_Settings_C_Gray);
        IV_Settings_C_Yellow = findViewById(R.id.IV_Settings_C_Yellow);
        IV_Settings_C_Pink = findViewById(R.id.IV_Settings_C_Pink);
        IV_Settings_C_Violet = findViewById(R.id.IV_Settings_C_Violet);
        IV_Settings_C_Brown = findViewById(R.id.IV_Settings_C_Brown);
        ET_ColorChooser = findViewById(R.id.ET_ColorChooser);
        LL_Colors_B_Wrapper = findViewById(R.id.LL_Colors_B_Wrapper);
        IV_Color_YES = findViewById(R.id.IV_Color_YES);
        IV_Color_NO = findViewById(R.id.IV_Color_NO);
        HSV_for_LLColors = findViewById(R.id.HSV_for_LLColors);

        //Figure Settings
        CL_FigureSettingsOut = findViewById(R.id.CL_FigureSettingsOut);
        CL_FigureSettingsIn = findViewById(R.id.CL_FigureSettingsIn);
        Space_FigSet_Mid = findViewById(R.id.Space_FigSet_Mid);
        ET_FigSet_Title = findViewById(R.id.ET_FigSet_Title);

        LL_FigSettings_Color1Wrap = findViewById(R.id.LL_FigSettings_Color1Wrap);
        TV_FigSet_FillColor = findViewById(R.id.TV_FigSet_FillColor);
        CL_Color1Helper = findViewById(R.id.CL_Color1Helper);
        V_FillColor = findViewById(R.id.V_FillColor);

        CL_ColorFillWrap = findViewById(R.id.CL_ColorFillWrap);
        IV_ChooseFillColor = findViewById(R.id.IV_ChooseFillColor);
        ET_FillColor_Hex = findViewById(R.id.ET_FillColor_Hex);
        HSV_FillColor = findViewById(R.id.HSV_FillColor);
        IV_Fill_00000000 = findViewById(R.id.IV_Fill_00000000);
        IV_Fill_ffffff = findViewById(R.id.IV_Fill_ffffff);
        IV_Fill_ffff66 = findViewById(R.id.IV_Fill_ffff66);
        IV_Fill_ff4dff = findViewById(R.id.IV_Fill_ff4dff);
        IV_Fill_9933ff = findViewById(R.id.IV_Fill_9933ff);
        IV_Fill_0066ff = findViewById(R.id.IV_Fill_0066ff);
        IV_Fill_00ff00 = findViewById(R.id.IV_Fill_00ff00);
        IV_Fill_ff0000 = findViewById(R.id.IV_Fill_ff0000);
        IV_Fill_993300 = findViewById(R.id.IV_Fill_993300);
        IV_Fill_000000 = findViewById(R.id.IV_Fill_000000);
        IV_Fill_8c8c8c = findViewById(R.id.IV_Fill_8c8c8c);

        LL_FigSettings_Dimensions = findViewById(R.id.LL_FigSettings_Dimensions);
        TV_FigSet_Width = findViewById(R.id.TV_FigSet_Width);
        ET_FigSet_Width = findViewById(R.id.ET_FigSet_Width);
        TV_FigSet_Height = findViewById(R.id.TV_FigSet_Height);
        ET_FigSet_Height = findViewById(R.id.ET_FigSet_Height);

        LL_FigSettings_Coords = findViewById(R.id.LL_FigSettings_Coords);
        TV_FigSet_X = findViewById(R.id.TV_FigSet_X);
        ET_FigSet_X = findViewById(R.id.ET_FigSet_X);
        TV_FigSet_Y = findViewById(R.id.TV_FigSet_Y);
        ET_FigSet_Y = findViewById(R.id.ET_FigSet_Y);

        V_FigSet_Object = findViewById(R.id.V_FigSet_Object);
        ET_FigSet_TOP = findViewById(R.id.ET_FigSet_TOP);
        ET_FigSet_BOTTOM = findViewById(R.id.ET_FigSet_BOTTOM);
        ET_FigSet_START = findViewById(R.id.ET_FigSet_START);
        ET_FigSet_END = findViewById(R.id.ET_FigSet_END);

        TV_FigSet_Stroke = findViewById(R.id.TV_FigSet_Stroke);
        ET_FigSet_StrokeSize = findViewById(R.id.ET_FigSet_StrokeSize);

        TV_FigSet_StrokeColor = findViewById(R.id.TV_FigSet_StrokeColor);
        V_FigSet_StrokeColor = findViewById(R.id.V_FigSet_StrokeColor);

        CL_ColorStrokeWrap = findViewById(R.id.CL_ColorStrokeWrap);
        IV_ChooseStrokeColor = findViewById(R.id.IV_ChooseStrokeColor);
        ET_StrokeColor_Hex = findViewById(R.id.ET_StrokeColor_Hex);
        HSV_StrokeColor = findViewById(R.id.HSV_StrokeColor);
        IV_Stroke_00000000 = findViewById(R.id.IV_Stroke_00000000);
        IV_Stroke_ffffff = findViewById(R.id.IV_Stroke_ffffff);
        IV_Stroke_ffff66 = findViewById(R.id.IV_Stroke_ffff66);
        IV_Stroke_ff4dff = findViewById(R.id.IV_Stroke_ff4dff);
        IV_Stroke_9933ff = findViewById(R.id.IV_Stroke_9933ff);
        IV_Stroke_0066ff = findViewById(R.id.IV_Stroke_0066ff);
        IV_Stroke_00ff00 = findViewById(R.id.IV_Stroke_00ff00);
        IV_Stroke_ff0000 = findViewById(R.id.IV_Stroke_ff0000);
        IV_Stroke_993300 = findViewById(R.id.IV_Stroke_993300);
        IV_Stroke_000000 = findViewById(R.id.IV_Stroke_000000);
        IV_Stroke_8c8c8c = findViewById(R.id.IV_Stroke_8c8c8c);

        ET_Angle = findViewById(R.id.ET_Angle);

        LL_FigSet_ButtonsWrap = findViewById(R.id.LL_FigSet_ButtonsWrap);
        B_FigSet_SAVE = findViewById(R.id.B_FigSet_SAVE);
        B_FigSet_COPY = findViewById(R.id.B_FigSet_COPY);
        B_FigSet_DELETE = findViewById(R.id.B_FigSet_DELETE);
        B_FigSet_CLOSE = findViewById(R.id.B_FigSet_CLOSE);

        /* ---------- Design ---------- */

        //Display Metrics
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        widthPane = width;
        heightPane = height - height/16;

        /* ---------- Resizing [ START ] ---------- */

        int _50px = height/16;

        //Top Menu
        CL_TopMenu.getLayoutParams().height = _50px;

        IV_Settings.getLayoutParams().width = _50px;
        IV_Settings.getLayoutParams().height = _50px;

        IV_Point.getLayoutParams().width = _50px;
        IV_Point.getLayoutParams().height = _50px;
        IV_Line.getLayoutParams().width = _50px;
        IV_Line.getLayoutParams().height = _50px;
        IV_Circle.getLayoutParams().width = _50px;
        IV_Circle.getLayoutParams().height = _50px;
        IV_Square.getLayoutParams().width = _50px;
        IV_Square.getLayoutParams().height = _50px;
        IV_Triangle.getLayoutParams().width = _50px;
        IV_Triangle.getLayoutParams().height = _50px;
        IV_Oval.getLayoutParams().width = _50px;
        IV_Oval.getLayoutParams().height = _50px;
        IV_Pentagon.getLayoutParams().width = _50px;
        IV_Pentagon.getLayoutParams().height = _50px;
        IV_Hexagon.getLayoutParams().width = _50px;
        IV_Hexagon.getLayoutParams().height = _50px;
        IV_Triangle_Prav.getLayoutParams().width = _50px;
        IV_Triangle_Prav.getLayoutParams().height = _50px;
        IV_Izpit.getLayoutParams().width = _50px;
        IV_Izpit.getLayoutParams().height = _50px;

        final ConstraintSet cs = new ConstraintSet();
        cs.clone(CL_TopMenu);
        cs.connect(R.id.LL_RazdelitelTop, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, height/200);
        cs.connect(R.id.LL_RazdelitelTop, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, height/200);
        cs.applyTo(CL_TopMenu);

        //List Figures
        CL_ListWrapper.getLayoutParams().width = height/8;
        CL_ListWrapper.getLayoutParams().height = (int) (height/1.6);
        TV_Figures.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (height/53.333));

        IV_ArrowFigures.getLayoutParams().height = (int) (height/26.666);
        IV_ArrowFigures.getLayoutParams().width = (int) (height/53.333);

        cs.clone(CL_Main);
        cs.connect(R.id.IV_ArrowFigures, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, (int) (height/9.411));
        cs.applyTo(CL_Main);

        //Windows Previews
        CL_Windows.getLayoutParams().width = _50px;
        CL_Windows.getLayoutParams().height = (int) ((_50px*1.666)*2);

        IV_ArrowWindows.getLayoutParams().height = (int) (height/26.666);
        IV_ArrowWindows.getLayoutParams().width = (int) (height/53.333);

        cs.clone(CL_Main);
        cs.connect(R.id.IV_ArrowWindows, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, (int) (height/22.857));
        cs.applyTo(CL_Main);

        //Settings Window
        CL_ActualSettings.getLayoutParams().width = (int) (width/1.371);
        CL_ActualSettings.getLayoutParams().height = (int) (width/2.4);

        TV_Settings_Text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width/19.2));
        TV_Settings_Resolution.setTextSize(TypedValue.COMPLEX_UNIT_PX, width/24);
        TV_Settings_ActualResolution.setTextSize(TypedValue.COMPLEX_UNIT_PX, width/24);
        TV_Settings_Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, width/24);
        ET_Settings_Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, width/24);
        TV_Settings_PaneColor.setTextSize(TypedValue.COMPLEX_UNIT_PX, width/24);
        V_Settings_PaneColor.getLayoutParams().width = width/24;
        V_Settings_PaneColor.getLayoutParams().height = width/24;
        B_Settings_Open.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width/34.285));
        B_Settings_Save.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width/34.285));
        B_Settings_Clear.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width/34.285));
        B_Settings_Close.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width/34.285));

        LL_Settings_WrapButtons.setPadding(width/32,0,width/32,0);
        LL_Settings_WrapButtons.getLayoutParams().height = (int) (width/10.666);

        cs.clone(CL_ActualSettings);
        cs.connect(R.id.TV_Settings_Resolution, ConstraintSet.TOP, R.id.TV_Settings_Text, ConstraintSet.BOTTOM, width/48);
        cs.connect(R.id.TV_Settings_Title, ConstraintSet.TOP, R.id.TV_Settings_Resolution, ConstraintSet.BOTTOM, width/48);
        cs.connect(R.id.TV_Settings_PaneColor, ConstraintSet.TOP, R.id.TV_Settings_Title, ConstraintSet.BOTTOM, width/48);
        cs.applyTo(CL_ActualSettings);
            //Color picker за фон на платната:
        CL_Settings_ColorChooser.getLayoutParams().width = (int) (width/1.548);
        CL_Settings_ColorChooser.getLayoutParams().height = width/16;
        HSV_for_LLColors.getLayoutParams().width = (int) (width/3.038);
        HSV_for_LLColors.getLayoutParams().height = (int) (width/20);
        LL_In_ColorChooser.getLayoutParams().width = (int) (width/3.038);
        LL_In_ColorChooser.getLayoutParams().height = (int) (width/20);
        ET_ColorChooser.getLayoutParams().width = (int) (width/6.4);
        ET_ColorChooser.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width/26.666));
        LL_Colors_B_Wrapper.getLayoutParams().width = (int) (width/6.4);
        LL_Colors_B_Wrapper.getLayoutParams().height = width/17;
        IV_Settings_C_White.getLayoutParams().width = width/20;
        IV_Settings_C_Yellow.getLayoutParams().width = width/20;
        IV_Settings_C_Pink.getLayoutParams().width = width/20;
        IV_Settings_C_Violet.getLayoutParams().width = width/20;
        IV_Settings_C_Blue.getLayoutParams().width = width/20;
        IV_Settings_C_Green.getLayoutParams().width = width/20;
        IV_Settings_C_Red.getLayoutParams().width = width/20;
        IV_Settings_C_Brown.getLayoutParams().width = width/20;
        IV_Settings_C_Black.getLayoutParams().width = width/20;
        IV_Settings_C_Gray.getLayoutParams().width = width/20;

        setMargins(IV_Settings_C_White, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Yellow, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Pink, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Violet, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Blue, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Green, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Red, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Brown, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Black, width/240, 0, 0, 0);
        setMargins(IV_Settings_C_Gray, width/240, 0, width/240, 0);

        //Figure Settings
        CL_FigureSettingsIn.getLayoutParams().height = (int) (height/1.185);
        CL_FigureSettingsIn.getLayoutParams().width = (int) (CL_FigureSettingsIn.getLayoutParams().height/1.5);

        ET_FigSet_Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        TV_FigSet_FillColor.getLayoutParams().width = (int) (height/3.2);
        TV_FigSet_FillColor.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        CL_Color1Helper.getLayoutParams().width = (int) (height/3.2);
        V_FillColor.getLayoutParams().width = height/40;
        V_FillColor.getLayoutParams().height = height/40;

        CL_ColorFillWrap.getLayoutParams().height = height/32;
        IV_ChooseFillColor.getLayoutParams().height = height/32;
        IV_ChooseFillColor.getLayoutParams().width = height/32;
        ET_FillColor_Hex.getLayoutParams().width = height/8;
        ET_FillColor_Hex.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        IV_Fill_00000000.getLayoutParams().width = height/32;
        IV_Fill_00000000.getLayoutParams().height = height/32;
        IV_Fill_ffffff.getLayoutParams().width = height/32;
        IV_Fill_ffffff.getLayoutParams().height = height/32;
        IV_Fill_ffff66.getLayoutParams().width = height/32;
        IV_Fill_ffff66.getLayoutParams().height = height/32;
        IV_Fill_ff4dff.getLayoutParams().width = height/32;
        IV_Fill_ff4dff.getLayoutParams().height = height/32;
        IV_Fill_9933ff.getLayoutParams().width = height/32;
        IV_Fill_9933ff.getLayoutParams().height = height/32;
        IV_Fill_0066ff.getLayoutParams().width = height/32;
        IV_Fill_0066ff.getLayoutParams().height = height/32;
        IV_Fill_00ff00.getLayoutParams().width = height/32;
        IV_Fill_00ff00.getLayoutParams().height = height/32;
        IV_Fill_ff0000.getLayoutParams().width = height/32;
        IV_Fill_ff0000.getLayoutParams().height = height/32;
        IV_Fill_993300.getLayoutParams().width = height/32;
        IV_Fill_993300.getLayoutParams().height = height/32;
        IV_Fill_000000.getLayoutParams().width = height/32;
        IV_Fill_000000.getLayoutParams().height = height/32;
        IV_Fill_8c8c8c.getLayoutParams().width = height/32;
        IV_Fill_8c8c8c.getLayoutParams().height = height/32;

        TV_FigSet_Width.getLayoutParams().width = (int) (height/7.143);
        TV_FigSet_Width.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_Width.getLayoutParams().width = (int) (height/7.143);
        ET_FigSet_Width.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        TV_FigSet_Height.getLayoutParams().width = (int) (height/7.143);
        TV_FigSet_Height.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_Height.getLayoutParams().width = (int) (height/7.143);
        ET_FigSet_Height.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        TV_FigSet_X.getLayoutParams().width = (int) (height/7.143);
        TV_FigSet_X.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_X.getLayoutParams().width = (int) (height/7.143);
        ET_FigSet_X.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        TV_FigSet_Y.getLayoutParams().width = (int) (height/7.143);
        TV_FigSet_Y.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_Y.getLayoutParams().width = (int) (height/7.143);
        ET_FigSet_Y.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        V_FigSet_Object.getLayoutParams().width = height/4;
        V_FigSet_Object.getLayoutParams().height = height/4;
        ET_FigSet_TOP.getLayoutParams().width = height/8;
        ET_FigSet_TOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_BOTTOM.getLayoutParams().width = height/8;
        ET_FigSet_BOTTOM.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_START.getLayoutParams().width = height/8;
        ET_FigSet_START.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_END.getLayoutParams().width = height/8;
        ET_FigSet_END.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        TV_FigSet_Stroke.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        ET_FigSet_StrokeSize.getLayoutParams().width = height/8;
        ET_FigSet_StrokeSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        TV_FigSet_StrokeColor.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);
        V_FigSet_StrokeColor.getLayoutParams().width = height/40;
        V_FigSet_StrokeColor.getLayoutParams().height = height/40;

        CL_ColorStrokeWrap.getLayoutParams().height = height/32;
        IV_ChooseStrokeColor.getLayoutParams().height = height/32;
        IV_ChooseStrokeColor.getLayoutParams().width = height/32;
        ET_StrokeColor_Hex.getLayoutParams().width = height/8;
        ET_StrokeColor_Hex.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        IV_Stroke_00000000.getLayoutParams().width = height/32;
        IV_Stroke_00000000.getLayoutParams().height = height/32;
        IV_Stroke_ffffff.getLayoutParams().width = height/32;
        IV_Stroke_ffffff.getLayoutParams().height = height/32;
        IV_Stroke_ffff66.getLayoutParams().width = height/32;
        IV_Stroke_ffff66.getLayoutParams().height = height/32;
        IV_Stroke_ff4dff.getLayoutParams().width = height/32;
        IV_Stroke_ff4dff.getLayoutParams().height = height/32;
        IV_Stroke_9933ff.getLayoutParams().width = height/32;
        IV_Stroke_9933ff.getLayoutParams().height = height/32;
        IV_Stroke_0066ff.getLayoutParams().width = height/32;
        IV_Stroke_0066ff.getLayoutParams().height = height/32;
        IV_Stroke_00ff00.getLayoutParams().width = height/32;
        IV_Stroke_00ff00.getLayoutParams().height = height/32;
        IV_Stroke_ff0000.getLayoutParams().width = height/32;
        IV_Stroke_ff0000.getLayoutParams().height = height/32;
        IV_Stroke_993300.getLayoutParams().width = height/32;
        IV_Stroke_993300.getLayoutParams().height = height/32;
        IV_Stroke_000000.getLayoutParams().width = height/32;
        IV_Stroke_000000.getLayoutParams().height = height/32;
        IV_Stroke_8c8c8c.getLayoutParams().width = height/32;
        IV_Stroke_8c8c8c.getLayoutParams().height = height/32;

        ET_Angle.getLayoutParams().width = (int) (height/10.666);
        ET_Angle.getLayoutParams().height = (int) (height/10.666);
        ET_Angle.setTextSize(TypedValue.COMPLEX_UNIT_PX, height/40);

        LL_FigSet_ButtonsWrap.getLayoutParams().height = height/16;

        B_FigSet_SAVE.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(height/53.333));
        B_FigSet_COPY.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(height/53.333));
        B_FigSet_DELETE.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(height/53.333));
        B_FigSet_CLOSE.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(height/53.333));

        cs.clone(CL_FigureSettingsIn);
        cs.connect(R.id.LL_FigSettings_Coords, ConstraintSet.BOTTOM, R.id.ET_FigSet_TOP, ConstraintSet.TOP, (int)(height/57.14));
        cs.connect(R.id.LL_FigSettings_Dimensions, ConstraintSet.BOTTOM, R.id.LL_FigSettings_Coords, ConstraintSet.TOP, (int)(height/57.14));
        cs.connect(R.id.CL_ColorFillWrap, ConstraintSet.BOTTOM, R.id.LL_FigSettings_Dimensions, ConstraintSet.TOP, (int)(height/57.14));
        cs.connect(R.id.LL_FigSettings_Color1Wrap, ConstraintSet.BOTTOM, R.id.CL_ColorFillWrap, ConstraintSet.TOP, (int)(height/57.14));
        cs.connect(R.id.TV_FigSet_Stroke, ConstraintSet.TOP, R.id.ET_FigSet_BOTTOM, ConstraintSet.BOTTOM, (int)(height/57.14));
        cs.connect(R.id.TV_FigSet_StrokeColor, ConstraintSet.TOP, R.id.TV_FigSet_Stroke, ConstraintSet.BOTTOM, (int)(height/57.14));
        cs.connect(R.id.CL_ColorStrokeWrap, ConstraintSet.TOP, R.id.TV_FigSet_StrokeColor, ConstraintSet.BOTTOM, (int)(height/57.14));
        cs.applyTo(CL_FigureSettingsIn);

        /* ---------- Resizing [  END  ] ---------- */


        /* ---------- Window Panes [ START ] ---------- */
        windows = new WindowPane[2];
        windows[0] = new WindowPane();
        windows[1] = new WindowPane();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss"); //03.02.2019_18:09:43
        Date currentTime = Calendar.getInstance().getTime();

        windows[0].setTitle("Window_1_" + dateFormat.format(currentTime)); //Window_1_03.02.2019_18:09:43
        windows[1].setTitle("Window_2_" + dateFormat.format(currentTime)); //Window_2_03.02.2019_18:09:43
        /* ---------- Window Panes [  END  ] ---------- */

        /* SETTINGS WINDOW */
        TV_Settings_ActualResolution.setText(width + "x" + heightPane + "px");
        /**/


        /* ---------- OnClick [ START ] ---------- */

        //Arrows
        IV_ArrowFigures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(figuresMenu) { //вижда се - трябва да се скрие

                    CL_ListWrapper.setVisibility(View.INVISIBLE);

                    cs.clone(CL_Main);
                    cs.connect(R.id.IV_ArrowFigures, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                    cs.applyTo(CL_Main);

                    IV_ArrowFigures.setImageResource(R.drawable.icon_arrow_right);

                    figuresMenu = false;

                } else { //не се вижда - трябва да се покаже

                    CL_ListWrapper.setVisibility(View.VISIBLE);

                    cs.clone(CL_Main);
                    cs.connect(R.id.IV_ArrowFigures, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, (int) (height/9.411));
                    cs.applyTo(CL_Main);

                    IV_ArrowFigures.setImageResource(R.drawable.icon_arrow_left);

                    figuresMenu = true;
                }
            }
        });

        IV_ArrowWindows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previewMenu) { //вижда се - трябва да се скрие

                    CL_Windows.setVisibility(View.INVISIBLE);

                    cs.clone(CL_Main);
                    cs.connect(R.id.IV_ArrowWindows, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                    cs.applyTo(CL_Main);

                    IV_ArrowWindows.setImageResource(R.drawable.icon_arrow_left);

                    previewMenu = false;

                } else { //не се вижда - трябва да се покаже

                    CL_Windows.setVisibility(View.VISIBLE);

                    cs.clone(CL_Main);
                    cs.connect(R.id.IV_ArrowWindows, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, (int) (height/22.857));
                    cs.applyTo(CL_Main);

                    IV_ArrowWindows.setImageResource(R.drawable.icon_arrow_right);

                    previewMenu = true;
                    updatePreviews();
                }
            }
        });

        //Windows Previews
        CL_Window1Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL_Window1.setVisibility(View.VISIBLE);
                CL_Window2.setVisibility(View.INVISIBLE);
                windowPointer = 0;
                //Код за показване на листа с фигурите
                updateFiguresList();
                updatePreviews();
            }
        });

        CL_Window2Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL_Window2.setVisibility(View.VISIBLE);
                CL_Window1.setVisibility(View.INVISIBLE);
                windowPointer = 1;
                //Код за показване на листа с фигурите
                updateFiguresList();
                updatePreviews();
            }
        });

        //Open Settings
        IV_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Зареждане на информацията за проекта в прозореца за настройки
                ET_Settings_Title.setText(windows[windowPointer].getTitle());

                if(windowPointer==0){
                    V_Settings_PaneColor.setBackgroundColor( ((ColorDrawable)CL_Window1.getBackground()).getColor() );
                } else {
                    V_Settings_PaneColor.setBackgroundColor( ((ColorDrawable)CL_Window2.getBackground()).getColor() );
                }


                CL_SettingsWindow.setBackground(new BitmapDrawable(getResources(), createBlurBitmapFromScreenSettings()));
                CL_SettingsWindow.setVisibility(View.VISIBLE);
                Animation expandIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.expand_in);
                CL_ActualSettings.startAnimation(expandIn);
            }
        });

        //Settings Window
        B_Settings_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                CL_SettingsWindow.setVisibility(View.INVISIBLE);
                CL_Settings_ColorChooser.setVisibility(View.INVISIBLE);
                IV_Color_YES.setVisibility(View.INVISIBLE);
                ET_ColorChooser.setText("");
            }
        });
        B_Settings_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ET_Settings_Title.getText().toString().isEmpty()) { // заглавието на прозореца(проекта) е изтрито и трябва да излезне прозорец с предупреждение

                    Toast.makeText(getApplicationContext(), "Невъведено име на платно!", Toast.LENGTH_LONG).show();

                }
                else { // имаме заглавие можем да запазим

                    windows[windowPointer].setTitle(ET_Settings_Title.getText().toString()); // запазване на заглавието

                    if(windowPointer==0) { // записваме настройки за прозорец 1

                        CL_Window1.setBackgroundColor( ((ColorDrawable)V_Settings_PaneColor.getBackground()).getColor() );

                    } else { //записваме настройки за прозорец 2 (=1)

                        CL_Window2.setBackgroundColor( ((ColorDrawable)V_Settings_PaneColor.getBackground()).getColor() );

                    }

                    B_Settings_Close.performClick(); //затваряме прозореца след успешен запис на промените
                    hideSoftKeyboard();
                    CL_Settings_ColorChooser.setVisibility(View.INVISIBLE);
                    updatePreviews();
                    updateFiguresList();
                }

            }
        });
        B_Settings_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Премахване на визуалните обекти:
                for(Figure f : windows[windowPointer].figures) {
                    f.setVisibility(View.GONE);
                }
                windows[windowPointer].figures.clear();

                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");
                Date currentTime = Calendar.getInstance().getTime();
                windows[windowPointer] = new WindowPane();
                windows[windowPointer].setTitle("New " + dateFormat.format(currentTime));

                B_Settings_Close.performClick();

                if(windowPointer==0){
                    CL_Window1.setBackgroundColor(Color.parseColor("#ffffffff"));
                } else {
                    CL_Window2.setBackgroundColor(Color.parseColor("#ffffffff"));
                }

                updateFiguresList();
                updatePreviews();
            }
        });
        B_Settings_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ET_Settings_Title.getText().toString().trim();
                db.saveProject(title, windows[windowPointer].figures);
                Toast.makeText(getApplicationContext(), title + " saved successfully!", Toast.LENGTH_SHORT).show();
                windows[windowPointer].setTitle(title);
            }
        });
        B_Settings_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ProjectsActivity.class), 1);
            }
        });
        V_Settings_PaneColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                CL_Settings_ColorChooser.setVisibility(View.VISIBLE);
            }
        });
        IV_Color_NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                CL_Settings_ColorChooser.setVisibility(View.INVISIBLE);
                IV_Color_YES.setVisibility(View.INVISIBLE);
                ET_ColorChooser.setText("");
            }
        });

        IV_Settings_C_Red.setOnClickListener(choosePaneColor);
        IV_Settings_C_Green.setOnClickListener(choosePaneColor);
        IV_Settings_C_Blue.setOnClickListener(choosePaneColor);
        IV_Settings_C_Black.setOnClickListener(choosePaneColor);
        IV_Settings_C_White.setOnClickListener(choosePaneColor);
        IV_Settings_C_Gray.setOnClickListener(choosePaneColor);
        IV_Settings_C_Yellow.setOnClickListener(choosePaneColor);
        IV_Settings_C_Pink.setOnClickListener(choosePaneColor);
        IV_Settings_C_Violet.setOnClickListener(choosePaneColor);
        IV_Settings_C_Brown.setOnClickListener(choosePaneColor);
        IV_Color_YES.setOnClickListener(choosePaneColor);

        //Figures settings colors
        IV_Fill_00000000.setOnClickListener(selectFillColor);
        IV_Fill_ffffff.setOnClickListener(selectFillColor);
        IV_Fill_ffff66.setOnClickListener(selectFillColor);
        IV_Fill_ff4dff.setOnClickListener(selectFillColor);
        IV_Fill_9933ff.setOnClickListener(selectFillColor);
        IV_Fill_0066ff.setOnClickListener(selectFillColor);
        IV_Fill_00ff00.setOnClickListener(selectFillColor);
        IV_Fill_ff0000.setOnClickListener(selectFillColor);
        IV_Fill_993300.setOnClickListener(selectFillColor);
        IV_Fill_000000.setOnClickListener(selectFillColor);
        IV_Fill_8c8c8c.setOnClickListener(selectFillColor);

        IV_Stroke_00000000.setOnClickListener(selectStrokeColor);
        IV_Stroke_ffffff.setOnClickListener(selectStrokeColor);
        IV_Stroke_ffff66.setOnClickListener(selectStrokeColor);
        IV_Stroke_ff4dff.setOnClickListener(selectStrokeColor);
        IV_Stroke_9933ff.setOnClickListener(selectStrokeColor);
        IV_Stroke_0066ff.setOnClickListener(selectStrokeColor);
        IV_Stroke_00ff00.setOnClickListener(selectStrokeColor);
        IV_Stroke_ff0000.setOnClickListener(selectStrokeColor);
        IV_Stroke_993300.setOnClickListener(selectStrokeColor);
        IV_Stroke_000000.setOnClickListener(selectStrokeColor);
        IV_Stroke_8c8c8c.setOnClickListener(selectStrokeColor);

        //Figures
        View.OnClickListener createFigureClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Figure figure;
                switch (v.getId()){
                    case R.id.IV_Point:
                        figure = new angelzani.drawingshapes.figures.Point(getApplicationContext()); break;
                    case R.id.IV_Line:
                        figure = new Line(getApplicationContext()); break;
                    case R.id.IV_Circle:
                        figure = new Circle(getApplicationContext()); break;
                    case R.id.IV_Square:
                        figure = new Rectangle(getApplicationContext()); break;
                    case R.id.IV_Triangle:
                        figure = new Triangle(getApplicationContext()); break;
                    case R.id.IV_Oval:
                        figure = new Oval(getApplicationContext()); break;
                    case R.id.IV_Pentagon:
                        figure = new Pentagon(getApplicationContext()); break;
                    case R.id.IV_Hexagon:
                        figure = new Hexagon(getApplicationContext()); break;
                    case R.id.IV_Triangle_Prav:
                        figure = new TrianglePrav(getApplicationContext()); break;
                    default:
                        figure = new Izpit(getApplicationContext()); break;
                }

                addFigureToPaneLayout(figure);

                figure.title = "Figure" + windows[windowPointer].figures.size();
                switch (figure.type){
                    // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = TrianglePrav; 10 = Изпитна фигура
                    case 1:
                        figure.drawShape(widthPane/2, heightPane/2, height/48, figure.fillColor, 0, figure.strokeColor); break;
                    case 2:
                        figure.drawShape(widthPane/2-width/6, heightPane/2, width/3, height/160, figure.fillColor, 0, figure.strokeColor); break;
                    case 3:
                        figure.drawShape(widthPane/2-width/8, heightPane/2-width/8, width/4, figure.fillColor, 0, figure.strokeColor); break;
                    case 4:
                        figure.drawShape(widthPane/2-width/8, heightPane/2-width/8, width/4, width/4, figure.fillColor, 0, figure.strokeColor); break;
                    case 5:
                        figure.drawShape(widthPane/2-width/8, heightPane/2-width/8, width/4, figure.fillColor, 0, figure.strokeColor); break;
                    case 6:
                        figure.drawShape(widthPane/2-width/8, heightPane/2-width/12, width/4, width/6, figure.fillColor, 0, figure.strokeColor); break;
                    case 7:
                        figure.drawShape(widthPane/2-width/6, widthPane/2-width/6, width/6,  figure.fillColor, 0, figure.strokeColor); break;
                    case 8:
                        figure.drawShape(widthPane/2-width/6, widthPane/2-width/6, width/6,  figure.fillColor, 0, figure.strokeColor); break;
                    case 9:
                        figure.drawShape(widthPane/2-width/8, heightPane/2-width/8, width/4, width/4, "#000000", 0, "#000000"); break;
                    default:
                        figure.drawShape(widthPane/2-width/8, heightPane/2-width/8, width/4, width/4, figure.fillColor, width/64, figure.strokeColor); break;
                }
                figure.setOnTouchListener(moveFigure);
                figure.setOnClickListener(figureClicker);

                figure.performClick();

                updateFiguresList();
                updatePreviews();
            }
        };

        IV_Point.setOnClickListener(createFigureClicker);
        IV_Line.setOnClickListener(createFigureClicker);
        IV_Circle.setOnClickListener(createFigureClicker);
        IV_Square.setOnClickListener(createFigureClicker);
        IV_Triangle.setOnClickListener(createFigureClicker);
        IV_Oval.setOnClickListener(createFigureClicker);
        IV_Pentagon.setOnClickListener(createFigureClicker);
        IV_Hexagon.setOnClickListener(createFigureClicker);
        IV_Triangle_Prav.setOnClickListener(createFigureClicker);
        IV_Izpit.setOnClickListener(createFigureClicker);

        //Figure Settings CLOSE button
        B_FigSet_CLOSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFigureSettings_Close();
                CL_FigureSettingsOut.setVisibility(View.INVISIBLE);
            }
        });

        //Figure Settings SAVE button
        B_FigSet_SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFigure();
            }
        });

        //Figure Settings COPY button
        B_FigSet_COPY.setOnClickListener(copyFigureClick);

        //Figure Settings DELETE button
        B_FigSet_DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windows[windowPointer].figures.remove(current_selected);
                current_selected.setVisibility(View.GONE);
                current_selected = null; // вече абсолютно нищо не сочи към визуалния обект и garbage collectora трябва да си свърши работата по някое време
                B_FigSet_CLOSE.performClick();
            }
        });

        //Figure Settings TEXT HEX Colors
        IV_ChooseFillColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFillColor = ET_FillColor_Hex.getText().toString();
                V_FillColor.setBackgroundColor(Color.parseColor(currentFillColor));
                ET_FillColor_Hex.setText("");
                IV_ChooseFillColor.setVisibility(View.INVISIBLE);
            }
        });
        IV_ChooseStrokeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStrokeColor = ET_StrokeColor_Hex.getText().toString();
                V_FigSet_StrokeColor.setBackgroundColor(Color.parseColor(currentStrokeColor));
                ET_StrokeColor_Hex.setText("");
                IV_ChooseStrokeColor.setVisibility(View.INVISIBLE);
            }
        });

        /* ---------- OnClick [  END  ] ---------- */

        /* ---------- Text Watchers [ START ] ---------- */
        ET_ColorChooser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ET_ColorChooser.setTextColor(Color.parseColor(ET_ColorChooser.getText().toString()));
                    IV_Color_YES.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    ET_ColorChooser.setTextColor(Color.parseColor("#000000"));
                    IV_Color_YES.setVisibility(View.INVISIBLE);
                }
            }
        });

        ET_FillColor_Hex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ET_FillColor_Hex.setTextColor(Color.parseColor(ET_FillColor_Hex.getText().toString()));
                    IV_ChooseFillColor.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    ET_FillColor_Hex.setTextColor(Color.parseColor("#000000"));
                    IV_ChooseFillColor.setVisibility(View.INVISIBLE);
                }
            }
        });

        ET_StrokeColor_Hex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ET_StrokeColor_Hex.setTextColor(Color.parseColor(ET_StrokeColor_Hex.getText().toString()));
                    IV_ChooseStrokeColor.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    ET_StrokeColor_Hex.setTextColor(Color.parseColor("#000000"));
                    IV_ChooseStrokeColor.setVisibility(View.INVISIBLE);
                }
            }
        });
        /* ---------- Text Watchers [  END  ] ---------- */

    } // END of onCreate()

    /* ---------- OnClick [ START ] ---------- */

    //Figure Settings Fill/Stroke Color clickers
    View.OnClickListener selectFillColor = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.IV_Fill_00000000: currentFillColor = "#00000000"; break;
                case R.id.IV_Fill_ffffff: currentFillColor = "#ffffffff"; break;
                case R.id.IV_Fill_ffff66: currentFillColor = "#ffffff66"; break;
                case R.id.IV_Fill_ff4dff: currentFillColor = "#ffff4dff"; break;
                case R.id.IV_Fill_9933ff: currentFillColor = "#ff9933ff"; break;
                case R.id.IV_Fill_0066ff: currentFillColor = "#ff0066ff"; break;
                case R.id.IV_Fill_00ff00: currentFillColor = "#ff00ff00"; break;
                case R.id.IV_Fill_ff0000: currentFillColor = "#ffff0000"; break;
                case R.id.IV_Fill_993300: currentFillColor = "#ff993300"; break;
                case R.id.IV_Fill_000000: currentFillColor = "#ff000000"; break;
                case R.id.IV_Fill_8c8c8c: currentFillColor = "#ff8c8c8c"; break;
            }
            ET_FillColor_Hex.setText(currentFillColor);
            V_FillColor.setBackgroundColor(Color.parseColor(currentFillColor));
        }
    };
    View.OnClickListener selectStrokeColor = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.IV_Stroke_00000000: currentStrokeColor = "#00000000"; break;
                case R.id.IV_Stroke_ffffff: currentStrokeColor = "#ffffffff"; break;
                case R.id.IV_Stroke_ffff66: currentStrokeColor = "#ffffff66"; break;
                case R.id.IV_Stroke_ff4dff: currentStrokeColor = "#ffff4dff"; break;
                case R.id.IV_Stroke_9933ff: currentStrokeColor = "#ff9933ff"; break;
                case R.id.IV_Stroke_0066ff: currentStrokeColor = "#ff0066ff"; break;
                case R.id.IV_Stroke_00ff00: currentStrokeColor = "#ff00ff00"; break;
                case R.id.IV_Stroke_ff0000: currentStrokeColor = "#ffff0000"; break;
                case R.id.IV_Stroke_993300: currentStrokeColor = "#ff993300"; break;
                case R.id.IV_Stroke_000000: currentStrokeColor = "#ff000000"; break;
                case R.id.IV_Stroke_8c8c8c: currentStrokeColor = "#ff8c8c8c"; break;
            }
            ET_StrokeColor_Hex.setText(currentStrokeColor);
            V_FigSet_StrokeColor.setBackgroundColor(Color.parseColor(currentStrokeColor));
        }
    };

    private Figure current_selected = null;
    private String currentFillColor = null, currentStrokeColor = null;
    //Прихващане на кликането в/у фигурите -> отваряне прозореца за настройки
    View.OnClickListener figureClicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen; 10 = изпитна
            switch( ((Figure) view).type ) {
                case 1: // Point
                    current_selected = (angelzani.drawingshapes.figures.Point) view;
                    ET_FigSet_Height.setVisibility(View.INVISIBLE);
                    TV_FigSet_Height.setVisibility(View.INVISIBLE);
                    TV_FigSet_Width.setText("Size:");
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_X.setText((int)(current_selected.getX()+current_selected.getLayoutParams().width/2)+"");
                    ET_FigSet_Y.setText((int)(current_selected.getY()+current_selected.getLayoutParams().width/2)+"");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_point);
                    ET_FigSet_START.setVisibility(View.INVISIBLE);
                    ET_FigSet_END.setVisibility(View.INVISIBLE);
                    ET_FigSet_TOP.setVisibility(View.INVISIBLE);
                    ET_FigSet_BOTTOM.setVisibility(View.INVISIBLE);
                    ET_Angle.setVisibility(View.INVISIBLE);
                    break;
                case 2: // Line
                    current_selected = (Line) view;
                    TV_FigSet_Width.setText("Lenght:");
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    TV_FigSet_Height.setText(":Thickness");
                    ET_FigSet_Height.setText(current_selected.getLayoutParams().height+"");
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_line);
                    break;
                case 3: // Circle
                    current_selected = (Circle) view;
                    TV_FigSet_Width.setText("Diameter:");
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_Height.setVisibility(View.INVISIBLE);
                    TV_FigSet_Height.setVisibility(View.INVISIBLE);
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setVisibility(View.INVISIBLE);
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_circle);
                    break;
                case 4: // Rectangle
                    current_selected = (Rectangle) view;
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_Height.setText(current_selected.getLayoutParams().height+"");
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_square);
                    break;
                case 5: // Triangle
                    current_selected = (Triangle) view;
                    TV_FigSet_Width.setText("Side:");
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    TV_FigSet_Height.setVisibility(View.INVISIBLE);
                    ET_FigSet_Height.setVisibility(View.INVISIBLE);
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_triangle);
                    break;
                case 6: // Oval/елипса
                    current_selected = (Oval) view;
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_Height.setText(current_selected.getLayoutParams().height+"");
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_oval);
                    break;
                case 7: // Pentagon
                    current_selected = (Pentagon) view;
                    TV_FigSet_Width.setText("Diameter:");
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_Height.setVisibility(View.INVISIBLE);
                    TV_FigSet_Height.setVisibility(View.INVISIBLE);
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_pentagon);
                    break;
                case 8: // Hexagon
                    current_selected = (Hexagon) view;
                    TV_FigSet_Width.setText("Diameter:");
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_Height.setVisibility(View.INVISIBLE);
                    TV_FigSet_Height.setVisibility(View.INVISIBLE);
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_hexagon);
                    break;
                case 9: // Triangle (правоъгълен)
                    current_selected = (TrianglePrav) view;
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_Height.setText(current_selected.getLayoutParams().height+"");
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_triangle_prav);
                    break;
                case 10: // Izpit
                    current_selected = (Izpit) view;
                    ET_FigSet_Width.setText(current_selected.getLayoutParams().width+"");
                    ET_FigSet_Height.setText(current_selected.getLayoutParams().height+"");
                    LL_FigSettings_Coords.setVisibility(View.INVISIBLE);
                    ET_Angle.setText((int) current_selected.getRotation() + "");
                    ET_FigSet_TOP.setText((int) current_selected.getY() + "");
                    ET_FigSet_BOTTOM.setText( (int) (heightPane-(current_selected.getY()+current_selected.getLayoutParams().height)) + "");
                    ET_FigSet_START.setText((int) current_selected.getX() + "");
                    ET_FigSet_END.setText((int) (widthPane - (current_selected.getX() + current_selected.getLayoutParams().width)) + "");
                    V_FigSet_Object.setBackgroundResource(R.drawable.icon_izpit);
                    break;
            }

            // Общи характеристики
            currentFillColor = current_selected.fillColor;
            currentStrokeColor = current_selected.strokeColor;
            ET_FigSet_Title.setText(current_selected.title);
            V_FillColor.setBackgroundColor(Color.parseColor(current_selected.fillColor));
            ET_FigSet_StrokeSize.setText(current_selected.strokeWidth+"");
            V_FigSet_StrokeColor.setBackgroundColor(Color.parseColor(current_selected.strokeColor));
            ET_FillColor_Hex.setText(current_selected.fillColor);
            ET_StrokeColor_Hex.setText(current_selected.strokeColor);

            ET_FillColor_Hex.setText(currentFillColor);
            ET_StrokeColor_Hex.setText(currentStrokeColor);

            //Показване на прозореца
            CL_FigureSettingsOut.setVisibility(View.VISIBLE);
            Animation expandIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.expand_in);
            CL_FigureSettingsOut.startAnimation(expandIn);
        }
    };

    private void saveFigure() {
        // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulen
        switch(current_selected.type) {
            case 1: //Point
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_X.getText().toString()),
                        Integer.parseInt(ET_FigSet_Y.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                break;
            case 2: //Line
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        Integer.parseInt(ET_FigSet_Height.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
            case 3: //Circle
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                break;
            case 4: //Rectangle
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        Integer.parseInt(ET_FigSet_Height.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
            case 5: //Triangle
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
            case 6: //Oval/елипса
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        Integer.parseInt(ET_FigSet_Height.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
            case 7: //Pentagon
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
            case 8: //Hexagon
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
            case 9: //Triangle (правоъгълен)
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        Integer.parseInt(ET_FigSet_Height.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
            case 10: // изпитна
                current_selected.title = ET_FigSet_Title.getText().toString();
                current_selected.drawShape(
                        Integer.parseInt(ET_FigSet_START.getText().toString()),
                        Integer.parseInt(ET_FigSet_TOP.getText().toString()),
                        Integer.parseInt(ET_FigSet_Width.getText().toString()),
                        Integer.parseInt(ET_FigSet_Height.getText().toString()),
                        currentFillColor,
                        Integer.parseInt(ET_FigSet_StrokeSize.getText().toString()),
                        currentStrokeColor
                );
                current_selected.setRotation(Float.parseFloat(ET_Angle.getText().toString()));
                break;
        }
        B_FigSet_CLOSE.performClick(); // чистим и затваряме
    }//end saveFigure()

    View.OnClickListener copyFigureClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 1 = Point; 2 = Line; 3 = Circle; 4 = Square; 5 = Triangle; 6 = Oval; 7 = Pentagon; 8 = Hexagon; 9 = Triangle Pravougulenl; 10 = Izpit
            int X = (int) current_selected.getX(), Y = (int) current_selected.getY();
            Figure figure;
            switch(current_selected.type) {
                case 1: //Point
                    figure = new angelzani.drawingshapes.figures.Point(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X+current_selected.getLayoutParams().width/2, Y+current_selected.getLayoutParams().width/2, current_selected.getLayoutParams().width, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    break;
                case 2: //Line
                    figure = new Line(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.getLayoutParams().height, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
                case 3: //Circle
                    figure = new Circle(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    break;
                case 4: //Rectangle
                    figure = new Rectangle(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.getLayoutParams().height, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
                case 5: //Triangle
                    figure = new Triangle(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
                case 6: //Oval/елипса
                    figure = new Oval(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.getLayoutParams().height, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
                case 7: //Pentagon
                    figure = new Pentagon(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
                case 8: //Hexagon
                    figure = new Hexagon(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
                case 9: //Triangle (правоъгълен)
                    figure = new TrianglePrav(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.getLayoutParams().height, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
                default: //Izpit
                    figure = new Izpit(getApplicationContext());
                    addFigureToPaneLayout(figure);
                    figure.drawShape(X, Y, current_selected.getLayoutParams().width, current_selected.getLayoutParams().height, current_selected.fillColor, current_selected.strokeWidth, current_selected.strokeColor);
                    figure.setRotation(current_selected.getRotation());
                    break;
            }

            figure.title = current_selected.title + " Copy";
            figure.setOnTouchListener(moveFigure);
            figure.setOnClickListener(figureClicker);

            B_FigSet_CLOSE.performClick(); // чистим и затваряме
        }
    };

    private void addFigureToPaneLayout(Figure figure){
        windows[windowPointer].figures.add(figure);
        figure.setId(View.generateViewId());
        if(windowPointer==0) { //намираме се в прозорец 1
            CL_Window1.addView(figure);
        }
        else { //намираме се в прозорец 2
            CL_Window2.addView(figure);
        }
    }

    private void clearFigureSettings_Close(){
        TV_FigSet_Width.setText("Width:");
        ET_FigSet_Height.setVisibility(View.VISIBLE);
        TV_FigSet_Height.setText(":Height");
        TV_FigSet_Height.setVisibility(View.VISIBLE);
        ET_FigSet_Height.setVisibility(View.VISIBLE);
        LL_FigSettings_Coords.setVisibility(View.VISIBLE);
        ET_Angle.setVisibility(View.VISIBLE);
        ET_FigSet_START.setVisibility(View.VISIBLE);
        ET_FigSet_TOP.setVisibility(View.VISIBLE);
        IV_ChooseFillColor.setVisibility(View.INVISIBLE);
        IV_ChooseStrokeColor.setVisibility(View.INVISIBLE);

        updateFiguresList();
        updatePreviews();
    }

    //Смяна на цвета на бекграунда на паното от настройките
    View.OnClickListener choosePaneColor = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.IV_Settings_C_Red:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#ff0000"));
                    break;
                case R.id.IV_Settings_C_Green:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#00ff00"));
                    break;
                case R.id.IV_Settings_C_Blue:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#0066ff"));
                    break;
                case R.id.IV_Settings_C_Black:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#000000"));
                    break;
                case R.id.IV_Settings_C_White:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
                case R.id.IV_Settings_C_Gray:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#8c8c8c"));
                    break;
                case R.id.IV_Settings_C_Yellow:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#ffff66"));
                    break;
                case R.id.IV_Settings_C_Pink:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#ff4dff"));
                    break;
                case R.id.IV_Settings_C_Brown:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#993300"));
                    break;
                case R.id.IV_Settings_C_Violet:
                    V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#9933ff"));
                    break;
                case R.id.IV_Color_YES:
                    try {
                        V_Settings_PaneColor.setBackgroundColor(Color.parseColor(ET_ColorChooser.getText().toString()));
                    } catch (Exception e) {
                        V_Settings_PaneColor.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                    break;
            }
            ColorDrawable viewColor = (ColorDrawable) V_Settings_PaneColor.getBackground();
            int colorId = viewColor.getColor();
            if(windowPointer == 0){
                CL_Window1.setBackgroundColor(colorId);
            } else {
                CL_Window2.setBackgroundColor(colorId);
            }
            updatePreviews();
            IV_Color_NO.performClick();
        }
    };

    /* ---------- OnClick [  END  ] ---------- */

    /* ---------- OnTouchListeners [ START ] ---------- */
    float dX, dY;
    View.OnTouchListener moveFigure = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:

                    view.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                    break;
                default:
                    if(previewMenu) updatePreviews();
                    return false;
            }
            if(previewMenu) updatePreviews();
            return true;
        }
    };
    /* ---------- OnTouchListeners [  END  ] ---------- */


    /* ---------- Design Updater [ START ] ---------- */

    private void updateFiguresList(){
        LL_Figures.removeAllViews();
        for(final Figure f : windows[windowPointer].figures){
            ConstraintLayout cl = new ConstraintLayout(getApplicationContext());
            cl.setId(View.generateViewId());
            LL_Figures.addView(cl);

            cl.getLayoutParams().width = CL_ListWrapper.getLayoutParams().width;
            cl.getLayoutParams().height = CL_ListWrapper.getLayoutParams().width;

            TextView tv = new TextView(getApplicationContext());
            tv.setId(View.generateViewId());
            cl.addView(tv);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (height/53.333));
            tv.setText(f.title);
            tv.setTextColor(Color.parseColor("#2767E6"));
            tv.setShadowLayer(1, 0, 0, Color.BLACK);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(cl);
            cs.connect(tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            cs.connect(tv.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            cs.connect(tv.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            cs.applyTo(cl);

            switch(f.type){
                case 1: //point
                    cl.setBackgroundResource(R.drawable.icon_point);
                    break;
                case 2: //line
                    cl.setBackgroundResource(R.drawable.icon_line);
                    break;
                case 3: //circle
                    cl.setBackgroundResource(R.drawable.icon_circle);
                    break;
                case 4: //square
                    cl.setBackgroundResource(R.drawable.icon_square);
                    break;
                case 5: //triangle
                    cl.setBackgroundResource(R.drawable.icon_triangle);
                    break;
                case 6: //oval
                    cl.setBackgroundResource(R.drawable.icon_oval);
                    break;
                case 7: //pentagon
                    cl.setBackgroundResource(R.drawable.icon_pentagon);
                    break;
                case 8: //hexagon
                    cl.setBackgroundResource(R.drawable.icon_hexagon);
                    break;
                case 9: //triangle prav
                    cl.setBackgroundResource(R.drawable.icon_triangle_prav);
                    break;
                case 10: //izpit
                    cl.setBackgroundResource(R.drawable.icon_izpit);
                    break;
            }

            cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    f.performClick(); // натиск в/у фигурите в листа от ляво води до клик на истинската фигура -> отваря прозорец с настойки за фигурата
                }
            });
        }
    }

    private void updatePreviews(){
        if(windowPointer==0){
            Bitmap bitmap1 = null;
            CL_Window1.setDrawingCacheEnabled(true);
            bitmap1 = Bitmap.createBitmap(CL_Window1.getDrawingCache());
            CL_Window1.setDrawingCacheEnabled(false);
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, 100, (int) (100*1.666), false); // ппц трябва да се скейлва и за други устройства, които не са 16:9, обаче все тая, просто малко preview
            BitmapDrawable bmpDrawable1 = new BitmapDrawable(getResources(), bitmap1);
            //bmpDrawable1.setAlpha(200);
            CL_Window1Preview.setBackground(bmpDrawable1);
        } else {
            Bitmap bitmap2 = null;
            CL_Window2.setDrawingCacheEnabled(true);
            bitmap2 = Bitmap.createBitmap(CL_Window2.getDrawingCache());
            CL_Window2.setDrawingCacheEnabled(false);
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, 100, (int) (100*1.666), false);

            BitmapDrawable bmpDrawable2 = new BitmapDrawable(getResources(), bitmap2);
            //bmpDrawable2.setAlpha(200);

            CL_Window2Preview.setBackground(bmpDrawable2);
        }
    }

    /* ---------- Design Updater [  END  ] ---------- */

    // ----- Result -> избиране на проект от ProjectActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // получаваме резултат от ProjectsActivity
            if(resultCode == Activity.RESULT_OK){ // ако резултата е OK -> зареждаме проекта, който е избран
                String projectName = data.getStringExtra("projectName"); // взимаме името на избрания проект
                // чистим прозореца
                for(Figure f : windows[windowPointer].figures) {
                    f.setVisibility(View.GONE);
                }
                windows[windowPointer].figures.clear();
                windows[windowPointer].setTitle(projectName);
                if(windowPointer==0) CL_Window1.setBackgroundColor(Color.parseColor("#ffffffff"));
                else CL_Window2.setBackgroundColor(Color.parseColor("#ffffffff"));

                ET_Settings_Title.setText(projectName); // задаваме името в прозореца с настройки
                // забравихме да вкараме в датабазата цвета на бекграунда на платното ама к'вото такова
                ArrayList<FigureDB> figuresDB = db.getProject(projectName);
                for(FigureDB figureDB : figuresDB) { // започваме чертането на фигурите
                    Figure figure;
                    switch (figureDB.type) {
                        case 1: //point
                            figure = new angelzani.drawingshapes.figures.Point(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x+figureDB.width/2, figureDB.y+figureDB.height/2, figureDB.width, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 2: //line
                            figure = new Line(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.height, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 3: //circle
                            figure = new Circle(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 4: //square
                            figure = new Rectangle(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.height, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 5: //triangle
                            figure = new Triangle(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 6: //oval
                            figure = new Oval(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.height, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 7: //pentagon
                            figure = new Pentagon(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 8: //hexagon
                            figure = new Hexagon(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 9: //triangle prav
                            figure = new TrianglePrav(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.height, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                        case 10: //izpit
                            figure = new Izpit(getApplicationContext());
                            figure.setId(View.generateViewId());
                            if(windowPointer==0) { CL_Window1.addView(figure); }
                            else { CL_Window2.addView(figure); }
                            windows[windowPointer].figures.add(figure);
                            figure.title = figureDB.title;
                            figure.type = figureDB.type;
                            figure.drawShape(figureDB.x, figureDB.y, figureDB.width, figureDB.height, figureDB.fillColor, figureDB.strokeWidth, figureDB.strokeColor);
                            figure.setOnTouchListener(moveFigure);
                            figure.setOnClickListener(figureClicker);
                            figure.setRotation(figureDB.rotation);
                            break;
                    }
                }
                updateFiguresList();
                updatePreviews();
                B_Settings_Close.performClick();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult


    /* ---------- UTILITY ---------- */
    private Bitmap createBlurBitmapFromScreenSettings() {

        Bitmap bitmap = null;
        CL_Main.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(CL_Main.getDrawingCache());
        CL_Main.setDrawingCacheEnabled(false);
        bitmap = Bitmap.createScaledBitmap(bitmap, 480, 800, false);

        Bitmap result = null;
        try {
            RenderScript rsScript = RenderScript.create(getApplicationContext());
            Allocation alloc = Allocation.createFromBitmap(rsScript, bitmap);

            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rsScript, Element.U8_4(rsScript));
            blur.setRadius(21);
            blur.setInput(alloc);

            result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Allocation outAlloc = Allocation.createFromBitmap(rsScript, result);

            blur.forEach(outAlloc);
            outAlloc.copyTo(result);

            rsScript.destroy();
        } catch (Exception e) {
            return bitmap;
        }
        return result;
    }

    private void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view != null) inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() { // block finish()
        moveTaskToBack(true);
    }

} // END MainActivity{}