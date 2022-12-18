package com.good.music;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<String> folders = new ArrayList<>();

    private static FileWriter CreateFile(String absolutePath, boolean append) {
        String folder = absolutePath.substring(0, absolutePath.lastIndexOf('/'));
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdir();
            Log.i(null, "Created \"" + folder + "\" folder");
        }

        try {
            if (file.exists()) {
                File folderTxt = new File(absolutePath);
                return new FileWriter(folderTxt, append);
            }
            else {
                Log.e(null, "The folder doesn't exist (Missing permissions)");
            }
        }
        catch (Exception e) {
            Log.e("FILE CREATION", e.toString());
        }

        return null;
    }
    private static boolean FileExists(String absolutePath) {
        File file = new File(absolutePath);
        return file.exists();
    }

    // Which folders can I use?
    public static void ReadFoldersTXT(Context context) {
        String filename = "/storage/emulated/0/GoodMusic/folders.txt";

        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String inputString;

            //Reading data line by line and storing it into the stringbuffer

            while ((inputString = inputReader.readLine()) != null) {
                folders.add(inputString);
                stringBuffer.append(inputString + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(stringBuffer.toString().length() == 0){
            Toast.makeText(context,"File not Available...!", Toast.LENGTH_LONG).show();
            CreateFoldersTXT();
        }
    }
    // Create folders.txt if it doesn't exist
    private static void CreateFoldersTXT() {
        FileWriter file = CreateFile("/storage/emulated/0/GoodMusic/folders.txt", false);
        try {
            file.append("/storage/emulated/0/Music");
            file.flush();
            file.close();
        }
        catch (Exception e) {
            Log.e("FILE CREATION ERROR", "Writing permission is required");
        }
    }

    // ------------------
    public static ArrayList<Song> songs = new ArrayList<>();

    // Read folder and put contents in Song List
    private static void GetSongListFromFolder(String startPoint) {
        FileWriter songListFile = CreateFile("/storage/emulated/0/GoodMusic/SongInfo.txt", true);

        File folder = new File(startPoint);
        File[] filesInFolder = folder.listFiles(); // This returns all the folders and files in your path
        for (File file : filesInFolder) { //For each of the entries do:
            if (!file.isDirectory()) { //check that it's not a dir
                String filename = file.getName();
                if (
                        filename.endsWith(".mp3") ||
                        filename.endsWith(".wav") ||
                        filename.endsWith(".flac") ||
                        filename.endsWith(".ogg")
                ) {
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();

                    try {
                        mmr.setDataSource(startPoint + "/" + filename);
                    } catch (Exception e) {
                        Log.i("IGNORED SONG", startPoint + "/" + filename);
                    }

                    String id = filename.substring(0, filename.lastIndexOf('.')),
                            title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                            album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
                            artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                    ;

                    try {
                        songListFile.append(
                                id + '\n' +
                                startPoint + '/' + filename + '\n' +
                                (title!=null ? title : id) + '\n' +
                                (album!=null ? album : "Unknown") + '\n' +
                                (artist!=null ? artist : "Unknown") + '\n'
                        );
                        songListFile.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                GetSongListFromFolder(file.getAbsolutePath());
            }
        }

        try {
            songListFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void GetSongListFromFile() {
        String[] data = new String[5];
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream("/storage/emulated/0/GoodMusic/SongInfo.txt")));
            String inputString;
            int counter = 0;

            //Reading data line by line and storing it into the stringbuffer

            while ((inputString = inputReader.readLine()) != null) {
                data[counter%5] = inputString;
                counter++;
                if (counter%5 == 0) {
                    Song song = new Song(
                            songs.size(),
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            data[4]
                    );
                    songs.add(song);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generate Song List
    public static void GetSongList(Context context) {
        ReadFoldersTXT(context);

        if (!FileExists("/storage/emulated/0/GoodMusic/SongInfo.txt")) {
            Log.e("FILE CREATION", "SCANNING SONGS");
            for (int i = 0; i < folders.size(); i++) {
                GetSongListFromFolder(folders.get(i));
            }
        }

        GetSongListFromFile();
    }
}
