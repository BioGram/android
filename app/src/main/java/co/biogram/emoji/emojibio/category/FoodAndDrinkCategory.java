package co.biogram.emoji.emojibio.category;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import co.biogram.emoji.core.emoji.EmojiCategory;
import co.biogram.emoji.emojibio.IosEmoji;
import co.biogram.main.R;

@SuppressWarnings("PMD.MethodReturnsInternalArray")
public final class FoodAndDrinkCategory implements EmojiCategory
{
    private static final IosEmoji[] DATA = new IosEmoji[] { new IosEmoji(0x1F347, 7, 9), new IosEmoji(0x1F348, 7, 10), new IosEmoji(0x1F349, 7, 11), new IosEmoji(0x1F34A, 7, 12), new IosEmoji(0x1F34B, 7, 13), new IosEmoji(0x1F34C, 7, 14), new IosEmoji(0x1F34D, 7, 15), new IosEmoji(0x1F34E, 7, 16), new IosEmoji(0x1F34F, 7, 17), new IosEmoji(0x1F350, 7, 18), new IosEmoji(0x1F351, 7, 19), new IosEmoji(0x1F352, 7, 20), new IosEmoji(0x1F353, 7, 21), new IosEmoji(0x1F95D, 42, 9), new IosEmoji(0x1F345, 7, 7), new IosEmoji(0x1F965, 42, 17), new IosEmoji(0x1F951, 41, 49), new IosEmoji(0x1F346, 7, 8), new IosEmoji(0x1F954, 42, 0), new IosEmoji(0x1F955, 42, 1), new IosEmoji(0x1F33D, 6, 51), new IosEmoji(new int[] { 0x1F336, 0xFE0F }, 6, 44), new IosEmoji(0x1F952, 41, 50), new IosEmoji(0x1F966, 42, 18), new IosEmoji(0x1F344, 7, 6), new IosEmoji(0x1F95C, 42, 8), new IosEmoji(0x1F330, 6, 38), new IosEmoji(0x1F35E, 7, 32), new IosEmoji(0x1F950, 41, 48), new IosEmoji(0x1F956, 42, 2), new IosEmoji(0x1F968, 42, 20), new IosEmoji(0x1F95E, 42, 10), new IosEmoji(0x1F9C0, 42, 48), new IosEmoji(0x1F356, 7, 24), new IosEmoji(0x1F357, 7, 25), new IosEmoji(0x1F969, 42, 21), new IosEmoji(0x1F953, 41, 51), new IosEmoji(0x1F354, 7, 22), new IosEmoji(0x1F35F, 7, 33), new IosEmoji(0x1F355, 7, 23), new IosEmoji(0x1F32D, 6, 35), new IosEmoji(0x1F96A, 42, 22), new IosEmoji(0x1F32E, 6, 36), new IosEmoji(0x1F32F, 6, 37), new IosEmoji(0x1F959, 42, 5), new IosEmoji(0x1F95A, 42, 6), new IosEmoji(0x1F373, 8, 1), new IosEmoji(0x1F958, 42, 4), new IosEmoji(0x1F372, 8, 0), new IosEmoji(0x1F963, 42, 15), new IosEmoji(0x1F957, 42, 3), new IosEmoji(0x1F37F, 8, 13), new IosEmoji(0x1F96B, 42, 23), new IosEmoji(0x1F371, 7, 51), new IosEmoji(0x1F358, 7, 26), new IosEmoji(0x1F359, 7, 27), new IosEmoji(0x1F35A, 7, 28), new IosEmoji(0x1F35B, 7, 29), new IosEmoji(0x1F35C, 7, 30), new IosEmoji(0x1F35D, 7, 31), new IosEmoji(0x1F360, 7, 34), new IosEmoji(0x1F362, 7, 36), new IosEmoji(0x1F363, 7, 37), new IosEmoji(0x1F364, 7, 38), new IosEmoji(0x1F365, 7, 39), new IosEmoji(0x1F361, 7, 35), new IosEmoji(0x1F95F, 42, 11), new IosEmoji(0x1F960, 42, 12), new IosEmoji(0x1F961, 42, 13), new IosEmoji(0x1F366, 7, 40), new IosEmoji(0x1F367, 7, 41), new IosEmoji(0x1F368, 7, 42), new IosEmoji(0x1F369, 7, 43), new IosEmoji(0x1F36A, 7, 44), new IosEmoji(0x1F382, 8, 16), new IosEmoji(0x1F370, 7, 50), new IosEmoji(0x1F967, 42, 19), new IosEmoji(0x1F36B, 7, 45), new IosEmoji(0x1F36C, 7, 46), new IosEmoji(0x1F36D, 7, 47), new IosEmoji(0x1F36E, 7, 48), new IosEmoji(0x1F36F, 7, 49), new IosEmoji(0x1F37C, 8, 10), new IosEmoji(0x1F95B, 42, 7), new IosEmoji(0x2615, 47, 24), new IosEmoji(0x1F375, 8, 3), new IosEmoji(0x1F376, 8, 4), new IosEmoji(0x1F37E, 8, 12), new IosEmoji(0x1F377, 8, 5), new IosEmoji(0x1F378, 8, 6), new IosEmoji(0x1F379, 8, 7), new IosEmoji(0x1F37A, 8, 8), new IosEmoji(0x1F37B, 8, 9), new IosEmoji(0x1F942, 41, 38), new IosEmoji(0x1F943, 41, 39), new IosEmoji(0x1F964, 42, 16), new IosEmoji(0x1F962, 42, 14), new IosEmoji(new int[] { 0x1F37D, 0xFE0F }, 8, 11), new IosEmoji(0x1F374, 8, 2), new IosEmoji(0x1F944, 41, 40), new IosEmoji(0x1F52A, 27, 44), new IosEmoji(0x1F3FA, 12, 24) };

    @Override
    @NonNull
    public IosEmoji[] getEmojis()
    {
        return DATA;
    }

    @Override
    @DrawableRes
    public int getIcon()
    {
        return R.drawable.emoji_ios_category_foodanddrink;
    }
}
