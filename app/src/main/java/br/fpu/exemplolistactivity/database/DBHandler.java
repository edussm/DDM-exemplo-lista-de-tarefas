package br.fpu.exemplolistactivity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.fpu.exemplolistactivity.domain.Status;
import br.fpu.exemplolistactivity.domain.Tarefa;


public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "lista-de-tarefas";

    private static final String TABLE_TAREFA = "tarefa";
    // Nome das colunas
    private static final String KEY_ID = "id";
    private static final String KEY_DESCRICAO = "desc";
    private static final String KEY_RESPONSAVEL = "resp";
    private static final String KEY_STATUS = "status";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTarefa = "CREATE TABLE " + TABLE_TAREFA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DESCRICAO + " TEXT,"
                + KEY_RESPONSAVEL + " TEXT, " + KEY_STATUS + " TEXT )";
        db.execSQL(createTableTarefa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        // Quero apagar a tabela antiga
        // TODO: Melhorar essa estratégia
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREFA);
        // Cria a tabela
        onCreate(db);
    }

    public void addTarefa(Tarefa tarefa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DESCRICAO, tarefa.getDescricao());
        values.put(KEY_RESPONSAVEL, tarefa.getExecutor());
        values.put(KEY_STATUS, tarefa.getStatus().toString());

        db.insert(TABLE_TAREFA, null, values);
        db.close();
    }

    public Tarefa getTarefa(int id) {
        Tarefa tarefa = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TAREFA,
                new String[]{KEY_ID, KEY_DESCRICAO,
                        KEY_RESPONSAVEL, KEY_STATUS},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            tarefa = new Tarefa();
            tarefa.setId(cursor.getLong(0));
            tarefa.setDescricao(cursor.getString(1));
            tarefa.setExecutor(cursor.getString(2));
            tarefa.setStatus(Status.valueOf(cursor.getString(3)));
        }

        return tarefa;
    }

    public List<Tarefa> getAllTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TAREFA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(cursor.getLong(0));
                tarefa.setDescricao(cursor.getString(1));
                tarefa.setExecutor(cursor.getString(2));
                tarefa.setStatus(Status.valueOf(cursor.getString(3)));
                tarefas.add(tarefa);
            } while (cursor.moveToNext());
        }

        return tarefas;
    }

    public int updateTarefa(Tarefa tarefa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRICAO, tarefa.getDescricao());
        values.put(KEY_RESPONSAVEL, tarefa.getExecutor());
        values.put(KEY_STATUS, tarefa.getStatus().toString());

        return db.update(TABLE_TAREFA, values, KEY_ID + " = ?",
                new String[]{String.valueOf(tarefa.getId())});
    }

    public void deleteTarefa(Tarefa tarefa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAREFA, KEY_ID + " = ?",
                new String[] { String.valueOf(tarefa.getId()) });
        db.close();
    }

}
