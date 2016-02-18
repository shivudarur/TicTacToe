package tictactoe.shore.com.tictactoe.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import tictactoe.shore.com.tictactoe.model.Player;

/**
 * Created by Shiva on 2/18/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "playersManager";
    private static final String TABLE_CONTACTS = "players";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "socre";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance  
    }

    // Creating Tables  
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database  
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed  
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again  
        onCreate(db);
    }

    // code to add the new player  
    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName()); // Player Name  
        values.put(KEY_SCORE, player.getScore()); // Player Phone

        // Inserting Row  
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack  
        db.close(); // Closing database connection  
    }

    // code to get all players in a list view  
    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<>();
        // Select All Query, in ascending order of score
        String selectQuery = "SELECT " + KEY_NAME + " , " + KEY_SCORE + " FROM " + TABLE_CONTACTS + " ORDER BY " + KEY_SCORE + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                player.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCORE)));
                // Adding player to list  
                playerList.add(player);
            } while (cursor.moveToNext());
        }

        // return player list
        cursor.close();
        return playerList;
    }
}