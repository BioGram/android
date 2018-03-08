package co.biogram.main.handler;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class CacheHandler
{
    public final static int DOWNLOAD = 0;
    public final static int DOCUMENT = 1;
    public final static int PICTURE = 2;
    public final static int VIDEO = 3;
    public final static int AUDIO = 4;
    public final static int FILE = 5;

    public static File TempDir(Context context)
    {
        File TempFolder = new File(context.getCacheDir(), "Temp");

        if (!TempFolder.exists())
            TempFolder.mkdir();

        return TempFolder;
    }

    public static File Dir(int Type)
    {
        File Bio = new File(Environment.getExternalStorageDirectory(), "Bio");

        if (!Bio.exists())
            Bio.mkdir();

        String Folder = "";

        switch (Type)
        {
            case DOWNLOAD: Folder = "Download"; break;
            case DOCUMENT: Folder = "Document"; break;
            case PICTURE: Folder = "Picture"; break;
            case VIDEO: Folder = "Video"; break;
            case AUDIO: Folder = "Audio"; break;
            case FILE: Folder = "File"; break;
        }

        File Select = new File(Bio, Folder);

        if (!Select.exists())
            Select.mkdir();

        return Select;
    }

    public static void SetUp(Context context)
    {
        File TempFolder = TempDir(context);

        if (TempFolder.exists() && TempFolder.isDirectory())
        {
            File[] TempFiles = TempFolder.listFiles();

            if (TempFiles != null)
                for (File file : TempFiles)
                    file.delete();
        }
    }

    /*File CacheFolder = new File(context.getCacheDir(), "BioGramCache");

            if (!CacheFolder.exists())
                CacheFolder.mkdir();

            File CacheFile = new File(context.getCacheDir() + "/BioGramCache/BioGramCacheList");

            if (!CacheFile.exists())
                 CacheFile.createNewFile();

            File CacheTemp = new File(CacheFile.getAbsolutePath() + ".tmp");
            CacheTemp.delete();
            CacheTemp.createNewFile();

            BufferedReader Reader = new BufferedReader(new FileReader(CacheFile));
            PrintWriter Writer = new PrintWriter(new FileWriter(CacheTemp));
            String Row;

            while ((Row = Reader.readLine()) != null)
            {
                String[] Data = Row.split("--");

                if (Data.length == 2)
                {
                    if (Integer.parseInt(Data[0]) < (System.currentTimeMillis() / 1000))
                    {
                        new File(context.getCacheDir() + "/BioGramCache/" + Data[1]).delete();
                    }
                    else
                    {
                        Writer.println(Row);
                        Writer.flush();
                    }
                }
            }

            Writer.close();
            Reader.close();

            if (CacheFile.delete())
                CacheTemp.renameTo(CacheFile);*/

    /*private static void StoreCacheData(Context context, String Data)
    {
        try
        {
            File CacheFile = new File(context.getCacheDir() + "/BioGramCache/BioGramCacheList");

            OutputStreamWriter Stream = new OutputStreamWriter(new FileOutputStream(CacheFile, true));
            Stream.append(String.valueOf((System.currentTimeMillis() / 1000) + 604800));
            Stream.append("--");
            Stream.append(Data);
            Stream.append("\n");
            Stream.flush();
            Stream.close();
        }
        catch (Exception e)
        {
            Misc.Debug("CacheHandler-StoreCacheData: " + e.toString());
        }
    }

    public static boolean LinkIsCache(Context context, String Name)
    {
        return new File(context.getCacheDir() + "/BioGramCache/" + Name.replaceAll("[^a-zA-Z0-9]", "")).exists();
    }

    public static String[] LinkFind(Context context, String Name)
    {
        try
        {
            File LinkFile = new File(context.getCacheDir() + "/BioGramCache/" + Name.replaceAll("[^a-zA-Z0-9]", ""));

            BufferedReader Reader = new BufferedReader(new FileReader(LinkFile));
            String[] Result = Reader.readLine().split(":::");
            Reader.close();

            return Result;
        }
        catch (Exception e)
        {
            Misc.Debug("CacheHandler-LinkFind: " + e.toString());
        }

        return null;
    }

    public static void LinkStore(Context context, String Name, String Title, String Description, String Image)
    {
        try
        {
            Name = Name.replaceAll("[^a-zA-Z0-9]", "");

            File LinkFile = new File(context.getCacheDir() + "/BioGramCache/" + Name);
            LinkFile.createNewFile();

            PrintWriter Writer = new PrintWriter(new FileWriter(LinkFile));
            Writer.println(Title + ":::" + Description + ":::" + Image);
            Writer.flush();
            Writer.close();

            StoreCacheData(context, Name);
        }
        catch (Exception e)
        {
            Misc.Debug("CacheHandler-LinkStore: " + e.toString());
        }
    }

    static boolean BitmapIsCache(Context context, String Name)
    {
        return new File(context.getCacheDir() + "/BioGramCache/" + Name.replaceAll("[^a-zA-Z0-9]", "")).exists();
    }

    static Bitmap BitmapFind(Context context, String Name)
    {
        try
        {
            File BitmapFile = new File(context.getCacheDir() + "/BioGramCache/" + Name.replaceAll("[^a-zA-Z0-9]", ""));

            RandomAccessFile RAFile = new RandomAccessFile(BitmapFile, "r");
            byte[] ByteArray = new byte[(int) RAFile.length()];
            RAFile.read(ByteArray);

            if (ByteArray.length == 0)
                return null;

            return BitmapFactory.decodeByteArray(ByteArray, 0, ByteArray.length);
        }
        catch (Exception e)
        {
            Misc.Debug("CacheHandler-BitmapFind: " + e.toString());
        }

        return null;
    }

    static void BitmapStore(Context context, Bitmap bitmap, String Name)
    {
        if (bitmap == null)
            return;

        try
        {
            Name = Name.replaceAll("[^a-zA-Z0-9]", "");

            File BitmapFile = new File(context.getCacheDir() + "/BioGramCache/" + Name);
            BitmapFile.createNewFile();

            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, BAOS);
            BAOS.toByteArray();
            byte[] BitmapData = BAOS.toByteArray();

            FileOutputStream fos = new FileOutputStream(BitmapFile);
            fos.write(BitmapData);
            fos.flush();
            fos.close();

            StoreCacheData(context, Name);
        }
        catch (Exception e)
        {
            Misc.Debug("CacheHandler-StoreBitmap: " + e.toString());
        }
    }*/
}
