package co.biogram.main.handler;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;

import android.widget.TextView;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import co.biogram.main.R;

class TagHandler
{
    public static void Show(TextView textView)
    {
        if (textView.getText().length() <= 2)
            return;

        textView.setText(textView.getText(), TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);

        int Index = 0;
        CharSequence Text = textView.getText();

        while (Index < Text.length() - 1)
        {
            char Sign = Text.charAt(Index);
            char NextSign = Text.charAt(Index + 1);
            int NextNotLetterDigitCharIndex = Index + 1;

            if ((Sign == '#' || Sign == '@') && (NextSign != '#' && NextSign != '@'))
            {
                NextNotLetterDigitCharIndex = FindNextValidTagChar(Text, Index);

                Spannable Span = (Spannable) textView.getText();
                CharacterStyle TagChar = new HashTagSpan(Sign == '@' ? 2 : 1, textView.getContext());

                Span.setSpan(TagChar, Index, NextNotLetterDigitCharIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            Index = NextNotLetterDigitCharIndex;
        }
    }

    private static int FindNextValidTagChar(CharSequence Text, int Start)
    {
        int NonLetterDigitCharIndex = -1;

        for (int I = Start + 1; I < Text.length(); I++)
        {
            char Sign = Text.charAt(I);
            boolean IsValidSign = Character.isLetterOrDigit(Sign) || Sign == '_' || Sign == '.';

            if (!IsValidSign)
            {
                NonLetterDigitCharIndex = I;
                break;
            }
        }

        if (NonLetterDigitCharIndex == -1)
            NonLetterDigitCharIndex = Text.length();

        return NonLetterDigitCharIndex;
    }

    private static class HashTagSpan extends ClickableSpan
    {
        private final Context context;
        private final int TagColor;
        private final int Type;

        HashTagSpan(int type, Context c)
        {
            Type = type;
            context = c;
            TagColor = ContextCompat.getColor(c, R.color.BlueLight);
        }

        @Override
        public void updateDrawState(TextPaint textpaint)
        {
            textpaint.setColor(TagColor);
        }

        @Override
        public void onClick(View Widget)
        {
            CharSequence Text = ((TextView) Widget).getText();
            Spanned Span = (Spanned) Text;
            int Start = Span.getSpanStart(this);
            int End = Span.getSpanEnd(this);
            String Message = Text.subSequence(Start + 1, End).toString();

            if (Type == 1)
            {
                Misc.Toast(Message + " - HashTag Clicked");

                // TODO Open View
            }
            else if (Type == 2)
            {
                if (SharedHandler.GetString(context, "Username").equalsIgnoreCase(Message))
                    return;

                Misc.Toast(Message + " - ID Clicked");

                // TODO Open View
            }
        }
    }
}
