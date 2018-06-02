package co.tpcreative.common.ui;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;
import co.tpcreative.R;


public class FontEditText extends AppCompatEditText {
    private boolean blockSelection = false;
    public FontEditText(Context context) {
        super(context);
    }
    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(this, context, attrs);
    }
    public FontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(this, context, attrs);
    }

    private void setCustomFont(EditText editText, Context context, AttributeSet attrs) {
        try {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
            String font = a.getString(R.styleable.CustomFont_fonts);
            setCustomFont(editText, font, context);
            a.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setCustomFont(EditText editText, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            editText.setTypeface(tf);
        }
    }
    @Override
    public void onSelectionChanged(int start, int end) {
        if (blockSelection) {
            CharSequence text = getText();
            if (text != null) {
                if (start != text.length() || end != text.length()) {
                    setSelection(text.length(), text.length());
                    return;
                }
            }
        }

        super.onSelectionChanged(start, end);
    }
    public void setBlockSelection(boolean blockSelection) {
        this.blockSelection = blockSelection;
    }
}
