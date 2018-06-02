package co.tpcreative.common.ui;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import co.tpcreative.R;
import co.tpcreative.common.Configs;
import co.tpcreative.common.utils.FileUtils;


public class FontButton extends AppCompatButton {

    private static final String TAG = FontButton.class.getSimpleName();
    private static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    // Original text value (before spacing)
    private CharSequence originalText = null;
    // Flag to present text in ALL CAPS
    private boolean textAllCaps = false;
    // Spacing value (dp)
    private float spacing = 0f;
    // Density (dp) scale based on 320 screen resolution
    private float dpScale = 1f;
    // Pixel scale based on 320 screen resolution
    private float pxScale = 1f;
    public FontButton(Context context) {
        super(context);
    }

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(this, context, attrs);
    }

    public FontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(this, context, attrs);
    }

    private void setCustomFont(Button button, Context context, AttributeSet attrs) {
        dpScale = FileUtils.scaleDensity(context);
        pxScale = FileUtils.scalePixel(context);
        originalText = getText();
        try {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
            String font = a.getString(R.styleable.CustomFont_fonts);
            textAllCaps = attrs.getAttributeBooleanValue(XML_NAMESPACE_ANDROID, "textAllCaps", false);
            originalText = super.getText();
            spacing = a.getFloat(R.styleable.CustomFont_spacings , 0f);
            setSpacing(spacing);
            setCustomFont(button, font, context);
            a.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCustomFont(Button button, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            button.setTypeface(tf);
        }
        button.setBackgroundDrawable(getDrawable());
    }

    public Drawable getDrawable() {
        StateListDrawable states = new StateListDrawable() {
            @Override
            protected boolean onStateChange(int[] states) {
                boolean isSelected = false;
                boolean isPressed = false;
                for (int state : states) {
                    if (state == android.R.attr.state_pressed) {
                        isPressed = true;
                    }
                    if (state == android.R.attr.state_selected) {
                        isSelected = true;
                    }
                }
                if (FontButton.this.getBackground() != null) {
                    if (isPressed)
                        FontButton.this.getBackground().setAlpha(200);
                    else if (isSelected)
                        FontButton.this.getBackground().setAlpha(155);
                    else
                        FontButton.this.getBackground().setAlpha(255);
                }
                return super.onStateChange(states);
            }
        };
        Drawable normalDrawable = this.getBackground();

        states.addState(new int[]{android.R.attr.state_pressed}, normalDrawable);
        states.addState(new int[]{android.R.attr.state_selected}, normalDrawable);
        states.addState(new int[]{}, normalDrawable);
        return states;
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        this.setBackgroundDrawable(getDrawable());
    }

    private void setCustomFont(TextView textView, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            textView.setTypeface(tf);
        }
    }

    public float getSpacing() {
        return this.spacing;
    }

    public void setSpacing(float spacing) {
        if (spacing == 0f) {
            return;
        }
        this.spacing = spacing;
        applyLetterSpacing();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text,type);
        if (this.spacing == 0f){
            super.setText(text , type);
            return;
        }
        originalText = text;
        applyLetterSpacing();
    }

    private void applyLetterSpacing() {
        if (spacing == 0f) {
            return;
        }
        // Check if current SDK version is after LOLLIPOP (21), then use letter spacing instead
        if (!isInEditMode() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setLetterSpacing(spacing * dpScale / 11); // 'EM' unit: 1 'EM' = 11 dp
            super.setText(originalText , BufferType.NORMAL);
            return;
        }
        if (TextUtils.isEmpty(originalText)
                || originalText.length() < 2
                || (spacing >= -1f && spacing <= 1f)) {
            return;
        }
        // Make ALL CAPS flag false first
        setAllCaps(false);
        // Create text builder with non breaking space letter between
        StringBuilder builder;
        if (textAllCaps) {
            builder = new StringBuilder(originalText.toString().toUpperCase().replaceAll("(.)", Configs.NON_BREAKING_SPACE + "$1"));
        } else {
            builder = new StringBuilder(originalText.toString().replaceAll("(.)", Configs.NON_BREAKING_SPACE + "$1"));
        }
        float nonBreakingSpace = getPaint().measureText(Configs.NON_BREAKING_SPACE);
        Log.d(TAG, "nonBreakingSpace = " + nonBreakingSpace);
        float scale = spacing * pxScale / nonBreakingSpace;
        Log.d(TAG, "scale = " + scale);
        if (scale > 0.9 && scale < 1f) {
            super.setText(builder, BufferType.NORMAL);
            return;
        }
        // Scale all non breaking space letters by provided spacing value
        SpannableString finalText = new SpannableString(builder);
        if (builder.length() > 1) {
            for (int i = 0; i < builder.length(); i += 2) {
                finalText.setSpan(new ScaleXSpan(scale),
                        i,
                        i + 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                );
            }
        }
        // Set text with buffer type SPANNABLE
        super.setText(finalText, BufferType.SPANNABLE);

        setPadding(getPaddingLeft() - (int) (spacing * pxScale - spacing), getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }
}
