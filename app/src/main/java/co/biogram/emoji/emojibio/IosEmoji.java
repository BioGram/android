package co.biogram.emoji.emojibio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import co.biogram.emoji.core.emoji.Emoji;
import co.biogram.main.R;

public class IosEmoji extends Emoji
{
    private static final Object LOCK = new Object();
    private static volatile Bitmap sheet;

    private final int x;
    private final int y;

    public IosEmoji(@NonNull final int[] codePoints, final int x, final int y)
    {
        super(codePoints, -1);

        this.x = x;
        this.y = y;
    }

    public IosEmoji(final int codePoint, final int x, final int y)
    {
        super(codePoint, -1);

        this.x = x;
        this.y = y;
    }

    public IosEmoji(final int codePoint, final int x, final int y, final Emoji... variants)
    {
        super(codePoint, -1, variants);

        this.x = x;
        this.y = y;
    }

    public IosEmoji(@NonNull final int[] codePoints, final int x, final int y, final Emoji... variants)
    {
        super(codePoints, -1, variants);

        this.x = x;
        this.y = y;
    }

    @NonNull
    @Override
    public Drawable getDrawable(final Context context)
    {
        if (sheet == null)
        {
            synchronized (LOCK)
            {
                if (sheet == null)
                {
                    sheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.emoji_ios_sheet);
                }
            }
        }

        final Bitmap cut = Bitmap.createBitmap(sheet, x * 66, y * 66, 64, 64);

        return new BitmapDrawable(context.getResources(), cut);
    }

    @Override
    public void destroy()
    {
        if (sheet != null)
        {
            synchronized (LOCK)
            {
                if (sheet != null)
                {
                    sheet.recycle();
                    sheet = null;
                }
            }
        }
    }
}
