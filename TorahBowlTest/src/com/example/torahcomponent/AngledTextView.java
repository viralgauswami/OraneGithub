package com.example.torahcomponent;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;
  
public class AngledTextView extends TextView  
{  
	Random rnd = new Random();
	int rndAngle = rnd.nextInt()*360;
    public AngledTextView(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas)  
    {  
    	
        //Save the current matrix  
        canvas.save();  
        //Rotate this View at its center  
        canvas.rotate(rndAngle, this.getWidth()/2, this.getHeight()/2);  
        //Draw it  
        super.onDraw(canvas);  
        //Restore to the previous matrix  
        canvas.restore();  
    }  
}  
